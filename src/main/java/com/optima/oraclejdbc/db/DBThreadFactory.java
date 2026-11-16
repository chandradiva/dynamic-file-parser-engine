package com.optima.oraclejdbc.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

public class DBThreadFactory implements ThreadFactory{

	private final int maxThread = 4;
	private List<Runnable> queue;
	private static DBThreadFactory instance;
	private volatile int runNow = 0;
	private Object sync = new Object();
	private Object waitSync = new Object();
	
	private DBThreadFactory(){
		queue = new ArrayList<Runnable>();
	}
	
	public static DBThreadFactory intances(){
		if( instance == null )
			instance = new DBThreadFactory();
		return instance;
	}
	
	public Thread newThread(Runnable r) {
		synchronized (sync) {
			queue.add(r);			
		}
		next();
		return null;
	}
	
	private void next(){
		if( runNow < maxThread ){
			synchronized (sync) {
				if( queue.size() == 0 ){
					sync.notifyAll();
					return;			
				}
			}
			runNow++;
			Thread tControll = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Thread t = null;
					synchronized (sync) {
						t = new Thread(queue.get(queue.size()-1));
						queue.remove(queue.size()-1);
					}
					
					t.start();
					try {
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					runNow--;
					synchronized (waitSync) {
						waitSync.notify();
					}
					next();
				}
			});
			tControll.start();									
		}else{
			synchronized (waitSync) {
				try {
					waitSync.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Object getSyncObject(){
		return sync;
	}
}
