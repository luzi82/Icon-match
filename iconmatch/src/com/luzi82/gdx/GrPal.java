package com.luzi82.gdx;


public interface GrPal {
	
	public class TextBlock{
		public byte[] mData;
//		public int mWidth;
//		public int mHeight;
		public float mOffsetX;
		public float mOffsetY;
	}

	public TextBlock createText(String text, int align, float fontSize);
	
}
