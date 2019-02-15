package osp.leobert.android.fugleman.pool;

import java.util.Arrays;

final class Utils {

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }
}
