package com.example.patryk.kolorowanka;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import math.geom2d.Point2D;
import math.geom2d.Shape2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.polygon.Rectangle2D;
import math.geom2d.polygon.SimplePolygon2D;

public class MyViewModel extends ViewModel {

    private MutableLiveData<List<Shape2D>> mutableShapes;
    private MutableLiveData<List<Integer>> mutableColors;
    private MutableLiveData<Integer> paintColor;

    public LiveData<List<Shape2D>> getShapes() {
        if (mutableShapes == null) {
            mutableShapes = new MutableLiveData<List<Shape2D>>();
            initShapeArray();
        }
        return mutableShapes;
    }

    private void initShapeArray() {
        List<Shape2D> shapes = new ArrayList<>();

        shapes.add(new Circle2D(new Point2D(0.655, 0.045), 0.03)); // dym gorny
        shapes.add(new Circle2D(new Point2D(0.620, 0.1), 0.023)); // dym
        shapes.add(new Circle2D(new Point2D(0.607, 0.161), 0.02)); // dym dolny

        shapes.add(new Rectangle2D(0.246, 0.475, 0.48, 0.345)); // dom
        shapes.add(new Rectangle2D(0.433, 0.639, 0.107, 0.181)); // drzwi

        shapes.add(new Rectangle2D(0.560, 0.200, 0.087, 0.227)); // komin
        SimplePolygon2D poli = new SimplePolygon2D(
                new Point2D(0.489, 0.265),
                new Point2D(0.247, 0.475),
                new Point2D(0.723, 0.475)
        );
        shapes.add(poli); // dach
        shapes.add(new Rectangle2D(0.28,0.509,0.126,0.116));
        shapes.add(new Rectangle2D(0.56,0.509,0.126,0.116));


        shapes.add(new Circle2D(new Point2D(0.817, 0.741), 0.056)); // krzak
        shapes.add(new Circle2D(new Point2D(0.837, 0.771), 0.045)); // krzak
        shapes.add(new Circle2D(new Point2D(0.9, 0.78), 0.050)); // krzak

        mutableShapes.postValue(shapes);
    }

    public LiveData<List<Integer>> getColors() {
        if (mutableColors == null) {
            mutableColors = new MutableLiveData<List<Integer>>();
            initColorArray();
        }
        return mutableColors;
    }

    private void initColorArray() {
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);
        colors.add(Color.WHITE);

        mutableColors.postValue(colors);
    }

    public LiveData<Integer> getPaintColor(){
        if (paintColor == null) {
            paintColor = new MutableLiveData<Integer>();
            paintColor.postValue(Color.WHITE);
        }
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor.postValue(paintColor);
    }
}
