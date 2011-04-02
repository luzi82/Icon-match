package com.luzi82.iconmatch;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;

public class GamePauseState extends AbstractState {

	IconMatchGame mIconMatchGame;

	public GamePauseState(IconMatchGame iconMatchGame) {
		mIconMatchGame = iconMatchGame;
	}

	@Override
	public void draw(Canvas c) {
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
		mIconMatchGame.setCurrentState(IconMatchGame.STATE_RUN);
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
		// do nothing
	}

	@Override
	public void tick() {
		// do nothing
	}

	@Override
	public void onStateEnd() {
		// do nothing
	}

	@Override
	public void onStateStart() {
		// do nothing
	}

}
