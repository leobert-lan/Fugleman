package osp.leobert.android.fugleman.pool;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import osp.leobert.android.fugleman.FugleAction;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman.pool </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> FugleActionPool </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/15.
 */
public class FugleActionPool {
    private final Map<Key, FugleAction> pool = new HashMap<>();


    public void add(@NonNull Key key, @NonNull FugleAction action) {
        pool.put(key, action);
    }

    @Nullable
    public FugleAction fetch(@NonNull String uniqueKey) {
        Set<Key> keys = pool.keySet();
        for (Key key : keys) {
            if (key != null && key.equalExact(uniqueKey))
                return pool.remove(key);
        }
        return null;
    }

    public void release(@NonNull String dimKey) {
        Set<Key> keys = pool.keySet();
        for (Key key : keys) {
            if (key != null && key.equalDim(dimKey)) {
                FugleAction action = pool.remove(key);
                if (action != null)
                    action.dismiss();
            }
        }
    }
}
