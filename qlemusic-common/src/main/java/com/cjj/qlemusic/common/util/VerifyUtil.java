package com.cjj.qlemusic.common.util;

import java.util.Random;

public class VerifyUtil {
    private static final Random random = new Random();

    public static String getSixNumber() {
        int verify = random.nextInt(999999);
        if(verify < 100000){
            verify = verify + 100000;
        }
        return verify+"";
    }

    public static String getJsonSixNumber(){
        return "{'code':"+getSixNumber()+"}";
    }

    public static void main(String[] args) {
        System.out.println(getSixNumber());
    }
}
