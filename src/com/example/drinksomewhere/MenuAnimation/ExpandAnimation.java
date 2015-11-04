

package com.example.drinksomewhere.MenuAnimation;

import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.FrameLayout.LayoutParams;

public class ExpandAnimation extends TranslateAnimation implements Animation.AnimationListener{

	private RelativeLayout slidingLayout;
	int panelWidth;
	
	public ExpandAnimation(RelativeLayout layout, int width, int fromXType, float fromXValue, int toXType,
			float toXValue, int fromYType, float fromYValue, int toYType, float toYValue) {
		
		super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
		
		//Initialize
		slidingLayout = layout;
		panelWidth = width;
		setDuration(400);
  	    setFillAfter( false );
  	    setInterpolator(new AccelerateDecelerateInterpolator());
  	    setAnimationListener(this);
  	    slidingLayout.startAnimation(this);
	}


	public void onAnimationEnd(Animation arg0) {
		
		//Create margin and align left
		LayoutParams params = (LayoutParams) slidingLayout.getLayoutParams();
  	   	params.leftMargin = panelWidth;
  	   	params.gravity = Gravity.LEFT;	   
  	   	slidingLayout.clearAnimation();
  	   	slidingLayout.setLayoutParams(params);
  	   	slidingLayout.requestLayout();
  	  			
	}

	public void onAnimationRepeat(Animation arg0) {
		
	}

	public void onAnimationStart(Animation arg0) {
		
	}

}
