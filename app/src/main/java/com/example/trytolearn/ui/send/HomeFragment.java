package com.example.trytolearn.ui.send;

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

import com.example.trytolearn.R;
import com.example.trytolearn.ReceiverFormActivity;
import com.example.trytolearn.SenderFormActivity;
import com.example.trytolearn.Time.TimeRangePickerDialog;

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
                Toast.makeText(getActivity(), "上传成功！", Toast.LENGTH_SHORT).show();

            }
        });
    }


}