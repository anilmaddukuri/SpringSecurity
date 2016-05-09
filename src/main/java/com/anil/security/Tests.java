package com.anil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Tests {
	
	static ExecutorService es;
	
	static {		
		int nbrOfProcessors = Runtime.getRuntime().availableProcessors();
		es = Executors.newFixedThreadPool(nbrOfProcessors);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		DirWalkingThread dwt = new DirWalkingThread(new File(input));
		Future<List<File>> result = es.submit(dwt);
		try {
			List<File> output = result.get();
			Collections.sort(output);
			for(File f : output) {
				System.out.println(f.getAbsolutePath());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		es.shutdownNow();
		sc.close();
	}

}

class DirWalkingThread implements Callable<List<File>> {
	
	private File start = null; 
	
	public DirWalkingThread(File input) {
		this.start = input;
	}
	
	@Override
	public List<File> call() throws Exception {
		List<File> list = new ArrayList<>();
		list.add(start);
		if(start.isDirectory()) {
			//traverse sub directories also
			File[] subDirs = start.listFiles();
			List<Future<List<File>>> futures = new ArrayList<>();
			if(subDirs != null && subDirs.length > 0) {
				ExecutorService es = Executors.newFixedThreadPool(3);
				for (int i = 0; i < subDirs.length; i++) {
					DirWalkingThread th = new DirWalkingThread(subDirs[i]);					
					futures.add(es.submit(th));
				}
				for(int i =0; i < futures.size(); i++) {
					Future<List<File>> t = futures.get(i);
					List<File> f = t.get();
					list.addAll(f);
				}
				es.shutdownNow();
			}
		}
		return list;
	}
}

