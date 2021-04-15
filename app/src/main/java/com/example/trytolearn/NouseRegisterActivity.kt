package com.example.trytolearn

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter

class NouseRegisterActivity : AppCompatActivity() {
    var mLocationClient: LocationClient? = null
    var positionText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mLocationClient = LocationClient(applicationContext)
        //注册定位监听器接口
        mLocationClient!!.registerLocationListener(MyLocationListener())

        setContentView(R.layout.activity_location)
        // 建立权限数组，动态申请
        positionText = findViewById<View>(R.id.position_text_view) as TextView

        val permissionList: MutableList<String> = ArrayList()
        if (ContextCompat.checkSelfPermission(
                        this, android.Manifest.permission.ACCESS_FINE_LOCATION

                ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE)
        }
        if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!permissionList.isEmpty()) {
            val permissions = permissionList.toTypedArray()
            ActivityCompat.requestPermissions(this, permissions, 1)
        } else {
            requestLocation()
        }
    }

    private fun requestLocation() {
        mLocationClient!!.start()//有权限则进行定位
    }

    //处理回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults.size > 0) {
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(
                                this, "必须同意所有权限才能使用本程序",
                                Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        return
                    }
                }
                requestLocation()
            } else {
                Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show()
                finish()
            }
            else -> {
            }
        }
    }

    inner class MyLocationListener : BDLocationListener {
        override fun onReceiveLocation(p0: BDLocation?) {
            runOnUiThread(Runnable {
                val currentPosition = StringBuilder()
                if (p0 != null) {

                    currentPosition.append("纬度： ").append(p0.getLatitude()).append("\n")
                    currentPosition.append("经度： ").append(p0.getLongitude()).append("\n")
                    currentPosition.append("定位方式： ")
                    if (p0.getLocType() === BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS")
                    } else if (p0.getLocType() ===
                            BDLocation.TypeNetWorkLocation
                    ) {
                        currentPosition.append("网络")
                    }
                }
                positionText?.setText(currentPosition)
            })
        }


        private fun save(inputText: String) {
            try {
                val output = openFileOutput("Location_cpabe", Context.MODE_PRIVATE)
                val writer = BufferedWriter(OutputStreamWriter(output))
                writer.use { it.write(inputText) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

}


