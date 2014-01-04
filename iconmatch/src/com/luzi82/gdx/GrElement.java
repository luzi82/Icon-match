package com.luzi82.gdx;

import java.lang.ref.WeakReference;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class GrElement implements Disposable {

	public final boolean mIsTouchEnabled;
	public final boolean mIsDrawEnabled;

	public GrElement(boolean drawEnabled, boolean touchEnabled) {
		mIsTouchEnabled = touchEnabled;
		mIsDrawEnabled = drawEnabled;
	}

	public abstract void draw(SpriteBatch aSpriteBatch);

	@Override
	public void dispose() {
		GrDeepDispose.disposeMember(this, Object.class);
	}

	public void update() {
		if (!iIsDirty)
			return;
		doUpdate();
		iIsDirty = false;
	}

	public abstract void doUpdate();

	protected boolean iIsDirty = true;

	protected void markDirty() {
		if (iIsDirty)
			return;
		iIsDirty = true;
		if (iListener != null) {
			GrElementListener v = iListener.get();
			if (v != null) {
				v.onElementDirty(this);
			}
		}
	}

	WeakReference<GrElementListener> iListener;

	public void setListener(GrElementListener aView) {
		iListener = new WeakReference<GrElementListener>(aView);
		if (iIsDirty) {
			if (aView != null) {
				aView.onElementDirty(this);
			}
		}
	}

	protected GrElementListener getListener() {
		if (iListener == null)
			return null;
		return iListener.get();
	}

	public abstract void touchDown(int x, int y, int pointer, int button, long aTime);

	public abstract void touchUp(int x, int y, int pointer, int button, long aTime);

	public abstract void touchDragged(int x, int y, int pointer, long aTime);

	public abstract void mouseMoved(int x, int y, long aTime);

}
