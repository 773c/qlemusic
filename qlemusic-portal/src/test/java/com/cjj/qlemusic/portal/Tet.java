package com.cjj.qlemusic.portal;

import com.cjj.qlemusic.portal.entity.BbsMusic;
import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Tet {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<BbsMusic> bbsMusicList = new ArrayList<>();
        BbsMusic bbsMusic = new BbsMusic();
        bbsMusic.setId((long)1);
        BbsMusic bbsMusic2 = new BbsMusic();
        bbsMusic2.setId((long)2);
        bbsMusicList.add(bbsMusic);
        bbsMusicList.add(bbsMusic2);

        List<BbsMusicOperation> bbsMusicOperationList = new ArrayList<>();
        BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
        bbsMusicOperation.setMusicId((long)1);
        bbsMusicOperation.setCommentCount(10);
        BbsMusicOperation bbsMusicOperation2 = new BbsMusicOperation();
        bbsMusicOperation2.setMusicId((long)2);
        bbsMusicOperation2.setLikeCount(20);

        bbsMusicOperationList.add(bbsMusicOperation);
        bbsMusicOperationList.add(bbsMusicOperation2);

       for (BbsMusic music: bbsMusicList){
           for (BbsMusicOperation musicOperation:bbsMusicOperationList){
               System.out.println(music.getId() +"==?"+ musicOperation.getMusicId());
               if(music.getId() == musicOperation.getMusicId()){
                   System.out.println("相等");
                   music.setBbsMusicOperation(musicOperation);
                   break;
               }
           }
           System.out.println(music);
       }
        System.out.println("___________________________________________");
        System.out.println(bbsMusicList);
    }
}
