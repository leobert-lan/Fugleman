package osp.leobert.android.fugleman;

import android.view.View;

import java.util.List;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> OnPlatClickedListener </p>
 * <p><b>Description:</b> listener for the click event on the {@link TipView} inject into the {@link PLAT} </p>
 * Created by leobert on 2019/2/15.
 */
public interface OnPlatClickedListener {
    /**
     * @param currentTips all tips on displaying
     * @param tipView the TipView
     */
    void onClicked(List<Tip> currentTips, View tipView);
}
