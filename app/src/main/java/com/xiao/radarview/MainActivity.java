package com.xiao.radarview;

import android.app.Activity;
import android.os.Bundle;

import com.xiao.radarview.widget.RadarView;

public class MainActivity extends Activity {

    private RadarView radarView;

    private String[] titles = {"助攻", "物理", "魔法", "防御", "金钱", "击杀", "生存"};
    private double[] datas = {2, 4, 2, 3, 3, 4, 3};
    private int[] colors = {0xFF575757, 0xFF888888, 0xFFAEAEAE, 0xFFCECECE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        radarView = (RadarView) findViewById(R.id.radar_view);
        radarView.setTitles(titles);
        radarView.setDatas(datas);
        radarView.setBeginAngle(Math.PI / 14);
        radarView.setColors(colors);
        radarView.invalidate();
    }
}
