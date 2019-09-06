package com.acgnu.origin.controller;

import com.alibaba.fastjson.JSONObject;
import com.acgnu.origin.model.User;
import com.acgnu.origin.model.SimpleModel;
import com.acgnu.origin.rabbitmq.RabbitmqConfig;
import com.acgnu.origin.redis.RedisHelper;
import com.acgnu.origin.service.SimpleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class DemoController {
    @Value("${origin.config.creator}")
    private String creator;

    @Autowired
    private User admin;

    @Autowired
    private SimpleService simpleService;

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    private RedisHelper redisHelper;

    /**
     * 最基本的返回json数据
     * @return
     */
    @GetMapping("demo")    //使用GetMapping可以省略method
    public JSONObject demoData(){
        JSONObject fastjson = new JSONObject();
        fastjson.put("code", 0);
        fastjson.put("msg", "success");
        User user = simpleService.findOneUser(1);
        fastjson.put("operator", user);
        return fastjson;
    }

    /**
     * 使用组合url实现检索数据
     * @param id
     * @return
     */
    @RequestMapping(value = {"/cardmsg/{id}", "/cardmsg"}, method = RequestMethod.GET)
    public List<SimpleModel> cardMsg(@PathVariable(value = "id", required = false) Integer id){
        List<SimpleModel> simpleModels = null;
        if (null == id) {
            simpleModels = simpleService.findAll();
        } else {
            simpleModels = simpleService.queryOne(id);
        }
        return simpleModels;
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
     * Redis工具类测试入口
     * @param content
     */
    @RequestMapping(value = "/redis/set/{content}", method = RequestMethod.GET)
    public String redisSet(@PathVariable(value = "content", required = false) String content){
        redisHelper.set("key", content);
        return redisHelper.get("key").toString();
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
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.hasRole("admin"));
        return "shiroDel";
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
