package com.example.patryk.kolorowanka;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.awt.font.TextAttribute;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.Shape2D;
import math.geom2d.ShapeArray2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.conic.Ellipse2D;
import math.geom2d.polygon.Rectangle2D;
import math.geom2d.polygon.SimplePolygon2D;

/**
 * @author Patryk Heciak 228051
 */
public class MainActivity extends AppCompatActivity {

    private MySurfaceView surface;
    private MyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surface = findViewById(R.id.surface_view);

        model = ViewModelProviders.of(this).get(MyViewModel.class);

        model.getShapes().observe(this, new Observer<List<Shape2D>>() {
            @Override
            public void onChanged(@Nullable List<Shape2D> shapes) {
                surface.setShapes(shapes);
            }
        });

        model.getColors().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(@Nullable List<Integer> colors) {
                surface.setColors(colors);
            }
        });

        model.getPaintColor().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer color) {
                surface.setDrawingColor(color);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hide();
    }

    private void hide() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        surface.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void colorClicked(View v) {
        switch (v.getId()) {
            case R.id.color_red:
                model.setPaintColor(Color.RED);
                break;
            case R.id.color_green:
                model.setPaintColor(Color.GREEN);
                break;
            case R.id.color_blue:
                model.setPaintColor(Color.BLUE);

                break;
            case R.id.color_yellow:
                model.setPaintColor(Color.YELLOW);

                break;
            case R.id.color_magenta:
                model.setPaintColor(Color.MAGENTA);

                break;
            case R.id.color_cyan:
                model.setPaintColor(Color.CYAN);

                break;
            case R.id.color_white:
                model.setPaintColor(Color.WHITE);

                break;
            case R.id.color_black:
                model.setPaintColor(Color.BLACK);

                break;
        }
    }
}
