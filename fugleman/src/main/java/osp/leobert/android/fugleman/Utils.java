package osp.leobert.android.fugleman;

import android.graphics.RectF;
import android.util.Log;
import android.view.View;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> Utils </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/1/15.
 */
final class Utils {

    static RectF getRectOnScreen(View view) {
        if (view == null) {
            Log.e("ViewUtils", "Please pass non-null referParent and child.");
            return new RectF();
        }

        RectF result = new RectF();

        int[] pos = new int[2];
        view.getLocationOnScreen(pos);

        result.left = pos[0];
        result.top = pos[1];
        result.right = result.left + view.getMeasuredWidth();
        result.bottom = result.top + view.getMeasuredHeight();

        return result;
    }

    static void calculateHighlightedViewRect(TipView tipView, Tip tip) {
        if (tipView == null
                || tip == null
                || tip.getAnchorView() == null) {
            return;
        }

        RectF anchorViewRect = Utils.getRectOnScreen(tip.getAnchorView());

        if (anchorViewRect == null
                || anchorViewRect.isEmpty()) {
            return;
        }

        View parent = (View) tipView.getParent();
        int[] rootViewPos = new int[2];
        parent.getLocationOnScreen(rootViewPos);

        anchorViewRect.left -= rootViewPos[0];
        anchorViewRect.right -= rootViewPos[0];
        anchorViewRect.top -= rootViewPos[1];
        anchorViewRect.bottom -= rootViewPos[1];

        anchorViewRect.left -= parent.getPaddingLeft();
        anchorViewRect.right -= parent.getPaddingLeft();
        anchorViewRect.top -= parent.getPaddingTop();
        anchorViewRect.bottom -= parent.getPaddingTop();

        tip.setAnchorArea(anchorViewRect);
        tip.getAnchorShape().setAreaRect(new RectF(
                anchorViewRect.left - tip.getShapeXOffset(),
                anchorViewRect.top - tip.getShapeYOffset(),
                anchorViewRect.right + tip.getShapeXOffset(),
                anchorViewRect.bottom + tip.getShapeYOffset()
        ));
    }

    public static <T> T checkNotNull(T arg) {
        return checkNotNull(arg, "Argument must not be null");
    }

    public static <T> T checkNotNull(T arg, String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
        return arg;
    }
}
