package com.luzi82.gdx;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GrView2D extends GrView implements GrElementListener {

	public OrthographicCamera mCamera;
	public SpriteBatch mBatch;
	public float clearR = 0.5f;
	public float clearG = 0.5f;
	public float clearB = 0.5f;
	public float clearA = 0.5f;

	// public Iterable<Sprite> mSpriteIterable;
	// public Iterable<DetectUnit> mDetectUnitIterable;
	public LinkedList<GrElement> mWidgetList = new LinkedList<GrElement>();
	public LinkedList<GrElement> iDrawWidgetList = new LinkedList<GrElement>();
	public LinkedList<GrElement> iTouchWidgetList = new LinkedList<GrElement>();

	public GrView2D(int aWidth, int aHeight) {
		super(aWidth, aHeight);

		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(true, WIDTH, HEIGHT);
		mCamera.update();
		mBatch = new SpriteBatch();
	}

	@Override
	public void render(float aDelta) {
		updateDirtyElement();

		Gdx.gl.glClearColor(clearR, clearG, clearB, clearA);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		for (GrElement w : iDrawWidgetList) {
			w.draw(mBatch);
		}
		mBatch.end();
	}

	public int detect(int aX, int aY) {
		// for (DetectUnit du : mDetectUnitIterable) {
		// if (du.mRect.contains(aX, aY)) {
		// return du.mValue;
		// }
		// }
		return -1;
	}

	public class DetectUnit {
		Rectangle mRect;
		int mValue;
	}

	LinkedList<GrElement> iDirtyElement = new LinkedList<GrElement>();

	public void onElementDirty(GrElement aElement) {
		iDirtyElement.add(aElement);
	}

	public void updateDirtyElement() {
		for (GrElement element : iDirtyElement) {
			element.update();
		}
		iDirtyElement.clear();
	}

	public void addElement(GrElement aWidget) {
		mWidgetList.add(aWidget);
		if (aWidget.mIsDrawEnabled) {
			iDrawWidgetList.add(aWidget);
		}
		if (aWidget.mIsTouchEnabled) {
			iTouchWidgetList.addLast(aWidget);

		}
		aWidget.setListener(this);
	}

	public void touchDown(int x, int y, int pointer, int button, long aTime) {
		for (GrElement e : iTouchWidgetList) {
			e.touchDown(x, y, pointer, button, aTime);
		}
	}

	public void touchUp(int x, int y, int pointer, int button, long aTime) {
		for (GrElement e : iTouchWidgetList) {
			e.touchUp(x, y, pointer, button, aTime);
		}
	}

	public void touchDragged(int x, int y, int pointer, long aTime) {
		for (GrElement e : iTouchWidgetList) {
			e.touchDragged(x, y, pointer, aTime);
		}
	}

	public void mouseMoved(int x, int y, long aTime) {
		for (GrElement e : iTouchWidgetList) {
			e.mouseMoved(x, y, aTime);
		}
	}

}
