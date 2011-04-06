package com.luzi82.iconmatch;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class DownloadIconPackActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<IconPackEntry> onlineIconPackList = IconPackEntry
				.listOnline("http://www.luzi82.com/~luzi82/iconmatch/data.php",
						getAppVer());

		if (onlineIconPackList != null) {
			List<Map<String, String>> lmss = new LinkedList<Map<String, String>>();
			for (IconPackEntry ipe : onlineIconPackList) {
				lmss.add(ipe.toMap());
			}

			setListAdapter(new SimpleAdapter(this, lmss,
					android.R.layout.simple_list_item_1,
					new String[] { "title" }, new int[] { android.R.id.text1 }));
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

}
