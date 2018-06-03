package com.github.developframework.mock;

import com.github.developframework.mock.random.*;
import develop.framework.components.EntityRegistry;

/**
 * 生成器注册器
 * @author qiuzhenhao
 * @since 0.1
 */
public class RandomGeneratorRegistry extends EntityRegistry<RandomGenerator<?>, String> {

    @Override
    protected RandomGenerator<?>[] defaultEntity() {
        return new RandomGenerator[] {
                new StringRandomGenerator(),
                new NumberRandomGenerator(),
                new PersonNameRandomGenerator(),
                new MobileRandomGenerator(),
                new DateTimeRandomGenerator(),
                new DateRandomGenerator(),
                new TimeRandomGenerator(),
                new EnumRandomGenerator(),
                new BooleanRandomGenerator(),
                new QuoteRandomGenerator(),
                new IdentityCardRandomGenerator(),
                new AddressRandomGenerator(),
                new IPRandomGenerator()
        };
    }

    public void customRandomGenerators(RandomGenerator[] customRandomGenerators) {
        super.addCustomEntities(customRandomGenerators);
    }

    public RandomGenerator getRandomGenerator(String name) {
        return super.extractRequired(name, new MockException("\"%s\" generator is not exist.", name));
    }
}
