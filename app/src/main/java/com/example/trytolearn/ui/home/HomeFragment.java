package com.example.trytolearn.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


        TextView receiver = getActivity().findViewById(R.id.select_receiver);
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
                b.setClass(getActivity(), ReceiverFormActivity.class);
                startActivity(b);
            }
        });
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


    }


}