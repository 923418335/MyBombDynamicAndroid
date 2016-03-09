package madan.com.test.project.ui;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import madan.com.test.R;
import madan.com.test.project.base.BaseActivity;
import madan.com.test.project.proxy.UserProxy;

public class RegisterAndLoginActivity extends BaseActivity implements View.OnClickListener, UserProxy.IFindPassWordListence, UserProxy.ILogListence, UserProxy.IRegisterListence {
    private TextView mLogetMenu, mRegisterMenu, mFindPassWordMenu;
    private TextInputLayout mUserNameLayout, mPassWordLayout, mMeilLayout;
    private EditText mUserNameInput, mPassWordInput, mMeilInout;
    private Button mLoginBtn;
    private Resources mResources;
    private ProgressDialog mProgressDialog;
    private UserProxy mUserProxy;

    private enum UserOperation {
        LOGIN, REGISTER, FINDPASSWORD;
    }

    private UserOperation operation = UserOperation.LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_and_login);
        findView();
        setListener();
        initToolBar();

    }

    private void findView() {
        //为菜单绑定事件
        mLogetMenu = (TextView) findViewById(R.id.mLoginMenu);
        mFindPassWordMenu = (TextView) findViewById(R.id.mFindPassWordMenu);
        mRegisterMenu = (TextView) findViewById(R.id.mRegisterMenu);

        mUserNameLayout = (TextInputLayout) findViewById(R.id.mUerNameInputLayout);
        mPassWordLayout = (TextInputLayout) findViewById(R.id.mPassWordInputLayout);
        mMeilLayout = (TextInputLayout) findViewById(R.id.mMeilInputLayout);

        mUserNameInput = mUserNameLayout.getEditText();
        mPassWordInput = mPassWordLayout.getEditText();
        mMeilInout = mMeilLayout.getEditText();

        mLoginBtn = (Button) findViewById(R.id.mRegisterBtn);

        mUserProxy = new UserProxy(this);
    }

    public void setListener() {
        //绑定监听事件
        mLogetMenu.setOnClickListener(this);
        mFindPassWordMenu.setOnClickListener(this);
        mRegisterMenu.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);

        mUserProxy.setFindPassWordListence(this);
        mUserProxy.setLogListence(this);
        mUserProxy.setRegisterListence(this);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.navigation_back_arrow);
        mResources = getResources();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 根据当前状态更新界面
     *
     * @param operation
     */
    private void updataLayout(UserOperation operation) {
        if (operation == UserOperation.LOGIN) {
            //转换为登录状态
            mLogetMenu.setTextColor(mResources.getColor(R.color.bg));
            mRegisterMenu.setTextColor(mResources.getColor(R.color.gray));
            mFindPassWordMenu.setTextColor(mResources.getColor(R.color.gray));
            mUserNameLayout.setVisibility(View.VISIBLE);
            mPassWordLayout.setVisibility(View.VISIBLE);
            mMeilLayout.setVisibility(View.GONE);
            mLoginBtn.setText("登录");
        } else if (operation == UserOperation.REGISTER) {
            //转换为注册状态
            mRegisterMenu.setTextColor(mResources.getColor(R.color.bg));
            mLogetMenu.setTextColor(mResources.getColor(R.color.gray));
            mFindPassWordMenu.setTextColor(mResources.getColor(R.color.gray));
            mUserNameLayout.setVisibility(View.VISIBLE);
            mPassWordLayout.setVisibility(View.VISIBLE);
            mMeilLayout.setVisibility(View.VISIBLE);
            mLoginBtn.setText("注册");
        } else {
            //转换为找回密码状态
            mFindPassWordMenu.setTextColor(mResources.getColor(R.color.bg));
            mRegisterMenu.setTextColor(mResources.getColor(R.color.gray));
            mRegisterMenu.setTextColor(mResources.getColor(R.color.gray));
            mUserNameLayout.setVisibility(View.GONE);
            mPassWordLayout.setVisibility(View.GONE);
            mMeilLayout.setVisibility(View.VISIBLE);
            mLoginBtn.setText("找回密码");
        }
    }

    /**
     * 登录
     */
    private void login() {
        String userName = mUserNameInput.getText().toString().trim();
        String passWord = mPassWordInput.getText().toString().trim();
        String meil = mMeilInout.getText().toString().trim();
        if (operation == UserOperation.LOGIN) {
            //登录的情況
            if(checkUserNameAndPassWordAndMeil(userName, passWord)){
                showProgress();
                mUserProxy.login(userName, passWord);
            }
        } else if (operation == UserOperation.REGISTER) {
            //注册的情况
            if(checkUserNameAndPassWordAndMeil(userName, passWord, meil)){
                showProgress();
                mUserProxy.register(userName, passWord, meil);
            }

        } else {
            //找回密码的情况
            if(checkUserNameAndPassWordAndMeil(meil)){
                showProgress();
                mUserProxy.findPassWord(meil);
            }
        }
    }

    /**
     * 判断用户名与密码是否不为空
     * @param userName
     * @param passWord
     * @return
     */
    private boolean checkUserNameAndPassWordAndMeil(String userName, String passWord) {
        if(userName.isEmpty()){
            toast("请输入用户名");
            return false;
        }
        if(passWord.isEmpty()){
            toast("请输入密码");
            return false;
        }
        return true;
    }

    private boolean checkUserNameAndPassWordAndMeil(String mail){
        if(mail.isEmpty()){
            toast("请输入邮箱");
            return false;
        }
        if(!mail.contains("@")){
            toast("请输入正确的邮箱");
            return false;
        }
        return true;
    }

    private boolean checkUserNameAndPassWordAndMeil(String userName, String passWord, String mail) {
        if(!checkUserNameAndPassWordAndMeil(userName, passWord)){
            return false;
        }
        return checkUserNameAndPassWordAndMeil(mail);
    }


    /**
     * 显示进度条
     */
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("加载中,请稍后。");
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    /**
     * 隐藏进度条
     */
    public void dissmissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mLoginMenu:
                //登录菜单
                operation = UserOperation.LOGIN;
                updataLayout(operation);
                break;
            case R.id.mRegisterMenu:
                //注册菜单
                operation = UserOperation.REGISTER;
                updataLayout(operation);
                break;
            case R.id.mFindPassWordMenu:
                //找回密码菜单
                operation = UserOperation.FINDPASSWORD;
                updataLayout(operation);
                break;
            case R.id.mRegisterBtn:
                //登录菜单
                login();
                break;
        }
    }


    /***
     * 找回密码成功
     */
    @Override
    public void onResetSuccess() {
        dissmissProgress();
        toast("找回密码成功,请检查邮箱。");
        finish();
        operation = UserOperation.LOGIN;
        updataLayout(operation);
    }

    /**
     * 找回密码失败
     * @param s
     */
    @Override
    public void onResetFailure(String s) {
        dissmissProgress();
        toast("找回密码失败，请检查网络后重试");
        log(s);
    }

    /**
     * 登陆成功
     */
    @Override
    public void onLoginSuccess() {
        dissmissProgress();
        toast("登陆成功");
        finish();
    }

    /**
     * 登录失败
     * @param msg
     */
    @Override
    public void onLoginFailure(String msg) {
        dissmissProgress();
        toast("登录失败，请检查网络后重试");
        log(msg);
    }

    /**
     * 注册成功
     */
    @Override
    public void onRegisterSuccess() {
        dissmissProgress();
        toast("注册成功");
        finish();
        operation = UserOperation.LOGIN;
        updataLayout(operation);
    }

    /**
     * 注册失败
     * @param s
     */
    @Override
    public void onRegisterFailure(String s) {
        dissmissProgress();
        toast("注册失败，请检查网络后重试");
        log(s);
    }


}
