package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 随机日期时间生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class DateTimeRandomGenerator implements RandomGenerator<Date> {

    protected static final String PARAMETER_RANGE = "range";
    protected static final String PARAMETER_FUTURE = "future";
    protected static final String PARAMETER_PATTERN = "pattern";

    @Override
    public Date randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache cache) {
        String range = mockPlaceholder.getParameterOrDefault(PARAMETER_RANGE, String.class, "1y");
        boolean future = mockPlaceholder.getParameterOrDefault(PARAMETER_FUTURE, boolean.class, false);

        long max = calcRange(range);
        long d = RandomUtils.nextLong(1000, max);
        long dateLong = System.currentTimeMillis() + (future ? d : -d);
        return new Date(dateLong);
    }

    @Override
    public String name() {
        return "datetime";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, Date value) {
        String pattern = mockPlaceholder.getParameterOrDefault(PARAMETER_PATTERN, String.class, "yyyy-MM-dd HH:mm:ss");
        return DateFormatUtils.format(value, pattern);
    }

    protected long calcRange(String range) {
        if (range.matches("^(\\d+y)?(\\d+M)?(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?$")) {
            int temp = 0;
            long sum = 0;
            for (int i = 0; i < range.length(); i++) {
                char ch = range.charAt(i);
                if (ch >= '0' && ch <= '9') {
                    temp = temp * 10 + (ch - '0');
                }
                switch(ch) {
                    case 'y': {
                        sum += temp * 365 * 24 * 3600;
                    }break;
                    case 'M': {
                        sum += temp * 30 * 24 * 3600;
                    }break;
                    case 'd': {
                        sum += temp * 24 * 3600;
                    }break;
                    case 'h': {
                        sum += temp * 3600;
                    }break;
                    case 'm': {
                        sum += temp * 60;
                    }break;
                    case 's': {
                        sum += temp;
                    }break;
                }
            }
            return sum * 1000;
        } else {
            throw new MockException("range format error");
        }
    }
}
