package madan.com.test.project.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by 山东娃 on 2016/3/4.
 */
public class ActivityManagerUtil {
    private ArrayList<Activity> activities = new ArrayList<>();
    private static ActivityManagerUtil activityManagerUtils;

    private ActivityManagerUtil(){

    }

    public static ActivityManagerUtil getInstance(){
        if(activityManagerUtils == null){
            activityManagerUtils = new ActivityManagerUtil();
        }
        return activityManagerUtils;
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public Activity getTopActivity(){
        return activities.get(activities.size() - 1);
    }

    public void removeAllActivity(){
        for(Activity activity : activities){
            if(activity != null && !activity.isFinishing()){
                activity.finish();
            }
            activity = null;
        }
        activities.clear();
    }
}
