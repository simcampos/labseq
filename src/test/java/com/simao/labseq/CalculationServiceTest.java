package com.simao.labseq;

import com.simao.labseq.persistence.CacheRepository;
import com.simao.labseq.persistence.InMemoryCacheImplementation;
import com.simao.labseq.service.CalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CalculationServiceTest {
    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        CacheRepository cacheRepository = new InMemoryCacheImplementation();
        calculationService = new CalculationService(cacheRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void testCalculation(int n) {
        // Act
        BigInteger result = calculationService.calculateSequenceValue(n);

        // Assert
        switch (n) {
            case 0, 2 -> assertEquals(BigInteger.ZERO, result);
            case 1, 3, 4, 5, 6 -> assertEquals(BigInteger.ONE, result);
            case 7, 8, 9 -> assertEquals(BigInteger.TWO, result);
            case 10 -> assertEquals(BigInteger.valueOf(3), result);
            default -> fail("Unexpected value: " + n);
        }
    }
}
