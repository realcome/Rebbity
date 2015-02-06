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

package com.rebbity.datastore;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tyler on 15/2/6.
 */
final class UiContextCreator implements Parcelable.Creator {
    @Override
    public Object createFromParcel(Parcel source) {
        return null;
    }

    @Override
    public Object[] newArray(int size) {
        return new Object[0];
    }

    public UiContext create(Parcel parcel) {
        return new UiContext(parcel);
    }

    public UiContext[] create(int size) {
        return new UiContext[size];
    }
}
