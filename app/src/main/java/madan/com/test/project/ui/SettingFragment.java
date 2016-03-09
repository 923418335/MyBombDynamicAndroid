package madan.com.test.project.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import madan.com.test.R;

/**
 * Created by 山东娃 on 2016/3/9.
 */
public class SettingFragment extends Fragment {
    private View mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_setting, container, false);
        findView();
        return mRootView;
    }

    private void findView() {

    }

}
