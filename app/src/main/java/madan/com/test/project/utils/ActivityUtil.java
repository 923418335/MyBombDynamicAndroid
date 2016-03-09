package madan.com.test.project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;

import madan.com.test.project.MyApplication;


/**
 * Created by 山东娃 on 2016/3/4.
 */
public final class ActivityUtil {
    /**
     * 获取屏幕宽和高
     * @return
     */
    public static int[] getScreenSize(){
        DisplayMetrics dm = MyApplication.getContext().getResources().getDisplayMetrics();
        return new int[]{dm.widthPixels, dm.heightPixels};
    }


    /**
     * 通过外部浏览器打开网页
     * @param url
     */
    public static void openBrowserWeb(Context context, String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 设置屏幕方向为垂直
     * @param activity
     */
    public static void setScreenVertical(Activity activity){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置屏幕方向为垂直
     * @param activity
     */
    public static void setScreenHorizontal(Activity activity){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 判断当前网络是否可用
     * @return
     */
    public static boolean hasoNetword(){
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(MyApplication.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info != null && info.isConnected()){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }


}
