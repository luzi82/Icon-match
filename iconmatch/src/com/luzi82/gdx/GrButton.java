package com.luzi82.gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GrButton extends GrElement implements GrElementListener {

	public GrButton() {
		super(true, true);
	}

	@Override
	public void draw(SpriteBatch aSpriteBatch) {
		GrElement currentElement = getCurrentElement();
		if (currentElement == null)
			return;
		currentElement.draw(aSpriteBatch);
	}

	@Override
	public void doUpdate() {
		GrElement currentElement = getCurrentElement();
		if (currentElement == null)
			return;
		currentElement.update();
	}

	int mButtonId = -1;

	public void setButtonId(int aButtonId) {
		mButtonId = aButtonId;
	}

	Rectangle mRect;

	public void setRect(Rectangle aRect) {
		mRect = aRect;
	}

	GrElement mOutElement;
	GrElement mOverElement;
	GrElement mDownElement;

	public void setOutElement(GrElement aElement) {
		if (mOutElement != null) {
			mOutElement.setListener(null);
			mOutElement.dispose();
		}
		mOutElement = aElement;
		mOutElement.setListener(this);
		markDirty();
	}

	public void setOverElement(GrElement aElement) {
		if (mOverElement != null) {
			mOverElement.setListener(null);
			mOverElement.dispose();
		}
		mOverElement = aElement;
		mOverElement.setListener(this);
		markDirty();
	}

	public void setDownElement(GrElement aElement) {
		if (mDownElement != null) {
			mDownElement.setListener(null);
			mDownElement.dispose();
		}
		mDownElement = aElement;
		mDownElement.setListener(this);
		markDirty();
	}

	public GrElement getCurrentElement() {
		return mStateEngine.getCurrentElement();
	}

	// State mState = State.OUT;
	//
	public void setStateEngine(StateEngine aStateEngine) {
		mStateEngine = aStateEngine;
		markDirty();
	}

	@Override
	public void onElementDirty(GrElement aElement) {
		markDirty();
	}

	private abstract class StateEngine {
		public abstract GrElement getCurrentElement();

		public abstract void touchDown(int x, int y);

		public abstract void touchUp(int x, int y);

		public abstract void touchDragged(int x, int y);

		public abstract void mouseMoved(int x, int y);
	}

	StateEngine mStateEngine = new OutState();

	private class OutState extends StateEngine {

		@Override
		public GrElement getCurrentElement() {
			return mOutElement;
		}

		@Override
		public void touchDown(int x, int y) {
			if (mRect.contains(x, y)) {
				setStateEngine(new DownState());
			}
		}

		@Override
		public void touchUp(int x, int y) {
			// none
		}

		@Override
		public void touchDragged(int x, int y) {
			// none
		}

		@Override
		public void mouseMoved(int x, int y) {
			if (mRect.contains(x, y)) {
				setStateEngine(new OverState());
			}
		}

	}

	private class OverState extends StateEngine {

		@Override
		public GrElement getCurrentElement() {
			return mOverElement;
		}

		@Override
		public void touchDown(int x, int y) {
			if (mRect.contains(x, y)) {
				setStateEngine(new DownState());
			}
		}

		@Override
		public void touchUp(int x, int y) {
			// none
		}

		@Override
		public void touchDragged(int x, int y) {
			if (!mRect.contains(x, y)) {
				setStateEngine(new OutState());
			}
		}

		@Override
		public void mouseMoved(int x, int y) {
			if (!mRect.contains(x, y)) {
				setStateEngine(new OutState());
			}
		}

	}

	private class DownState extends StateEngine {

		@Override
		public GrElement getCurrentElement() {
			return mDownElement;
		}

		@Override
		public void touchDown(int x, int y) {
			if (!mRect.contains(x, y)) {
				setStateEngine(new OutState());
			}
		}

		@Override
		public void touchUp(int x, int y) {
			if (mRect.contains(x, y)) {
				setStateEngine(new OverState());
				GrElementListener listener = getListener();
				if (listener != null) {
					listener.onClick(mButtonId);
				}
			} else {
				setStateEngine(new OutState());
			}
		}

		@Override
		public void touchDragged(int x, int y) {
			if (!mRect.contains(x, y)) {
				setStateEngine(new OutState());
			}
		}

		@Override
		public void mouseMoved(int x, int y) {
			if (!mRect.contains(x, y)) {
				setStateEngine(new OutState());
			}
		}

	}

	@Override
	public void touchDown(int x, int y, int pointer, int button, long aTime) {
		if ((pointer != 0) || (button != Input.Buttons.LEFT))
			return;
		mStateEngine.touchDown(x, y);
	}

	@Override
	public void touchUp(int x, int y, int pointer, int button, long aTime) {
		if ((pointer != 0) || (button != Input.Buttons.LEFT))
			return;
		mStateEngine.touchUp(x, y);
	}

	@Override
	public void touchDragged(int x, int y, int pointer, long aTime) {
		if (pointer != 0)
			return;
		mStateEngine.touchDragged(x, y);
	}

	@Override
	public void mouseMoved(int x, int y, long aTime) {
		mStateEngine.mouseMoved(x, y);
	}

	@Override
	public void onClick(int aButtonId) {
		// ignore
	}

}
