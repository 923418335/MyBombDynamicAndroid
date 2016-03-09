package madan.com.test.project.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import madan.com.test.R;

/**
 * Created by 山东娃 on 2016/3/9.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private CircleImageView mIcView;
    private TextView mSexView;
    private TextView mUserNameView;
    private TextView mAutographView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_setting, container, false);
        findView();
        setListener();
        return mRootView;
    }


    private void findView() {
        mIcView = (CircleImageView) mRootView.findViewById(R.id.mIcSetting);
        mSexView = (TextView) mRootView.findViewById(R.id.mSexSetting);
        mUserNameView = (TextView) mRootView.findViewById(R.id.mUserNameSetting);
        mAutographView = (TextView) mRootView.findViewById(R.id.mAutographSetting);
    }

    private void setListener() {
        mIcView.setOnClickListener(this);
        mSexView.setOnClickListener(this);
        mUserNameView.setOnClickListener(this);
        mAutographView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mIcSetting:
                //点击头像
                break;
            case R.id.mSexSetting:
                //点击性别
                break;
            case R.id.mUserNameSetting:
                //点击用户名
                break;
            case R.id.mAutographSetting:
                //点击个人签名
                break;
        }
    }
}
