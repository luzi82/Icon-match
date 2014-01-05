package com.luzi82.gdx;

import java.io.IOException;
import java.lang.ref.WeakReference;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.luzi82.gdx.GrPal.TextBlock;

public class GrText extends GrElement {

	final WeakReference<GrPal> iPalRef;

	public GrText(GrPal aPalRef) {
		super(true, false);
		iPalRef = new WeakReference<GrPal>(aPalRef);
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

	// enable

	boolean iEnable = true;

	public void setEnable(boolean aEnable) {
		iEnable = aEnable;
	}

	// text
	public boolean iTextDirty = true;
	public String iText = null;

	public void setText(String aText) {
		this.iTextDirty = true;
		this.iText = aText;
		markDirty();
	}

	// color

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

	// size
	public boolean iSizeDirty = true;
	public float iSize = 0f;

	public void setSize(float aSize) {
		this.iSizeDirty = true;
		this.iSize = aSize;
		markDirty();
	}

	// position
	public boolean iPositionDirty = true;
	public float iX = 0f;
	public float iY = 0f;

	public void setPosition(float aX, float aY) {
		this.iPositionDirty = true;
		this.iX = aX;
		this.iY = aY;
		markDirty();
	}

	// align
	public boolean iAlignDirty = true;
	public int iAlign = 5;

	public void setAlign(int aAlign) {
		this.iAlignDirty = true;
		this.iAlign = aAlign;
		markDirty();
	}

	// res
	Texture mTexture;

	@Override
	public void doUpdate() {
		boolean spriteExist = ((iSize > 0) && (iText != null) && (iText.length() > 0));
		if (!spriteExist) {
			mSprite = null;
			iTextDirty = false;
			iColorDirty = false;
			iSizeDirty = false;
			iPositionDirty = false;
			iAlignDirty = false;
			mTexture = null;
			return;
		}
		if (iTextDirty || iSizeDirty || iAlignDirty) {
			mSprite = null;
		}
		if (mSprite == null) {
			mSprite = new Sprite();
			GrDeepDispose.deepDispose(mTexture);
			mTexture = null;
			iTextDirty = true;
			iColorDirty = true;
			iSizeDirty = true;
			iPositionDirty = true;
			iAlignDirty = true;
		}
		if (mTexture == null) {
//			TextBlock textBlock = iPalRef.get().createText(iText, iAlign, iSize);
//			Pixmap p = null;
//			try {
//				p = new Pixmap(new Gdx2DPixmap(textBlock.mData, 0, textBlock.mData.length, Gdx2DPixmap.GDX2D_FORMAT_ALPHA));
//			} catch (IOException e) {
//				e.printStackTrace();
//				throw new Error(e);
//			}
//			mTexture = new Texture(p);
//			TextureRegion textureRegion = new TextureRegion(mTexture, 0, txtH, txtW,
//					-txtH);
		}
	}

	@Override
	public void touchDown(int x, int y, int pointer, int button, long aTime) {
		// nothing
	}

	@Override
	public void touchUp(int x, int y, int pointer, int button, long aTime) {
		// nothing
	}

	@Override
	public void touchDragged(int x, int y, int pointer, long aTime) {
		// nothing
	}

	@Override
	public void mouseMoved(int x, int y, long aTime) {
		// nothing
	}

}
