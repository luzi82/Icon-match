package com.luzi82.iconmatch;

import java.util.LinkedList;
import java.util.Random;

public class GameLogic {

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;

	public static final float LIFE_MAX = PHI * 3;

	final Random mRandom;

	public float mLifeBase;

	public State mState = State.PLAY;

	public final int mSelectionSize;

	public final int mTarget;
	public int mBlockNextIdx = 0;

	public int mBlockKill = 0;

	public LinkedList<Block> mAnswer = new LinkedList<Block>();
	LinkedList<Integer> mRandomHistory = new LinkedList<Integer>();

	public GameLogic(float aMulti, Random aRandom, int aSelectionSize, int aTarget) {
		mFactor = aMulti * FACTOR;
		mLifeBase = LIFE_MAX + ing(0);
		mRandom = aRandom;
		mTarget = aTarget;
		mSelectionSize = aSelectionSize;
	}

	public void tick(float aSecGone) {
		if (mState != State.PLAY)
			return;

		// win
		if (mBlockKill >= mTarget) {
			mState = State.WIN;
			return;
		}

		// gameover
		if (life(aSecGone) <= 0) {
			mState = State.LOSE;
			return;
		}

		buildBlock();
	}

	public int kill(Side aSize) {
		if (mState != State.PLAY)
			return -2;
		Block first = mAnswer.getFirst();
		if (first == null)
			return -2;
		boolean hit = ((aSize == Side.LEFT) == (first.left == first.center));
		if (hit) {
			int ret = first.center;
			mAnswer.removeFirst();
			mLifeBase += 1;
			++mBlockKill;
			return ret;
		} else {
			mState = State.LOSE;
			return -1;
		}
	}

	public float life(float aSecGone) {
		return mLifeBase - ing(aSecGone);
	}

	public static final float FACTOR = (float) (30f / Math.log(PHI));
	public final float mFactor;

	public float ing(float aT) {
		return ing(mFactor, aT);
	}

	public static float ing(float aFactor, float aT) {
		return (float) (aFactor * Math.pow(PHI, aT / 30f));
	}

	enum State {
		PLAY, WIN, LOSE
	}

	public class Block {
		int left;
		int center;
		int right;
	}

	void buildBlock() {
		if (mSelectionSize > 0) {
			while ((mAnswer.size() < LIFE_MAX) && (mBlockNextIdx < mTarget)) {
				Block newBlock = new Block();
				newBlock.left = randomNext();
				newBlock.right = randomNext();
				newBlock.center = (mRandom.nextBoolean()) ? newBlock.left : newBlock.right;
				mAnswer.addLast(newBlock);
				++mBlockNextIdx;
			}
		}
	}

	private int randomNext() {
		int ret;
		do {
			ret = mRandom.nextInt(mSelectionSize);
		} while (mRandomHistory.contains(ret));
		mRandomHistory.addFirst(ret);
		while (mRandomHistory.size() > Math.max(1, Math.min(10, mSelectionSize / 2))) {
			mRandomHistory.removeLast();
		}
		return ret;
	}

	public static float toFactor(int aTarget) {
		float i0 = ing(1, 0);
		float i30 = ing(1, 30);
		float iDiff = i30 - i0;
		float ret = (LIFE_MAX + aTarget - 1) / iDiff;
		// System.err.println(ing(ret, 30) - ing(ret, 0));
		ret /= FACTOR;
		return ret;
	}

	public enum Side {
		LEFT, RIGHT
	}

}
