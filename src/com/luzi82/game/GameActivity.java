package com.luzi82.game;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

public abstract class GameActivity extends Activity implements StateParent {

	GameView view;
	private PowerManager mPowerManager;
	private PowerManager.WakeLock mWakeLock;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
		mWakeLock = mPowerManager.newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getTag());

		setContentView(getLayoutId());

		view = (GameView) findViewById(getViewId());
	}

	protected abstract AbstractState<?> createGame();

	@Override
	protected void onResume() {
		super.onResume();
		mWakeLock.acquire();
		view.onResume();
	}

	@Override
	protected void onPause() {
		view.onPause();
		mWakeLock.release();
		super.onPause();
	}

	abstract protected int getLayoutId();

	abstract protected int getViewId();

	abstract public int getPeriodMs();

	abstract protected String getTag();

}
