package osp.leobert.android.fugleman;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> AbsShape </p>
 * <p><b>Description:</b> the share of the area highlighted in PLAT </p>
 * Created by leobert on 2019/1/15.
 */
public abstract class AbsShape {
    protected RectF areaRect;
    protected Path path;
    protected Paint paint;

    protected AbsShape() {
        this(0, Color.WHITE);
    }

    public AbsShape(float areaBlurRadius, @ColorInt int color) {
        path = new Path();
        paint = new Paint();

        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(color);

        if (areaBlurRadius > 0) {
            paint.setMaskFilter(new BlurMaskFilter(areaBlurRadius, BlurMaskFilter.Blur.SOLID));
        }
    }

    public void onDraw(Canvas canvas) {
        if (path != null && paint != null) {
            canvas.drawPath(path, paint);
        }
    }

    /**
     * Set {@link RectF} the highlighted area.
     */
    public void setAreaRect(RectF areaRect) {
        this.areaRect = areaRect;
    }

    public RectF getAreaRect() {
        return areaRect;
    }

    public boolean isAreaRectIllegal() {
        return areaRect == null
                || areaRect.isEmpty();
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Path getShapePath() {
        return path;
    }
}
