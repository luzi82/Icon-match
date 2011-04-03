package com.luzi82.iconmatch;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;

public class GameEndState extends AbstractState {

	final IconMatchGame mIconMatchGame;

	int mCountDown;

	public GameEndState(IconMatchGame iconMatchGame) {
		mIconMatchGame = iconMatchGame;
	}

	@Override
	public void draw(Canvas c) {
		mIconMatchGame.drawGrayLayer(c);
		mIconMatchGame.drawCenterText(c, "END");
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
			mIconMatchGame.mActivity.finish();
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
		mCountDown -= mIconMatchGame.mPeriodMs;
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
