package com.example.tmap_last1;


import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapData.FindPathDataListenerCallback;
import com.skt.Tmap.TMapData.TMapPathType;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import com.unity3d.player.UnityPlayerActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MainActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback  {
    //public static double slat;
    //public static double slon;
    // T Map GPS
    TMapGpsManager tMapGPS = null;
    TMapView TMapView = null;
    String tot1="";
    String tot2="";
    TMapData tmapdata=new TMapData();
    TMapData q1=new TMapData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // TMapView.setCompassMode(true);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // T Map View
        TMapView = new TMapView(this);
        double slat;
        double slon;
        double dlat;
        double dlon;



        Intent secondIntent = getIntent();
        slat=secondIntent.getDoubleExtra("시작위도",0);

        slon=secondIntent.getDoubleExtra("시작경도",0);
        dlat=secondIntent.getDoubleExtra("도착위도",0);
        dlon=secondIntent.getDoubleExtra("도착경도",0);

        // API Key
        TMapView.setSKTMapApiKey("l7xx0c744316e8454769a3892cc232238b4a");
        TMapPoint tMapPointStart = new TMapPoint(slat, slon); // 출발지
        TMapPoint tMapPointEnd = new TMapPoint(dlat ,dlon); // 목적지
        TMapPoint tMapPoint1 = new TMapPoint(37.299955, 127.036742);

        Button openBtn=(Button) findViewById(R.id.button);
        openBtn.setOnClickListener(v->{
            AR(tMapPointStart,tMapPointEnd);
        });

        openBtn = (Button)findViewById(R.id.button3);
        openBtn.setOnClickListener(v -> {
            searchClick();
        });




        tmapdata.findPathDataWithType(TMapPathType.PEDESTRIAN_PATH, tMapPointStart, tMapPointEnd, new FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine polyLine) {
                TMapView.addTMapPath(polyLine);
            }


        });






        // Initial Setting
        TMapView.setZoomLevel(17);
        TMapView.setIconVisibility(true);
        TMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        TMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        // T Map View Using Linear Layout
        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        linearLayoutTmap.addView(TMapView);



        //TMapView.setCenterPoint(37.570841, 130, true);
        TMapMarkerItem markerItem1 = new TMapMarkerItem();
        // Request For GPS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // GPS using T Map
        tMapGPS = new TMapGpsManager(this);

        // Initial Setting
        tMapGPS.setMinTime(1);
        tMapGPS.setMinDistance(1);
        tMapGPS.setProvider(tMapGPS.NETWORK_PROVIDER);
       // tMapGPS.setProvider(tMapGPS.GPS_PROVIDER);



        tMapGPS.OpenGps();




        TMapView.setCenterPoint( 127.036742, 37.2997989 );//




    }

    @Override
    public void onLocationChange(Location location) {

        TMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        TMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
        //slat=location.getLatitude();
        //slon=location.getLongitude();
    }


    public void searchClick() {
        Intent i = new Intent(MainActivity.this, com.example.tmap_last1.search.class);
        startActivity(i);
        finish();

    }

    public void AR(TMapPoint tMapPointStart,TMapPoint tMapPointEnd) {
        tmapdata.findPathDataAllType(TMapPathType.PEDESTRIAN_PATH, tMapPointStart, tMapPointEnd, new TMapData.FindPathDataAllListenerCallback() {
                    @Override
                    public void onFindPathDataAll(Document document) {
                        Element root = document.getDocumentElement();
                        NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");

                        for( int i=0; i<nodeListPlacemark.getLength(); i++ ) {
                            NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();

                            for( int j=0; j<nodeListPlacemarkItem.getLength(); j++ ) {
                                if( nodeListPlacemarkItem.item(j).getNodeName().equals("description") ) {
                                    Log.d("debug", nodeListPlacemarkItem.item(j).getTextContent().trim() );
                                }
                                if(nodeListPlacemarkItem.item(j).getNodeName().equals("Point")){

                                    NodeList nodeListPlacemarkItem2 = nodeListPlacemarkItem.item(j).getChildNodes();

                                    for (int k=0;k<nodeListPlacemarkItem2.getLength();k++){
                                        if(nodeListPlacemarkItem2.item(k).getNodeName().equals("coordinates")){
                                            String coordinates = nodeListPlacemarkItem2.item(k).getTextContent();
                                            tot1 = tot1 + coordinates+"/";

                                            double[] b = new double[2];
                                            b[0]=Double.parseDouble(coordinates.substring(0,coordinates.indexOf(',')));
                                            b[1]=Double.parseDouble(coordinates.substring(coordinates.indexOf(',')+1,coordinates.length()));



                                            // Log.d("debug", "노드 포인트 의 내용 : " + "<" + nodeListPlacemarkItem2.item(k).getNodeName() + "> 위도 : " + b[1] + "// 경도 : " + b[0]);

                                        }

                                    }

                                }
                            }
                        }
                        Log.d("debug", tot1);
                        Intent unityi = new Intent(MainActivity.this, UnityPlayerActivity.class);
                        unityi.putExtra("tot",tot1);
                        startActivity(unityi);
                        finish();
                    }
                }
        );


    }


}