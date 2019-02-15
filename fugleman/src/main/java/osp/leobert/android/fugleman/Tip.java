package osp.leobert.android.fugleman;

import android.graphics.RectF;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.animation.Animation;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> Tip </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/1/15.
 */
public class Tip {
    private int anchorViewId;
    private View anchorView;
    private RectF anchorArea;

    @LayoutRes
    private int tipLayoutId;
    private View tipView;

    private AbsShape anchorShape;

    private float shapeXOffset;
    private float shapeYOffset;

    @Align
    private int alignRule;

    private TipViewMargin tipViewMargins;
    private Animation tipDisplayAnimation;

    public void setAnchorArea(RectF anchorArea) {
        this.anchorArea = anchorArea;
    }

    public void setAnchorViewId(int anchorViewId) {
        this.anchorViewId = anchorViewId;
    }

    public void setAnchorView(View anchorView) {
        this.anchorView = anchorView;
    }

    public void setTipLayoutId(int tipLayoutId) {
        this.tipLayoutId = tipLayoutId;
    }

    public void setTipView(View tipView) {
        this.tipView = tipView;
    }

    public void setAnchorShape(AbsShape anchorShape) {
        this.anchorShape = anchorShape;
    }

    public void setShapeXOffset(float shapeXOffset) {
        this.shapeXOffset = shapeXOffset;
    }

    public void setShapeYOffset(float shapeYOffset) {
        this.shapeYOffset = shapeYOffset;
    }

    public void setAlignRule(int alignRule) {
        this.alignRule = alignRule;
    }

    public void setTipViewMargins(TipViewMargin tipViewMargins) {
        this.tipViewMargins = tipViewMargins;
    }

    public void setTipDisplayAnimation(Animation tipDisplayAnimation) {
        this.tipDisplayAnimation = tipDisplayAnimation;
    }

    public int getAnchorViewId() {
        return anchorViewId;
    }

    public View getAnchorView() {
        return anchorView;
    }

    public RectF getAnchorArea() {
        return anchorArea;
    }

    public int getTipLayoutId() {
        return tipLayoutId;
    }

    public View getTipView() {
        return tipView;
    }

    public AbsShape getAnchorShape() {
        return anchorShape;
    }

    public float getShapeXOffset() {
        return shapeXOffset;
    }

    public float getShapeYOffset() {
        return shapeYOffset;
    }

    @Align
    public int getAlignRule() {
        return alignRule;
    }

    public TipViewMargin getTipViewMargins() {
        return tipViewMargins;
    }

    public Animation getTipDisplayAnimation() {
        return tipDisplayAnimation;
    }

    private Tip(Builder builder) {
        anchorViewId = builder.anchorViewId;
        anchorView = builder.anchorView;
        anchorArea = builder.anchorArea;
        tipLayoutId = builder.tipLayoutId;
        tipView = builder.tipView;
        anchorShape = builder.anchorShape;
        shapeXOffset = builder.shapeXOffset;
        shapeYOffset = builder.shapeYOffset;
        alignRule = builder.alignRule;
        tipViewMargins = builder.tipViewMargins;
        tipDisplayAnimation = builder.tipDisplayAnimation;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private int anchorViewId;
        private View anchorView;
        private RectF anchorArea;
        private int tipLayoutId;
        private View tipView;
        private AbsShape anchorShape;
        private float shapeXOffset;
        private float shapeYOffset;
        private int alignRule;
        private TipViewMargin tipViewMargins;
        private Animation tipDisplayAnimation;

        private Builder() {
        }

        public Builder anchorViewId(int val) {
            anchorViewId = val;
            return this;
        }

        public Builder anchorView(View val) {
            anchorView = val;
            return this;
        }

        public Builder anchorArea(RectF val) {
            anchorArea = val;
            return this;
        }

        public Builder tipLayoutId(int val) {
            tipLayoutId = val;
            return this;
        }

        public Builder tipView(View val) {
            tipView = val;
            return this;
        }

        public Builder anchorShape(AbsShape val) {
            anchorShape = val;
            return this;
        }

        public Builder shapeXOffset(float val) {
            shapeXOffset = val;
            return this;
        }

        public Builder shapeYOffset(float val) {
            shapeYOffset = val;
            return this;
        }

        public Builder alignRule(@Align int val) {
            alignRule = val;
            return this;
        }

        public Builder tipViewMargins(TipViewMargin val) {
            tipViewMargins = val;
            return this;
        }

        public Builder tipDisplayAnimation(Animation val) {
            tipDisplayAnimation = val;
            return this;
        }

        public Tip build() {
            return new Tip(this);
        }
    }
}
