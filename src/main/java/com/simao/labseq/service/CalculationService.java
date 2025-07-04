package com.simao.labseq.service;

import com.simao.labseq.persistence.CacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.math.BigInteger;

/**
 * CalculationService is responsible for calculating the sequence value based on the provided integer n.
 * It uses a cache to store previously calculated values to optimize performance.
 */
@ApplicationScoped
public class CalculationService {
    private final CacheRepository cacheRepository;
    private static final Logger LOGGER = Logger.getLogger(CalculationService.class.getName());
    private static final int OP_1_SUBTRACTOR = 4;
    private static final int OP_2_SUBTRACTOR = 3;

    public CalculationService(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public BigInteger calculateSequenceValue(int n) {
        if (cacheRepository.isCachedByKey(n)) return cacheRepository.getValueFromCache(n);
        int lastIndex = cacheRepository.getCacheSize();
        for (int i = lastIndex; i <= n; i++) {
            cacheRepository.cacheValue(i, cacheRepository.getValueFromCache(i - OP_1_SUBTRACTOR).add(cacheRepository.getValueFromCache(i - OP_2_SUBTRACTOR)));
        }
        return cacheRepository.getValueFromCache(n);
    }
}
