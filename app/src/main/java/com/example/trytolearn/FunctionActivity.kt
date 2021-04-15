package com.example.trytolearn

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class FunctionActivity : AppCompatActivity() {
    fun createFolder(path: String?) {
        val file = File(path)
        if (!file.exists()) { //如果文件夹不存在
            file.mkdir() //创建文件夹
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        //test file
        val default_path_benchmarks = "/data/data/com.example.trytolearn/files/benchmarks/"
        val default_path_encryption = "/data/data/com.example.trytolearn/files/benchmarks/encryption/"
        val default_path_cpabe = "/data/data/com.example.trytolearn/files/benchmarks/encryption/cpabe/"
        val default_path_header = "/data/data/com.example.trytolearn/files/benchmarks/encryption/cpabe/header/"
        val default_path_publickey = "/data/data/com.example.trytolearn/files/benchmarks/encryption/cpabe/publickey/"
        val default_path_masterkey = "/data/data/com.example.trytolearn/files/benchmarks/encryption/cpabe/masterkey/"
        val default_path_secretkey = "/data/data/com.example.trytolearn/files/benchmarks/encryption/cpabe/secretkey/"
        val default_path_ciphertext = "/data/data/com.example.trytolearn/files/benchmarks/encryption/cpabe/ciphertext/"
        val default_path_message = "/data/data/com.example.trytolearn/files/benchmarks/encryption/cpabe/message/"
        //
        Test_solve.setOnClickListener() {
            val intent = Intent(this, TestDescriptionActivity::class.java)
            startActivity(intent)
        }
        //button_connect
        Connect_button.setOnClickListener() {
            val intent2 = Intent(this, TrytocommunicateActivity::class.java)
            startActivity(intent2)
        }
        //build file
        createFolder(default_path_benchmarks)
        createFolder(default_path_encryption)
        createFolder(default_path_cpabe)
        createFolder(default_path_header)
        createFolder(default_path_publickey)
        createFolder(default_path_secretkey)
        createFolder(default_path_ciphertext)
        createFolder(default_path_message)
        createFolder(default_path_masterkey)
        //
        fun save_file() {
            save("ciphertext1", "XsN/Tk1K9C/oxeEKnxWUaA==")
            save(
                    "header_C", "125\n" +
                    "-116\n" +
                    "-47\n" +
                    "89\n" +
                    "-57\n" +
                    "46\n" +
                    "76\n" +
                    "112\n" +
                    "92\n" +
                    "-13\n" +
                    "-121\n" +
                    "101\n" +
                    "-48\n" +
                    "-83\n" +
                    "-1\n" +
                    "107\n" +
                    "57\n" +
                    "-53\n" +
                    "-100\n" +
                    "77\n" +
                    "-100\n" +
                    "-56\n" +
                    "-31\n" +
                    "73\n" +
                    "85\n" +
                    "-29\n" +
                    "80\n" +
                    "56\n" +
                    "60\n" +
                    "-46\n" +
                    "85\n" +
                    "81\n" +
                    "51\n" +
                    "40\n" +
                    "-103\n" +
                    "47\n" +
                    "29\n" +
                    "26\n" +
                    "-28\n" +
                    "20\n" +
                    "20\n" +
                    "33\n" +
                    "-119\n" +
                    "92\n" +
                    "4\n" +
                    "44\n" +
                    "97\n" +
                    "41\n" +
                    "-99\n" +
                    "-113\n" +
                    "-40\n" +
                    "-5\n" +
                    "-21\n" +
                    "116\n" +
                    "-29\n" +
                    "48\n" +
                    "62\n" +
                    "91\n" +
                    "-36\n" +
                    "82\n" +
                    "-92\n" +
                    "-124\n" +
                    "116\n" +
                    "-16\n"
            )
            save(
                    "header_C1s", "102\n" +
                    "-111\n" +
                    "14\n" +
                    "-107\n" +
                    "17\n" +
                    "-41\n" +
                    "6\n" +
                    "-18\n" +
                    "-89\n" +
                    "26\n" +
                    "24\n" +
                    "-37\n" +
                    "20\n" +
                    "76\n" +
                    "-125\n" +
                    "-115\n" +
                    "52\n" +
                    "126\n" +
                    "-127\n" +
                    "-15\n" +
                    "57\n" +
                    "-89\n" +
                    "-118\n" +
                    "99\n" +
                    "-1\n" +
                    "15\n" +
                    "51\n" +
                    "-37\n" +
                    "109\n" +
                    "-19\n" +
                    "36\n" +
                    "107\n" +
                    "113\n" +
                    "28\n" +
                    "45\n" +
                    "52\n" +
                    "-85\n" +
                    "50\n" +
                    "-71\n" +
                    "92\n" +
                    "73\n" +
                    "-73\n" +
                    "6\n" +
                    "-24\n" +
                    "-79\n" +
                    "115\n" +
                    "-44\n" +
                    "75\n" +
                    "21\n" +
                    "36\n" +
                    "-5\n" +
                    "-8\n" +
                    "100\n" +
                    "-43\n" +
                    "30\n" +
                    "21\n" +
                    "-99\n" +
                    "-7\n" +
                    "127\n" +
                    "-5\n" +
                    "48\n" +
                    "103\n" +
                    "-71\n" +
                    "-35\n" +
                    "next\n" +
                    "113\n" +
                    "73\n" +
                    "103\n" +
                    "-26\n" +
                    "-99\n" +
                    "61\n" +
                    "-45\n" +
                    "12\n" +
                    "-27\n" +
                    "51\n" +
                    "-79\n" +
                    "23\n" +
                    "-21\n" +
                    "-107\n" +
                    "-101\n" +
                    "-40\n" +
                    "-72\n" +
                    "-94\n" +
                    "82\n" +
                    "74\n" +
                    "7\n" +
                    "109\n" +
                    "18\n" +
                    "-100\n" +
                    "24\n" +
                    "-37\n" +
                    "12\n" +
                    "-77\n" +
                    "-95\n" +
                    "-5\n" +
                    "93\n" +
                    "-81\n" +
                    "19\n" +
                    "-13\n" +
                    "83\n" +
                    "-62\n" +
                    "79\n" +
                    "-38\n" +
                    "23\n" +
                    "-24\n" +
                    "2\n" +
                    "43\n" +
                    "126\n" +
                    "75\n" +
                    "78\n" +
                    "-75\n" +
                    "80\n" +
                    "-60\n" +
                    "-128\n" +
                    "94\n" +
                    "103\n" +
                    "29\n" +
                    "38\n" +
                    "-4\n" +
                    "91\n" +
                    "-31\n" +
                    "99\n" +
                    "56\n" +
                    "110\n" +
                    "85\n" +
                    "-47\n" +
                    "-120\n" +
                    "27\n" +
                    "-14\n" +
                    "next\n" +
                    "-108\n" +
                    "22\n" +
                    "-89\n" +
                    "-45\n" +
                    "101\n" +
                    "36\n" +
                    "-98\n" +
                    "-57\n" +
                    "44\n" +
                    "-7\n" +
                    "-17\n" +
                    "-46\n" +
                    "-47\n" +
                    "-118\n" +
                    "40\n" +
                    "-109\n" +
                    "-71\n" +
                    "-75\n" +
                    "68\n" +
                    "-54\n" +
                    "-121\n" +
                    "118\n" +
                    "10\n" +
                    "65\n" +
                    "27\n" +
                    "93\n" +
                    "4\n" +
                    "47\n" +
                    "32\n" +
                    "33\n" +
                    "51\n" +
                    "-55\n" +
                    "-107\n" +
                    "-12\n" +
                    "-119\n" +
                    "-55\n" +
                    "-81\n" +
                    "-22\n" +
                    "42\n" +
                    "45\n" +
                    "113\n" +
                    "63\n" +
                    "11\n" +
                    "115\n" +
                    "-109\n" +
                    "12\n" +
                    "57\n" +
                    "45\n" +
                    "119\n" +
                    "41\n" +
                    "-11\n" +
                    "5\n" +
                    "108\n" +
                    "-18\n" +
                    "45\n" +
                    "11\n" +
                    "-73\n" +
                    "47\n" +
                    "119\n" +
                    "109\n" +
                    "-40\n" +
                    "5\n" +
                    "-78\n" +
                    "-64\n" +
                    "next\n"
            )
            save(
                    "header_C2s", "53\n" +
                    "-60\n" +
                    "16\n" +
                    "-92\n" +
                    "-61\n" +
                    "94\n" +
                    "4\n" +
                    "-124\n" +
                    "-90\n" +
                    "-120\n" +
                    "-54\n" +
                    "-65\n" +
                    "83\n" +
                    "-44\n" +
                    "-67\n" +
                    "-14\n" +
                    "106\n" +
                    "61\n" +
                    "-44\n" +
                    "-51\n" +
                    "84\n" +
                    "-115\n" +
                    "113\n" +
                    "-93\n" +
                    "65\n" +
                    "-24\n" +
                    "119\n" +
                    "-28\n" +
                    "-21\n" +
                    "-56\n" +
                    "-36\n" +
                    "-32\n" +
                    "-98\n" +
                    "-26\n" +
                    "-19\n" +
                    "-108\n" +
                    "61\n" +
                    "22\n" +
                    "57\n" +
                    "75\n" +
                    "68\n" +
                    "-118\n" +
                    "18\n" +
                    "1\n" +
                    "7\n" +
                    "-44\n" +
                    "-11\n" +
                    "-109\n" +
                    "-124\n" +
                    "-72\n" +
                    "-69\n" +
                    "47\n" +
                    "47\n" +
                    "20\n" +
                    "99\n" +
                    "-94\n" +
                    "-64\n" +
                    "-82\n" +
                    "-105\n" +
                    "-124\n" +
                    "-107\n" +
                    "10\n" +
                    "-109\n" +
                    "-57\n" +
                    "next\n" +
                    "-104\n" +
                    "-70\n" +
                    "14\n" +
                    "-69\n" +
                    "0\n" +
                    "-89\n" +
                    "22\n" +
                    "47\n" +
                    "-89\n" +
                    "86\n" +
                    "76\n" +
                    "-93\n" +
                    "88\n" +
                    "-32\n" +
                    "-81\n" +
                    "80\n" +
                    "-82\n" +
                    "-74\n" +
                    "23\n" +
                    "-13\n" +
                    "96\n" +
                    "-30\n" +
                    "100\n" +
                    "-29\n" +
                    "-33\n" +
                    "-24\n" +
                    "6\n" +
                    "-112\n" +
                    "-23\n" +
                    "-103\n" +
                    "-54\n" +
                    "-104\n" +
                    "18\n" +
                    "12\n" +
                    "-121\n" +
                    "61\n" +
                    "-106\n" +
                    "51\n" +
                    "36\n" +
                    "-7\n" +
                    "10\n" +
                    "-18\n" +
                    "-37\n" +
                    "22\n" +
                    "-37\n" +
                    "32\n" +
                    "17\n" +
                    "81\n" +
                    "40\n" +
                    "-121\n" +
                    "-35\n" +
                    "-96\n" +
                    "-127\n" +
                    "83\n" +
                    "-124\n" +
                    "73\n" +
                    "-106\n" +
                    "-26\n" +
                    "-106\n" +
                    "64\n" +
                    "-26\n" +
                    "-16\n" +
                    "90\n" +
                    "59\n" +
                    "next\n" +
                    "-98\n" +
                    "119\n" +
                    "-96\n" +
                    "-20\n" +
                    "5\n" +
                    "-102\n" +
                    "-65\n" +
                    "122\n" +
                    "45\n" +
                    "105\n" +
                    "-85\n" +
                    "-98\n" +
                    "80\n" +
                    "-8\n" +
                    "-8\n" +
                    "53\n" +
                    "-88\n" +
                    "98\n" +
                    "-113\n" +
                    "-120\n" +
                    "85\n" +
                    "87\n" +
                    "-6\n" +
                    "-70\n" +
                    "-44\n" +
                    "-100\n" +
                    "43\n" +
                    "-56\n" +
                    "40\n" +
                    "123\n" +
                    "-66\n" +
                    "62\n" +
                    "75\n" +
                    "60\n" +
                    "-61\n" +
                    "-26\n" +
                    "35\n" +
                    "-102\n" +
                    "49\n" +
                    "-18\n" +
                    "47\n" +
                    "-47\n" +
                    "-65\n" +
                    "61\n" +
                    "-28\n" +
                    "-25\n" +
                    "-30\n" +
                    "62\n" +
                    "100\n" +
                    "-119\n" +
                    "-3\n" +
                    "-79\n" +
                    "-42\n" +
                    "-51\n" +
                    "-104\n" +
                    "92\n" +
                    "83\n" +
                    "104\n" +
                    "50\n" +
                    "15\n" +
                    "-65\n" +
                    "56\n" +
                    "-41\n" +
                    "-104\n" +
                    "next\n"
            )
            save(
                    "header_Crhos", "BUAA\n" +
                    "Beijing\n" +
                    "HD\n"
            )
            save(
                    "header_Parameters", "type a\n" +
                    "q 81869981414486565817042987620009425916711137248094272342132238763687306328559\n" +
                    "r 604462909877683331530751\n" +
                    "h 135442522736512392410054892783912140655846630328108560\n" +
                    "exp1 46\n" +
                    "exp2 79\n" +
                    "sign0 -1\n" +
                    "sign1 1\n"
            )
            save(
                    "pubkey_eggalpha", "-85\n" +
                    "23\n" +
                    "-106\n" +
                    "47\n" +
                    "-41\n" +
                    "-21\n" +
                    "-64\n" +
                    "-125\n" +
                    "2\n" +
                    "-48\n" +
                    "87\n" +
                    "41\n" +
                    "-27\n" +
                    "90\n" +
                    "-11\n" +
                    "-7\n" +
                    "-42\n" +
                    "-118\n" +
                    "45\n" +
                    "87\n" +
                    "-109\n" +
                    "102\n" +
                    "27\n" +
                    "-92\n" +
                    "54\n" +
                    "44\n" +
                    "12\n" +
                    "-79\n" +
                    "-127\n" +
                    "31\n" +
                    "12\n" +
                    "79\n" +
                    "113\n" +
                    "79\n" +
                    "21\n" +
                    "-85\n" +
                    "-77\n" +
                    "31\n" +
                    "78\n" +
                    "-14\n" +
                    "-95\n" +
                    "10\n" +
                    "-100\n" +
                    "-113\n" +
                    "-99\n" +
                    "-122\n" +
                    "-36\n" +
                    "-46\n" +
                    "97\n" +
                    "9\n" +
                    "8\n" +
                    "-86\n" +
                    "-28\n" +
                    "74\n" +
                    "75\n" +
                    "-122\n" +
                    "-82\n" +
                    "86\n" +
                    "73\n" +
                    "-109\n" +
                    "47\n" +
                    "5\n" +
                    "69\n" +
                    "-67\n"
            )
            save(
                    "pubkey_f", "-122\n" +
                    "-105\n" +
                    "53\n" +
                    "-53\n" +
                    "-21\n" +
                    "-45\n" +
                    "-1\n" +
                    "-93\n" +
                    "64\n" +
                    "19\n" +
                    "-30\n" +
                    "-87\n" +
                    "4\n" +
                    "109\n" +
                    "-118\n" +
                    "56\n" +
                    "-67\n" +
                    "88\n" +
                    "-107\n" +
                    "-56\n" +
                    "-71\n" +
                    "57\n" +
                    "82\n" +
                    "101\n" +
                    "27\n" +
                    "121\n" +
                    "19\n" +
                    "39\n" +
                    "-41\n" +
                    "-25\n" +
                    "-93\n" +
                    "-13\n" +
                    "14\n" +
                    "59\n" +
                    "47\n" +
                    "-68\n" +
                    "116\n" +
                    "-48\n" +
                    "-115\n" +
                    "34\n" +
                    "3\n" +
                    "-33\n" +
                    "37\n" +
                    "-96\n" +
                    "-94\n" +
                    "79\n" +
                    "-12\n" +
                    "-72\n" +
                    "113\n" +
                    "7\n" +
                    "123\n" +
                    "1\n" +
                    "-29\n" +
                    "-128\n" +
                    "78\n" +
                    "-4\n" +
                    "60\n" +
                    "65\n" +
                    "48\n" +
                    "-32\n" +
                    "-5\n" +
                    "-22\n" +
                    "-62\n" +
                    "-101\n"
            )
            save(
                    "pubkey_g", "-92\n" +
                    "-103\n" +
                    "-101\n" +
                    "-103\n" +
                    "-118\n" +
                    "-11\n" +
                    "-10\n" +
                    "-99\n" +
                    "17\n" +
                    "-120\n" +
                    "-105\n" +
                    "-69\n" +
                    "24\n" +
                    "-70\n" +
                    "29\n" +
                    "-120\n" +
                    "-67\n" +
                    "37\n" +
                    "113\n" +
                    "-88\n" +
                    "95\n" +
                    "23\n" +
                    "-49\n" +
                    "-30\n" +
                    "24\n" +
                    "57\n" +
                    "1\n" +
                    "-65\n" +
                    "26\n" +
                    "26\n" +
                    "69\n" +
                    "84\n" +
                    "123\n" +
                    "32\n" +
                    "-38\n" +
                    "-36\n" +
                    "-124\n" +
                    "-15\n" +
                    "85\n" +
                    "-79\n" +
                    "-5\n" +
                    "95\n" +
                    "56\n" +
                    "27\n" +
                    "-19\n" +
                    "-109\n" +
                    "11\n" +
                    "-121\n" +
                    "-94\n" +
                    "-128\n" +
                    "86\n" +
                    "-32\n" +
                    "-75\n" +
                    "98\n" +
                    "1\n" +
                    "-65\n" +
                    "98\n" +
                    "-102\n" +
                    "97\n" +
                    "56\n" +
                    "-39\n" +
                    "-51\n" +
                    "-123\n" +
                    "5\n"
            )
            save(
                    "pubkey_h", "63\n" +
                    "74\n" +
                    "-119\n" +
                    "-59\n" +
                    "81\n" +
                    "1\n" +
                    "18\n" +
                    "124\n" +
                    "-127\n" +
                    "71\n" +
                    "-74\n" +
                    "84\n" +
                    "8\n" +
                    "107\n" +
                    "62\n" +
                    "28\n" +
                    "79\n" +
                    "-106\n" +
                    "76\n" +
                    "-11\n" +
                    "63\n" +
                    "-4\n" +
                    "-3\n" +
                    "-18\n" +
                    "41\n" +
                    "-32\n" +
                    "113\n" +
                    "92\n" +
                    "70\n" +
                    "110\n" +
                    "-27\n" +
                    "77\n" +
                    "-128\n" +
                    "60\n" +
                    "-1\n" +
                    "-70\n" +
                    "57\n" +
                    "122\n" +
                    "102\n" +
                    "-95\n" +
                    "-68\n" +
                    "119\n" +
                    "4\n" +
                    "-53\n" +
                    "-97\n" +
                    "-88\n" +
                    "106\n" +
                    "-3\n" +
                    "46\n" +
                    "-91\n" +
                    "-44\n" +
                    "-126\n" +
                    "81\n" +
                    "-46\n" +
                    "42\n" +
                    "-97\n" +
                    "-100\n" +
                    "-81\n" +
                    "-57\n" +
                    "-126\n" +
                    "-43\n" +
                    "-14\n" +
                    "120\n" +
                    "41\n"
            )
            save(
                    "pubkey_p", "type a\n" +
                    "q 81869981414486565817042987620009425916711137248094272342132238763687306328559\n" +
                    "r 604462909877683331530751\n" +
                    "h 135442522736512392410054892783912140655846630328108560\n" +
                    "exp1 46\n" +
                    "exp2 79\n" +
                    "sign0 -1\n" +
                    "sign1 1\n"
            )
            save(
                    "secretkey_attributes", "Beijing\n" +
                    "HD\n" +
                    "BUAA\n"
            )
            save(
                    "secretkey_D", "10\n" +
                    "38\n" +
                    "-22\n" +
                    "-35\n" +
                    "-90\n" +
                    "-54\n" +
                    "7\n" +
                    "20\n" +
                    "68\n" +
                    "-125\n" +
                    "-3\n" +
                    "106\n" +
                    "-9\n" +
                    "-35\n" +
                    "-81\n" +
                    "99\n" +
                    "-42\n" +
                    "122\n" +
                    "-25\n" +
                    "-108\n" +
                    "-70\n" +
                    "90\n" +
                    "45\n" +
                    "-13\n" +
                    "-69\n" +
                    "95\n" +
                    "-57\n" +
                    "31\n" +
                    "42\n" +
                    "87\n" +
                    "125\n" +
                    "-121\n" +
                    "48\n" +
                    "-118\n" +
                    "-101\n" +
                    "117\n" +
                    "58\n" +
                    "93\n" +
                    "-29\n" +
                    "66\n" +
                    "-37\n" +
                    "-66\n" +
                    "100\n" +
                    "32\n" +
                    "-40\n" +
                    "90\n" +
                    "103\n" +
                    "-52\n" +
                    "-103\n" +
                    "-69\n" +
                    "100\n" +
                    "-26\n" +
                    "-99\n" +
                    "-55\n" +
                    "-30\n" +
                    "79\n" +
                    "0\n" +
                    "-45\n" +
                    "-89\n" +
                    "-2\n" +
                    "-26\n" +
                    "104\n" +
                    "31\n" +
                    "82\n"
            )
            save(
                    "secretkey_D1s", "42\n" +
                    "93\n" +
                    "-109\n" +
                    "-29\n" +
                    "127\n" +
                    "43\n" +
                    "-68\n" +
                    "-80\n" +
                    "62\n" +
                    "97\n" +
                    "-13\n" +
                    "-23\n" +
                    "-96\n" +
                    "67\n" +
                    "20\n" +
                    "63\n" +
                    "103\n" +
                    "110\n" +
                    "-58\n" +
                    "-65\n" +
                    "105\n" +
                    "-37\n" +
                    "-58\n" +
                    "11\n" +
                    "16\n" +
                    "-60\n" +
                    "118\n" +
                    "114\n" +
                    "-109\n" +
                    "8\n" +
                    "-121\n" +
                    "33\n" +
                    "1\n" +
                    "20\n" +
                    "110\n" +
                    "-108\n" +
                    "-30\n" +
                    "-112\n" +
                    "25\n" +
                    "64\n" +
                    "-77\n" +
                    "32\n" +
                    "-52\n" +
                    "-6\n" +
                    "20\n" +
                    "-6\n" +
                    "-73\n" +
                    "-104\n" +
                    "122\n" +
                    "73\n" +
                    "1\n" +
                    "-83\n" +
                    "101\n" +
                    "-64\n" +
                    "1\n" +
                    "76\n" +
                    "44\n" +
                    "-46\n" +
                    "39\n" +
                    "12\n" +
                    "101\n" +
                    "-25\n" +
                    "71\n" +
                    "-3\n" +
                    "next\n" +
                    "105\n" +
                    "-124\n" +
                    "-60\n" +
                    "-71\n" +
                    "8\n" +
                    "60\n" +
                    "-92\n" +
                    "29\n" +
                    "-51\n" +
                    "-52\n" +
                    "62\n" +
                    "120\n" +
                    "116\n" +
                    "63\n" +
                    "-41\n" +
                    "-3\n" +
                    "-74\n" +
                    "-52\n" +
                    "104\n" +
                    "-12\n" +
                    "12\n" +
                    "-27\n" +
                    "-29\n" +
                    "98\n" +
                    "25\n" +
                    "-104\n" +
                    "106\n" +
                    "-120\n" +
                    "3\n" +
                    "64\n" +
                    "-16\n" +
                    "-2\n" +
                    "-113\n" +
                    "24\n" +
                    "59\n" +
                    "-117\n" +
                    "115\n" +
                    "-28\n" +
                    "-45\n" +
                    "-113\n" +
                    "109\n" +
                    "27\n" +
                    "30\n" +
                    "-79\n" +
                    "90\n" +
                    "59\n" +
                    "-67\n" +
                    "-21\n" +
                    "-118\n" +
                    "-64\n" +
                    "106\n" +
                    "-82\n" +
                    "126\n" +
                    "107\n" +
                    "90\n" +
                    "-72\n" +
                    "-82\n" +
                    "-30\n" +
                    "-28\n" +
                    "-82\n" +
                    "-104\n" +
                    "-73\n" +
                    "-7\n" +
                    "63\n" +
                    "next\n" +
                    "59\n" +
                    "-97\n" +
                    "110\n" +
                    "-93\n" +
                    "78\n" +
                    "-19\n" +
                    "80\n" +
                    "-97\n" +
                    "68\n" +
                    "43\n" +
                    "40\n" +
                    "51\n" +
                    "64\n" +
                    "-23\n" +
                    "-121\n" +
                    "92\n" +
                    "-10\n" +
                    "-10\n" +
                    "-57\n" +
                    "31\n" +
                    "55\n" +
                    "66\n" +
                    "19\n" +
                    "7\n" +
                    "73\n" +
                    "-5\n" +
                    "50\n" +
                    "32\n" +
                    "30\n" +
                    "83\n" +
                    "-47\n" +
                    "127\n" +
                    "47\n" +
                    "64\n" +
                    "53\n" +
                    "-11\n" +
                    "-27\n" +
                    "-85\n" +
                    "-102\n" +
                    "24\n" +
                    "12\n" +
                    "44\n" +
                    "-114\n" +
                    "-37\n" +
                    "25\n" +
                    "69\n" +
                    "-57\n" +
                    "114\n" +
                    "-76\n" +
                    "97\n" +
                    "-59\n" +
                    "42\n" +
                    "-21\n" +
                    "-12\n" +
                    "-85\n" +
                    "-82\n" +
                    "-9\n" +
                    "-86\n" +
                    "32\n" +
                    "105\n" +
                    "-66\n" +
                    "-30\n" +
                    "-101\n" +
                    "-101\n" +
                    "next\n"
            )
            save(
                    "secretkey_D2s", "67\n" +
                    "62\n" +
                    "-40\n" +
                    "37\n" +
                    "72\n" +
                    "-97\n" +
                    "-78\n" +
                    "82\n" +
                    "-27\n" +
                    "28\n" +
                    "-44\n" +
                    "124\n" +
                    "96\n" +
                    "116\n" +
                    "126\n" +
                    "-68\n" +
                    "42\n" +
                    "11\n" +
                    "127\n" +
                    "42\n" +
                    "16\n" +
                    "-15\n" +
                    "-75\n" +
                    "-42\n" +
                    "79\n" +
                    "85\n" +
                    "30\n" +
                    "-2\n" +
                    "-78\n" +
                    "16\n" +
                    "-78\n" +
                    "-58\n" +
                    "-88\n" +
                    "-74\n" +
                    "-102\n" +
                    "-17\n" +
                    "78\n" +
                    "25\n" +
                    "-32\n" +
                    "-121\n" +
                    "-94\n" +
                    "118\n" +
                    "84\n" +
                    "33\n" +
                    "-53\n" +
                    "33\n" +
                    "-72\n" +
                    "109\n" +
                    "37\n" +
                    "67\n" +
                    "-113\n" +
                    "-62\n" +
                    "-4\n" +
                    "96\n" +
                    "-115\n" +
                    "77\n" +
                    "-73\n" +
                    "-53\n" +
                    "-96\n" +
                    "1\n" +
                    "-57\n" +
                    "51\n" +
                    "-115\n" +
                    "-73\n" +
                    "next\n" +
                    "77\n" +
                    "-103\n" +
                    "-17\n" +
                    "-19\n" +
                    "16\n" +
                    "2\n" +
                    "-100\n" +
                    "90\n" +
                    "29\n" +
                    "53\n" +
                    "-119\n" +
                    "-14\n" +
                    "91\n" +
                    "-124\n" +
                    "-50\n" +
                    "38\n" +
                    "18\n" +
                    "-101\n" +
                    "7\n" +
                    "-2\n" +
                    "62\n" +
                    "-87\n" +
                    "-116\n" +
                    "5\n" +
                    "71\n" +
                    "-5\n" +
                    "78\n" +
                    "-59\n" +
                    "-90\n" +
                    "-106\n" +
                    "-97\n" +
                    "-119\n" +
                    "107\n" +
                    "-31\n" +
                    "32\n" +
                    "13\n" +
                    "-51\n" +
                    "-40\n" +
                    "-48\n" +
                    "-115\n" +
                    "-41\n" +
                    "71\n" +
                    "114\n" +
                    "-122\n" +
                    "3\n" +
                    "76\n" +
                    "12\n" +
                    "2\n" +
                    "56\n" +
                    "76\n" +
                    "-43\n" +
                    "-106\n" +
                    "102\n" +
                    "110\n" +
                    "46\n" +
                    "92\n" +
                    "60\n" +
                    "39\n" +
                    "-69\n" +
                    "-6\n" +
                    "-110\n" +
                    "-88\n" +
                    "50\n" +
                    "90\n" +
                    "next\n" +
                    "58\n" +
                    "120\n" +
                    "-12\n" +
                    "-28\n" +
                    "105\n" +
                    "-117\n" +
                    "-116\n" +
                    "73\n" +
                    "14\n" +
                    "97\n" +
                    "-19\n" +
                    "-34\n" +
                    "60\n" +
                    "-107\n" +
                    "44\n" +
                    "52\n" +
                    "-15\n" +
                    "87\n" +
                    "-64\n" +
                    "-33\n" +
                    "-96\n" +
                    "63\n" +
                    "106\n" +
                    "28\n" +
                    "123\n" +
                    "-9\n" +
                    "-18\n" +
                    "91\n" +
                    "63\n" +
                    "-118\n" +
                    "28\n" +
                    "-44\n" +
                    "116\n" +
                    "55\n" +
                    "-128\n" +
                    "-124\n" +
                    "13\n" +
                    "80\n" +
                    "92\n" +
                    "22\n" +
                    "-3\n" +
                    "37\n" +
                    "-34\n" +
                    "-63\n" +
                    "-53\n" +
                    "74\n" +
                    "-69\n" +
                    "-54\n" +
                    "-3\n" +
                    "37\n" +
                    "-11\n" +
                    "55\n" +
                    "-115\n" +
                    "-31\n" +
                    "-3\n" +
                    "52\n" +
                    "-59\n" +
                    "78\n" +
                    "66\n" +
                    "-23\n" +
                    "50\n" +
                    "-87\n" +
                    "-8\n" +
                    "59\n" +
                    "next\n"
            )
            save(
                    "secretkey_Parameters", "type a\n" +
                    "q 81869981414486565817042987620009425916711137248094272342132238763687306328559\n" +
                    "r 604462909877683331530751\n" +
                    "h 135442522736512392410054892783912140655846630328108560\n" +
                    "exp1 46\n" +
                    "exp2 79\n" +
                    "sign0 -1\n" +
                    "sign1 1\n"
            )
        }
        //calling function
        val button_call: Button = findViewById(R.id.button3)
        button_call.setOnClickListener() {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        }

        //scan qrcode and return MailID
        val button_scan: Button = findViewById(R.id.button5)
        button_scan.setOnClickListener() {
            val intent2 = Intent(this, RealMyScanActivity::class.java)
            startActivity(intent2)
            Toast.makeText(this, "Start scanning", Toast.LENGTH_SHORT).show()
        }
        //for test web function

//        //webview
//        val url=URL("https://www.baidu.com")
//        val connection=url.openConnection()as HttpURLConnection
//        //post & get
//        connection.requestMethod="GET"
//        connection.connectTimeout=8000
//        connection.readTimeout=8000
//        val input = connection.inputStream
//
//        connection.disconnect()
//
//
//        sendRequestWithHttpURLConnection()

    }


    private fun save(name: String, inputText: String) {
        try {
            val output = openFileOutput(name, Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use { it.write(inputText) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun showResponse(response: String) {
        runOnUiThread() {
            responseText.text = response
        }
    }

    private fun sendRequestWithHttpURLConnection() {
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                var url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                var input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)

                    }
                }
                showResponse(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }


}