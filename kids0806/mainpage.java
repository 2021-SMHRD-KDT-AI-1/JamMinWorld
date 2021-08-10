package com.example.kids0806;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Random;

public class mainpage extends AppCompatActivity {
    public static final int REQUEST_CODE = 0;
    public static final int REQUEST_PERMISSION = 11;
    ImageView main_img;
    Bitmap img;
    SharedPreferences pref;
    String globalName;

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
        setContentView(R.layout.activity_mainpage);
        checkPermission();

        pref = getSharedPreferences("mine", MODE_PRIVATE);
        globalName = pref.getString("globalName", "");
        TextView tv_globa = findViewById(R.id.tv_glona);
        tv_globa.setText(globalName);


        main_img =(ImageView)findViewById(R.id.main_img);
        SharedPreferences pref1 = getSharedPreferences("image", MODE_PRIVATE);

        String image2 =  pref1.getString("imagestrings", "");

        Bitmap bitmap = StringToBitMap(image2);
        main_img.setImageBitmap(bitmap);

        main_img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        // 메인게임 이동버튼
        Button main_btn_main =(Button)findViewById(R.id.main_btn_main);
        main_btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_maingame = new Intent(mainpage.this, maingame.class);
                startActivity(main_maingame);
                finish();

            }
        });

        // 타이머게임 이동버튼
        Button main_btn_timer =(Button)findViewById(R.id.main_btn_timer);
        main_btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_timergame = new Intent(mainpage.this, timergame.class);
                startActivity(main_timergame);
                finish();
            }
        });

        // 랜덤게임 이동버튼
        Button main_btn_random =(Button)findViewById(R.id.main_btn_random);
        main_btn_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_randomquiz = new Intent(mainpage.this, com.example.kids0806.randomquiz.class);
                startActivity(main_randomquiz);
                finish();
            }
        });

        // 오답노트 이동버튼
        Button main_btn_note =(Button)findViewById(R.id.main_btn_note);
        main_btn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_note = new Intent(mainpage.this, note.class);
                startActivity(main_note);
                finish();
            }
        });

        // 마이페이지 이동버튼
        Button main_btn_mypage =(Button)findViewById(R.id.main_btn_mypage);
        main_btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_mypage = new Intent(mainpage.this, mypage.class);
                startActivity(main_mypage);
                finish();
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();

                    main_img.setImageBitmap(img);

                    String image1 = BitMapToString(img);
                    SharedPreferences pref = getSharedPreferences("image", MODE_PRIVATE);

                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("imagestrings", image1);



                    editor.commit();
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
    public String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte [] b=baos.toByteArray();

        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        return temp;

    }
    public Bitmap StringToBitMap(String encodedString){

        try{

            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);

            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            return bitmap;

        }catch(Exception e){

            e.getMessage();

            return null;

        }

    }
}