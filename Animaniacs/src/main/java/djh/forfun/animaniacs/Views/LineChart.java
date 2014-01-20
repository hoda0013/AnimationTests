package djh.forfun.animaniacs.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dillonhodapp on 1/19/14.
 */
public class LineChart extends View {

    private float [] dataPoints;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float maxValue = getMax(dataPoints);

        Path path = new Path();
        path.moveTo(getXPos(0), getYPos(dataPoints[0], maxValue));

        for(int i = 1; i < dataPoints.length; i++){
            path.lineTo(getXPos(i), getYPos(dataPoints[i], maxValue));
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

    public void setChartData(float[] dataPoints){
        this.dataPoints = dataPoints.clone();
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

    private float getMax(float[] data){
        float max = data[0];

        for(int i = 0; i < data.length; i++){
            if(data[i] > max){
                max = data[i];
            }
        }
        return max;
    }
}
