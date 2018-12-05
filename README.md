## RadarView

自定义雷达控件、蜘蛛网图（可设置各种属性） 

效果图

<img src="/pic/test.jpg" width="500" align=center />

### Usage

**Init in xml**

```html
<com.xiao.radarview.widget.RadarView
        android:id="@+id/radar_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:count_angle="7"
        app:count_circle="4"
        app:is_full_radar="true"
        app:max_value="5"
        app:text_size="15dp" />
```

**Use in java**

```java
private String[] titles = {"助攻", "物理", "魔法", "防御", "金钱", "击杀", "生存"};
    private double[] datas = {2, 4, 2, 3, 3, 4, 3};
    private int[] colors = {0xFF575757, 0xFF888888, 0xFFAEAEAE, 0xFFCECECE};
radarView = (RadarView) findViewById(R.id.radar_view);
radarView.setTitles(titles);
radarView.setDatas(datas);
radarView.setBeginAngle(Math.PI / 14);
radarView.setColors(colors);
radarView.invalidate();
```





