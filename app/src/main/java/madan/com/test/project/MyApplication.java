package madan.com.test.project;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import madan.com.test.project.utils.ActivityManagerUtil;

/**
 * Created by 山东娃 on 2016/3/4.
 */
public class MyApplication extends Application {
    private static MyApplication application = null;
    private static String TAG;
    private static String KEY = "fd953e0bc582e6b0400fb3596501c0b9";

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        TAG = getPackageName();
        Bmob.initialize(this, KEY);
    }


    public MyApplication getInstance() {
        return application;
    }

    public static String getTag() {
        return TAG;
    }

    public static Context getContext() {
        return application;
    }

    public static void exit() {
        ActivityManagerUtil.getInstance().removeAllActivity();
    }
}
