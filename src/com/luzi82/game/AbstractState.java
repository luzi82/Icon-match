package com.luzi82.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class AbstractState<P extends StateParent> implements
		StateParent {

	protected final P mParent;

	protected AbstractState(P parent) {
		mParent = parent;
	}

	public P getParent() {
		return mParent;
	}

	public void onTouchEvent(MotionEvent event) {
	}

	public void onKeyDown(int keyCode, KeyEvent msg) {
	}

	public void onKeyUp(int keyCode, KeyEvent msg) {
	}

	public void onGameResume() {
	}

	public void onGamePause() {
	}

	public void onStateStart() {
	}

	public void onStateEnd() {
	}

	public void surfaceChanged(int format, int width, int height) {
	}

	public void draw(Canvas c) {
	}

	public void tick() {
	}

	public Resources getResources() {
		return mParent.getResources();
	}

	@Override
	public int getHeight() {
		return mParent.getHeight();
	}

	@Override
	public int getWidth() {
		return mParent.getWidth();
	}

}
