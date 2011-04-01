package com.luzi82.iconmatch;

import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.luzi82.game.AbstractGame;

public class IconMatchGame extends AbstractGame {

	final static float BAR_UNIT = 20;
	final static float LIFE_MAX_UNIT = 80;
	final static float LIFE_MIN_UNIT = 0;
	final static float PENALTY_UNIT = 20;

	final static Paint LIVE_PAINT = new Paint();
	final static Paint INACTIVE_PAINT = new Paint();
	final static Paint PENALTY_PAINT = new Paint();
	final static Paint DEAD_PAINT = new Paint();
	static {
		LIVE_PAINT.setColor(Color.WHITE);
		INACTIVE_PAINT.setColor(Color.GRAY);
		PENALTY_PAINT.setColor(Color.YELLOW);
		DEAD_PAINT.setColor(Color.RED);
	}

	final static float BASE_SPEED = 1;
	final static float SPEED_FACTOR = (float) (Math.log(2) / 60);

	// long mStartTime = System.currentTimeMillis();

	int mPeriodMs;

	int mScreenWidth;
	int mScreenHeight;

	int mBarScreenHeight;
	int mScreenBarCount;
	int mBottomScreenHeight;

	float mLifeUnit = LIFE_MAX_UNIT; // 0-100
	float mFastReduceUnit = LIFE_MAX_UNIT;
	float mPenaltyUnit;

	boolean mGameEnd = false;
	long mGameEndStartMs;

	boolean mPenaltyState = false;;

	int mBlockDone = 0;

	LinkedList<Integer> answer = new LinkedList<Integer>();

	@Override
	public void draw(Canvas c) {
		Paint firstpaint = mGameEnd ? DEAD_PAINT
				: mPenaltyState ? PENALTY_PAINT : LIVE_PAINT;
		Paint otherpaint = mGameEnd ? DEAD_PAINT
				: mPenaltyState ? PENALTY_PAINT : INACTIVE_PAINT;
		c.drawColor(Color.BLACK);
		float lineY = mScreenHeight - (mLifeUnit * mBarScreenHeight / BAR_UNIT)
				- mBottomScreenHeight;
		Paint paint = firstpaint;
		for (int v : answer) {
			float topY = lineY - mBarScreenHeight + 1;
			if (v == 0) {
				c.drawRect(0, topY, mScreenWidth / 2, lineY, paint);
			} else {
				c.drawRect(mScreenWidth / 2, topY, mScreenWidth, lineY, paint);
			}
			lineY -= mBarScreenHeight;
			paint = otherpaint;
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
			// speed cal
			// long timeDiff = System.currentTimeMillis() - mStartTime;
			// float speed = BAR_UNIT * (mIconMatchGameActivity.getPeriodMs())
			// * (float) Math.pow(2.0d, ((double) (timeDiff) / 60000))
			// / 1000.0f;
			float speed = BAR_UNIT * mPeriodMs
					* (BASE_SPEED + SPEED_FACTOR * mBlockDone) / 1000;

			// bar re-gen
			while (answer.size() < mScreenBarCount) {
				answer.addLast((Math.random() < 0.5) ? 0 : 1);
			}

			// life reduce
			mLifeUnit -= speed;
			mFastReduceUnit -= speed;
			if (mLifeUnit - mFastReduceUnit < 1) {
				mLifeUnit = mFastReduceUnit;
			} else {
				mLifeUnit -= 1;
				mLifeUnit = (mLifeUnit * 3 + mFastReduceUnit) / 4;
			}

			// penalty state
			if (mPenaltyState && (mLifeUnit < mPenaltyUnit)) {
				mPenaltyState = false;
			}

			// game over
			if (mLifeUnit <= 0) {
				mLifeUnit = 0;
				mGameEnd = true;
				mGameEndStartMs = now;
			}
		} else {
			if (now - mGameEndStartMs >= 2000) {
				// mIconMatchGameActivity.setResult(Activity.RESULT_OK);
				mActivity.finish();
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
			if (!mPenaltyState) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					int v = (event.getX() < mScreenWidth / 2) ? 0 : 1;
					int first = answer.getFirst();
					if (first == v) {
						mLifeUnit += BAR_UNIT;
						mFastReduceUnit += BAR_UNIT;
						if (mFastReduceUnit > LIFE_MAX_UNIT) {
							mFastReduceUnit = LIFE_MAX_UNIT;
						}
						++mBlockDone;
					} else {
						mPenaltyState = true;
						mPenaltyUnit = mLifeUnit - PENALTY_UNIT;
						mLifeUnit += BAR_UNIT;
						mFastReduceUnit += BAR_UNIT;
					}
					answer.removeFirst();
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
		mBarScreenHeight = width * 2 / 7;
		mScreenBarCount = (mScreenHeight / mBarScreenHeight) + 1;
		mBottomScreenHeight = width / 8;
	}

	IconMatchGameActivity mActivity;

	public IconMatchGame(IconMatchGameActivity iconMatchGameActivity) {
		mActivity = iconMatchGameActivity;
		mPeriodMs = (int) mActivity.getPeriodMs();
	}

}
