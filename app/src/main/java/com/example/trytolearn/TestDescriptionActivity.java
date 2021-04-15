package com.example.trytolearn;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trytolearn.CPABEAPP.CPABE;

import cn.edu.buaa.crypto.algebra.serparams.PairingCipherSerParameter;
import cn.edu.buaa.crypto.algebra.serparams.PairingKeySerParameter;

public class TestDescriptionActivity extends AppCompatActivity {
    private static final String default_path_header = "/data/data/com.example.trytolearn/files/";
    private static final String default_path_publickey = "/data/data/com.example.trytolearn/files/";
    private static final String default_path_secretkey = "/data/data/com.example.trytolearn/files/";
    private static final String default_path_ciphertext = "/data/data/com.example.trytolearn/files/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_description);
        CPABE cpabe = new CPABE();
        String attribute = "Beijing and HD and BUAA";
        String message = "Pass";
        PairingKeySerParameter publicKey = cpabe.getpublickey(default_path_publickey + "pubkey_p", default_path_publickey + "pubkey_g",
                default_path_publickey + "pubkey_h", default_path_publickey + "pubkey_f", default_path_publickey + "pubkey_eggalpha");
        PairingKeySerParameter secretKey = cpabe.getsecretkey(default_path_secretkey + "secretkey_Parameters",
                default_path_secretkey + "secretkey_D", default_path_secretkey + "secretkey_D1s",
                default_path_secretkey + "secretkey_D2s", default_path_secretkey + "secretkey_attributes");
        PairingCipherSerParameter header = cpabe.getheader(default_path_header + "header_Parameters",
                default_path_header + "header_C", default_path_header + "header_C1s",
                default_path_header + "header_C2s", default_path_header + "header_Crhos");
        message = cpabe.Deryption(publicKey, attribute, secretKey, header, default_path_ciphertext + "ciphertext1");
        TextView message_view = findViewById(R.id.message_test);
        message_view.setText(message);
    }

}