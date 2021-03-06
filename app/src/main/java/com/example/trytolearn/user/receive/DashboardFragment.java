package com.example.trytolearn.user.receive;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trytolearn.R;
import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.yxing.ScanCodeActivity;
import com.yxing.ScanCodeConfig;
import com.yxing.def.ScanStyle;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startScan(ScanStyle.WECHAT, ScanCodeActivity.class);
//        SharedPreferences reader = getActivity().getSharedPreferences("QRCode",0);
//        String code = reader.getString("code1","");
//        if(!code.equals("")){
//            TextView tv = getActivity().findViewById(R.id.code);
//            tv.setText(code);
//        }
    }

    public void startScan(int style, Class mClass) {
        new RxPermissions(this)
                .requestEachCombined(Manifest.permission.CAMERA)
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Permission permission) {
                        if (permission.granted) {
                            ScanCodeConfig.create(getActivity())
                                    //????????????????????? ScanStyle.NONE??????  ScanStyle.QQ ??????QQ??????   ScanStyle.WECHAT ??????????????????
                                    .setStyle(ScanStyle.QQ)
                                    //??????????????????????????????  true ??? ??????   false ??? ?????????
                                    .setPlayAudio(true)
                                    .buidler()
                                    //???????????????   ???????????????????????????
                                    .start(mClass);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }

                });
    }


}