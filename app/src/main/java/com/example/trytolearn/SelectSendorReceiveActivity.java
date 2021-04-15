package com.example.trytolearn;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SelectSendorReceiveActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sendor_receive);

        Button sender = findViewById(R.id.select_sender);
        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(SelectSendorReceiveActivity.this, SenderActivity.class);
                startActivity(a);
            }
        });

        Button receiver = findViewById(R.id.select_receiver);
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendpostRequestwithOKhttp();
                Intent a = new Intent();
                a.setClass(SelectSendorReceiveActivity.this, UserBottomBarActivity.class);
                startActivity(a);

            }
        });

    }


    public void sendRequestwithOKhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://www.baidu.com")

                            .build();
                    Response response = client.newCall(request).execute();
                    String requestdata = response.body().string();
//                    showResponse(requestdata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void sendpostRequestwithOKhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("s", "admin")
                            .build();

                    //"http://10.136.97.196:8080//waybill/android"192.168.43.7
                    Request postrequest = new Request.Builder()
                            .url("http://10.135.110.103:8080//waybill/android")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(postrequest).execute();
                    String requestdata = response.body().string();
//                    showResponse(requestdata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button receiver = findViewById(R.id.select_receiver);
                receiver.setText(response);
            }
        });

    }

}









