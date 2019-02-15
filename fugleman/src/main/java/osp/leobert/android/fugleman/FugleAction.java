package osp.leobert.android.fugleman;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> FugleAction </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/1/15.
 */
public interface FugleAction {
    boolean canDisplay();

    void display();

    boolean isDisplaying();

    void dismiss();

    boolean hasNextTip();

    void nextTip();
}
