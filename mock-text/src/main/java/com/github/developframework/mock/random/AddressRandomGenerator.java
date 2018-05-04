package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.toolkit.base.region.Area;
import com.github.developframework.toolkit.base.region.RegionModule;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * @author qiuzhenhao
 */
public class AddressRandomGenerator implements RandomGenerator<String>{

    private RegionModule regionModule = new RegionModule();

    private List<Area> areas;

    public AddressRandomGenerator() {
        areas = regionModule.getCountry().allAreas();
    }

    @Override
    public String randomValue(MockPlaceholder mockPlaceholder, MockCache mockCache) {
        int level = mockPlaceholder.getParameterOrDefault("level", int.class, 3);
        if(level < 1 || level > 3) {
            level = 3;
        }
        Area randomArea = areas.get(RandomUtils.nextInt(0, areas.size()));
        String result = null;
        switch(level) {
            case 1: {
                result = randomArea.getName();
            }break;
            case 2: {
                result = randomArea.getCity().getName() + randomArea.getName();
            }break;
            case 3: {
                result = randomArea.getCity().getProvince().getName() + randomArea.getCity().getName() + randomArea.getName();
            }break;
        }
        return result;
    }

    @Override
    public String name() {
        return "address";
    }
}
