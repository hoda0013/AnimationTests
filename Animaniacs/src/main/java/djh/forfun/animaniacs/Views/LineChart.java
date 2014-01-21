package djh.forfun.animaniacs.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;

import djh.forfun.animaniacs.Model.Dynamics;

/**
 * Created by dillonhodapp on 1/19/14.
 */
public class LineChart extends View {

//    private float [] dataPoints;
    private Dynamics[] dataPoints;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //This runnable runs on the UI thread
    //It works by gettin the current time, then updating each Dynamic, checking if the Dynamic is still moving, and calling a new animation frame if any points are still in motion
    private Runnable animator = new Runnable(){

        @Override
        public void run() {
            boolean scheduleNewFrame = false;
            long now = AnimationUtils.currentAnimationTimeMillis();
            //loop through the Dynamics and update their positions and check if any are still moving
            for(Dynamics dataPoint : dataPoints){
                dataPoint.update(now);
                if(!dataPoint.isAtRest()){
                    scheduleNewFrame = true;
                }
            }
            //if any points are still moving, create a new animation frame
            if(scheduleNewFrame){
                postDelayed(this, 15);
            }

            invalidate();
        }
    };

    //This method kicks off the view. datapoints are input as floats, we convert the floats to Dynamics and start the animation cycle
    public void setChartData(float[] newDataPoints){
        //does dataPoints have data and is it the same length as the data set?
        if(dataPoints == null || dataPoints.length != newDataPoints.length){

            dataPoints = new Dynamics[newDataPoints.length];
            long now = AnimationUtils.currentAnimationTimeMillis();

            //loop through the raw points and convert them to Dynamics points
            for(int i = 0; i < newDataPoints.length; i++){
                dataPoints[i] = new Dynamics(60.0f,0.4f);
                dataPoints[i].setState(newDataPoints[i], 0, now);
                dataPoints[i].setTargetPosition(newDataPoints[i]);
            }

            invalidate();
        }else{
            for(int i = 0; i < newDataPoints.length; i++){
                dataPoints[i].setTargetPosition(newDataPoints[i]);
            }

            removeCallbacks(animator);
            post(animator);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float maxValue = getMax(dataPoints);

        Path path = new Path();
        path.moveTo(getXPos(0), getYPos(dataPoints[0].getPosition(), maxValue));

        for(int i = 1; i < dataPoints.length; i++){
            path.lineTo(getXPos(i), getYPos(dataPoints[i].getPosition(), maxValue));
        }
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF33B5E5);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setShadowLayer(4,2,2,0x80000000);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();

        canvas.drawPath(path, paint);
    }

    private float getYPos(float value, float maxValue){
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        value = (value / maxValue) * height;
        value = height - value;
        value += getPaddingTop();
        return value;
    }

    private float getXPos(int pos){

        int position = getPaddingLeft() + ((getWidth() - getPaddingLeft() - getPaddingRight()) / (dataPoints.length - 1)) * pos;

        return position;
    }

    private float getMax(Dynamics[] data){
        float max = data[0].getPosition();

        for(int i = 0; i < data.length; i++){
            if(data[i].getPosition() > max){
                max = data[i].getPosition();
            }
        }
        return max;
    }


}
