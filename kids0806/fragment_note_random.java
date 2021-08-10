package com.example.kids0806;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.kids0806.R;
import com.example.kids0806.noteRandomAdapter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class fragment_note_random extends AppCompatActivity {

  String[] filename1;
  Drawable[] daa;
  String ran_img2[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note_random);

        ListView note_ran_listview = findViewById(R.id.note_ran_listview);

        Intent intent = getIntent();
        Serializable s = intent.getSerializableExtra("words");



        ArrayList<String> words = (ArrayList<String>) s;
        ArrayList<Drawable> imgs = new ArrayList<>();


        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture");
        filename1 = storageDir.list();
        daa = new Drawable[filename1.length];
        String extDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture/";
      ran_img2 = new String[filename1.length];
       for (int i = 0; i < filename1.length; i++) {
            ran_img2[i] = (filename1[i].substring(0, filename1[i].length() - 4));
        }
        for (int i = 0; i < filename1.length; i++) {
            daa[i] = Drawable.createFromPath(extDir + ran_img2[i] + ".jpg");
        }




        for (int i = 0; i < words.size(); i++) {
            for (int j = 0; j < filename1.length; j++) {
                if (ran_img2[j].equals(words.get(i))) {
                    imgs.add(Drawable.createFromPath(extDir + ran_img2[j] + ".jpg"));


                }
            }


        }
        // 뒤로가기 버튼(연습모드 ->  다시하기 선택화면)
        Button random_note_back = (Button) findViewById(R.id.random_note_back);
        random_note_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomquiz_1 = new Intent(fragment_note_random.this, randomquiz.class);
                startActivity(randomquiz_1);
                finish();
            }
        });

        // 홈버튼(랜덤게임 -> 메인페이지)
        Button random_note_out =(Button)findViewById(R.id.random_note_out);
        random_note_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomquiz_mainpage = new Intent(fragment_note_random.this, mainpage.class);
                startActivity(randomquiz_mainpage);
                finish();
            }
        });



        // 연결을 도와줄 수 있는 Adapter 불러오기!
        noteRandomAdapter adapter = new noteRandomAdapter();

        for (int i = 0; i < words.size(); i++) {
            adapter.addItem(imgs.get(i), words.get(i));
            Log.d("img", String.valueOf(imgs));
            Log.d("word", String.valueOf(words));

        }

        note_ran_listview.setAdapter(adapter);
  }
}