package com.sseung.slidingpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView[] views, balloonViews;
    int[] images;
    boolean[] check;
    int[][] list;

    ImageView originImage, lookImage;
    LinearLayout game_layout, pause_text, pause_layout, under_menu, clear_under;
    TextView menu_restart, menu_rank, menu_cancel;
    ImageView start_layout, under_replay, under_rank;

    TextView time_text;

    Handler handler = new Handler();
    TimeCheck thread = new TimeCheck();

    int time = 0;
    boolean value = true, anim_value = true, clear_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        views = new ImageView[17];
        balloonViews = new ImageView[12];
        check = new boolean[17];
        images = new int[17];
        list = new int[4][4];

        time_text = findViewById(R.id.time_text);

        lookImage = findViewById(R.id.lookImage);
        originImage = findViewById(R.id.originImage);

        Arrays.fill(check, false);

        start_layout = findViewById(R.id.start_layout);
        game_layout = findViewById(R.id.game_layout);

        pause_text = findViewById(R.id.pause_text);
        pause_layout = findViewById(R.id.pause_layout);
        menu_restart = findViewById(R.id.menu_restart);
        menu_rank = findViewById(R.id.menu_rank);
        menu_cancel = findViewById(R.id.menu_cancel);

        under_menu = findViewById(R.id.under_menu);
        clear_under = findViewById(R.id.clear_under);

        under_replay = findViewById(R.id.under_replay);
        under_rank = findViewById(R.id.under_rank);

        for (int i = 1; i < 17; i++){
            String num = (i < 10) ? "0" + i : Integer.toString(i);

            int id = getResources().getIdentifier("image" + num, "id", getApplicationContext().getPackageName());
            int res = getResources().getIdentifier("image" + num, "drawable", getApplicationContext().getPackageName());

            views[i] = (ImageView) findViewById(id);
            images[i] = (int) res;

            int position = i;
            views[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickEvent(position);
                }
            });
        }

        for (int i = 0; i < 12; i++){
            String num = (i+1 < 10) ? "0" + (i+1) : Integer.toString(i+1);

            int id = getResources().getIdentifier("balloon_image" + num, "id", getApplicationContext().getPackageName());

            balloonViews[i] = (ImageView) findViewById(id);
        }

        testNumber();
//        randomNumber();

        for (int i = 0; i < 4; i++){
            Log.d("tlqkf", Arrays.toString(list[i]));
        }

        lookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originImage.setImageResource(R.drawable.origin);
                originImage.setVisibility(View.VISIBLE);
                Animation alpha_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_in);
                alpha_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        anim_value = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            Thread.sleep(3000);
                            Animation alpha_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_out);
                            alpha_out.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    originImage.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                            originImage.startAnimation(alpha_out);
                            anim_value = true;
                            value = true;

                            if(thread.isAlive()){
                                thread.interrupt();
                            }

                            thread = new TimeCheck();
                            thread.start();

                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                if (anim_value) {
                    originImage.startAnimation(alpha_in);
                    value = false;
                }
            }
        });

        pause_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = false;
                pause_layout.setVisibility(View.VISIBLE);
                Animation layout_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_in);
                pause_layout.startAnimation(layout_in);
            }
        });

        menu_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = true;
                randomNumber();
                time = 0;

                Animation layout_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_out);
                layout_out.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        pause_layout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                pause_layout.startAnimation(layout_out);

                if(thread.isAlive()){
                    thread.interrupt();
                }

                thread = new TimeCheck();
                thread.start();
            }
        });

        menu_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menu_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = true;

                Animation layout_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_out);
                layout_out.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        pause_layout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                pause_layout.startAnimation(layout_out);

                if(thread.isAlive()){
                    thread.interrupt();
                }

                thread = new TimeCheck();
                thread.start();
            }
        });

        start_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation layout_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.layout_in);
                layout_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Animation layout_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.layout_out);
                        layout_out.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                start_layout.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        start_layout.startAnimation(layout_out);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        time = 0;

                        if(thread.isAlive()){
                            thread.interrupt();
                        }

                        thread = new TimeCheck();
                        thread.start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                game_layout.setVisibility(View.VISIBLE);
                game_layout.startAnimation(layout_in);
            }
        });

        under_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear_under.setVisibility(View.INVISIBLE);
                under_menu.setVisibility(View.VISIBLE);

                testNumber();
