package com.example.kids0806;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class mypageplant extends AppCompatActivity {

    ImageView imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7;
    Button btn_water;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypageplant);
// 뒤로가기 버튼(연습모드 -> 타이머게임 선택화면)


        ImageView plant_back = (ImageView) findViewById(R.id.plant_back);
        plant_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypageplant = new Intent(mypageplant.this, mypage.class);
                startActivity(mypageplant);
                finish();
            }
        });

        // 홈버튼(연습모드 -> 메인페이지)
        ImageView plant_out = (ImageView) findViewById(R.id.plant_out);
        plant_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypage_1 = new Intent(mypageplant.this, com.example.kids0806.mainpage.class);
                startActivity(mypage_1);
                finish();
            }
        });
        btn_water = (Button) findViewById(R.id.button2);

        imageView7 = findViewById(R.id.water_img);
        imageView7.setVisibility(View.INVISIBLE);
        btn_water.setClickable(false);
        Intent intentCheck = getIntent();
        if (intentCheck != null) {
            btn_water.setClickable(true);
            btn_water.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (imageView7.getVisibility() == View.INVISIBLE) {
                        imageView7.setVisibility(View.VISIBLE);
                    }
                    imageView7.animate().alpha(0.0f).setDuration(10000);
                }
            });
        }


        imageView = (ImageView) findViewById(R.id.plant_img);
        imageView2 = (ImageView) findViewById(R.id.plant_background);
        imageView3 = (ImageView) findViewById(R.id.col_img1);
        imageView4 = (ImageView) findViewById(R.id.col_img2);
        imageView5 = (ImageView) findViewById(R.id.col_img3);
        imageView6 = (ImageView) findViewById(R.id.col_img4);
//        imageView7 = (ImageView) findViewById(R.id.water_img);
        Glide.with(this).load(R.drawable.plant7).into(imageView);
        Glide.with(this).load(R.drawable.sky_and_sun).into(imageView2);
        Glide.with(this).load(R.drawable.plant1).into(imageView3);
        Glide.with(this).load(R.drawable.plant6).into(imageView4);
        Glide.with(this).load(R.drawable.plant8).into(imageView5);
        Glide.with(this).load(R.drawable.plant4).into(imageView6);
        Glide.with(this).load(R.drawable.watering2).into(imageView7);
    }
}