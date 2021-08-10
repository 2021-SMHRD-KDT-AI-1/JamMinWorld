package com.example.kids0806;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class note_random extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment1 = inflater.inflate(R.layout.fragment_note_random, container, false);

        // 오답노트(랜덤게임) 뒤로가기 버튼
        Button ran_note_back = fragment1.findViewById(R.id.random_note_back);
        ran_note_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rna_mainPage_back = new Intent(getActivity(), mainpage.class);
                startActivity(rna_mainPage_back);
            }
        });

        // 오답노트(랜덤게임) 홈버튼
        Button ran_note_out = fragment1.findViewById(R.id.random_note_out);
        ran_note_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ran_mainPage_out = new Intent(getActivity(), mainpage.class);
                startActivity(ran_mainPage_out);
            }
        });

        // 만들어진 View를 뿌려줄 수 있는 ListView 찾아오기!
        ListView note_ranListView = fragment1.findViewById(R.id.note_ran_listview);


        // 연결을 도와줄 수 있는 Adapter 불러오기!
        noteRandomAdapter adapter1 = new noteRandomAdapter();

        // 가져온 이미지와 텍스트 넣는 부분
        adapter1.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.arrow), "ARROW");
        adapter1.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.arrow), "ARROW");
        adapter1.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.arrow), "ARROW");
        adapter1.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.arrow), "ARROW");

        note_ranListView.setAdapter(adapter1);

        return fragment1;
    }
}