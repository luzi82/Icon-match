package com.luzi82.iconmatch;

import java.io.File;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class IconPack {

	public Bitmap[] mCenterBitmap;

	public Bitmap[] mSelectionBitmap;

	public int mSelectionSize;

	public static IconPack load(String path, int width, int height) {
		LinkedList<Bitmap> aBitmapList = new LinkedList<Bitmap>();
		LinkedList<Bitmap> bBitmapList = new LinkedList<Bitmap>();

		File bitmapFolder = new File(path);
		String[] folderContent = bitmapFolder.list();
		for (String filename : folderContent) {
			if (!filename.endsWith(".a.png"))
				continue;
			String prefix = filename.substring(0, filename.length() - 6);
			String aFilename = path + File.separator + prefix + ".a.png";
			String bFilename = path + File.separator + prefix + ".b.png";
			if (!new File(aFilename).exists())
				continue;
			if (!new File(bFilename).exists())
				continue;
			Bitmap aBitmap = BitmapFactory.decodeFile(aFilename);
			aBitmap = resize(aBitmap, width, height);
			aBitmapList.add(aBitmap);
			Bitmap bBitmap = BitmapFactory.decodeFile(bFilename);
			bBitmap = resize(bBitmap, width, height);
			bBitmapList.add(bBitmap);
		}

		IconPack ret = new IconPack();

		ret.mCenterBitmap = aBitmapList.toArray(new Bitmap[0]);
		ret.mSelectionBitmap = bBitmapList.toArray(new Bitmap[0]);
		ret.mSelectionSize = aBitmapList.size();

		return ret;
	}

	private static Bitmap resize(Bitmap bitmap, int targetWidth,
			int targetHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float scaleWidth = ((float) targetWidth) / width;
		float scaleHeight = ((float) targetHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);

		return resizedBitmap;
	}

}
