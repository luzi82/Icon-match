package com.luzi82.game;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Util {

	public static Bitmap resize(Bitmap bitmap, int targetWidth, int targetHeight) {
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
