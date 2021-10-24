package com.devjpah.socialem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PublicationsAdapter extends RecyclerView.Adapter<PublicationsAdapter.MyViewHolder> {

    private List<Publication>  publications;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public PublicationsAdapter(List<Publication> publications, Context context) {
        this.publications = publications;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.publication_item, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Publication publication = publications.get(position);

        //holder.progressBar.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.INVISIBLE);

        holder.tvAuthor.setText(publication.getAuthor());
        holder.tvDateCreated.setText(publication.getDateCreated());
        holder.tvDescription.setText(publication.getDescription());
        holder.imgProfile.setImageResource(R.drawable.avatar_man);
        holder.imgPublication.setImageResource(R.drawable.avatar_woman);

    }

    @Override
    public int getItemCount() {
        return publications.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProgressBar progressBar;
        CircleImageView imgProfile;
        TextView tvAuthor, tvDateCreated, tvDescription;
        ImageView imgPublication;
        ImageButton imgbLikedPublication, imgbComment;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            progressBar = itemView.findViewById(R.id.progress_load);
            imgProfile = itemView.findViewById(R.id.img_profile);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvDateCreated = itemView.findViewById(R.id.tv_date_created);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPublication = itemView.findViewById(R.id.img_publication);
            imgbLikedPublication = itemView.findViewById(R.id.imgb_liked_publication);
            imgbComment = itemView.findViewById(R.id.imgb_comment);
            this.onItemClickListener = this.onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
