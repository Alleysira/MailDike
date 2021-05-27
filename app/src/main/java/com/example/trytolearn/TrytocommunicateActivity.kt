package com.example.trytolearn

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.DownloadListener
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_trytocommunicate.*

class TrytocommunicateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trytocommunicate)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://192.168.137.225:8080/code/de?id=111&location=114.172%20and%2022.724")
//        webView.setDownloadListener(object : DownloadListener {
//            override fun onDownloadStart(
//                    url: String?,
//                    userAgent: String?,
//                    contentDisposition: String?,
//                    mimeType: String?,
//                    contentLength: Long
//            ) {
//                // 处理下载事件
//                if (!url.isNullOrEmpty()) {
//                    downloadByBrowser(url)
//                }
//            }
//        })
    }

    private fun downloadByBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }


}