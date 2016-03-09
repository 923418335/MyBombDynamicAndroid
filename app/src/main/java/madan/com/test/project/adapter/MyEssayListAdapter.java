package madan.com.test.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import madan.com.test.R;
import madan.com.test.project.bean.Essay;
import madan.com.test.project.utils.LogUtil;
import madan.com.test.project.utils.VolleyUtil;

/**
 * Created by 山东娃 on 2016/3/7.
 */

public class MyEssayListAdapter extends RecyclerView.Adapter<MyEssayListAdapter.MyViewHolder> {
    private Context mContext;
    private List<Essay> datas;

    public MyEssayListAdapter(Context mContext, List<Essay> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.cell_essay_list, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.position = position;
        holder.mEssayContent.setText(datas.get(position).getContent());
        if(!datas.get(position).getImageUrls().isEmpty()){
           holder.mEssayImage.setDefaultImageResId(R.drawable.xxx);
            holder.mEssayImage.setImageUrl(datas.get(position).getImageUrls().get(0), VolleyUtil.getInstance(mContext).getImageLoader());
        }
        holder.mDespatcherId.setText(datas.get(position).getAuthor().getUsername());
        // // TODO: 2016/3/8 头像的设置  这里先用默认头像
        if(datas.get(position).getAuthor().getSex()){
            holder.mDespatcherIc.setImageResource(R.drawable.default_ic_man);
        }else{
            holder.mDespatcherIc.setImageResource(R.drawable.default_ic_woman);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        NetworkImageView mEssayImage;
        TextView mEssayContent, mDespatcherId;
        ImageView mCollet ;
        CircleImageView mDespatcherIc;
        LinearLayout mZan, mFenxiang, mPinglun;
        View rootView;
        int position = -1;
        public MyViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            rootView = itemView;
            mDespatcherIc = (CircleImageView) itemView.findViewById(R.id.mDespatcherIc);
            mDespatcherId = (TextView) itemView.findViewById(R.id.mDespatcherId);
            mEssayImage = (NetworkImageView) itemView.findViewById(R.id.mEssayImage);
            mEssayContent = (TextView) itemView.findViewById(R.id.mEssayContent);
            mCollet = (ImageView) itemView.findViewById(R.id.mCollect);
            mZan = (LinearLayout) itemView.findViewById(R.id.mZan);
            mFenxiang = (LinearLayout) itemView.findViewById(R.id.mFenxiang);
            mPinglun = (LinearLayout) itemView.findViewById(R.id.mPinglun);
            setListener();
        }

        private void setListener() {
            rootView.setOnClickListener(mJumpToEssayDetails);
            mDespatcherIc.setOnClickListener(mJumpToDespatcherDetails);
            mDespatcherId.setOnClickListener(mJumpToDespatcherDetails);
        }
    }

    private View.OnClickListener mJumpToDespatcherDetails = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtil.i("MyShowEssayAdapter", "跳转到作者的详情界面");
        }
    };

    private View.OnClickListener mJumpToEssayDetails = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtil.i("MyShowEssayAdapter", "跳转到随笔的详情界面（评论）");
        }
    };





}
