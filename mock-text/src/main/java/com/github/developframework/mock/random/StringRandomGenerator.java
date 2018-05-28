package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 字符串随机生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class StringRandomGenerator implements RandomGenerator<String>{

    @Override
    public String randomValue(MockPlaceholder mockPlaceholder, MockCache cache) {
        int length = mockPlaceholder.getParameterOrDefault("length", int.class, 6);
        boolean uppercase = mockPlaceholder.getParameterOrDefault("uppercase", boolean.class, false);
        boolean lowercase = mockPlaceholder.getParameterOrDefault("lowercase", boolean.class, false);
        boolean letters = mockPlaceholder.getParameterOrDefault("letters", boolean.class, false);
        boolean numbers = mockPlaceholder.getParameterOrDefault("numbers", boolean.class, false);

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
}
