package com.acgnu.origin;

import com.acgnu.origin.entity.Accesser;
import com.acgnu.origin.entity.Privilege;
import com.acgnu.origin.entity.Role;
import com.acgnu.origin.repository.AccesserRepository;
import com.acgnu.origin.service.SimpleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import pojo.IpAnalyseResult;

import java.util.Iterator;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OriginApplicationTests {
    @Autowired
    AccesserRepository accesserRepository;
    private SimpleService simpleService;

    @Test
    public void contextLoads() {
        System.out.println(accesserRepository);
    }

    @Test
    public void testRestTemplate(){
        System.out.println(simpleService);
        RestTemplate restTemplate = new RestTemplate();
        IpAnalyseResult jsonObject = restTemplate.getForObject("http://api.online-service.vip/ip3?ip=114.218.96.52",
                IpAnalyseResult.class);
        System.out.println(jsonObject);
    }

    @Test
    public void testQuery(){
        Accesser acgnu = simpleService.findUserByUname("Acgnu");
        System.out.println(acgnu.getRoles());
        Iterator<Role> iterator = acgnu.getRoles().iterator();
        while (iterator.hasNext()) {
            Role next = iterator.next();
            Set<Privilege> privileges = next.getPrivileges();
            System.out.println(privileges);
        }
        System.out.println("done");
    }
}
