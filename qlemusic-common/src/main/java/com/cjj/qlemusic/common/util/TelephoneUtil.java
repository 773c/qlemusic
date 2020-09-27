package com.cjj.qlemusic.common.util;

/**
 * 手机号工具类
 */
public class TelephoneUtil {
    public static String encryptTelephone(String telephone){
        String encryptPart = telephone.substring(3,7);
        return telephone.replace(encryptPart,"****");
    }
}
