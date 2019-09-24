package com.acgnu.origin.service;

import com.acgnu.origin.repository.AccesserRepository;
import com.acgnu.origin.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SimpleService {
    @Autowired
    private AccesserRepository accesserRepository;

    public List<Accesser> findAll(){
        System.out.println(System.currentTimeMillis() + " : findAll");
//        return simpleMapper.findAll();
        return null;
    }

    @Cacheable("db0")
    public Accesser findOne(int id){
        System.out.println(System.currentTimeMillis() + " : findOne");
        Optional<Accesser> optional = accesserRepository.findById(id);
        return optional.orElseGet(Accesser::new);
    }

    /**
     * 模拟数据查询，返回用户
     * @param name
     * @return
     */
    public Accesser findUserByUname(String name){
        return accesserRepository.getByUname(name);
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
