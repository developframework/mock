package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * 随机手机号生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class MobileRandomGenerator implements RandomGenerator<String> {

    private static final int[] PREFIX = {
            // 移动
            134,135,136,137,138,139,150,151,152,157,158,159,182 ,183,184,187,188,147,178,
            // 联通
            130,131,132,155,156,185,186,145,176,
            // 电信
            180,181,189,133,153,177
    };

    @Override
    public String randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        return PREFIX[RandomUtils.nextInt(0, PREFIX.length)] + RandomStringUtils.randomNumeric(8);
    }

    @Override
    public String name() {
        return "mobile";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, String value) {
        return value;
    }
}
