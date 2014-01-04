package com.luzi82.gdx;

public abstract class GrView {

	protected final int WIDTH;
	protected final int HEIGHT;

	private static final int[] itmCount = { 0 };

	public GrView(int aWidth, int aHeight) {
		this.WIDTH = aWidth;
		this.HEIGHT = aHeight;
		System.err.println("GrRender.WIDTH = " + this.WIDTH);
		System.err.println("GrRender.HEIGHT = " + this.HEIGHT);
		synchronized (itmCount) {
			++itmCount[0];
			System.err.println("GrRender.itmCount " + itmCount[0]);
		}
	}

	public abstract void render(float aDelta);

	public void dispose() {
		synchronized (itmCount) {
			--itmCount[0];
			System.err.println("GrRender.itmCount " + itmCount[0]);
		}
		GrDeepDispose.disposeMember(this, Object.class);
	}

	public void touchDown(int x, int y, int pointer, int button, long aTime) {
		// dummy
	}

	public void touchUp(int x, int y, int pointer, int button, long aTime) {
		// dummy
	}

	public void touchDragged(int x, int y, int pointer, long aTime) {
		// dummy
	}

	public void mouseMoved(int x, int y, long aTime) {
		// dummy
	}

}
