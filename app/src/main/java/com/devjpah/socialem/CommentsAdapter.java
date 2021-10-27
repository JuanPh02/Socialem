package com.devjpah.socialem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    List<Comment> comments;
    Context context;

    public CommentsAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        holder.assignData(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgProfile;
        TextView tvUsername, tvComment;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.img_profile_comment);
            tvUsername = itemView.findViewById(R.id.tv_username_comment);
            tvComment = itemView.findViewById(R.id.tv_comment);
        }

        public void assignData(Comment  comment) {
            imgProfile.setImageResource(R.drawable.avatar_woman);
            tvUsername.setText(comment.getUsername());
            tvComment.setText(comment.getComment());
        }
    }
}
