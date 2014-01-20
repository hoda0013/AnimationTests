package djh.forfun.animaniacs.Activities;

import android.app.Activity;
import android.os.Bundle;

import djh.forfun.animaniacs.R;
import djh.forfun.animaniacs.Views.LineChart;

/**
 * Created by dillonhodapp on 1/19/14.
 */
public class LineChartActivity extends Activity{

    LineChart lineChart;
    float [] data = {10,15,21,5,36,80,51,2,5,15,45,88,80,4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart_activity);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setChartData(data);
    }
}
