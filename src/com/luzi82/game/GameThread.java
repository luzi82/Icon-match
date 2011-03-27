package com.luzi82.game;

public class GameThread extends Thread {

	private AbstractGame game;

	public void setGame(AbstractGame game) {
		this.game = game;
	}

}
