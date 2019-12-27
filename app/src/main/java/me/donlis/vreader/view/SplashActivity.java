package me.donlis.vreader.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import me.donlis.vreader.R;
import me.donlis.vreader.view.main.MainActivity;

public class SplashActivity extends FragmentActivity {

    private MyHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new MyHandler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }, 500);

    }

    @Override
    protected void onDestroy() {
        handler = null;
        super.onDestroy();
    }

    private static class MyHandler extends Handler{

    }
}