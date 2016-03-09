package madan.com.test.project.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telecom.ConnectionService;

import madan.com.test.project.MyApplication;
import madan.com.test.project.ui.MainActivity;

/**
 * Created by 山东娃 on 2016/3/6.
 */
public class NetworkUtil {
    public static boolean isAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(MyApplication.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }
}
