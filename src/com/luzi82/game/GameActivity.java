package com.luzi82.game;

import android.app.Activity;
import android.os.Bundle;

import com.luzi82.iconmatch.R;

public abstract class GameActivity extends Activity {

	AbstractGame game;
	GameView view;
	GameThread thread;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		game = getGame();

		setContentView(R.layout.game_layout);

		view = (GameView) findViewById(R.id.game_view);
		thread = view.getThread();

		view.setGame(game);
		thread.setGame(game);
	}

	protected abstract AbstractGame getGame();

}
