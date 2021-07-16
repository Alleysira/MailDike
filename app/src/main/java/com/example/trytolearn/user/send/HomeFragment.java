package com.example.trytolearn.user.send;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trytolearn.CPABEAPP.CPABE;
import com.example.trytolearn.R;
import com.example.trytolearn.RSAUtils;
import com.example.trytolearn.ReceiverFormActivity;
import com.example.trytolearn.SenderFormActivity;
import com.example.trytolearn.Time.TimeRangePickerDialog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView tvPushTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView a = getActivity().findViewById(R.id.please_select);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.setClass(getActivity(), SenderFormActivity.class);
                startActivity(b);
            }
        });

        //get the already entered info
        SharedPreferences reader = getActivity().getSharedPreferences("sender", 0);
        String sname = reader.getString("sName", "");
        String sphone = reader.getString("sPhone", "");
        String sAddress = reader.getString("sAddress", "");
        String item_type = reader.getString("item_type", "日用品");
        String item_weight = reader.getString("item_weight", "0");

        if (!item_type.equals("日用品")) {
            TextView kind = getActivity().findViewById(R.id.kind);
            kind.setText(item_type);
        }

        if (!item_weight.equals("")) {
            TextView weight = getActivity().findViewById(R.id.weight);
            weight.setText(item_weight + "公斤");
        }

        if (!sname.equals("") && !sphone.equals("") && !sAddress.equals("")) {
            a.setHint(sname + " " + sphone + " " + sAddress + "\n点击更改");
        }


        TextView receiver = getActivity().findViewById(R.id.select_receiver);
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.setClass(getActivity(), ReceiverFormActivity.class);
                startActivity(b);
            }
        });

        //get the already entered info
        SharedPreferences reader2 = getActivity().getSharedPreferences("receiver", 0);
        String rname = reader2.getString("rName", "");
        String rphone = reader2.getString("rPhone", "");
        String rAddress = reader2.getString("rAddress", "");
        if (!rname.equals("") && !rphone.equals("") && !rAddress.equals("")) {
            receiver.setHint(rname + " " + rphone + " " + rAddress + "\n点击更改");
        }

        TextView tvPushTime = getActivity().findViewById(R.id.tvPushTime);
        getActivity().findViewById(R.id.setTimeLayout).setOnClickListener(view -> {
            TimeRangePickerDialog dialog =
                    new TimeRangePickerDialog(getActivity(), tvPushTime.getText().toString(), new TimeRangePickerDialog.ConfirmAction() {
                        @Override
                        public void onLeftClick() {
                        }

                        @Override
                        public void onRightClick(String startAndEndTime) {
                            tvPushTime.setText(startAndEndTime);
                        }
                    });
            dialog.show();
        });


        Button up = getActivity().findViewById(R.id.ime);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSec();
                Toast.makeText(getActivity(), "上传成功！", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public String getHn() {
        Random seed = new Random();
        int r = seed.nextInt();
        String rn = Integer.toString(r);
        int RN = rn.hashCode();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("receiver", MODE_PRIVATE).edit();
        String HN = Integer.toString(RN);
        editor.putString("RN", rn);
        editor.putString("HN", HN);
        editor.apply();
        return HN;
    }

    public void cpabe_encrypt(String message) {
        String default_path_header = "/data/data/com.example.trytolearn/files/my";
        String default_path_publickey = "/data/data/com.example.trytolearn/files/";
        String default_path_ciphertext = "/data/data/com.example.trytolearn/files/";
        CPABE cpabe = new CPABE();
        String attribute = "116.350" + " and "
                + "9.983";
        PairingKeySerParameter publicKey = cpabe.getpublickey(default_path_publickey + "pubkey_p", default_path_publickey + "pubkey_g",
                default_path_publickey + "pubkey_h", default_path_publickey + "pubkey_f", default_path_publickey + "pubkey_eggalpha");
        cpabe.Encryption(publicKey, attribute, message,
                default_path_header + "header_Parameters", default_path_header + "header_C",
                default_path_header + "header_Crhos", default_path_header + "header_C1s",
                default_path_header + "header_C2s", default_path_ciphertext + "ciphertext2");
    }


    public String readFormFile(String name) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = getActivity().openFileInput(name);
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

    public String getHeader(String name) {
        String header = readFormFile(name);
        return "my" + header;
    }

    public void postSec() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences my_sender = getActivity().getSharedPreferences("sender", MODE_PRIVATE);
                    SharedPreferences my_receiver = getActivity().getSharedPreferences("receiver", MODE_PRIVATE);


                    //build sec2
                    String message = "";
                    message += my_sender.getString("sAddress", "") + "\n";
                    message += my_receiver.getString("rAddress", "") + "\n";
                    message += getHn() + "\n";
                    message += my_sender.getString("sPhone", "") + "\n";
                    message += my_receiver.getString("rPhone", "") + "\n";
                    cpabe_encrypt(message);

                    String cp_cipher = readFormFile("ciphertext2");

                    //build sec3


                    String msg2 = "";
                    //msg2 contains signature  both names and phone numbers
                    msg2 += my_sender.getString("sName", "") + "\n";
                    msg2 += my_sender.getString("sPhone", "") + "\n";
                    msg2 += my_receiver.getString("rName", "") + "\n";
                    msg2 += my_receiver.getString("rPhone", "") + "\n";

                    //build rsa keys
                    SharedPreferences.Editor editor_key = getActivity().getSharedPreferences("rsakey", MODE_PRIVATE).edit();
                    String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDTJK2H-siKcVJDPmNL9iDeAUCI2kHMp4SkZJb8EXqPpbQIVGTQjqwTD7H2Lt0XLgLkZgldLzMleGER7sXGCyPTpzjH8Xcm_QhUXGbH8aV9zju3KkNHPGSSW58jNwfGW9R5K75Px4zzt0NlHCa1k7Du5YHVbyy7xuodlrOLOKSNnnlKys4Rkj_sHnnECMerqe7OiKdFAEkBjEQ9FTXQ9F-PC761bvRIGE23fU1b2z5wyZ-2tCcEDl0mm4HkDLaWoQeQN1bQMs6GgTVaMJWsAM145O4wy9e6irbraoTTNLQFANdEmX-O2u82wp-_6xHQxQYLldUgMJMJJ_z7uNJ9fGzlAgMBAAECggEBAKA8jZVMtTSbm5p8lk7nkznoKVmyiY0O9JLt48eYXDL3xhEsOvYr0FsG5j-2gQM-X-OFrEWTtHUTTleVpXIOsjnGBkl27r4f-VpMZZQHx1gaUydGY5iENK796V-IO9ZFipIKqHtTLZ0nz3XET3wtgXFLq6SWopQ--R1LaSaW7gqqgLZGZb6EQmil3JOkEBvV1zWmHcoYUY9gKDYboyU0pn5Jli6R3GezF1IcmSdN7wXuVTCYCQBImIIfOFJ7VotumWiDkcP6oIAjS8g2g017ad52iCoOLTc4ki0s86rptrsR7tQVm17F2SPMbRzcGCa88EQpDVfOLLkW0FZ3kyTY-QECgYEA8P1_8MXapHVFJo2bRwf8UBtUkH4toaQn7USSpWX6yGQ1KsjwlTZ-qvYzyArv-7zOHHMvswL96WGMjwG9urT_4FqtkNMCAL8fAcfeOojsUpp61b2S-K-vnrXqscRiPlT2FhxZEW45zARY2o-a_vhyLLYeSiqcxUPCyJ-62SOH65kCgYEA4EtHgiUVHc6Dpi1oFsXTKHYxOuNeWZUOEz4pksK5n3hVbCVG7mY46kDgNpsgGYwDR3rKsZAQsfmPnZjJun96LKYsiTam1h-mAB93ULLWQ6PmNhhFEENHwSvo0pCA1m4ShvvW77s-jyfX1TM67H6H2XOkql-Mx8NReSO-gucB-y0CgYEAqRVT1P0dANJ-6CPm1JmXwCTM2myNW6IvmVvJgF7i7ALTAuflVOvdR9piTnLOGlRIUNHIn9Lzj_Gviw7vrbYc6a71pG1INHnkKX2wQGWdWf-lO549Jlst3y9IMd3WCGHYH39YRtCNoMVUClVDrK1oflJxQhxPzmBSpCzeDkfNr9ECgYAEGnx5bhI_1FpmPOhtmjrtv5PQ_v3n56k1Qurhy3w35ayyaNAuZmJeLserWBUzQnOA6EczDm6vwuAUwwnVxqVGkde4vu44dqXD1M-LA2qWHDaHANSqooB6kUIWMBybT7I5E_xvsF5JojH5rZFDaGE14j69-zeJJBKmg7fljzJjFQKBgHC731QM3tfz58JQCdzydwtsLnqKjkdADECYScT-q2IdnH04BcBPZxyeriT_EpXfjbDeCLVrpQWvCDWiLtpfSyRB1fAI8FAH-NWw1fthy9vgQE2ncBA8vKSXKhi3joH6fSFfZJq-oMAOqxUJnKAKOHqozvYjUVtfDeaQclbLOMf3";
                    String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0ySth_rIinFSQz5jS_Yg3gFAiNpBzKeEpGSW_BF6j6W0CFRk0I6sEw-x9i7dFy4C5GYJXS8zJXhhEe7Fxgsj06c4x_F3Jv0IVFxmx_Glfc47typDRzxkklufIzcHxlvUeSu-T8eM87dDZRwmtZOw7uWB1W8su8bqHZazizikjZ55SsrOEZI_7B55xAjHq6nuzoinRQBJAYxEPRU10PRfjwu-tW70SBhNt31NW9s-cMmftrQnBA5dJpuB5Ay2lqEHkDdW0DLOhoE1WjCVrADNeOTuMMvXuoq262qE0zS0BQDXRJl_jtrvNsKfv-sR0MUGC5XVIDCTCSf8-7jSfXxs5QIDAQAB";
                    String signature = RSAUtils.publicEncrypt("BUAA", RSAUtils.getPublicKey(publicKey));  //传入明文和公钥加密,得到密文
                    editor_key.putString("rsapublickey", publicKey);
                    editor_key.putString("rsaprivateKey", privateKey);
                    editor_key.apply();

                    msg2 += signature + "\n";
                    msg2 += my_receiver.getString("RN", "");
                    String section3 = RSAUtils.publicDecrypt(msg2, RSAUtils.getPublicKey(publicKey));

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("sec", MODE_PRIVATE).edit();


                    String rsadecrypt = RSAUtils.privateDecrypt(signature, RSAUtils.getPrivateKey(privateKey));
                    editor.putString("rsaencrypt", signature);
                    editor.putString("rsadecrypt", rsadecrypt);
                    editor.apply();


                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("section2", cp_cipher)
                            .add("section3", section3)
                            .add("sPro", my_sender.getString("sPro", ""))
                            .add("sCity", my_sender.getString("sCity", ""))
                            .add("sArea", my_sender.getString("sArea", ""))
                            .add("item_type", my_sender.getString("item_type", ""))
                            .add("item_weight", my_sender.getString("item_weight", ""))
                            .add("rPro", my_receiver.getString("rPro", ""))
                            .add("rCity", my_receiver.getString("rCity", ""))
                            .add("rArea", my_receiver.getString("rArea", ""))
                            .add("rec_header_Parameters", getHeader("header_Parameters"))
                            .add("rec_header_C", getHeader("header_C"))
                            .add("rec_header_Crhos", getHeader("header_Crhos"))
                            .add("rec_header_C1s", getHeader("header_C1s"))
                            .add("rec_header_C2s", getHeader("header_C2s"))
                            .build();

                    Request postRequest = new Request.Builder()
                            .url("http://10.135.170.15:8080/waybill/android")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(postRequest).execute();
//                    String requestdata = response.body().string();
//                    showResponse(requestdata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


//    public void postSec1to3() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SharedPreferences my_sender = getActivity().getSharedPreferences("sender", MODE_PRIVATE);
//                    SharedPreferences my_receiver = getActivity().getSharedPreferences("receiver", MODE_PRIVATE);
//                    SharedPreferences order = getActivity().getSharedPreferences("order",MODE_PRIVATE);
//                    OkHttpClient client = new OkHttpClient();
//
//                    String sec1 = "";
//                    String sec2 = "";
//                    String sec3 = "";
//
//                    String sec1_sAddress = my_sender.getString("sPro","")+my_sender.getString("sCity","")+my_sender.getString("sArea","");
//                    String sec1_rAddress = my_sender.getString("rPro","")+my_sender.getString("rCity","")+my_sender.getString("rArea","");
//
//                    String sec2_rn = getRn();
//
////                    String sec_order = order.getString("");
//
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("sec1",sec1)
//                            .add("sec2",sec2)
//                            .add("sec3",sec3)
//                            .build();
//
//                    //"http://10.136.97.196:8080//waybill/android"192.168.43.7
//                    Request post_request = new Request.Builder()
//                            .url("http://10.136.97.196:8080/waybill/android")
//                            .post(requestBody)
//                            .build();
//                    Response response = client.newCall(post_request).execute();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }


}