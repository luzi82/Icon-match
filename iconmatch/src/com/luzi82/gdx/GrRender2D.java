package com.luzi82.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GrRender2D extends GrRender {

	public OrthographicCamera mCamera;
	public SpriteBatch mBatch;
	public float clearR = 0.5f;
	public float clearG = 0.5f;
	public float clearB = 0.5f;
	public float clearA = 0.5f;

	public Iterable<Sprite> mSpriteIterable;

	public GrRender2D(int aWidth, int aHeight) {
		super(aWidth, aHeight);

		mCamera = new OrthographicCamera(WIDTH, HEIGHT);
		mCamera.translate(WIDTH / 2f, HEIGHT / 2f);
		mCamera.update();
		mBatch = new SpriteBatch();
	}

	@Override
	public void render(float aDelta) {
		Gdx.gl.glClearColor(clearR, clearG, clearB, clearA);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		for (Sprite s : mSpriteIterable) {
			s.draw(mBatch);
		}
		mBatch.end();
	}

}
