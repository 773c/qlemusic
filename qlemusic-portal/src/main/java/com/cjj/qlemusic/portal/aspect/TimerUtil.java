package com.cjj.qlemusic.portal.aspect;


import com.cjj.qlemusic.portal.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@EnableAsync
@EnableScheduling
public class TimerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimerUtil.class);

    @Autowired
    private BbsLikeService bbsLikeService;
    @Autowired
    private BbsCommentService bbsCommentService;
    @Autowired
    private BbsPlayService bbsPlayService;
    @Autowired
    private BbsMusicService bbsMusicService;
    @Autowired
    private UmsUserVisitService umsUserVisitService;

    @Scheduled(cron="0 0/3 * * * ? ")//每过4分钟执行
    public void likeAfterSave() throws IOException {
        try {
            System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ 数据库更新开始");
            bbsLikeService.userLikeToDatabaseTimer();
            bbsLikeService.likedCountToDatabaseTimer();
            bbsPlayService.playedCountToDatabaseTimer();
            bbsCommentService.commentedCountToDatabaseTimer();
            umsUserVisitService.visitedCountToDatabaseTimer();
            System.out.println("数据库全部更新完毕");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("缓存写入数据库失败");
        }finally {

        }
    }
//    @Scheduled(cron="0 0/3 * * * ? ")//每过2分钟执行
    public void getLikeHotData(){
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ 数据库点赞热点数据表加载开始");
        try {
            bbsLikeService.getLikeDataFromDao();
        }catch (NullPointerException e){
            LOGGER.error("热点数据空指针异常");
        }
        System.out.println("数据库点赞热点数据表加载完毕");
    }
}
