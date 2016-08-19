/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.capitalbio.oemv2.myapplication.FirstPeriod.Utils;

import android.content.Context;
import android.widget.Toast;

public class ToastMaster {

    private static Toast sToast = null;

    private ToastMaster() {

    }
    
    /**
     * 显示一个普通Toast<br>
     * 内容不可可被下一个 Toast 替换
     * 
     * @param context
     * @param text 内容
     * @param duration 时长
     * void
     */
    public static void showCommonToast(Context context, String text, int duration) {
        try {
            // 创建 Toast并显示
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 显示一个Toast, 时长 Toast.LENGTH_LONG<br>
     * 内容可被下一个 Toast 替换
     * 
     * @param context
     * @param text 内容
     * void
     */
    public static void showToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }
    
    /**
     * 显示一个Toast<br>
     * 内容可被下一个 Toast 替换
     * 
     * @param context
     * @param text 内容
     * @param duration 时长
     * void
     */
    public static void showToast(Context context, String text, int duration) {
        try {
            Toast toast = Toast.makeText(context, text, duration);
            
            // 替换 Toast
            setToast(toast);
            
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换 Toast
     * 
     * @param toast
     * void
     */
    public static void setToast(Toast toast) {
        if (sToast != null)
            sToast.cancel();
        sToast = toast;
    }

    /**
     * 取消 Toast 显示
     * 
     * void
     */
    public static void cancelToast() {
        if (sToast != null)
            sToast.cancel();
        sToast = null;
    }

}
