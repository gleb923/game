package com.example.gleb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Game game;
    Control control;
    ControlGun gun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = findViewById(R.id.gameView);
        control = findViewById(R.id.control);
        gun = findViewById(R.id.gun);

        control.setOnChangeListener((float vx, float vy, float direction) -> {
            game.setHeroSpeed(vx, vy, direction);
        });

        gun.setOnShootListener(() -> game.shootHero());
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        game.resume();
//    }
}