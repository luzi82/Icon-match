package com.luzi82.iconmatch;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;

public class GameEndState extends AbstractState<IconMatchGame> {

	int mCountDown;

	public GameEndState(IconMatchGame iconMatchGame) {
		super(iconMatchGame);
	}

	@Override
	public void draw(Canvas c) {
		mParent.drawGrayLayer(c);
		mParent.drawCenterText(c, "END");
	}

	@Override
	public void onKeyDown(int keyCode, KeyEvent msg) {
		// do nothing
	}

	@Override
	public void onKeyUp(int keyCode, KeyEvent msg) {
		// do nothing
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		if (mCountDown < 0) {
			mParent.getParent().finish();
		}
	}

	@Override
	public void onGamePause() {
		// do nothing
	}

	@Override
	public void onGameResume() {
		// do nothing
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
	}

	@Override
	public void tick() {
		mCountDown -= mParent.mPeriodMs;
	}

	@Override
	public void onStateEnd() {
		// do nothing
	}

	@Override
	public void onStateStart() {
		mCountDown = 2000;
	}

}
