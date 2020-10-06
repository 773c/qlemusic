package com.cjj.qlemusic.common.util;

import java.util.UUID;

/**
 * uuid处理类
 */
public class UuidUtil {
    /**
     * 获取16位
     * @return
     */
    public static String getSxiteenBit(){
        return UUID.randomUUID().toString().replace("-","").substring(0,16);
    }

    /**
     * 获取20位
     * @return
     */
    public static String getTwentyBit(){
        return UUID.randomUUID().toString().replace("-","").substring(0,20);
    }

    /**
     * 获取24位
     * @return
     */
    public static String getTwentyFourBit(){
        return UUID.randomUUID().toString().replace("-","").substring(0,24);
    }

    /**
     * 获取32位
     * @return
     */
    public static String getThirtyTwoBit(){
        return UUID.randomUUID().toString().replace("-","").substring(0,32);
    }

    public static void main(String[] args) {
        System.out.println(getSxiteenBit());
    }
}
