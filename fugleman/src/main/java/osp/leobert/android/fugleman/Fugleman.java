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
public final class Fugleman implements FugleAction {
    private final FugleActionImpl internalImpl;

    private Fugleman(@NonNull PLAT plat, @NonNull Activity activity) {
        internalImpl = new FugleActionImpl(plat, activity);
    }

    private Fugleman(@NonNull PLAT plat, @NonNull ViewGroup viewGroup) {
        internalImpl = new FugleActionImpl(plat, viewGroup);
    }


    public static Fugleman with(@NonNull PLAT plat, @NonNull Activity activity) {
        Utils.checkNotNull(activity,
                "You can not show a highlight view on a null activity.");
        return new Fugleman(plat, activity);
    }


    public static Fugleman with(@NonNull PLAT plat, @NonNull ViewGroup rootView) {
        Utils.checkNotNull(rootView,
                "You can not show a highlight view on a null root view.");
        return new Fugleman(plat, rootView);
    }

    public Fugleman addTips(Tip... tips) {
        internalImpl.addTips(tips);
        return this;
    }


    @Override
    public boolean canDisplay() {
        return internalImpl.canDisplay();
    }

    @Override
    public void display() {
        internalImpl.display();
    }

    @Override
    public boolean isDisplaying() {
        return internalImpl.isDisplaying();
    }

    @Override
    public void dismiss() {
        internalImpl.dismiss();
    }

    @Override
    public boolean hasNextTip() {
        return internalImpl.hasNextTip();
    }

    @Override
    public void nextTip() {
        internalImpl.nextTip();
    }
}
