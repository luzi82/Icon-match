package com.luzi82.gdx;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.badlogic.gdx.utils.Disposable;

public class GrDeepDispose {

	public static void disposeMember(Object aObject,Class<?> aRootClass) {
//		 iLogger.debug("disposeMember");
		for (Class<?> c = aObject.getClass(); c != aRootClass; c = c.getSuperclass()) {
			Field[] fv = c.getDeclaredFields();
			for (Field f : fv) {
				if ((f.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) != 0)
					continue;
				String n = f.getName();
				f.setAccessible(true);
				if (n.startsWith("m")) {
					// iLogger.debug(n);
					try {
						Object o = f.get(aObject);
						deepDispose(o);
						if (!f.getType().isPrimitive())
							f.set(aObject, null);
					} catch (IllegalArgumentException e) {
						// iLogger.debug("", e);
					} catch (IllegalAccessException e) {
						// iLogger.debug("", e);
					}
				}
				f.setAccessible(false);
			}
		}
	}

	public static void deepDispose(Object mObject) {
		if (mObject == null)
			return;
		Class<?> c = mObject.getClass();
		if (c.isPrimitive()) {
			// do nothing
		} else if (Disposable.class.isAssignableFrom(c)) {
			Disposable d = (Disposable) mObject;
			d.dispose();
		} else if (c.isArray()) {
			Class<?> cc = c.getComponentType();
			if (cc.isPrimitive()) {
				// do nothing
			} else {
				for (int i = 0; i < Array.getLength(mObject); ++i) {
					Object o = Array.get(mObject, i);
					deepDispose(o);
					Array.set(mObject, i, null);
				}
			}
		}
	}

}
