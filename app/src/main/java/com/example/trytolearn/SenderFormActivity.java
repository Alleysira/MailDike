package com.example.trytolearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

public class SenderFormActivity extends AppCompatActivity {
    private String item_type = "";
    private String item_weight = "";
    private String sPro = "";
    private String sCity = "";
    private String sArea = "";
    private String sName = "";
    private String sPhone = "";
    private String sAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_form);

        TextView kind = findViewById(R.id.kind);
        kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SenderFormActivity.this, kind);
                popupMenu.getMenuInflater().inflate(R.menu.express_kind, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 控件每一个item的点击事件
                        switch (item.getItemId()) {
                            case R.id.daily_item:
                                item_type = item.getTitle().toString();
                                break;
                            case R.id.food_item:
                                item_type = item.getTitle().toString();
                                break;
                            case R.id.file_item:
                                item_type = item.getTitle().toString();
                                break;
                            case R.id.clothes_item:
                                item_type = item.getTitle().toString();
                                break;
                            case R.id.digital_item:
                                item_type = item.getTitle().toString();
                                break;
                            case R.id.others_item:
                                item_type = item.getTitle().toString();
                                break;
                            default:
                                item_type = "日用品";
                                break;
                        }
                        return false;

                    }
                });

                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        // 控件消失时的事件
                        kind.setText(item_type);
                    }
                });
            }
        });


        Button update = findViewById(R.id.update_sender_info);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = findViewById(R.id.weight);
                item_weight = t.getText().toString();

                EditText e = findViewById(R.id.s_name);
                sName = e.getText().toString();

                EditText tel = findViewById(R.id.s_tel);
                sPhone = tel.getText().toString();

                EditText add = findViewById(R.id.sAddress);
                sAddress = add.getText().toString();

                EditText pro = findViewById(R.id.sPro);
                sPro = pro.getText().toString();

                EditText city = findViewById(R.id.sCity);
                sCity = city.getText().toString();

                EditText area = findViewById(R.id.sArea);
                sArea = area.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences("sender", MODE_PRIVATE).edit();
                editor.putString("sName", sName);
                editor.putString("sPhone", sPhone);
                editor.putString("sAddress", sAddress);
                editor.putString("sPro", sPro);
                editor.putString("sCity", sCity);
                editor.putString("sArea", sArea);
                editor.putString("item_type", item_type);
                editor.putString("item_weight", item_weight);
                editor.apply();

                Intent a = new Intent();
                a.setClass(SenderFormActivity.this, UserBottomBarActivity.class);
                startActivity(a);
                SenderFormActivity.this.finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}