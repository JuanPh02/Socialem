package com.devjpah.socialem;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment {

    RecyclerView storiesBar, blogFeed;
    RecyclerView.LayoutManager layoutManager;
    ImageButton btnStar;
    List<Publication> publications = new ArrayList<Publication>();
    PublicationsAdapter adapter;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        context = getActivity();
        connectXml(view);
        initFeed();
        initStoriesBar();

        return view;
    }

    private void initFeed() {
        layoutManager = new LinearLayoutManager(context);
        blogFeed.setLayoutManager(layoutManager);
        blogFeed.setItemAnimator(new DefaultItemAnimator());
        blogFeed.setNestedScrollingEnabled(false);

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("eli@h.com", "Que juiciioooooo"));
        //Request Options
        publications.add(new Publication("Juan Pablo Hernandez", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        publications.add(new Publication("Juan Pablo Hernandez", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        publications.add(new Publication("Juan Pablo Hernandez", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        publications.add(new Publication("Juan Pablo Hernandez", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        publications.add(new Publication("Juan Pablo Hernandez", "20211020", "media\\fddfs.png", "Trabajando ando", 34, comments));
        //Create feed
        adapter = new PublicationsAdapter(publications, context);
        blogFeed.setAdapter(adapter);

    }

    private void connectXml(View view) {
        storiesBar = view.findViewById(R.id.stories_bar);
        blogFeed = view.findViewById(R.id.blog_feed);
        btnStar = view.findViewById(R.id.btn_star);
    }

    private void initStoriesBar() {

        List<Story> stories = new ArrayList<>();
        stories.add(new Story(false));
        stories.add(new Story(false));
        stories.add(new Story(true));
        stories.add(new Story(false));
        stories.add(new Story(true));
        stories.add(new Story(false));
        stories.add(new Story(false));
        stories.add(new Story(true));
        stories.add(new Story(false));

        StoriesAdapter adapter = new StoriesAdapter(stories, context);
        storiesBar.setAdapter(adapter);
        storiesBar.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        storiesBar.addItemDecoration(new StoriesDecoration(10));
    }
}