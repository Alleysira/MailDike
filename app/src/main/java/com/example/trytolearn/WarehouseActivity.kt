package com.example.trytolearn

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_warehouse.*


class WarehouseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse)
        Putin.setOnClickListener() {
            val intent2 = Intent(this, RealMyScanActivity::class.java)
            startActivity(intent2)
            Toast.makeText(this, "开始入库", Toast.LENGTH_SHORT).show()
        }
        Output.setOnClickListener() {
            val intent2 = Intent(this, RealMyScanActivity::class.java)
            startActivity(intent2)
            Toast.makeText(this, "开始出库", Toast.LENGTH_SHORT).show()
        }
        Search_personalinfo.setOnClickListener() {
            val intent2 = Intent(this, PersonalinfoActivity::class.java)
            startActivity(intent2)
            Toast.makeText(this, "查询个人信息", Toast.LENGTH_SHORT).show()
        }

    }

}