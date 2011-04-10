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
import android.preference.Preference.OnPreferenceChangeListener;

public class StartGameMenuActivity extends PreferenceActivity {

	String[] mEntries;
	String[] mEntriesValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.startgamemenu);
	}

	@Override
	protected void onResume() {
		super.onResume();

		List<String> packEntriesList = new LinkedList<String>();
		List<String> packEntriesValueList = new LinkedList<String>();

		List<IconPackEntry> iconPackEntryList = IconPackEntry
				.listDisk(getAppVer());

		for (IconPackEntry ipe : iconPackEntryList) {
			packEntriesList.add(ipe.title);
			packEntriesValueList.add(ipe.filename);
		}

		mEntries = packEntriesList.toArray(new String[0]);
		mEntriesValue = packEntriesValueList.toArray(new String[0]);

		ListPreference packPreference = (ListPreference) findPreference("startgamemenu_pack");
		packPreference
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						updateIconPreferenceSummury((String) newValue);
						return true;
					}
				});
		packPreference.setEntries(mEntries);
		packPreference.setEntryValues(mEntriesValue);
		if (!iconPackEntryList.isEmpty()) {
			if ((packPreference.getValue() == null)
					|| (!packEntriesValueList.contains(packPreference
							.getValue()))) {
				packPreference.setValue(packEntriesValueList.get(0));
			}
		} else {
			packPreference.setEnabled(false);
			Preference startPreference = findPreference("startgamemenu_start");
			startPreference.setEnabled(false);
		}
		updateIconPreferenceSummury(packPreference.getValue());
	}

	protected void updateIconPreferenceSummury(String newValue) {
		int i;
		for (i = 0; i < mEntriesValue.length; ++i) {
			if (mEntriesValue[i].equals(newValue)) {
				break;
			}
		}
		if (i >= mEntriesValue.length)
			return;
		ListPreference packPreference = (ListPreference) findPreference("startgamemenu_pack");
		packPreference.setSummary(mEntries[i]);
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

	@Override
	public void onContentChanged() {
		super.onContentChanged();

		IconMatch.logd("StartGameMenuActivity.onContentChanged");
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
