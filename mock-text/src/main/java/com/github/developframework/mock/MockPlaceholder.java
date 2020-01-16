package com.github.developframework.mock;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 占位符
 *
 * @author qiuzhenhao
 * @since 0.1
 */
@Getter
public class MockPlaceholder {

    private String placeholder;

    private String name;

    private Map<String, Object> parameters;

    public MockPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (placeholder.contains("|")) {
            String[] fragments = placeholder.split("\\s*\\|\\s*");
            this.name = fragments[0].trim();
            if(fragments.length > 1) {
                this.parameters = createParameters(fragments[1].trim());
            } else {
                this.parameters = Collections.emptyMap();
            }
        } else {
            this.name = placeholder;
            this.parameters = Collections.emptyMap();
        }
    }

    private Map<String, Object> createParameters(String parameterFragment) {
        Map<String, Object> map = new HashMap<>();
        String[] parameterArray = parameterFragment.split(",");
        for (String parameterStr : parameterArray) {
            if(parameterStr.contains("=")) {
                String[] split = parameterStr.split("=");
                map.put(split[0].trim(), guessType(split[1].trim()));
            } else {
                map.put(parameterStr.trim(), true);
            }
        }
        return map;
    }

    private Object guessType(String value) {
        if(StringUtils.isBlank(value)) {
            return null;
        } else if(value.matches("^'.*'$")) {
            return value.substring(1, value.length() - 1);
        } else if(value.matches("^\\d+$")) {
            return Integer.valueOf(value);
        } else if(value.matches("^\\d+\\.\\d+$")) {
            return Double.valueOf(value);
        } else if (value.equals("true") || value.equals("false")) {
            return Boolean.valueOf(value);
        } else {
            return value;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getParameter(String name, Class<T> clazz) {
        Object obj = parameters.get(name);
        return obj == null ? Optional.empty() : Optional.of((T) obj);
    }

    @SuppressWarnings("unchecked")
    public <T> T getParameterOrDefault(String name, Class<T> clazz, T defValue) {
        Object obj = parameters.get(name);
        return obj == null ? defValue : (T) obj;
    }

    public Optional<String> getId() {
        return getParameter("id", String.class);
    }

    @Override
    public String toString() {
        return String.format("${%s}", placeholder);
    }
}
