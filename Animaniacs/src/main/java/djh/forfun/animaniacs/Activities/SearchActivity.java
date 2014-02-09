package djh.forfun.animaniacs.Activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import djh.forfun.animaniacs.R;
import djh.forfun.animaniacs.Views.AnimatedSearchBar;

/**
 * Created by dillonhodapp on 2/5/14.
 */
public class SearchActivity extends Activity {

    AnimatorSet animatorSet1;
    AnimatorSet animatorSet2;
    AnimatorSet animatorSet3;
    AnimatorSet animatorSet4;

    RelativeLayout searchBar;
    LinearLayout animationWindow;
    TextView textView;
    boolean isOpen = false;
    ObjectAnimator halfFlip;
    ObjectAnimator swayBack;
    ObjectAnimator swayForward;
    ObjectAnimator swayBack2;
    ObjectAnimator swayForward2;
    ObjectAnimator swayBack3;
    ObjectAnimator swayForward3;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.scale_animation_test);

        if(savedInstanceState != null){

        }

    }


    public AnimatorSet makeHalfFlip(View view, int numSways){
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animatorList= new ArrayList<Animator>();

        int duration = 700;
        int startDegrees = 0;
        int endDegrees = -230;
        double delta = Math.abs(startDegrees - endDegrees);
        boolean isNegativeSwing = true;
        Animator objectAnimator;

        for(int i = 0; i < numSways - 1; i++){
            objectAnimator = (Animator)ObjectAnimator.ofFloat(view, "rotationX", endDegrees);
            objectAnimator.setDuration(duration);
            animatorList.add(objectAnimator);
            delta = delta * .4;
            startDegrees = endDegrees;

            if(isNegativeSwing){
                isNegativeSwing = false;

                endDegrees = startDegrees + (int)delta;
                if (!(endDegrees > -180)){
                    endDegrees = -180;
                    i = numSways;
                }

            }else{
                isNegativeSwing = true;
                endDegrees = startDegrees - (int)delta;
                if(!(endDegrees < -180)){
                    endDegrees = -180;
                    i = numSways;
                }
            }

        }

        objectAnimator = (Animator)ObjectAnimator.ofFloat(view, "rotationX", -180);
        objectAnimator.setDuration(duration);
        animatorList.add(objectAnimator);
        animatorSet.playSequentially(animatorList);

        return animatorSet;
    }

    public AnimatorSet makeSwinging(View view, int numSways){
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animatorList= new ArrayList<Animator>();
        boolean isNegativeSwing = true;

        Animator objectAnimator;
        int endDegrees = -45;
        int degrees = -80;

        for(int i = 0; i < numSways; i++){

            objectAnimator = (Animator)ObjectAnimator.ofFloat(view, "rotation", degrees);
            objectAnimator.setDuration(500);
            animatorList.add(objectAnimator);

            if(isNegativeSwing){
                isNegativeSwing = false;
                int toCenter = (Math.abs(degrees - endDegrees));
                int delta = toCenter + toCenter / 3;
                degrees = degrees + delta;
            }else{
                isNegativeSwing = true;
                int toCenter = (Math.abs(degrees - endDegrees));
                int delta = toCenter + toCenter / 3;
                degrees = degrees - delta;
            }
        }

        animatorSet.playSequentially(animatorList);
        return animatorSet;
    }

    public AnimatorSet makeFalling(View view, View parentView){
        AnimatorSet animatorSet = new AnimatorSet();
        Animator falling = ObjectAnimator.ofFloat(view, "translationY", 2000);
        falling.setDuration(1200);
        falling.setInterpolator(new AccelerateInterpolator());
        animatorSet.play(falling);
        return animatorSet;
    }

    public void makeAnimation(){

        //get the UI elements
        setContentView(R.layout.scale_animation_test);

        RelativeLayout leftPane = (RelativeLayout) findViewById(R.id.leftSide);
        leftPane.setPivotY(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        TextView leftPaneTV = (TextView) findViewById(R.id.leftTV);
        leftPaneTV.setText(String.valueOf(counter));

        TextView leftTVStationary = (TextView)findViewById(R.id.leftTVStationary);
        leftTVStationary.setText(String.valueOf(counter + 1));

        AnimatorSet animation = new AnimatorSet();
        if(counter < 10){
            animation.playSequentially(makeHalfFlip(leftPane, 5), makeSwinging(leftPane, 5), makeFalling(leftPane, findViewById(R.id.animationWindow)));
            animation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    counter++;
                    makeAnimation();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animation.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        makeAnimation();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {


//        super.onBackPressed();
//

    }
}
