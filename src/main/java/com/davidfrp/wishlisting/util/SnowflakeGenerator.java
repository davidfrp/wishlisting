package com.davidfrp.wishlisting.util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class SnowflakeGenerator implements IdentifierGenerator {

    public static final String GENERATOR_NAME = "snowflakeGenerator";

    private static final long WISHLISTING_EPOCH = 1609455600000L; // 01-01-2021 00:00:00
    private static int incrementalValue;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object object) {

        // 111111111111111111111111111111111111111111 1111111111 111111111111
        // 64                                         22         12          0
        //  ^ timestamp                               ^ random   ^ sequence

        if (incrementalValue > 4095)
            incrementalValue = 0;

        String snowflakeSequence = Integer.toBinaryString(incrementalValue++);
        String snowflakeInstance = Integer.toBinaryString(new Random().nextInt(1023) & 1023);
        String snowflakeTimestamp = Long.toBinaryString(System.currentTimeMillis() - WISHLISTING_EPOCH);

        snowflakeSequence = String.format("%1$12s", snowflakeSequence).replace(' ', '0');
        snowflakeInstance = String.format("%1$10s", snowflakeInstance).replace(' ', '0');
        snowflakeTimestamp = String.format("%1$42s", snowflakeTimestamp).replace(' ', '0');

        String snowflake = snowflakeTimestamp + snowflakeInstance + snowflakeSequence;

        return Long.parseLong(snowflake, 2);
    }
}
