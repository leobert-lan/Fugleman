package osp.leobert.android.fugleman;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> FugleAction </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/1/15.
 */
public interface FugleAction {
    FugleAction setBackgroundColor(@ColorInt int color);

    FugleAction setBackgroundColorRes(@ColorRes int colorRes);

    FugleAction addTips(Tip... tips);

    FugleAction setInteractive(boolean interactive);

    FugleAction lifecycle(OnTipLifecycleListener onTipLifecycleListener);

    FugleAction setPlatClickedListener(OnPlatClickedListener platClickedListener);

    boolean canDisplay();

    void display();

    boolean isDisplaying();

    void dismiss();

    boolean hasNextTip();

    void nextTip();
}
