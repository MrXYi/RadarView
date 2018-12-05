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

**属性**

```html
<declare-styleable name="RadarView">
        <attr name="count_circle" format="integer|reference" /><!--圈数-->
        <attr name="count_angle" format="integer|reference" /><!--角数-->
        <attr name="max_value" format="float|reference" /><!--最大值（数据）-->
        <attr name="radar_color" format="color|reference" /><!--网格边线颜色-->
        <attr name="text_color" format="color|reference" /><!--文字颜色-->
        <attr name="line_color" format="color|reference" /><!--网格中线颜色-->
        <attr name="value_color" format="color|reference" /><!--数据描线颜色-->
        <attr name="text_size" format="dimension|reference" /><!--文字大小-->
        <attr name="is_full_radar" format="boolean|reference" /><!--是否填充方式绘制网格-->
</declare-styleable>
```





## 需求

有其他需求、控件有bug时可发邮件到邮箱（xiaoyi.hn@foxmail.com）