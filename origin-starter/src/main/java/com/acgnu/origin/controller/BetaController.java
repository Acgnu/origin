package com.acgnu.origin.controller;

import com.acgnu.origin.entity.AccessUvLog;
import com.acgnu.origin.pojo.Result;
import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.redis.RedisKeyConst;
import com.acgnu.origin.repository.AccessUvLogRepository;
import com.acgnu.origin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class BetaController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    @Autowired
    private AccessUvLogRepository accessUvLogRepository;

    @GetMapping("/accessLog")
    public List<AccessUvLog> accessLog(){
        var accessIpKes = redisHelper.keys(RedisKeyConst.ACCESS_IP_PRE + "*");
        //        Optional<AccessUvLog> optionalAccessUvLog = accessUvLogRepository.findById(1L);
//        List<AccessUvLog> list = new ArrayList<>();
//        list.add(optionalAccessUvLog.orElseGet(AccessUvLog::new));
        return redisHelper.mGet(accessIpKes);
    }

    /**
     * 最基本的返回json数据
     * @return
     */
    @GetMapping("/demo")    //使用GetMapping可以省略method
    public Result demoData() {
        var user = userService.findById(1);
        return new Result<>(user, messageHolder);
    }

    /**
     * rabbitmq消息发送推送入口
     * @param content
     */
    @RequestMapping(value = "/rabbitmq/send/{content}", method = RequestMethod.GET)
    public void rabbitmqSend(@PathVariable(value = "content", required = false) String content){
        rabbitMessagingTemplate.convertAndSend("" , "queen_beta", content);
//        String result = rabbitMessagingTemplate.convertSendAndReceive("", RabbitmqConfig.QUEEN_NAME, null, message -> message);
//        System.out.println(result);
    }
}
