package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import com.github.developframework.regiondata.ChinaRegionProvider;
import com.github.developframework.regiondata.County;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * 随机地址生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class AddressRandomGenerator implements RandomGenerator<County>{

    private static final String PARAMETER_LEVEL = "level";

    private static ChinaRegionProvider chinaRegionProvider = new ChinaRegionProvider();

    private List<County> counties;

    public AddressRandomGenerator() {
        counties = chinaRegionProvider.getChina().getAllCounties();
    }

    @Override
    public County randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        return counties.get(RandomUtils.nextInt(0, counties.size()));
    }

    @Override
    public String name() {
        return "address";
    }

    @Override
    public String forString(MockPlaceholder mockPlaceholder, County value) {
        int level = mockPlaceholder.getParameterOrDefault(PARAMETER_LEVEL, int.class, 3);
        String address;
        switch(level) {
            case 1: {
                address = value.getCity().getProvince().getName();
            }break;
            case 2: {
                address = value.getCity().getProvince().getName() + value.getCity().getName();
            }break;
            default: {
                address = value.getCity().getProvince().getName() + value.getCity().getName() + value.getName();
            }break;
        }
        return address;
    }
}
