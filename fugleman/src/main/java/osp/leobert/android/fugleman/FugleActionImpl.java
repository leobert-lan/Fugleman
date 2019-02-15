package osp.leobert.android.fugleman;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import osp.leobert.android.fugleman.shapes.Rect;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> FugleActionImpl </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/1/15.
 */
final class FugleActionImpl implements FugleAction {
    @NonNull
    private final WeakReference<PLAT> platRef;

    private List<List<Tip>> tipsList = new ArrayList<>();
    private TipView fullTipView;
    private ViewGroup mRootView;
    private boolean isReleased = false;
    private boolean isAutoNext = true;
    private boolean isShowing = false;
    private boolean isDecorView = false;
    private boolean hasDidRootViewGlobalLayout = false;

    private int showIndex;
    private OnTipLifecycleListener onTipLifecycleListener;
    private OnPlatClickedListener onPlatClickedListener;

    private View.OnClickListener onFullTipViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onPlatClickedListener != null) {
                onPlatClickedListener.onClicked(currentTips, v);
            }

            if (isAutoNext) {
                nextTip();
            }
        }
    };
    private boolean interactive = true;

    FugleActionImpl(@NonNull PLAT plat, @NonNull Activity activity) {
        platRef = new WeakReference<>(plat);

        fullTipView = new TipView(activity);
        mRootView = (ViewGroup) activity.getWindow().getDecorView();
        isDecorView = true;

        activity.findViewById(android.R.id.content).addOnLayoutChangeListener(mRootViewLayoutChangeListener);
    }

    FugleActionImpl(@NonNull PLAT plat, @NonNull ViewGroup rootView) {
        this.platRef = new WeakReference<>(plat);

        mRootView = rootView;
        fullTipView = new TipView(rootView.getContext());
        mRootView.addOnLayoutChangeListener(mRootViewLayoutChangeListener);
    }

    @Nullable
    private PLAT getPlat() {
        return platRef.get();
    }

    public FugleAction addTips(Tip... tips) {
        if (isReleased) {
            return this;
        }

        if (tips != null
                && tips.length > 0) {
            tipsList.add(Arrays.asList(tips));
        }
        return this;
    }

    @Override
    public FugleAction setInteractive(boolean interactive) {
        this.interactive = interactive;
        return this;
    }

    @Override
    public FugleAction lifecycle(OnTipLifecycleListener onTipLifecycleListener) {
        this.onTipLifecycleListener = onTipLifecycleListener;
        return this;
    }

    @Override
    public FugleAction setPlatClickedListener(OnPlatClickedListener platClickedListener) {
        this.onPlatClickedListener = platClickedListener;
        return this;
    }

    @Override
    public boolean canDisplay() {
        PLAT plat = platRef.get();
        if (plat == null)
            return false;
        return plat.inEyesight();
    }

    @Override
    public void display() {
        if (isReleased) {
            return;
        }

        if (interactive) {
            fullTipView.setOnClickListener(onFullTipViewClickListener);
        }

        //To ensure the root view has attached to window
        if (Utils.isAttachedToWindow(mRootView)) {
            PLAT plat = getPlat();
            if (plat != null) {
                if (plat.inEyesight()) {

                    if (fullTipView.getParent() == null) {
                        mRootView.addView(fullTipView,
                                new ViewGroup.LayoutParams(mRootView.getWidth(), mRootView.getHeight()));
                    }
                    showIndex = -1;
                    nextTip();
                } else {
                    // TODO: 2019/2/15 add to waiting pool
                }
            }
        } else {
            mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }

    @Override
    public boolean isDisplaying() {
        return isShowing;
    }

    @Override
    public void dismiss() {
        if (onTipLifecycleListener != null) {
            onTipLifecycleListener.onDismiss(this);
        }

        isShowing = false;
        onRelease();
    }

    @Override
    public boolean hasNextTip() {
        if (isReleased) {
            return false;
        }

        return !tipsList.isEmpty();
    }

    private List<Tip> currentTips;

    @Override
    public void nextTip() {
        if (isReleased) {
            return;
        }

        if (!hasNextTip()) {
            dismiss();
        } else {
            isShowing = true;
            showIndex++;
            if (onTipLifecycleListener != null) {
                onTipLifecycleListener.onDisplay(showIndex);
            }

            currentTips = tipsList.get(0);
            for (Tip tip : currentTips) {
                checkTip(tip);
            }

            fullTipView.setInitWidth(mRootView.getWidth() - mRootView.getPaddingLeft() - mRootView.getPaddingRight());
            fullTipView.setInitHeight(mRootView.getHeight() - mRootView.getPaddingTop() - mRootView.getPaddingBottom());

            fullTipView.addTip(currentTips);
            tipsList.remove(0);
        }
    }

    private void checkTip(Tip tip) {
        if (tip.getAnchorShape() == null) {
            tip.setAnchorShape(new Rect());
        }

        if (tip.getAnchorView() == null) {
            tip.setAnchorView(mRootView.findViewById(tip.getAnchorViewId()));
        }

        if (tip.getTipView() == null) {
            tip.setTipView(LayoutInflater.from(fullTipView.getContext()).inflate(tip.getTipLayoutId(),
                    fullTipView, false));
        }

        Utils.checkNotNull(tip.getAnchorView(), "Please pass a anchor view or an id of it.");

        Utils.checkNotNull(tip.getTipView(), "Please pass a tip view or a layout id of tip view.");

        if (tip.getTipViewMargins() == null) {
            tip.setTipViewMargins(new TipViewMargin()); //use empty offset.
        }

        Utils.calculateHighlightedViewRect(fullTipView, tip);

    }

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            /*
             * Guaranteed to be called only once.
             * */
            if (hasDidRootViewGlobalLayout) {
                return;
            }

            hasDidRootViewGlobalLayout = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(mGlobalLayoutListener);
            }

            display();
        }
    };

    private View.OnLayoutChangeListener mRootViewLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (left == oldLeft
                    && top == oldTop
                    && right == oldRight
                    && bottom == oldBottom) {
                return;
            }

            if (fullTipView == null || fullTipView.getParent() == null) {
                return;
            }

            if (!isDecorView) {
                ViewGroup.LayoutParams layoutParams = fullTipView.getLayoutParams();
                layoutParams.width = Math.abs(right - left);
                layoutParams.height = Math.abs(bottom - top);

                fullTipView.setInitWidth(layoutParams.width);
                fullTipView.setInitHeight(layoutParams.height);
                fullTipView.setLayoutParams(layoutParams);
            }

            fullTipView.reLayout();
        }
    };

    /**
     * Release all when all specified highlights are completed.
     */
    private void onRelease() {
        if (isReleased) {
            return;
        }

        isReleased = true;
        if (isDecorView) {
            mRootView.findViewById(android.R.id.content).removeOnLayoutChangeListener(mRootViewLayoutChangeListener);
        } else {
            mRootView.removeOnLayoutChangeListener(mRootViewLayoutChangeListener);
        }

        mRootView.removeView(fullTipView);
        fullTipView.removeAllViews();

        tipsList.clear();
        tipsList = null;

        onPlatClickedListener = null;
        onTipLifecycleListener = null;
        mRootView = null;
        fullTipView = null;
    }

    @Override
    public FugleAction setBackgroundColor(@ColorInt int color) {
        if (isReleased) {
            return this;
        }
        fullTipView.setBackgroundColor(color);
        return this;
    }

    @Override
    public FugleAction setBackgroundColorRes(@ColorRes int colorRes) {
        if (isReleased) {
            return this;
        }
        fullTipView.setBackgroundColor(fullTipView.getResources().getColor(colorRes));
        return this;
    }
}
