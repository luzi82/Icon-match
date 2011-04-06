package com.luzi82.iconmatch;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class IconPackEntry {

	String title;

	String id;

	String ver;

	int appvermin = -1;

	int appvermax = -1;

	String filename; // absolute

	enum Type {
		DIR, ZIP
	}

	Type type;

	public static List<IconPackEntry> listDisk(int appver) {
		List<IconPackEntry> ret = new LinkedList<IconPackEntry>();
		File externalStorageDirectory = Environment
				.getExternalStorageDirectory();
		File appRootDirectory = new File(externalStorageDirectory, "IconMatch");
		File iconPackDirectory = new File(appRootDirectory, "iconpack");
		if (!iconPackDirectory.exists()) {
			return ret;
		}
		if (!iconPackDirectory.isDirectory()) {
			return ret;
		}
		String[] iconPackFilenameList = iconPackDirectory.list();
		for (String iconPackFilename : iconPackFilenameList) {
			IconPackEntry entry = get(iconPackDirectory.getAbsolutePath()
					+ File.separator + iconPackFilename);
			if (entry == null) {
				continue;
			}
			if (!entry.acceptAppVer(appver)) {
				continue;
			}
			ret.add(entry);
		}

		return ret;
	}

	public static IconPackEntry get(String filename) {
		if (filename == null) {
			throw new NullPointerException();
		}
		File iconPackFile = new File(filename);
		if (!iconPackFile.exists())
			return null;

		// zip file
		if ((!iconPackFile.isDirectory()) && (filename.endsWith(".zip"))) {
			return loadZipEntry(iconPackFile);
		}

		return null;
	}

	public static List<IconPackEntry> listOnline(String urlSpec, int appver) {
		List<IconPackEntry> ret = new LinkedList<IconPackEntry>();
		try {
			URL url = new URL(urlSpec);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = new BufferedInputStream(conn.getInputStream());

			DownloadXmlContentHandler dxch = new DownloadXmlContentHandler();
			Xml.parse(is, Xml.Encoding.UTF_8, dxch);

			for (IconPackEntry ipe : dxch.entryList) {
				if (!ipe.acceptAppVer(appver)) {
					continue;
				}
				ret.add(ipe);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}

	private static IconPackEntry loadZipEntry(File file) {
		try {
			ZipFile zf = new ZipFile(file);

			ZipEntry ze = zf.getEntry("data.xml");
			if (ze == null) {
				return null;
			}

			DataXmlContentHandler dxch = new DataXmlContentHandler();
			Xml.parse(zf.getInputStream(ze), Xml.Encoding.UTF_8, dxch);
			if (!dxch.success) {
				return null;
			}
			IconPackEntry entry = dxch.entry;

			entry.filename = file.getAbsolutePath();
			entry.type = Type.ZIP;

			return entry;
		} catch (ZipException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (SAXException e) {
			return null;
		}
	}

	private static class DataXmlContentHandler implements ContentHandler {

		boolean success = false;
		IconPackEntry entry = new IconPackEntry();

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
		}

		@Override
		public void endDocument() throws SAXException {
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
		}

		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
		}

		@Override
		public void processingInstruction(String target, String data)
				throws SAXException {
		}

		@Override
		public void setDocumentLocator(Locator locator) {
		}

		@Override
		public void skippedEntity(String name) throws SAXException {
		}

		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			if (localName.equals("IconMatchPack")) {
				entry.title = atts.getValue("", "title");
				entry.id = atts.getValue("", "id");
				entry.ver = atts.getValue("", "ver");
				entry.appvermin = parseInt(atts.getValue("", "appvermin"), -1);
				entry.appvermax = parseInt(atts.getValue("", "appvermax"), -1);
				success = true;
			}
		}

		@Override
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
		}

	}

	private static class DownloadXmlContentHandler implements ContentHandler {

		List<IconPackEntry> entryList = new LinkedList<IconPackEntry>();

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
		}

		@Override
		public void endDocument() throws SAXException {
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
		}

		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
		}

		@Override
		public void processingInstruction(String target, String data)
				throws SAXException {
		}

		@Override
		public void setDocumentLocator(Locator locator) {
		}

		@Override
		public void skippedEntity(String name) throws SAXException {
		}

		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			if (localName.equals("IconMatchPack")) {
				IconPackEntry entry = new IconPackEntry();
				entry.title = atts.getValue("", "title");
				entry.id = atts.getValue("", "id");
				entry.ver = atts.getValue("", "ver");
				entry.appvermin = parseInt(atts.getValue("", "appvermin"), -1);
				entry.appvermax = parseInt(atts.getValue("", "appvermax"), -1);
				entry.filename = atts.getValue("", "url");
				entry.type = Type.ZIP;
				entryList.add(entry);

				entry.trace();
			}
		}

		@Override
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
		}

	}

	static int parseInt(String s, int def) {
		if (s == null)
			return def;
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	void trace() {
		logd("title = " + title);
		logd("id = " + id);
		logd("ver = " + ver);
		logd("appvermin = " + appvermin);
		logd("appvermax = " + appvermax);
	}

	static void logd(String v) {
		Log.d("IconMatch", v);
	}

	boolean acceptAppVer(int appver) {
		if ((appvermin >= 0) && (appvermin > appver)) {
			return false;
		}
		if ((appvermax >= 0) && (appvermax < appver)) {
			return false;
		}
		return true;
	}

	Map<String, String> toMap() {
		TreeMap<String, String> ret = new TreeMap<String, String>();
		ret.put("title", title);
		ret.put("id", id);
		ret.put("ver", ver);
		ret.put("appvermin", Integer.toString(appvermin));
		ret.put("appvermax", Integer.toString(appvermax));
		ret.put("filename", filename);
		ret.put("type", type.toString());
		return ret;
	}

}
