package com.cjj.qlemusic.portal.components;


import cn.hutool.core.date.DateUtil;
import com.cjj.qlemusic.common.util.VerifyUtil;
import com.cjj.qlemusic.portal.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


@Component
@EnableAsync
@EnableScheduling
public class ScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private BbsLikeService bbsLikeService;
    @Autowired
    private BbsCommentService bbsCommentService;
    @Autowired
    private BbsPlayService bbsPlayService;
    @Autowired
    private UmsUserVisitService umsUserVisitService;

    /**
     * 点赞、播放、评论、访问数量的更新写入数据库操作
     * @throws IOException
     */
//    @Scheduled(cron="0 0/3 * * * ? ")//每过4分钟执行
    public void likeAfterSave() throws IOException {
        LOGGER.info("数据库更新开始");
        try {
            bbsLikeService.userLikeToDatabaseTimer();
            bbsLikeService.likedCountToDatabaseTimer();
            bbsPlayService.playedCountToDatabaseTimer();
            bbsCommentService.commentedCountToDatabaseTimer();
            umsUserVisitService.visitedCountToDatabaseTimer();
            LOGGER.info("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ 数据库全部更新完毕");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("缓存写入数据库失败");
        }finally {

        }
    }

    public static void main(String[] args) {
        System.out.println("eiqle_"+
                DateUtil.dayOfMonth(DateUtil.date())%10 +
                VerifyUtil.getSixNumber() +
                DateUtil.format(new Date(),"ss"));
    }
}
