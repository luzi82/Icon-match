package com.luzi82.iconmatch;

import com.luzi82.game.AbstractGame;
import com.luzi82.game.GameActivity;

public class IconMatchGameActivity extends GameActivity {

	IconMatchGame iconMatchGame = new IconMatchGame();

	@Override
	protected AbstractGame getGame() {
		return iconMatchGame;
	}

}
