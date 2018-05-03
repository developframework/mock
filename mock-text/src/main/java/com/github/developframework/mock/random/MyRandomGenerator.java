package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;

/**
 * @author qiuzhenhao
 */
public class MyRandomGenerator implements RandomGenerator<String>{
    @Override
    public String randomValue(MockPlaceholder mockPlaceholder, MockCache mockCache) {
        // 获得参数
        String param = mockPlaceholder.getParameterOrDefault("param", String.class, "default value");

        // 返回随机值
        return null;
    }

    @Override
    public String name() {
        return "myPlaceholder";
    }
}
