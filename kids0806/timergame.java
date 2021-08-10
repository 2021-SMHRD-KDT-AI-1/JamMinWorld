package com.example.kids0806;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class timergame extends AppCompatActivity {

    int limit, game_limit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timergame);

        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        limit = pref.getInt("limit", 0);
        game_limit = limit;

        // 홈버튼(타이머게임(선택) -> 메인페이지)
        Button timer_out =(Button)findViewById(R.id.timer_out);
        timer_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timergame_mainpage = new Intent(timergame.this, com.example.kids0806.mainpage.class);
                startActivity(timergame_mainpage);
                finish();
            }
        });

        // 연습모드 이동버튼
        Button timer_btn_practice =(Button)findViewById(R.id.timer_btn_practice);
        timer_btn_practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timergame_practice = new Intent(timergame.this, com.example.kids0806.timerpractice.class);
                startActivity(timergame_practice);
                finish();
            }
        });

        // 경쟁모드 이동버튼
        Button timer_btn_rank =(Button)findViewById(R.id.timer_btn_rank);
        timer_btn_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(game_limit<5) {
                    game_limit += 1;
                    String game_limit12 = Integer.toString(game_limit);
                    Log.d("limit", game_limit12);
                    Intent timergame_rank = new Intent(timergame.this, com.example.kids0806.timerrank.class);
                    startActivity(timergame_rank);
                    finish();


                }else {

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("limit",game_limit);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "내일 또 만나요~",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}