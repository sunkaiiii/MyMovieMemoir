package com.example.mymoviememoir.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.example.mymoviememoir.R;

/**
 * @author sunkai
 */
public class RatingSelectView extends LinearLayout {
    private double rating = -1f;

    public RatingSelectView(Context context) {
        super(context);
        initView(context);
    }

    public RatingSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RatingSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                generateImageViews(context);
                setTouchListener();
            }
        });
    }

    public void setRating(double rating) {
        this.rating = rating;
        generateImageViews(getContext());
    }

    private void generateImageViews(Context context) {
        double r = rating;
        ImageView[] imageViews = getImageViews();
        for (int i = 0; i < getChildCount(); i++) {
            ImageView imageView = imageViews[i];
            if (r >= 1) {
                imageView.setImageResource(R.drawable.baseline_star_black_48);
            } else if (r > 0) {
                imageView.setImageResource(R.drawable.baseline_star_half_black_48);
            } else {
                imageView.setImageResource(R.drawable.baseline_star_border_black_48);
            }
            r--;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.secondaryColor)));
            }
        }
    }

    private ImageView[] getImageViews() {
        ImageView[] imageViews = new ImageView[5];
        if (getChildCount() == 5) {
            for (int i = 0; i < getChildCount(); i++) {
                imageViews[i] = (ImageView) getChildAt(i);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                ImageView imageView = new ImageView(getContext());
                final LayoutParams layoutParams;
                if (getLayoutParams().height > 0) {
                    layoutParams = new LayoutParams(getLayoutParams().height, getLayoutParams().height);
                } else {
                    layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageViews[i] = imageView;
                addView(imageView);
            }
        }
        return imageViews;
    }

    private void setTouchListener() {
        setOnTouchListener((v, event) -> {
            rating = 0;
            for (int i = 0; i < getChildCount(); i++) {
                ImageView imageView = (ImageView) getChildAt(i);
                SpringAnimation animationX = new SpringAnimation(imageView, SpringAnimation.SCALE_X, 1.0f);
                SpringAnimation animationY = new SpringAnimation(imageView, SpringAnimation.SCALE_Y, 1.0f);
                animationX.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
                animationX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
                animationX.setStartValue(0.7f);
                animationY.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
                animationY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
                animationY.setStartValue(0.7f);
                int render = (int) (event.getX() - imageView.getX());
                if (render > imageView.getWidth()) {
                    rating += 1;
                    imageView.setImageResource(R.drawable.baseline_star_black_48);
                    animationX.start();
                    animationY.start();
                } else if (render > 0 && render > imageView.getWidth() / 2.0) {
                    rating += 1;
                    imageView.setImageResource(R.drawable.baseline_star_black_48);
                    animationX.start();
                    animationY.start();
                } else if (render > 0 && render < imageView.getWidth() / 2.0) {
                    rating += 0.5;
                    imageView.setImageResource(R.drawable.baseline_star_half_black_48);
                } else if (event.getX() < imageView.getX() + imageView.getWidth()) {
                    imageView.setImageResource(R.drawable.baseline_star_border_black_48);
                }
            }
            performClick();
            return true;
        });
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public double getRatingScore() {
        return rating;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
}

