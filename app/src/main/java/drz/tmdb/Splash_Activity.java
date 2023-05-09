package drz.tmdb;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);  //快捷键alt+enter
        //去除默认标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }


        //延时操作
        Timer timer = new Timer();
        timer.schedule(timetast, 500);
    }
    TimerTask timetast = new TimerTask() {
        @Override
        public void run() {

            startActivity(new Intent(Splash_Activity.this, MainActivity.class));//跳转登录界面
        }


    };



}