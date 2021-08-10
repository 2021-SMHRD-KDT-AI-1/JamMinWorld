package com.example.kids0806;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static android.speech.tts.TextToSpeech.ERROR;

public class timerrank extends AppCompatActivity {
    public static final int REQUEST_PERMISSION = 11;
    public long mLastClickTime = 0;
    public TextToSpeech tts;

    TextView rank_time;
    TextView rank_text;

    ImageView rank_img1;
    ImageView rank_img2;
    ImageView rank_img3;
    ImageView rank_img4;

    int ran_num[] = new int[4];
    String[] filename1;
    File[] ran_img;
    String[] ran_img2;

    int check;

    int b;
    String answer;

    ConstraintLayout layout;

    Random rand = new Random();
    String[] btn_img_answer;
    int cnt = 0;

    RequestQueue requestQueue;
    StringRequest request2, request3;


    String globalName;

    //    String answer;
    int answer_num;

    //    String[] btn_img_answer;
    int a;
    int point = 0;  // 맞춘 문제 카운트 변수 이게 현재점수!!

    public void checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //권한이 없으면 권한 요청
        if (permissionCamera != PackageManager.PERMISSION_GRANTED
                || permissionRead != PackageManager.PERMISSION_GRANTED
                || permissionWrite != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "이 앱을 실행하기 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timerrank);
        checkPermission();
        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        // globalName 은 전역변수처럼 쓸수 있음
        globalName = pref.getString("globalName", "");

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.ENGLISH);
                } else {
                    Log.d("speech", "error");
                }
            }
        });

        // 뒤로가기 버튼(경쟁모드 -> 타이머게임 선택모드)
        Button rank_back = (Button) findViewById(R.id.rank_back);
        rank_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rank_timer = new Intent(timerrank.this, timergame.class);
                startActivity(rank_timer);
                finish();
            }
        });

        // 홈버튼(경쟁모드 -> 메인페이지)
        Button rank_out = (Button) findViewById(R.id.rank_out);
        rank_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rank_mainpage = new Intent(timerrank.this, mainpage.class);
                startActivity(rank_mainpage);
                finish();
            }
        });

        rank_time = findViewById(R.id.rank_time);

        Thread rankthread = new RankThread();
        rankthread.start();

        initView_timer();
        Quiz_timer();
        answercheck_timer();


    }


    private void initView_timer() {
        rank_text = findViewById(R.id.rank_text);

        rank_img1 = findViewById(R.id.rank_img1);
        rank_img2 = findViewById(R.id.rank_img2);
        rank_img3 = findViewById(R.id.rank_img3);
        rank_img4 = findViewById(R.id.rank_img4);

    }

    private void Quiz_timer() {
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture");
        filename1 = storageDir.list();
        ran_img = storageDir.listFiles();
        Log.d("file", String.valueOf(storageDir));
        int ran_num[] = new int[4];

        for (int i = 0; i <= 3; i++) {
            ran_num[i] = rand.nextInt(filename1.length);
            for (int j = 0; j < i; j++) {
                if (ran_num[i] == ran_num[j]) {
                    i--;
                }
            }
        }

        int r_num[] = new int[]{0, 1, 2, 3};
        ran_img2 = new String[filename1.length];
        for (int i = 0; i < filename1.length; i++) {
            ran_img2[i] = String.valueOf(filename1[i].substring(0, filename1[i].length() - 4));
        }
        int answer_num;
        //텍스트가져오기
        answer_num = ran_num[rand.nextInt(ran_num.length)];
        answer = ran_img2[answer_num];
        rank_text.setText(answer);


        int cnt = 0;
        ImageView[] btn_img = {rank_img1, rank_img2, rank_img3, rank_img4};
        for (int n = 0; n < r_num.length; n++) {
            Drawable daaa;
            String extDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture/";
            daaa = Drawable.createFromPath(extDir + ran_img2[ran_num[n]] + ".jpg");
            Log.d("daaa", String.valueOf(daaa));
            btn_img[cnt].setBackgroundDrawable(daaa);
            cnt += 1;
            if (cnt == 4) {
                break;
            }
        }


        btn_img_answer = new String[4];
        for (int i = 0; i < ran_num.length; i++) {
            btn_img_answer[i] = ran_img2[ran_num[i]];
        }


    }

    private void answercheck_timer() {

        rank_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_img_answer[0].equals(answer)) {
                    point += 1;
                    Log.d("gkgk", String.valueOf(point));
                    initView_timer();
                    Quiz_timer();
                } else {

                    rank_img1.setClickable(false);
                    rank_img2.setClickable(false);
                    rank_img3.setClickable(false);
                    rank_img4.setClickable(false);
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_img_answer[0], TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), btn_img_answer[0], Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rank_img1.setClickable(true);
                            rank_img2.setClickable(true);
                            rank_img3.setClickable(true);
                            rank_img4.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });
        rank_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_img_answer[1].equals(answer)) {
                    point += 1;
                    initView_timer();
                    Quiz_timer();
                } else {
                    rank_img1.setClickable(false);
                    rank_img2.setClickable(false);
                    rank_img3.setClickable(false);
                    rank_img4.setClickable(false);
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_img_answer[1], TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), btn_img_answer[1], Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rank_img1.setClickable(true);
                            rank_img2.setClickable(true);
                            rank_img3.setClickable(true);
                            rank_img4.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });
        rank_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_img_answer[2].equals(answer)) {
                    point += 1;
                    initView_timer();
                    Quiz_timer();
                } else {
                    rank_img1.setClickable(false);
                    rank_img2.setClickable(false);
                    rank_img3.setClickable(false);
                    rank_img4.setClickable(false);
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_img_answer[2], TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), btn_img_answer[2], Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rank_img1.setClickable(true);
                            rank_img2.setClickable(true);
                            rank_img3.setClickable(true);
                            rank_img4.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });
        rank_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_img_answer[3].equals(answer)) {
                    point += 1;
                    initView_timer();
                    Quiz_timer();
                } else {
                    rank_img1.setClickable(false);
                    rank_img2.setClickable(false);
                    rank_img3.setClickable(false);
                    rank_img4.setClickable(false);
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_img_answer[3], TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), btn_img_answer[3], Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rank_img1.setClickable(true);
                            rank_img2.setClickable(true);
                            rank_img3.setClickable(true);
                            rank_img4.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });

    }

    Handler rankhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int num = msg.arg1;
            rank_time.setText(String.valueOf(num));

            if (num == 0) {
                rank_img1.setClickable(false);
                rank_img2.setClickable(false);
                rank_img3.setClickable(false);
                rank_img4.setClickable(false);




                SharedPreferences point1 = getSharedPreferences("point", MODE_PRIVATE);
                SharedPreferences.Editor point_editor = point1.edit();
                point_editor.putInt("point", point);
                point_editor.commit();


                requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url1 = "http://121.179.170.180:8095/jammin/UadateController";
                // 전송 방법, url, 응답 성공시, 응답 실패시 - > request 작성
                StringRequest request = new StringRequest(Request.Method.POST, url1,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {



                                if(response!=null){
                                    Toast.makeText(getApplicationContext(), "갱신성공..", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(getApplicationContext(), "넌뜨지마..", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), "갱신실패....", Toast.LENGTH_SHORT).show();

                    }
                }

                ){


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();

                        // 넘겨줄 데이터 params 에 담아주기
                        params.put("edt_up_score", Integer.toString(point));
                        params.put("edt_up_name", globalName);




                        return params;
                    }
                };
                requestQueue.add(request);





                Intent intent = new Intent(getApplicationContext(), timerscore.class);
//                intent.putExtra("point",point);

                startActivity(intent);
                finish();


            }

        }
    };


    class RankThread extends Thread {
        @Override
        public void run() {
            try {
                for (a = 30; a >= 0; a--) {
                    Thread.sleep(1000);

                    Message message = new Message();
                    message.arg1 = a;
                    rankhandler.sendMessage(message);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                // 권한이 취소되면 result 배열은 비어있다.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "권한 확인", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
                    finish(); //권한이 없으면 앱 종료
                }
            }
        }
    }

}
