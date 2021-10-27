package com.devjpah.socialem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView rcvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        this.setTitle("Comentarios");
        connectXml();
        initComments();
    }

    private void initComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo 😂😂😂"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo Que trabajo "));
        comments.add(new Comment("tiwar02", "Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que juiciioooooo Que juiciioooooo  Que juiciioooooo Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo 😂😂😂"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo Que trabajo "));
        comments.add(new Comment("tiwar02", "Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que juiciioooooo Que juiciioooooo  Que juiciioooooo Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo 😂😂😂"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo Que trabajo "));
        comments.add(new Comment("tiwar02", "Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo Que juiciioooooo Que juiciioooooo Que juiciioooooo  Que juiciioooooo Que juiciioooooo Que juiciioooooo Que trabajo Que juiciioooooo"));
        comments.add(new Comment("tiwar02", "Que juiciioooooo"));

        //Load comments
        CommentsAdapter adapter = new CommentsAdapter(comments, getApplicationContext());
        rcvComments.setAdapter(adapter);
        rcvComments.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
    }

    private void connectXml() {
        rcvComments = findViewById(R.id.rcv_comments);
    }
}