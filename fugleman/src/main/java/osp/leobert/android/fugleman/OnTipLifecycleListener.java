package osp.leobert.android.fugleman;

import android.support.annotation.IntRange;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> OnTipLifecycleListener </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/1/15.
 */
public interface OnTipLifecycleListener {

    void onDisplay(@IntRange(from = 0) int index);

    void onDismiss(FugleAction fugleAction);
}
