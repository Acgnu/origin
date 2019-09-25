package com.acgnu.origin.service;

import com.acgnu.origin.repository.UserRepository;
import com.acgnu.origin.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SimpleService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
//        return simpleMapper.findAll();
        return null;
    }

    @Cacheable("db0")
    public User findOne(int id){
        var optional = userRepository.findById(id);
        return optional.orElseGet(User::new);
    }

    /**
     * 模拟数据查询，返回用户
     * @param name
     * @return
     */
    public User findUserByUname(String name){
        return userRepository.getByUname(name);
    }

//    public AccessLog findAccessLogByHash(String accessHash) {
//    }
//
//    public void updateAccessLogById(AccessLog accessLog) {
//    }
//
//    public void saveAccessLog(AccessLog accessLog) {
//    }
}
