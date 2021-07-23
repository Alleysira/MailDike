package com.example.trytolearn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yxing.ScanCodeConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserBottomBarActivity extends AppCompatActivity {
    String code = "";
    String default_path_header = "/data/data/com.example.trytolearn/files/";
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
    }


    public String rsaDecrypt(String sec3) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDTJK2H-siKcVJDPmNL9iDeAUCI2kHMp4SkZJb8EXqPpbQIVGTQjqwTD7H2Lt0XLgLkZgldLzMleGER7sXGCyPTpzjH8Xcm_QhUXGbH8aV9zju3KkNHPGSSW58jNwfGW9R5K75Px4zzt0NlHCa1k7Du5YHVbyy7xuodlrOLOKSNnnlKys4Rkj_sHnnECMerqe7OiKdFAEkBjEQ9FTXQ9F-PC761bvRIGE23fU1b2z5wyZ-2tCcEDl0mm4HkDLaWoQeQN1bQMs6GgTVaMJWsAM145O4wy9e6irbraoTTNLQFANdEmX-O2u82wp-_6xHQxQYLldUgMJMJJ_z7uNJ9fGzlAgMBAAECggEBAKA8jZVMtTSbm5p8lk7nkznoKVmyiY0O9JLt48eYXDL3xhEsOvYr0FsG5j-2gQM-X-OFrEWTtHUTTleVpXIOsjnGBkl27r4f-VpMZZQHx1gaUydGY5iENK796V-IO9ZFipIKqHtTLZ0nz3XET3wtgXFLq6SWopQ--R1LaSaW7gqqgLZGZb6EQmil3JOkEBvV1zWmHcoYUY9gKDYboyU0pn5Jli6R3GezF1IcmSdN7wXuVTCYCQBImIIfOFJ7VotumWiDkcP6oIAjS8g2g017ad52iCoOLTc4ki0s86rptrsR7tQVm17F2SPMbRzcGCa88EQpDVfOLLkW0FZ3kyTY-QECgYEA8P1_8MXapHVFJo2bRwf8UBtUkH4toaQn7USSpWX6yGQ1KsjwlTZ-qvYzyArv-7zOHHMvswL96WGMjwG9urT_4FqtkNMCAL8fAcfeOojsUpp61b2S-K-vnrXqscRiPlT2FhxZEW45zARY2o-a_vhyLLYeSiqcxUPCyJ-62SOH65kCgYEA4EtHgiUVHc6Dpi1oFsXTKHYxOuNeWZUOEz4pksK5n3hVbCVG7mY46kDgNpsgGYwDR3rKsZAQsfmPnZjJun96LKYsiTam1h-mAB93ULLWQ6PmNhhFEENHwSvo0pCA1m4ShvvW77s-jyfX1TM67H6H2XOkql-Mx8NReSO-gucB-y0CgYEAqRVT1P0dANJ-6CPm1JmXwCTM2myNW6IvmVvJgF7i7ALTAuflVOvdR9piTnLOGlRIUNHIn9Lzj_Gviw7vrbYc6a71pG1INHnkKX2wQGWdWf-lO549Jlst3y9IMd3WCGHYH39YRtCNoMVUClVDrK1oflJxQhxPzmBSpCzeDkfNr9ECgYAEGnx5bhI_1FpmPOhtmjrtv5PQ_v3n56k1Qurhy3w35ayyaNAuZmJeLserWBUzQnOA6EczDm6vwuAUwwnVxqVGkde4vu44dqXD1M-LA2qWHDaHANSqooB6kUIWMBybT7I5E_xvsF5JojH5rZFDaGE14j69-zeJJBKmg7fljzJjFQKBgHC731QM3tfz58JQCdzydwtsLnqKjkdADECYScT-q2IdnH04BcBPZxyeriT_EpXfjbDeCLVrpQWvCDWiLtpfSyRB1fAI8FAH-NWw1fthy9vgQE2ncBA8vKSXKhi3joH6fSFfZJq-oMAOqxUJnKAKOHqozvYjUVtfDeaQclbLOMf3";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0ySth_rIinFSQz5jS_Yg3gFAiNpBzKeEpGSW_BF6j6W0CFRk0I6sEw-x9i7dFy4C5GYJXS8zJXhhEe7Fxgsj06c4x_F3Jv0IVFxmx_Glfc47typDRzxkklufIzcHxlvUeSu-T8eM87dDZRwmtZOw7uWB1W8su8bqHZazizikjZ55SsrOEZI_7B55xAjHq6nuzoinRQBJAYxEPRU10PRfjwu-tW70SBhNt31NW9s-cMmftrQnBA5dJpuB5Ay2lqEHkDdW0DLOhoE1WjCVrADNeOTuMMvXuoq262qE0zS0BQDXRJl_jtrvNsKfv-sR0MUGC5XVIDCTCSf8-7jSfXxs5QIDAQAB";
        String msg = RSAUtils.publicDecrypt(sec3, RSAUtils.getPublicKey(publicKey));
        return msg;
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

    public String getFileRequset(String id, String fileName) {
        final String[] requestdata = {new String("")};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ip = "192.168.137.254";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("id", id)
                            .add("fileName", fileName)
                            .build();

                    Request postrequest = new Request.Builder()
                            .url("http://" + ip + ":8080/code/userGetCode")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(postrequest).execute();
                    requestdata[0] = response.body().string();
//                    SharedPreferences.Editor file = getSharedPreferences(id,MODE_PRIVATE).edit();
//                    file.putString(fileName,requestdata);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return requestdata[0];
    }


    public void write2File(String data, String path) {
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file, true); //设置成true就是追加
            fw.write(data);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收扫码结果
        if (resultCode == RESULT_OK && requestCode == ScanCodeConfig.QUESTCODE && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                code = extras.getString(ScanCodeConfig.CODE_KEY);
                SharedPreferences.Editor editor = getSharedPreferences("QRCode", 0).edit();
                editor.putString("code1", code);
                editor.apply();
                code = deAffine(code);
                if (code.equals("87489014")) {
                    String sec3 = getFileRequset(code, "section3");
                    try {
                        String dec_sec3 = rsaDecrypt(sec3);
                        //to be test,sec3 contains sName,sPhone,rName,rPhone,signature(BUAA),RN
                        String[] personal_info = dec_sec3.split("\\n");
                        TextView name = findViewById(R.id.rece_name);
                        name.setText(personal_info[0]);
                        TextView phone = findViewById(R.id.rece_tel);
                        phone.setText(personal_info[1]);
                    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    write2File(getFileRequset(code, "rec_header_Parameters"), default_path_header + "header_Parameters");
                    write2File(getFileRequset(code, "rec_header_C"), default_path_header + "header_C");
                    write2File(getFileRequset(code, "rec_header_Crhos"), default_path_header + "header_Crhos");
                    write2File(getFileRequset(code, "rec_header_C1s"), default_path_header + "header_C1s");
                    write2File(getFileRequset(code, "rec_header_C2s"), default_path_header + "header_C2s");

                    AlertDialog.Builder dialog = new AlertDialog.Builder(UserBottomBarActivity.this);
                    dialog.setTitle("签收成功！");
                    TextView text = findViewById(R.id.get_changed_receive);
                    text.setText("已签收");
//                    TextView name = findViewById(R.id.rece_name);
//                    name.setText("小邮");
//                    TextView phone = findViewById(R.id.rece_tel);
//                    phone.setText("18993771199");
                    TextView tel = findViewById(R.id.tel);
                    tel.setText(code);
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