package com.devjpah.socialem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder> {

    private List<Story> stories;
    private Context context;

    public StoriesAdapter(List<Story> stories, Context context) {
        this.stories = stories;
        this.context = context;
    }

    @NonNull
    @Override
    public StoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_item, parent, false);
        return new StoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesViewHolder holder, int position) {
        holder.assignData(stories.get(position));
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public static class StoriesViewHolder extends RecyclerView.ViewHolder{

        CardView storyOutline;
        ImageView imgProfile;
        TextView tvUsername;

        public StoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            storyOutline = itemView.findViewById(R.id.story_outline);
            imgProfile = itemView.findViewById(R.id.img_story);
            tvUsername = itemView.findViewById(R.id.tv_username_story);
        }

        public void assignData(Story story) {
            String username = story.getUsername();
            if(story.isSeen()) {
                storyOutline.setCardBackgroundColor(itemView.getResources().getColor(R.color.gray_transparent));
            }
            if(username.length() > 12) {
                username = username.substring(0,13);
                username += "...";
            }
            imgProfile.setImageResource(R.drawable.avatar_man);
            tvUsername.setText(username);
        }
    }
}
