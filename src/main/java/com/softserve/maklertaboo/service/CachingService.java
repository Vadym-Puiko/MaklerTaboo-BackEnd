package com.softserve.maklertaboo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CachingService {

    private final CacheManager cacheManager;

    @Autowired
    public CachingService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void evictSingleCacheValue(String cacheName, String cacheKey) {
        cacheManager.getCache(cacheName).evict(cacheKey);
    }

    public void evictAllCacheValues(String cacheName) {
        cacheManager.getCache(cacheName).clear();
    }
}
