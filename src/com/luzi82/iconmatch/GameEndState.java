package com.luzi82.iconmatch;

import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.luzi82.game.AbstractState;

public class GameEndState extends AbstractState<IconMatchGame> {

	static final DecimalFormat TIME_FORMAT = new DecimalFormat("0.00s");
	static final DecimalFormat SPEED_FORMAT = new DecimalFormat("0.00/s");
	static final float UNIT_FACTOR = 1f / 30f;

	float mUnit;

	Paint mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint mItemTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint mItemValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint mClickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
	String mTime;
	String mAvgSpeed;
	String mEndSpeed;

	public GameEndState(IconMatchGame iconMatchGame) {
		super(iconMatchGame);
	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);

		mParent.drawGrayLayer(c);

		float centerX = mParent.getScreenWidth() / 2.0f;
		float textY = mUnit * 2.5f;
		float rightX = mParent.getScreenWidth();

		textY += mUnit * 2;
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

		textY += mUnit * 2;
		c.drawText("Time", 0, textY, mItemTitlePaint);
		c.drawText(mTime, rightX, textY, mItemValuePaint);

		textY += mUnit * 2;
		c.drawText("Avg. Speed", 0, textY, mItemTitlePaint);
		c.drawText(mAvgSpeed, rightX, textY, mItemValuePaint);

		textY += mUnit * 2;
		c.drawText("End Speed", 0, textY, mItemTitlePaint);
		c.drawText(mEndSpeed, rightX, textY, mItemValuePaint);

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
		mTime = TIME_FORMAT.format(mParent.mGameMachine.timeGone() / 1000f);
		mAvgSpeed = SPEED_FORMAT.format(mParent.mGameMachine.avgSpeed());
		mEndSpeed = SPEED_FORMAT.format(mParent.mGameMachine.currentSpeed());

		updateSize();
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
		super.surfaceChanged(format, width, height);
		updateSize();
	}

	private void updateSize() {
		mUnit = mParent.getScreenHeight() * UNIT_FACTOR;
		mTitlePaint.setTextSize(mUnit * 2);
		mItemTitlePaint.setTextSize(mUnit * 2);
		mItemValuePaint.setTextSize(mUnit * 2);
		mClickPaint.setTextSize(mUnit);
	}

}
