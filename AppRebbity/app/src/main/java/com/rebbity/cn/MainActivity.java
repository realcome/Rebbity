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

package com.rebbity.cn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.rebbity.common.utils.WindowUtils;
import com.rebbity.widget.ColorPicker.ColorPickListener;
import com.rebbity.widget.ColorPicker.ColorPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowUtils.setDarkStatusIconForFlyme(this, true);
        setToolbarLogo(R.drawable.toolbar_logo);
        hideTitle();

        List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
        for (int i = 1; i <= 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (i % 2 == 0) {
                map.put("ICON", R.drawable.jpeg6);
                map.put("TITLE", i + "  Test Title one");
                map.put("CONTENT", "Test Content one");
            } else {
                map.put("ICON", R.drawable.jpeg7);
                map.put("TITLE", i + "  Test Title one");
                map.put("CONTENT", "Test Content two Test Content two");
            }
            contents.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, contents,
                R.layout.list_item,
                new String[] { "ICON", "TITLE", "CONTENT" }, new int[] {
                android.R.id.icon, android.R.id.text1,
                android.R.id.text2 });

        ListView v = (ListView) findViewById(R.id.list);
//        v.setPadding(v.getPaddingLeft(), v.getPaddingTop() + getActionBarHeight(this), v.getPaddingRight(), v.getBottom());
        v.setAdapter(adapter);


        int appendPaddingTop = WindowUtils.getActionBarSize(this) + WindowUtils.getStatusBarHeight(this);
        int appendPaddingBottom = WindowUtils.getNavigationBarHeight(this);

        v.setPadding(v.getPaddingLeft(), v.getPaddingTop() + appendPaddingTop, v.getPaddingRight(), v.getPaddingBottom() + appendPaddingBottom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_help:

                ColorPicker colorPicker = new ColorPicker();
                ColorPickListener listener = new ColorPickListener() {
                    @Override
                    public void notifyColorPicked(int color, boolean isOk) {
                        Toast.makeText(MainActivity.this, ""+color, Toast.LENGTH_SHORT).show();
                    }
                };
                colorPicker.addColorPickedListener(listener);
                colorPicker.show(getFragmentManager(), "colorpicker");
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void pickedColor(View view) {
        showToast(this, "OOOOOO", Toast.LENGTH_LONG);
    }
}
