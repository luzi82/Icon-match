package com.luzi82.iconmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrView;
import com.luzi82.gdx.GrScreen;

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

		public OrthographicCamera mCamera;
		public SpriteBatch mBatch;
		public Texture mTexture;
		public Sprite mSprite;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			int minSide = Math.min(aWidth, aHeight);
			int btnSize = Math.round(minSide / PHI);
			mPlayBtnRect = new Rectangle();
			mPlayBtnRect.setSize(btnSize);
			mPlayBtnRect.setCenter(aWidth / 2, aHeight / 2);

			mCamera = new OrthographicCamera(aWidth, aHeight);
			mCamera.translate(aWidth / 2f, aHeight / 2f);
			mCamera.update();
			mBatch = new SpriteBatch();

			mTexture = new Texture(Gdx.files.internal("data/libgdx.png"));
			mTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			TextureRegion region = new TextureRegion(mTexture, 0, 0, 512, 275);

			mSprite = new Sprite(region);
			mSprite.setBounds(mPlayBtnRect.x, mPlayBtnRect.y, mPlayBtnRect.width, mPlayBtnRect.height);
		}

		@Override
		public void render(float aDelta) {
			Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			mBatch.setProjectionMatrix(mCamera.combined);
			mBatch.begin();
			mSprite.draw(mBatch);
			mBatch.end();
		}

	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button, long aTime) {
		Render render = (Render) mRender;
		if (render == null)
			return false;
		if (button != Input.Buttons.LEFT)
			return false;
		iParent.setScreen(new SelectPackScreen(iParent));
		return true;
	}

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;
}
