package com.luzi82.iconmatch;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.luzi82.gdx.GrActorUtils;
import com.luzi82.gdx.GrGame;
import com.luzi82.gdx.GrScreen;
import com.luzi82.gdx.GrView;
import com.luzi82.gdx.GrRectUtils;
import com.luzi82.gdx.GrRectUtils.Point;
import com.luzi82.gdx.GrRectUtils.Size;

public class GameScreen extends GrScreen {

	public IconPack mIconPack;
	public GameLogic mLogic;
	public long mTimeStamp;

	public boolean mEndProcessed = false;

	protected GameScreen(GrGame aParent, int aTarget) {
		super(aParent);
		try {
			mIconPack = IconPack.load(Gdx.files.internal("data/icon.zip"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mLogic = new GameLogic(GameLogic.toFactor(aTarget), aParent.mRandom, mIconPack.mCenterBitmap.length, aTarget);
		mTimeStamp = System.currentTimeMillis();
	}

	@Override
	protected GrView createRender(int aWidth, int aHeight) {
		return new Render(aWidth, aHeight);
	}

	public static final int VIEW_BLOCK_MAX = ((int) Math.ceil(GameLogic.LIFE_MAX) + 2);

	class Render extends GrView {

		public Pixmap mWhite1Pixmap;
		public Texture mWhite1Tex;
		public TextureRegion mWhite1TexReg;

		public Button mLeftBtn;
		public Button mRightBtn;

		Rectangle mPlayGroundRect;
		public Group mPlayground;
		public Image[] mLeftImgAry = new Image[VIEW_BLOCK_MAX];
		public Image[] mCenterImgAry = new Image[VIEW_BLOCK_MAX];
		public Image[] mRightImgAry = new Image[VIEW_BLOCK_MAX];
		public float mUnitSize;

		public Texture[] mCenterTextureAry;
		public Texture[] mSelectionTextureAry;
		public Drawable[] mCenterDrawableAry;
		public Drawable[] mSelectionDrawableAry;

		public Render(int aWidth, int aHeight) {
			super(aWidth, aHeight);

			Rectangle screenRect = new Rectangle(0, 0, WIDTH, HEIGHT);
			Size screenSize = new Size(WIDTH, HEIGHT);
			Point screen5 = GrRectUtils.getPoint(screenRect, 5);

			mWhite1Pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			mWhite1Pixmap.setColor(1f, 1f, 1f, 1f);
			mWhite1Pixmap.fill();
			mWhite1Tex = new Texture(mWhite1Pixmap);
			mWhite1TexReg = new TextureRegion(mWhite1Tex);

			mCenterTextureAry = new Texture[mIconPack.mCenterBitmap.length];
			mSelectionTextureAry = new Texture[mIconPack.mSelectionBitmap.length];
			mCenterDrawableAry = new Drawable[mIconPack.mCenterBitmap.length];
			mSelectionDrawableAry = new Drawable[mIconPack.mCenterBitmap.length];
			for (int i = 0; i < mCenterTextureAry.length; ++i) {
				mCenterTextureAry[i] = new Texture(mIconPack.mCenterBitmap[i]);
				mSelectionTextureAry[i] = new Texture(mIconPack.mSelectionBitmap[i]);
				mCenterDrawableAry[i] = new TextureRegionDrawable(new TextureRegion(mCenterTextureAry[i]));
				mSelectionDrawableAry[i] = new TextureRegionDrawable(new TextureRegion(mSelectionTextureAry[i]));
			}

			mPlayground = new Group();
			Size playGroundSize = GrRectUtils.getInner(screenSize, 1 / PHI);
			mPlayGroundRect = GrRectUtils.createRect(screen5, playGroundSize, 5);
			mUnitSize = mPlayGroundRect.width / 3f;
			GrActorUtils.setBound(mPlayground, mPlayGroundRect);
			for (int i = 0; i < VIEW_BLOCK_MAX; ++i) {
				mLeftImgAry[i] = new Image();
				mCenterImgAry[i] = new Image();
				mRightImgAry[i] = new Image();
				mPlayground.addActor(mLeftImgAry[i]);
				mPlayground.addActor(mCenterImgAry[i]);
				mPlayground.addActor(mRightImgAry[i]);
			}
			mStage.addAction(new Action() {
				@Override
				public boolean act(float delta) {
					if (mLogic.mState != GameLogic.State.PLAY) {
						return false;
					}
					float y = mPlayGroundRect.height * mLogic.life(nowF()) / GameLogic.LIFE_MAX;
					int imgI;
					for (imgI = 0; imgI < VIEW_BLOCK_MAX; ++imgI) {
						if (imgI >= mLogic.mAnswer.size())
							break;
						GameLogic.Block block = mLogic.mAnswer.get(imgI);
						float x = 0;
						mLeftImgAry[imgI].setVisible(true);
						mLeftImgAry[imgI].setDrawable(mSelectionDrawableAry[block.left]);
						mLeftImgAry[imgI].setBounds(x, y, mUnitSize, mUnitSize);
						x += mUnitSize;
						mCenterImgAry[imgI].setVisible(true);
						mCenterImgAry[imgI].setDrawable(mCenterDrawableAry[block.center]);
						mCenterImgAry[imgI].setBounds(x, y, mUnitSize, mUnitSize);
						x += mUnitSize;
						mRightImgAry[imgI].setVisible(true);
						mRightImgAry[imgI].setDrawable(mSelectionDrawableAry[block.right]);
						mRightImgAry[imgI].setBounds(x, y, mUnitSize, mUnitSize);
						y += mUnitSize;
					}
					for (; imgI < VIEW_BLOCK_MAX; ++imgI) {
						mLeftImgAry[imgI].setVisible(false);
						mCenterImgAry[imgI].setVisible(false);
						mRightImgAry[imgI].setVisible(false);
					}
					return false;
				}
			});
			mStage.addActor(mPlayground);

			if (mPlayGroundRect.height < HEIGHT) {
				Image img = new Image(mWhite1TexReg);
				img.setBounds(0, 0, WIDTH, (HEIGHT - mPlayGroundRect.height) / 2);
				img.setColor(Color.BLACK);
				img.setZIndex(1);
				mStage.addActor(img);
				img = new Image(mWhite1TexReg);
				img.setBounds(0, (HEIGHT + mPlayGroundRect.height) / 2, WIDTH, (HEIGHT - mPlayGroundRect.height) / 2);
				img.setColor(Color.BLACK);
				img.setZIndex(1);
				mStage.addActor(img);
			} else {

			}

			mLeftBtn = new Button(new ButtonStyle());
			mLeftBtn.setBounds(0, 0, WIDTH / PHI / PHI, HEIGHT);
			mLeftBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					mLogic.kill(GameLogic.Side.LEFT);
				}
			});
			mStage.addActor(mLeftBtn);

			mRightBtn = new Button(new ButtonStyle());
			mRightBtn.setBounds(WIDTH / PHI, 0, WIDTH / PHI / PHI, HEIGHT);
			mRightBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					mLogic.kill(GameLogic.Side.RIGHT);
				}
			});
			mStage.addActor(mRightBtn);

			mStage.addAction(new Action() {
				@Override
				public boolean act(float delta) {
					if (!mEndProcessed) {
						mLogic.tick(nowF());
						if (mLogic.mState != GameLogic.State.PLAY) {
							Timer.instance().scheduleTask(new Timer.Task() {
								@Override
								public void run() {
									iParent.setScreen(new TargetScreen(iParent));
								}
							}, 1);
							mEndProcessed = true;
						}
					}
					return false;
				}
			});
		}
	}

	public float nowF() {
		return (float) (System.currentTimeMillis() - mTimeStamp) / 1000f;
	}

	public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;

}
