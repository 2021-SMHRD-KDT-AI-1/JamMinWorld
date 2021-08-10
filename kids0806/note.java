package com.example.kids0806;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//public class note extends AppCompatActivity {
public class note extends AppCompatActivity {

    BottomNavigationView btn_nav;
    note_timer note_t;
    note_random note_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        btn_nav = findViewById(R.id.btn_nav);

        // Fragment는 각각에 대하여 객체를 생성한다!
        note_t = new note_timer();
        note_r = new note_random();

        // fragment의 화면 초기화 하기
        // 맨 처음의 fragment(note_timer)를 초기화 한다!
        // commit() : 파일에 저장하기!
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, note_t).commit();

        // 네비게이션 버튼이 눌렸을 때 해당하는 Fragment로 이동 시켜주기!
        btn_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.list_timer){
                    // fragment_timer
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, note_t).commit();
                }else if (item.getItemId() == R.id.list_random){
                    // fragment_random
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, note_r).commit();
                }

                return true; // 선택한 메뉴를 반환해주는 부분
            }
        });

        Log.d("test", "실행");
    }



}