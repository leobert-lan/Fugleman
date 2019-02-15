package osp.leobert.android.fugleman;

import android.view.View;

import java.util.List;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> OnPlatClickedListener </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/15.
 */
public interface OnPlatClickedListener {
    void onClicked(List<Tip> currentTips, View tipView);
}
