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


public class note_timer extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_note_timer, container, false);

        // 오답노트(타이머게임) 뒤로가기 버튼
        Button timer_note_back = fragment.findViewById(R.id.timer_note_back);
        timer_note_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timer_mainPage_back = new Intent(getActivity(), com.example.kids0806.mainpage.class);
                startActivity(timer_mainPage_back);
            }
        });

        // 오답노트(타이머게임) 홈버튼
        Button timer_note_out = fragment.findViewById(R.id.timer_note_out);
        timer_note_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timer_mainPage_out = new Intent(getActivity(), com.example.kids0806.mainpage.class);
                startActivity(timer_mainPage_out);
            }
        });

        // 만들어진 View를 뿌려줄 수 있는 ListView 찾아오기!
        ListView note_timerListView = fragment.findViewById(R.id.note_ran_listview);

        // 연결을 도와줄 수 있는 Adapter 불러오기!
        noteAdapter adapter = new noteAdapter();

        // 가져온 이미지와 텍스트 넣는 부분
        adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.house), "HOUSE");
        adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.house), "HOUSE");
        adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.house), "HOUSE");
        adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.house), "HOUSE");
        adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.house), "HOUSE");
        adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.house), "HOUSE");

        note_timerListView.setAdapter(adapter);

        return fragment;

    }
}