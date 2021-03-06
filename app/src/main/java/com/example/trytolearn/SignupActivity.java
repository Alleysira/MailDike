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
import android.widget.EditText;
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
    int flag = 0;

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
                EditText acc = findViewById(R.id.first_editText);
                EditText pass1 = findViewById(R.id.second_edittext_2);
                EditText pass2 = findViewById(R.id.third_edittext);

                String word1 = pass1.getText().toString();
                String word2 = pass2.getText().toString();
                String account = acc.getText().toString();
                if (!account.equals("")) {
                    if (!word1.equals("")) {
                        if (!word2.equals("")) {
                            if (word1.equals(word2)) {
                                if (flag == 1) {
                                    //store the account and password
                                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                                    editor.putString("account", account);
                                    editor.putString("password", word1);
                                    editor.apply();

                                    //change the activity
                                    Intent a = new Intent();
                                    a.setClass(SignupActivity.this, FirstActivity.class);
                                    Toast toast = Toast.makeText(getApplicationContext(), "???????????????????????????", Toast.LENGTH_SHORT);
                                    toast.show();
                                    startActivity(a);
                                    SignupActivity.this.finish();
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "??????????????????????????????", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "???????????????", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "???????????????", Toast.LENGTH_SHORT);
                    toast.show();
                }


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
//                    ProgressBar v = findViewById(R.id.gps_bar);
//                    v.setVisibility(View.GONE);
//                    positionText.setText(currentPosition);
                    info_property = currentPosition.toString();

                    Button upload = findViewById(R.id.button11);
                    upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
                            dialog.setTitle("??????????????????????????????");
                            dialog.setMessage(info_property);
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String str = "";
                                    String str2 = "";
                                    Double Latitude = location.getLatitude();
                                    Double Longitude = location.getLongitude();
                                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.000");
                                    str = df.format(Latitude);
                                    str2 = df.format(Longitude);
                                    SharedPreferences.Editor editor = getSharedPreferences("Attr", MODE_PRIVATE).edit();
                                    editor.putString("Latitude", str);
                                    editor.putString("Longitude", str2);
                                    editor.apply();
                                    flag = 1;
                                    Toast toast = Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });

                            dialog.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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
                            Toast.makeText(this, "?????????????????????????????????????????????",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
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