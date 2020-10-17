package com.cjj.qlemusic.common.util;

import org.assertj.core.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUploadUtil {
    /**
     * 获得16位的filename
     * @param unique
     * @param filename
     * @return
     */
    public static String getSxiteenBitBitFileName(String unique,String filename){
        return unique+"_"+UuidUtil.getSxiteenBit()+"_"+filename;
    }
    /**
     * 获得20位的filename
     * @param unique
     * @param filename
     * @return
     */
    public static String getTwentyBitFileName(String unique,String filename){
        return unique+"_"+UuidUtil.getTwentyBit()+"_"+filename;
    }
    /**
     * 获得24位的filename
     * @param unique
     * @param filename
     * @return
     */
    public static String getTwentyFourBitFileName(String unique,String filename){
        return unique+"_"+UuidUtil.getTwentyFourBit()+"_"+filename;
    }
    /**
     * 获得32位的filename
     * @param unique
     * @param filename
     * @return
     */
    public static String getThirtyTwoBitFileName(String unique,String filename){
        return unique+"_"+UuidUtil.getThirtyTwoBit()+"_"+filename;
    }

    public static boolean isAudioType(String filename){
        int lastIndex = filename.lastIndexOf(".");
        String suffix = filename.substring(lastIndex+1);
        if(suffix.equals("mp3"))
            return true;
        else
            return false;
    }

    public static void main(String[] args) throws ParseException {
        String time = "2020-10-14 18:22:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse(time);

        System.out.println(parse.getTime());
    }
}
