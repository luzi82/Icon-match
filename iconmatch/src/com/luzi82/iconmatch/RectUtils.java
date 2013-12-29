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
		case 2:
			y = aY;
			break;
		case 1:
			y = aY - aH / 2;
			break;
		case 0:
			y = aY - aH;
			break;
		}
		return new Rectangle(x, y, aW, aH);
	}

}
