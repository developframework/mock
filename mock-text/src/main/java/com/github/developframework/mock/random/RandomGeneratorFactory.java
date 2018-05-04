package com.github.developframework.mock.random;

import com.github.developframework.mock.MockException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qiuzhenhao
 */
public class RandomGeneratorFactory {

    private RandomGenerator[] defaultRandomGenerators = {
            new StringRandomGenerator(),
            new NumberRandomGenerator(),
            new PersonNameRandomGenerator(),
            new MobileRandomGenerator(),
            new DateRandomGenerator(),
            new DateTimeRandomGenerator(),
            new BooleanRandomGenerator(),
            new QuoteRandomGenerator(),
            new IdentityCardRandomGenerator(),
            new AddressRandomGenerator()
    };

    private Map<String, RandomGenerator> randomGeneratorMap = new HashMap<>();

    public RandomGeneratorFactory() {
        for (RandomGenerator randomGenerator : defaultRandomGenerators) {
            randomGeneratorMap.put(randomGenerator.name(), randomGenerator);
        }
    }

    public void customRandomGenerators(RandomGenerator[] customRandomGenerators) {
        for (RandomGenerator customRandomGenerator : customRandomGenerators) {
            randomGeneratorMap.put(customRandomGenerator.name(), customRandomGenerator);
        }
    }

    public RandomGenerator getRandomGenerator(String name) {
        if (randomGeneratorMap.containsKey(name)) {
            return randomGeneratorMap.get(name);
        } else {
            throw new MockException("\"%s\" generator is not exist.", name);
        }
    }
}
