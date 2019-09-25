package com.acgnu.origin.controller;

import com.acgnu.origin.common.Constants;
import com.acgnu.origin.entity.AccessUvLog;
import com.acgnu.origin.pojo.RestResult;
import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.redis.RedisKeyConst;
import com.acgnu.origin.repository.AccessUvLogRepository;
import com.acgnu.origin.service.SimpleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class BetaController extends BaseController {
    @Autowired
    private SimpleService simpleService;
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
    public RestResult demoData() {
        var user = simpleService.findOne(1);
        return new RestResult<>(Constants.API_CODE_SUCCESS, Constants.API_MSG_SUCCESS, user);
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

    /**
     * shiro访问控制1
     * 通过shiro角色控制
     */
    @RequiresPermissions("see")
    @RequestMapping(value = "/shiro/see", method = RequestMethod.GET)
    public String shiroSee(){
        return "shiroSee";
    }

    /**
     * shiro访问控制2
     * 通过shiro权限控制
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/shiro/del", method = RequestMethod.GET)
    public String shiroDel(){
        var subject = SecurityUtils.getSubject();
        log.info(subject.hasRole("admin") + "");
        return "shiroDel";
    }

    @RequestMapping(value = {"login", "success", "deny"})
    public String login(HttpServletRequest request){
        return request.getRequestURI();
    }
}
