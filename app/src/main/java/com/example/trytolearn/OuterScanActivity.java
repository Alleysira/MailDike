package com.example.trytolearn;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.yxing.ScanCodeActivity;

public class OuterScanActivity extends ScanCodeActivity {

    private AppCompatButton btnOpenFlash;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myscan;
    }

    @Override
    public void initData() {
        super.initData();
        btnOpenFlash = findViewById(R.id.btn_openflash);

        btnOpenFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OuterScanActivity.this, "打开闪光灯", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
