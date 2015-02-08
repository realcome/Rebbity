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

package com.rebbity.reader;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Method;

/**
 * Created by Tyler on 15/2/6.
 */
public class BaseWebView extends WebView {

    private static Method method_getVisibleTitleHeight;


    public BaseWebView(Context context) {
        super(context);
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        try
        {
            method_getVisibleTitleHeight = WebView.class.getDeclaredMethod("getVisibleTitleHeight", new Class[0]);
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {

        }

        WebSettings settings = getSettings();

        settings.setJavaScriptEnabled(true);
    }

//    private int getViewHeightWithTitle()  {
//        int i = getHeight();
//        if ((isHorizontalScrollBarEnabled()) && (!(overlayHorizontalScrollbar())))
//            i -= getHorizontalScrollbarHeight();
//        return i;
//    }
//
//    private void setEmbeddedTitleBarJellyBean(View paramView)
//    {
//        int i;
//        if (this.h == paramView)
//            return;
//        if (this.h != null)
//            removeView(this.h);
//        if (paramView != null)
//        {
//            ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
//            if ((localLayoutParams == null) || (localLayoutParams.height <= 0))
//                break label112;
//            i = localLayoutParams.height;
//        }
//        while (true)
//        {
//            FrameLayout.LayoutParams localLayoutParams1 = new FrameLayout.LayoutParams(-1, i);
//            AbsoluteLayout.LayoutParams localLayoutParams2 = new AbsoluteLayout.LayoutParams(-1, -2, 0, 0);
//            FrameLayout localFrameLayout = new FrameLayout(getContext());
//            localFrameLayout.addView(paramView, localLayoutParams1);
//            addView(localFrameLayout, localLayoutParams2);
//            paramView = localFrameLayout;
//            this.h = paramView;
//            return;
//            label112: i = -2;
//        }
//    }
//
//    @Override
//    protected int computeVerticalScrollExtent() {
//        return super.computeVerticalScrollExtent();
//    }
//
//    @Override
//    protected int computeVerticalScrollOffset() {
//        return super.computeVerticalScrollOffset();
//    }
//
//    @Override
//    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
//        canvas.save();
//        if (child == this.h)
//        {
//            this.h.offsetLeftAndRight(getScrollX() - this.h.getLeft());
//            if (Build.VERSION.SDK_INT < 16)
//            {
//                this.b.set(canvas.getMatrix());
//                this.b.postTranslate(0F, -getScrollY());
//                canvas.setMatrix(this.b);
//            }
//        }
//        boolean bool = super.drawChild(canvas, paramView, paramLong);
//        canvas.restore();
//        return bool;
//    }
//
//    public int getTitleHeight() {
//        if (this.h != null)
//            return this.h.getHeight();
//        return 0;
//    }
//
//    public int getVisibleTitleHeightCompat() {
//        if ((this.h == null) && (c != null))
//            try
//            {
//                int i = ((Integer)c.invoke(this, new Object[0])).intValue();
//                return i;
//            }
//            catch (Exception localException)
//            {
//            }
//        return Math.max(getTitleHeight() - Math.max(0, getScrollY()), 0);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (Build.VERSION.SDK_INT >= 16) {
//            super.onDraw(canvas);
//            return;
//        }
//    }
}
