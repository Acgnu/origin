package com.acgnu.origin.controller;

import com.acgnu.origin.config.Constants;
import com.acgnu.origin.entity.AccessUvLog;
import com.acgnu.origin.entity.Accesser;
import com.acgnu.origin.rabbitmq.RabbitmqConfig;
import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.redis.RedisKeyConst;
import com.acgnu.origin.repository.AccessUvLogRepository;
import com.acgnu.origin.service.SimpleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.RestResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Set<String> accessIpKes = redisHelper.keys(RedisKeyConst.ACCESS_IP_PRE + "*");
        List<AccessUvLog> accessUvLogs = redisHelper.mGet(accessIpKes);
//        Optional<AccessUvLog> optionalAccessUvLog = accessUvLogRepository.findById(1L);
//        List<AccessUvLog> list = new ArrayList<>();
//        list.add(optionalAccessUvLog.orElseGet(AccessUvLog::new));
        return accessUvLogs;
    }

    /**
     * 最基本的返回json数据
     * @return
     */
    @GetMapping("/demo")    //使用GetMapping可以省略method
    public RestResult demoData() {
        Accesser accesser = simpleService.findOne(1);
        RestResult<Accesser> demoResult = new RestResult<>(Constants.API_CODE_SUCCESS, Constants.API_MSG_SUCCESS, accesser);
        return demoResult;
    }

    /**
     * rabbitmq消息发送推送入口
     * @param content
     */
    @RequestMapping(value = "/rabbitmq/send/{content}", method = RequestMethod.GET)
    public void rabbitmqSend(@PathVariable(value = "content", required = false) String content){
        rabbitMessagingTemplate.convertAndSend("", RabbitmqConfig.QUEEN_NAME, content);
//        String result = rabbitMessagingTemplate.convertSendAndReceive("", RabbitmqConfig.QUEEN_NAME, null, message -> message);
//        System.out.println(result);
    }

    /**
     * shiro访问控制1
     * 通过shiro角色控制
     */
//    @RequiresPermissions("see")
    @RequestMapping(value = "/shiro/see", method = RequestMethod.GET)
    public String shiroSee(){
        return "shiroSee";
    }

    /**
     * shiro访问控制2
     * 通过shiro权限控制
     */
//    @RequiresRoles("admin")
    @RequestMapping(value = "/shiro/del", method = RequestMethod.GET)
    public String shiroDel(){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.hasRole("admin"));
        return "shiroDel";
    }

    @RequestMapping(value = {"login", "success", "deny"})
    public String login(HttpServletRequest request){
        return request.getRequestURI();
    }

    /**
     * shiro访问控制3
     */
    @RequestMapping(value = "/shiro/login/{name}/{password}", method = RequestMethod.GET)
    public String shiroLogin(@PathVariable(value = "name") String name, @PathVariable(value = "password") String password){
        String result = "success";
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, password);
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            result = e.getLocalizedMessage();
            subject.logout();
        }
        return result;
    }
}
