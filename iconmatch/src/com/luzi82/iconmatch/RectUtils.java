package com.luzi82.iconmatch;

import com.badlogic.gdx.math.Rectangle;

public class RectUtils {

	public static Rectangle createRect(float aX, float aY, float aW, float aH, int aAlign) {
		float x = 0, y = 0;
		switch (aAlign % 3) {
		case 1:
			x = aX;
			break;
		case 2:
			x = aX - aW / 2;
			break;
		case 0:
			x = aX - aW;
			break;
		}
		switch ((aAlign - 1) / 3) {
		case 0:
			y = aY;
			break;
		case 1:
			y = aY - aH / 2;
			break;
		case 2:
			y = aY - aH;
			break;
		}
		return new Rectangle(x, y, aW, aH);
	}

	public static Rectangle createRect(Point aPoint, Size aSize, int aAlign) {
		return createRect(aPoint.mX, aPoint.mY, aSize.mWidth, aSize.mHeight, aAlign);
	}

	public static Point getPoint(Rectangle aRect, int aAlign) {
		float x=0, y=0;
		switch (aAlign % 3) {
		case 1:
			x = aRect.x;
			break;
		case 2:
			x = aRect.x + aRect.width / 2;
			break;
		case 0:
			x = aRect.x + aRect.width;
			break;
		}
		switch ((aAlign - 1) / 3) {
		case 0:
			y = aRect.y;
			break;
		case 1:
			y = aRect.y + aRect.height / 2;
			break;
		case 2:
			y = aRect.y + aRect.height;
			break;
		}
		return new Point(x, y);
	}

	public static class Point {
		public float mX;
		public float mY;

		public Point(float aX, float aY) {
			mX = aX;
			mY = aY;
		}
	}

	public static class Size {
		public float mWidth;
		public float mHeight;

		public Size(float aWidth, float aHeight) {
			mWidth = aWidth;
			mHeight = aHeight;
		}
	}
	
	public static Size getSize(Rectangle aRect){
		return new Size(aRect.width,aRect.height);
	}

	public static Size getInner(Size aBox, float aWH) {
		if (aBox.mHeight * aWH > aBox.mWidth) {
			return new Size(aBox.mWidth, aBox.mWidth / aWH);
		} else {
			return new Size(aBox.mHeight * aWH, aBox.mHeight);
		}
	}
	
}
