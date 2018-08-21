package com.feng.baseframework.service.impl;

import com.feng.baseframework.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisPipelineException;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * capaa-parent
 * 2018/8/20 15:23
 * redis缓存业务实现
 *
 * @author lanhaifeng
 * @since
 **/
@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void changeDb(int index) {
        JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) stringRedisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(index);
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, Long timeOut) {
        stringRedisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.MILLISECONDS);
    }

    @Override
    public void multiSetList(String key, Collection<String> listData) {
        stringRedisTemplate.opsForList().leftPushAll(key, listData);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public List<Object> getRightListMultValueAfterDel(final String key, final long count) {
        final RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
        final Long allCount = stringRedisTemplate.opsForList().size(key);
        if(allCount == 0){
            return null;
        }

        List<Object> rightList = stringRedisTemplate.executePipelined(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] listName  = serializer.serialize(key);
                for (int i=0 ;i<count ;i++) {
                    redisConnection.rPop(listName);
                }
                return null;
            }
        });
        removeNullObject(rightList);
        return rightList;
    }

    private void removeNullObject(List<Object> rightList) {
        if(rightList != null){
            Iterator iterator = rightList.iterator();
            while (iterator.hasNext()){
                Object obj = iterator.next();
                if(obj == null){
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public List<Object> getLeftListMultValueAfterDel(final String key, final long count) {
        final RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
        final Long allCount = stringRedisTemplate.opsForList().size(key);
        if(allCount == 0){
            return null;
        }

        List<Object> leftList = stringRedisTemplate.executePipelined(new SessionCallback<String>() {
            @Override
            public String execute(RedisOperations redisOperations) throws DataAccessException {
                for (int i=0 ;i<count ;i++) {
                    redisOperations.opsForList().rightPop(key);
                }
                return null;
            }
        });
        removeNullObject(leftList);
        return leftList;
    }
}
