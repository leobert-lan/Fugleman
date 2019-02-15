package osp.leobert.android.fugleman_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import osp.leobert.android.fugleman.Align;
import osp.leobert.android.fugleman.FugleAction;
import osp.leobert.android.fugleman.Fugleman;
import osp.leobert.android.fugleman.OnPlatClickedListener;
import osp.leobert.android.fugleman.OnTipLifecycleListener;
import osp.leobert.android.fugleman.PLAT;
import osp.leobert.android.fugleman.Tip;
import osp.leobert.android.fugleman.TipViewMargin;
import osp.leobert.android.fugleman.shapes.RoundRect;

public class MainActivity extends AppCompatActivity implements PLAT {

    private FugleAction fugleman1;
    private FugleAction fugleman2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.test_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fugleman1 == null) {
                    fugleman1 = Fugleman.with(MainActivity.this, MainActivity.this)
                            .addTips(Tip.newBuilder()
                                    .anchorViewId(R.id.test_1)
                                    .anchorShape(new RoundRect())
                                    .shapeXOffset(30)
                                    .alignRule(Align.ToRight)
                                    .tipViewMargins(new TipViewMargin(30, 0, 0, 0))
                                    .tipLayoutId(R.layout.layout_tip1)
                                    .build());
                    if (fugleman1.canDisplay())
                        fugleman1.display();
                    else
                        Toast.makeText(MainActivity.this, "cannot display fugleman", Toast.LENGTH_SHORT).show();
                } else {
                    fugleman1.dismiss();
                    fugleman1 = null;
                }
            }
        });


        findViewById(R.id.test_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fugleman2 == null) {
                    fugleman2 = Fugleman.with(MainActivity.this, MainActivity.this)
                            .setBackgroundColorRes(android.R.color.transparent)
                            .lifecycle(new OnTipLifecycleListener() {
                                @Override
                                public void onDisplay(int index) {

                                }

                                @Override
                                public void onDismiss(FugleAction fugleAction) {
                                    fugleman2 = null;
                                }
                            })
                            .setPlatClickedListener(new OnPlatClickedListener() {
                                @Override
                                public void onClicked(List<Tip> currentTips, View tipView) {

                                }
                            })
                            .addTips(Tip.newBuilder()
                                            .anchorViewId(R.id.test_2)
                                            .anchorShape(new RoundRect())
                                            .alignRule(Align.ToLeft)
                                            .tipLayoutId(R.layout.layout_tip1)
                                            .build(),

                                    Tip.newBuilder()
                                            .anchorViewId(R.id.test_2)
                                            .anchorShape(new RoundRect())
                                            .alignRule(Align.Below)
                                            .tipLayoutId(R.layout.layout_tip1)
                                            .build()
                            )
                            .addTips(
                                    Tip.newBuilder()
                                            .anchorViewId(R.id.test_1)
                                            .anchorShape(new RoundRect())
                                            .alignRule(Align.Below)
                                            .tipLayoutId(R.layout.layout_tip1)
                                            .build()
                            );
                    if (fugleman2.canDisplay())
                        fugleman2.display();
                    else
                        Toast.makeText(MainActivity.this, "cannot display fugleman", Toast.LENGTH_SHORT).show();
                } else {
                    fugleman2.dismiss();
                    fugleman2 = null;
                }
            }
        });


    }

    @Override
    public boolean inEyesight() {
        return true;
    }

    @Override
    public String uniqueKey() {
        return "main";
    }
}
