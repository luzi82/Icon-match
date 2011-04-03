package com.luzi82.iconmatch;

import android.graphics.Canvas;

import com.luzi82.game.AbstractState;

public class GameStartState extends AbstractState<IconMatchGame> {

	int mCountDown;

	public GameStartState(IconMatchGame iconMatchGame) {
		super(iconMatchGame);
	}

	@Override
	public void draw(Canvas c) {
		mParent.drawGrayLayer(c);
		mParent.drawCenterText(c, "START");
	}

	@Override
	public void tick() {
		mCountDown -= mParent.mPeriodMs;
		if (mCountDown < 0) {
			mParent.setCurrentState(IconMatchGame.STATE_RUN);
		}
	}

	@Override
	public void onStateStart() {
		mCountDown = 2000;
	}

}
