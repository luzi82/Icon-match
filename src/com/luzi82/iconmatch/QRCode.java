package com.luzi82.iconmatch;

import android.app.Activity;
import android.os.Bundle;

public class QRCode extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Be sure to call the super class.
		super.onCreate(savedInstanceState);

		// See assets/res/any/layout/dialog_activity.xml for this
		// view layout definition, which is being set here as
		// the content of our screen.
		setTitle(R.string.qrcode_title);
		setContentView(R.layout.qrcode);
	}

}
