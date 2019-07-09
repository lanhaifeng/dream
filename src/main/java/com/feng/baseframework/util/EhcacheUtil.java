package com.feng.baseframework.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.net.URL;

/**
 * capaa-web2
 * 2019/7/8 10:45
 * 缓存工具类
 *
 * @author lanhaifeng
 * @since
 **/
public class EhcacheUtil {

	public static String cacheName = "ehcache";

	private static final String path = "/cache/ehcache.xml";
	private URL url;
	private CacheManager manager;
	private static EhcacheUtil ehCache;

	private EhcacheUtil(String path) {
		url = getClass().getResource(path);
		manager = CacheManager.create(url);
	}

	public static EhcacheUtil getInstance() {
		if (ehCache == null) {
			ehCache = new EhcacheUtil(path);
		}
		return ehCache;
	}

	public void put(String cacheName, String key, Object value) {
		Cache cache = manager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	public Object get(String cacheName, String key) {
		Cache cache = manager.getCache(cacheName);
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	public Cache get(String cacheName) {
		return manager.getCache(cacheName);
	}

	public void remove(String cacheName, String key) {
		Cache cache = manager.getCache(cacheName);
		cache.remove(key);
	}
}
