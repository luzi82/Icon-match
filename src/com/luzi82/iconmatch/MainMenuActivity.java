package com.luzi82.iconmatch;

import java.util.List;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class MainMenuActivity extends PreferenceActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.mainmenu);
	}

	@Override
	protected void onResume() {
		super.onResume();

		List<IconPackEntry> iconPackEntryList = IconPackEntry.listDisk(getAppVer());
		if (iconPackEntryList.isEmpty()) {
			Preference playPreference = findPreference("mainmenu_play");
			playPreference.setEnabled(false);
		}
	}

	int getAppVer() {
		PackageManager pm = getPackageManager();
		String pn = getPackageName();
		try {
			PackageInfo pi = pm.getPackageInfo(pn, 0);
			return pi.versionCode;
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
			finish();
			return -1;
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.main_default, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.menu_item_about: {
	// Intent intent = new Intent(this, AboutActivity.class);
	// startActivity(intent);
	// return true;
	// }
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }

}
