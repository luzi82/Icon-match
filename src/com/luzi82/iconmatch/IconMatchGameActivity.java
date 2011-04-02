package com.luzi82.iconmatch;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.luzi82.game.AbstractState;
import com.luzi82.game.GameActivity;

public class IconMatchGameActivity extends GameActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	protected AbstractState createGame() {
		return new IconMatchGame(this);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.game_layout;
	}

	@Override
	public long getPeriodMs() {
		return 20;
	}

	@Override
	protected int getViewId() {
		return R.id.game_view;
	}

}
