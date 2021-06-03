package com.android.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailedScheduleActivity extends AppCompatActivity implements OnMapReadyCallback{
     EditText editText;
     Button search_Btn;
     GoogleMap mMap;
     Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_schedule);

        //제목 설정
        ActionBar ab = getSupportActionBar();
        ab.setTitle("CalendarApp");


        //객체 초기화
        editText = findViewById(R.id.GoogleMap_EditText);
        search_Btn = findViewById(R.id.GoogleMap_Button);
        
        //GoogleMap
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.GoogleMap);
        mapFragment.getMapAsync(this);


    }


    //버튼 설정
    public void btnClick(View view){
        if(view.getId()==R.id.Schedule_Save){
            finish();
        }else if(view.getId()==R.id.Schedule_Cancle){
            finish();
        }else if(view.getId()==R.id.Schedule_Delete){
            finish();
        }
    }

    //GoogleMap 관련
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this, Locale.KOREA);



        // 버튼 이벤트
        search_Btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                }
                if (addressList != null) {
                    if (addressList.size() == 0)
                        editText.setText("해당되는 주소 정보는 없습니다.");

                    else {
                        System.out.println(addressList.get(0).toString());
                        // 콤마를 기준으로 split
                        String[] splitStr = addressList.get(0).toString().split(",");
                        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                        System.out.println(address);

                        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                        System.out.println(latitude);
                        System.out.println(longitude);

                        // 좌표(위도, 경도) 생성
                        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        // 마커 생성
                        MarkerOptions mOptions2 = new MarkerOptions();
                        mOptions2.title("search result");
                        mOptions2.snippet(address);
                        mOptions2.position(point);
                        // 마커 추가
                        mMap.addMarker(mOptions2);
                        // 해당 좌표로 화면 줌
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
                    }
                }
            }


        });
        ////////////////////

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(37.5817891, 127.008175);
        mMap.addMarker(new MarkerOptions().position(sydney).title("한성대학교"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
    }
    
    
}