package com.yuchi.realtimeservice.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;


public class SnowflakeDynamicUtil {

    @Value("${snowflake.workerId}")
    private static long workerId;

    @Value("${snowflake.datacenterId}")
    private static long dataCenterId;

    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(workerId, dataCenterId);

    public static long nextId() {
        return SNOWFLAKE.nextId();
    }
}
