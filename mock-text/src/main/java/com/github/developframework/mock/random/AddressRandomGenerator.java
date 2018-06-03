package com.github.developframework.mock.random;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockException;
import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.RandomGeneratorRegistry;
import com.github.developframework.regiondata.ChinaRegionProvider;
import com.github.developframework.regiondata.City;
import com.github.developframework.regiondata.County;
import com.github.developframework.regiondata.Province;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Optional;

/**
 * 随机地址生成器
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class AddressRandomGenerator implements RandomGenerator<County>{

    private static final String PARAMETER_LEVEL = "level";
    private static final String PARAMETER_PROVINCE = "province";
    private static final String PARAMETER_CITY = "city";

    private static ChinaRegionProvider chinaRegionProvider = new ChinaRegionProvider();

    @Override
    public County randomValue(RandomGeneratorRegistry randomGeneratorRegistry, MockPlaceholder mockPlaceholder, MockCache mockCache) {
        List<County> counties;

        Optional<String> provinceOptional = mockPlaceholder.getParameter(PARAMETER_PROVINCE, String.class);

        if (provinceOptional.isPresent()) {
            Optional<Province> provinceByName = chinaRegionProvider.getChina().getProvinceByName(provinceOptional.get());
            if(provinceByName.isPresent()) {
                Optional<String> cityOptional = mockPlaceholder.getParameter(PARAMETER_CITY, String.class);
                if(cityOptional.isPresent()) {
                    Optional<City> cityByName = provinceByName.get().getCityByName(cityOptional.get());
                    if(cityByName.isPresent()) {
                        counties = cityByName.get().getAllCounties();
                    } else {
                        throw new MockException("address random generator city attribute \"%s\" value is not exist.", cityOptional.get());
                    }
                } else {
                    counties = provinceByName.get().getAllCounties();
                }
            } else {
                throw new MockException("address random generator province attribute \"%s\" value is not exist.", provinceOptional.get());
            }
        } else {
            counties = chinaRegionProvider.getChina().getAllCounties();
        }
        return counties.get(RandomUtils.nextInt(0, counties.size()));
    }

    @Override
    public String key() {
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
