package djh.forfun.animaniacs.Activities;

import android.app.Activity;
import android.os.Bundle;

import djh.forfun.animaniacs.R;
import djh.forfun.animaniacs.Views.DurationTextView;

/**
 * Created by dillonhodapp on 1/19/14.
 */
public class DurationTVActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duration_tv_activity);

        DurationTextView durationTextView1 = (DurationTextView) findViewById(R.id.durationView1);
        durationTextView1.setDuration(25);
        DurationTextView durationTextView2 = (DurationTextView) findViewById(R.id.durationView2);
        durationTextView2.setDuration(78);
        DurationTextView durationTextView3 = (DurationTextView) findViewById(R.id.durationView3);
        durationTextView3.setDuration(2378);
        DurationTextView durationTextView4 = (DurationTextView) findViewById(R.id.durationView4);
        durationTextView4.setDuration(3670);
        DurationTextView durationTextView5 = (DurationTextView) findViewById(R.id.durationView5);
        durationTextView5.setDuration(18550);

    }
}
