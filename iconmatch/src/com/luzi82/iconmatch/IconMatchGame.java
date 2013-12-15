package com.luzi82.iconmatch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class IconMatchGame extends Game {

	@Override
	public void create() {
		Screen screen = new IconMatchScreen();
		setScreen(screen);
	}

}
