package com.simao.labseq.persistence;

import jakarta.inject.Singleton;
import org.jboss.logging.Logger;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * InMemoryCacheImplementation is a singleton class that implements the CacheRepository interface.
 * It provides an in-memory cache for storing and retrieving calculated values based on integer keys.
 * The cache is initialized with some predefined values for keys 0 to 3.
 */
@Singleton
public class InMemoryCacheImplementation implements CacheRepository {
    private static final Map<Integer, BigInteger> cache = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(InMemoryCacheImplementation.class);

    public InMemoryCacheImplementation() {
        cache.putIfAbsent(0, BigInteger.valueOf(0L));
        cache.putIfAbsent(1, BigInteger.valueOf(1L));
        cache.putIfAbsent(2, BigInteger.valueOf(0L));
        cache.putIfAbsent(3, BigInteger.valueOf(1L));
    }

    @Override
    public void cacheValue(int key, BigInteger value) {
        cache.putIfAbsent(key, value);
    }

    @Override
    public BigInteger getValueFromCache(int key) {
        return cache.get(key);
    }

    @Override
    public boolean isCachedByKey(int key) {
        boolean isCacheHit = cache.containsKey(key);
        if(isCacheHit) LOGGER.debug("Cache hit for key: " + key);
        return isCacheHit;
    }
}
