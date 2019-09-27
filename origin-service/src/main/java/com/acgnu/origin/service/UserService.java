package com.acgnu.origin.service;

import com.acgnu.origin.common.Constants;
import com.acgnu.origin.entity.User;
import com.acgnu.origin.exception.ArgException;
import com.acgnu.origin.pojo.LoginCredential;
import com.acgnu.origin.repository.UserRepository;
import com.acgnu.origin.util.MessageHolder;
import net.bytebuddy.utility.RandomString;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageHolder messageHolder;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateById(User user) {
        return null;//userRepository.updateById(user);
    }


    public List<User> findAll(){
//        return simpleMapper.findAll();
        return null;
    }

    @Cacheable("db0")
    public User findById(int id){
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

    /**
     * 保存用户
     * @param newUser      编辑中的用户信息
     * @param loginCredential  当前登陆的用户信息
     * @return
     * @throws RuntimeException
     */
    public User addUser(User newUser, LoginCredential loginCredential) throws RuntimeException {
        var exists = userRepository.existsByUname(newUser.getUname());
        if (exists) {
            throw new ArgException(messageHolder.lGet("user.add.exsists"));
        }

        Optional.ofNullable(loginCredential).ifPresentOrElse(user -> newUser.setCreateby(user.getId()), () -> newUser.setCreateby(0));
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setUpdateTime(LocalDateTime.now());
        var salt = RandomString.make();
        var byteSourceSalt = ByteSource.Util.bytes(salt);
        //对密码加盐
        var simpleHash = new SimpleHash("MD5", newUser.getUpass(), byteSourceSalt, Constants.SHIRO_HASH_ITERATE);
        newUser.setUpass(simpleHash.toHex());
        newUser.setSalt(salt);
        return userRepository.save(newUser);
    }
}
