package com.luzi82.iconmatch;

import java.util.LinkedList;
import java.util.Random;

public class GameMachine {

	final public static float BAR_UNIT = 20;
	final static float LIFE_MAX_UNIT = 80;
	final static float LIFE_MIN_UNIT = 0;
	final static float PENALTY_UNIT = 20;

	final static float BASE_SPEED = 0.5f;
	final static float SPEED_FACTOR = (float) (Math.log(2) / 60);

	final Random mRandom;

	final int mPeriodMs;

	public int mSelectionSize;

	public int mScreenBarCount;

	public float mLifeUnit = LIFE_MAX_UNIT; // 0-100
	float mFastReduceUnit = LIFE_MAX_UNIT;
	public float mPenaltyUnit;

	int mBlockDone = 0;

	float mScore = 0;
	float mScoreBase = 0;
	public int mCombo = 0;

	public boolean mPenaltyState = false;
	public boolean mGameEnd = false;

	public LinkedList<Block> mAnswer = new LinkedList<Block>();

	LinkedList<Integer> mRandomHistory = new LinkedList<Integer>();

	public GameMachine(int periodMs, Random random) {
		mPeriodMs = periodMs;
		mRandom = random;
	}

	void tick() {
		if (mGameEnd) {
			return;
		}
		float speed = BAR_UNIT * mPeriodMs
				* (BASE_SPEED + SPEED_FACTOR * mBlockDone) / 1000;

		buildBlock();

		// life reduce
		mLifeUnit -= speed;
		mFastReduceUnit -= speed;
		if (mLifeUnit - mFastReduceUnit < 1) {
			mLifeUnit = mFastReduceUnit;
		} else {
			mLifeUnit -= 1;
			mLifeUnit = (mLifeUnit * 7 + mFastReduceUnit) / 8;
		}

		// penalty state
		if (mPenaltyState && (mLifeUnit < mPenaltyUnit)) {
			mPenaltyState = false;
		}

		// game over
		if (mLifeUnit <= 0) {
			mLifeUnit = 0;
			mGameEnd = true;
		}
	}

	void buildBlock() {
		if (mSelectionSize > 0) {
			while (mAnswer.size() < mScreenBarCount) {
				Block newBlock = new Block();
				newBlock.left = randomNext();
				newBlock.right = randomNext();
				newBlock.center = (Math.random() < 0.5) ? newBlock.left
						: newBlock.right;
				mAnswer.addLast(newBlock);
			}
		}
	}

	void killBlock(int pos) {
		if (mPenaltyState) {
			return;
		}
		if (mGameEnd) {
			return;
		}
		Block first = mAnswer.getFirst();
		if ((pos == 0) == (first.center == first.left)) {
			mLifeUnit += BAR_UNIT;
			mFastReduceUnit += BAR_UNIT;
			if (mFastReduceUnit > LIFE_MAX_UNIT) {
				mFastReduceUnit = LIFE_MAX_UNIT;
			}
			++mBlockDone;
			++mCombo;
		} else {
			mPenaltyState = true;
			mPenaltyUnit = mLifeUnit - PENALTY_UNIT;
			mLifeUnit += BAR_UNIT;
			mFastReduceUnit += BAR_UNIT;
			mScoreBase = mScore;
			mCombo = 0;
		}
		mAnswer.removeFirst();
		mScore = mScoreBase
				+ ((mCombo > 0) ? ((float) (mCombo * Math.log10(mCombo))) : 0f);
		buildBlock();
	}

	private int randomNext() {
		int ret;
		do {
			ret = mRandom.nextInt(mSelectionSize);
		} while (mRandomHistory.contains(ret));
		mRandomHistory.addFirst(ret);
		while (mRandomHistory.size() > 10) {
			mRandomHistory.removeLast();
		}
		return ret;
	}

	public class Block {
		int left;
		int center;
		int right;
	}

}
