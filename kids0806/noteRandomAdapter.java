package com.example.kids0806;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// 나만의 Adapter를 만들기 위한 클래스!
// 일반 클래스를 Adapter로 만들기 위해서는 기존의 Adapter를 상속받아야 한다!

public class noteRandomAdapter extends BaseAdapter {

    // 여러개의 데이터를 담아줄 수 있는 ArrayList
    private ArrayList<listVO> note_ran_list = new ArrayList<>();

    // 데이터의 갯수를 반환해 줄 수 있는 메소드
    @Override
    public int getCount() {
        return note_ran_list.size();
    }

    @Override
    public Object getItem(int position) {
        return note_ran_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 제일 중요한 메소드!!!!!
    // getView() : 화면에 만들어진 note.xml을 뿌려주는 역할
    // Activity에서 setAdapter()를 사용할 때 호출된다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context1 = parent.getContext();

        if (convertView == null){
            // 내가 만들어 둔 Layout 가져오기!
            // 1. 디자인 가져올때 xml 파일을 View 타입으로 변환해주기! -> LayoutInflater
            LayoutInflater inflater1 = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. convertView에 inflater 담아주기
            convertView = inflater1.inflate(R.layout.note, parent, false);
        }

        // 각각의 View를 찾기 위한 작업 진행
        ImageView note_img1 = convertView.findViewById(R.id.note_img);
        TextView note_answer1 = convertView.findViewById(R.id.note_answer);

        listVO list = note_ran_list.get(position);
        note_img1.setImageDrawable(list.getNote_img());
        note_answer1.setText(list.getNote_answer());

        return convertView;
    } // getView() 마무리

    // 원하는 데이터들을 추가 할 수 있는 메소드 생성하기 -> 생성자 메소드
    public void addItem(Drawable icon, String text){
        listVO listViewItem1 = new listVO();

        listViewItem1.setNote_img(icon);
        listViewItem1.setNote_answer(text);

        note_ran_list.add(listViewItem1);
    }

} // class 마무리
