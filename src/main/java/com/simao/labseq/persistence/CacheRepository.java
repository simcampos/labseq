package com.simao.labseq.persistence;

import java.math.BigInteger;

/**
 * CacheRepository is an interface that defines methods for caching and retrieving values based on integer keys.
 * It is used to optimize the performance of calculations by storing previously computed results.
 */
public interface CacheRepository {
    void cacheValue(int key, BigInteger value);
    BigInteger getValueFromCache(int key);
    boolean isCachedByKey(int key);
    int getCacheSize();
}

