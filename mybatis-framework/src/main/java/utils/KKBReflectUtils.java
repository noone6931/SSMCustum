package utils;

public class KKBReflectUtils {
    public static Class<?> resolveType(String resultType) {
        try {
            Class<?> clazz = Class.forName(resultType);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
