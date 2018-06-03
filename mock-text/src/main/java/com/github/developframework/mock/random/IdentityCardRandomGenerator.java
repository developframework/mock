package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import com.github.developframework.regiondata.County;
import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 随机身份证号生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class IdentityCardRandomGenerator implements RandomGenerator<IdentityCardRandomGenerator.IdentityCard> {

    private static final String PARAMETER_ADDRESS_REF = "address-ref";
    private static final String PARAMETER_BIRTHDAY_REF = "birthday-ref";
    private static final String PARAMETER_SEX_REF = "sex-ref";
    private static final String PARAMETER_RANGE = "range";

    @Override
    public IdentityCard randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        County address = getAddress(randomGeneratorRegistry, mockPlaceholder, mockCache);
        Date birthday = getBirthday(randomGeneratorRegistry, mockPlaceholder, mockCache);
        int number = getNumber(randomGeneratorRegistry, mockPlaceholder, mockCache);
        return new IdentityCard(address, birthday, number);
    }

    @Override
    public String key() {
        return "identityCard";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, IdentityCard value) {
        return value.toString();
    }

    private County getAddress(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        String addressRef = mockPlaceholder.getParameterOrDefault(PARAMETER_ADDRESS_REF, String.class, "random");
        if (addressRef.equals("random")) {
            // 随机生成一个地址
            RandomGenerator addressRandomGenerator = randomGeneratorRegistry.getRandomGenerator("address");
            MockPlaceholder addressMockPlaceholder = new MockPlaceholder("${ address }");
            return (County) addressRandomGenerator.randomValue(randomGeneratorRegistry, addressMockPlaceholder, mockCache);
        } else {
            // 引用一个地址
            MockCache.Cache cache = mockCache.get(addressRef);
            if (cache == null) {
                throw new MockException("identityCard address-ref \"%s\" value is undefined.", addressRef);
            }
            Object value = cache.getValue();
            if (value instanceof County) {
                return (County) cache.getValue();
            } else {
                throw new MockException("identityCard address-ref \"%s\" value is not a java.util.Date instance.", addressRef);
            }
        }
    }

    private Date getBirthday(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        String birthdayRef = mockPlaceholder.getParameterOrDefault(PARAMETER_BIRTHDAY_REF, String.class, "random");
        String range = mockPlaceholder.getParameterOrDefault(PARAMETER_RANGE, String.class, "30y");
        if (birthdayRef.equals("random")) {
            // 随机生成一个生日
            RandomGenerator dateRandomGenerator = randomGeneratorRegistry.getRandomGenerator("date");
            MockPlaceholder dateMockPlaceholder = new MockPlaceholder("${ date | range = " + range + " }");
            return (Date) dateRandomGenerator.randomValue(randomGeneratorRegistry, dateMockPlaceholder, mockCache);
        } else {
            // 引用取生日
            MockCache.Cache cache = mockCache.get(birthdayRef);
            if (cache == null) {
                throw new MockException("identityCard birthday-ref \"%s\" value is undefined.", birthdayRef);
            }
            Object value = cache.getValue();
            if (value instanceof Date) {
                return (Date) cache.getValue();
            } else {
                throw new MockException("identityCard birthday-ref \"%s\" value is not a java.util.Date instance.", birthdayRef);
            }
        }
    }

    private int getNumber(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        String sexRef = mockPlaceholder.getParameterOrDefault(PARAMETER_SEX_REF, String.class, "random");
        if (sexRef.equals("random")) {
            // 随机生成3位数字
            return RandomUtils.nextInt(0, 100);
        } else {
            // 按性别生成3位数字
            int[] femaleNumber = sexRef.equals("FEMALE") ? new int[]{0, 2, 4, 6, 8} : new int[]{1, 3, 5, 7, 9};
            return RandomUtils.nextInt(0, 9) + femaleNumber[RandomUtils.nextInt(0, 5)];
        }
    }

    @Getter
    public static class IdentityCard {

        private County address;

        private Date birthday;

        private int number;

        private char lastCode;

        public IdentityCard(County address, Date birthday, int number) {
            this.address = address;
            this.birthday = birthday;
            this.number = number;
            String front17Chars = address.getCode() + DateFormatUtils.format(birthday, "yyyyMMdd") + String.format("%03d", number);
            this.lastCode = computeLastCode(front17Chars);
        }

        @Override
        public String toString() {
            return address.getCode()
                    + DateFormatUtils.format(birthday, "yyyyMMdd")
                    + String.format("%03d", number)
                    + lastCode;
        }

        /**
         * 计算身份证最后一位校验码
         * @param front17Chars 前17位数字字符串
         * @return 校验码
         */
        private char computeLastCode(String front17Chars) {
            final int[] VERIFY_NUMBERS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
            final char[] TARGET_CHARS = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
            int sum = 0;
            for (int i = 0; i < front17Chars.length(); i++) {
                sum += (front17Chars.charAt(i) - '0') * VERIFY_NUMBERS[i];
            }
            return TARGET_CHARS[sum % 11];
        }
    }
}
