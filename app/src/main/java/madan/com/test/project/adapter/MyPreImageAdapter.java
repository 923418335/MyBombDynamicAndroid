package madan.com.test.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import madan.com.test.R;

/**
 * Created by 山东娃 on 2016/3/6.
 */
public class MyPreImageAdapter extends RecyclerView.Adapter<MyPreImageAdapter.MyViewHolder> {
    private Context mContext;
    private List<File> mFiles;
    BitmapFactory.Options options = new BitmapFactory.Options();

    public MyPreImageAdapter(Context mContext, List<File> mFiles) {
        this.mContext = mContext;
        this.mFiles = mFiles;
        options.inJustDecodeBounds = false;
        options.inSampleSize = 2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.cell_preview_image, parent,false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.position = position;
        holder.bitmap = BitmapFactory.decodeFile(String.valueOf(mFiles.get(position)),options);
        holder.preImage.setImageBitmap(holder.bitmap);
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView preImage;
        ImageView preXXX;
        Bitmap bitmap;
        int position;
        public MyViewHolder(View itemView) {
            super(itemView);
            preImage = (ImageView) itemView.findViewById(R.id.mPreView);
            preXXX = (ImageView) itemView.findViewById(R.id.mPreXXX);
            preXXX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bitmap != null && !bitmap.isRecycled()){
                        bitmap.recycle();
                        bitmap = null;
                    }
                    mFiles.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
