package osp.leobert.android.fugleman;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> Fugleman </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/15.
 */
public final class Fugleman {

    public static FugleAction with(@NonNull PLAT plat, @NonNull Activity activity) {
        Utils.checkNotNull(activity,
                "You can not show a highlight view on a null activity.");
        return new FugleActionImpl(plat, activity);
    }


    public static FugleAction with(@NonNull PLAT plat, @NonNull ViewGroup rootView) {
        Utils.checkNotNull(rootView,
                "You can not show a highlight view on a null root view.");
        return new FugleActionImpl(plat, rootView);
    }


}
