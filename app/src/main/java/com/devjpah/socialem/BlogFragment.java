package com.devjpah.socialem;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment {

    RecyclerView storiesBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        storiesBar = view.findViewById(R.id.stories_bar);
        Context context = getActivity().getApplicationContext();
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
        storiesBar.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false));
        storiesBar.addItemDecoration(new StoriesDecoration(10));
    }
}