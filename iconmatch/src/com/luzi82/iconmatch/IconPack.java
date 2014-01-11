package com.luzi82.iconmatch;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Disposable;
import com.luzi82.gdx.GrDeepDispose;

public class IconPack implements Disposable {

	// public Bitmap[] mCenterBitmap;
	public Pixmap[] mCenterBitmap;

	// public Bitmap[] mSelectionBitmap;
	public Pixmap[] mSelectionBitmap;

	public int mSelectionSize;

	public static IconPack load(FileHandle fh) throws IOException {
		LinkedList<Pixmap> aBitmapList = new LinkedList<Pixmap>();
		LinkedList<Pixmap> bBitmapList = new LinkedList<Pixmap>();

		ZipEntry[] zeAry = ZipUtils.getZipEntrieAry(fh.read());
		Map<String, ZipEntry> zeMap = ZipUtils.toZeMap(zeAry);
		for (ZipEntry zea : zeAry) {
			String filename = zea.getName();
			if (!filename.endsWith(".a.png"))
				continue;
			String prefix = filename.substring(0, filename.length() - 6);
			String bFilename = prefix + ".b.png";
			ZipEntry zeb = zeMap.get(bFilename);
			if (zeb == null) {
				continue;
			}
			byte[] data;
			data = ZipUtils.getContent(fh.read(), filename);
			aBitmapList.add(new Pixmap(data, 0, data.length));
			data = ZipUtils.getContent(fh.read(), bFilename);
			bBitmapList.add(new Pixmap(data, 0, data.length));
		}

		IconPack ret = new IconPack();

		ret.mCenterBitmap = aBitmapList.toArray(new Pixmap[0]);
		ret.mSelectionBitmap = bBitmapList.toArray(new Pixmap[0]);
		ret.mSelectionSize = aBitmapList.size();

		return ret;
	}

	// public static IconPack load(String filename) throws ZipException,
	// IOException {
	// IconPackEntry ipe = IconPackEntry.get(filename);
	// return load(ipe);
	// }
	//
	// public static IconPack load(File file) throws ZipException, IOException {
	// IconPackEntry ipe = IconPackEntry.loadZipEntry(file);
	// return load(ipe);
	// }

	// public static IconPack load(IconPackEntry entry) throws ZipException,
	// IOException {
	// LinkedList<Pixmap> aBitmapList = new LinkedList<Pixmap>();
	// LinkedList<Pixmap> bBitmapList = new LinkedList<Pixmap>();
	//
	// if (entry.type == IconPackEntry.Type.ZIP) {
	// ZipFile zipFile = new ZipFile(entry.file);
	// Enumeration<? extends ZipEntry> zee = zipFile.entries();
	// while (zee.hasMoreElements()) {
	// ZipEntry zea = zee.nextElement();
	// String filename = zea.getName();
	// if (!zea.getName().endsWith(".a.png"))
	// continue;
	// String prefix = filename.substring(0, filename.length() - 6);
	// String bFilename = prefix + ".b.png";
	// ZipEntry zeb = zipFile.getEntry(bFilename);
	// if (zeb == null) {
	// continue;
	// }
	// byte[] data;
	// data = dump(zipFile.getInputStream(zea));
	// aBitmapList.add(new Pixmap(data, 0, data.length));
	// data = dump(zipFile.getInputStream(zeb));
	// bBitmapList.add(new Pixmap(data, 0, data.length));
	// // Bitmap aBitmap =
	// // BitmapFactory.decodeStream(zipFile.getInputStream(zea));
	// // aBitmap = Util.resize(aBitmap, width, height);
	// // aBitmapList.add(aBitmap);
	// // Bitmap bBitmap =
	// // BitmapFactory.decodeStream(zipFile.getInputStream(zeb));
	// // bBitmap = Util.resize(bBitmap, width, height);
	// // bBitmapList.add(bBitmap);
	// }
	// zipFile.close();
	// }
	//
	// IconPack ret = new IconPack();
	//
	// ret.mCenterBitmap = aBitmapList.toArray(new Pixmap[0]);
	// ret.mSelectionBitmap = bBitmapList.toArray(new Pixmap[0]);
	// ret.mSelectionSize = aBitmapList.size();
	//
	// return ret;
	// }

	@Override
	public void dispose() {
		GrDeepDispose.disposeMember(this, super.getClass());
	}

}
