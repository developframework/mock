package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * IP随机生成器
 *
 * @author qiushui on 2018-05-31.
 * @since 0.2
 */
public class IPRandomGenerator implements RandomGenerator<String>{

    private static final String PARAMETER_PREFIX = "prefix";

    @Override
    public String randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        Optional<String> prefixOptional = mockPlaceholder.getParameter(PARAMETER_PREFIX, String.class);
        String prefix;
        int needNumberCount;
        if(prefixOptional.isPresent()) {
            prefix = prefixOptional.get();
            if(prefix.matches("^(([1-9]?\\d|1\\d{2}|(2([0-4]\\d|5[0-5])))\\.){0,2}([1-9]?\\d|1\\d{2}|(2([0-4]\\d|5[0-5])))$")) {
                needNumberCount = 4 - prefix.split("\\.").length;
            } else {
                throw new MockException("\"%s\" is not a valid IP prefix", prefix);
            }
        } else {
            prefix = "";
            needNumberCount = 4;
        }

        int[] numbers = new int[needNumberCount];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = RandomUtils.nextInt(0, 256);
        }
        return prefix + "." + StringUtils.join(numbers, '.');
    }

    @Override
    public String key() {
        return "ip";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, String value) {
        return value;
    }
}
