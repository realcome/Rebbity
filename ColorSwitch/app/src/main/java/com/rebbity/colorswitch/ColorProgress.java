/*
 * Copyright (C) 2015 The Rebbity Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rebbity.colorswitch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Tyler on 15/1/18.
 */
public class ColorProgress extends View {
    public ColorProgress(Context context) {
        super(context);
    }

    public ColorProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        p.setTextSize(30.0f);

        canvas.drawText("画贝塞尔曲线:", 50, 310, p);
        p.reset();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(3.0f);
        p.setColor(Color.GREEN);
        Path path2=new Path();
        RectF ret = new RectF();
        ret.set(100, 320, 1200, 600);
        path2.addArc(ret, (float)Math.PI * 1, (float)Math.PI * 2);
        canvas.drawPath(path2, p);//画出贝塞尔曲线
    }
}
