package com.example.trytolearn;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class UploadLocationActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    private TextView positionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_location);
        positionText = (TextView) findViewById(R.id.position_text_view);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(UploadLocationActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(UploadLocationActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(UploadLocationActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.
                    size()]);
            ActivityCompat.requestPermissions(UploadLocationActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
        Button a = findViewById(R.id.GotoSign);
        a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(UploadLocationActivity.this, "??????????????????", Toast.LENGTH_SHORT);
                toast.show();
                Intent a = new Intent();
                a.setClass(UploadLocationActivity.this, SignupActivity.class);
                startActivity(a);
            }
        });
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        mLocationClient.setLocOption(option);
    }


    protected void onDestory() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "?????????????????????????????????????????????",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("?????????").append(location.getLatitude()).
                            append("\n");
                    currentPosition.append("?????????").append(location.getLongitude()).
                            append("\n");
                    currentPosition.append("?????????").append(location.getCountry()).
                            append("\n");
                    currentPosition.append("??????").append(location.getProvince()).
                            append("\n");
                    currentPosition.append("??????").append(location.getCity()).
                            append("\n");
                    currentPosition.append("??????").append(location.getDistrict()).
                            append("\n");
                    currentPosition.append("?????????").append(location.getStreet()).
                            append("\n");
                    ProgressBar v = findViewById(R.id.gps_bar);
                    v.setVisibility(View.INVISIBLE);
                    positionText.setText(currentPosition);
                    String str = "";
                    String str2 = "";
                    Double Latitude = location.getLatitude();
                    Double Longitude = location.getLongitude();
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.000");
                    str = df.format(Latitude);
                    str2 = df.format(Longitude);
                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.putString("Latitude", str);
                    editor.putString("Longitude", str2);
                    editor.apply();
                }
            });

        }


    }


}