package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;

import java.util.Optional;

/**
 * 引用生成器
 * @author qiuzhenhao
 */
public class QuoteRandomGenerator implements RandomGenerator<Object>{
    @Override
    public Object randomValue(MockPlaceholder mockPlaceholder, MockCache mockCache) {
        Optional<String> idOptional = mockPlaceholder.getParameter("ref", String.class);
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
    public String name() {
        return "quote";
    }
}
