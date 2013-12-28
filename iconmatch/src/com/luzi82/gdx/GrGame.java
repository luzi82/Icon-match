package com.luzi82.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public abstract class GrGame extends Game implements InputProcessor {

	GrScreen<?> mCurrentScreen;
	TouchTrace mTouchTrace;

	@Override
	public void create() {
		mTouchTrace = new TouchTrace();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void setScreen(Screen aScreen) {
		super.setScreen(aScreen);
		if (aScreen instanceof GrScreen) {
			mCurrentScreen = (GrScreen<?>) aScreen;
		} else {
			mCurrentScreen = null;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (mCurrentScreen == null)
			return false;
		return mCurrentScreen.keyDown(keycode, Gdx.input.getCurrentEventTime());
	}

	@Override
	public boolean keyUp(int keycode) {
		if (mCurrentScreen == null)
			return false;
		return mCurrentScreen.keyUp(keycode, Gdx.input.getCurrentEventTime());
	}

	@Override
	public boolean keyTyped(char character) {
		if (mCurrentScreen == null)
			return false;
		return mCurrentScreen.keyTyped(character, Gdx.input.getCurrentEventTime());
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (mCurrentScreen == null)
			return false;
		if (mTouchTrace != null)
			mTouchTrace.touchDown(x, y, pointer, button);
		return mCurrentScreen.touchDown(x, y, pointer, button, Gdx.input.getCurrentEventTime());
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (mCurrentScreen == null)
			return false;
		if (mTouchTrace != null)
			mTouchTrace.touchUp(x, y, pointer, button);
		return mCurrentScreen.touchUp(x, y, pointer, button, Gdx.input.getCurrentEventTime());
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (mCurrentScreen == null)
			return false;
		if (mTouchTrace != null)
			mTouchTrace.touchDragged(x, y, pointer);
		return mCurrentScreen.touchDragged(x, y, pointer, Gdx.input.getCurrentEventTime());
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		if (mCurrentScreen == null)
			return false;
		if (mTouchTrace != null)
			mTouchTrace.touchMoved(x, y);
		return mCurrentScreen.touchMoved(x, y, Gdx.input.getCurrentEventTime());
	}

	@Override
	public boolean scrolled(int amount) {
		if (mCurrentScreen == null)
			return false;
		return mCurrentScreen.scrolled(amount, Gdx.input.getCurrentEventTime());
	}

	@Override
	public void render() {
		super.render();
		if (mTouchTrace != null)
			mTouchTrace.render();
	}

	@Override
	public void dispose() {
		mTouchTrace.dispose();
		super.dispose();
	}
}
