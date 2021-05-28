package com.example.trytolearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ReceiverFormActivity extends AppCompatActivity {
    private String rPro = "";
    private String rCity = "";
    private String rArea = "";
    private String rName = "";
    private String rPhone = "";
    private String rAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_form);

        Button update = findViewById(R.id.update_receiver_info);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = findViewById(R.id.r_name);
                rName = e.getText().toString();

                EditText tel = findViewById(R.id.r_tel);
                rPhone = tel.getText().toString();

                EditText add = findViewById(R.id.rAddress);
                rAddress = add.getText().toString();

                EditText pro = findViewById(R.id.rPro);
                rPro = pro.getText().toString();

                EditText city = findViewById(R.id.rCity);
                rCity = city.getText().toString();

                EditText area = findViewById(R.id.rArea);
                rArea = area.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences("receiver", MODE_PRIVATE).edit();
                editor.putString("rName", rName);
                editor.putString("rPhone", rPhone);
                editor.putString("rAddress", rAddress);
                editor.putString("rPro", rPro);
                editor.putString("rCity", rCity);
                editor.putString("rArea", rArea);
                editor.apply();

                Intent a = new Intent();
                a.setClass(ReceiverFormActivity.this, UserBottomBarActivity.class);
                startActivity(a);
                ReceiverFormActivity.this.finish();
            }
        });

        Button cancel = findViewById(R.id.cancelable);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(ReceiverFormActivity.this, UserBottomBarActivity.class);
                startActivity(a);
                ReceiverFormActivity.this.finish();
            }
        });
    }


}