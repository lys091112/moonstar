package io.crescent.multi.datasource;

/**
 * @author liuhongjun
 * @since 2019-06-27
 */
public class DataSourceHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void set(String dbType) {
        contextHolder.set(dbType);
    }

    public static String get() {
        return contextHolder.get();
    }

    public static void remove() {
        contextHolder.remove();
    }
}
