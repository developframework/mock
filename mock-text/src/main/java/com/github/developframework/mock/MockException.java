package com.github.developframework.mock;

import com.github.developframework.toolkit.base.FormatRuntimeException;

/**
 * @author qiuzhenhao
 */
public class MockException extends FormatRuntimeException {

    public MockException(String message) {
        super(message);
    }

    public MockException(String format, Object args) {
        super(format, args);
    }
}
