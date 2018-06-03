package com.github.developframework.mock.random;

import com.github.developframework.mock.MockPlaceholder;
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
    public String key() {
        return "date";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, Date value) {
        String pattern = mockPlaceholder.getParameterOrDefault(PARAMETER_FUTURE, String.class, "yyyy-MM-dd");
        return DateFormatUtils.format(value, pattern);
    }
}
