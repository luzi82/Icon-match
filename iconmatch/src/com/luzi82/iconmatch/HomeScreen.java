package com.luzi82.iconmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrScreen;
import com.luzi82.gdx.GrView;

public class HomeScreen extends GrScreen {

	public HomeScreen(GrGame aParent) {
		super(aParent);
	}

	@Override
	protected GrView createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	class Render extends GrView {

		public Rectangle mPlayBtnRect;

		public Texture mTexture;

		public Button mBtn;

		// public GrImage mTitleImg;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			int minSide = Math.min(aWidth, aHeight);
			int btnSize = Math.round(minSide / PHI);
			mPlayBtnRect = new Rectangle();
			mPlayBtnRect.setSize(btnSize);
			mPlayBtnRect.setCenter(aWidth / 2, aHeight / 2);

			mTexture = new Texture(Gdx.files.internal("data/libgdx.png"));
			mTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			TextureRegion region = new TextureRegion(mTexture, 0, 0, 512, 275);

			Image img = new Image(region);
			img.setBounds(mPlayBtnRect.x, mPlayBtnRect.y, mPlayBtnRect.width, mPlayBtnRect.height);
			mStage.addActor(img);

			mBtn = new Button(new BaseDrawable());
			mBtn.setBounds(0, 0, WIDTH, HEIGHT);
			mBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					onBtnClick();
				}

			});
			mStage.addActor(mBtn);

			// mTitleImg = new GrImage();
			// mTitleImg.setRect(mPlayBtnRect);
			// mTitleImg.setTextureRegion(region);
			// addElement(mTitleImg);
			//
			// GrButton screenBtn = new GrButton();
			// screenBtn.setButtonId(BtnIdx.NEXT.ordinal());
			// screenBtn.setRect(RectUtils.createRect(0, 0, WIDTH, HEIGHT, 1));
			// addElement(screenBtn);
		}

	}

	// enum BtnIdx {
	// NEXT
	// }

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;

	void onBtnClick() {
		iParent.setScreen(new SelectPackScreen(iParent));
	}

	// @Override
	// public void onClick(int aButtonId) {
	// switch (BtnIdx.values()[aButtonId]) {
	// case NEXT:
	// iParent.setScreen(new SelectPackScreen(iParent));
	// break;
	// }
	// }

}
