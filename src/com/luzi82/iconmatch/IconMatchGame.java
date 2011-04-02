package com.luzi82.iconmatch;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.luzi82.game.StateGroup;

public class IconMatchGame extends StateGroup {

	final static String STATE_START = "start";
	final static String STATE_PAUSE = "pause";
	final static String STATE_END = "end";
	final static String STATE_RUN = "run";

	// final static Paint LIVE_PAINT = new Paint();
	// final static Paint INACTIVE_PAINT = new Paint();
	// final static Paint PENALTY_PAINT = new Paint();
	// final static Paint DEAD_PAINT = new Paint();
	// final static Paint LIFE_LIMIT_PAINT = new Paint();
	final static Paint DARK_BLOCK_PAINT = new Paint();
	final static Paint BOTTOM_BAR_BACKGROUND = new Paint();
	static {
		// LIVE_PAINT.setColor(Color.WHITE);
		// INACTIVE_PAINT.setColor(Color.GRAY);
		// PENALTY_PAINT.setColor(Color.YELLOW);
		// DEAD_PAINT.setColor(Color.RED);
		// LIFE_LIMIT_PAINT.setColor(Color.WHITE);
		BOTTOM_BAR_BACKGROUND.setColor(Color.DKGRAY);
		DARK_BLOCK_PAINT.setColor(Color.argb(0x7f, 0, 0, 0));
	}

	final static Random mRandom = new Random();

	// instance const

	final IconMatchGameActivity mActivity;
	final int mPeriodMs;

	// device input var

	int mScreenWidthPx;
	int mScreenHeightPx;

	// env cal var

	final Paint mScorePaint = new Paint();
	final Paint mComboPaint = new Paint();
	{
		mScorePaint.setColor(Color.WHITE);
		mScorePaint.setTextAlign(Paint.Align.LEFT);
		mComboPaint.setColor(Color.WHITE);
		mComboPaint.setTextAlign(Paint.Align.RIGHT);
	}
	float mScoreY = 0;
	int mBarScreenHeight;
	// int mScreenBarCount;
	int mBottomScreenHeight;
	Bitmap[] mSelectionBitmap;
	Bitmap[] mCenterBitmap;

	// game var

	GameMachine mGameMachine;
	int mShowScore;

	// tick/draw var

	public long now;

	// ////////////////////

	public IconMatchGame(IconMatchGameActivity iconMatchGameActivity) {
		mActivity = iconMatchGameActivity;
		mPeriodMs = (int) mActivity.getPeriodMs();
		mGameMachine = new GameMachine(mActivity.getPeriodMs(), mRandom);

		addState(STATE_START, new GameStartState(this));
		addState(STATE_PAUSE, new GamePauseState(this));
		addState(STATE_END, new GameEndState(this));
		addState(STATE_RUN, new GameRunState(this, mGameMachine));

		setCurrentState(STATE_START);
	}

	public int getScreenWidth() {
		return mScreenWidthPx;
	}

	public int getScreenHeight() {
		return mScreenHeightPx;
	}

	@Override
	public void surfaceChanged(int format, int width, int height) {
		mScreenWidthPx = width;
		mScreenHeightPx = height;
		mBarScreenHeight = width * 2 / 7;
		mGameMachine.mScreenBarCount = (mScreenHeightPx / mBarScreenHeight) + 1;
		mBottomScreenHeight = width / 8;
		mScorePaint.setTextSize(mBottomScreenHeight * 0.75f);
		mComboPaint.setTextSize(mBottomScreenHeight * 0.75f);
		mScoreY = height - (mBottomScreenHeight / 8.0f);

		loadFile();
	}

	@Override
	public void draw(Canvas c) {
		now = System.currentTimeMillis();
		c.drawColor(Color.BLACK);
		drawBlock(c);
		drawBottomBar(c);
		super.draw(c);
	}

	@Override
	public void tick() {
		now = System.currentTimeMillis();
		super.tick();
	}

