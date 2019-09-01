package com.acgnu.origin.service;

import com.acgnu.origin.mapper.CardMsgMapper;
import com.acgnu.origin.model.User;
import com.acgnu.origin.model.CardMsg;
import com.acgnu.origin.model.Permission;
import com.acgnu.origin.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SimpleDbService{
    @Autowired
    private CardMsgMapper cardMsgMapper;

    public List<CardMsg> findAll(){
        System.out.println(System.currentTimeMillis() + " : findAll");
        return cardMsgMapper.findAll();
    }

    @Cacheable("db0")
    public CardMsg findOne(Integer id){
        System.out.println(System.currentTimeMillis() + " : findOne");
        return cardMsgMapper.findOne(id);
    }

    public User findOneUser(Integer uid){
        return cardMsgMapper.findOneUser(uid);
    }

    public List<CardMsg> queryOne(Integer id){
        return cardMsgMapper.queryOne(id);
    }

    /**
     * 模拟数据查询，返回用户
     * @param name
     * @return
     */
    public User findVirtulUser(String name){
        System.out.println("执行了：findVirtulUser -> " + name);
        Permission permission1 = new Permission();
        permission1.setId(1);
        permission1.setPremission("add");

        Permission permission2 = new Permission();
        permission2.setId(2);
        permission2.setPremission("see");

        Permission permission3 = new Permission();
        permission3.setId(3);
        permission3.setPremission("update");

        Permission permission4 = new Permission();
        permission4.setId(4);
        permission4.setPremission("del");

        List<Permission> permissionList1 = new ArrayList<>();
        permissionList1.add(permission1);
        permissionList1.add(permission2);
        permissionList1.add(permission3);
        permissionList1.add(permission4);

        List<Permission> permissionList2 = new ArrayList<>();
        permissionList2.add(permission2);

        Role role1 = new Role();
        role1.setId(1);
        role1.setName("admin");
        role1.setPermissions(permissionList1);

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("supervise");
        role2.setPermissions(permissionList2);

        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(role1);
        adminRoles.add(role2);
        User admin = new User();
        admin.setId(1);
        admin.setUname(name);
        admin.setUpass("123");
        admin.setRoles(adminRoles);

        List<Role> superviseRoles = new ArrayList<>();
        superviseRoles.add(role2);
        User supervise = new User();
        supervise.setId(2);
        supervise.setUname(name);
        supervise.setUpass("456");
        supervise.setRoles(superviseRoles);

        if ("admin".equals(name)) {
            return admin;
        }
        if ("supervise".equals(name)) {
            return supervise;
        }
        return null;
    }

}
