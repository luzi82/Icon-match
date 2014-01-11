package com.luzi82.gdx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

	public static ZipEntry[] getZipEntrieAry(InputStream is) throws IOException {
		LinkedList<ZipEntry> zList = new LinkedList<ZipEntry>();
		ZipInputStream zis = new ZipInputStream(is);
		while (true) {
			ZipEntry ze = zis.getNextEntry();
			if (ze == null)
				break;
			zList.add(ze);
		}
		zis.close();
		return zList.toArray(new ZipEntry[0]);
	}

	public static Map<String, ZipEntry> toZeMap(ZipEntry[] zeAry) {
		HashMap<String, ZipEntry> ret = new HashMap<String, ZipEntry>();
		for (ZipEntry ze : zeAry) {
			ret.put(ze.getName(), ze);
		}
		return ret;
	}

	public static byte[] getContent(InputStream is, String zeName) throws IOException {
		ZipInputStream zis = new ZipInputStream(is);
		byte[] ret = null;
		ZipEntry ze = null;
		while (true) {
			ZipEntry zee = zis.getNextEntry();
			if (zee == null)
				break;
			if (zee.getName().equals(zeName)) {
				ze = zee;
				break;
			}
		}
		if (ze != null) {
			ret = dump(zis);
		}
		zis.close();
		return ret;
	}

	public static byte[] dump(InputStream is) throws IOException {
		byte[] b = new byte[4096];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (true) {
			int len = is.read(b);
			if (len < 0)
				break;
			baos.write(b, 0, len);
		}
		return baos.toByteArray();
	}

}
