package com.luzi82.iconmatch;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class StartGameMenuActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.startgamemenu);
	}

	@Override
	protected void onResume() {
		super.onResume();

		List<IconPackEntry> iconPackEntryList = IconPackEntry.list(getAppVer());

		List<String> packEntriesList = new LinkedList<String>();
		List<String> packEntriesValueList = new LinkedList<String>();

		for (IconPackEntry ipe : iconPackEntryList) {
			packEntriesList.add(ipe.title);
			packEntriesValueList.add(ipe.filename);
		}

		ListPreference packPreference = (ListPreference) findPreference("startgamemenu_pack");
		packPreference.setEntries(packEntriesList.toArray(new String[0]));
		packPreference.setEntryValues(packEntriesValueList
				.toArray(new String[0]));
		if (!iconPackEntryList.isEmpty()) {
			packPreference.setDefaultValue(packEntriesValueList.get(0));
		} else {
			Preference startPreference = findPreference("startgamemenu_start");
			startPreference.setEnabled(false);
		}
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if (preference.getKey().equals("startgamemenu_start")) {
			ListPreference packPreference = (ListPreference) findPreference("startgamemenu_pack");
			String value = packPreference.getValue();

			Intent intent = new Intent(this, IconMatchGameActivity.class);
			intent.putExtra("filename", value);
			startActivity(intent);
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
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
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	// super.onListItemClick(l, v, position, id);
	// System.err.println(id);
	// if (id == R.id.startgamemenu_start) {
	// System.err.println("startgamemenu_start");
	// }
	// }

	// private void initList() {
	// List<String> idList = new ArrayList<String>();
	// idList.add("Color");
	//
	// ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
	// android.R.layout.simple_list_item_1, idList);
	// setListAdapter(aa);
	//
	// onContentChanged();
	// }

}
