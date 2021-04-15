package com.example.trytolearn;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class SignupActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    public String info_property = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new SignupActivity.MyLocationListener());
        setContentView(R.layout.activity_signup);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(SignupActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(SignupActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(SignupActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.
                    size()]);
            ActivityCompat.requestPermissions(SignupActivity.this, permissions, 1);
        } else {
            requestLocation();
        }


        Button goback = findViewById(R.id.button9);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(SignupActivity.this, FirstActivity.class);
                Toast toast = Toast.makeText(getApplicationContext(), "注册成功！前往登录", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(a);
            }
        });


    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(location.getLatitude()).
                            append("\n");
                    currentPosition.append("经度：").append(location.getLongitude()).
                            append("\n");
                    currentPosition.append("国家：").append(location.getCountry()).
                            append("\n");
                    currentPosition.append("省：").append(location.getProvince()).
                            append("\n");
                    currentPosition.append("市：").append(location.getCity()).
                            append("\n");
                    currentPosition.append("区：").append(location.getDistrict()).
                            append("\n");
                    currentPosition.append("街道：").append(location.getStreet()).
                            append("\n");
//                    ProgressBar v = findViewById(R.id.gps_bar);
//                    v.setVisibility(View.GONE);
//                    positionText.setText(currentPosition);
                    info_property = currentPosition.toString();

                    Button upload = findViewById(R.id.button11);
                    upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                            dialog.setTitle("请确认您的位置属性：");
                            dialog.setMessage(info_property);
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("上传", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
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
                                    Toast toast = Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });

                            dialog.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.show();
                        }
                    });


                }
            });

        }


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
                            Toast.makeText(this, "必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
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


}