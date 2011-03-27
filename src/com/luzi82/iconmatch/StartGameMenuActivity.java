package com.luzi82.iconmatch;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.ListView;

public class StartGameMenuActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.startgamemenu);
		// initList();
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
