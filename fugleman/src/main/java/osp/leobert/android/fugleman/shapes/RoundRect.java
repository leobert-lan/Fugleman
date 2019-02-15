package osp.leobert.android.fugleman.shapes;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;

import osp.leobert.android.fugleman.AbsShape;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman.shapes </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> RoundRect </p>
 * <p><b>Description:</b> round rect area shape </p>
 * Created by leobert on 2019/1/15.
 */
public class RoundRect extends AbsShape {
    float xRadius;
    float yRadius;

    public RoundRect() {
        this(10, Color.WHITE, 6, 6);
    }

    public RoundRect(float areaBlurRadius, int color, float xRadius, float yRadius) {
        super(areaBlurRadius, color);
        this.xRadius = xRadius;
        this.yRadius = yRadius;
    }

    @Override
    public void setAreaRect(RectF areaRect) {
        super.setAreaRect(areaRect);
        if (!isAreaRectIllegal()) {
            path.reset();
            path.addRoundRect(areaRect, xRadius, yRadius, Path.Direction.CW);
        }
    }
}
