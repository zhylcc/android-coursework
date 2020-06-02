package com.example.apppro.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apppro.R;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private List<Friends> mFriendList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView friendImage;
        TextView friendName;

        public ViewHolder(View view){
            super(view);
            friendImage = view.findViewById(R.id.friend_image);
            friendName = view.findViewById(R.id.friend_name);
        }
    }

    public FriendAdapter(List<Friends> friendsList){
        mFriendList = friendsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friends friend = mFriendList.get(position);
        holder.friendImage.setImageResource(friend.getImageId());
        holder.friendName.setText(friend.getName());
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
}
