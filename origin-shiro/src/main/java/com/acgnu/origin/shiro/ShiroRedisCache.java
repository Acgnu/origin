package com.acgnu.origin.shiro;

import com.acgnu.origin.redis.RedisHelper;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class ShiroRedisCache implements Cache{
    private RedisHelper redisHelper;

    private String prefix = "shiro_redis:";

    public ShiroRedisCache(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Override
    public Object get(Object key) throws CacheException {
        return ObjectUtils.isEmpty(key) ? null : redisHelper.get(prefix + key.toString());
    }

    @Override
    public Object put(Object k, Object v) throws CacheException {
        if (ObjectUtils.isEmpty(k) || ObjectUtils.isEmpty(v)) {
            return null;
        }
        redisHelper.set(prefix + k.toString(), v);
        return v;
    }

    @Override
    public Object remove(Object k) throws CacheException {
        if(ObjectUtils.isEmpty(k)){
            return null;
        }
        Object v = redisHelper.get(prefix + k.toString());
        redisHelper.del(prefix + k.toString());
        return v;
    }

    @Override
    public void clear() throws CacheException {
        redisHelper.clear();
    }

    @Override
    public int size() {
        return redisHelper.size();
    }

    @Override
    public Set keys() {
        return redisHelper.sSetKeys(prefix + "*");
    }

    @Override
    public Collection values() {
        return redisHelper.sGet(prefix + "*");
    }
}
