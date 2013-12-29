package com.luzi82.iconmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.luzi82.gdx.GrView;
import com.luzi82.gdx.GrScreen;

public class GameScreen extends GrScreen {
	private OrthographicCamera camera;
	private SpriteBatch mBatch;
	private Texture texture;
	private Sprite sprite;

	protected GameScreen(IconMatchGame aParent) {
		super(aParent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onScreenShow() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, h / w);
		mBatch = new SpriteBatch();

		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
	}

	@Override
	public void onScreenHide() {
		if (mBatch != null) {
			mBatch.dispose();
			mBatch = null;
		}
		if (texture != null) {
			texture.dispose();
			texture = null;
		}
	}

	@Override
	public void onScreenDispose() {
		hide();
	}

	@Override
	public void onScreenRender(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		mBatch.setProjectionMatrix(camera.combined);
		mBatch.begin();
		sprite.draw(mBatch);
		mBatch.end();
	}

	@Override
	public void onScreenResize() {
	}

	@Override
	public void onScreenPause() {
	}

	@Override
	public void onScreenResume() {
	}

	@Override
	protected GrView createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	class Render extends GrView {

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);
		}

		@Override
		public void render(float aDelta) {
			// TODO Auto-generated method stub

		}

	}
}
