package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * 数值随机生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class NumberRandomGenerator implements RandomGenerator<Number> {

    private static final String PARAMETER_MAX = "max";
    private static final String PARAMETER_MIN = "min";
    private static final String PARAMETER_DIGIT = "digit";
    private static final String PARAMETER_DECIMALS = "decimals";
    private static final String PARAMETER_FILL_ZERO = "fillZero";


    @Override
    public Number randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache cache) {
        Number min = mockPlaceholder.getParameterOrDefault(PARAMETER_MIN, Number.class, 0);
        Number max = mockPlaceholder.getParameterOrDefault(PARAMETER_MAX, Number.class, 100);
        Optional<Integer> digitOptional = mockPlaceholder.getParameter(PARAMETER_DIGIT, Integer.class);
        if (min.doubleValue() > max.doubleValue()) {
            throw new MockException("min value greater than max value.");
        }
        boolean isDecimals = mockPlaceholder.getParameterOrDefault(PARAMETER_DECIMALS, boolean.class, false);
        Number result = RandomUtils.nextDouble(min.doubleValue(), max.doubleValue());
        if (isDecimals) {
            if (digitOptional.isPresent()) {
                return new BigDecimal(result.doubleValue()).setScale(digitOptional.get(), RoundingMode.CEILING);
            } else {
                return result;
            }
        }
        return result.intValue();
    }

    @Override
    public String key() {
        return "number";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, Number value) {
        Optional<Integer> fillZeroOptional = mockPlaceholder.getParameter(PARAMETER_FILL_ZERO, Integer.class);
        if (fillZeroOptional.isPresent()) {
            return String.format("%0" + fillZeroOptional.get() + "d", value.intValue());
        } else {
            return String.valueOf(value);
        }
    }
}
