package madan.com.test.project.proxy;

import android.content.Context;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import madan.com.test.project.bean.User;
import madan.com.test.project.utils.Constance;
import madan.com.test.project.utils.LogUtil;

/**
 * Created by 山东娃 on 2016/3/5.
 */
public class UserProxy {
    private Context mContext;
    public static final String TAG = "UserProxy";

    public UserProxy(Context mContext) {
        this.mContext = mContext;
    }
    /**
     * 登录
     */
    public interface ILogListence{
        void onLoginSuccess();
        void onLoginFailure(String msg);
    }

    private ILogListence logListence;

    public void setLogListence(ILogListence logListence) {
        this.logListence = logListence;
    }

    public void login(String userName, String passWord){
        BmobUser user = new BmobUser();
        user.setUsername(userName);
        user.setPassword(passWord);
        user.login(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                if(logListence != null){
                    logListence.onLoginSuccess();
                }else{
                    LogUtil.i(TAG, "尚未添加登陆监听事件");
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if(logListence != null){
                    logListence.onLoginFailure(s);
                }else{
                    LogUtil.i(TAG, "尚未添加登陆监听事件");
                }
            }
        });
    }

    /**
     * 找回密码
     */
    public interface IFindPassWordListence{
        void onResetSuccess();
        void onResetFailure(String s);
    }

    private IFindPassWordListence findPassWordListence;

    public void setFindPassWordListence(IFindPassWordListence findPassWordListence) {
        this.findPassWordListence = findPassWordListence;
    }

    public void findPassWord(String mail){
        BmobUser.resetPasswordByEmail(mContext, mail, new ResetPasswordByEmailListener() {
            @Override
            public void onSuccess() {
                if(findPassWordListence == null){
                    findPassWordListence.onResetSuccess();
                }else{
                    LogUtil.i(TAG, "尚未找回密码监听事件");
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if(findPassWordListence == null){
                    findPassWordListence.onResetFailure(s);
                }else{
                    LogUtil.i(TAG, "尚未找回密码监听事件");
                }
            }
        });
    }

    /**
     * 注册
     */
    public interface IRegisterListence{
        void onRegisterSuccess();
        void onRegisterFailure(String s);
    }

    IRegisterListence registerListence;

    public void setRegisterListence(IRegisterListence registerListence) {
        this.registerListence = registerListence;
    }

    public void register(String userName, String passWord, String mail){
        User user = new User();
        user.setUsername(userName);
        user.setPassword(passWord);
        user.setEmail(mail);
        user.setSex(Constance.MAN);
        user.signUp(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                if(registerListence != null){
                    registerListence.onRegisterSuccess();
                }else{
                    LogUtil.i(TAG, "尚未注册监听事件");
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if(registerListence != null){
                    registerListence.onRegisterFailure(s);
                }else{
                    LogUtil.i(TAG, "尚未注册监听事件");
                }
            }
        });
    }
}
