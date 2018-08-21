package com.feng.baseframework.service;

import java.util.Collection;
import java.util.List;

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
}
