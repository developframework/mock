package com.github.developframework.mock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

/**
 * @author qiuzhenhao
 */
public class MockCache extends HashMap<String, MockCache.Cache> {

    @Data
    @AllArgsConstructor
    public static class Cache {

        private Object value;

        private MockPlaceholder mockPlaceholder;
    }
}
