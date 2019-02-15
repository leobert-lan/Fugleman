package osp.leobert.android.fugleman;

/**
 * <p><b>Package:</b> osp.leobert.android.fugleman </p>
 * <p><b>Project:</b> Fugleman </p>
 * <p><b>Classname:</b> PLAT </p>
 * <p><b>Description:</b> the page or virtual area in eyesight can display the tip </p>
 * Created by leobert on 2019/1/15.
 */
public interface PLAT {
    /**
     * @return true if the area in eyesight, means it can display the tip by fugleman,
     * false otherwise; If false return, the FugleAction will be post to waiting pool;
     */
    boolean inEyesight();

    /**
     * @return unique key of the PLAT, used as the key in waiting pool;
     */
    String uniqueKey();

    String dimKey();
}
