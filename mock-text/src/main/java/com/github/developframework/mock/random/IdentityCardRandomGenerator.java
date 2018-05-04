package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author qiuzhenhao
 */
public class IdentityCardRandomGenerator extends DateTimeRandomGenerator{


    @Override
    public String randomValue(MockPlaceholder mockPlaceholder, MockCache mockCache) {
        String birthdayRef = mockPlaceholder.getParameterOrDefault("birthday-ref", String.class, "random");
        String range = mockPlaceholder.getParameterOrDefault("range", String.class, "30y");
        return getBirthday(birthdayRef, range, mockCache);
    }

    @Override
    public String name() {
        return "identityCard";
    }

    private String getBirthday(String birthday, String range, MockCache mockCache) {
        Date date = null;
        if(birthday.equals("random")) {
            // 随机取生日
            long max = calcRange(range);
            long dateLong = System.currentTimeMillis() - RandomUtils.nextLong(1000, max);
            date = new Date(dateLong);
        } else {
            // 引用取生日
            MockCache.Cache cache = mockCache.get(birthday);
            if(cache == null) {
                throw new MockException("identityCard \"%s\" value is undefined.", birthday);
            }
            String pattern = cache.getMockPlaceholder().getParameterOrDefault("pattern", String.class, "yyyy-MM-dd");
            try {
                date = DateUtils.parseDate((String) cache.getValue(), pattern);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        birthday = DateFormatUtils.format(date, "yyyyMMdd");

        return RandomStringUtils.randomNumeric(6) + birthday + RandomStringUtils.randomNumeric(3)
                + RandomStringUtils.random(1, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X'});
    }
}
