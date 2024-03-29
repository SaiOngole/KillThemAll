package com.is3av.kill;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
 
@SuppressLint("WrongCall")
public class GameView extends SurfaceView {
       private GameLoopThread gameLoopThread;
       private List<Sprite> sprites = new ArrayList<Sprite>();
       private List<TempSprite> temps = new ArrayList<TempSprite>();
       private long lastClick;
       private Bitmap bmpBlood;
 
       public GameView(Context context) {
             super(context);
             gameLoopThread = new GameLoopThread(this);
             getHolder().addCallback(new SurfaceHolder.Callback() {
 
                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                           boolean retry = true;
                           gameLoopThread.setRunning(false);
                           while (retry) {
                                  try {
                                        gameLoopThread.join();
                                        retry = false;
                                  } catch (InterruptedException e) {}
                           }
                    }
 
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                           createSprites();
                           gameLoopThread.setRunning(true);
                           gameLoopThread.start();
                    }
 
                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format,
                                  int width, int height) {
                    }
             });
             bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
       }
 
       private void createSprites() {
           for(int i = 0;i<4;i++) {  
        	 sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad2));
             sprites.add(createSprite(R.drawable.bad2));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             sprites.add(createSprite(R.drawable.bad1));
             
             sprites.add(createSprite(R.drawable.bad2));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
             sprites.add(createSprite(R.drawable.good));
           }
       }
 
       private Sprite createSprite(int resouce) {
             Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
             return new Sprite(this, bmp);
       }
 
       @SuppressLint("WrongCall")
	@Override
       protected void onDraw(Canvas canvas) {
             canvas.drawColor(Color.BLACK);
             for (int i = temps.size() - 1; i >= 0; i--) {
                    temps.get(i).onDraw(canvas);
             }
             for (Sprite sprite : sprites) {
                    sprite.onDraw(canvas);
             }
       }
 
       @Override
       public boolean onTouchEvent(MotionEvent event) {
             if (System.currentTimeMillis() - lastClick > 300) {
                    lastClick = System.currentTimeMillis();
                    float x = event.getX();
                    float y = event.getY();
                    synchronized (getHolder()) {
                           for (int i = sprites.size() - 1; i >= 0; i--) {
                                  Sprite sprite = sprites.get(i);
                                  if (sprite.isCollition(x, y)) {
                                        sprites.remove(sprite);
                                        temps.add(new TempSprite(temps, this, x, y, bmpBlood));
                                        break;
                                  }
                           }
                    }
             }
             return true;
       }
}