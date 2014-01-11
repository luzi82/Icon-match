package com.luzi82.iconmatch;

import com.badlogic.gdx.Screen;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrPal;
import com.luzi82.homuvalue.Value.Variable;

public class IconMatchGame extends GrGame {

	public Variable<Integer> mItemCount = new Variable<Integer>();

	public IconMatchGame(GrPal aPal) {
		super(aPal);
		mItemCount.set(30);
	}

	@Override
	public void create() {
		super.create();
		Screen screen = new HomeScreen(this);
		setScreen(screen);
	}

}
