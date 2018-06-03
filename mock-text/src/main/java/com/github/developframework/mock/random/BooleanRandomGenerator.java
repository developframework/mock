package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import org.apache.commons.lang3.RandomUtils;

/**
 * 布尔值随机生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class BooleanRandomGenerator implements RandomGenerator<Boolean> {

    @Override
    public Boolean randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache cache) {
        return RandomUtils.nextBoolean();
    }

    @Override
    public String key() {
        return "boolean";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, Boolean value) {
        return String.valueOf(value);
    }
}
