package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import com.github.developframework.regiondata.County;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
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
    private static final String PARAMETER_RANGE = "range";

    @Override
    public IdentityCard randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        String lastCode = RandomStringUtils.random(1, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X'});
        County address = getAddress(randomGeneratorRegistry, mockPlaceholder, mockCache);
        Date birthday = getBirthday(randomGeneratorRegistry, mockPlaceholder, mockCache);
        return new IdentityCard(address, birthday, lastCode);
    }

    @Override
    public String name() {
        return "identityCard";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, IdentityCard value) {
        return value.getAddress().getCode()
                + DateFormatUtils.format(value.getBirthday(), "yyyyMMdd")
                + RandomStringUtils.randomNumeric(3)
                + value.getLastCode();
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

    @Getter
    public static class IdentityCard {

        private County address;

        private Date birthday;

        private String lastCode;

        public IdentityCard(County address, Date birthday, String lastCode) {
            this.address = address;
            this.birthday = birthday;
            this.lastCode = lastCode;
        }
    }
}
