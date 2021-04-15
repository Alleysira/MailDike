package com.example.trytolearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trytolearn.CPABEAPP.CPABE;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SenderActivity extends AppCompatActivity {
    private static final String default_path_header = "/data/data/com.example.trytolearn/files/";
    private static final String default_path_publickey = "/data/data/com.example.trytolearn/files/";
    private static final String default_path_ciphertext = "/data/data/com.example.trytolearn/files/";
    public String cipher = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
        Button send = findViewById(R.id.fill_the_sender);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(SenderActivity.this, SenderFormActivity.class);
                startActivity(a);
            }
        });

        Button receiver = findViewById(R.id.fill_the_receiver);
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(SenderActivity.this, ReceiverFormActivity.class);
                startActivity(a);
            }
        });

        Button date = findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(SenderActivity.this, DateActivity.class);
                startActivity(a);
            }
        });

        Button time = findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(SenderActivity.this, TimeActivity.class);
                startActivity(a);
            }
        });

        Button encryption = findViewById(R.id.encrypt);
        encryption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CPABE cpabe = new CPABE();
                String message = "";

                SharedPreferences my_sender = getSharedPreferences("sender", MODE_PRIVATE);
                SharedPreferences my_receiver = getSharedPreferences("receiver", MODE_PRIVATE);

                message +=
                        my_sender.getString("sName", "")
                                + " "
                                + my_sender.getString("sPhone", "")
                                + " "
                                + my_sender.getString("sAddress", "")
                                + " "
                                + my_receiver.getString("rName", "")
                                + " "
                                + my_receiver.getString("rPhone", "")
                                + " "
                                + my_receiver.getString("rAddress", "");
                TextView show = findViewById(R.id.show_personal_info);
                TextView show_cipher = findViewById(R.id.show_cipher);
                show.setText(message);
//                message="BUAA";
                String Policy = "116.350 and 9.983";

                PairingKeySerParameter publicKey = cpabe.getpublickey(default_path_publickey + "pubkey_p", default_path_publickey + "pubkey_g",
                        default_path_publickey + "pubkey_h", default_path_publickey + "pubkey_f", default_path_publickey + "pubkey_eggalpha");


                cpabe.Encryption(publicKey, Policy, message,
                        default_path_header + "header_Parameters", default_path_header + "header_C",
                        default_path_header + "header_Crhos", default_path_header + "header_C1s",
                        default_path_header + "header_C2s", default_path_ciphertext + "ciphertext1");
                cipher = readFormFile("ciphertext1");
                show_cipher.setText(cipher);
                postPersonlaandExpressInfo();

            }
        });


    }

    public void postPersonlaandExpressInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences my_sender = getSharedPreferences("sender", MODE_PRIVATE);
                    SharedPreferences my_receiver = getSharedPreferences("receiver", MODE_PRIVATE);

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("personal_info", cipher)
                            .add("sPro", my_sender.getString("sPro", ""))
                            .add("sCity", my_sender.getString("sCity", ""))
                            .add("sArea", my_sender.getString("sArea", ""))
                            .add("item_type", my_sender.getString("item_type", ""))
                            .add("item_weight", my_sender.getString("item_weight", ""))
                            .add("rPro", my_receiver.getString("rPro", ""))
                            .add("rCity", my_receiver.getString("rCity", ""))
                            .add("rArea", my_receiver.getString("rArea", ""))
                            .build();

                    //"http://10.136.97.196:8080//waybill/android"192.168.43.7
                    Request postrequest = new Request.Builder()
                            .url("http://10.136.97.196:8080/waybill/android")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(postrequest).execute();
//                    String requestdata = response.body().string();
//                    showResponse(requestdata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String readFormFile(String name) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput(name);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}