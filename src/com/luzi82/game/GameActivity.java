package com.luzi82.game;

import android.app.Activity;
import android.os.Bundle;

import com.luzi82.iconmatch.R;

public class GameActivity extends Activity {

	AbstractGame game;
	GameView view;
	GameThread thread;

	protected void onCreate(Bundle savedInstanceState, AbstractGame game) {
		super.onCreate(savedInstanceState);

		this.game = game;

		setContentView(R.layout.game_layout);

		view = (GameView) findViewById(R.id.game_view);
		thread = view.getThread();

		view.setGame(game);
		thread.setGame(game);
	}

}
