package osp.leobert.android.fugleman.pool;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman.pool </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> Key </p>
 * <p><b>Description:</b> key used in {@link FugleActionPool FugleActionPool} </p>
 * Created by leobert on 2019/2/15.
 */
public class Key {
    /**
     * the unique key to be compared
     */
    @NonNull
    private final String uniqueKey;

    /**
     * one key related to the host. maybe activity, fragment, dialog which has lifecycle.
     * The key will be used to release all related {@link osp.leobert.android.fugleman.FugleAction FugleAction}
     * in {@link FugleActionPool FugleActionPool} avoid memory leak when the host going destroy
     */
    @NonNull
    private final String dimKey;

    public Key(@NonNull String uniqueKey, @NonNull String dimKey) {
        this.uniqueKey = uniqueKey;
        this.dimKey = dimKey;
    }

    public boolean equalExact(String uniqueKey) {
        return TextUtils.equals(uniqueKey,this.uniqueKey);
    }

    public boolean equalDim(String dimKey) {
        return TextUtils.equals(this.dimKey,dimKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Utils.equals(uniqueKey, key.uniqueKey) &&
                Utils.equals(dimKey, key.dimKey);
    }

    @Override
    public int hashCode() {
        return Utils.hash(uniqueKey, dimKey);
    }
}
