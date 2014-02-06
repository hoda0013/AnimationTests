package djh.forfun.animaniacs.Activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import djh.forfun.animaniacs.R;
import djh.forfun.animaniacs.Views.AnimatedSearchBar;

/**
 * Created by dillonhodapp on 2/5/14.
 */
public class SearchActivity extends Activity {

    RelativeLayout searchBar;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scale_animation_test);

        if(savedInstanceState != null){

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchBar = (RelativeLayout)findViewById(R.id.box);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ObjectAnimator  animator = ObjectAnimator.ofFloat(searchBar, "scaleX", 2);
//                    animator.setDuration(2000);
//                    animator.start();
//                    isOpen = true;




                AnimatorSet set1 = new AnimatorSet();
                ObjectAnimator halfFlip = ObjectAnimator.ofFloat(searchBar, "rotationX", -105);
                set1.play(halfFlip);
                set1.setDuration(1000);

                AnimatorSet set2 = new AnimatorSet();
                ObjectAnimator translate = ObjectAnimator.ofFloat(searchBar, "translateY", 800);
                ObjectAnimator rotateX = ObjectAnimator.ofFloat(searchBar, "rotateX", 360);
                set2.setDuration(2000);
                set2.playTogether(translate, rotateX);

                AnimatorSet s3 = new AnimatorSet();
                s3.play(set1).before(set2);
//                s3.start();
//
//                AnimatorSet set = new AnimatorSet();
//                set.playTogether(
//                        ObjectAnimator.ofFloat(searchBar, "translationY", 0.0f, 800.0f),
//                        ObjectAnimator.ofFloat(searchBar, "rotationX", -540)
////                        ObjectAnimator.ofFloat(searchBar, "rotationY", 180)
//                );
//
//
//                set.setInterpolator(new AccelerateInterpolator());
//                set.setDuration(2000);
//
//                set1.play(set1).before(set);


            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
