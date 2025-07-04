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
        cache.putIfAbsent(0, BigInteger.ZERO);
        cache.putIfAbsent(1, BigInteger.ONE);
        cache.putIfAbsent(2, BigInteger.ZERO);
        cache.putIfAbsent(3, BigInteger.ONE);
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
        return cache.containsKey(key);
    }

    @Override
    public int getCacheSize() {
        return cache.size();
    }
}
