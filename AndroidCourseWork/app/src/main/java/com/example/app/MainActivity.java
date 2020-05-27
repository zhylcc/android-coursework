package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.utils.MyAdapter;
import com.example.app.utils.MyService;
import com.example.app.utils.MyData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener{

    private MyAdapter mRvAdapter;
    private MyService mService;
    private List<MyData> mDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO 1.使用Retrofit访问api，获取数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(MyService.class);
        mService.getData().enqueue(new Callback<List<MyData>>() {
            @Override
            public void onResponse(Call<List<MyData>> call, Response<List<MyData>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    mDataList = response.body();
                    mRvAdapter.setDataList(response.body());
                    mRvAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MyData>> call, Throwable t) {
            }
        });

        //TODO 2.使用RecyclerView展示，每个item包含视频作者、描述和封面信息
        RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRvAdapter = new MyAdapter(this);
        recyclerView.setAdapter(mRvAdapter);
    }

    @Override
    public void onItemClickListener(int clickedItemIndex) {
        //TODO 3.点击视频封面跳转播放界面
        MyData response = mDataList.get(clickedItemIndex);
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("feedurl", response.feedurl);
        startActivity(intent);
    }
}
