package com.example.app.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final ItemClickListener itemClickListener;
    private List<MyData> dataList;

    public MyAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.utils_list_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
       MyData data = dataList.get(i);
        //TODO 2.1.使用Glide加载视频封面
        Glide.with(myViewHolder.itemView)
                .load(data.feedurl)
                .into(myViewHolder.imageView);
        //TODO 2.2.展示补充信息
        myViewHolder.nicknameTv.setText(String.format("作者：%s", data.nickname));
        myViewHolder.descTv.setText(data.description);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setDataList(List<MyData> dataList) {
        this.dataList = dataList;
    }

    public interface ItemClickListener {
        void onItemClickListener(int clickedItemIndex);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView nicknameTv;
        TextView descTv;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_imageview);
            nicknameTv = itemView.findViewById(R.id.item_nickname_tv);
            descTv = itemView.findViewById(R.id.item_desc_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClickListener(getAdapterPosition());
            }
        }
    }

}
