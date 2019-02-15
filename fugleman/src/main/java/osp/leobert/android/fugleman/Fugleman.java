package osp.leobert.android.fugleman;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import osp.leobert.android.fugleman.pool.FugleActionPool;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> Fugleman </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/15.
 */
public final class Fugleman {

    private final FugleActionPool fugleActionPool;

    private Fugleman() {
        fugleActionPool = new FugleActionPool();
    }

    private static Fugleman instance = null;

    static Fugleman getInstance() {
        if (instance == null) {
            synchronized (Fugleman.class) {
                if (instance == null)
                    instance = new Fugleman();
            }
        }
        return instance;
    }

    public static FugleActionPool getPool() {
        return getInstance().fugleActionPool;
    }


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

    public static void onDestory(@NonNull Activity activity) {
        getPool().release(String.valueOf(activity));
    }

    public static void onDestory(@NonNull String dimKey) {
        getPool().release(dimKey);
    }

    public static void releaseFromPool(@NonNull PLAT plat) {
        FugleAction action = getPool().fetch(plat.uniqueKey());
        if (action != null)
            action.dismiss();
    }

    public static void tryDisplay(@NonNull PLAT plat) {
        FugleAction action = getPool().fetch(plat.uniqueKey());
        if (action != null)
            action.display();
    }

}
