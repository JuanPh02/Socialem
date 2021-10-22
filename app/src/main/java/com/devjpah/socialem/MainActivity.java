package com.devjpah.socialem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    AnimatedBottomBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        //Badges
        //bottomAppBar.setBadgeAtTabIndex(0, new AnimatedBottomBar.Badge('99',));
    }

    private void conectar() {
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        fabAdd = findViewById(R.id.fab_add);
    }
}