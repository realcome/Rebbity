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

package com.rebbity.widget.ColorPicker;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.Toast;

import com.meizu.flyme.blur.drawable.BlurDrawable;
import com.rebbity.cn.R;
import com.rebbity.common.utils.AnimationUtils;
import com.rebbity.widget.CircleButton;

/**
 * Created by Tyler on 15/2/5.
 */
public class ColorPicker extends DialogFragment{

    private final static int ANIMATION_DURING = 1000;

    private ViewGroup mRoot;

    private View mBgView;

    private int mBgColor = 0x88f8f6e9;

    private IColorPickListener mColorPickListener;

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
    }

    public void addColorPickedListener(IColorPickListener listener) {
        mColorPickListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_picker, container, false);
        
        ColorPickClickListener clickListener = new ColorPickClickListener(this);

        CircleButton localCircleButton1 = (CircleButton)view.findViewById(R.id.color_btn1);
        CircleButton localCircleButton2 = (CircleButton)view.findViewById(R.id.color_btn2);
        CircleButton localCircleButton3 = (CircleButton)view.findViewById(R.id.color_btn3);
        CircleButton localCircleButton4 = (CircleButton)view.findViewById(R.id.color_btn4);
        CircleButton localCircleButton5 = (CircleButton)view.findViewById(R.id.color_btn5);
        CircleButton localCircleButton6 = (CircleButton)view.findViewById(R.id.color_btn6);
        CircleButton localCircleButton7 = (CircleButton)view.findViewById(R.id.color_btn7);
        CircleButton localCircleButton8 = (CircleButton)view.findViewById(R.id.color_btn8);
        CircleButton localCircleButton9 = (CircleButton)view.findViewById(R.id.color_btn9);
        CircleButton localCircleButton10 = (CircleButton)view.findViewById(R.id.color_btn10);
        CircleButton localCircleButton11 = (CircleButton)view.findViewById(R.id.color_btn11);
        CircleButton localCircleButton12 = (CircleButton)view.findViewById(R.id.color_btn12);
        CircleButton localCircleButton13 = (CircleButton)view.findViewById(R.id.color_btn13);
        CircleButton localCircleButton14 = (CircleButton)view.findViewById(R.id.color_btn14);
        CircleButton localCircleButton15 = (CircleButton)view.findViewById(R.id.color_btn15);
        CircleButton localCircleButton16 = (CircleButton)view.findViewById(R.id.color_btn16);
        CircleButton localCircleButton17 = (CircleButton)view.findViewById(R.id.color_btn17);
        CircleButton localCircleButton18 = (CircleButton)view.findViewById(R.id.color_btn18);
        CircleButton localCircleButton19 = (CircleButton)view.findViewById(R.id.color_btn19);

        localCircleButton1.setOnClickListener(clickListener);
        localCircleButton2.setOnClickListener(clickListener);
        localCircleButton3.setOnClickListener(clickListener);
        localCircleButton4.setOnClickListener(clickListener);
        localCircleButton5.setOnClickListener(clickListener);
        localCircleButton6.setOnClickListener(clickListener);
        localCircleButton7.setOnClickListener(clickListener);
        localCircleButton8.setOnClickListener(clickListener);
        localCircleButton9.setOnClickListener(clickListener);
        localCircleButton10.setOnClickListener(clickListener);
        localCircleButton11.setOnClickListener(clickListener);
        localCircleButton12.setOnClickListener(clickListener);
        localCircleButton13.setOnClickListener(clickListener);
        localCircleButton14.setOnClickListener(clickListener);
        localCircleButton15.setOnClickListener(clickListener);
        localCircleButton16.setOnClickListener(clickListener);
        localCircleButton17.setOnClickListener(clickListener);
        localCircleButton18.setOnClickListener(clickListener);
        localCircleButton19.setOnClickListener(clickListener);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = getDialog().getWindow();
        BlurDrawable winbg = new BlurDrawable();
        winbg.setColorFilter(mBgColor, PorterDuff.Mode.SRC_OVER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mBgView = new View(getActivity());
        mBgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mBgView.setBackground(winbg);
        mRoot = (ViewGroup) getActivity().getWindow().getDecorView();
        mRoot.addView(mBgView);

        view.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                startExitAnimation();
                return true;
            }
        });
    }

    public void notifyPickedColor(int color, boolean isOk) {
        if (mColorPickListener != null) {
            mColorPickListener.notifyColorPicked(color, isOk);
        }

        dismiss();
        startExitAnimation();
    }

    private void startExitAnimation() {
        AnimationUtils.animateAlpha(mBgView, 1f, 0f, ANIMATION_DURING, new Runnable() {
            @Override
            public void run() {
                mRoot.removeView(mBgView);
                startExitAnimation();
            }
        });
    }

    private void startEnterAnimation() {
        AnimationUtils.animateAlpha(mBgView, 0f, 1f, ANIMATION_DURING, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        startEnterAnimation();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        startExitAnimation();
    }

    class ColorPickClickListener implements View.OnClickListener {
        private ColorPicker picker_;
        public ColorPickClickListener(ColorPicker picker) {
            picker_ = picker;
        }

        @Override
        public void onClick(View v) {
            if (picker_ == null) {
                return;
            }

            if (v.getTag().toString().contains("#"))
            {
                int color = 0xffffffff;
                boolean isOk = true;
                try {
                    color = Color.parseColor(v.getTag().toString());
                } catch (Exception e) {
                    Log.e("ColorPicker", "Convert color error.");
                    isOk = false;
                }

                picker_.notifyPickedColor(color, isOk);
            }
        }
    }
}
