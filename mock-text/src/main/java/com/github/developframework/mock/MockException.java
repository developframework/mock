package com.github.developframework.mock;

import develop.framework.commons.exceptions.FormatRuntimeException;

/**
 * Mock异常
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class MockException extends FormatRuntimeException {

    public MockException(String message) {
        super(message);
    }

    public MockException(String format, Object... args) {
        super(format, args);
    }
}
