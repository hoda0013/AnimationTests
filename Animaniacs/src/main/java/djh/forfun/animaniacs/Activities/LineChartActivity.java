package djh.forfun.animaniacs.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import djh.forfun.animaniacs.R;
import djh.forfun.animaniacs.Views.LineChart;

/**
 * Created by dillonhodapp on 1/19/14.
 */
public class LineChartActivity extends Activity implements View.OnClickListener{

    LineChart lineChart;
    float [] runData = {10,15,21,5,36,80,51,2,5,15,45,88,80,4};
    float [] jumpData = {90,53,5,16,84,32,99,75,14,25,88,40,9,66};
    float [] flyData = {99,14,80,20,75,30,60,40,90,80,50,20,2,18};

    Button buttonRun;
    Button buttonJump;
    Button buttonFly;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart_activity);

        buttonRun = (Button)findViewById(R.id.buttonRun);
        buttonRun.setOnClickListener(this);
        buttonJump = (Button)findViewById(R.id.buttonJump);
        buttonJump.setOnClickListener(this);
        buttonFly = (Button)findViewById(R.id.buttonFly);
        buttonFly.setOnClickListener(this);
        textView = (TextView)findViewById(R.id.textView);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setChartData(runData);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.buttonRun:
                textView.setText("Run Data");
                lineChart.setChartData(runData);
                break;

            case R.id.buttonJump:
                textView.setText("Jump Data");
                lineChart.setChartData(jumpData);
                break;

            case R.id.buttonFly:
                textView.setText("Fly Data");
                lineChart.setChartData(flyData);
                break;
        }
    }
}
