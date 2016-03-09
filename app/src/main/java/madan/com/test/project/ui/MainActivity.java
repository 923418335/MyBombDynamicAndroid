package madan.com.test.project.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import madan.com.test.R;
import madan.com.test.project.MyApplication;
import madan.com.test.project.base.BaseActivity;
import madan.com.test.project.bean.User;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;//抽屉布局 左拉菜单
    private NavigationView mNavigation;//显示用户名 头像 与 登出 的布局
    private FloatingActionButton mPublishBtn;//发表动态的按钮
    private ShowEssayListFragment mShowEssayListFragment;//显示所有动态的Fragment
    private CircleImageView mUserIc;//显示用户头像
    private ImageView mUserLogout;//显示登出按钮
    private TextView mUserName;//显示用户姓名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findView();
        initToolBar();
        initNavigation();
        initFloatButton();
        initFragment();
    }

    private void initFragment() {
        mShowEssayListFragment = (ShowEssayListFragment) getFragmentManager().findFragmentById(R.id.mMainRootView);
        if(mShowEssayListFragment == null){
            mShowEssayListFragment = new ShowEssayListFragment();
            getFragmentManager().beginTransaction().add(R.id.mMainRootView, mShowEssayListFragment).commit();
        }
    }

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPublishBtn = (FloatingActionButton) findViewById(R.id.mPublishBtn);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        mNavigation = (NavigationView) findViewById(R.id.navigation);
    }

    /**
     * 在这里判断用户是否登录
     * 如果登录 则在抽屉布局中
     * 设置用户头像和用户名
     */
    @Override
    protected void onResume() {
        super.onResume();
        User user = BmobUser.getCurrentUser(this, User.class);
        if(user == null){
            mUserName.setText("游客");
        }else{
            mUserName.setText(user.getUsername());
            // // TODO: 2016/3/8 添加用户头像
            if(user.getSex()){
                mUserIc.setImageResource(R.drawable.default_ic_man);
            }else{
                mUserIc.setImageResource(R.drawable.default_ic_woman);
            }
        }
    }

    /**
     * 初始化 悬浮按钮
     * 点击则发表动态
     * 判断当前用户是否登录
     * 若未登录 则转入登陆界面
     */
    private void initFloatButton() {
        mPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser currentUser = BmobUser.getCurrentUser(MainActivity.this);
                Intent intent = new Intent();
                if (currentUser == null) {
                    //当前未有用户登录
                    //则直接转向登录注册界面
                    intent.setClass(MainActivity.this, RegisterAndLoginActivity.class);
                } else {
                    //已经登录
                    //转向发表动态界面
                    intent.setClass(MainActivity.this, EditActivity.class);
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化抽屉侧栏
     */
    private void initNavigation() {
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        //主页
                        toast("还未完成呢");
                        break;
                    case R.id.setting:
                        toast("还未完成呢");
                        //设置
                        break;
                    case R.id.collect:
                        toast("还未完成呢");
                        //收藏
                        break;
                    case R.id.recommend:
                        toast("还未完成呢");
                        //推荐
                        break;
                    case R.id.about:
                        toast("还未完成呢");
                        //关于
                        break;
                }
                return true;
            }
        });

        /**
         * 为Navigation上的控件
         * findViewById
         */
        View NavigationRootView = mNavigation.getHeaderView(0);
        mUserIc = (CircleImageView) NavigationRootView.findViewById(R.id.mUserIc);
        mUserLogout = (ImageView) NavigationRootView.findViewById(R.id.mUserLogout);
        mUserName = (TextView) NavigationRootView.findViewById(R.id.mUserName);
        mUserLogout.setOnClickListener(this);

    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Hello World");
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        toggle.syncState();
        mDrawerLayout.setDrawerListener(toggle);
    }


    private long lastExitTime;

    /**
     * 连续按两次后退键退出程序
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastExitTime < 2000) {
            MyApplication.exit();
            super.onBackPressed();
        } else {
            toast("再按一次退出此程序");
        }
        lastExitTime = System.currentTimeMillis();
    }


    /**
     * 点击的监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mUserLogout:
                //点击登出按钮
                logout();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        new AlertDialog.Builder(this).setTitle("退出登录").setMessage("确定要退出么？")
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser.logOut(MainActivity.this);//退出登录
                        mDrawerLayout.closeDrawers();
                        onResume();
                    }
                }).setPositiveButton("取消", null).show();
    }
}
