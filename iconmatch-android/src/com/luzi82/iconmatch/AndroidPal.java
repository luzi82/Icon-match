package com.luzi82.iconmatch;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.luzi82.gdx.GrPal;

public class AndroidPal implements GrPal {

	public TextBlock createText(String text, int align, float fontSize) {

		int fontSizePx = getMaxFontSizePt(fontSize);
		int[] limit = getFontLimit(fontSizePx);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(fontSizePx);
		paint.setTypeface(Typeface.DEFAULT);
		paint.setHinting(Paint.HINTING_OFF);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);

		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);

		int x0int = Math.min(0, (int) Math.floor(rect.left));
		int x1int = Math.max(0, (int) Math.ceil(rect.right));

		int txtW = x1int - x0int;
		int txtH = limit[1] - limit[0];

		int imgW = 1;
		while (imgW < txtW) {
			imgW <<= 1;
		}
		int imgH = 1;
		while (imgH < txtH) {
			imgH <<= 1;
		}

		Bitmap bmp = Bitmap.createBitmap(imgW, imgH, Config.ALPHA_8);
		Canvas canvas = new Canvas(bmp);
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		canvas.drawText(text, -x0int, -limit[0], paint);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 0, baos);
		// byte[] buf = baos.toByteArray();
		// ByteBuffer bb = ByteBuffer.allocate(imgW * imgH);
		// bmp.copyPixelsToBuffer(bb);

		TextBlock textBlock = new TextBlock();

		// textBlock.mWidth = imgW;
		// textBlock.mHeight = imgH;
		textBlock.mData = baos.toByteArray();

		// Pixmap p = new Pixmap(buf, 0, buf.length);
		// Texture texture = new Texture(p);
		// textBlock.mTextureRegion = new TextureRegion(texture, 0, txtH, txtW,
		// -txtH);

		switch ((align - 1) % 3) {
		case 0: {
			textBlock.mOffsetX = x0int;
			break;
		}
		case 1: {
			textBlock.mOffsetX = -txtW / 2;
			break;
		}
		case 2: {
			textBlock.mOffsetX = -txtW;
			break;
		}
		}

		switch ((align - 1) / 3) {
		case 0: {
			textBlock.mOffsetY = -txtH;
			break;
		}
		case 1: {
			textBlock.mOffsetY = -(txtH / 2);
			break;
		}
		case 2: {
			textBlock.mOffsetY = 0;
			break;
		}
		}

		return textBlock;
	}

	public static int getMaxFontSizePt(float fontSize) {
		// find font size in px
		int fontSizePx = 1; // out

		while (true) {
			int[] limit = getFontLimit(fontSizePx);

			if ((limit[1] - limit[0]) > fontSize) {
				return fontSizePx - 1;
			}
			++fontSizePx;
		}
	}

	public static int[] getFontLimit(float fontSizePx) {
		int top = 0; // negative
		int bottom = 0; // positive

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(fontSizePx);
		paint.setTypeface(Typeface.DEFAULT);
		paint.setHinting(Paint.HINTING_OFF);
		paint.setStyle(Paint.Style.FILL);

		FontMetrics fm = paint.getFontMetrics();

		top = Math.min(top, (int) Math.floor(fm.top));
		top = Math.min(top, (int) Math.floor(fm.ascent));
		bottom = Math.max(bottom, (int) Math.ceil(fm.bottom));
		bottom = Math.max(bottom, (int) Math.ceil(fm.descent));

		return new int[] { top, bottom };
	}

}
