package com.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tanzepeng on 2015/8/6.
 */
public class RedisCache {

    private final static Logger log = LoggerFactory.getLogger(RedisCache.class);

    private static RedisCache redisCache;

    public RedisCache getInstance() {
        if (redisCache == null) {
            redisCache = new RedisCache();
        }
        return redisCache;
    }
}