	// @Override
	// public void draw(Canvas c) {
	// // Paint firstpaint = mGameEnd ? DEAD_PAINT
	// // : mPenaltyState ? PENALTY_PAINT : LIVE_PAINT;
	// // Paint otherpaint = mGameEnd ? DEAD_PAINT
	// // : mPenaltyState ? PENALTY_PAINT : INACTIVE_PAINT;
	// c.drawColor(mPenaltyState ? Color.YELLOW : Color.BLACK);
	// float lineY = mScreenHeight - (mLifeUnit * mBarScreenHeight / BAR_UNIT)
	// - mBottomScreenHeight;
	// // Paint paint = firstpaint;
	// for (Block v : mAnswer) {
	// float topY = lineY - mBarScreenHeight + 1;
	// // if (v == 0) {
	// // c.drawRect(0, topY, mScreenWidth / 2, lineY, paint);
	// // } else {
	// // c.drawRect(mScreenWidth / 2, topY, mScreenWidth, lineY, paint);
	// // }
	// c.drawBitmap(mSelectionBitmap[v.left], 0, topY, null);
	// c.drawBitmap(mCenterBitmap[v.center],
	// (mScreenWidth - mBarScreenHeight) / 2, topY, null);
	// c.drawBitmap(mSelectionBitmap[v.right],
	// (mScreenWidth - mBarScreenHeight), topY, null);
	// c.drawLine(0, lineY, mScreenWidth, lineY, LIVE_PAINT);
	// lineY -= mBarScreenHeight;
	// // paint = otherpaint;
	// }
	// c.drawRect(0, mScreenHeight - mBottomScreenHeight, mScreenWidth,
	// mScreenHeight, BOTTOM_BAR_BACKGROUND);
	// c.drawLine(0, mScreenHeight - mBottomScreenHeight, mScreenWidth,
	// mScreenHeight - mBottomScreenHeight, LIFE_LIMIT_PAINT);
	// c.drawText(Integer.toString(mShowScore), 0, mScoreY, mScorePaint);
	// c
	// .drawText(Integer.toString(mCombo), mScreenWidth, mScoreY,
	// mComboPaint);
	// }
	//
	// @Override
	// public void onGamePause() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void tick() {
	// long now = System.currentTimeMillis();
	// if (!mGameEnd) {
	// // speed cal
	// // long timeDiff = System.currentTimeMillis() - mStartTime;
	// // float speed = BAR_UNIT * (mIconMatchGameActivity.getPeriodMs())
	// // * (float) Math.pow(2.0d, ((double) (timeDiff) / 60000))
	// // / 1000.0f;
	// float speed = BAR_UNIT * mPeriodMs
	// * (BASE_SPEED + SPEED_FACTOR * mBlockDone) / 1000;
	//
	// // bar re-gen
	// while (mAnswer.size() < mScreenBarCount) {
	// Block newBlock = new Block();
	// newBlock.left = randomNext();
	// newBlock.right = randomNext();
	// newBlock.center = (Math.random() < 0.5) ? newBlock.left
	// : newBlock.right;
	// mAnswer.addLast(newBlock);
	// }
	//
	// // life reduce
	// mLifeUnit -= speed;
	// mFastReduceUnit -= speed;
	// if (mLifeUnit - mFastReduceUnit < 1) {
	// mLifeUnit = mFastReduceUnit;
	// } else {
	// mLifeUnit -= 1;
	// mLifeUnit = (mLifeUnit * 7 + mFastReduceUnit) / 8;
	// }
	//
	// // penalty state
	// if (mPenaltyState && (mLifeUnit < mPenaltyUnit)) {
	// mPenaltyState = false;
	// }
	//
	// // game over
	// if (mLifeUnit <= 0) {
	// mLifeUnit = 0;
	// mGameEnd = true;
	// mGameEndStartMs = now;
	// }
	// } else {
	// if (now - mGameEndStartMs >= 2000) {
	// // mIconMatchGameActivity.setResult(Activity.RESULT_OK);
	// mActivity.finish();
	// }
	// }
	// }
	//
	// @Override
	// public void onKeyDown(int keyCode, KeyEvent msg) {
	// }
	//
	// @Override
	// public void onKeyUp(int keyCode, KeyEvent msg) {
	// }
	//
	// @Override
	// public void onTouchEvent(MotionEvent event) {
	// if (!mGameEnd) {
	// if (!mPenaltyState) {
	// if (event.getAction() == MotionEvent.ACTION_DOWN) {
	// int v = (event.getX() < mScreenWidth / 2) ? 0 : 1;
	// Block first = mAnswer.getFirst();
	// if ((v == 0) == (first.center == first.left)) {
	// mLifeUnit += BAR_UNIT;
	// mFastReduceUnit += BAR_UNIT;
	// if (mFastReduceUnit > LIFE_MAX_UNIT) {
	// mFastReduceUnit = LIFE_MAX_UNIT;
	// }
	// ++mBlockDone;
	// ++mCombo;
	// } else {
	// mPenaltyState = true;
	// mPenaltyUnit = mLifeUnit - PENALTY_UNIT;
	// mLifeUnit += BAR_UNIT;
	// mFastReduceUnit += BAR_UNIT;
	// mScoreBase = mScore;
	// mCombo = 0;
	// }
	// mAnswer.removeFirst();
	// mScore = mScoreBase
	// + ((mCombo > 0) ? ((float) (mCombo * Math
	// .log10(mCombo))) : 0f);
	// mShowScore = (int) (mScore * 10);
	// }
	// }
	// }
	// }
	//
	// @Override
	// public void onGameResume() {
	// }

