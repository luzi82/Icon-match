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
	final static float LIFE_MAX_UNIT = 100;
	final static float LIFE_MIN_UNIT = 0;

	final static Paint LINE_PAINT = new Paint();
	static {
		LINE_PAINT.setColor(Color.WHITE);
	}

	long mStartTime = System.currentTimeMillis();

	int mScreenWidth;
	int mScreenHeight;

	int mBarScreenHeight;
	int mScreenBarCount;

	float mLifeUnit = LIFE_MAX_UNIT; // 0-100

	LinkedList<Integer> answer = new LinkedList<Integer>();

	@Override
	public void draw(Canvas c) {
		c.drawColor(Color.BLACK);
		float lineY = mScreenHeight - (mLifeUnit * mBarScreenHeight / BAR_UNIT);
		for (int v : answer) {
			float topY = lineY - mBarScreenHeight+1;
			if (v == 0) {
				c.drawRect(0, topY, mScreenWidth / 2, lineY, LINE_PAINT);
			} else {
				c.drawRect(mScreenWidth / 2, topY, mScreenWidth, lineY,
						LINE_PAINT);
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
		long timeDiff = System.currentTimeMillis() - mStartTime;
		float speed = BAR_UNIT * (mIconMatchGameActivity.getPeriodMs())
				* (float) Math.pow(2.0d, ((double) (timeDiff) / 60000))
				/ 1000.0f;
		mLifeUnit -= speed;
		while (answer.size() < mScreenBarCount) {
			answer.addLast((Math.random() > 0.5) ? 1 : 0);
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
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mLifeUnit += BAR_UNIT;
			if (mLifeUnit > LIFE_MAX_UNIT) {
				mLifeUnit = LIFE_MAX_UNIT;
			}
			answer.removeFirst();
		}
	}

	@Override
	public void resume() {
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
		mScreenWidth = width;
		mScreenHeight = height;
		mBarScreenHeight = width / 3;
		mScreenBarCount = (mScreenHeight / mBarScreenHeight) + 1;
	}

	IconMatchGameActivity mIconMatchGameActivity;

	public IconMatchGame(IconMatchGameActivity iconMatchGameActivity) {
		mIconMatchGameActivity = iconMatchGameActivity;
	}

}
