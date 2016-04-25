package com.pujanparikh.dodger;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class gameLoop extends AppCompatActivity {


    boolean gocalled;
    int backspeed;
    Handler h = new Handler();
    Button retry, moveright, moveleft;
    TextView score, scorelive, scoretemp;
    int countscore = 0;
    TextView scorebox;
    ImageView bear1, bear2, animImageView;
    ImageView back1, back2, back3;
    int speed = 15;
    Rect thiefRect = new Rect();
    Rect bear1Rect = new Rect();
    Rect bear2Rect = new Rect();
    Runnable r;
    int devWidth, devHeight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_loop);

        initstuff();
        setinitialchar();
        movement();
        huntermovement();


    }

    public void initstuff(){

        retry = (Button) findViewById(R.id.retryBtn);
        score = (TextView)findViewById(R.id.scoretxt);
        scorebox = (TextView) findViewById(R.id.scorebox);
        scoretemp = (TextView) findViewById(R.id.scoretemp);
        scorelive = (TextView) findViewById(R.id.scoreview);
        score.setVisibility(View.INVISIBLE);
        scorebox.setVisibility(View.INVISIBLE);
        retry.setVisibility(View.INVISIBLE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        devWidth = size.x;
        devHeight = size.y;
        System.out.println("devheight = "+ devHeight + " devwidth = " + devWidth);

        back1 = (ImageView) findViewById(R.id.back1);
        back2 = (ImageView) findViewById(R.id.back2);
        back3 = (ImageView) findViewById(R.id.back3);

        back1.setMaxWidth(devWidth);
        back1.setMaxHeight(devHeight);
        back2.setMaxWidth(devWidth);
        back2.setMaxHeight(devHeight);
        back3.setMaxWidth(devWidth);
        back3.setMaxHeight(devHeight);

        spriteani();
        bear1.getHitRect(bear1Rect);
        bear2.getHitRect(bear2Rect);
        animImageView.getHitRect(thiefRect);

        gocalled = false;

    }

    public  void reframebear() {
        int limit = devHeight + 200 ;

        if (bear1.getY() > limit){
            bear1.setX(placecrowdX());
            bear1.setY(-30);
        }

        if (bear2.getY() > limit){
            bear2.setX(placecrowdX());
            bear2.setY(-30);
        }

        if (back1.getY() > limit){
            back1.setY(back3.getY() - devWidth);
        }
        if (back2.getY() > limit){
            back2.setY(back1.getY() - devWidth);
        }
        if (back3.getY() > limit){
            back3.setY(back2.getY() - devWidth);
        }

    }

    public  void spriteani(){

        animImageView = (ImageView) findViewById(R.id.ivAnim);
        animImageView.setBackgroundResource(R.drawable.anim);
        animImageView.post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation =
                        (AnimationDrawable) animImageView.getBackground();
                frameAnimation.start();
            }
        });

        bear1 = (ImageView) findViewById(R.id.bear1);
        bear1.setBackgroundResource(R.drawable.bear);
        bear1.post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation = (AnimationDrawable) bear1.getBackground();
                frameAnimation.start();

            }
        });

        bear2 = (ImageView) findViewById(R.id.bear2);
        bear2.setBackgroundResource(R.drawable.bear);
        bear2.post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation = (AnimationDrawable) bear2.getBackground();
                frameAnimation.start();

            }
        });

    }

    public void setinitialchar() {

        back1.setX(0);
        back1.setY(0);
        back2.setX(0);
        back2.setY( devHeight);
        back3.setX(0);
        back3.setY(devHeight +  devHeight);


        animImageView.setX(20);
        animImageView.setY((float) (devHeight - (0.3 * devHeight)));



        bear1.setX(placecrowdX());
        bear1.setY(20);
        bear2.setX(placecrowdX());
        bear2.setY(-520);


    }

    public float placecrowdX (){
        int random = (int)(Math.random() * (devWidth - 90) + 30);
        return random;
    }

    public void scoreincrease(){

        countscore++;

        scorelive.setText(Integer.toString(countscore));


    }

    public void huntermovement(){
        moveright = (Button) findViewById(R.id.moveRight);
        moveright.setBackgroundColor(Color.TRANSPARENT);
        moveleft = (Button) findViewById(R.id.moveleft);
        moveleft.setBackgroundColor(Color.TRANSPARENT);

        moveright.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (animImageView.getX() > 0.8 * devWidth) {
                    animImageView.setX(animImageView.getX());
                } else {
                    animImageView.setX(animImageView.getX() + 50);
                }

                return true;
            }
        });

        moveleft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (animImageView.getX() < 0.1 * devWidth) {
                    animImageView.setX(animImageView.getX());
                } else {
                    animImageView.setX(animImageView.getX() - 50);
                }

                return true;
            }
        });

    }

    protected void movement(){
        //miliseconds the Y moves down
        final int delay = 5;
        speed = 10;

        backspeed = 5;

        r = new Runnable() {

            @Override
            public void run() {


                if (countscore % 300 == 0) {
                    speed = speed + 5;
                    backspeed = backspeed + 3;

                }
                boolean isDead = false;
                scoreincrease();

                bear1.setY(bear1.getY() + speed);
                bear2.setY(bear2.getY() + speed);

                back1.setY(back1.getY() + backspeed);
                back2.setY(back2.getY() + backspeed);
                back3.setY(back3.getY() + backspeed);
                reframebear();


                if ((animImageView.getX() > bear2.getX() - 50) && ( animImageView.getX() < bear2.getX() + 50 )) {

                if ((animImageView.getY() > bear2.getY()) && (animImageView.getY() < bear2.getY() + 40)){
                        System.out.println("goes inside  bear2 x ");
                        isDead = true;
                        retry.setVisibility(View.VISIBLE);
                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(gameLoop.this, startScreen.class));
                                finish();
                            }
                        });
                        dead();
                    }

                }


                if ((animImageView.getX() > bear1.getX() - 50 ) && (animImageView.getX() < bear1.getX() + 50 ) ){
                    if ((animImageView.getY() > bear1.getY()) && (animImageView.getY() < bear1.getY() + 40)){

                        System.out.println("goes inside  bear2 x ");
                        isDead = true;
                        retry.setVisibility(View.VISIBLE);
                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(gameLoop.this, startScreen.class));
                                finish();
                            }
                        });
                        dead();
                    }

                }



                if (!isDead) {
                    h.postDelayed(this, delay);
                }
            }
        };h.postDelayed(r, speed);
    }

    public void dead(){
        System.out.println("before callbacks null");
        h.removeCallbacksAndMessages(r);
        animImageView.removeCallbacks(null);
        bear1.removeCallbacks(null);
        bear2.removeCallbacks(null);
        System.out.println("goes inside dead function");
        bear1.setVisibility(View.INVISIBLE);
        bear2.setVisibility(View.INVISIBLE);
        back1.setVisibility(View.INVISIBLE);
        back2.setVisibility(View.INVISIBLE);
        back3.setVisibility(View.INVISIBLE);


        animImageView.setVisibility(View.INVISIBLE);
        scorelive.setVisibility(View.INVISIBLE);
        scoretemp.setVisibility(View.INVISIBLE);
        scorebox.setText(countscore + "");
        scorebox.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);

    }

}
