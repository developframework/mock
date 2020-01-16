package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 随机枚举生成器
 *
 * @author qiushui on 2018-05-28.
 * @since 0.2
 */
public class EnumRandomGenerator implements RandomGenerator<String> {


    @Override
    public String randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        List<String> enums = new ArrayList<>(mockPlaceholder.getParameters().keySet());
        enums.remove("id");
        int index = RandomUtils.nextInt(0, enums.size());
        return enums.get(index);
    }

    @Override
    public String key() {
        return "enum";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, String value) {
        return value;
    }
}
