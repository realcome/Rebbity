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

package com.rebbity.common.utils;

import android.content.Context;
import android.content.res.Resources;

import com.rebbity.cn.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Tyler on 15/2/8.
 */
public class DateTimeUtils {
    public static String timeDiffLocalStr(long time, Context context) {
        final Calendar nowcal = Calendar.getInstance();

        final Calendar another = Calendar.getInstance();
        another.setTimeInMillis(time);

        long diff = another.getTimeInMillis() - nowcal.getTimeInMillis();

        Resources resources = context.getResources();

        long absdiff = Math.abs(diff);
        String ret;

        if (absdiff < 1000) {
            // 小于1秒，可以认为是现在
            ret = resources.getString(R.string.time_now);
        } else if (absdiff < (60 * 60 * 1000)) {
            // 1小时以内，最小单位为1分钟
            long minute = absdiff / (60 * 1000) + 1;
            if (diff > 0) { // in future.
                ret = String.valueOf(minute) + resources.getString(R.string.time_minute) + resources.getString(R.string.time_future);
            } else {
                ret = String.valueOf(minute) + resources.getString(R.string.time_minute) + resources.getString(R.string.time_ago);
            }
        } else if (absdiff < (24 * 60 * 60 * 1000)) {
            // 一天以内，最小单位小时
            long hour = absdiff / (60 * 60 * 1000) + 1;

            final Calendar now = Calendar.getInstance();

            long today_hour = now.get(Calendar.HOUR_OF_DAY);

            if (diff > 0) { // in future.
                if ((today_hour + hour) >= 24) {
                    ret = resources.getString(R.string.time_tomorrow);
                } else {
                    ret = String.valueOf(hour) + resources.getString(R.string.time_hour) + resources.getString(R.string.time_future);
                }
            } else {
                if ((today_hour - hour) < 0) {
                    ret = resources.getString(R.string.time_yesterday);
                } else {
                    ret = String.valueOf(hour) + resources.getString(R.string.time_hour) + resources.getString(R.string.time_ago);
                }
            }
        } else if (absdiff < (30 * 24 * 60 * 60 * 1000)) {
            // 一个月以内，按天计算
            long day = absdiff / (24 * 60 * 60 * 1000) + 1;

            if (diff > 0) {
                ret = String.valueOf(day) + resources.getString(R.string.time_day) + resources.getString(R.string.time_future);
            } else {
                ret = String.valueOf(day) + resources.getString(R.string.time_day) + resources.getString(R.string.time_ago);
            }
        } else {
            // TODO 更加详细的计算几个月前后还是去年等。
//            long day = absdiff / (24 * 60 * 60 * 1000) + 1;
//            final Calendar now = Calendar.getInstance();
//            long day_of_year = now.get(Calendar.DAY_OF_YEAR);
//            // 一年之内按月计算
//            if ((day_of_year - day) < 0) {
//                // 去年？？
//            } else if ((day + day_of_year) > now.get(Calendar.))
            ret = "" + another.get(Calendar.YEAR) + "-" + another.get(Calendar.MONTH) + "-" + another.get(Calendar.DAY_OF_MONTH);
        }

        return ret;
    }
}
