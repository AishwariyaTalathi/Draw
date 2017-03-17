package edu.csulb.android.draw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by aishwariyatalathi on 3/12/17.
 */

public class CustomView extends View {
    Path path_draw;
    Paint paint;//defines how to draw
    Paint cpaint;//defines what to draw
    int colour=0xFF000000;
    int colour_grey=0xFFCFD8DC;
    Canvas canvas;
    float currentsize;


    Bitmap bitmap;

    public void initialize(){
        currentsize=20;
        //lastsize=currentsize;
        path_draw=new Path();
        paint=new Paint();
        paint.setColor(colour);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(currentsize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        cpaint=new Paint(Paint.DITHER_FLAG);
    }
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0 , 0, cpaint);
        canvas.drawPath(path_draw, paint);
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //create canvas of certain device size.
        super.onSizeChanged(w, h, oldw, oldh);

        //create Bitmap of certain w,h
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        //apply bitmap to graphic to start drawing.
        canvas = new Canvas(bitmap);
    }
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path_draw.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path_draw.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                path_draw.lineTo(touchX, touchY);
                canvas.drawPath(path_draw, paint);
                path_draw.reset();
                break;
            default:
                return false;
        }
        //redraw
        invalidate();
        return true;
    }
    public void onshake(){
        Intent intent=new Intent(getContext(),PlayMusicService.class);
        Context x=getContext();
        x.startService(intent);
        path_draw=new Path();
        canvas.drawColor(colour_grey);
        invalidate();
    }
}
