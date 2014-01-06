package com.luzi82.iconmatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class DrawableUtils {

	static public SpriteDrawable createDrawable(Texture aTexture, Color aColor) {
		Sprite s = new Sprite(aTexture);
		s.setColor(aColor);
		SpriteDrawable sd = new SpriteDrawable(s);
		return sd;
	}

}
