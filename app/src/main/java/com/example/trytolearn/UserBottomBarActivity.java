package com.example.trytolearn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yxing.ScanCodeConfig;

public class UserBottomBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        //change color
//        Resources resource = getResources();
//        ColorStateList csl = ContextCompat.getColor(context, R.color.blue);
//        navView.setItemTextColor(csl);
//        startScan(ScanStyle.WECHAT, ScanCodeActivity.class);
    }

    public String deAffine(String cipher) {
        char[] table = {'.', '?', '@', '^', '*', 'a', '&', '%', '/', '$'};
        char[] c = cipher.toCharArray();
        StringBuilder id = new StringBuilder();
        for (char i : c) {
            for (int j = 0; j <= 9; j++) {
                if (i == table[j]) {
                    id.append(j);
                    break;
                }
            }
        }
        return id.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收扫码结果
        if (resultCode == RESULT_OK && requestCode == ScanCodeConfig.QUESTCODE && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                String code = extras.getString(ScanCodeConfig.CODE_KEY);
                SharedPreferences.Editor editor = getSharedPreferences("QRCode", 0).edit();
                editor.putString("code1", code);
                editor.apply();
                code = deAffine(code);
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserBottomBarActivity.this);
                dialog.setTitle("签收成功！");
                dialog.setMessage("您编号为：" + code + "的订单已经签收");
                dialog.setCancelable(true);
                dialog.setPositiveButton("查看具体信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(getApplicationContext(), "请核对物流信息", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                dialog.show();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserBottomBarActivity.this);
                dialog.setTitle("签收失败！");
                dialog.setMessage("请核对是否是您的快递");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(getApplicationContext(), "返回", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(UserBottomBarActivity.this);
            dialog.setTitle("签收失败！");
            dialog.setMessage("请核对是否是您的快递");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast toast = Toast.makeText(getApplicationContext(), "返回", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            dialog.setCancelable(true);
            dialog.show();
        }
    }

}