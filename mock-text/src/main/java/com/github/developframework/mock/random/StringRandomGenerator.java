package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 字符串随机生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class StringRandomGenerator implements RandomGenerator<String>{

    private static final String PARAMETER_LENGTH = "length";
    private static final String PARAMETER_UPPERCASE = "uppercase";
    private static final String PARAMETER_LOWERCASE = "lowercase";
    private static final String PARAMETER_LETTERS = "letters";
    private static final String PARAMETER_NUMBERS = "numbers";

    @Override
    public String randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache cache) {
        int length = mockPlaceholder.getParameterOrDefault(PARAMETER_LENGTH, int.class, 6);
        boolean uppercase = mockPlaceholder.getParameterOrDefault(PARAMETER_UPPERCASE, boolean.class, false);
        boolean lowercase = mockPlaceholder.getParameterOrDefault(PARAMETER_LOWERCASE, boolean.class, false);
        boolean letters = mockPlaceholder.getParameterOrDefault(PARAMETER_LETTERS, boolean.class, false);
        boolean numbers = mockPlaceholder.getParameterOrDefault(PARAMETER_NUMBERS, boolean.class, false);

        if(!letters && !numbers) {
            letters = true;
        }

        String result = RandomStringUtils.random(length, letters, numbers);
        if(uppercase == lowercase) {
            return result;
        } else if (uppercase) {
            return result.toUpperCase();
        } else if(lowercase) {
            return result.toLowerCase();
        }
        return result;
    }

    @Override
    public String name() {
        return "string";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, String value) {
        return value;
    }
}
