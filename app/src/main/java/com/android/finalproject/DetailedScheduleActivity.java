package com.android.finalproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

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
     EditText Map_editText;
     Button search_Btn;
     GoogleMap mMap;
     Geocoder geocoder;

    EditText mTitle;
    String mYear;
    String mMonth;
    String mDate;
    int mTimeStart;
    int mTimeEnd;
    EditText mPlace;
    EditText mMemo;

    private DBHelper mDbHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_schedule);

        mDbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String year, month, date, time;
        year = intent.getStringExtra("year");
        month = intent.getStringExtra("month");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");

        EditText a = findViewById(R.id.Schedule_Title);
        a.setText(year+"년 "+month+"월 "+date+"일 "+time+"시");

        mYear = year;
        mMonth = month;
        mDate = date;

        //제목 설정
        ActionBar ab = getSupportActionBar();
        ab.setTitle("CalendarApp");

        //객체 초기화
        Map_editText = findViewById(R.id.GoogleMap_EditText);
        search_Btn = findViewById(R.id.GoogleMap_Button);
        
        //GoogleMap
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.GoogleMap);
        mapFragment.getMapAsync(this);

    }


    //버튼 설정
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnClick(View view){
        if(view.getId()==R.id.Schedule_Save){
            insertRecord();
            ((MainActivity) MainActivity.context).findDateFromDb(mYear, mMonth, mDate);
            finish();
        }else if(view.getId()==R.id.Schedule_Cancle){
            Intent intent = new Intent(getApplication(), Testdb.class);
            startActivity(intent); //액티비티 열기
            //finish();
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
                String str = Map_editText.getText().toString();
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
                        Map_editText.setText("해당되는 주소 정보는 없습니다.");

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertRecord() {
        mTitle = (EditText) findViewById(R.id.Schedule_Title);
        mPlace = (EditText) findViewById(R.id.GoogleMap_EditText);
        mMemo = (EditText) findViewById(R.id.memo);

        TimePicker startTime = (TimePicker) findViewById(R.id.tp_timepicker_start);
        mTimeStart = startTime.getHour();
        TimePicker endTime = (TimePicker) findViewById(R.id.tp_timepicker_end);
        mTimeEnd = endTime.getHour();

        mDbHelper.insertUserBySQL(mTitle.getText().toString(), mYear, mMonth, mDate, Integer.toString(mTimeStart), Integer.toString(mTimeEnd), mPlace.getText().toString(), mMemo.getText().toString());
//        long nOfRows = mDbHelper.insertUserByMethod(name.getText().toString(),phone.getText().toString());
//        if (nOfRows >0)
//            Toast.makeText(this,nOfRows+" Record Inserted", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this,"No Record Inserted", Toast.LENGTH_SHORT).show();
    }
}