	public void drawBlock(Canvas c) {
		float startY = mScreenHeightPx
				- (mGameMachine.mLifeUnit * mBarScreenHeight / GameMachine.BAR_UNIT)
				- mBottomScreenHeight;
		float lineY = startY;
		for (GameMachine.Block v : mGameMachine.mAnswer) {
			float topY = lineY - mBarScreenHeight + 1;
			c.drawBitmap(mSelectionBitmap[v.left], 0, topY, null);
			c.drawBitmap(mCenterBitmap[v.center],
					(mScreenWidthPx - mBarScreenHeight) / 2, topY, null);
			c.drawBitmap(mSelectionBitmap[v.right],
					(mScreenWidthPx - mBarScreenHeight), topY, null);
			lineY -= mBarScreenHeight;
		}
		lineY = startY - mBarScreenHeight;
		c.drawRect(0, 0, mScreenWidthPx, lineY, DARK_BLOCK_PAINT);
	}

	public void drawBottomBar(Canvas c) {
		c.drawRect(0, mScreenHeightPx - mBottomScreenHeight, mScreenWidthPx,
				mScreenHeightPx, BOTTOM_BAR_BACKGROUND);
		c.drawText(Integer.toString(mShowScore), 0, mScoreY, mScorePaint);
		c.drawText(Integer.toString(mGameMachine.mCombo), mScreenWidthPx,
				mScoreY, mComboPaint);
	}

	private void loadFile() {
		LinkedList<Bitmap> aBitmapList = new LinkedList<Bitmap>();
		LinkedList<Bitmap> bBitmapList = new LinkedList<Bitmap>();

		String path = "/sdcard/IconMatch/MadokaRune";
		File bitmapFolder = new File(path);
		String[] folderContent = bitmapFolder.list();
		for (String filename : folderContent) {
			if (!filename.endsWith(".a.png"))
				continue;
			String prefix = filename.substring(0, filename.length() - 6);
			String aFilename = path + File.separator + prefix + ".a.png";
			String bFilename = path + File.separator + prefix + ".b.png";
			if (!new File(aFilename).exists())
				continue;
			if (!new File(bFilename).exists())
				continue;
			Bitmap aBitmap = BitmapFactory.decodeFile(aFilename);
			aBitmap = resize(aBitmap, mBarScreenHeight, mBarScreenHeight);
			aBitmapList.add(aBitmap);
			Bitmap bBitmap = BitmapFactory.decodeFile(bFilename);
			bBitmap = resize(bBitmap, mBarScreenHeight, mBarScreenHeight);
			bBitmapList.add(bBitmap);
		}

		mCenterBitmap = aBitmapList.toArray(new Bitmap[0]);
		mSelectionBitmap = bBitmapList.toArray(new Bitmap[0]);

		mGameMachine.mSelectionSize = aBitmapList.size();
		mGameMachine.buildBlock();

	}

	private Bitmap resize(Bitmap bitmap, int targetWidth, int targetHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float scaleWidth = ((float) targetWidth) / width;
		float scaleHeight = ((float) targetHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);

		return resizedBitmap;
	}

}
