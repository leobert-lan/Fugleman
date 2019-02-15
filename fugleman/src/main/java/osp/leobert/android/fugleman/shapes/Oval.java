package osp.leobert.android.fugleman.shapes;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;

import osp.leobert.android.fugleman.AbsShape;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman.shapes </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> Oval </p>
 * <p><b>Description:</b> oval area shape </p>
 * Created by leobert on 2019/1/15.
 */
public class Oval extends AbsShape {

    public Oval() {
        super(10, Color.WHITE);
    }

    public Oval(float areaBlurRadius, int color) {
        super(areaBlurRadius, color);
    }

    @Override
    public void setAreaRect(RectF areaRect) {
        super.setAreaRect(areaRect);
        if (!isAreaRectIllegal()) {
            path.reset();
            path.addOval(areaRect, Path.Direction.CW);
        }
    }
}
