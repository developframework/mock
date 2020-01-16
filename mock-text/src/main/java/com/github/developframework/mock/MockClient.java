package com.github.developframework.mock;

import develop.toolkit.base.utils.ObjectAdvice;
import lombok.Getter;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 客户端
 *
 * @author qiuzhenhao
 *
 * @since 0.1
 */
@SuppressWarnings("unchecked")
public class MockClient {

    @Getter
    protected RandomGeneratorRegistry randomGeneratorRegistry = new RandomGeneratorRegistry();

    protected MockCache mockCache = new MockCache();

    /**
     * 清空缓存
     */
    public void clearCache() {
        mockCache.clear();
    }

    /**
     * 依据模板填充随机值
     * @param template 模板
     * @return 随机值字符串
     */
    public String mock(String template) {
        return new MockTask(randomGeneratorRegistry, mockCache, template).run();
    }

    /**
     * 构建实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T mock(Class<T> clazz) {
        T entity = ObjectAdvice.newInstanceQuietly(clazz);
        Field[] fields = FieldUtils.getFieldsWithAnnotation(clazz, Mock.class);
        for (Field field : fields) {
            Mock annotation = field.getAnnotation(Mock.class);
            Object object = complexMock(annotation.value(), field.getType(), annotation.with(), annotation.size());
            ObjectAdvice.set(entity, field.getName(), object, true);
        }
        return entity;
    }

    /**
     * 构建数组
     *
     * @param template
     * @param withClass
     * @param size
     * @param <T>
     * @return
     */
    public <T> T[] mockArray(String template, Class<T> withClass, int size) {
        T[] array = (T[]) Array.newInstance(withClass, size);
        for (int i = 0; i < size; i++) {
            array[i] = (T) complexMock(template, withClass, null, null);
        }
        return array;
    }

    /**
     * 构建列表
     *
     * @param template
     * @param withClass
     * @param size
     * @param <T>
     * @return
     */
    public <T> List<T> mockList(String template, Class<T> withClass, int size) {
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((T) complexMock(template, withClass, null, null));
        }
        return list;
    }

    /**
     * 构建集合
     *
     * @param template
     * @param withClass
     * @param size
     * @param <T>
     * @return
     */
    public <T> Set<T> mockSet(String template, Class<T> withClass, int size) {
        Set<T> set = new HashSet<>(size);
        for (int i = 0; i < size; i++) {
            set.add((T) complexMock(template, withClass, null, null));
        }
        return set;
    }

    /**
     * 复合构建
     *
     * @param template
     * @param clazz
     * @param withClass
     * @param size
     * @return
     */
    private Object complexMock(String template, Class<?> clazz, Class<?> withClass, Integer size) {
        if (clazz == String.class) {
            return mock(template);
        } else if (ObjectAdvice.isPrimitiveType(clazz)) {
            // 基本类型
            return ObjectAdvice.primitiveTypeCast(mock(template), clazz);
        } else if (clazz.isArray()) {
            // 数组类型
            Class<?> c = withClass == void.class ? clazz.getComponentType() : withClass;
            return mockArray(template, c, size);
        } else if (List.class.isAssignableFrom(clazz)) {
            // List类型
            return mockList(template, withClass, size);
        } else if (Set.class.isAssignableFrom(clazz)) {
            // Set类型
            return mockSet(template, withClass, size);
        } else {
            // 其它普通类
            return mock(clazz);
        }
    }
}
