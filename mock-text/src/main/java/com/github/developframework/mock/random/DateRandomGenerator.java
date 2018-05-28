package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 随机日期生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class DateRandomGenerator extends DateTimeRandomGenerator {

    @Override
    public String randomValue(MockPlaceholder mockPlaceholder, MockCache mockCache) {
        String range = mockPlaceholder.getParameterOrDefault("range", String.class, "1y");
        String pattern = mockPlaceholder.getParameterOrDefault("pattern", String.class, "yyyy-MM-dd");
        boolean future = mockPlaceholder.getParameterOrDefault("future", boolean.class, false);

        long max = calcRange(range);
        long d = RandomUtils.nextLong(1000, max);
        long dateLong = System.currentTimeMillis() + (future ? d : -d);
        return DateFormatUtils.format(new Date(dateLong), pattern);
    }

    @Override
    public String name() {
        return "date";
    }
}
