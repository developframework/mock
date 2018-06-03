package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        try (InputStream is = getClass().getResourceAsStream("/person_name_chinese.txt")) {
            List<String> list = IOUtils.readLines(is, Charset.forName("UTF-8"));
            for (String line : list) {
                if (line.startsWith("--")) {
                    flag++;
                    continue;
                }
                String[] words = line.split("");
                switch (flag) {
                    case 0: {
                        familyNameList.addAll(Arrays.asList(words));
                    }
                    break;
                    case 1: {
                        maleNameList.addAll(Arrays.asList(words));
                    }
                    break;
                    case 2: {
                        femaleNameList.addAll(Arrays.asList(words));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            throw new MockException("mock client read resource \"person_name_chinese.txt\" failed, " + e.getMessage());
        }
        this.familyNameLib = familyNameList.toArray(new String[familyNameList.size()]);
        this.maleNameLib = maleNameList.toArray(new String[maleNameList.size()]);
        this.femaleNameLib = femaleNameList.toArray(new String[femaleNameList.size()]);
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
            case "FEMALE":
                isMale = false;
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
