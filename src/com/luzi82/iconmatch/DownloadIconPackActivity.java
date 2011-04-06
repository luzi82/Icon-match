package com.luzi82.iconmatch;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.luzi82.iconmatch.FileDownload.State;

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

	@SuppressWarnings("unchecked")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		SimpleAdapter sa = (SimpleAdapter) getListAdapter();
		Map<String, String> mss = (Map<String, String>) sa.getItem(position);

		String url = mss.get("filename");
		String filename = IconMatch.getIconPackSaveFilename(mss.get("id"));

		FileDownload fd = new FileDownload(url, filename);
		fd.run();

		Toast.makeText(
				this,
				(fd.state == State.SUCCESS) ? R.string.download_success
						: R.string.download_fail, Toast.LENGTH_SHORT).show();
		// Log.d("IconMatch", fd.state.toString());
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
