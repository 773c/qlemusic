package com.cjj.qlemusic.portal;

import com.cjj.qlemusic.portal.service.BbsLikeCacheService;
import com.cjj.qlemusic.portal.service.BbsMusicService;
import com.cjj.qlemusic.security.service.UmsUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = QlemusicPortalApplication.class)
public class RedisTest {
    @Autowired
    private BbsLikeCacheService likeCacheService;
    @Autowired
    private BbsMusicService bbsMusicService;
    @Autowired
    private UmsUserService umsUserService;
    @Test
    public void t1(){
    }

    @Test
    public void t2(){
//        List<BbsMusic> myMusicList = bbsMusicService.getMyMusicList((long)1);
//        System.out.println("111"+myMusicList);
    }
}
