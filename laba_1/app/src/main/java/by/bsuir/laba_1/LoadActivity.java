package by.bsuir.laba_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoadActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Integer loading  = 0;
    private ImageView cat;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        progressBar = findViewById(R.id.progressBar);
        cat = findViewById(R.id.cat_load);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.load_animation);
        cat.startAnimation(animation);
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                loading++;
                Log.i("LOADING", loading.toString());
                progressBar.setProgress(loading);
                if(loading == 100){
                    timer.cancel();
                    Intent intent = new Intent(LoadActivity.this, AuthActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.schedule(task, 0, 30);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Log.i("LOADING", loading.toString());
        loading = 0;
    }
}