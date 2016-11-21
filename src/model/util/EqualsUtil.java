package model.util;

public class EqualsUtil {
	public static boolean equals(Object obj1, Object obj2) {
		if (obj1 == obj2) {
			return true;
		}

		if (obj1 == null || obj2 == null) {
			return false;
		}

		return obj1.equals(obj2);
	}
}
