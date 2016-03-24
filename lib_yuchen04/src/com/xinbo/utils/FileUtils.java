package com.xinbo.utils;

import java.io.File;

public final class FileUtils {
	private static long fileLen;

	public static void delFilesFromPath(File filePath) {
		File[] files = filePath.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()){
				files[i].delete();
			}else{
				delFilesFromPath(files[i]);
			}
		}
	}
	public static void getFileLenFromPath(File filePath) {
		File[] files = filePath.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()){
				fileLen += files[i].length();
			}else{
				getFileLenFromPath(files[i]);
			}
		}
	}
	
	private FileUtils(){}
}

