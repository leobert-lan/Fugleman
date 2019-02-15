package osp.leobert.android.fugleman;

import android.support.annotation.IntDef;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> Align </p>
 * <p><b>Description:</b> rules to layout the tip </p>
 * Created by leobert on 2019/1/15.
 */
@IntDef({Align.ToLeft, Align.Above, Align.ToRight, Align.Below})
public @interface Align {
    /**
     * to left of the target
     */
    int ToLeft = 1;

    /**
     * above the target
     */
    int Above = 2;

    /**
     * to right of the target
     */
    int ToRight = 3;

    /**
     * below the target
     */
    int Below = 4;
}
