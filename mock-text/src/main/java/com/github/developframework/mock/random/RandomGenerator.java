package com.github.developframework.mock.random;


import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;

/**
 * 随机生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public interface RandomGenerator<T> {

    T randomValue(MockPlaceholder mockPlaceholder, MockCache mockCache);

    String name();
}
