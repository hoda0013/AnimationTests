package djh.forfun.animaniacs.Views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import djh.forfun.animaniacs.R;

/**
 * Created by dillonhodapp on 2/5/14.
 */
public class AnimatedSearchBar extends RelativeLayout {

    Context mContext;
    RelativeLayout mSearchBarLayout;
    ObjectAnimator mAnimator = new ObjectAnimator();

    public AnimatedSearchBar(Context context) {
        super(context);
        mContext = context;
        setup();
    }

    public AnimatedSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode());
        mContext = context;
        setup();
    }

    public AnimatedSearchBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setup();
    }

    private void setup(){

        ((Activity)getContext()).getLayoutInflater().inflate(R.layout.animated_search_bar, this, true);
        mSearchBarLayout = (RelativeLayout)findViewById(R.id.searchBarContainer);

        mAnimator = ObjectAnimator.ofInt(mSearchBarLayout, "scaleX", 50, 300);
        mAnimator.setDuration(2000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();

    }

    public void startAnimation(){
        mAnimator.start();
    }
}
