package com.github.developframework.mock;

import com.github.developframework.mock.random.RandomGenerator;
import com.github.developframework.mock.random.RandomGeneratorRegistry;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一个任务
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class MockTask {

    private static final String REGEX = "(?=\\$\\{)(.*?)(?<=\\})";

    private RandomGeneratorRegistry randomGeneratorRegistry;

    private String template;

    private MockCache cache = new MockCache();


    public MockTask(RandomGeneratorRegistry randomGeneratorRegistry, String template) {
        this.randomGeneratorRegistry = randomGeneratorRegistry;
        this.template = template;
    }

    public String run() {
        List<MockPlaceholder> mockPlaceholders = extractPlaceholder(template);
        String result = template;
        for (MockPlaceholder mockPlaceholder : mockPlaceholders) {
            RandomGenerator randomGenerator = randomGeneratorRegistry.getRandomGenerator(mockPlaceholder.getName());
            Optional<String> idOptional = mockPlaceholder.getId();
            Object value = randomGenerator.randomValue(mockPlaceholder, cache);
            // 加入缓存
            if (idOptional.isPresent()) {
                cache.put(idOptional.get(), new MockCache.Cache(value, mockPlaceholder));
            }
            result = result.replace(mockPlaceholder.getPlaceholder(), value.toString());
        }
        return result;
    }

    private List<MockPlaceholder> extractPlaceholder(String template) {
        List<MockPlaceholder> list = new LinkedList<>();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher m = pattern.matcher(template);
        while(m.find()) {
            list.add(new MockPlaceholder(m.group()));
        }
        return list;
    }
}
