package com.luzi82.iconmatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.luzi82.gdx.GrDrawableUtils;
import com.luzi82.gdx.GrActorUtils;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrScreen;
import com.luzi82.gdx.GrView;
import com.luzi82.gdx.GrRectUtils;
import com.luzi82.homuvalue.Value.Variable;

public class TargetScreen extends GrScreen {

	public TargetScreen(GrGame aParent) {
		super(aParent);
	}

	@Override
	protected GrView createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	class Render extends GrView {

		public Pixmap mWhite1Pixmap;
		public Texture mWhite1Tex;

		public BitmapFont mFont;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			final IconMatchGame game = (IconMatchGame) iParent;

			mWhite1Pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			mWhite1Pixmap.setColor(1f, 1f, 1f, 1f);
			mWhite1Pixmap.fill();
			mWhite1Tex = new Texture(mWhite1Pixmap);

			float minSide = Math.min(WIDTH, HEIGHT);
			float minPhi1 = minSide / PHI;
			float minPhi2 = minPhi1 / PHI;
			float minPhi3 = minPhi2 / PHI;

			float btnW = minPhi1;
			float btnH = minPhi2;
			float fontSize = minPhi3;

			FreeTypeFontGenerator g = new FreeTypeFontGenerator(Gdx.files.internal("data/Roboto-Regular.ttf"));
			mFont = g.generateFont((int) Math.floor(minPhi3), "0123456789", false);
			g.dispose();

			Label.LabelStyle ls = new LabelStyle();
			ls.font = mFont;
			ls.fontColor = Color.WHITE;

			ButtonStyle bs;
			Rectangle rect;

			Rectangle labelRect = GrRectUtils.createRect(0, (HEIGHT - fontSize - btnH) / PHI + btnH, WIDTH, fontSize, 1);
			final Label label = new Label("", ls);
			label.setAlignment(Align.center);
			GrActorUtils.setBound(label, labelRect);
			connect(label, game.mItemCount);
			mStage.addActor(label);

			rect = GrRectUtils.createRect(WIDTH / PHI / PHI / 2, labelRect.y, fontSize, fontSize, 2);
			bs = new ButtonStyle();
			bs.up = GrDrawableUtils.createDrawable(mWhite1Tex, new Color(0xff0000ff));
			bs.down = GrDrawableUtils.createDrawable(mWhite1Tex, new Color(0xff7f7fff));
			final Button minusButton = new Button(bs);
			GrActorUtils.setBound(minusButton, rect);
			mStage.addActor(minusButton);
			minusButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.mItemCount.set(game.mItemCount.get() - 1);
				}
			});

			rect = GrRectUtils.createRect(WIDTH * PHI / 2, labelRect.y, fontSize, fontSize, 2);
			bs = new ButtonStyle();
			bs.up = GrDrawableUtils.createDrawable(mWhite1Tex, new Color(0x00ff00ff));
			bs.down = GrDrawableUtils.createDrawable(mWhite1Tex, new Color(0x7fff7fff));
			final Button addButton = new Button(bs);
			GrActorUtils.setBound(addButton, rect);
			mStage.addActor(addButton);
			addButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.mItemCount.set(game.mItemCount.get() + 1);
				}
			});

			bs = new ButtonStyle();
			bs.up = GrDrawableUtils.createDrawable(mWhite1Tex, new Color(0x0000ffff));
			bs.down = GrDrawableUtils.createDrawable(mWhite1Tex, new Color(0x7f7fffff));
			final Button nextButton = new Button(bs);
			GrActorUtils.setBound(nextButton, GrRectUtils.createRect(WIDTH / 2, (HEIGHT - fontSize - btnH) / PHI / PHI, btnW, btnH, 2));
			mStage.addActor(nextButton);
			nextButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					iParent.setScreen(new GameScreen(iParent, game.mItemCount.get()));
					// GameLogic.toFactor(mItemCount.get());
				}
			});
		}

		// public Rectangle mPlayBtnRect;
		//
		// public Texture mTexture;
		//
		// public GrImage mTitleImg;
		//
		// public Render(int aWidth, int aHeight) {
		// super(aWidth, aHeight);
		//
		// int minSide = Math.min(aWidth, aHeight);
		// int btnSize = Math.round(minSide / PHI);
		// mPlayBtnRect = new Rectangle();
		// mPlayBtnRect.setSize(btnSize);
		// mPlayBtnRect.setCenter(aWidth / 2, aHeight / 2);
		//
		// mTexture = new Texture(Gdx.files.internal("data/libgdx.png"));
		// mTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//
		// TextureRegion region = new TextureRegion(mTexture, 0, 0, 512, 275);
		//
		// mTitleImg = new GrImage();
		// mTitleImg.setRect(mPlayBtnRect);
		// mTitleImg.setTextureRegion(region);
		// addElement(mTitleImg);
		//
		// }

	}

	enum BtnIdx {
		INCREASE, DECREASE, OK
	}

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;

	// @Override
	// public void onClick(int aButtonId) {
	// switch (BtnIdx.values()[aButtonId]) {
	// case INCREASE:
	// break;
	// }
	// }

	// @Override
	// public boolean handle(Event event) {
	// // TODO Auto-generated method stub
	// return false;
	// }
}
