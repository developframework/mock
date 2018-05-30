package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
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
    public Date randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        String range = mockPlaceholder.getParameterOrDefault(PARAMETER_RANGE, String.class, "1h");
        boolean future = mockPlaceholder.getParameterOrDefault(PARAMETER_FUTURE, boolean.class, false);

        long max = calcRange(range);
        long d = RandomUtils.nextLong(1000, max);
        long dateLong = System.currentTimeMillis() + (future ? d : -d);
        return new Date(dateLong);
    }

    @Override
    public String name() {
        return "time";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, Date value) {
        String pattern = mockPlaceholder.getParameterOrDefault(PARAMETER_PATTERN, String.class, "HH:mm:ss");
        return DateFormatUtils.format(value, pattern);
    }
}
