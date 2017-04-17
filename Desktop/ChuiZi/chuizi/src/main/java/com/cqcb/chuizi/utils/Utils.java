package com.cqcb.chuizi.utils;

import android.app.Activity;
import android.content.Context;

import java.util.regex.Pattern;

/**
 * Created by chen on 2017/3/24.
 */
public class Utils {

    /**
     * 获取随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public static int randomNumber(int max) {
        return randomNumber(0, max);
    }

    /**
     * 判断是否为http链接
     *
     * @return
     */
    public static boolean isHttp(String url) {
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }

    /**
     * 添加链接host地址
     *
     * @return
     */
    public static String addHttp(String url, String host) {
        if (isHttp(url)) {
            return url;
        } else {
            if (isHttp(host + url)) {
                return host + url;
            } else {
                //throw new FormatException("sdf");
                return host + url;
            }
        }

    }

    public static int getWindowWidth(Activity context){
     return context.getWindowManager().getDefaultDisplay().getWidth();
    }


    public static  int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }





}
