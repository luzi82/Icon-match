package com.luzi82.iconmatch;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class IconPack {

	public Bitmap[] mCenterBitmap;

	public Bitmap[] mSelectionBitmap;

	public int mSelectionSize;

	public int mWidth;

	public int mHeight;

	public static IconPack load(String filename, int width, int height)
			throws ZipException, IOException {
		IconPackEntry ipe = IconPackEntry.get(filename);
		return load(ipe, width, height);
	}

	public static IconPack load(IconPackEntry entry, int width, int height)
			throws ZipException, IOException {
		LinkedList<Bitmap> aBitmapList = new LinkedList<Bitmap>();
		LinkedList<Bitmap> bBitmapList = new LinkedList<Bitmap>();

		if (entry.type == IconPackEntry.Type.ZIP) {
			ZipFile zipFile = new ZipFile(new File(entry.filename));
			Enumeration<? extends ZipEntry> zee = zipFile.entries();
			while (zee.hasMoreElements()) {
				ZipEntry zea = zee.nextElement();
				String filename = zea.getName();
				if (!zea.getName().endsWith(".a.png"))
					continue;
				String prefix = filename.substring(0, filename.length() - 6);
				String bFilename = prefix + ".b.png";
				ZipEntry zeb = zipFile.getEntry(bFilename);
				if (zeb == null) {
					continue;
				}
				Bitmap aBitmap = BitmapFactory.decodeStream(zipFile
						.getInputStream(zea));
				aBitmap = resize(aBitmap, width, height);
				aBitmapList.add(aBitmap);
				Bitmap bBitmap = BitmapFactory.decodeStream(zipFile
						.getInputStream(zeb));
				bBitmap = resize(bBitmap, width, height);
				bBitmapList.add(bBitmap);
			}
		}

		// File file = new File(path);
		// if (file.isDirectory()) {
		// String[] folderContent = file.list();
		// for (String filename : folderContent) {
		// if (!filename.endsWith(".a.png"))
		// continue;
		// String prefix = filename.substring(0, filename.length() - 6);
		// String aFilename = path + File.separator + prefix + ".a.png";
		// String bFilename = path + File.separator + prefix + ".b.png";
		// if (!new File(aFilename).exists())
		// continue;
		// if (!new File(bFilename).exists())
		// continue;
		// Bitmap aBitmap = BitmapFactory.decodeFile(aFilename);
		// aBitmap = resize(aBitmap, width, height);
		// aBitmapList.add(aBitmap);
		// Bitmap bBitmap = BitmapFactory.decodeFile(bFilename);
		// bBitmap = resize(bBitmap, width, height);
		// bBitmapList.add(bBitmap);
		// }
		// } else {
		// }

		IconPack ret = new IconPack();

		ret.mWidth = width;
		ret.mHeight = height;
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
