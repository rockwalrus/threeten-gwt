package java.util;

public class Objects {
    public static <T> T requireNonNull(T obj, String message) throws NullPointerException {
	if (obj == null) {
	    throw new NullPointerException(message);
	}

	return obj;
    }
}
