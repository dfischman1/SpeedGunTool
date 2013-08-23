package com.example.speedguntool;
 
import java.util.ArrayList;
import java.util.Random;
 
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
 
import android.os.Bundle;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
//import android.view.Menu;

public class XYChartBuilder extends Activity {
	public ArrayList <String> graphdata;
    //private static final int SERIES_NR = 2;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        graphdata = bundle.getStringArrayList("com.example.speedguntool.data");
        Log.e("OnCreate", "recieved Intent");
        setContentView(R.layout.xy_chart);
        Log.e("SetContentView", "Created view");
        XYMultipleSeriesRenderer renderer = getTruitonBarRenderer();
        myChartSettings(renderer);
        Intent intent = ChartFactory.getBarChartIntent(this, getTruitonBarDataset(), renderer, Type.DEFAULT);
        startActivity(intent);
        Log.e("StartActivity", "start activity");
    }
 
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.truiton_achart_engine, menu);
        return true;
    }
 */
    private XYMultipleSeriesDataset getTruitonBarDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 6;
        Random r = new Random();
        //ArrayList <String> Speeds = new ArrayList <String>();
        //Speeds.add("Speeds");
        CategorySeries series = new CategorySeries("Speeds");
        for (int k = 0; k < nr; k++) {
                series.add(graphdata.get(k), 3);
            }
            dataset.addSeries(series.toXYSeries());
       
        return dataset;
    }
 
    public XYMultipleSeriesRenderer getTruitonBarRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[] { 30, 40, 15, 0 });
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);
        r = new SimpleSeriesRenderer();
        r.setColor(Color.RED);
        renderer.addSeriesRenderer(r);
        return renderer;
    }
 
    private void myChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setChartTitle("Your Measured Speeds");
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(10.5);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(210);
        renderer.addXTextLabel(1, "0-10");
        renderer.addXTextLabel(2, "10-20");
        renderer.addXTextLabel(3, "20-30");
        renderer.addXTextLabel(4, "30-40");
        renderer.addXTextLabel(5, "40-50");
        renderer.addXTextLabel(6, "50+");
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setBarSpacing(0.5);
        renderer.setXTitle("Speeds");
        renderer.setYTitle("Number of Cars");
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setXLabels(0); // sets the number of integer labels to appear
    }
 
}
