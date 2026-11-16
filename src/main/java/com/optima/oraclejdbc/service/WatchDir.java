package com.optima.oraclejdbc.service;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.optima.oraclejdbc.parser.NISPParser;
import com.optima.oraclejdbc.utils.FileUtility;

public class WatchDir<G> {	
	private static final Logger logger = Logger.getLogger(WatchDir.class);
	
	private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private boolean recursive=false;
    private boolean trace = false;
    private Hashtable<String, Timer> executor = new Hashtable<String, Timer>();
    
    private boolean stop = false;
    
    private static Object lock = new Object();
    
    @Autowired
    protected NISPParser<G> parser;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
    	String dirName = dir.toString();
    	if( dirName.endsWith("input") ){
    		processEntries(dirName);
    	}
    	
        WatchKey key = dir.register(watcher, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public WatchDir() throws IOException{
    	this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();        
    }
    
    public void setRootDir(Path dir, boolean recursive) throws IOException{
    	this.recursive = recursive;

        if (recursive) {
            System.out.format("Scanning %s ...\n", dir);
            registerAll(dir);
            System.out.println("Done.");
        } else {
            register(dir);
        }

        // enable trace after initial registration
        this.trace = true;
    }
    
    /**
     * Creates a WatchService and registers the given directory
     */
    WatchDir(Path dir, boolean recursive) throws IOException {
    	this();
        setRootDir(dir, recursive);
    }

    /*
     * Remove directory executor from queue
     */
    private void removeFromExecutor(String pathChk){
	    Timer timer = executor.get(pathChk);
		if( timer != null ){
			timer.cancel();
			timer.purge();
			executor.remove(pathChk);
		}
    }
    
	
    /**
     * Process all events for keys queued to the watcher
     */
    public void processEvents() {
	    for (;!stop;) {

            // wait for key to be signalled
            WatchKey key;
            try {
            	System.out.println("Before take key "+this.hashCode());
                key = watcher.take();
                System.out.println("After take key");
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }
                
                synchronized (lock) {
                	// Context for directory entry event is the file name of entry
                        WatchEvent<Path> ev = cast(event);
                        Path name = ev.context();
                        Path child = dir.resolve(name);

                        // print out event
                        System.out.format("%s: %s\n", event.kind().name(), child);
                        File f = new File(child.toString());
                        if( !f.exists() )
                        	break;
                        if( f.isDirectory() ){
                        	// if directory is created, and watching recursively, then
                            // register it and its sub-directories
                        	if (recursive && (kind == ENTRY_CREATE)) {
                                try {
                                    if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                                        registerAll(child);
                                    }
                                } catch (IOException x) {
                                    // ignore to keep sample readbale
                                }
                            }
                        	break;            
                        }
                        	
                        if( f.isFile() && kind == ENTRY_MODIFY ){
                        	if (f.getParent().endsWith("input")) {
                        		//move to pending to process
                        		processEntries(child.getParent().toString());
//                        		String pathChk = child.toString();                        		
//                        		processEntry(pathChk);
                        	}
                        	/*
                        	 * Disabled, we don't need watcher on process directory
                        	 * after successfully moved to process folder data file will be processed directly
                        	 */
//                        	else if (child.toString().contains("process")) {
//                        		//check file .DTA
//                        		String ext = child.toString().substring(child.toString().length() - 4);
//                            	ext = ext.toUpperCase();
//                            	
//                            	if (ext.equals(".DTA") || ext.equals(".DAT")) {
//                            		List<String> content = parser.setFilename(child.toString());
//                            		
//                            	}
//                        	}
                        }       
				}
                        
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
	    System.out.println("Watcher finished");
    }
    
    public void stop(){
	    try {
		    stop = true;
		    watcher.close();		    
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void processEntry(String pathChk){
    	removeFromExecutor(pathChk);
		String fileDta = "";
		
		String ext = pathChk.substring(pathChk.length() - 4);
    	ext = ext.toUpperCase();
    	
    	boolean moveToPending = false;
    	
    	if (ext.equals(".CHK")) {
    		fileDta = pathChk.replace(".CHK", ".DTA");
    		File data = new File(fileDta);
    		if (data.exists()) {
    			moveToPending = true;
    			//FileUtility.moveFileToPending(pathChk, fileDta);
    		} else {
    			fileDta = pathChk.replace(".CHK", ".DAT");
    			data = new File(fileDta);
    			if (data.exists()) {
    				moveToPending = true;
        			//FileUtility.moveFileToPending(pathChk, fileDta);
        		}
    		}
    	}else if( ext.equals(".DTA") || ext.equals(".DAT")){
    		fileDta = pathChk;
    		String strFileChk = pathChk.replace(ext, ".CHK");
    		pathChk = strFileChk;
    		File fileChk = new File(strFileChk);
    		if( fileChk.exists() ){
    			moveToPending = true;
    			//FileUtility.moveFileToPending(strFileChk, fileDta);
    		}                        		
    	}                		
    	
    	final String fChk = pathChk;
    	final String fDta = fileDta;
    	if( moveToPending ){
    		System.out.println("path chk: "+pathChk);
    		Timer t = executor.get(pathChk);
    		if( t == null ){
    			t = new Timer();
        		t.schedule(new TimerTask() {
					
					@Override
					public void run() {
						removeFromExecutor(fChk);
						String pendingFilename = FileUtility.moveFileToPending(fChk, fDta);
						if( pendingFilename != null)
							try {
								parser.setFilename(pendingFilename);
							} catch (Exception e) {
								logger.error("Error", e);
							}
						
					}
				}, 3000);
        		executor.put(pathChk, t);        		
    		}                    		
    	}
    }
    
    private void processEntries(String dirName){
    	File folder = new File(dirName);
		if (folder.isDirectory()) {
			boolean done = false;
			do{
				File[] files = folder.listFiles();
				if( files.length == 0 )
					done = true;
				else{
        			for (File item : files) {    				
        				if (item.isFile() && item.getName().endsWith(".CHK")) {
        					processEntry(item.getAbsolutePath());
        				}
        			}
        			try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}while(!done);
		}
    }
}
