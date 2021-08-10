package com.example.kids0806;

import android.Manifest;
import android.content.Intent;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static android.speech.tts.TextToSpeech.ERROR;

public class timerpractice extends AppCompatActivity {
    public static final int REQUEST_PERMISSION = 11;

    // 음성
    public TextToSpeech tts;
    TextView textView20;
    TextView textView19;
    TextView practice_time;
    TextView practice_text;
    ImageView practice_img1;
    ImageView practice_img2;
    ImageView practice_img3;
    ImageView practice_img4;

    String[] filename1;
    File[] ran_img;
    String[] ran_img2;
    int b;
    String answer;
    int[] ran_num;

    ConstraintLayout layout;

    ArrayList<String> note_words = new ArrayList<String>();

    Random rand = new Random();
    String[] btn_img_answer;
    int cnt = 0;


    //권한부여
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
        setContentView(R.layout.activity_timerpractice);
        checkPermission();
//음성
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

        // 뒤로가기 버튼(연습모드 -> 타이머게임 선택화면)
        Button practice_back = (Button) findViewById(R.id.practice_back);
        practice_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent practice_timer = new Intent(timerpractice.this, timergame.class);
                startActivity(practice_timer);
                finish();
            }
        });

        // 홈버튼(연습모드 -> 메인페이지)
        Button practice_out = (Button) findViewById(R.id.practice_out);
        practice_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent practice_mainpage = new Intent(timerpractice.this, com.example.kids0806.mainpage.class);
                startActivity(practice_mainpage);
                finish();
            }
        });

        practice_time = findViewById(R.id.practice_time);



        Thread tvthread = new TvThread();
        tvthread.start();

        initView_timer();
        layout.setVisibility(View.INVISIBLE);
        Quiz_timer();

        answercheck_timer();

    }


    private void initView_timer() {
        practice_text = findViewById(R.id.practice_text);
        practice_time = findViewById(R.id.practice_time);
        textView20 = findViewById(R.id.textView20);
        textView19 = findViewById(R.id.textView19);
        practice_img1 = findViewById(R.id.practice_img1);
        practice_img2 = findViewById(R.id.practice_img2);
        practice_img3 = findViewById(R.id.practice_img3);
        practice_img4 = findViewById(R.id.practice_img4);

        layout = findViewById(R.id.layout);

        Button btn_back = findViewById(R.id.btn_back1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), timerpractice.class);
                startActivity(intent);
            }
        });



        TextView point = findViewById(R.id.point_1);
        point.setText(String.valueOf(cnt));


    }

    private void Quiz_timer() {
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture");
        filename1 = storageDir.list();
        ran_img = storageDir.listFiles();

        int ran_num[] = new int[4];

        for (int i = 0; i <= 3; i++) {
            ran_num[i] = rand.nextInt(filename1.length);
            for (int j = 0; j < i; j++) {
                if (ran_num[i] == ran_num[j]) {
                    i--;
                }
            }
        }

        Intent intent = new Intent(getApplicationContext(),fragment_note_timer.class);
        intent.putExtra("ran_num",ran_num);

        ran_img2 = new String[filename1.length];
        for(int i =0;i<filename1.length;i++){
            ran_img2[i] = (filename1[i].substring(0,filename1[i].length()-4));
        }
        int answer_num;
        //텍스트가져오기
        answer_num = ran_num[rand.nextInt(ran_num.length)];
        answer = ran_img2[answer_num];
        practice_text.setText(answer);


        int cnt = 0;
        ImageView[] btn_img = {practice_img1, practice_img2, practice_img3, practice_img4};
        Drawable daaa;
        for (int n=0; n<btn_img.length;n++){

            String extDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture/";
            daaa  = Drawable.createFromPath(extDir+ran_img2[ran_num[n]]+".jpg");
            btn_img[cnt].setBackgroundDrawable(daaa);
            cnt+=1;
            if(cnt==4){
                break;
            }
        }


        btn_img_answer = new String[4];
        for (int i = 0; i < ran_num.length; i++) {
            btn_img_answer[i] = ran_img2[ran_num[i]];
        }


    }

    private void answercheck_timer() {
        practice_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_img_answer[0].equals(answer)) {
                    cnt += 1;
                    initView_timer();
                    Quiz_timer();
                } else {
                    practice_img1.setClickable(false);
                    practice_img2.setClickable(false);
                    practice_img3.setClickable(false);
                    practice_img4.setClickable(false);
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_img_answer[0],TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), btn_img_answer[0], Toast.LENGTH_SHORT).show();
                    if(note_words.isEmpty()){
                        note_words.add(btn_img_answer[0]);
                    }
                    if (!note_words.contains(btn_img_answer[0])) {
                        note_words.add(btn_img_answer[0]);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            practice_img1.setClickable(true);
                            practice_img2.setClickable(true);
                            practice_img3.setClickable(true);
                            practice_img4.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });

        practice_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_img_answer[1].equals(answer)) {
                    cnt += 1;
                    initView_timer();
                    Quiz_timer();
                } else {
                    practice_img1.setClickable(false);
                    practice_img2.setClickable(false);
                    practice_img3.setClickable(false);
                    practice_img4.setClickable(false);
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_img_answer[1],TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), btn_img_answer[1], Toast.LENGTH_SHORT).show();
                    if(note_words.isEmpty()){
                        note_words.add(btn_img_answer[1]);
                    }
                    if (!note_words.contains(btn_img_answer[1])) {
                        note_words.add(btn_img_answer[1]);
                    }
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            practice_img1.setClickable(true);
                            practice_img2.setClickable(true);
                            practice_img3.setClickable(true);
                            practice_img4.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });
        practice_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_img_answer[2].equals(answer)) {
                    cnt += 1;
                    initView_timer();
                    Quiz_timer();
                } else {
                    practice_img1.setClickable(false);
                    practice_img2.setClickable(false);
                    practice_img3.setClickable(false);
                    practice_img4.setClickable(false);
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_img_answer[2],TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), btn_img_answer[2], Toast.LENGTH_SHORT).show();
                    if(note_words.isEmpty()){
                        note_words.add(btn_img_answer[2]);
                    }
                    if (!note_words.contains(btn_img_answer[2])) {
                        note_words.add(btn_img_answer[2]);
                    }
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            practice_img1.setClickable(true);
                            practice_img2.setClickable(true);
                            practice_img3.setClickable(true);
                            practice_img4.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });
        practice_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_img_answer[3].equals(answer)) {
                    cnt += 1;
                    initView_timer();
                    Quiz_timer();
                } else {
                    practice_img1.setClickable(false);
                    practice_img2.setClickable(false);
                    practice_img3.setClickable(false);
                    practice_img4.setClickable(false);
                    tts.setPitch(1.0f);         // 음성 톤을 기본 설정
                    tts.setSpeechRate(0.8f);
                    tts.speak(btn_img_answer[3],TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(), btn_img_answer[3], Toast.LENGTH_SHORT).show();
                    if(note_words.isEmpty()){
                        note_words.add(btn_img_answer[3]);
                    }
                    if (!note_words.contains(btn_img_answer[3])) {
                        note_words.add(btn_img_answer[3]);
                    }
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            practice_img1.setClickable(true);
                            practice_img2.setClickable(true);
                            practice_img3.setClickable(true);
                            practice_img4.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });
        Button btn_note = findViewById(R.id.btn_note);
        btn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),fragment_note_timer.class);
                intent.putExtra("words",note_words);
                startActivity(intent);
            }
        });
    }

    Handler tvhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int num = msg.arg1;
            practice_time.setText(String.valueOf(num));

            if (num == 0) {
                layout.setVisibility(View.VISIBLE);
                practice_img1.setClickable(false);
                practice_img2.setClickable(false);
                practice_img3.setClickable(false);
                practice_img4.setClickable(false);

                practice_img1.setVisibility(View.INVISIBLE);
                practice_img2.setVisibility(View.INVISIBLE);
                practice_img3.setVisibility(View.INVISIBLE);
                practice_img4.setVisibility(View.INVISIBLE);
                textView20.setVisibility(View.INVISIBLE);
                textView19.setVisibility(View.INVISIBLE);
                practice_text.setVisibility(View.INVISIBLE);
                practice_time.setVisibility(View.INVISIBLE);


            }

        }
    };

    class TvThread extends Thread {
        @Override
        public void run() {
            try {
                for (b = 30; b >= 0; b--) {
                    Thread.sleep(1000);

                    Message message = new Message();
                    message.arg1 = b;
                    tvhandler.sendMessage(message);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}
