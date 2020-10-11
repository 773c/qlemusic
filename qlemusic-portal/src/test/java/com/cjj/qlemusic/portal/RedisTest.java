package com.cjj.qlemusic.portal;

import com.cjj.qlemusic.portal.service.BbsLikeCacheService;
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
    @Test
    public void t1(){
        likeCacheService.incrementLikedCount((long)1);
    }
}
