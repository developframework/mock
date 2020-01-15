package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import develop.toolkit.base.utils.IOAdvice;
import org.apache.commons.lang3.RandomUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 人名随机生成器
 *
 * @author qiuzhenhao
 */
public class PersonNameRandomGenerator implements RandomGenerator<String> {

    private static final String PARAMETER_LENGTH = "length";
    private static final String PARAMETER_ONLY_NAME = "onlyName";
    private static final String PARAMETER_SEX = "sex";

    private String[] familyNameLib;
    private String[] maleNameLib;
    private String[] femaleNameLib;

    public PersonNameRandomGenerator() {
        List<String> familyNameList = new LinkedList<>();
        List<String> maleNameList = new LinkedList<>();
        List<String> femaleNameList = new LinkedList<>();
        int flag = 0;
        List<String> list = IOAdvice.readLinesFromClasspath("/person_name_chinese.txt").collect(Collectors.toList());
        for (String line : list) {
            if (line.startsWith("--")) {
                flag++;
                continue;
            }
            String[] words = line.split("");
            switch (flag) {
                case 0: {
                    familyNameList.addAll(List.of(words));
                }
                break;
                case 1: {
                    maleNameList.addAll(List.of(words));
                }
                break;
                case 2: {
                    femaleNameList.addAll(List.of(words));
                }
                break;
            }
        }
        this.familyNameLib = familyNameList.toArray(String[]::new);
        this.maleNameLib = maleNameList.toArray(String[]::new);
        this.femaleNameLib = femaleNameList.toArray(String[]::new);
    }

    @Override
    public String randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache cache) {
        int length = mockPlaceholder.getParameterOrDefault(PARAMETER_LENGTH, Integer.class, RandomUtils.nextBoolean() ? 3 : 2);
        if (length < 2) {
            throw new MockException("person name least 2 words.");
        }
        boolean isOnlyName = mockPlaceholder.getParameterOrDefault(PARAMETER_ONLY_NAME, boolean.class, false);
        String sex = mockPlaceholder.getParameterOrDefault(PARAMETER_SEX, String.class, "UNKNOWN");
        boolean isMale;
        switch (sex) {
            case "MALE":
                isMale = true;
                break;
            case "FEMALE":
                isMale = false;
                break;
            default:
                isMale = RandomUtils.nextBoolean();
        }
        String r = isOnlyName ? "" : familyNameLib[RandomUtils.nextInt(0, familyNameLib.length)];
        for (int i = 0; i < length - 1; i++) {
            if (isMale) {
                r += maleNameLib[RandomUtils.nextInt(0, maleNameLib.length)];
            } else {
                r += femaleNameLib[RandomUtils.nextInt(0, femaleNameLib.length)];
            }
        }
        return r;
    }

    @Override
    public String key() {
        return "personName";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, String value) {
        return value;
    }
}
