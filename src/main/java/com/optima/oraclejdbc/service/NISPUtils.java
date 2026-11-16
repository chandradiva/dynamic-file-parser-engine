package com.optima.oraclejdbc.service;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.beans.factory.annotation.Autowired;

import com.optima.oraclejdbc.cons.CommonCons;

public class NISPUtils {
	
//	@Autowired
//	WatchDir watchDir;

//	private static File getNISPMasterDataDir() {
//		File dir = new File(CommonCons.FTP_INPUT);
//		if (!dir.exists())
//			dir.mkdirs();
//		return dir;
//	}
//	
//	public static void initFolderListener() throws IOException {
//		String pathToWatch = CommonCons.FTP_INPUT;
//		
//		Path dir = Paths.get(pathToWatch);
//		final WatchDir watch = new WatchDir(dir, true);
//		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				watch.processEvents();
//			}
//		}).start();
//		
//	}
	
//	public static void initMasterDataListener() throws IOException {
//		final File file = getNISPMasterDataDir();
//		FolderWatchers.getInstance().addFolderListener(Paths.get(file.toURI()),
//                new ChangeListener(ENTRY_MODIFY) {
//					
//					@Override
//					public void onEvent(WatchEvent<Path> anEvent) {
//						new Thread(new Runnable() {
//							
//							@Override
//							public void run() {
//								try {
//									for (String item : file.list()) {
//										System.out.println(item);
//									}
////									readMasterDataFile();
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//							}
//						}).start();
//					}
//				});
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					for (String item : file.list()) {
//						System.out.println(item + " =");
//					}
////					readMasterDataFile();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
}
