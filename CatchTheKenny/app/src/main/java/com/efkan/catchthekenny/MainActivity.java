package com.efkan.catchthekenny;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView scoreCounter;
    TextView timeCounter;
    int score;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;
    ImageView[] imageArray;
    Runnable runnable;
    Handler handler;
    int highestScore=0;
    SharedPreferences sharedPreferences;
    int deger=0;
    TextView high;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = this.getSharedPreferences("com.efkan.catchthekenny", MODE_PRIVATE);
        highestScore = sharedPreferences.getInt("highest", 0); // En yüksek skoru sharedPreferences'ten al
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        high = findViewById(R.id.textViewhighscore);
        high.setText("Highest Score:"+ highestScore); // En yüksek skoru TextView'a set et

        scoreCounter = findViewById(R.id.textViewscore);
        timeCounter = findViewById(R.id.textViewtime);
        score = 0;
        new CountDownTimer(16000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeCounter.setText("Time:"+(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                if(highestScore<score)
                {
                    highestScore=score;
                }
                sharedPreferences.edit().putInt("highest",highestScore).apply();


                for(ImageView image:imageArray)
                {
                    image.setVisibility(View.INVISIBLE);
                }
                timeCounter.setText("Time Off!");
                handler.removeCallbacks(runnable);   //burada runnable işlemini durdurdum çünkü süre bitti.hiçbir resim olamayacak ekranda .
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart game ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=getIntent(); //gelen bir değer olmadığı için aynı aktiviteyi tekrardan çağırdım.
                        finish();//uygulumayı çok yormamak için güncel aktiviteyi kapattım. aynı aktiviteyi baştan açtım.
                        startActivity(intent);
                        high.setText("Highest Score:"+ highestScore);

                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this,"Game Over!",Toast.LENGTH_SHORT).show();
                    finish();
                    }
                });
                alert.show();  //unutmamak lazım . bunu yazmazsak alert gösterilmez.
                Toast.makeText(getApplicationContext(),"Time is up!",Toast.LENGTH_SHORT).show();
            }
        }.start();
        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);
        imageView10=findViewById(R.id.imageView10);
        imageView11=findViewById(R.id.imageView11);
        imageView12=findViewById(R.id.imageView12);
        imageArray =new ImageView[]{imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12};
        hideImages();

    }
    public void increaseScore(View view)
    {
        score++;
        scoreCounter.setText("Score:"+score);
    }
    public  void hideImages()    //resimleri saklamamızı sağlayan fonksiyon,
    {
        handler=new Handler();
        runnable =new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE); //ınvısıble=görünmez
                }
                Random random=new Random();
                int i= random.nextInt(9);  //0 dan 8 e kadar olan değerleri alıyor.
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(runnable,400); //500 milisaniyede bir rötarlı olarak çalışmasını ayarladım.
            }
        };
        handler.post(runnable);

    }
}