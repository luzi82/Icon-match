package com.luzi82.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	private GameThread thread;
	private AbstractGame game;

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GameThread getThread() {
		return thread;
	}

	public void setGame(AbstractGame game) {
		this.game = game;
	}

}
