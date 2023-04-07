package com.example.tmap_last1;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class search extends AppCompatActivity {

    public static ArrayList<Building> buildingList = new ArrayList<Building>();
    public double slat;double slon;double dlat;double dlon;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchBuilding();

        setUpData(); //데이터 셋팅

        setUpList(); //리스트 셋팅
        //start_setUpOnClickListener();// 출발지 클릭시 이벤트
        //end_setUpOnClickListener();// 목적지 클릭시 이벤트

        //버튼 클릭시 리스트 초기화
        Button Button = (android.widget.Button)findViewById(R.id.button2) ;
        Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                // 리스트 초기화.
                buildingList.clear();
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                myIntent.putExtra("시작위도", slat);
                myIntent.putExtra("시작경도", slon);
                myIntent.putExtra("도착위도", dlat);
                myIntent.putExtra("도착경도", dlon);
                Log.d("debug",String.valueOf(slat)+String.valueOf(slon));
                startActivity(myIntent);
            }
        }) ;

    }

    private void searchBuilding(){
    //출발지 검색이벤트
        SearchView start_searchView = findViewById(R.id.start_search_view);
        start_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                start_setUpOnClickListener();// 출발지 클릭시 이벤트
                // 검색 버튼 누를 때 호출
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               /*
                // 검색창에서 글자가 변경이 일어날 때마다 호출
                ArrayList<Animal> filterAnimal = new ArrayList<>();

                for(int i = 0; i < animalList.size(); i++){

                    Animal animal = animalList.get(i);

                    //데이터와 비교해서 내가 쓴 동물 이름이 있다면
                    if(animal.getName().toLowerCase().contains(newText.toLowerCase())){

                        filterAnimal.add(animal);
                    }
                }

                AnimalAdapter start_adapter = new AnimalAdapter(getApplicationContext(), 0, filterAnimal);
                listView.setAdapter(start_adapter);
*/
                return false;
            }
        });

    //목적지 검색이벤트
        SearchView end_searchView = findViewById(R.id.end_search_view);
        end_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                end_setUpOnClickListener();// 검색 버튼 누를 때 호출
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
  /*
                // 검색창에서 글자가 변경이 일어날 때마다 호출
                ArrayList<Animal> filterAnimal = new ArrayList<>();

                for(int i = 0; i < animalList.size(); i++){

                    Animal animal = animalList.get(i);

                    //데이터와 비교해서 내가 쓴 동물 이름이 있다면
                    if(animal.getName().toLowerCase().contains(newText.toLowerCase())){

                        filterAnimal.add(animal);
                    }
                }

                AnimalAdapter end_adapter = new AnimalAdapter(getApplicationContext(), 0, filterAnimal);
                listView.setAdapter(end_adapter);
*/
                return false;
            }
        });


    }

    private void setUpData() {

        Building jinligwan = new Building("0", "진리관",37.30079158,127.0339679);
        buildingList.add(jinligwan);

        Building seongsingwan = new Building("1", "성신관",37.29962504,127.033633661461);
        buildingList.add(seongsingwan);

        Building aegyeonggwan = new Building("2", "애경관",37.2994333118362,127.034259548015);
        buildingList.add(aegyeonggwan);

        Building yejigwan = new Building("3", "예지관",37.3007083,127.036551);
        buildingList.add(yejigwan);

        Building deogmungwan = new Building("4", "덕문관",37.29998617,127.03677323);
        buildingList.add(deogmungwan);

        Building gwanggyogwan = new Building("5", "광교관",37.30093053,127.03832862);
        buildingList.add(gwanggyogwan);

        Building jibhyeongwan = new Building("6", "집현관",37.30129161,127.03877301);
        buildingList.add(jibhyeongwan);

        Building yugyeonggwan = new Building("7", "육영관",37.30070835,127.03921743);
        buildingList.add(yugyeonggwan);

        Building hoyeongwan = new Building("8", "호연관",37.30412453,127.03394004);
        buildingList.add(hoyeongwan);

        Building yehak = new Building("9", "예학관",37.30315241,127.0338845);
        buildingList.add(yehak);

        Building gonghak = new Building("10", "제2공학관",37.30037507,127.0399396);
        buildingList.add(gonghak);

        Building library = new Building("11", "도서관",37.30115268,127.0355233);
        buildingList.add(library);

        Building jonghab = new Building("12", "종합강의동",37.30143045,127.037412);
        buildingList.add(jonghab);

        Building Eseukweeo = new Building("13", "E-스퀘어");
        buildingList.add(Eseukweeo);
    }

    private void setUpList() {

        listView = findViewById(R.id.building_listView);

        BuildingAdapter adapter = new BuildingAdapter(getApplicationContext(), 0, buildingList);
        listView.setAdapter(adapter);

    }

    private void start_setUpOnClickListener() {

        BuildingAdapter start_adapter = new BuildingAdapter(getApplicationContext(), 0, buildingList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),

                        start_adapter.getItem(position).getName(),
                        Toast.LENGTH_LONG).show();
               //검색어 설정
                SearchView start_searchView = findViewById(R.id.start_search_view);
                start_searchView.setQuery(start_adapter.getItem(position).getName(),true);
                //값전달은 adapter.getItem(position).animal내의 함수사용하면 됨.
                slat=start_adapter.getItem(position).getLat();
                slon=start_adapter.getItem(position).getLon();
            }
        });

    }
    private void end_setUpOnClickListener() {

        BuildingAdapter end_adapter = new BuildingAdapter(getApplicationContext(), 0, buildingList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),

                        end_adapter.getItem(position).getName(),
                        Toast.LENGTH_LONG).show();
                //검색어 설정
                SearchView end_searchView = findViewById(R.id.end_search_view);
                //클릭시 적용
                end_searchView.setQuery(end_adapter.getItem(position).getName(),true);
                //값전달은 adapter.getItem(position).animal내의 함수사용하면 됨.
                dlat=end_adapter.getItem(position).getLat();
                dlon=end_adapter.getItem(position).getLon();
            }
        });
    }
}


