package com.example.kids0806;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class mypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Button mypage_back = (Button) findViewById(R.id.mypage_back);
        mypage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypage_1 = new Intent(mypage.this, com.example.kids0806.mainpage.class);
                startActivity(mypage_1);
                finish();
            }
        });
        // 홈버튼(마이페이지 -> 메인페이지)
        Button mypage_out =(Button)findViewById(R.id.mypage_out);
        mypage_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypage_mainpage = new Intent(mypage.this, com.example.kids0806.mainpage.class);
                startActivity(mypage_mainpage);
                finish();
            }
        });

        // 출석페이지 이동버튼
        Button mypage_btn_plant =(Button)findViewById(R.id.mypage_btn_plant);
        mypage_btn_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypage_plant = new Intent(mypage.this, com.example.kids0806.mypageplant.class);
                startActivity(mypage_plant);
                finish();
            }
        });

        // 앨범 이동버튼
        Button mypage_btn_album =(Button)findViewById(R.id.mypage_btn_album);
        mypage_btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypage_album = new Intent(mypage.this, mypagealbum.class);
                startActivity(mypage_album);
                finish();
            }
        });

        // 순위표 이동버튼
        Button mypage_btn_score =(Button)findViewById(R.id.mypage_btn_score);
        mypage_btn_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypage_score = new Intent(mypage.this, com.example.kids0806.mypagescore.class);
                startActivity(mypage_score);
                finish();
            }
        });

        // 로그아웃 버튼
        Button mypage_btn_logout =(Button)findViewById(R.id.mypage_btn_logout);
        mypage_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypage_logout = new Intent(mypage.this, login.class);
                startActivity(mypage_logout);
                finish();
            }
        });

    }
}