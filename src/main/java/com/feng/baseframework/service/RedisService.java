package com.feng.baseframework.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * capaa-parent
 * 2018/8/20 15:03
 * redis缓存接口
 *
 * @author lanhaifeng
 * @since
 **/
public interface RedisService {

    /**
     * @author: lanhaifeng
     * @description 从缓存中删除键值为key的值
     * @date: 2018/8/20 15:05
     * @param key 键值
     * @return java.lang.String
     */
    public void remove(String key);

    /**
     * @author: lanhaifeng
     * @description 向缓存中存放数据，键值对均为对象
     * @date: 2018/8/20 15:05
     * @param key     键值
     * @param value   值
     * @return
     */
    public void set(String key, String value);

    /**
     * @author: lanhaifeng
     * @description 向缓存中存放数据，键值对均为对象
     * @date: 2018/8/20 15:05
     * @param key     键值
     * @param value   值
     * @param timeOut 过期时间
     * @return
     */
    public void set(String key, String value, Long timeOut);

    /**
     * @author: lanhaifeng
     * @description 从缓存中取键值为key的值
     * @date: 2018/8/20 15:05
     * @param key 键值
     * @return java.lang.String
     */
    public String get(String key);

    /**
     * @author: lanhaifeng
     * @description 往缓存中存键值为key的list
     * @date: 2018/8/20 15:05
     * @param key  列表键
     * @param listData 列表值
     */
    public void multiSetList(String key, Collection<String> listData);

    /**
     * @author: lanhaifeng
     * @description 从缓存中取数量为count键为key的list，从list队尾取
     * @date: 2018/8/20 15:05
     * @param key       列表的键
     * @param count     数量
     * @author lanhaifeng
     * @return java.util.List<java.lang.Object>
     */
    public List<Object> getRightListMultValueAfterDel(String key, long count);

    /**
     * @author: lanhaifeng
     * @description 从缓存中取数量为count键为key的list，从list队首取
     * @date: 2018/8/20 15:05
     * @param key       列表的键
     * @param count     数量
     * @author lanhaifeng
     * @return java.util.List<java.lang.Object>
     */
    public List<Object> getLeftListMultValueAfterDel(String key, long count);

    /**
     * 2018/8/20 17:05
     * 切换数据库
     *
     * @param
     * @author lanhaifeng
     * @param  index    数据库索引下标
     * @return void
     */
    public void changeDb(int index);

    /**
     * 2018/8/22 13:59
     * 批量从hash中取值
     *
     * @param hashName     hash表的名字
     * @param keys        需要取值的key集合
     * @param isDelete    是否在取值同时删除
     * @author lanhaifeng
     * @return java.util.List<java.lang.Object>
     */
    public List<Object> getHashMultValue(String hashName, Set<String> keys, boolean isDelete);

    /**
     * 2018/8/22 14:07
     * 批量从hash中存值
     *
     * @param hashName    hash表的名字
     * @param data       需要存值的集合
     * @author lanhaifeng
     * @return void
     */
    public void setHashMultValue(String hashName, Map<String,String> data);

    /**
     * 2018/11/21 15:02
     * 获取hash名为hashName表长度
     *
     * @param hashName
     * @author lanhaifeng
     * @return java.lang.Long
     */
    public Long getHashCount(String hashName);

    /**
     * 2018/11/21 16:40
     * 删除hash表为hashName，数据key为dataKey的数据
     *
     * @param hashName
     * @param dataKey
     * @author lanhaifeng
     * @return void
     */
    public void deleteHashKey(String hashName, String dataKey);

    /**
     * 2018/8/30 14:55
     * 发布redis订阅
     *
     * @param channel   发布的通道
     * @param message   发布的消息
     * @author lanhaifeng
     * @return void
     */
    public void convertAndSend(String channel,String message);

    /**
     * 2018/8/30 14:55
     * pipe批量发布redis订阅
     *
     * @param channel   发布的通道
     * @param message   发布的消息
     * @author lanhaifeng
     * @return void
     */
    public void convertAndSend(String channel,List<String> messages);
}
