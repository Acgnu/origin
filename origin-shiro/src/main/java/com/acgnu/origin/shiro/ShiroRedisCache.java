package com.acgnu.origin.shiro;

import com.acgnu.origin.redis.RedisHelper;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

public class ShiroRedisCache implements Cache{
    private RedisHelper redisHelper;

    private String prefix = "shiro_redis:";

    public ShiroRedisCache(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Override
    public Object get(Object key) throws CacheException {
        if (null == key) {
            return null;
        }
        return redisHelper.get(prefix + key.toString());
    }

    @Override
    public Object put(Object k, Object v) throws CacheException {
        if (k== null || v == null) {
            return null;
        }
        redisHelper.set(prefix + k.toString(), v);
        return v;
    }

    @Override
    public Object remove(Object k) throws CacheException {
        if(k==null){
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
