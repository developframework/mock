package com.github.developframework.mock;

import com.github.developframework.mock.random.RandomGenerator;
import develop.toolkit.base.utils.StringAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 一个任务
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class MockTask {

    private RandomGeneratorRegistry randomGeneratorRegistry;

    private String template;

    private MockCache cache;

    public MockTask(RandomGeneratorRegistry randomGeneratorRegistry, MockCache cache, String template) {
        this.randomGeneratorRegistry = randomGeneratorRegistry;
        this.cache = cache;
        this.template = template;
    }

    /**
     * 运行任务
     *
     * @return 随机生成结果
     */
    @SuppressWarnings("unchecked")
    public String run() {
        List<MockPlaceholder> mockPlaceholders = extractPlaceholder(template);
        String result = template;
        for (MockPlaceholder mockPlaceholder : mockPlaceholders) {
            RandomGenerator randomGenerator = randomGeneratorRegistry.getRandomGenerator(mockPlaceholder.getName());
            Object value = randomGenerator.randomValue(randomGeneratorRegistry, mockPlaceholder, cache);
            // 加入缓存
            mockPlaceholder.getId().ifPresent(s -> cache.put(s, new MockCache.Cache(value, mockPlaceholder)));
            final String stringValue = randomGenerator.forString(mockPlaceholder, value);
            result = result.replace(mockPlaceholder.toString(), stringValue);
        }
        return result;
    }

    /**
     * 提取占位符
     *
     * @param template
     * @return
     */
    private List<MockPlaceholder> extractPlaceholder(String template) {
        return StringAdvice
                .regexMatchStartEnd(template, "\\$\\{", "\\}")
                .stream()
                .map(MockPlaceholder::new)
                .collect(Collectors.toList());
    }
}
