package com.luzi82.gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;

public abstract class GrScreen implements Screen, Disposable, GrView.Listener {

	protected GrGame iParent;
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected GrView mRender;

	protected Logger iLogger = new Logger(this.getClass().getSimpleName(), Logger.DEBUG);

	protected GrScreen(GrGame aParent) {
		iParent = aParent;
	}

	@Override
	public final void show() {
		iLogger.debug("show");
		if (mRender == null) {
			mRender = createRender(mScreenWidth, mScreenHeight);
			mRender.setListener(this);
		}
		onScreenShow();
	}

	@Override
	public final void resume() {
		iLogger.debug("resume");
		if (mRender == null) {
			mRender = createRender(mScreenWidth, mScreenHeight);
			mRender.setListener(this);
		}
		onScreenResume();
	}

	@Override
	public final void resize(int aWidth, int aHeight) {
		iLogger.debug("resize");
		if ((mScreenWidth == aWidth) && (mScreenHeight == aHeight)) {
			return;
		}
		mScreenWidth = aWidth;
		mScreenHeight = aHeight;
		onScreenResize();
		if (mRender != null) {
			mRender.dispose();
			mRender = createRender(mScreenWidth, mScreenHeight);
			mRender.setListener(this);
		}
	}

	@Override
	public final void render(float aDelta) {
		// iLogger.debug("render");
		if (mRender != null) {
			mRender.render(aDelta);
		}
	}

	@Override
	public final void hide() {
		iLogger.debug("hide");
		onScreenHide();
		if (mRender != null) {
			mRender.dispose();
			mRender = null;
		}
	}

	@Override
	public final void pause() {
		iLogger.debug("pause");
		onScreenPause();
		if (mRender != null) {
			mRender.dispose();
			mRender = null;
		}
	}

	@Override
	public final void dispose() {
		// iLogger.debug("dispose");
		onScreenDispose();
		disposeMember();
	}

	private void disposeMember() {
		GrDeepDispose.disposeMember(this, Object.class);
	}

	protected void onScreenResize() {
		// dummy
	}

	protected void onScreenShow() {
		// dummy
	}

	protected void onScreenResume() {
		// dummy
	}

	protected void onScreenRender(float aDelta) {
		// dummy
	}

	protected void onScreenHide() {
		// dummy
	}

	protected void onScreenPause() {
		// dummy
	}

	protected void onScreenDispose() {
		// dummy
	}

	public boolean keyDown(int keycode, long aTime) {
		// dummy
		return false;
	}

	public boolean keyUp(int keycode, long aTime) {
		// dummy
		return false;
	}

	public boolean keyTyped(char character, long aTime) {
		// dummy
		return false;
	}

	public boolean touchDown(int x, int y, int pointer, int button, long aTime) {
		if (mRender != null) {
			mRender.touchDown(x, y, pointer, button, aTime);
		}
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button, long aTime) {
		if (mRender != null) {
			mRender.touchUp(x, y, pointer, button, aTime);
		}
		return false;
	}

	public boolean touchDragged(int x, int y, int pointer, long aTime) {
		if (mRender != null) {
			mRender.touchDragged(x, y, pointer, aTime);
		}
		return false;
	}

	public boolean mouseMoved(int x, int y, long aTime) {
		if (mRender != null) {
			mRender.mouseMoved(x, y, aTime);
		}
		return false;
	}

	public boolean scrolled(int amount, long aTime) {
		// dummy
		return false;
	}

	public int getScreenWidth() {
		return mScreenWidth;
	}

	public int getScreenHeight() {
		return mScreenHeight;
	}

	public Logger getLogger() {
		return iLogger;
	}

	protected abstract GrView createRender(int aWidth, int aHeight);

}
