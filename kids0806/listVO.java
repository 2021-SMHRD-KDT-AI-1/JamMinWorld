package com.example.kids0806;

import android.graphics.drawable.Drawable;

public class listVO {

    // note.xml 틀에 들어갈 데이터들을 한번에 정리해 두는 클래스 생성!

    // 내가 원하는 디자인에 들어가야하는 데이터들을 정리!
    // 이미지를 경로를 통해 저장할 수 있도록 Drawable 타입으로 정의
    private Drawable note_img;
    private String note_answer;
    private Drawable gridview;


    // getter / setter 메소드 만들기
    public Drawable getNote_img() {
        return note_img;
    }
    public void setNote_img(Drawable note_img) {
        this.note_img = note_img;
    }
    public String getNote_answer() {return note_answer; }
    public void setNote_answer(String note_answer) {this.note_answer = note_answer; }
    public Drawable getGridview() {return gridview; }
    public void setGridview(Drawable gridview) {this.gridview = gridview; }
}
