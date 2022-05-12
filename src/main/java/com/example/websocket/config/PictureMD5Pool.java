package com.example.websocket.config;

import org.springframework.util.DigestUtils;

import java.util.HashMap;

public class PictureMD5Pool {
    public static HashMap<String, String> picMD5 = new HashMap<>();

public static void addMD5(byte[] file, String filename) {
    String md5 = DigestUtils.md5DigestAsHex(file);
    picMD5.put(md5, filename);
}

public static boolean exists(byte[] file) {
    if (picMD5.isEmpty()) return false;
    String md5 = DigestUtils.md5DigestAsHex(file);
    return picMD5.containsKey(md5);
}

public static String getMD5(byte[] file) {
    return DigestUtils.md5DigestAsHex(file);
}


}
