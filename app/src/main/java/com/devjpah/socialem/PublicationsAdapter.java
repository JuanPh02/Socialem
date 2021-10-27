package com.devjpah.socialem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PublicationsAdapter extends RecyclerView.Adapter<PublicationsAdapter.PublicationsViewHolder> {

    List<Publication> publications;
    Context context;

    public PublicationsAdapter(List<Publication> publications, Context context) {
        this.publications = publications;
        this.context = context;
    }

    @NonNull
    @Override
    public PublicationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.publication_item, parent, false);
        return new PublicationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicationsViewHolder holder, int position) {
        holder.assignData(publications.get(position));
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }

    public static class PublicationsViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ProgressBar progressBar;
        CircleImageView imgProfile;
        TextView tvAuthor, tvDateCreated, tvDescription;
        ImageView imgPublication;
        ImageButton imgbLikedPublication, imgbComment;

        public PublicationsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            progressBar = itemView.findViewById(R.id.progress_load);
            imgProfile = itemView.findViewById(R.id.img_profile_edit);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvDateCreated = itemView.findViewById(R.id.tv_date_created);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPublication = itemView.findViewById(R.id.img_publication);
            imgbLikedPublication = itemView.findViewById(R.id.imgb_like);
            imgbComment = itemView.findViewById(R.id.imgb_comment);

            //Events

            imgbLikedPublication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Like ", Toast.LENGTH_SHORT).show();
                }
            });

            imgbComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Comment ", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context,CommentsActivity.class));
                }
            });
        }

        public void assignData(Publication publication) {
            tvAuthor.setText(publication.getAuthor());
            tvDateCreated.setText(publication.getDateCreated());
            tvDescription.setText(publication.getDescription());
            imgProfile.setImageResource(R.drawable.avatar_woman);
            imgPublication.setImageResource(R.drawable.avatar_woman);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
