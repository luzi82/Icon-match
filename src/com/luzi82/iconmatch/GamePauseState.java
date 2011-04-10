package com.luzi82.iconmatch;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;

public class GamePauseState extends AbstractState<IconMatchGame> {

	public GamePauseState(IconMatchGame iconMatchGame) {
		super(iconMatchGame);
	}

	@Override
	public void draw(Canvas c) {
		mParent.drawGrayLayer(c);
		mParent.drawCenterText(c, "PAUSE");
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mParent.setCurrentState(IconMatchGame.STATE_RUN);
		}
	}

}
