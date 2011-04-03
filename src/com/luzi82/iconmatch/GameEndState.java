package com.luzi82.iconmatch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;

public class GameEndState extends AbstractState<IconMatchGame> {

	float mUnit;

	Paint mTitlePaint = new Paint();
	Paint mItemTitlePaint = new Paint();
	Paint mItemValuePaint = new Paint();
	Paint mClickPaint = new Paint();
	{
		mTitlePaint.setColor(Color.WHITE);
		mTitlePaint.setTextAlign(Paint.Align.CENTER);
		mItemTitlePaint.setColor(Color.WHITE);
		mItemValuePaint.setColor(Color.WHITE);
		mItemValuePaint.setTextAlign(Paint.Align.RIGHT);
		mClickPaint.setColor(Color.WHITE);
		mClickPaint.setTextAlign(Paint.Align.CENTER);
	}

	int mCountDown;
	String mScore;
	String mCorrect;
	String mMiss;
	String mMaxCombo;

	public GameEndState(IconMatchGame iconMatchGame) {
		super(iconMatchGame);
	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);

		mParent.drawGrayLayer(c);

		float centerX = mParent.getScreenWidth() / 2.0f;
		float textY = mUnit * 5;
		float rightX = mParent.getScreenWidth();

		textY += mUnit * 3;
		c.drawText("FIN", centerX, textY, mTitlePaint);

		textY += mUnit * 1;

		textY += mUnit * 2;
		c.drawText("Score", 0, textY, mItemTitlePaint);
		c.drawText(mScore, rightX, textY, mItemValuePaint);

		textY += mUnit * 2;
		c.drawText("Hit", 0, textY, mItemTitlePaint);
		c.drawText(mCorrect, rightX, textY, mItemValuePaint);

		textY += mUnit * 2;
		c.drawText("Miss", 0, textY, mItemTitlePaint);
		c.drawText(mMiss, rightX, textY, mItemValuePaint);

		textY += mUnit * 2;
		c.drawText("Max combo", 0, textY, mItemTitlePaint);
		c.drawText(mMaxCombo, rightX, textY, mItemValuePaint);

		textY += mUnit * 1;

		textY += mUnit * 1;
		if (mCountDown < 0) {
			c.drawText("Press screen to continue", centerX, textY, mClickPaint);
		}
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		if (mCountDown < 0) {
			mParent.getParent().finish();
		}
	}

	@Override
	public void tick() {
		super.tick();

		mCountDown -= mParent.mPeriodMs;
	}

	@Override
	public void onStateStart() {
		super.onStateStart();

		mCountDown = 2000;

		mScore = Integer.toString((int) mParent.mGameMachine.mScore);
		mCorrect = Integer.toString(mParent.mGameMachine.mBlockDone);
		mMiss = Integer.toString(mParent.mGameMachine.mMiss);
		mMaxCombo = Integer.toString(mParent.mGameMachine.mMaxCombo);

		mUnit = mParent.getScreenHeight() / 24f;
		mTitlePaint.setTextSize(mUnit * 3);
		mItemTitlePaint.setTextSize(mUnit * 2);
		mItemValuePaint.setTextSize(mUnit * 2);
		mClickPaint.setTextSize(mUnit);
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
		super.surfaceChanged(format, width, height);

		mUnit = mParent.getScreenHeight() / 24f;
		mTitlePaint.setTextSize(mUnit * 3);
		mItemTitlePaint.setTextSize(mUnit * 2);
		mItemValuePaint.setTextSize(mUnit * 2);
		mClickPaint.setTextSize(mUnit);
	}

}
