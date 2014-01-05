package com.luzi82.iconmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrScreen;
import com.luzi82.gdx.GrView;

public class SelectPackScreen extends GrScreen {
	public SelectPackScreen(GrGame aParent) {
		super(aParent);
	}

	@Override
	protected GrView createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	class Render extends GrView {

		public Pixmap mWhite1Pixmap;
		public Texture mWhite1Tex;

		public Button mPackImgBtn;

		public Image mLeftArrowImg;
		public Image mRightArrowImg;

		public Button mBackBtn;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			Sprite up, down;

			// mSpriteIterable = mSpriteList = new LinkedList<Sprite>();

			mWhite1Pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			mWhite1Pixmap.setColor(1f, 1f, 1f, 1f);
			mWhite1Pixmap.fill();

			mWhite1Tex = new Texture(mWhite1Pixmap);

			int minSide = Math.min(aWidth, aHeight);
			int packImgSize = Math.round(minSide / PHI);
			up = new Sprite(mWhite1Tex);
			up.setColor(0f, 1f, 0f, 1f);
			down = new Sprite(mWhite1Tex);
			down.setColor(0.5f, 1f, 0.5f, 1f);
			mPackImgBtn = new Button(new SpriteDrawable(up), new SpriteDrawable(down));
			ActorUtils.setBound(mPackImgBtn, RectUtils.createRect(aWidth / 2, aHeight / 2, packImgSize, packImgSize, 5));
			mStage.addActor(mPackImgBtn);

			int arrowImgSize = Math.round(minSide / PHI / PHI / PHI / 2);
			int arrowImgDx = Math.round(minSide * (0.5f - 1 / PHI / PHI / 4));

			mLeftArrowImg = new Image(mWhite1Tex);
			// mLeftArrowImg.setRect(RectUtils.createRect(aWidth / 2 -
			// arrowImgDx, aHeight / 2, arrowImgSize, arrowImgSize, 5));
			mLeftArrowImg.setColor(1f, 0f, 0f, 1f);
			ActorUtils.setBound(mLeftArrowImg, RectUtils.createRect(aWidth / 2 - arrowImgDx, aHeight / 2, arrowImgSize, arrowImgSize, 5));
			mStage.addActor(mLeftArrowImg);

			mRightArrowImg = new Image(mWhite1Tex);
			// mRightArrowImg.setRect(RectUtils.createRect(aWidth / 2 +
			// arrowImgDx, aHeight / 2, arrowImgSize, arrowImgSize, 5));
			mRightArrowImg.setColor(0f, 0f, 1f, 1f);
			ActorUtils.setBound(mRightArrowImg, RectUtils.createRect(aWidth / 2 + arrowImgDx, aHeight / 2, arrowImgSize, arrowImgSize, 5));
			mStage.addActor(mRightArrowImg);

			int backBtnSize = Math.round(Gdx.graphics.getPpcX() * 1f);
			int backBtnMargin = Math.round(Gdx.graphics.getPpcX() * 1f / PHI);

			up = new Sprite(mWhite1Tex);
			up.setColor(0f, 0f, 0f, 1f);
			down = new Sprite(mWhite1Tex);
			down.setColor(0.5f, 0.5f, 0.5f, 1f);
			mBackBtn = new Button(new SpriteDrawable(up), new SpriteDrawable(down));
			ActorUtils.setBound(mBackBtn, RectUtils.createRect(backBtnMargin, HEIGHT - backBtnMargin, backBtnSize, backBtnSize, 7));
			mStage.addActor(mBackBtn);
		}

	}

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;
	//
	// enum BtnIdx {
	// SELECT, LEFT, RIGHT, BACK, DL
	// }
	//
	// @Override
	// public void onClick(int aButtonId) {
	// switch (BtnIdx.values()[aButtonId]) {
	// case BACK:
	// iParent.setScreen(new HomeScreen(iParent));
	// break;
	// }
	// }

	// @Override
	// public boolean handle(Event event) {
	// // TODO Auto-generated method stub
	// return false;
	// }

}
