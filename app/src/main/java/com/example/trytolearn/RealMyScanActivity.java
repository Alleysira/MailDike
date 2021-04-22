package com.example.trytolearn;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trytolearn.CPABEAPP.CPABE;
import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.yxing.ScanCodeActivity;
import com.yxing.ScanCodeConfig;
import com.yxing.def.ScanStyle;

import cn.edu.buaa.crypto.algebra.serparams.PairingCipherSerParameter;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class RealMyScanActivity extends AppCompatActivity {
    private static final String default_path_header = "/data/data/com.example.trytolearn/files/";
    private static final String default_path_publickey = "/data/data/com.example.trytolearn/files/";
    private static final String default_path_secretkey = "/data/data/com.example.trytolearn/files/";
    private static final String default_path_ciphertext = "/data/data/com.example.trytolearn/files/";
    private TextView tvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scan);

        tvCode = findViewById(R.id.tv_code);

        startScan(ScanStyle.WECHAT, ScanCodeActivity.class);

        Button a = findViewById(R.id.test_for_download);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(RealMyScanActivity.this, TrytocommunicateActivity.class);
                startActivity(a);
            }
        });


        Button tell = findViewById(R.id.call);
        tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(RealMyScanActivity.this, "您没有权限拨打用户电话", Toast.LENGTH_SHORT);
                toast.show();
            }

        });


        Button decrypt = findViewById(R.id.solve);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast toast=Toast.makeText(RealMyScanActivity.this,"您没有权限解密",Toast.LENGTH_SHORT);
//                toast.show();
                CPABE cpabe = new CPABE();
//                        String attribute = "Beijing and HD and BUAA";
                String attribute = "116.350" + " and "
                        + "9.983";
                String message = "";
                PairingKeySerParameter publicKey = cpabe.getpublickey(default_path_publickey + "pubkey_p", default_path_publickey + "pubkey_g",
                        default_path_publickey + "pubkey_h", default_path_publickey + "pubkey_f", default_path_publickey + "pubkey_eggalpha");
                PairingKeySerParameter secretKey = cpabe.getsecretkey(default_path_secretkey + "secretkey_Parameters",
                        default_path_secretkey + "secretkey_D", default_path_secretkey + "secretkey_D1s",
                        default_path_secretkey + "secretkey_D2s", default_path_secretkey + "secretkey_attributes");
                PairingCipherSerParameter header = cpabe.getheader(default_path_header + "header_Parameters",
                        default_path_header + "header_C", default_path_header + "header_C1s",
                        default_path_header + "header_C2s", default_path_header + "header_Crhos");
                message = cpabe.Deryption(publicKey, attribute, secretKey, header, default_path_ciphertext + "ciphertext1");
                TextView message_view = findViewById(R.id.message_view);
                message_view.setText(message);
            }

        });


    }

    public void startScan(int style, Class mClass) {
        new RxPermissions(this)
                .requestEachCombined(Manifest.permission.CAMERA)
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Permission permission) {
                        if (permission.granted) {
                            ScanCodeConfig.create(RealMyScanActivity.this)
                                    //设置扫码页样式 ScanStyle.NONE：无  ScanStyle.QQ ：仿QQ样式   ScanStyle.WECHAT ：仿微信样式
                                    .setStyle(ScanStyle.QQ)
                                    //扫码成功是否播放音效  true ： 播放   false ： 不播放
                                    .setPlayAudio(true)
                                    .buidler()
                                    //跳转扫码页   扫码页可自定义样式
                                    .start(mClass);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收扫码结果
        if (resultCode == RESULT_OK && requestCode == ScanCodeConfig.QUESTCODE && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Button tell = findViewById(R.id.call);
                tell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(Intent.ACTION_DIAL);
                        a.setData(Uri.parse("tel:17793709599"));
                        startActivity(a);
                    }
                });

                String code = extras.getString(ScanCodeConfig.CODE_KEY);
                //store in the order.xml
                SharedPreferences.Editor editor = getSharedPreferences("order", MODE_PRIVATE).edit();
                editor.putString("orderid", code);
                editor.apply();

                tvCode.setText(String.format("%s%s", "订单号： ", code));

                //send String to the admin
                Button b = findViewById(R.id.solve);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CPABE cpabe = new CPABE();
//                        String attribute = "Beijing and HD and BUAA";
                        String attribute = "116.350" + " and "
                                + "9.983";
                        String message = "";
                        PairingKeySerParameter publicKey = cpabe.getpublickey(default_path_publickey + "pubkey_p", default_path_publickey + "pubkey_g",
                                default_path_publickey + "pubkey_h", default_path_publickey + "pubkey_f", default_path_publickey + "pubkey_eggalpha");
                        PairingKeySerParameter secretKey = cpabe.getsecretkey(default_path_secretkey + "secretkey_Parameters",
                                default_path_secretkey + "secretkey_D", default_path_secretkey + "secretkey_D1s",
                                default_path_secretkey + "secretkey_D2s", default_path_secretkey + "secretkey_attributes");
                        PairingCipherSerParameter header = cpabe.getheader(default_path_header + "header_Parameters",
                                default_path_header + "header_C", default_path_header + "header_C1s",
                                default_path_header + "header_C2s", default_path_header + "header_Crhos");
                        message = cpabe.Deryption(publicKey, attribute, secretKey, header, default_path_ciphertext + "ciphertext1");
                        TextView message_view = findViewById(R.id.message_view);
                        message_view.setText(message);
                    }
                });

//                File file = new File("/data/data/com.example.trytolearn/files/orderid");
//                FileWriter fw = null;
//                try {
//                    fw = new FileWriter(file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                BufferedWriter bw = new BufferedWriter(fw);
//                try {
//                    bw.write(code + '\n');
//                    bw.close();
//                    fw.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }


}