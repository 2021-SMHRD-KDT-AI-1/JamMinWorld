package com.example.kids0806;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import static android.speech.tts.TextToSpeech.ERROR;


public class randomquiz extends AppCompatActivity {

    public static final int REQUEST_PERMISSION = 11;

    ArrayList<String> note_words = new ArrayList<String>();
    public TextToSpeech tts;
    File[] ran_img;
    Uri uri ;
    String[] ran_img2;


    ConstraintLayout layout;
    int ran_img_int;
    Random rand;
    String ran_answer[] = new String[4];
    TextView random_page;
    int cnt = 1;
    Button random_btn1;
    Button random_btn2;
    Button random_btn3;
    Button random_btn4;
    Button[] random_btn = new Button[]{random_btn1,random_btn2,random_btn3,random_btn4};
    String[] filename1;
    String btn_answer;
    ImageView random_img;
    TextView cnt_num;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomquiz);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.ENGLISH);
                }
                else{
                    Log.d("speech","error");
                }
            }
        });

        checkPermission();

        initView();
        layout.setVisibility(View.INVISIBLE);
        Quiz();
        answercheck();


        // 뒤로가기 버튼(연습모드 ->  다시하기 선택화면)
        Button btn_back1 = (Button) findViewById(R.id.btn_back1);
        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomquiz_1 = new Intent(randomquiz.this, randomquiz.class);
                startActivity(randomquiz_1);
                finish();
            }
        });

        // 홈버튼(랜덤게임 -> 메인페이지)
        Button random_out =(Button)findViewById(R.id.random_out);
        random_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomquiz_mainpage = new Intent(randomquiz.this, mainpage.class);
                startActivity(randomquiz_mainpage);
                finish();
            }
        });

    }



    private void initView() {
        random_img = findViewById(R.id.random_img);
        random_page = findViewById(R.id.random_page);

        random_btn1 = findViewById(R.id.random_btn1);
        random_btn2 = findViewById(R.id.random_btn2);
        random_btn3 = findViewById(R.id.random_btn3);
        random_btn4 = findViewById(R.id.random_btn4);


        rand = new Random();

        cnt_num =  findViewById(R.id.cnt_num);
        cnt_num.setText(String.valueOf(cnt));

        layout = findViewById(R.id.layout);

        // 외부폴더에서 파일이름 가져오기




    }

    private void Quiz() {
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture/");
        filename1 = storageDir.list();
        ran_img = storageDir.listFiles();

        int[] ran_img_int = new int[4];
        for (int i = 0; i < 4; i++) {
            ran_img_int[i] = rand.nextInt(ran_img.length);
            for (int j = 0; j < i; j++) {
                if (ran_img_int[i] == ran_img_int[j]) {
                    i--;
                }

            }
        }

        ran_img2 = new String[filename1.length];

        for(int i =0;i<filename1.length;i++){
            ran_img2[i] = (filename1[i].substring(0,filename1[i].length()-4));
        }

        for (int i = 0; i < 4; i++) {
            ran_answer[i] = ran_img2[ran_img_int[i]];
        }
        random_btn1.setText(ran_answer[0]);
        random_btn2.setText(ran_answer[1]);
        random_btn3.setText(ran_answer[2]);
        random_btn4.setText(ran_answer[3]);

        int r_num[] = new int[]{0, 1, 2, 3};
        int answer_num;
        answer_num = ran_img_int[rand.nextInt(r_num.length)];


        btn_answer = ran_img2[answer_num];

        ImageView random_img = findViewById(R.id.random_img);
        Drawable daaa;
        String extDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture/";
        daaa = Drawable.createFromPath(extDir+btn_answer+".jpg");
        Log.d("dd", String.valueOf(daaa));

        random_img.setBackgroundDrawable(daaa);



    }






    private void answercheck() {
        random_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random_btn1.getText().equals(btn_answer)) {
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_answer,TextToSpeech.QUEUE_FLUSH, null);
                    cnt+=1;
                    if (cnt > 10){
                        random_btn1.setClickable(false);
                        random_btn2.setClickable(false);
                        random_btn3.setClickable(false);
                        random_btn4.setClickable(false);
                        random_btn1.setVisibility(View.INVISIBLE);
                        random_btn2.setVisibility(View.INVISIBLE);
                        random_btn3.setVisibility(View.INVISIBLE);
                        random_btn4.setVisibility(View.INVISIBLE);
                        random_img.setVisibility(View.INVISIBLE);
                        random_page.setVisibility(View.INVISIBLE);
                        cnt_num.setVisibility(View.INVISIBLE);

                        layout.setVisibility(View.VISIBLE);
                        cnt = 1;

                    }
                    initView();
                    Quiz();

                } else {
                    Toast.makeText(getApplicationContext(), ran_answer[0], Toast.LENGTH_SHORT).show();
                    if(note_words.isEmpty()){
                        note_words.add(ran_answer[0]);
                    }
                    if (!note_words.contains(ran_answer[0])) {
                        note_words.add(ran_answer[0]);
                    }
                }
            }

        });
        random_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random_btn2.getText().equals(btn_answer)) {
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_answer,TextToSpeech.QUEUE_FLUSH, null);
                    cnt+=1;
                    if (cnt > 10) {
                        random_btn1.setClickable(false);
                        random_btn2.setClickable(false);
                        random_btn3.setClickable(false);
                        random_btn4.setClickable(false);
                        random_btn1.setVisibility(View.INVISIBLE);
                        random_btn2.setVisibility(View.INVISIBLE);
                        random_btn3.setVisibility(View.INVISIBLE);
                        random_btn4.setVisibility(View.INVISIBLE);
                        random_img.setVisibility(View.INVISIBLE);
                        random_page.setVisibility(View.INVISIBLE);
                        cnt_num.setVisibility(View.INVISIBLE);

                        layout.setVisibility(View.VISIBLE);
                        cnt = 1;
                    }
                    initView();
                    Quiz();
                } else {
                    Toast.makeText(getApplicationContext(), ran_answer[1], Toast.LENGTH_SHORT).show();
                    if(note_words.isEmpty()){
                        note_words.add(ran_answer[1]);
                    }
                    if (!note_words.contains(ran_answer[1])) {
                        note_words.add(ran_answer[1]);
                    }
                }
            }
        });
        random_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random_btn3.getText().equals(btn_answer)) {
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_answer,TextToSpeech.QUEUE_FLUSH, null);
                    cnt+=1;
                    if (cnt > 10){
                        random_btn1.setClickable(false);
                        random_btn2.setClickable(false);
                        random_btn3.setClickable(false);
                        random_btn4.setClickable(false);
                        random_btn1.setVisibility(View.INVISIBLE);
                        random_btn2.setVisibility(View.INVISIBLE);
                        random_btn3.setVisibility(View.INVISIBLE);
                        random_btn4.setVisibility(View.INVISIBLE);
                        random_img.setVisibility(View.INVISIBLE);
                        random_page.setVisibility(View.INVISIBLE);
                        cnt_num.setVisibility(View.INVISIBLE);

                        layout.setVisibility(View.VISIBLE);
                        cnt = 1;
                    }
                    initView();
                    Quiz();
                } else {
                    Toast.makeText(getApplicationContext(), ran_answer[2], Toast.LENGTH_SHORT).show();
                    if(note_words.isEmpty()){
                        note_words.add(ran_answer[2]);
                    }
                    if (!note_words.contains(ran_answer[2])) {
                        note_words.add(ran_answer[2]);
                    }
                }

            }
        });
        random_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random_btn4.getText().equals(btn_answer)) {
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_answer,TextToSpeech.QUEUE_FLUSH, null);
                    cnt+=1;
                    if (cnt > 10){
                        random_btn1.setClickable(false);
                        random_btn2.setClickable(false);
                        random_btn3.setClickable(false);
                        random_btn4.setClickable(false);
                        random_btn1.setVisibility(View.INVISIBLE);
                        random_btn2.setVisibility(View.INVISIBLE);
                        random_btn3.setVisibility(View.INVISIBLE);
                        random_btn4.setVisibility(View.INVISIBLE);
                        random_img.setVisibility(View.INVISIBLE);
                        random_page.setVisibility(View.INVISIBLE);
                        cnt_num.setVisibility(View.INVISIBLE);

                        layout.setVisibility(View.VISIBLE);
                        cnt = 1;
                    }
                    initView();
                    Quiz();
                } else {
                    Toast.makeText(getApplicationContext(), ran_answer[3], Toast.LENGTH_SHORT).show();
                    if(note_words.isEmpty()){
                        note_words.add(ran_answer[3]);
                    }
                    if (!note_words.contains(ran_answer[3])) {
                        note_words.add(ran_answer[3]);
                    }
                }

            }

        });

        Button btn_note = findViewById(R.id.btn_note);
        btn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),fragment_note_random.class);
                intent.putExtra("words",note_words);
                startActivity(intent);
            }
        });

    }

}