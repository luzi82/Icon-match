package com.luzi82.gdx;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class GrSpriteUtils {

	public static void setSpriteRect(Sprite aSprite, Rectangle aRect) {
		aSprite.setBounds(aRect.x, aRect.y, aRect.width, aRect.height);
	}

}
