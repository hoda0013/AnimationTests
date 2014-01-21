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
    private static final float GRAPH_SMOOTHNES = 0.15f;


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
        drawPath(canvas, maxValue);



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

    private void drawPath(Canvas canvas, float maxValue){

        Path path = drawSmoothPath(maxValue);
        Path roughPath = drawRoughPath(maxValue);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF33B5E5);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setShadowLayer(4,2,2,0x80000000);

        Paint paint2 = new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(0x99F08080);
        paint2.setStrokeWidth(4);
        paint2.setAntiAlias(true);
        paint.setShadowLayer(4,2,2,0x80000000);


        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();

        canvas.drawPath(path, paint);
        canvas.drawPath(roughPath, paint2);
    }

    private Path drawRoughPath(float maxValue){

        Path path = new Path();

        path.moveTo(getXPos(0), getYPos(dataPoints[0].getPosition(), maxValue));
        for(int i = 0; i < dataPoints.length; i++){
            path.lineTo(getXPos(i), getYPos(dataPoints[i].getPosition(), maxValue));
        }

        return path;
    }

    private Path drawSmoothPath(float maxValue){
        Path path = new Path();

        path.moveTo(getXPos(0), getYPos(dataPoints[0].getPosition(), maxValue));

        for (int i = 0; i < dataPoints.length - 1; i++) {
            float thisPointX = getXPos(i);
            float thisPointY = getYPos(dataPoints[i].getPosition(), maxValue);
            float nextPointX = getXPos(i + 1);
            float nextPointY = getYPos(dataPoints[si(i + 1)].getPosition(), maxValue);

            float startdiffX = (nextPointX - getXPos(si(i - 1)));
            float startdiffY = (nextPointY - getYPos(dataPoints[si(i - 1)].getPosition(), maxValue));
            float endDiffX = (getXPos(si(i + 2)) - thisPointX);
            float endDiffY = (getYPos(dataPoints[si(i + 2)].getPosition(), maxValue) - thisPointY);

            float firstControlX = thisPointX + (GRAPH_SMOOTHNES * startdiffX);
            float firstControlY = thisPointY + (GRAPH_SMOOTHNES * startdiffY);
            float secondControlX = nextPointX - (GRAPH_SMOOTHNES * endDiffX);
            float secondControlY = nextPointY - (GRAPH_SMOOTHNES * endDiffY);

            path.cubicTo(firstControlX, firstControlY, secondControlX, secondControlY, nextPointX,
                    nextPointY);
        }
        return path;
    }

    private int si(int i) {
        if (i > dataPoints.length - 1) {
            return dataPoints.length - 1;
        } else if (i < 0) {
            return 0;
        }
        return i;
    }

}
