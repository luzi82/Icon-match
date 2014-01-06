package com.luzi82.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GrView {

	protected final int WIDTH;
	protected final int HEIGHT;

	// public OrthographicCamera mCamera;
	// public SpriteBatch mBatch;
	public float clearR = 0.5f;
	public float clearG = 0.5f;
	public float clearB = 0.5f;
	public float clearA = 0.5f;

	private static final int[] itmCount = { 0 };

	public Stage mStage;

	public GrView(int aWidth, int aHeight) {
		this.WIDTH = aWidth;
		this.HEIGHT = aHeight;
		System.err.println("GrRender.WIDTH = " + this.WIDTH);
		System.err.println("GrRender.HEIGHT = " + this.HEIGHT);
		synchronized (itmCount) {
			++itmCount[0];
			System.err.println("GrRender.itmCount " + itmCount[0]);
		}

		// mCamera = new OrthographicCamera();
		// mCamera.setToOrtho(false, WIDTH, HEIGHT);
		// mCamera.update();
		// mBatch = new SpriteBatch();

		mStage = new Stage(WIDTH, HEIGHT);
//		mStage.addListener(this);
//		mStage.addCaptureListener(this);
	}

	public void render(float aDelta) {
		// updateDirtyElement();
		mStage.act();

		Gdx.gl.glClearColor(clearR, clearG, clearB, clearA);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// mBatch.setProjectionMatrix(mCamera.combined);
		// mBatch.begin();
		// for (GrElement w : iDrawWidgetList) {
		// w.draw(mBatch);
		// }
		// mBatch.end();

		mStage.draw();
	}

	// LinkedList<GrElement> iDirtyElement = new LinkedList<GrElement>();
	//
	// public void onElementDirty(GrElement aElement) {
	// iDirtyElement.add(aElement);
	// }
	//
	// public void updateDirtyElement() {
	// for (GrElement element : iDirtyElement) {
	// element.update();
	// }
	// iDirtyElement.clear();
	// }
	//
	// public void addElement(GrElement aWidget) {
	// mWidgetList.add(aWidget);
	// if (aWidget.mIsDrawEnabled) {
	// iDrawWidgetList.add(aWidget);
	// }
	// if (aWidget.mIsTouchEnabled) {
	// iTouchWidgetList.addLast(aWidget);
	//
	// }
	// aWidget.setListener(this);
	// }

	public void touchDown(int x, int y, int pointer, int button, long aTime) {
		mStage.touchDown(x, y, pointer, button);
	}

	public void touchUp(int x, int y, int pointer, int button, long aTime) {
		mStage.touchUp(x, y, pointer, button);
		// for (GrElement e : iTouchWidgetList) {
		// e.touchUp(x, HEIGHT - y, pointer, button, aTime);
		// }
	}

	public void touchDragged(int x, int y, int pointer, long aTime) {
		mStage.touchDragged(x, y, pointer);
		// for (GrElement e : iTouchWidgetList) {
		// e.touchDragged(x, HEIGHT - y, pointer, aTime);
		// }
	}

	public void mouseMoved(int x, int y, long aTime) {
		mStage.mouseMoved(x, y);
		// for (GrElement e : iTouchWidgetList) {
		// e.mouseMoved(x, HEIGHT - y, aTime);
		// }
	}

	public void dispose() {
		synchronized (itmCount) {
			--itmCount[0];
			System.err.println("GrRender.itmCount " + itmCount[0]);
		}
		GrDeepDispose.disposeMember(this, Object.class);
	}

//	public interface Listener {
		// public void onClick(int aButtonId);
//	}

//	WeakReference<Listener> iListener;
//
//	public void setListener(Listener aListener) {
//		iListener = new WeakReference<Listener>(aListener);
//	}
//	
//	public Listener getListener(){
//		if (iListener == null)
//			return null;
//		return iListener.get();
//	}
//
//	@Override
//	public boolean handle(Event event) {
//		return getListener().handle(event);
//	}

	// public void onClick(int aButtonId) {
	// if (iListener == null)
	// return;
	// Listener l = iListener.get();
	// if (l == null)
	// return;
	// l.onClick(aButtonId);
	// }

}
