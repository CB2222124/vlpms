package com.github.cb2222124.vlpms.backend.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Service used to clear a specific cache.
 */
@Service
public class CacheService {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void clearCache(String name) {
        Cache cache = cacheManager.getCache(name);
        if (cache != null) cache.clear();
    }
}
