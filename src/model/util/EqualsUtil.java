package model.util;

import java.util.Arrays;
import java.util.List;

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

	public static <T> boolean equalsCollection(T[] arr1, T[] arr2) {
		return equalsCollection(Arrays.asList(arr1), Arrays.asList(arr2));
	}

	public static boolean equalsCollection(List<?> col1, List<?> col2) {
		if (col1 == col2) {
			return true;
		}

		if (col1 == null || col2 == null) {
			return false;
		}

		if (col1.size() != col2.size()) {
			return false;
		}

		for (int i = 0; i < col1.size(); i++) {
			if (!equals(col1.get(i), col2.get(i))) {
				return false;
			}
		}

		return true;
	}
}
