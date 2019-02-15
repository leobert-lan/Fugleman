package osp.leobert.android.fugleman;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
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
    private TipView mLighterView;
    private ViewGroup mRootView;
    private boolean isReleased = false;
    private boolean isAutoNext = true;
    private boolean isShowing = false;
    private boolean isDecorView = false;
    private boolean hasDidRootViewGlobalLayout = false;

    private int showIndex;
    private OnTipLifecycleListener mOnLighterListener;
//    private OnLighterViewClickListener mOutSideLighterClickListener;

    FugleActionImpl(@NonNull PLAT plat, @NonNull Activity activity) {
        platRef = new WeakReference<>(plat);

        mLighterView = new TipView(activity);
        mRootView = (ViewGroup) activity.getWindow().getDecorView();
        isDecorView = true;

        activity.findViewById(android.R.id.content).addOnLayoutChangeListener(mRootViewLayoutChangeListener);
    }

    FugleActionImpl(@NonNull PLAT plat, @NonNull ViewGroup rootView) {
        this.platRef = new WeakReference<>(plat);

        mRootView = rootView;
        mLighterView = new TipView(rootView.getContext());
        mRootView.addOnLayoutChangeListener(mRootViewLayoutChangeListener);
    }

    private PLAT getPlat() {
        return platRef.get();
    }

    public void addTips(Tip... tips) {
        if (isReleased){
            return;
        }

        if (tips != null
                && tips.length > 0) {
            tipsList.add(Arrays.asList(tips));
        }
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

    }

    @Override
    public boolean isDisplaying() {
        return isShowing;
    }

    @Override
    public void dismiss() {

    }

    @Override
    public boolean hasNextTip() {
        if (isReleased) {
            return false;
        }

        return !tipsList.isEmpty();
    }

    @Override
    public void nextTip() {
        if (isReleased) {
            return;
        }


        if (!hasNextTip()) {
            dismiss();
        } else {
            isShowing = true;
            if (mOnLighterListener != null) {
                mOnLighterListener.onDisplay(showIndex);
            }

            showIndex++;
            List<Tip> tips = tipsList.get(0);
            for (Tip tip : tips) {
                checkTip(tip);
            }

            mLighterView.setInitWidth(mRootView.getWidth() - mRootView.getPaddingLeft() - mRootView.getPaddingRight());
            mLighterView.setInitHeight(mRootView.getHeight() - mRootView.getPaddingTop() - mRootView.getPaddingBottom());

            mLighterView.addTip(tips);
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
            tip.setTipView(LayoutInflater.from(mLighterView.getContext()).inflate(tip.getTipLayoutId(),
                    mLighterView, false));
        }

            Utils.checkNotNull(tip.getAnchorView(), "Please pass a anchor view or an id of it.");

            Utils.checkNotNull(tip.getTipView(), "Please pass a tip view or a layout id of tip view.");

        if (tip.getTipViewMargins() == null) {
            tip.setTipViewMargins(new TipViewMargin()); //use empty offset.
        }

        Utils.calculateHighlightedViewRect(mLighterView, tip);

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

            if (mLighterView == null || mLighterView.getParent() == null) {
                return;
            }

            if (!isDecorView) {
                ViewGroup.LayoutParams layoutParams = mLighterView.getLayoutParams();
                layoutParams.width = Math.abs(right - left);
                layoutParams.height = Math.abs(bottom - top);

                mLighterView.setInitWidth(layoutParams.width);
                mLighterView.setInitHeight(layoutParams.height);
                mLighterView.setLayoutParams(layoutParams);
            }

            mLighterView.reLayout();
        }
    };

}
