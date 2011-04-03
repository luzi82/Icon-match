package com.luzi82.iconmatch;

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
	public void onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int v = (event.getX() < (mParent.mScreenWidthPx / 2)) ? 0 : 1;
			int hit = mGameMachine.killBlock(v);
			if(hit!=0){
				IconMatchGame.ClickDraw cd=new IconMatchGame.ClickDraw();
				cd.mHit=hit==1;
				cd.mTime=System.currentTimeMillis();
				cd.mX=event.getX();
				cd.mY=event.getY();
				mParent.mClickDrawList.add(cd);
			}
		}
	}

}
