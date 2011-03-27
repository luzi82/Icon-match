package com.luzi82.iconmatch;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MainMenuActivity extends PreferenceActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		addPreferencesFromResource(R.xml.mainmenu);
    }
    
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main_default, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.menu_item_about: {
//			Intent intent = new Intent(this, AboutActivity.class);
//			startActivity(intent);
//			return true;
//		}
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
	
}
