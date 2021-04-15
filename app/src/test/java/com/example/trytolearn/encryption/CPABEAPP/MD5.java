package com.example.trytolearn.encryption.CPABEAPP;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public String md5(String data) {
        try {
            byte[] md5 = md5(data.getBytes("utf-8"));
            return toHexString(md5);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public byte[] md5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    private String toHexString(byte[] md5) {
        StringBuilder sb = new StringBuilder();
        System.out.println("md5.length: " + md5.length);
        for (byte b : md5) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        return sb.toString();
    }
}
