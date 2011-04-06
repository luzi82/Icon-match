package com.luzi82.iconmatch;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class IconMatch {
	
	public static void initIconPackSaveFolder() {
		File file = new File(getIconPackSaveFolderFilename());
		file.mkdirs();
	}

	public static String getIconPackSaveFolderFilename() {
		StringBuffer ret = new StringBuffer();
		ret.append(getStorageDirectoryRootPath());
		ret.append(File.separator);
		ret.append("iconpack");
		ret.append(File.separator);
		return ret.toString();
	}

	public static String getIconPackSaveFilename(String id) {
		StringBuffer ret = new StringBuffer();
		ret.append(getIconPackSaveFolderFilename());
		ret.append(id);
		ret.append(".zip");
		return ret.toString();
	}

	public static String getStorageDirectoryRootPath() {
		File externalStorageDirectory = Environment
				.getExternalStorageDirectory();
		return externalStorageDirectory.getAbsolutePath() + File.separator
				+ "IconMatch";
	}
	
	public static void logd(String v){
		Log.d("IconMatch",v);
	}

}
