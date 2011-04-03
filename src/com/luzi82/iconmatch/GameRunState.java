package com.luzi82.iconmatch;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;

public class GameRunState extends AbstractState<IconMatchGame> {

	final static String STATE_PLAY = "play";
	final static String STATE_PENALTY = "penalty";

	final private GameLogic mGameMachine;

	public GameRunState(IconMatchGame iconMatchGame, GameLogic gameMachine) {
		super(iconMatchGame);
		mGameMachine = gameMachine;
	}

	@Override
	public void onGamePause() {
		mParent.setCurrentState(IconMatchGame.STATE_PAUSE);
	}

	@Override
	public void tick() {
		mGameMachine.tick();
		if (mGameMachine.mGameEnd) {
			mParent.setCurrentState(IconMatchGame.STATE_END);
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
			int v = (event.getX() < mParent.mScreenWidthPx / 2) ? 0 : 1;
			mGameMachine.killBlock(v);
			mParent.mShowScore = (int) mGameMachine.mScore;
		}
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
		// do nothing
	}

}
