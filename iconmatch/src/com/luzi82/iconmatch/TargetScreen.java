package com.luzi82.iconmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrScreen;
import com.luzi82.gdx.GrView;

public class TargetScreen extends GrScreen {

	public TargetScreen(GrGame aParent) {
		super(aParent);
	}

	@Override
	protected GrView createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	class Render extends GrView {

		public Pixmap mWhite1Pixmap;
		public Texture mWhite1Tex;

		public BitmapFont bf;

		public Image x;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			FreeTypeFontGenerator g = new FreeTypeFontGenerator(Gdx.files.internal("data/Roboto-Regular.ttf"));
			bf = g.generateFont(30, "0123456789", false);
			g.dispose();

			Label.LabelStyle ls = new LabelStyle();
			ls.font = bf;
			ls.fontColor = Color.WHITE;

			Label label = new Label("123", ls);
			// label.setOrigin(WIDTH / 2, HEIGHT / 2);
			// label.setPosition(WIDTH / 2, HEIGHT / 2);
			label.setAlignment(Align.center);
			ActorUtils.setBound(label, RectUtils.createRect(WIDTH / 2, HEIGHT / 2, 200, 200, 5));

			mWhite1Pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			mWhite1Pixmap.setColor(1f, 1f, 1f, 1f);
			mWhite1Pixmap.fill();
			mWhite1Tex = new Texture(mWhite1Pixmap);

			x = new Image(mWhite1Tex);
			x.setColor(Color.BLACK);
			ActorUtils.setBound(x, RectUtils.createRect(WIDTH / 2, HEIGHT / 2, 200, 200, 5));
			mStage.addActor(x);
			mStage.addActor(label);
		}

		// public Rectangle mPlayBtnRect;
		//
		// public Texture mTexture;
		//
		// public GrImage mTitleImg;
		//
		// public Render(int aWidth, int aHeight) {
		// super(aWidth, aHeight);
		//
		// int minSide = Math.min(aWidth, aHeight);
		// int btnSize = Math.round(minSide / PHI);
		// mPlayBtnRect = new Rectangle();
		// mPlayBtnRect.setSize(btnSize);
		// mPlayBtnRect.setCenter(aWidth / 2, aHeight / 2);
		//
		// mTexture = new Texture(Gdx.files.internal("data/libgdx.png"));
		// mTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//
		// TextureRegion region = new TextureRegion(mTexture, 0, 0, 512, 275);
		//
		// mTitleImg = new GrImage();
		// mTitleImg.setRect(mPlayBtnRect);
		// mTitleImg.setTextureRegion(region);
		// addElement(mTitleImg);
		//
		// }

	}

	enum BtnIdx {
		INCREASE, DECREASE, OK
	}

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;

	// @Override
	// public void onClick(int aButtonId) {
	// switch (BtnIdx.values()[aButtonId]) {
	// case INCREASE:
	// break;
	// }
	// }

	// @Override
	// public boolean handle(Event event) {
	// // TODO Auto-generated method stub
	// return false;
	// }
}
