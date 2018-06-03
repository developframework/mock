package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;

import java.util.Optional;

/**
 * 引用生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class QuoteRandomGenerator implements RandomGenerator<Object>{

    private static final String PARAMETER_REF = "ref";

    @Override
    public Object randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        Optional<String> idOptional = mockPlaceholder.getParameter(PARAMETER_REF, String.class);
        if (idOptional.isPresent()) {
            MockCache.Cache cache = mockCache.get(idOptional.get());
            if(cache == null) {
                throw new MockException("quote \"%s\" value is undefined.", idOptional.get());
            }
            return cache.getValue();
        }
        throw new MockException("\"id\" is not exist.");
    }

    @Override
    public String key() {
        return "quote";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, Object value) {
        return String.valueOf(value);
    }
}
