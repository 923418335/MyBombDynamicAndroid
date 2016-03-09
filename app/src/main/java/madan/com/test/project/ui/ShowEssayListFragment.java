package madan.com.test.project.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import madan.com.test.project.adapter.MyEssayListAdapter;
import madan.com.test.project.bean.Essay;
import madan.com.test.project.utils.Constance;
import madan.com.test.project.utils.LogUtil;

/**
 * Created by 山东娃 on 2016/3/8.
 * 显示动态列表的Fragment
 * Form MainActivity
 */
public class ShowEssayListFragment extends Fragment {
    private XRecyclerView mShowEssayListView;
    private MyEssayListAdapter mEssayListAdapter;
    private List<Essay> mEssays = new ArrayList<>();
    private int currentIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initListView();
        return mShowEssayListView;
    }


    /**
     * 初始化RecyclerView 和 相应的 Adapter
     */
    private void initListView() {
        mShowEssayListView = new XRecyclerView(getActivity());
        mEssayListAdapter = new MyEssayListAdapter(getActivity(), mEssays);
        mShowEssayListView.setAdapter(mEssayListAdapter);
        mShowEssayListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShowEssayListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                reFresh();
                log("reFresh");
            }

            @Override
            public void onLoadMore() {
                loadMore();
                log("loadMore");
            }
        });
    }

    /**
     * 下拉加载更多
     */
    private void loadMore() {
        BmobQuery<Essay> query = new BmobQuery<>();
        query.order("-createdAt");
        query.include("author");
        query.setSkip(Constance.REFLUSH_NULBER * (++currentIndex));
        query.setLimit(Constance.REFLUSH_NULBER);
        query.findObjects(getActivity(), new FindListener<Essay>() {
            @Override
            public void onSuccess(List<Essay> list) {
                log("加载成功");
                mEssays.addAll(list);
                mEssayListAdapter.notifyDataSetChanged();
                mShowEssayListView.loadMoreComplete();
                if(list.size() < Constance.REFLUSH_NULBER){
                    mShowEssayListView.setLoadingMoreEnabled(false);
                }
            }

            @Override
            public void onError(int i, String s) {
                log("加载失败");
                toast("加载失败");
            }
        });
    }


    /**
     * 上拉刷新数据
     */
    private void reFresh() {
        BmobQuery<Essay> query = new BmobQuery<>();
        query.order("-createdAt");
        query.include("author");
        query.setLimit(Constance.REFLUSH_NULBER);
        query.findObjects(getActivity(), new FindListener<Essay>() {
            @Override
            public void onSuccess(List<Essay> list) {
                log("刷新成功");
                currentIndex = 0;
                mEssays.clear();
                mEssays.addAll(list);
                mEssayListAdapter.notifyDataSetChanged();
                mShowEssayListView.refreshComplete();
            }

            @Override
            public void onError(int i, String s) {
                log("刷新失败");
                toast("刷新失败");
            }
        });
    }

    /**
     * 每次进入都要刷新数据
     */
    @Override
    public void onResume() {
        super.onResume();
        reFresh(); //刷新数据
    }

    private void log(String data) {
        LogUtil.i("ShowEssayListFragment", data);
    }

    private void toast(String data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }
}
