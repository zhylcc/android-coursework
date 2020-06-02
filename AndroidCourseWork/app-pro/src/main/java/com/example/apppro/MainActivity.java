package com.example.apppro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apppro.utils.MyAdapter;
import com.example.apppro.utils.MyData;
import com.example.apppro.utils.MyService;
import com.example.apppro.utils.NavItemRefreshListener;
import com.example.apppro.utils.NavItemView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemListener, View.OnClickListener {

    private MyAdapter mAdapter;
    private List<MyData> mDataList;
    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private int Position;

    NavItemView FrontPageButton, FollowButton, MessageButton, MeButton, Selected_Item;//导航栏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FrontPageButton =findViewById(R.id.one);
        FollowButton =findViewById(R.id.two);
        MessageButton =findViewById(R.id.three);
        MeButton =findViewById(R.id.four);
        FrontPageButton.setShowRefreshImage(true);
        FrontPageButton.setNavItemRefreshListener(new NavItemRefreshListener() {
            @Override
            public void onRefresh(View v) {
                Toast.makeText(MainActivity.this, "刷新中..", Toast.LENGTH_SHORT).show();
                pageSelected(Position);//刷新时重新传入当前页面
            }
        });
        FrontPageButton.setOnClickListener(this);
        FollowButton.setOnClickListener(this);
        MessageButton.setOnClickListener(this);
        MeButton.setOnClickListener(this);

        //TODO 1.使用Retrofit访问api，获取数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyService mService = retrofit.create(MyService.class);
        mService.getDataList().enqueue(new Callback<List<MyData>>() {
            @Override
            public void onResponse(Call<List<MyData>> call, Response<List<MyData>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    mDataList = response.body();
                    mAdapter.setDataList(mDataList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MyData>> call, Throwable t) {
            }
        });

        //TODO 2.使用ViewPager2展示，每页1个item，包含视频作者、描述，自动循环播放
        mediaPlayer = new MediaPlayer();
        mAdapter = new MyAdapter(this, mediaPlayer);
        ViewPager2 viewPager2 = findViewById(R.id.main_viewpager2);
        viewPager2.setAdapter(mAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Position = position;
                pageSelected(position);
            }
        });
        imageView = findViewById(R.id.main_heart);
    }

    @Override
    protected void onPause() {
        //TODO Q1：后台暂停播放
        super.onPause();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.pause();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pageSelected(int position) {
        //TODO 3.滑动切换视频
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(mDataList.get(position).feedurl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pageSingleClicked() {
        //TODO 4.单击暂停/播放
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void pageDoubleClicked() {
        //TODO 5.Glide实现双击点赞动态画
        Glide.with(this)
                .load(getResources().getDrawable(R.drawable.heart_fill))
                .transition(withCrossFade(750))
                .into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setImageDrawable(null);
            }
        }, 1000);
    }

    //TODO6:导航栏控件监听
    @Override
    public void onClick(View v) {
        if(Selected_Item !=null){
            Selected_Item.cancelActive();
        }
        switch (v.getId()){
            case R.id.one:
                FrontPageButton.startActive();
                Selected_Item = FrontPageButton;
                break;
            case R.id.two:
                FollowButton.startActive();
                Selected_Item = FollowButton;
                break;
            case R.id.three:
                MessageButton.startActive();
                Selected_Item = MessageButton;
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.four:
                MeButton.startActive();
                Selected_Item = MeButton;
                break;
        }
    }
}
