package com.example.kids0806;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    Button login_btn_login, login_btn_join;
    RequestQueue requestQueue;
    Request request1;

    EditText login_edt_nick, login_edt_pw;


    int limit;
    String globalName, auto_name, auto_pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // 변수 지정
        login_btn_login = findViewById(R.id.login_btn_login);
        login_btn_join = findViewById(R.id.login_btn_join);

        login_edt_nick = findViewById(R.id.login_edt_nick);
        login_edt_pw = findViewById(R.id.login_edt_pw);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);


        auto_name = auto.getString("auto_name",null);
        auto_pw = auto.getString("auto_pw",null);

        if(auto_name != null && auto_pw != null){

            login_edt_nick.setText(auto_name);
            login_edt_pw.setText(auto_pw);
        }else{

        }

        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("asdf", login_edt_nick.getText().toString()+" / "+ login_edt_pw.getText().toString());

                //로그인시 아이디를 프로젝트에 담아주는 메소드
                save();

                //해당 URL로 이클립스 서버를 사용하여 오라클에 접속
                String url = "http://121.179.170.180:8095/jammin/LoginController";



                request1 = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //여기서 response는 이클립스에서 받아오는 값을 의미함

                                Log.d("name", response);

                                //현재 Login controller 에는 vo != null 이 아닐때 1을 out.print 로 반환을 하여
                                // 안드로이드로 보내줌
                                if (response.equals("1")) {// 이클립스에서 out.print 로 보내준 1

                                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "점수가 갱신되었습니다!", Toast.LENGTH_SHORT).show();


                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();

                                }
                                else if(response.equals("2")){
                                    Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"갱신까지 6일 남았습니다!", Toast.LENGTH_SHORT).show();
                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();
                                }
                                else if(response.equals("3")){
                                    Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"갱신까지 5일 남았습니다!", Toast.LENGTH_SHORT).show();
                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();
                                }else if(response.equals("4")){
                                    Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"갱신까지 4일 남았습니다!", Toast.LENGTH_SHORT).show();
                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();
                                }else if(response.equals("5")){
                                    Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"갱신까지 3일 남았습니다!", Toast.LENGTH_SHORT).show();
                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();
                                }else if(response.equals("6")){
                                    Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"갱신까지 2일 남았습니다!", Toast.LENGTH_SHORT).show();
                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();
                                }else if(response.equals("7")){
                                    Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"갱신까지 1일 남았습니다!", Toast.LENGTH_SHORT).show();
                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();
                                }else if(response.equals("8")){
                                    Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"갱신까지 7일 남았습니다!", Toast.LENGTH_SHORT).show();


                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();
                                }else if(response.equals("10")){
                                    Toast.makeText(getApplicationContext(),"오늘의 첫 로그인!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"오늘하루도 화이팅!!", Toast.LENGTH_SHORT).show();
                                    SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("limit",0);
                                    editor.commit();



                                    Intent login_Intent = new Intent(getApplicationContext(), mainpage.class);
                                    startActivity(login_Intent);
                                    finish();
                                }


                                else {
                                    // response. 값이 1이 아닐떄 나오는 토스트 메시지
                                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                                }

                            }


                        }, new Response.ErrorListener() {
                    @Override
                    // 얘는 나오면 서버 통신이 안되어 있다는 소리임 임폴트랑 다시 확인해봐야함
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Sever Error", Toast.LENGTH_LONG).show();

                    }
                }
                ){

                    //안드로이드에서 이클립스로 값을 전송해주는 메소드
                    // 이클립스에서는 login_name 이라는 값으로 받아서 db에 접속
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("login_name", login_edt_nick.getText().toString());
                        params.put("login_pw", login_edt_pw.getText().toString());
                        return params;
                    }

                };
                requestQueue.add(request1);
            }
        });


        // 아무런 기능이 없는 회원가입창으로 보내주는 버튼
        login_btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent login_join = new Intent(login.this, join.class);
                startActivity(login_join);
                finish();

            }
        });


    }


    // 로그인시 아이디를 저장해주는 save 메소드 생성
    private  void  save(){
        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("globalName", login_edt_nick.getText().toString().trim());

        editor.commit();

        SharedPreferences auto_login = getSharedPreferences("auto", MODE_PRIVATE);
        SharedPreferences.Editor auto_editor = auto_login.edit();
        auto_editor.putString("auto_name", login_edt_nick.getText().toString().trim());
        auto_editor.putString("auto_pw", login_edt_pw.getText().toString().trim());
        auto_editor.commit();


    }
}

