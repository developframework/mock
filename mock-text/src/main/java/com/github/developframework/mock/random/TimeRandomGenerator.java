package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 随机时间生成器
 *
 * @author qiushui on 2018-05-28.
 * @since 0.2
 */
public class TimeRandomGenerator extends DateTimeRandomGenerator {

    @Override
    public String randomValue(MockPlaceholder mockPlaceholder, MockCache mockCache) {
        String range = mockPlaceholder.getParameterOrDefault("range", String.class, "1h");
        String pattern = mockPlaceholder.getParameterOrDefault("pattern", String.class, "HH:mm:ss");
        boolean future = mockPlaceholder.getParameterOrDefault("future", boolean.class, false);

        long max = calcRange(range);
        long d = RandomUtils.nextLong(1000, max);
        long dateLong = System.currentTimeMillis() + (future ? d : -d);
        return DateFormatUtils.format(new Date(dateLong), pattern);
    }

    @Override
    public String name() {
        return "time";
    }
}
