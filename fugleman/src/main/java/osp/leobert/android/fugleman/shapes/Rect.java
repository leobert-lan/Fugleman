package osp.leobert.android.fugleman.shapes;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;

import osp.leobert.android.fugleman.AbsShape;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman.shapes </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> Rect </p>
 * <p><b>Description:</b> rect area shape </p>
 * Created by leobert on 2019/1/15.
 */
public class Rect extends AbsShape {
    public Rect() {
        super(10, Color.WHITE);
    }

    public Rect(float areaBlurRadius, int color) {
        super(areaBlurRadius, color);
    }

    @Override
    public void setAreaRect(RectF areaRect) {
        super.setAreaRect(areaRect);
        if (!isAreaRectIllegal()) {
            path.reset();
            path.addRect(areaRect, Path.Direction.CW);
        }
    }
}
