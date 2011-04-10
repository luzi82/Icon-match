package com.luzi82.iconmatch;

import java.io.IOException;
import java.util.zip.ZipException;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CheatSheetActivity extends ListActivity {

	IconPack mIconPack;

	String mFilename;

	// List<Map<String, ?>> mList = new LinkedList<Map<String, ?>>();

	int mIconWidth;

	int mIconHeight;

	MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFilename = getIntent().getStringExtra("filename");
		// mAdapter = new SimpleAdapter(this, mList,
		// R.layout.cheatsheetlist_entry, new String[] { "center",
		// "selection" }, new int[] { R.id.ImageView01,
		// R.id.ImageView02 });
		mAdapter = new MyAdapter();
		setListAdapter(mAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			updateIconSize();
			updateIconPack();
			// updateList();
			mAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
			finish();
		}
	}

	private void updateIconSize() {
		Display display = getWindowManager().getDefaultDisplay();
		mIconWidth = display.getWidth();
		mIconHeight = display.getHeight();
		mIconWidth = Math.min(mIconWidth, mIconHeight);
		mIconWidth = mIconWidth * 2 / 5;
		mIconHeight = mIconWidth;
	}

	private void updateIconPack() throws ZipException, IOException {
		if ((mIconPack == null) || (mIconPack.mWidth != mIconWidth)
				|| (mIconPack.mHeight != mIconHeight)) {
			mIconPack = IconPack.load(mFilename, mIconWidth, mIconHeight);
		}
	}

	// private void updateList() {
	// mList.clear();
	// for (int i = 0; i < mIconPack.mSelectionSize; ++i) {
	// HashMap<String, Bitmap> map = new HashMap<String, Bitmap>();
	// map.put("center", mIconPack.mCenterBitmap[i]);
	// map.put("selection", mIconPack.mSelectionBitmap[i]);
	// mList.add(map);
	// }
	// }

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mIconPack == null) {
				return 0;
			} else {
				return mIconPack.mSelectionSize;
			}
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(CheatSheetActivity.this)
						.inflate(R.layout.cheatsheetlist_entry, parent, false);
				// convertView = View.inflate(CheatSheetActivity.this,
				// R.layout.cheatsheetlist_entry, parent);
			}
			ImageView centerImage = (ImageView) convertView
					.findViewById(R.id.ImageView01);
			centerImage.setImageBitmap(mIconPack.mCenterBitmap[position]);
			ImageView selectionImage = (ImageView) convertView
					.findViewById(R.id.ImageView02);
			selectionImage.setImageBitmap(mIconPack.mSelectionBitmap[position]);
			return convertView;
		}
	}

}
