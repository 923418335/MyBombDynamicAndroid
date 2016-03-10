package madan.com.test.project.ui;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;
import madan.com.test.R;
import madan.com.test.project.utils.FileUtil;
import madan.com.test.project.utils.LogUtil;

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
        initCallBack();
        return mRootView;
    }

    /**
     * 初始化回调接口
     */
    private void initCallBack() {
        ((MainActivity)getActivity()).setSettingCallBack(new MainActivity.SettingCallBack() {
            @Override
            public void changeAutoGraph(String data) {
                //Todo 当得到用户修改后的个性签名后回调次方法
            }

            @Override
            public void changeUserName(String data) {
                //Todo 当得到用户修改后的用户名后回调次方法

            }
        });
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

    static final int CHANGE_USERNAME = 0x43;
    static final int CHANGE_AUTOGRAPH = 0x53;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mIcSetting:
                //点击头像
                changeIc();
                break;
            case R.id.mSexSetting:
                //点击性别
                changeSex();
                break;
            case R.id.mUserNameSetting:
                //点击用户名
                changeUserName();
                break;
            case R.id.mAutographSetting:
                //点击个人签名
                changeAutogreph();
                break;
        }
    }


    /**
     * 修改用户个性签名
     */
    private void changeAutogreph() {
        String request = "个性签名";
        Intent intent = new Intent(getActivity(), EditUserMessageActivity.class);
        intent.putExtra("request", request);
        getActivity().startActivityForResult(intent,CHANGE_AUTOGRAPH);
    }

    /**
     * 修改用户昵称
     */
    private void changeUserName() {
        String request = "用户昵称";
        Intent intent = new Intent(getActivity(), EditUserMessageActivity.class);
        intent.putExtra("request", request);
        getActivity().startActivityForResult(intent, CHANGE_USERNAME);
    }

    /**
     * 修改用户的性别
     */
    private void changeSex() {

    }

    /**
     *  转到相册并且获取裁剪后的图片uri
     */
    private void changeIc() {
        Crop.pickImage(getActivity());
    }

    /**
     * 将新的头像上传
     * @param uri
     */
    public void updataUserIc(Uri uri){
        mIcView.setImageURI(uri);
        File oldFile = new File(String.valueOf(FileUtil.getFileByUri(uri, getActivity())));
        File newFile = new File(Environment.getExternalStorageDirectory() , "2.jpg");
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileUtil.copyFile(oldFile.getPath(), newFile.getPath());
        BmobProFile.getInstance(getActivity()).upload(newFile.getPath(), new UploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                LogUtil.i("SettingFragment", "文件上传成功：" + fileName + ",可访问的文件地址：" + file.getUrl());
                //// TODO: 2016/3/10 存入用户云端
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                LogUtil.i("SettingFragment", errormsg);
            }
        });
    }

}
