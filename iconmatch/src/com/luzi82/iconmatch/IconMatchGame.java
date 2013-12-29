package com.luzi82.iconmatch;

import com.badlogic.gdx.Screen;
import com.luzi82.gdx.GrGame;

public class IconMatchGame extends GrGame {

	@Override
	public void create() {
		super.create();
		Screen screen = new HomeScreen(this);
		setScreen(screen);
	}

}