//                randomNumber();

                time = 0;

                Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_out);
                out.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        originImage.setVisibility(View.INVISIBLE);

                        if(thread.isAlive()){
                            thread.interrupt();
                        }

                        thread = new TimeCheck();
                        thread.start();


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                originImage.startAnimation(out);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!clear_check){
                    if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                        value = true;
                        if(thread.isAlive()){
                            thread.interrupt();
                        }

                        thread = new TimeCheck();
                        thread.start();
                        Log.d("tlqkf", "????????? on");
                    } else {
                        value = false;
                        Log.d("tlqkf", "????????? off");
                    }
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    public void clickEvent(int num){
        if (list[(num-1)/4][(num-1)%4] == 0) return;

        int row = (num-1)/4;
        int col = (num-1)%4;

        int[] rowArray = new int[]{1, -1, 0, 0};
        int[] colArray = new int[]{0, 0, 1, -1};

        for (int i = 0; i < 4; i++){
            int rowTemp = row + rowArray[i];
            int colTemp = col + colArray[i];

            if (rowTemp < 0 || rowTemp > 3 ||colTemp < 0 || colTemp > 3) continue;

            if (list[rowTemp][colTemp] == 0) {
                list[rowTemp][colTemp] = list[row][col];
                list[row][col] = 0;

                int position = (i == 0) ? num + 4 : (i == 1) ? num - 4 : (i == 2) ? num + 1 : num - 1;
                views[position].setImageResource(images[list[rowTemp][colTemp]]);
                views[num].setImageResource(0);

                break;
            }
        }

        boolean checkValue = false;
        for (int i = 1; i < 16; i++){
            if (list[(i-1)/4][(i-1)%4] != i) {
                Log.d("tlqkf", ((i-1)/4) + ", " + ((i-1)%4) + " : " + list[(i-1)/4][(i-1)%4] + ", " + i);
                checkValue = true;
                break;
            }
        }

        if (!checkValue) {
            value = false;
            clear_check = true;
            clear();
        }

        Log.d("tlqkf", "value : " + value);
    }

    public void clear(){
        views[16].setImageResource(images[16]);

        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_in);
        in.setDuration(1500);
        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation in2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_in);
                in2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        clear_under.setVisibility(View.VISIBLE);
                        under_menu.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                originImage.setVisibility(View.VISIBLE);
                originImage.setImageResource(R.drawable.clear_image);
                originImage.startAnimation(in2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        views[16].startAnimation(in);

        balloonAnimation1(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                balloonAnimation2(11);
            }
        }, 500);

    }

    public void balloonAnimation1(int num){
        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.balloon_anim);
        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (num < 11) {
                    balloonAnimation1(num+1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        balloonViews[num].startAnimation(in);
        Log.d("tlqkf", "balloon start : " + num);
    }

    public void balloonAnimation2(int num){
        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.balloon_anim);
        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (num > 0) {
                    balloonAnimation2(num-1);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        balloonViews[num].startAnimation(in);
        Log.d("tlqkf", "balloon start : " + num);
    }

    public void randomNumber(){
        Random random = new Random();

        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            numbers.add(i);
        }

        int position = 1;
        while (position < 16) {

            if (position == 15) {
                check[numbers.get(0)] = true;
                views[position].setImageResource(images[numbers.get(0)]);
                list[(position-1)/4][(position-1)%4] = numbers.get(0);
                position++;

                continue;
            }

            int num = random.nextInt(numbers.size());

            int selectN = numbers.get(num);
            numbers.remove(num);

            check[selectN] = true;

            views[position].setImageResource(images[selectN]);

            list[(position-1)/4][(position-1)%4] = selectN;

            position++;
        }

//        for (int i = 1; i <= 15; i++){
//                int num = random.nextInt(15) + 1; //0 ~ 14 ????????? ???????????? ????????? +1??? ????????? ????????? 1 ~ 15
//
//                while(check[num]){
//                    num = random.nextInt(15) + 1;
//                }
//
//                check[num] = true;
//
//                views[i].setImageResource(images[num]);
//
//                Log.d("tlqkf", i + ":" + ((i-1)/4 + ", " + ((i-1)%4)));
//                list[(i-1)/4][(i-1)%4] = num;
//        }

        views[16].setImageResource(0);
    }

    public void testNumber(){
        for (int i = 1; i <= 15; i++){
            int num = i;

            check[num] = true;

            views[i].setImageResource(images[num]);
            list[(i-1)/4][(i-1)%4] = num;
        }

        views[16].setImageResource(0);
    }

    class TimeCheck extends Thread {

        public void run(){

            try {
                while (value) {
                    Thread.sleep(1000);

                    time++;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int minute = time / 60;
                            String second = (time % 60 < 10) ? "0" + time % 60 : Integer.toString(time % 60);

                            time_text.setText(minute + ":" + second);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.d("tlqkf", "thread ??????");
            }
        }

    }
}