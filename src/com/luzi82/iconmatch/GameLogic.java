package com.luzi82.iconmatch;

import java.util.LinkedList;
import java.util.Random;

public class GameLogic {

	// const

	final public static float BAR_UNIT = 20;
	final static float LIFE_MAX_UNIT = 80;
	final static float LIFE_MIN_UNIT = 0;
	final static float PENALTY_UNIT = 20;

	final static float BASE_SPEED = 0.5f;
	final static float SPEED_FACTOR = (float) (Math.log(2) / 120);

	// instance const

	final Random mRandom;
	final int mPeriodMs;

	// env var

	public int mSelectionSize;
	public int mScreenBarCount;

	// long var

	int mBlockDone = 0;
	int mScore = 0;
	float mScoreFloat = 0;
	float mScoreBase = 0;
	int mCombo = 0;
	int mMaxCombo = 0;
	int mMiss = 0;
	int mTickGone = 0;

	public float mLifeUnit = LIFE_MAX_UNIT; // 0-100
	float mFastReduceUnit = LIFE_MAX_UNIT;
	public float mPenaltyUnit;

	// short var

	public boolean mPenaltyState = false;
	public boolean mGameEnd = false;

	public LinkedList<Block> mAnswer = new LinkedList<Block>();
	LinkedList<Integer> mRandomHistory = new LinkedList<Integer>();

	Block mLastMissBlock;

	int mLastHitTick;
	int mLastHitScoreAdd;

	public GameLogic(int periodMs, Random random) {
		mPeriodMs = periodMs;
		mRandom = random;
	}

	void tick() {
		if (mGameEnd) {
			return;
		}
		++mTickGone;

		float speed = BAR_UNIT * mPeriodMs * currentSpeed() / 1000;

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
			mLastMissBlock = null;
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
				if (Math.random() < 0.5){
					newBlock.left = randomNext();
					newBlock.right = randomNext();
				} else {
					newBlock.right = randomNext();
					newBlock.left = randomNext();				
				}
				newBlock.center = (Math.random() < 0.5) ? newBlock.left
						: newBlock.right;
				mAnswer.addLast(newBlock);
			}
		}
	}

	// return: 1:hit, -1:miss, 0:ignore
	int killBlock(int pos) {
		if (mPenaltyState) {
			return 0;
		}
		if (mGameEnd) {
			return 0;
		}
		int ret;
		Block first = mAnswer.removeFirst();
		if ((pos == 0) == (first.center == first.left)) {
			mLifeUnit += BAR_UNIT;
			mFastReduceUnit += BAR_UNIT;
			if (mFastReduceUnit > LIFE_MAX_UNIT) {
				mFastReduceUnit = LIFE_MAX_UNIT;
			}
			++mBlockDone;
			++mCombo;
			if (mCombo > mMaxCombo) {
				mMaxCombo = mCombo;
			}
			mLastHitTick = mTickGone;
			ret = 1;
		} else {
			mPenaltyState = true;
			mPenaltyUnit = mLifeUnit - PENALTY_UNIT;
			mLifeUnit += BAR_UNIT;
			mFastReduceUnit += BAR_UNIT;
			mScoreBase = mScoreFloat;
			mCombo = 0;
			mLastMissBlock = first;
			++mMiss;
			ret = -1;
		}
		mScoreFloat = mScoreBase
				+ ((mCombo > 0) ? ((float) (mCombo * Math.log10(mCombo))) : 0f);
		int oldScore = mScore;
		mScore = (int) (mScoreFloat * 10);
		mLastHitScoreAdd = mScore - oldScore;
		
		buildBlock();

		return ret;
	}

	private int randomNext() {
		int ret;
		do {
			ret = mRandom.nextInt(mSelectionSize);
		} while (mRandomHistory.contains(ret));
		mRandomHistory.addFirst(ret);
		while (mRandomHistory.size() > 
			Math.max(1, Math.min(10, mSelectionSize / 2))) {
			mRandomHistory.removeLast();
		}
		return ret;
	}

	int timeGone() {
		return mPeriodMs * mTickGone;
	}

	float avgSpeed() {
		return (((float) (mBlockDone + mMiss)) / ((float) timeGone())) * 1000f;
	}

	float currentSpeed() {
		return (BASE_SPEED + SPEED_FACTOR * mBlockDone);
	}

	public class Block {
		int left;
		int center;
		int right;
	}

}
