package my.utar.com.billsplittingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        TextView textView = findViewById(R.id.textViewSplashScreen);
        // Start blinking animation
        SplashIconBlink();
        SplashTextBlink(textView);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    // icon Blinking animation
    private void SplashIconBlink(){
        ImageView imageView = findViewById(R.id.icon_blink);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.icon_blink);
        imageView.startAnimation(animation);
    }

    // text Blinking animation
    private void SplashTextBlink(TextView textView){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.icon_blink);
        textView.startAnimation(animation);
    }
}