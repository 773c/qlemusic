package com.cjj.qlemusic.portal.aspect;


import com.cjj.qlemusic.portal.service.BbsLikeService;
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
    @Autowired
    private BbsLikeService bbsLikeService;

    @Scheduled(cron="1-4 0/2 * * * ?")
    public void first() throws IOException {
        System.out.println("数据库点赞表更新开始");
        bbsLikeService.userLikeToDatabaseTimer();
        bbsLikeService.likedCountToDatabaseTimer();
        System.out.println("数据库点赞表更新完毕");
    }
}
