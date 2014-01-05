package com.luzi82.iconmatch;

import com.badlogic.gdx.Screen;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrPal;

public class IconMatchGame extends GrGame {

	public IconMatchGame(GrPal aPal) {
		super(aPal);
	}

	@Override
	public void create() {
		super.create();
		Screen screen = new HomeScreen(this);
		setScreen(screen);
	}

}
