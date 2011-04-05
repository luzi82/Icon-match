package com.luzi82.iconmatch;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.zip.ZipException;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.luzi82.game.StateGroup;

public class IconMatchGame extends
		StateGroup<IconMatchGame, IconMatchGameActivity> {

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
	final static Paint CENTER_TEXT_PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
	final static Paint MISS_LINE_PAINT = new Paint();
	static {
		// LIVE_PAINT.setColor(Color.WHITE);
		// INACTIVE_PAINT.setColor(Color.GRAY);
		// PENALTY_PAINT.setColor(Color.YELLOW);
		// DEAD_PAINT.setColor(Color.RED);
		// LIFE_LIMIT_PAINT.setColor(Color.WHITE);
		BOTTOM_BAR_BACKGROUND.setColor(Color.DKGRAY);
		DARK_BLOCK_PAINT.setColor(Color.argb(0x7f, 0, 0, 0));
		CENTER_TEXT_PAINT.setColor(Color.WHITE);
		CENTER_TEXT_PAINT.setTextAlign(Paint.Align.CENTER);
		CENTER_TEXT_PAINT.setTextSize(30);
		MISS_LINE_PAINT.setColor(Color.RED);
		MISS_LINE_PAINT.setStrokeWidth(5);
	}

	final static Random mRandom = new Random();

	final static long CLICK_DRAW_FADE_MS = 300;
	final static int CLICK_ALPHA_MAX = 0x7f;
	final static float CLICK_RADIUS_FACTOR = 0.5f;

	final static int ADD_SCORE_MS = 1000;

	// instance const

	final int mPeriodMs;
	final String mFilename;

	// device input var

	int mScreenWidthPx;
	int mScreenHeightPx;

	// env cal var

	final Paint mScorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	final Paint mComboPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	final Paint mAddScorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	{
		mScorePaint.setColor(Color.WHITE);
		mScorePaint.setTextAlign(Paint.Align.LEFT);
		mComboPaint.setColor(Color.WHITE);
		mComboPaint.setTextAlign(Paint.Align.RIGHT);
		mAddScorePaint.setColor(Color.DKGRAY);
		mAddScorePaint.setTextAlign(Paint.Align.CENTER);
	}
	float mScoreY = 0;
	int mBarScreenHeight;
	// int mScreenBarCount;
	int mBottomScreenHeight;
	// Bitmap[] mSelectionBitmap;
	// Bitmap[] mCenterBitmap;
	IconPack mIconPack;

	float mClickDrawRadius;

	// game var

	GameLogic mGameMachine;

	// tick/draw var

	public long mNow;
	final Paint mPressHitPaint = new Paint();
	final Paint mPressMissPaint = new Paint();
	{
		mPressHitPaint.setColor(Color.GREEN);
		mPressHitPaint.setStrokeWidth(5);
		mPressHitPaint.setStyle(Paint.Style.STROKE);
		mPressMissPaint.setColor(Color.RED);
		mPressMissPaint.setStrokeWidth(5);
		mPressMissPaint.setStyle(Paint.Style.STROKE);
	}
	LinkedList<ClickDraw> mClickDrawList = new LinkedList<ClickDraw>();

	// ////////////////////

	public IconMatchGame(IconMatchGameActivity iconMatchGameActivity,
			String iconPackFilename) {
		super(iconMatchGameActivity);

		mPeriodMs = (int) mParent.getPeriodMs();
		mFilename = iconPackFilename;
		mGameMachine = new GameLogic(mParent.getPeriodMs(), mRandom);

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
		mClickDrawRadius = width * CLICK_RADIUS_FACTOR;
		mAddScorePaint.setTextSize(mBarScreenHeight / 3f);

		try {
			loadFile();
		} catch (ZipException e) {
			e.printStackTrace();
			mParent.finish();
		} catch (IOException e) {
			e.printStackTrace();
			mParent.finish();
		}
	}

	@Override
	public void draw(Canvas c) {
		mNow = System.currentTimeMillis();
		c.drawColor(Color.BLACK);
		drawBlockList(c);
		drawBottomBar(c);
		drawClickList(c);
		super.draw(c);
	}

	@Override
	public void tick() {
		mNow = System.currentTimeMillis();
		super.tick();
	}

	public void drawBlockList(Canvas c) {
		float startY = mScreenHeightPx
				- (mGameMachine.mLifeUnit * mBarScreenHeight / GameLogic.BAR_UNIT)
				- mBottomScreenHeight;
		float lineY = startY;

		// draw miss block
		GameLogic.Block missBlock = mGameMachine.mLastMissBlock;
		if (missBlock != null) {
			drawBlock(c, missBlock, lineY + mBarScreenHeight);
			float x0, x1, y0, y1;
			y0 = lineY;
			y1 = lineY + mBarScreenHeight;
			if (missBlock.left != missBlock.center) {
				x0 = 0;
				x1 = mBarScreenHeight;
			} else {
				x0 = mScreenWidthPx - mBarScreenHeight;
				x1 = mScreenWidthPx;
			}
			c.drawLine(x0, y0, x1, y1, MISS_LINE_PAINT);
			c.drawLine(x0, y1, x1, y0, MISS_LINE_PAINT);
		}

		// draw last hit
		if (missBlock == null && mGameMachine.mCombo >= 1) {
			int mHitGoneTick = mGameMachine.mTickGone
					- mGameMachine.mLastHitTick;
			int mHitGoneTime = mHitGoneTick * mPeriodMs;
			if (mHitGoneTime < ADD_SCORE_MS) {
				float y = lineY
						+ (mBarScreenHeight + mAddScorePaint.getTextSize())
						/ 2f;
				String s = Integer.toString(mGameMachine.mCombo) + "combo +"
						+ Integer.toString(mGameMachine.mLastHitScoreAdd);
				c.drawText(s, mScreenWidthPx / 2f, y, mAddScorePaint);
			}
		}

		// draw running block
		for (GameLogic.Block v : mGameMachine.mAnswer) {
			drawBlock(c, v, lineY);
			lineY -= mBarScreenHeight;
		}

		// draw dark
		lineY = mGameMachine.mPenaltyState ? startY
				: (startY - mBarScreenHeight);
		c.drawRect(0, 0, mScreenWidthPx, lineY, DARK_BLOCK_PAINT);
	}

	public void drawBlock(Canvas c, GameLogic.Block b, float bottomY) {
		float topY = bottomY - mBarScreenHeight;
		c.drawBitmap(mIconPack.mSelectionBitmap[b.left], 0, topY, null);
		c.drawBitmap(mIconPack.mCenterBitmap[b.center],
				(mScreenWidthPx - mBarScreenHeight) / 2, topY, null);
		c.drawBitmap(mIconPack.mSelectionBitmap[b.right],
				(mScreenWidthPx - mBarScreenHeight), topY, null);
	}

	public void drawBottomBar(Canvas c) {
		c.drawRect(0, mScreenHeightPx - mBottomScreenHeight, mScreenWidthPx,
				mScreenHeightPx, BOTTOM_BAR_BACKGROUND);
		c.drawText(Integer.toString(mGameMachine.mScore), 0, mScoreY,
				mScorePaint);
		c.drawText(Integer.toString(mGameMachine.mCombo), mScreenWidthPx,
				mScoreY, mComboPaint);
	}

	public void drawClickList(Canvas c) {
		long now = mNow;
		LinkedList<ClickDraw> remove = new LinkedList<ClickDraw>();
		for (ClickDraw cd : mClickDrawList) {
			float diff = ((float) (now - cd.mTime)) / CLICK_DRAW_FADE_MS;
			if (diff >= 1) {
				remove.add(cd);
				continue;
			}
			drawClick(c, cd, diff);
		}
		for (ClickDraw cd : remove) {
			mClickDrawList.remove(cd);
		}
	}

	private void drawClick(Canvas c, ClickDraw cd, float diff) {
		if (diff <= 0) {
			return;
		}
		if (diff >= 1) {
			return;
		}
		float radius = diff * mClickDrawRadius;
		int alpha = (int) (CLICK_ALPHA_MAX * (1 - diff));
		// int alpha = 0xff;
		Paint paint = cd.mHit ? mPressHitPaint : mPressMissPaint;
		paint.setAlpha(alpha);
		// paint.setColor(Color.RED);
		c.drawCircle(cd.mX, cd.mY, radius, paint);
	}

	public void drawGrayLayer(Canvas c) {
		c.drawColor(Color.argb(0x7f, 0, 0, 0));
	}

	public void drawCenterText(Canvas c, String text) {
		float x = mScreenWidthPx / 2;
		float y = (mScreenHeightPx + CENTER_TEXT_PAINT.getTextSize()) / 2;
		c.drawText(text, x, y, CENTER_TEXT_PAINT);
	}

	private void loadFile() throws ZipException, IOException {
		mIconPack = IconPack
				.load(mFilename, mBarScreenHeight, mBarScreenHeight);
		mGameMachine.mSelectionSize = mIconPack.mSelectionSize;
		mGameMachine.buildBlock();
	}

	static class ClickDraw {
		float mX;
		float mY;
		long mTime;
		boolean mHit;
	}

}
