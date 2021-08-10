package com.example.kids0806;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class mypagealbum extends Activity {
    public static final int REQUEST_PERMISSION = 11;
    String[] filename1;
    File[] ran_img;
    String[] ran_img2;

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
    // -------------------------------------------
    // imageIDs 배열은 GridView 뷰를 구성하는 이미지 파일들의 리소스 ID들을 담습니다.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypagealbum);
        checkPermission();
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture");
        filename1 = storageDir.list();
        ran_img = storageDir.listFiles();
        ran_img2 = new String[filename1.length];
        for (int i = 0; i < filename1.length; i++) {
            ran_img2[i] = filename1[i].substring(0, filename1[i].length() - 4);
        }

        int[] imageIDs = new int[filename1.length];

        Drawable[] daaa = new Drawable[filename1.length];
        for(int i =0;i<filename1.length;i++) {
            String extDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture/";
            daaa[i] =  Drawable.createFromPath(extDir + ran_img2[i] + ".jpg");
        }
        // 뒤로가기 버튼(앨범 -> 마이페이지)
        Button album_back = (Button) findViewById(R.id.album_back);
        album_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypagealbum_mypage = new Intent(mypagealbum.this, com.example.kids0806.mypage.class);
                startActivity(mypagealbum_mypage);
                finish();
            }
        });

        // 홈버튼(앨범 -> 메인페이지)
        Button album_out = (Button) findViewById(R.id.album_out);
        album_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypagealbum_mainpage = new Intent(mypagealbum.this, com.example.kids0806.mainpage.class);
                startActivity(mypagealbum_mainpage);
                finish();
            }
        });

        // ---------------------------------------------
        // 사진들을 보여줄 GridView 뷰의 어댑터 객체를 정의하고 그것을 이 뷰의 어댑터로 설정합니다.


        GridView gridViewImages = findViewById(R.id.gridViewImages);

        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getApplicationContext(), imageIDs);

        for(int i = 0; i<daaa.length; i++){
            imageGridAdapter.addview(daaa[i]);
        }



        gridViewImages.setAdapter(imageGridAdapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
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