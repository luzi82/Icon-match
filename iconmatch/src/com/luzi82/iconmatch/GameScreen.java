package com.luzi82.iconmatch;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrScreen;
import com.luzi82.gdx.GrView;

public class GameScreen extends GrScreen {

	public GameLogic mLogic;
	public long mTimeStamp;

	protected GameScreen(GrGame aParent) {
		super(aParent);
		mLogic = new GameLogic(1);
		mTimeStamp = System.currentTimeMillis();
	}

	@Override
	protected GrView createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	class Render extends GrView {

		public Pixmap mWhite1Pixmap;
		public Texture mWhite1Tex;

		public Button mBtn;

		public Image mArea;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			mWhite1Pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			mWhite1Pixmap.setColor(1f, 1f, 1f, 1f);
			mWhite1Pixmap.fill();
			mWhite1Tex = new Texture(mWhite1Pixmap);

			mArea = new Image(mWhite1Tex);
			mArea.addAction(new Action() {
				@Override
				public boolean act(float delta) {
					float life = mLogic.life(nowF());
					float h = HEIGHT * (GameLogic.LIFE_MAX - life) / GameLogic.LIFE_MAX;
					mArea.setBounds(0, HEIGHT - h, WIDTH, h);
					return false;
				}
			});
			mStage.addActor(mArea);

			mBtn = new Button(new ButtonStyle());
			mBtn.setBounds(0, 0, WIDTH, HEIGHT);
			mBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					mLogic.kill();
				}
			});
			mStage.addActor(mBtn);

			mStage.addAction(new Action() {
				@Override
				public boolean act(float delta) {
					mLogic.tick(nowF());
					return false;
				}
			});
		}

	}

	public float nowF() {
		return (float) (System.currentTimeMillis() - mTimeStamp) / 1000f;
	}

}
