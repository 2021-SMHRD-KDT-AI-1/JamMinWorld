package com.example.kids0806;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class timerscore extends AppCompatActivity {
    Button score_back, score_out;

    TextView score_nowpoint, score_highpoint;

    RequestQueue requestQueue;
    StringRequest request2, request3;


    String globalName ;
    Intent intent;
    int point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timerscore);

        score_highpoint = findViewById(R.id.score_highpoint);

        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        // globalName 은 전역변수처럼 쓸수 있음
        globalName = pref.getString("globalName", "");

//        intent = getIntent();
//        int s = getIntent().getIntExtra("point",1);
//

        SharedPreferences point1  = getSharedPreferences("point", MODE_PRIVATE);
        point =  point1.getInt("point",0);
        Log.d("qw", String.valueOf(point));

        // 각각의 변수 지정


        score_nowpoint = findViewById(R.id.score_nowpoint);

        requestQueue = Volley.newRequestQueue(getApplicationContext());




        requestQueue = Volley.newRequestQueue(getApplicationContext());



        String url2 = "http://121.179.170.180:8094/jammin/bestscore";
        // 전송 방법, url, 응답 성공시, 응답 실패시 - > request 작성
        request3 = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {
                            Log.d("tre", response);

                            try {
                                JSONArray array = new JSONArray(response);
                                //Log.d("ranking",array.getString(2));

                                String bestscore = array.getString(2);
                                Log.d("best", bestscore);
                                score_highpoint.setText(bestscore);




                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            Toast.makeText(getApplicationContext(), "갱신성공..", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "넌뜨지마..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "갱신실패....", Toast.LENGTH_SHORT).show();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                // 넘겨줄 데이터 params 에 담아주기
                params.put("point", String.valueOf(point));
                params.put("edt_up_name", globalName);

                return params;
            }
        };
        requestQueue.add(request3);




        String url = "http://121.179.170.180:8095/jammin/ranking";

        // 전송 방법, url, 응답 성공시, 응답 실패시 - > request 작성

        request2 = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        //이클립스 ranking 컨트롤로에서 nor_list 를 반환하여 받아줌
                        if(response!=null){

                            // 잘 받아왓나 확인해주는 Log
                            Log.d("rank",response);


                                score_nowpoint.setText(String.valueOf(point));
                        }else{
                            Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "조회실패", Toast.LENGTH_SHORT).show();

            }
        }

        )
        {

            // 안드로이드에서 이클립스로 조회할 name을 보내주는 메소드
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();

                // 넘겨줄 데이터 params 에 담아주기
                // globalName 은 로그인시 save 메소드를 통하여 저장해준 name 을 담고잇는 전역변수같은거!
                params.put("edt_score_id", globalName);

                return params;
            }
        };
        requestQueue.add(request2);









        // 뒤로가기 버튼(타이머게임(순위표) -> 타이머게임 선택화면)
        score_back =(Button)findViewById(R.id.score_back);
        score_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timerscore_timer = new Intent(timerscore.this, com.example.kids0806.timergame.class);
                startActivity(timerscore_timer);
                finish();
            }
        });

        // 홈버튼(타이머게임(순위표) -> 메인페이지)
        score_out =(Button)findViewById(R.id.score_out);
        score_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timerscore_mainpage = new Intent(timerscore.this, com.example.kids0806.mainpage.class);
                startActivity(timerscore_mainpage);
                finish();
            }
        });

    }
}