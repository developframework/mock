package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * 数值随机生成器
 *
 * @author qiuzhenhao
 */
public class NumberRandomGenerator implements RandomGenerator<Number> {

    @Override
    public Number randomValue(MockPlaceholder mockPlaceholder, MockCache cache) {
        Number min = mockPlaceholder.getParameterOrDefault("min", Number.class, 0);
        Number max = mockPlaceholder.getParameterOrDefault("max", Number.class, 100);
        Optional<Integer> digitOptional = mockPlaceholder.getParameter("digit", Integer.class);
        if(min.doubleValue() > max.doubleValue()) {
            throw new MockException("min value greater than max value.");
        }
        boolean isDecimals = mockPlaceholder.getParameterOrDefault("decimals", boolean.class, false);
        Number result = new Double(RandomUtils.nextDouble(min.doubleValue(), max.doubleValue()));
        if (isDecimals) {
            if(digitOptional.isPresent()) {
                return new BigDecimal(result.doubleValue()).setScale(digitOptional.get(), RoundingMode.CEILING);
            } else {
                return result;
            }
        }
        return result.intValue();
    }

    @Override
    public String name() {
        return "number";
    }
}
