package com.example.trytolearn.user.info;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trytolearn.R;

import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        change_info();
    }


    private void change_info() {
        //change account into sender
        SharedPreferences reader = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String test = reader.getString("account", "");
        TextView id = getActivity().findViewById(R.id.user_account);
        id.setText(test);


        //change name into sender
        SharedPreferences reader1 = getActivity().getSharedPreferences("sender", MODE_PRIVATE);
        String string_name = reader1.getString("sName", "");
        TextView name = getActivity().findViewById(R.id.name);
        name.setText(string_name);

        //change address into receiver
        SharedPreferences reader2 = getActivity().getSharedPreferences("receiver", MODE_PRIVATE);
        String add = reader2.getString("rPro", "");
        add += reader2.getString("rCity", "");
        add += reader2.getString("rArea", "");
        TextView place = getActivity().findViewById(R.id.mail_station);
        place.setText(add);

        //change tel into receiver
        TextView tel = getActivity().findViewById(R.id.user_tel);
        tel.setText(reader2.getString("rPhone", ""));
    }


}