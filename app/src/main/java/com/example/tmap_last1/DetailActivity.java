package com.example.tmap_last1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    Building selectedBuilding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSelectedBuilding(); //선택한 건물정보 가져오기

        setValues(); // 가져온 정보 화면에 보여주기
    }

    private void setValues() {


    }

    private void getSelectedBuilding() {

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        selectedBuilding = com.example.tmap_last1.search.buildingList.get(Integer.valueOf(id));
    }
}