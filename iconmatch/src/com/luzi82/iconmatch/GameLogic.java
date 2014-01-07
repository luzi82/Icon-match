package com.luzi82.iconmatch;

public class GameLogic {

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;

	public static final float LIFE_MAX = PHI * 3;

	public float mDone;

	public State mState = State.PLAY;

	public GameLogic(float aMulti) {
		mFactor = aMulti * FACTOR;
		mDone = LIFE_MAX + ing(0);
	}

	public void tick(float aSecGone) {
		if (mState != State.PLAY)
			return;
		float gone = ing(aSecGone);
		if (gone > mDone) {
			mState = State.LOSE;
			return;
		}
	}

	public void kill() {
		if (mState != State.PLAY)
			return;
		mDone += 1;
	}

	public float life(float aSecGone) {
		return mDone - ing(aSecGone);
	}

	public static final float FACTOR = (float) (30f / Math.log(PHI));
	public final float mFactor;

	public float ing(float aT) {
		return (float) (mFactor * Math.pow(PHI, aT / 30f));
	}

	enum State {
		PLAY, WIN, LOSE
	}

}
