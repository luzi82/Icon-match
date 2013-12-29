package com.luzi82.iconmatch;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrRender;
import com.luzi82.gdx.GrRender2D;
import com.luzi82.gdx.GrScreen;

public class SelectPackScreen extends GrScreen {
	public SelectPackScreen(GrGame aParent) {
		super(aParent);
	}

	@Override
	protected GrRender createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	class Render extends GrRender2D {

		public Pixmap mWhite1Pixmap;
		public Texture mWhite1Tex;

		public Rectangle mPackImgRect;
		public Sprite mPackImgSprite;

		public Rectangle mLeftArrowRect;
		public Sprite mLeftArrowSprite;

		public Rectangle mRightArrowRect;
		public Sprite mRightArrowSprite;

		public Rectangle mBackBtnRect;
		public Sprite mBackBtnSprite;

		public LinkedList<Sprite> mSpriteList;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			mSpriteIterable = mSpriteList = new LinkedList<Sprite>();

			mWhite1Pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			mWhite1Pixmap.setColor(1f, 1f, 1f, 1f);
			mWhite1Pixmap.fill();

			mWhite1Tex = new Texture(mWhite1Pixmap);

			
			int minSide = Math.min(aWidth, aHeight);
			int packImgSize = Math.round(minSide / PHI);
			mPackImgRect = new Rectangle();
			mPackImgRect.setSize(packImgSize);
			mPackImgRect.setCenter(aWidth / 2, aHeight / 2);
			mPackImgSprite = new Sprite(mWhite1Tex);
			SpriteUtils.setSpriteRect(mPackImgSprite, mPackImgRect);
			mPackImgSprite.setColor(0f, 1f, 0f, 1f);
			mSpriteList.add(mPackImgSprite);

			
			int arrowImgSize = Math.round(minSide / PHI / PHI / PHI / 2);
			int arrowImgDx = Math.round(minSide * (0.5f - 1 / PHI / PHI / 4));

			mLeftArrowRect = new Rectangle();
			mLeftArrowRect.setSize(arrowImgSize);
			mLeftArrowRect.setCenter(aWidth / 2 - arrowImgDx, aHeight / 2);
			mLeftArrowSprite = new Sprite(mWhite1Tex);
			SpriteUtils.setSpriteRect(mLeftArrowSprite, mLeftArrowRect);
			mLeftArrowSprite.setColor(1f, 0f, 0f, 1f);
			mSpriteList.add(mLeftArrowSprite);

			mRightArrowRect = new Rectangle();
			mRightArrowRect.setSize(arrowImgSize);
			mRightArrowRect.setCenter(aWidth / 2 + arrowImgDx, aHeight / 2);
			mRightArrowSprite = new Sprite(mWhite1Tex);
			SpriteUtils.setSpriteRect(mRightArrowSprite, mRightArrowRect);
			mRightArrowSprite.setColor(0f, 0f, 1f, 1f);
			mSpriteList.add(mRightArrowSprite);

			int backBtnSize = Math.round(Gdx.graphics.getPpcX() * 0.7f);
			int backBtnMargin = Math.round(backBtnSize / PHI);

			mBackBtnRect = RectUtils.createRect(backBtnMargin, backBtnMargin, backBtnSize, backBtnSize, 7);
			mBackBtnSprite = new Sprite(mWhite1Tex);
			SpriteUtils.setSpriteRect(mBackBtnSprite, mBackBtnRect);
			mBackBtnSprite.setColor(0f, 0f, 0f, 1f);
			mSpriteList.add(mBackBtnSprite);
		}

	}
	
	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;
}
