/*
 * Copyright (c) 2015.
 *
 * If you want to use this file, you must retain this part of the statement。
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rebbity.common.animations;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by Tyler on 15/2/6.
 */
public class CircularRevealAnimation {
//    public static final int DURATION = 500;
//    PointF circleCernter = new PointF();
//    Path circlePath = new Path();
//    float circleRadius;
//    ViewGroup collapsedView;
//    ViewGroup expandedView;
//    ValueAnimator radius;
//    RelativeLayout rootContainer;
//
//    public CircularRevealAnimation(ViewGroup paramViewGroup1, ViewGroup paramViewGroup2, RelativeLayout paramRelativeLayout)
//    {
//        this.collapsedView = paramViewGroup1;
//        this.expandedView = paramViewGroup2;
//        this.rootContainer = paramRelativeLayout;
//    }
//
//    private void animateCircleToCenter(View paramView, float paramFloat)
//    {
//        float[] arrayOfFloat1 = new float[2];
//        arrayOfFloat1[0] = this.circleCernter.x;
//        arrayOfFloat1[1] = (paramFloat / 2F);
//        ValueAnimator localValueAnimator1 = ValueAnimator.ofFloat(arrayOfFloat1);
//        localValueAnimator1.setInterpolator(new LinearInterpolator());
//        localValueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this)
//        {
//            public void onAnimationUpdate()
//            {
//                this.this$0.circleCernter.x = ((Float)paramValueAnimator.getAnimatedValue()).floatValue();
//            }
//        });
//        localValueAnimator1.setInterpolator(new AccelerateInterpolator(0.80000001192092895508F));
//        localValueAnimator1.setDuration(500L);
//        localValueAnimator1.start();
//        float f = this.collapsedView.getResources().getDimension(2131296297);
//        float[] arrayOfFloat2 = new float[2];
//        arrayOfFloat2[0] = this.circleCernter.y;
//        arrayOfFloat2[1] = (f / 2F);
//        ValueAnimator localValueAnimator2 = ValueAnimator.ofFloat(arrayOfFloat2);
//        localValueAnimator2.setInterpolator(new DecelerateInterpolator(1.5F));
//        localValueAnimator2.setDuration(500L);
//        localValueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this)
//        {
//            public void onAnimationUpdate()
//            {
//                this.circleCernter.y = ((Float)paramValueAnimator.getAnimatedValue()).floatValue();
//                CircularRevealHelper.access$000(this.this$0);
//            }
//        });
//        localValueAnimator2.start();
//        float[] arrayOfFloat3 = new float[2];
//        arrayOfFloat3[0] = this.circleRadius;
//        arrayOfFloat3[1] = f;
//        this.radius = ValueAnimator.ofFloat(arrayOfFloat3);
//        this.radius.setInterpolator(new AccelerateInterpolator(2.70000004768371582031F));
//        this.radius.setDuration(500L);
//        this.radius.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this)
//        {
//            public void onAnimationUpdate()
//            {
//                this._circleRadius = ((Float)paramValueAnimator.getAnimatedValue()).floatValue();
//                CircularRevealHelper.access$000(this.this$0);
//            }
//        });
//        this.radius.start();
//        this.radius.addListener(new AnimatorListenerAdapter(this, paramView)
//        {
//            public void onAnimationEnd()
//            {
//                super.onAnimationEnd(paramAnimator);
//                this.val$charm.setHasTransientState(false);
//                this.val$charm.findViewById(2131361989).animate().alpha(1F).setDuration(500L).start();
//                ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this.val$charm, View.ROTATION, new float[] { 180.0F, 0F }).setDuration(1500L);
//                localObjectAnimator.setInterpolator(new QuintIn());
//                localObjectAnimator.start();
//                ObjectAnimator.ofFloat(this.val$charm, View.ALPHA, new float[] { 0F, 1F }).setDuration(250L).start();
//            }
//        });
//        scaleExpandedContent();
//    }
//
//    private void doCircularReveal(View paramView, float paramFloat)
//    {
//        int i = paramView.getHeight() / 2;
//        this.circleCernter = new PointF(paramView.getLeft() + paramView.getWidth() / 2, -i + paramView.getHeight() / 2);
//        float f = paramView.getY();
//        float[] arrayOfFloat = new float[2];
//        arrayOfFloat[0] = paramView.getTranslationY();
//        arrayOfFloat[1] = (paramView.getTranslationY() + i);
//        ValueAnimator localValueAnimator = ValueAnimator.ofFloat(arrayOfFloat).setDuration(138L);
//        localValueAnimator.setInterpolator(new LinearInterpolator());
//        localValueAnimator.start();
//        localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this, i, paramView)
//        {
//            public void onAnimationUpdate()
//            {
//                this.this$0.circleCernter.y = (-this.val$charmTranslateToEnter + ((Float)paramValueAnimator.getAnimatedValue()).floatValue() - this.val$charm.getY());
//                CircularRevealHelper.access$000(this.this$0);
//            }
//        });
//        paramView.setAlpha(0F);
//        localValueAnimator.addListener(new AnimatorListenerAdapter(this, paramView, paramFloat, f)
//        {
//            public void onAnimationEnd()
//            {
//                super.onAnimationEnd(paramAnimator);
//                CircularRevealHelper.access$100(this.this$0, this.val$charm, this.val$containerWidth);
//                this.val$charm.setY(this.val$yAtSTart);
//                this.val$charm.setAlpha(0F);
//            }
//        });
//    }
//
//    private void redrawCircle()
//    {
//        this.circlePath.reset();
//        this.circlePath.addCircle(this.circleCernter.x, this.circleCernter.y, this.circleRadius, Path.Direction.CCW);
//        this.rootContainer.invalidate();
//    }
//
//    private void scaleExpandedContent()
//    {
//        View localView = this.expandedView.getChildAt(0);
//        localView.setAlpha(0F);
//        ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(localView, View.ALPHA, new float[] { 0F, 1F }).setDuration(250L);
//        localObjectAnimator1.setStartDelay(250L);
//        localObjectAnimator1.start();
//        ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(localView, View.SCALE_X, new float[] { 0.5F, 1F }).setDuration(500L);
//        localObjectAnimator2.setStartDelay(250L);
//        localObjectAnimator2.setInterpolator(new QuintIn());
//        localObjectAnimator2.start();
//        ObjectAnimator localObjectAnimator3 = ObjectAnimator.ofFloat(localView, View.SCALE_Y, new float[] { 0.5F, 1F }).setDuration(500L);
//        localObjectAnimator3.setStartDelay(250L);
//        localObjectAnimator3.setInterpolator(new QuintIn());
//        localObjectAnimator3.addListener(new AnimatorListenerAdapter(this)
//        {
//            public void onAnimationEnd()
//            {
//                super.onAnimationEnd(paramAnimator);
//                this.this$0.rootContainer.setWillNotDraw(true);
//            }
//        });
//        localObjectAnimator3.start();
//    }
//
//    public void circularReveal(View paramView, float paramFloat)
//    {
//        this.collapsedView.setVisibility(4);
//        this.expandedView.setVisibility(0);
//        paramView.findViewById(2131361989).animate().alpha(0F).setDuration(500L).start();
//        this.rootContainer.setWillNotDraw(false);
//        this.circleRadius = (paramView.getWidth() / 2);
//        int i = (int)this.collapsedView.getResources().getDimension(2131296297);
//        ExpandAnimHelper localExpandAnimHelper = new ExpandAnimHelper(this.collapsedView, this.expandedView, this.rootContainer);
//        doCircularReveal(paramView, paramFloat);
//        localExpandAnimHelper.animateContainerHeight(this.collapsedView.getHeight(), i);
//    }
//
//    public Path getClipPath()
//    {
//        return this.circlePath;
//    }
//
//    public boolean isRunning()
//    {
//        return ((this.radius != null) && (this.radius.isRunning()));
//    }
}
