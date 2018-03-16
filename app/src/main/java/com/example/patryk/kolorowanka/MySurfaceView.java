package com.example.patryk.kolorowanka;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;


import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.Shape2D;
import math.geom2d.ShapeArray2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.conic.Ellipse2D;
import math.geom2d.polygon.Rectangle2D;
import math.geom2d.polygon.SimplePolygon2D;

public class MySurfaceView extends SurfaceView {

    private static final String TAG = MySurfaceView.class.getName();
    private Paint paint1, paint2;
    private boolean firstFrameRendered = false;
    private Context context; // TODO make sure I need context
    private int width;
    private int height;

    private List<Shape2D> shapeArray2D;
    private List<Integer> colorArray;
    private Integer drawingColor;

    public MySurfaceView(Context context) {
        super(context);
        init(context);
        Log.d(TAG, "MySurfaceView(Context context)");
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        Log.d(TAG, "MySurfaceView(Context context, atrs)");
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        Log.d(TAG, "MySurfaceView(Context context, atrs, defStyleAttr)");
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        Log.d(TAG, "MySurfaceView(Context context, atrs, defStyleAttr, defStyleRes)");
    }

    private void init(Context context) {
        paint1 = new Paint();
        paint1.setColor(Color.RED);

        paint2 = new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(8);


//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.context = context;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetached");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX() / width;
            float y = (event.getY() - (height - width) / 2) / width;

            for (int i = shapeArray2D.size() - 1; i >= 0; i--) {
                Shape2D shape2D = shapeArray2D.get(i);
                if (shape2D instanceof Rectangle2D) {
                    if (((Rectangle2D) shape2D).contains(x, y)) {
                        colorArray.set(i, drawingColor);
                        return true;
                    }
                } else if (shape2D instanceof Circle2D) {
                    if (((Circle2D) shape2D).isInside(new Point2D(x, y))) {
                        colorArray.set(i, drawingColor);
                        return true;
                    }
                } else if (shape2D instanceof SimplePolygon2D) {
                    SimplePolygon2D poly = (SimplePolygon2D) shape2D;
                    if (!poly.contains(x, y)) {
                        Log.d(TAG, String.valueOf(poly.vertexNumber()));
                        colorArray.set(i, drawingColor);
                        return true;
                    }
                }

            }
        }

        return true;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!firstFrameRendered) {
            width = getHolder().getSurfaceFrame().width();
            height = getHolder().getSurfaceFrame().height();
            firstFrameRendered = true;
        } else {
            canvas.drawColor(Color.WHITE);
            for (int i = 0; i < shapeArray2D.size(); i++) {
                Shape2D shape = shapeArray2D.get(i);
                paint1.setColor(colorArray.get(i));

                if (shape instanceof Circle2D) {
                    Circle2D c = (Circle2D) shape;
                    float x = (float) (width * c.center().x());
                    float y = (float) (width * c.center().y());
                    float r = (float) (width * c.radius());
                    canvas.drawCircle(x, y, r, paint1);
                    canvas.drawCircle(x, y, r, paint2);
                } else if (shape instanceof Rectangle2D) {
                    Rectangle2D c = (Rectangle2D) shape;
                    float x = (float) (width * c.getX());
                    float y = (float) (width * c.getY());
                    float w = (float) (width * c.getWidth());
                    float h = (float) (width * c.getHeight());
                    canvas.drawRect(x, y, x + w, y + h, paint1);
                    canvas.drawRect(x, y, x + w, y + h, paint2);
                } else if (shape instanceof SimplePolygon2D) {
                    SimplePolygon2D c = (SimplePolygon2D) shape;

                    Path path = new Path();
                    path.moveTo(
                            (float) (width * c.vertex(0).x()),
                            (float) (width * c.vertex(0).y()));
                    for (int j = 1; j < c.vertexNumber(); j++) {
                        path.lineTo(
                                (float) (width * c.vertex(j).x()),
                                (float) (width * c.vertex(j).y()));
                    }
                    path.lineTo(
                            (float) (width * c.vertex(0).x()),
                            (float) (width * c.vertex(0).y()));
                    path.close();
                    canvas.drawPath(path, paint1);
                    canvas.drawPath(path, paint2);
                }
            }
        }

        invalidate();
    }

    public void setShapes(List<Shape2D> shapeArray2D) {
        this.shapeArray2D = shapeArray2D;
    }

    public void setColors(List<Integer> colorArray) {
        this.colorArray = colorArray;
    }

    public void setDrawingColor(Integer drawingColor) {
        this.drawingColor = drawingColor;
    }
}

