package cn.com.cdgame.itemer;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2018/1/16 - 上午11:02
 * 注释：
 */
public class app extends Application  {

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
