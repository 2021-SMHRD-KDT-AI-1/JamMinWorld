package com.example.kids0806;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class mypagescore extends AppCompatActivity {

    Button myscore_back, myscore_out;

    TextView myscore_rank1,myscore_rank2,myscore_rank3,myscore_nick1,
            myscore_nick2,myscore_nick3,myscore_point1,myscore_point2,myscore_point3,
            myscore_myrank,myscore_mynick,myscore_mypoint, myscore_rank;

    RequestQueue requestQueue;
    StringRequest request2;


    String globalName;

    // 현재는 로그인이 안되어잇어 임의로 변수값 지정


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mypagescore);



        //로그인시 저장된 정보를 불러오는 함수
        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        // globalName 은 전역변수처럼 쓸수 있음
        globalName = pref.getString("globalName", "");


        Log.d("tq", globalName);

        // 각각의 변수 지정
        myscore_back =(Button)findViewById(R.id.myscore_back);
        myscore_out =(Button)findViewById(R.id.myscore_out);
        myscore_rank1 = findViewById(R.id.myscore_rank1);
        myscore_rank2 = findViewById(R.id.myscore_rank2);
        myscore_rank3 = findViewById(R.id.myscore_rank3);

        myscore_nick1 = findViewById(R.id.myscore_nick1);
        myscore_nick2 = findViewById(R.id.myscore_nick2);
        myscore_nick3 = findViewById(R.id.myscore_nick3);

        myscore_point1 = findViewById(R.id.myscore_point1);
        myscore_point2 = findViewById(R.id.myscore_point2);
        myscore_point3 = findViewById(R.id.myscore_point3);

        myscore_mynick = findViewById(R.id.myscore_mynick);
        myscore_myrank = findViewById(R.id.myscore_myrank);
        myscore_mypoint = findViewById(R.id.myscore_mypoint);

        myscore_rank = findViewById(R.id.myscore_rank);



        requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                            
                            // 리스트 형식이라 JSONArray 를 임폴트 하여 사용
                            try{
                                JSONArray array = new JSONArray(response);
                                //Log.d("ranking",array.getString(2));
                                
                                // for 문을 사용하기가 애매해서 일일이 입력함 
                                // array 에는 지금
                                // [1위, 1위 name, 1위 point, 2위, 2위 name, 2위 point, 3위, 3위 name, 3위 point, 내 등수, 내 name, 내 point]
                                // 순서로 담겨져서 옴

                                // setText를 사용하여 각각의 변수에 값을 넣어줌
                                myscore_nick1.setText(array.getString(1));
                                myscore_point1.setText(array.getString(2));

                                myscore_nick2.setText(array.getString(4));
                                myscore_point2.setText(array.getString(5));

                                myscore_nick3.setText(array.getString(7));
                                myscore_point3.setText(array.getString(8));

                                myscore_rank.setText(array.getString(9));
                                myscore_myrank.setText(array.getString(9));
                                myscore_mynick.setText(array.getString(10));
                                myscore_mypoint.setText(array.getString(11));





                            }catch (Exception e) {
                                e.printStackTrace();
                            } {

                            }






                        }else{
                            Toast.makeText(getApplicationContext(), "Sever Error", Toast.LENGTH_SHORT).show();
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





        myscore_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypagesocre_mypage = new Intent(mypagescore.this, mypage.class);
                startActivity(mypagesocre_mypage);
                finish();
            }
        });

        myscore_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypagesocre_mainpage = new Intent(mypagescore.this, mainpage.class);
                startActivity(mypagesocre_mainpage);
                finish();
            }
        });

    }
}