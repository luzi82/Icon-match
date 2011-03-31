package com.luzi82.iconmatch;

import java.util.LinkedList;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.luzi82.game.AbstractGame;

public class IconMatchGame extends AbstractGame {

	final static float BAR_UNIT = 20;
	final static float LIFE_MAX_UNIT = 100;
	final static float LIFE_MIN_UNIT = 0;

	final static Paint LIVE_PAINT = new Paint();
	final static Paint DEAD_PAINT = new Paint();
	static {
		LIVE_PAINT.setColor(Color.WHITE);
		DEAD_PAINT.setColor(Color.RED);
	}

	long mStartTime = System.currentTimeMillis();

	int mScreenWidth;
	int mScreenHeight;

	int mBarScreenHeight;
	int mScreenBarCount;

	float mLifeUnit = LIFE_MAX_UNIT; // 0-100

	boolean mGameEnd = false;
	long mGameEndStartMs;

	LinkedList<Integer> answer = new LinkedList<Integer>();

	@Override
	public void draw(Canvas c) {
		Paint paint = mGameEnd ? DEAD_PAINT : LIVE_PAINT;
		c.drawColor(Color.BLACK);
		float lineY = mScreenHeight - (mLifeUnit * mBarScreenHeight / BAR_UNIT);
		for (int v : answer) {
			float topY = lineY - mBarScreenHeight + 1;
			if (v == 0) {
				c.drawRect(0, topY, mScreenWidth / 2, lineY, paint);
			} else {
				c.drawRect(mScreenWidth / 2, topY, mScreenWidth, lineY, paint);
			}
			lineY -= mBarScreenHeight;
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick() {
		long now = System.currentTimeMillis();
		if (!mGameEnd) {
			long timeDiff = System.currentTimeMillis() - mStartTime;
			float speed = BAR_UNIT * (mIconMatchGameActivity.getPeriodMs())
					* (float) Math.pow(2.0d, ((double) (timeDiff) / 60000))
					/ 1000.0f;
			mLifeUnit -= speed;
			while (answer.size() < mScreenBarCount) {
				answer.addLast((Math.random() < 0.5) ? 0 : 1);
			}
			if (mLifeUnit <= 0) {
				mLifeUnit = 0;
				mGameEnd = true;
				mGameEndStartMs = now;
			}
		} else {
			if (now - mGameEndStartMs >= 2000) {
				// mIconMatchGameActivity.setResult(Activity.RESULT_OK);
				mIconMatchGameActivity.finish();
			}
		}
	}

	@Override
	public void onKeyDown(int keyCode, KeyEvent msg) {
	}

	@Override
	public void onKeyUp(int keyCode, KeyEvent msg) {
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		if (!mGameEnd) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int v = (event.getX() < mScreenWidth / 2) ? 0 : 1;
				int first = answer.getFirst();
				if (first == v) {
					mLifeUnit += BAR_UNIT;
					if (mLifeUnit > LIFE_MAX_UNIT) {
						mLifeUnit = LIFE_MAX_UNIT;
					}
					answer.removeFirst();
				} else {
					mGameEnd = true;
					mGameEndStartMs = System.currentTimeMillis();
				}
			}
		}
	}

	@Override
	public void resume() {
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
		mScreenWidth = width;
		mScreenHeight = height;
		mBarScreenHeight = width / 4;
		mScreenBarCount = (mScreenHeight / mBarScreenHeight) + 1;
	}

	IconMatchGameActivity mIconMatchGameActivity;

	public IconMatchGame(IconMatchGameActivity iconMatchGameActivity) {
		mIconMatchGameActivity = iconMatchGameActivity;
	}

}
