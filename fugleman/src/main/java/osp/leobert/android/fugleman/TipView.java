package osp.leobert.android.fugleman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> TipView </p>
 * <p><b>Description:</b> tip view </p>
 * Created by leobert on 2019/1/15.
 */
public final class TipView extends FrameLayout {
    private final List<Tip> tips = new ArrayList<>();
    private int mInitHeight;
    private int mInitWidth;
    @ColorInt
    private int mBgColor = -1;

    public TipView(@NonNull Context context) {
        super(context);
        init();
    }

    public TipView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TipView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //need the exactly width & height
        measureChildren(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        tips.clear();
    }


    public void reLayout() {
        for (int i = 0; i < getChildCount(); i++) {
            Utils.calculateHighlightedViewRect(TipView.this, tips.get(i));
            getChildAt(i).setLayoutParams(generateLayoutParams(mInitWidth, mInitHeight, tips.get(i), getChildAt(i)));
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        mBgColor = color;
    }


    public void addTip(List<Tip> tips) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).clearAnimation();
        }

        removeAllViews();

        if (tips == null || tips.isEmpty()) {
            return;
        }

        this.tips.addAll(tips);

        for (Tip tip : tips) {
            //check paramters
            addTipView(tip);
        }
    }

    public void addTipView(Tip tip) {
        if (tip == null) {
            return;
        }

        View tipView = tip.getTipView();
        LayoutParams layoutParams = generateLayoutParams(mInitWidth, mInitHeight, tip, tipView);

        if (tip.getTipDisplayAnimation() != null) {
            tipView.startAnimation(tip.getTipDisplayAnimation());
        }

        addView(tipView, layoutParams);
    }


    private LayoutParams generateLayoutParams(int width, int height, Tip tip, View tipView) {
        RectF anchorAreaRect = tip.getAnchorArea();

        TipViewMargin marginOffset = tip.getTipViewMargins();
        LayoutParams layoutParams = (LayoutParams) tipView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        if (anchorAreaRect == null
                || anchorAreaRect.isEmpty()) {
            return layoutParams;
        }

        boolean alignRight = false;
        // TODO: 2019/1/15 change the rule
        switch (tip.getAlignRule()) {
            case Align.Above:
                if (anchorAreaRect.left > width / 2) { //on the right
                    alignRight = true;
                    layoutParams.rightMargin = (int) (width - anchorAreaRect.right + marginOffset.getRightMargin());
                } else { //on the left
                    layoutParams.leftMargin = (int) (anchorAreaRect.left + marginOffset.getLeftMargin());
                }
                layoutParams.bottomMargin = (int) (height - anchorAreaRect.bottom + anchorAreaRect.height()
                        + marginOffset.getBottomMargin());
                break;
            case Align.Below:
                if (anchorAreaRect.left > width / 2) { //on the right
                    alignRight = true;
                    layoutParams.rightMargin = (int) (width - anchorAreaRect.right + marginOffset.getRightMargin());
                } else { //on the left
                    layoutParams.leftMargin = (int) (anchorAreaRect.left + marginOffset.getLeftMargin());
                }
                layoutParams.topMargin = (int) (anchorAreaRect.bottom + marginOffset.getTopMargin());
                break;
            case Align.ToLeft:
                layoutParams.topMargin = (int) anchorAreaRect.top + marginOffset.getTopMargin();
                layoutParams.rightMargin = (int) (width - anchorAreaRect.right + marginOffset.getRightMargin() + anchorAreaRect.width());
                break;
            case Align.ToRight:
                layoutParams.topMargin = (int) anchorAreaRect.top + marginOffset.getTopMargin();
                layoutParams.leftMargin = (int) (anchorAreaRect.right + marginOffset.getLeftMargin());
                break;
        }

        if (layoutParams.rightMargin != 0
                || alignRight) {
            layoutParams.gravity = Gravity.RIGHT;
        } else {
            layoutParams.gravity = Gravity.LEFT;
        }

        if (layoutParams.bottomMargin != 0) {
            layoutParams.gravity |= Gravity.BOTTOM;
        } else {
            layoutParams.gravity |= Gravity.TOP;
        }

        return layoutParams;
    }

    /**
     * Check if the shape is empty.
     */
    private boolean isShapeEmpty(AbsShape shape) {
        return shape == null
                || shape.getAreaRect() == null
                || shape.getAreaRect().isEmpty();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBgColor == -1) {
            mBgColor = 0xE5000000;
        }

//        canvas.save();
        //firstly, clip the rects of all the highlighted views.
        if (!tips.isEmpty()) {
            for (Tip tip : tips) {

                if (tip.getAnchorView() == null
                        || tip.getAnchorShape() == null
                        || tip.getAnchorShape().getAreaRect() == null
                        || tip.getAnchorShape().getAreaRect().isEmpty()) {
                    continue;
                }
                canvas.clipPath(tip.getAnchorShape().getShapePath(), Region.Op.DIFFERENCE);
            }
        }

        //then, draw the bg color
        canvas.drawColor(mBgColor);

        //finally, draw the rects of all the highlighted views.
        if (/*tips != null &&*/ !tips.isEmpty()) {
            for (Tip lighterParameter : tips) {

                if (isShapeEmpty(lighterParameter.getAnchorShape())) {
                    continue;
                }

                lighterParameter.getAnchorShape().onDraw(canvas);
            }
        }

    }

    void setInitHeight(int initHeight) {
        this.mInitHeight = initHeight;
    }

    void setInitWidth(int initWidth) {
        this.mInitWidth = initWidth;
    }


}
