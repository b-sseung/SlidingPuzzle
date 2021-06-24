package com.sseung.slidingpuzzle;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    boolean value = true;
    PuzzleDatabase database;

    View decorView;
    int	uiOption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //상단바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility( uiOption );

//        UseAll.window = getWindow();
//        UseAll.hideNavigationBar();

        if (PublicFunction.context == null) PublicFunction.context = this;
        database = PuzzleDatabase.getInstance(PublicFunction.context);
        database.open();

        Log.d("tlqkf", PublicFunction.loadNickName());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (PublicFunction.loadNickName().equals("noData")){
                    Intent intent = new Intent(getApplicationContext(), EntrolmentActivity.class);
                    if (value) startActivity(intent);
                    overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    if (value) startActivity(intent);
                    overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                    finish();
                }

            }
        }, 4000);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        // super.onWindowFocusChanged(hasFocus);

        if( hasFocus ) {
            decorView.setSystemUiVisibility(uiOption);
        }
    }
}

