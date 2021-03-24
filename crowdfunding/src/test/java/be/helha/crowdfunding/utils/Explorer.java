package be.helha.crowdfunding.utils;

import java.lang.reflect.Field;

public class Explorer {
	public static Object getField(final Object obj, final String name) {
		Object value = null;
		final Class<?> class1 = obj.getClass();
		try {
			final Field declaredField;
			(declaredField = class1.getDeclaredField(name)).setAccessible(true);
			value = declaredField.get(obj);
		} catch (NoSuchFieldException ex) {
			ex.printStackTrace();
		} catch (IllegalArgumentException ex2) {
			ex2.printStackTrace();
		} catch (IllegalAccessException ex3) {
			ex3.printStackTrace();
		}
		return value;
	}
}
