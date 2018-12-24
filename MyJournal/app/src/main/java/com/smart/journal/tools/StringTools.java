package com.smart.journal.tools;

import android.text.TextUtils;

/**
 * @author guandongchen
 * @date 2018/1/24
 */

public class StringTools {

    public static String getNotNullString(String str){

        return TextUtils.isEmpty(str)?"":str;
    }
}
