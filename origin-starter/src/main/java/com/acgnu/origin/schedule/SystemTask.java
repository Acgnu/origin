package com.acgnu.origin.schedule;

import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.service.SimpleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SystemTask {
    @Autowired
    private SimpleService simpleService;
    @Autowired
    private RedisHelper redisHelper;

//    @Scheduled(cron = "* * * * * *")
//    public void debugLog(){
//        for (int i = 0; i < 1000; i++){
//            logger.debug("DEBUG: {}", System.currentTimeMillis());
//        }
//    }

    @Scheduled(cron = "* 1 * * * *")
    public void storeAccessLogToDb(){
//        redisHelper.lGet();
//        simpleService.save();
    }
}
