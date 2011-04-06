package com.luzi82.iconmatch;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownload implements Runnable {

	final String mUrlSpec;

	final String mFilename;

	enum State {
		INIT, BUSY, SUCCESS, FAIL
	}

	State state = State.INIT;

	public FileDownload(String mUrlSpec, String mFilename) {
		super();
		this.mUrlSpec = mUrlSpec;
		this.mFilename = mFilename;
	}

	@Override
	public void run() {
		try {
			state = State.BUSY;

			URL url = new URL(mUrlSpec);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			BufferedInputStream bis = new BufferedInputStream(huc
					.getInputStream());

			FileOutputStream fos = new FileOutputStream(mFilename);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			byte[] buf = new byte[65536];

			while (true) {
				int len = bis.read(buf);
				if (len == -1)
					break;
				bos.write(buf, 0, len);
			}

			bos.flush();
			bos.close();
			
			bis.close();

			state = State.SUCCESS;
		} catch (MalformedURLException e) {
			state = State.FAIL;
			e.printStackTrace();
		} catch (IOException e) {
			state = State.FAIL;
			e.printStackTrace();
		}
	}

}
