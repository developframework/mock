package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 人名随机生成器
 * @author qiuzhenhao
 */
public class PersonNameRandomGenerator implements RandomGenerator<String> {

    private String[] familyNameLib;
    private String[] nameLib;

    public PersonNameRandomGenerator() {
        List<String> familyNameList = new LinkedList<>();
        List<String> nameList = new LinkedList<>();
        boolean flag = false;
        try(InputStream is = getClass().getResourceAsStream("/person_name_chinese.txt")) {
            List<String> list = IOUtils.readLines(is, Charset.forName("UTF-8"));
            for (String line : list) {
                if(!flag && line.startsWith("--")) {
                    flag = true;
                    continue;
                }
                String[] words = line.split("");
                if(flag) {
                    for (String word : words) {
                        nameList.add(word);
                    }
                } else {
                    for (String word : words) {
                        familyNameList.add(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.familyNameLib = familyNameList.toArray(new String[familyNameList.size()]);
        this.nameLib = nameList.toArray(new String[nameList.size()]);
    }

    @Override
    public String randomValue(MockPlaceholder mockPlaceholder, MockCache cache) {
        Optional<Integer> lengthOptional = mockPlaceholder.getParameter("length", Integer.class);
        boolean isOnlyName = mockPlaceholder.getParameterOrDefault("onlyName", boolean.class, false);
        int length;
        if(isOnlyName) {
            length = 3;
        } else {
            length = RandomUtils.nextBoolean() ? 3 : 2;
            if (lengthOptional.isPresent()) {
                length = lengthOptional.get();
            }
            if(length < 2) {
                throw new MockException("person name least 2 words.");
            }
        }
        String r;
        if(isOnlyName) {
            r = "";
        } else {
            String familyName = familyNameLib[RandomUtils.nextInt(0, familyNameLib.length)];
            r = familyName;
        }
        for (int i = 0; i < length - 1; i++) {
            r += nameLib[RandomUtils.nextInt(0, nameLib.length)];
        }
        return r;
    }

    @Override
    public String name() {
        return "personName";
    }
}
