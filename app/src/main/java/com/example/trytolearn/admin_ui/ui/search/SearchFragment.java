package com.example.trytolearn.admin_ui.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trytolearn.R;

public class SearchFragment extends Fragment {

    private SearchViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        notificationsViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebView webView = getActivity().findViewById(R.id.search_back);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://10.136.97.196:8080/waybill");
        webView.setWebViewClient(new WebViewClient());
    }

}
