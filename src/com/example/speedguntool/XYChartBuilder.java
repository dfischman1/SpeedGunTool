package com.example.speedguntool;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
//import org.achartengine.chartdemo.demo.R;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.chart.BarChart.Type;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Intent;

public class XYChartBuilder extends Activity {
  /** The main dataset that includes all the series that go into a chart. */
  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
  /** The main renderer that includes all the renderers customizing a chart. */
  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
  /** The most recently added series. */
  private XYSeries mCurrentSeries;
  /** The most recently created renderer, customizing the current series. */
  private XYSeriesRenderer mCurrentRenderer;
  /** The chart view that displays the data. */
  private GraphicalView mChartView;

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    // save the current data, for instance when changing screen orientation
    outState.putSerializable("dataset", mDataset);
    outState.putSerializable("renderer", mRenderer);
    outState.putSerializable("current_series", mCurrentSeries);
    outState.putSerializable("current_renderer", mCurrentRenderer);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedState) {
    super.onRestoreInstanceState(savedState);
    // restore the current data, for instance when changing the screen
    // orientation
    mDataset = (XYMultipleSeriesDataset) savedState.getSerializable("dataset");
    mRenderer = (XYMultipleSeriesRenderer) savedState.getSerializable("renderer");
    mCurrentSeries = (XYSeries) savedState.getSerializable("current_series");
    mCurrentRenderer = (XYSeriesRenderer) savedState.getSerializable("current_renderer");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.xy_chart);

    // set some properties on the main renderer
    mRenderer.setApplyBackgroundColor(true);
    mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
    mRenderer.setAxisTitleTextSize(16);
    mRenderer.setChartTitleTextSize(20);
    mRenderer.setLabelsTextSize(15);
    //mRenderer.setLegendTextSize(15);
    mRenderer.setShowLegend(false);
    mRenderer.setChartTitle("Speeding in this area");
    mRenderer.setXTitle("Speed");
    mRenderer.setYTitle("Number of drivers");
    mRenderer.setMargins(new int[] { 40, 40, 20, 10 });
    mRenderer.setZoomEnabled(false, false);
    //mRenderer.setZoomButtonsVisible(true);
    mRenderer.setPointSize(5);

    Intent intent = getIntent();
    int yAxisMax = intent.getIntExtra(MainActivity.MAX_FREQ, 1);
    mRenderer.setYAxisMin(0);
    mRenderer.setYAxisMax(yAxisMax);
    mRenderer.setPanEnabled(false);

    // Create a new series
    String seriesTitle = "Drivers' speeds";
    // create a new series of data
    XYSeries series = new XYSeries(seriesTitle);
    mDataset.addSeries(series);
    mCurrentSeries = series;
    // create a new renderer for the new series
    XYSeriesRenderer renderer = new XYSeriesRenderer();
    mRenderer.addSeriesRenderer(renderer);
    // set some renderer properties
    renderer.setPointStyle(PointStyle.CIRCLE);
    renderer.setFillPoints(true);
    //renderer.setDisplayChartValues(true);
    //renderer.setDisplayChartValuesDistance(10);
    mCurrentRenderer = renderer;

    // Create the chart view
    LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
    mChartView = ChartFactory.getBarChartView(this, mDataset, mRenderer, Type.DEFAULT);
    layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
                                                LayoutParams.FILL_PARENT));

    // Get the data from the data entry activity
    int[] speeds = intent.getIntArrayExtra(MainActivity.DATA);

    for (int i = 0; i < speeds.length; i++) {
        // add a new data point to the current series
        mCurrentSeries.add(i, speeds[i]);
    }

    mChartView.repaint();

  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mChartView == null) {
      LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
      mChartView = ChartFactory.getLineChartView(this, mDataset, mRenderer);
      // enable the chart click events
      mRenderer.setClickEnabled(true);
      mRenderer.setSelectableBuffer(10);
      mChartView.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          // handle the click event on the chart
          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
          if (seriesSelection == null) {
            Toast.makeText(XYChartBuilder.this, "No chart element", Toast.LENGTH_SHORT).show();
          } else {
            // display information of the clicked point
            Toast.makeText(
                XYChartBuilder.this,
                "Chart element in series index " + seriesSelection.getSeriesIndex()
                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
                    + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
          }
        }
      });
      layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
          LayoutParams.FILL_PARENT));
    } else {
      mChartView.repaint();
    }
  }

}
