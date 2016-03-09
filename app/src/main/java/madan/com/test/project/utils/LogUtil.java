package madan.com.test.project.utils;

import android.util.Log;

/**
 * Created by 山东娃 on 2016/3/4.
 */
public class LogUtil {
    public static void i(String tag, String data){
        if(Constance.debug){
            Log.i(tag, data);
        }
    }


    public static void e(String tag, String data){
        if(Constance.debug){
            Log.e(tag, data);
        }
    }
}
