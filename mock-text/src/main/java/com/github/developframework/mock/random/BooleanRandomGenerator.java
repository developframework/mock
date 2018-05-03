package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author qiuzhenhao
 */
public class BooleanRandomGenerator implements RandomGenerator<Boolean> {

    @Override
    public Boolean randomValue(MockPlaceholder mockPlaceholder, MockCache cache) {
        return RandomUtils.nextBoolean();
    }

    @Override
    public String name() {
        return "boolean";
    }
}
