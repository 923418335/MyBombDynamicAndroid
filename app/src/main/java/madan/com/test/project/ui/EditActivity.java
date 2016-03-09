package madan.com.test.project.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadBatchListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import madan.com.test.R;
import madan.com.test.project.adapter.MyPreImageAdapter;
import madan.com.test.project.base.BaseActivity;
import madan.com.test.project.bean.Essay;
import madan.com.test.project.bean.User;
import madan.com.test.project.utils.FileUtil;
import madan.com.test.project.utils.NetworkUtil;

public class EditActivity extends BaseActivity implements View.OnClickListener {
    private View mCameraForPhote;
    private View mAlbumForPhote;
    private EditText mContentInput;
    private List<File> mFiles = new ArrayList<>();
    private List<String> mEssayUrls = new ArrayList<>();
    private RecyclerView mPreImageRecyclerView;
    private MyPreImageAdapter mPreImageAdapter;
    private static final int REQUEST_CODE_ALBUM = 0X223;
    private static final  int REQUEST_COIDE_CAPTRUE = 0X523;
    private static final int UPDATA_PROGRESS = 0x432;
    private Essay mEssay = new Essay();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initToolBar();
        findView();
        setListence();
        initPreImageRecyclerView();
    }

    /**
     * 初始化预览列表
     */
    private void initPreImageRecyclerView() {
        mPreImageAdapter = new MyPreImageAdapter(this, mFiles);
        mPreImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mPreImageRecyclerView.setAdapter(mPreImageAdapter);
    }

    private void setListence() {
        mCameraForPhote.setOnClickListener(this);
        mAlbumForPhote.setOnClickListener(this);
    }

    private void findView() {
        mCameraForPhote = findViewById(R.id.mCamera);
        mAlbumForPhote = findViewById(R.id.mAlbum);
        mContentInput = (EditText) findViewById(R.id.mContentInput);
        mPreImageRecyclerView = (RecyclerView) findViewById(R.id.mPreRecyclerView);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.navigation_back_arrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.complete:
                publish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 发表动态
     */
    private void publish() {
        String content = mContentInput.getText().toString().trim();
        if(content.isEmpty()){
            toast("内容不能为空");
            return;
        }
        if(!NetworkUtil.isAvailable()){
            toast("当前网络不可用,请检查网络后重试");
            return;
        }
        publish(content);//发表动态
    }


    /**
     * 发表动态
     * 如果有图片则先上传图片
     * 最后都会调用  publish(String content, BmobFile figureFile)
     * 如果没有图片则第二个参数为null
     * @param content
     */
    private void publish(final String content) {
        if(mFiles.isEmpty()){
            //发表不带图片的动态
            publishContent(content);
        }else{
            //发表带图片的动态
            publishWithImage(content);
        }
    }

    /**
     * 批量上传图片
     */
    private void publishWithImage(final String content) {
        String[] fileNames = new String[mFiles.size()];
        for(int i = 0; i < mFiles.size(); i++){
            fileNames[i] = mFiles.get(i).toString();
        }

        showProgress();//显示进度条

        BmobProFile.getInstance(this).uploadBatch(fileNames, new UploadBatchListener() {
            @Override
            public void onSuccess(boolean b, String[] strings, String[] strings1, BmobFile[] bmobFiles) {
                if(bmobFiles[bmobFiles.length - 1] != null){
                    for(int i = 0; i < bmobFiles.length; i++){
                        mEssayUrls.add(bmobFiles[i].getUrl());
                    }
                    log(mEssayUrls.toString());
                    mEssay.setImageUrls(mEssayUrls);
                    publishContent(content);
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
                log(String.valueOf(i3));
                Message msg = mHandler.obtainMessage();
                msg.arg1 = UPDATA_PROGRESS;
                msg.arg2 = i3;
                msg.sendToTarget();
            }

            @Override
            public void onError(int i, String s) {
                log("上传失败");
                dismissProgress();
            }
        });
    }

    /**
     * 上传文章
     * @param content
     */
    private void publishContent(String content) {
        User user =  User.getCurrentUser(this, User.class);
        mEssay.setAuthor(user);
        mEssay.setContent(content);
        mEssay.setImageUrls(mEssayUrls);
        mEssay.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                toast("发表成功");
                log("发表成功");
                dismissProgress();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                toast("发表失败，请检查网络后重试");
                log("发表失败" + s);
                dismissProgress();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.mAlbum:
                //从相册中获取
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_ALBUM);
                break;
            case R.id.mCamera:
                //从相机中获取
                String state = Environment.getExternalStorageState();
                if(state.equals(Environment.MEDIA_MOUNTED)){
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, REQUEST_COIDE_CAPTRUE);
                }else{
                    toast("请确认已经插入SD卡");
                    log("没有SD卡");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_ALBUM:
                case REQUEST_COIDE_CAPTRUE:
                    mFiles.add(FileUtil.getFileByUri(data.getData(), this));
                    mPreImageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case UPDATA_PROGRESS:
                    int progress = msg.arg2;
                    updataProgress("加载中，请稍后  " + progress + "%");
                    break;
            }
        }
    };

    private ProgressDialog mProgress;

    private void showProgress(){
        if(mProgress == null){
            mProgress = new ProgressDialog(this);
            mProgress.setMessage("加载中，请稍后");
            mProgress.setCancelable(false);
        }
        mProgress.show();
    }

    private void dismissProgress(){
        if(mProgress != null){
            mProgress.dismiss();
        }
    }

    private void updataProgress(String data){
        if(mProgress != null){
            mProgress.setMessage(data);
        }
    }

}
