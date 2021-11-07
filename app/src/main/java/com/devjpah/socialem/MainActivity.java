package com.devjpah.socialem;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;
import nl.joery.animatedbottombar.BadgeView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabAdd;
    FragmentManager fragmentManager;
    BlogFragment blogFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();

        blogFragment = new BlogFragment();
        profileFragment = new ProfileFragment();
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.it_blog, true);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, blogFragment).commit();
        }
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (tab1.getId()) {
                    case R.id.it_blog:
                        transaction.replace(R.id.fragment_container, blogFragment);
                        break;
                    case R.id.it_profile:
                        transaction.replace(R.id.fragment_container, profileFragment);
                        break;
                }
                transaction.commit();
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });

        //Events
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        //Badges
        animatedBottomBar.setBadgeAtTabIndex(0, new AnimatedBottomBar.Badge("3", getResources().getColor(R.color.accent), getResources().getColor(R.color.primary_light), 20));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder
                .setTitle("Salir")
                .setMessage("¿Está seguro que desea salir?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    private void conectar() {
        animatedBottomBar = findViewById(R.id.animated_bottom_bar);
        fabAdd = findViewById(R.id.fab_add);
    }
}