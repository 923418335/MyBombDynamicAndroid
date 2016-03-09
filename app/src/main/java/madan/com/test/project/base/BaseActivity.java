package madan.com.test.project.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import madan.com.test.project.MyApplication;
import madan.com.test.project.utils.ActivityManagerUtil;
import madan.com.test.project.utils.LogUtil;


/**
 * Created by 山东娃 on 2016/3/4.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(MyApplication.getTag(), getClass().getSimpleName());
        ActivityManagerUtil.getInstance().addActivity(this);
    }



    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getInstance().removeActivity(this);
    }

    protected void log(String data) {
        LogUtil.i(getClass().getSimpleName(), data);
    }

    protected void toast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
