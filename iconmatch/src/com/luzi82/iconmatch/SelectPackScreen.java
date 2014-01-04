package com.luzi82.iconmatch;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.luzi82.gdx.GrButton;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrImage;
import com.luzi82.gdx.GrView;
import com.luzi82.gdx.GrView2D;
import com.luzi82.gdx.GrScreen;

public class SelectPackScreen extends GrScreen {
	public SelectPackScreen(GrGame aParent) {
		super(aParent);
	}

	@Override
	protected GrView createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	class Render extends GrView2D {

		public Pixmap mWhite1Pixmap;
		public Texture mWhite1Tex;

		// public Rectangle mPackImgRect;
		// public Sprite mPackImgSprite;

		public GrButton mPackImgBtn;

		// public Rectangle mLeftArrowRect;
		// public Sprite mLeftArrowSprite;
		public GrImage mLeftArrowImg;
		public GrImage mRightArrowImg;

		// public Rectangle mRightArrowRect;
		// public Sprite mRightArrowSprite;

		// public Rectangle mBackBtnRect;
		// public Sprite mBackBtnSprite;
		public GrButton mBackBtn;

		// public LinkedList<Sprite> mSpriteList;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			Rectangle rect;
			GrImage img;

			// mSpriteIterable = mSpriteList = new LinkedList<Sprite>();

			mWhite1Pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			mWhite1Pixmap.setColor(1f, 1f, 1f, 1f);
			mWhite1Pixmap.fill();

			mWhite1Tex = new Texture(mWhite1Pixmap);

			int minSide = Math.min(aWidth, aHeight);
			int packImgSize = Math.round(minSide / PHI);
			rect = RectUtils.createRect(aWidth / 2, aHeight / 2, packImgSize, packImgSize, 5);
			img = new GrImage();
			img.setRect(rect);
			img.setColor(0f, 1f, 0f, 1f);
			img.setTexture(mWhite1Tex);
			mPackImgBtn = new GrButton();
			mPackImgBtn.setButtonId(BtnIdx.SELECT.ordinal());
			mPackImgBtn.setRect(rect);
			mPackImgBtn.setOutElement(img);
			mPackImgBtn.setOverElement(img);
			img = new GrImage();
			img.setRect(rect);
			img.setColor(0.5f, 1f, 0.5f, 1f);
			img.setTexture(mWhite1Tex);
			mPackImgBtn.setDownElement(img);
			addElement(mPackImgBtn);

			int arrowImgSize = Math.round(minSide / PHI / PHI / PHI / 2);
			int arrowImgDx = Math.round(minSide * (0.5f - 1 / PHI / PHI / 4));

			mLeftArrowImg = new GrImage();
			mLeftArrowImg.setRect(RectUtils.createRect(aWidth / 2 - arrowImgDx, aHeight / 2, arrowImgSize, arrowImgSize, 5));
			mLeftArrowImg.setColor(1f, 0f, 0f, 1f);
			mLeftArrowImg.setTexture(mWhite1Tex);
			addElement(mLeftArrowImg);

			mRightArrowImg = new GrImage();
			mRightArrowImg.setRect(RectUtils.createRect(aWidth / 2 + arrowImgDx, aHeight / 2, arrowImgSize, arrowImgSize, 5));
			mRightArrowImg.setColor(0f, 0f, 1f, 1f);
			mRightArrowImg.setTexture(mWhite1Tex);
			addElement(mRightArrowImg);

			int backBtnSize = Math.round(Gdx.graphics.getPpcX() * 1f);
			int backBtnMargin = Math.round(Gdx.graphics.getPpcX() * 1f / PHI);

			rect = RectUtils.createRect(backBtnMargin, backBtnMargin, backBtnSize, backBtnSize, 7);
			img = new GrImage();
			img.setRect(rect);
			img.setColor(0f, 0f, 0f, 1f);
			img.setTexture(mWhite1Tex);
			mBackBtn = new GrButton();
			mBackBtn.setButtonId(BtnIdx.BACK.ordinal());
			mBackBtn.setRect(rect);
			mBackBtn.setOutElement(img);
			mBackBtn.setOverElement(img);
			img = new GrImage();
			img.setRect(rect);
			img.setColor(0.5f, 0.5f, 0.5f, 1f);
			img.setTexture(mWhite1Tex);
			mBackBtn.setDownElement(img);
			addElement(mBackBtn);
		}

	}

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;

	enum BtnIdx {
		SELECT, LEFT, RIGHT, BACK, DL
	}

}
