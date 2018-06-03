package com.github.developframework.mock.random;


import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import develop.framework.components.EntitySign;

/**
 * 随机生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 * @param <T> 随机值类型
 */
public interface RandomGenerator<T> extends EntitySign<String> {

    /**
     * 描述如何生成随机值
     * @param randomGeneratorRegistry 生成器注册器
     * @param mockPlaceholder 占位符
     * @param mockCache 缓存
     * @return 随机值
     */
    T randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache);

    /**
     * 描述随机值如何转换到字符串
     * @param mockPlaceholder 占位符
     * @param value 随机值
     * @return 随机值的字符串形式
     */
    String forString(MockPlaceholder mockPlaceholder, T value);
}
