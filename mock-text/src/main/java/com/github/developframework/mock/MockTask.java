package com.github.developframework.mock;

import com.github.developframework.mock.random.RandomFactory;
import com.github.developframework.mock.random.RandomGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qiuzhenhao
 */
class MockTask {

    private static final String REGEX = "(?=\\$\\{)(.*?)(?<=\\})";

    private RandomFactory randomFactory;

    private String template;

    private MockCache cache = new MockCache();


    public MockTask(RandomFactory randomFactory, String template) {
        this.randomFactory = randomFactory;
        this.template = template;
    }

    public String run() {
        List<MockPlaceholder> mockPlaceholders = extractPlaceholder(template);
        String result = template;
        for (MockPlaceholder mockPlaceholder : mockPlaceholders) {
            RandomGenerator randomGenerator = randomFactory.getRandomGenerator(mockPlaceholder.getName());
            Optional<String> idOptional = mockPlaceholder.getId();
            Object value = randomGenerator.randomValue(mockPlaceholder, cache);
            // 加入缓存
            if (idOptional.isPresent()) {
                cache.put(idOptional.get(), value);
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
