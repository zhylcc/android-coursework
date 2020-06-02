package com.example.apppro;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.apppro.utils.FriendAdapter;
import com.example.apppro.utils.Friends;

import java.util.ArrayList;
import java.util.List;


public class MessageActivity extends AppCompatActivity {
    private String[] data = {"马云", "马化腾", "张一鸣", "李彦宏"};
    private List<Friends> friendsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initFriends();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FriendAdapter adapter = new FriendAdapter(friendsList);
        recyclerView.setAdapter(adapter);
    }

    private void initFriends(){
        friendsList.add(new Friends(data[0], R.drawable.jackma));
        friendsList.add(new Friends(data[1], R.drawable.ponyma));
        friendsList.add(new Friends(data[2], R.drawable.yiming));
        friendsList.add(new Friends(data[2], R.drawable.robinli));
    }
}
