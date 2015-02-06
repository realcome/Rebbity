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
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rebbity.cn.R;
import com.rebbity.widget.CircleButton;

/**
 * Created by Tyler on 15/2/5.
 */
public class ColorPicker extends DialogFragment{

    private int mBgColor = 0xfff8f6e9;

    public EditText mCustomColor;

    private Button mUseButton;

    private ColorPickListener mColorPickListener;

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
    }

    public void addColorPickedListener(ColorPickListener listener) {
        mColorPickListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_picker, container, false);

        mCustomColor = (EditText) view.findViewById(R.id.color_custom_edit);
        CustomTextWatcher watcher = new CustomTextWatcher(this);
        mCustomColor.addTextChangedListener(watcher);


        ButtonClickListener clickListener = new ButtonClickListener(this);
        mUseButton = (Button) view.findViewById(R.id.color_custom_use);
        mUseButton.setEnabled(false);
        mUseButton.setOnClickListener(clickListener);

        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(mBgColor));

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
        view.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                //mRoot.removeView(mBgView);
                return true;
            }
        });
    }

    public void notifyPickedColor(int color, boolean isOk) {
        if (mColorPickListener != null) {
            mColorPickListener.notifyColorPicked(color, isOk);
        }

        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public void notifyCustomColor(int color, boolean isOk) {
        if (isOk) {
            if (mUseButton != null) {
                mUseButton.setBackgroundColor(color);
                mUseButton.setEnabled(true);
            }
        } else {
            if (mUseButton != null) {
                mUseButton.setBackgroundColor(0);
                mUseButton.setEnabled(false);
            }
        }
    }

    class ButtonClickListener implements View.OnClickListener {
        private ColorPicker picker_;
        public ButtonClickListener(ColorPicker picker) {
            picker_ = picker;
        }

        @Override
        public void onClick(View v) {
            int viewID = v.getId();
            int color = 0xffffffff;
            boolean isOk = true;

            switch (viewID) {
                case R.id.color_custom_use:
                    if (picker_ == null) {
                        break;
                    }

                    String str = picker_.mCustomColor.getText().toString();

                    try {
                        if (str.length() == 3)
                        {
                            String str1 = str.substring(0, 1);
                            String str2 = str.substring(1, 2);
                            String str3 = str.substring(2, 3);
                            str = str1 + str1 + str2 + str2 + str3 + str3;
                        }
                        color = Color.parseColor("#" + str);
                    } catch (Exception e) {
                        Log.e("ColorPicker", "Convert color error.");
                        isOk = false;
                    }

                    picker_.notifyPickedColor(color, isOk);
                    break;
                default:
                    if (picker_ == null) {
                        break;
                    }

                    if (v.getTag().toString().contains("#"))
                    {
                        try {
                            color = Color.parseColor(v.getTag().toString());
                        } catch (Exception e) {
                            Log.e("ColorPicker", "Convert color error.");
                            isOk = false;
                        }

                        picker_.notifyPickedColor(color, isOk);
                    }
                    break;
            }
        }
    }

    class CustomTextWatcher implements TextWatcher {
        ColorPicker picker_;

        public CustomTextWatcher(ColorPicker picker) {
            picker_ = picker;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (picker_ == null) {
                return;
            }

            int color = 0xffffffff;
            boolean isOk = true;
            String str = s.toString();

            try {
                if (str.length() == 3)
                {
                    String str1 = str.substring(0, 1);
                    String str2 = str.substring(1, 2);
                    String str3 = str.substring(2, 3);
                    str = str1 + str1 + str2 + str2 + str3 + str3;
                }
                color = Color.parseColor("#" + str);
            } catch (Exception e) {
                Log.e("ColorPicker", "Convert color error.");
                isOk = false;
            }

            picker_.notifyCustomColor(color, isOk);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    }
}