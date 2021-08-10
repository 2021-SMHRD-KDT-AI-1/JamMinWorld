package com.example.kids0806;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class join extends AppCompatActivity {
    Button join_btn_login;
    EditText join_edt_nick, join_edt_pw;

    RequestQueue requestQueue;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_edt_nick = findViewById(R.id.join_edt_nick);
        join_edt_pw = findViewById(R.id.join_edt_pw);

        join_btn_login = findViewById(R.id.join_btn_login);

        join_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                // 이클립스로 값을 전송할 url
                String url = "http://121.179.170.180:8095/jammin/JoinController";

                request = new StringRequest(Request.Method.POST, url,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {



                                // 여기서 response 는 이클립스에서 out.print()로 보내주는 값
                                // 이클립스에서 값을 반환 해줫을떄 밑에 if 구절이 실행
                                if(response!=null){

                                    //가입성공시 Login 화면으로 넘겨주는 인첸트
                                    Toast.makeText(getApplicationContext(),"회원가입 성공!", Toast.LENGTH_SHORT).show();
                                    Intent join_login = new Intent(join.this, login.class);
                                    startActivity(join_login);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "회원가입 실패!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
                        // 왔ㅅ브ㅜ니더 다시 봐봅시다!

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        // 데이터를 담기 위해서 Map 객체를 생성한다!
                        // Map은 인터페이스로 스스로 객체를 생성하지 못한다.
                        // 따라서 객체 생성시 HashMap을 통하여 객체를 생성해주어야 한다.

                        // 클래스 -> 객체 생성이 가능하지만, 인터페이스는 객체 생성이 불가능
                        Map<String, String> params = new HashMap<>();

                        // 넘겨줄 데이터 params 에 담아주기
                        params.put("join_name", join_edt_nick.getText().toString());
                        params.put("join_pw", join_edt_pw.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });


    } // oncreate
}// class