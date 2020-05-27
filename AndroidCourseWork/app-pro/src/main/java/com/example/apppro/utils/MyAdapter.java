package com.example.apppro.utils;


import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apppro.R;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyData> dataList;
    private MediaPlayer mediaPlayer;
    private ItemListener itemListener;
    private int savedHolderPos = -1;

    public interface ItemListener {
        void pageSingleClicked();
        void pageDoubleClicked();
    }

    public MyAdapter(ItemListener itemListener, MediaPlayer mediaPlayer) {
        this.itemListener = itemListener;
        this.mediaPlayer = mediaPlayer;
    }

    public void setDataList(List<MyData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vHolder, int position) {
        MyData data = dataList.get(position);
        vHolder.author_tv.setText(String.format("@%s", data.nickname));
        vHolder.description_tv.setText(data.description);
        vHolder.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setSurface(holder.getSurface());
                // TODO Q2.解决切换后台黑屏问题
                if (position == savedHolderPos) {
                    mediaPlayer.start();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                savedHolderPos = position;
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        private SurfaceView surfaceView;
        private TextView author_tv;
        private TextView description_tv;
        private int clickCount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            clickCount = 0;
            surfaceView = itemView.findViewById(R.id.item_surfaceview);
            surfaceView.setOnTouchListener(this);
            author_tv = itemView.findViewById(R.id.item_author_tv);
            description_tv = itemView.findViewById(R.id.item_description_tv);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (itemListener == null) return false;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                clickCount++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clickCount == 1) {
                            itemListener.pageSingleClicked();
                        } else {
                            itemListener.pageDoubleClicked();
                        }
                        clickCount = 0;
                    }
                }, 400);
            }
            return false;
        }
    }
}
