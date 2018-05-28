package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockPlaceholder;
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
public class AddressRandomGenerator implements RandomGenerator<String>{

    private static ChinaRegionProvider chinaRegionProvider = new ChinaRegionProvider();

    private List<County> counties;

    public AddressRandomGenerator() {
        counties = chinaRegionProvider.getChina().getAllCounties();
    }

    @Override
    public String randomValue(MockPlaceholder mockPlaceholder, MockCache mockCache) {
        int level = mockPlaceholder.getParameterOrDefault("level", int.class, 3);
        if(level < 1 || level > 3) {
            level = 3;
        }
        County randomCounty = counties.get(RandomUtils.nextInt(0, counties.size()));
        String result = null;
        switch(level) {
            case 1: {
                result = randomCounty.getName();
            }break;
            case 2: {
                result = randomCounty.getCity().getName() + randomCounty.getName();
            }break;
            case 3: {
                result = randomCounty.getCity().getProvince().getName() + randomCounty.getCity().getName() + randomCounty.getName();
            }break;
        }
        return result;
    }

    @Override
    public String name() {
        return "address";
    }
}
