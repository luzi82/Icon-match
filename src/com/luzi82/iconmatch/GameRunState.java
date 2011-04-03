package com.luzi82.iconmatch;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;

public class GameRunState extends AbstractState {

	final static String STATE_PLAY = "play";
	final static String STATE_PENALTY = "penalty";

	final private IconMatchGame mIconMatchGame;
	final private GameLogic mGameMachine;

	public GameRunState(IconMatchGame iconMatchGame, GameLogic gameMachine) {
		mIconMatchGame = iconMatchGame;
		mGameMachine = gameMachine;
	}

	public IconMatchGame getIconMatchGame() {
		return mIconMatchGame;
	}

	@Override
	public void onGamePause() {
		mIconMatchGame.setCurrentState(IconMatchGame.STATE_PAUSE);
	}

	@Override
	public void tick() {
		mGameMachine.tick();
		if (mGameMachine.mGameEnd) {
			mIconMatchGame.setCurrentState(IconMatchGame.STATE_END);
		}
	}

	@Override
	public void draw(Canvas c) {
		// do nothing
	}

	@Override
	public void onGameResume() {
		// do nothing
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
	public void onStateEnd() {
		// do nothing
	}

	@Override
	public void onStateStart() {
		// do nothing
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int v = (event.getX() < mIconMatchGame.mScreenWidthPx / 2) ? 0 : 1;
			mGameMachine.killBlock(v);
			mIconMatchGame.mShowScore = (int) mGameMachine.mScore;
		}
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
		// do nothing
	}

}
