package com.capitalbio.oemv2.myapplication.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chengkun on 15/11/20.
 */
public class Utils {

    public static boolean isMobilePhoneNumber(String phoneNumber) {

        boolean tag = true;
        String reg = "^(13[0-9]|15[012356789]|18[02367895]|14[57])[0-9]{8}$"; // 验证手机号码
        final Pattern pattern = Pattern.compile(reg);
        final Matcher mat = pattern.matcher(phoneNumber);
        if (!mat.find()) {
            tag = false;
        }
        return tag;

    }

}
