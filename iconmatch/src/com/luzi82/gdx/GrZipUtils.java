package com.luzi82.gdx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class GrZipUtils {

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

	public static FileHandle unzipToTmpFileHandle(InputStream is, String zeName, String subfix) throws IOException {
		FileHandle ret = null;
		try {
			ZipInputStream zis = new ZipInputStream(is);
			// byte[] ret = null;
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
				File tmpFile = File.createTempFile(zeName, subfix);
				tmpFile.deleteOnExit();
				System.err.println(tmpFile.getAbsolutePath());
				ret = Gdx.files.absolute(tmpFile.getAbsolutePath());
				// ret = new FileHandle(tmpFile);
				// // ret = FileHandle.tempFile("grzip");
				ret.write(zis, false);
			}
			zis.close();
			return ret;
		} catch (IOException ioe) {
			if (ret != null) {
				ret.delete();
			}
			throw ioe;
		}
	}

}
