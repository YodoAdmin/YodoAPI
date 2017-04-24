package co.yodo.restapi.utils;

import static android.text.TextUtils.isEmpty;

/**
 * Created by hei on 19/04/17.
 * Verifies some possible errors
 */
public final class ErrorUtils {
    /**
     * Avoid the creation of instances
     */
    private ErrorUtils() {}

    /**
     * Verifies that a value is not null
     * @param message The message word
     * @param value The value to validate
     */
    public static void checkNull(String message, Object value) {
        if (value == null) {
            throw new NullPointerException(message + " should not be null");
        }
    }

    /**
     * Validates that a value is not null or empty
     * @param message The message word
     * @param value The value to validate
     */
    public static void checkNullOrEmpty(String message, String value) {
        if (isEmpty(value)) {
            throw new NullPointerException(message + " should not be null or empty");
        }
    }
}
