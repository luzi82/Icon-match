package com.luzi82.iconmatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;
import com.luzi82.game.Util;

public class GameRunState extends AbstractState<IconMatchGame> {

	final static String STATE_PLAY = "play";
	final static String STATE_PENALTY = "penalty";

	final private GameLogic mGameMachine;

	private Bitmap mPauseButton;
	private int mPauseSize;
	private Paint mDummyPaint = new Paint();
	private int mWidth;
	private int mHeight;

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
			float x = event.getX();
			float y = event.getY();
			if ((x > mWidth - mPauseSize) && (y < mPauseSize)) {
				mParent.setCurrentState(IconMatchGame.STATE_PAUSE);
			} else {
				int v = (x < (mParent.mScreenWidthPx / 2)) ? 0 : 1;
				int hit = mGameMachine.killBlock(v);
				if (hit != 0) {
					IconMatchGame.ClickDraw cd = new IconMatchGame.ClickDraw();
					cd.mHit = hit == 1;
					cd.mTime = System.currentTimeMillis();
					cd.mX = x;
					cd.mY = y;
					mParent.mClickDrawList.add(cd);
				}
			}
		}
	}

	@Override
	public void onGameResume() {
		super.onGameResume();
		loadBitmap();
	}

	@Override
	public void onStateStart() {
		super.onStateStart();
		loadBitmap();
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
		super.surfaceChanged(format, width, height);
		loadBitmap();
	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);
		c.drawBitmap(mPauseButton, mWidth - mPauseSize, 0, mDummyPaint);
	}

	private void loadBitmap() {
		mWidth = getWidth();
		mHeight = getHeight();
		int size = Math.min(mWidth, mHeight);
		size /= 6;
		if (mPauseSize != size) {
			Bitmap tmpButton = BitmapFactory.decodeResource(getResources(),
					R.drawable.pause_button);
			mPauseButton = Util.resize(tmpButton, size, size);
			mPauseSize = size;
		}
	}

}
