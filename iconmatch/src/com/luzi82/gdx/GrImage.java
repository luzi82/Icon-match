package com.luzi82.gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GrImage extends GrElement {

	public GrImage() {
		super(true, false);
	}

	Sprite mSprite = null;

	@Override
	public void draw(SpriteBatch aSpriteBatch) {
		if (!iEnable)
			return;
		if (mSprite == null)
			return;
		mSprite.draw(aSpriteBatch);
	}

	boolean iEnable = true;

	public void setEnable(boolean aEnable) {
		iEnable = aEnable;
	}

	public boolean iColorDirty = true;
	public float iR = 1f;
	public float iG = 1f;
	public float iB = 1f;
	public float iA = 1f;

	public void setColor(float r, float g, float b, float a) {
		this.iColorDirty = true;
		this.iR = r;
		this.iG = g;
		this.iB = b;
		this.iA = a;
		markDirty();
	}

	public boolean iTextureDirty = true;
	Texture iTexture;
	TextureRegion iTextureRegion;

	public void setTexture(Texture aTexture) {
		this.iTextureDirty = true;
		this.iTexture = aTexture;
		this.iTextureRegion = null;
		markDirty();
	}

	public void setTextureRegion(TextureRegion aTextureRegion) {
		this.iTextureDirty = true;
		this.iTexture = null;
		this.iTextureRegion = aTextureRegion;
		markDirty();
	}

	public boolean iRectangleDirty = true;
	Rectangle iRectangle;

	public void setRect(Rectangle aRect) {
		this.iRectangleDirty = true;
		this.iRectangle = aRect;
		markDirty();
	}

	@Override
	public void doUpdate() {
		boolean spriteExist = (((iTexture != null) || (iTextureRegion != null)) && (iRectangle != null));
		if (!spriteExist) {
			mSprite = null;
			iTextureDirty = false;
			iRectangleDirty = false;
			iColorDirty = false;
			return;
		}
		if (mSprite == null) {
			mSprite = new Sprite();
			iTextureDirty = true;
			iRectangleDirty = true;
			iColorDirty = true;
		}
		if (iTextureDirty) {
			if (iTexture != null) {
				mSprite.setTexture(iTexture);
			}
			if (iTextureRegion != null) {
				mSprite.setRegion(iTextureRegion);
			}
		}
		if (iRectangleDirty) {
			mSprite.setBounds(iRectangle.x, iRectangle.y, iRectangle.width, iRectangle.height);
		}
		if (iColorDirty) {
			mSprite.setColor(iR, iG, iB, iA);
			iColorDirty = false;
		}
	}

	@Override
	public void touchDown(int x, int y, int pointer, int button, long aTime) {
		// none
	}

	@Override
	public void touchUp(int x, int y, int pointer, int button, long aTime) {
		// none
	}

	@Override
	public void touchDragged(int x, int y, int pointer, long aTime) {
		// none
	}

	@Override
	public void mouseMoved(int x, int y, long aTime) {
		// none
	}
}
