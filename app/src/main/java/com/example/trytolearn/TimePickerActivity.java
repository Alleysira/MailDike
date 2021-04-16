package com.example.trytolearn;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trytolearn.Time.TimeRangePickerDialog;

public class TimePickerActivity extends AppCompatActivity {
    TextView tvPushTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_time_picker);
        TextView tvPushTime = findViewById(R.id.tvPushTime);
        findViewById(R.id.setTimeLayout).setOnClickListener(view -> {
            TimeRangePickerDialog dialog =
                    new TimeRangePickerDialog(TimePickerActivity.this, tvPushTime.getText().toString(), new TimeRangePickerDialog.ConfirmAction() {
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
    }
}
