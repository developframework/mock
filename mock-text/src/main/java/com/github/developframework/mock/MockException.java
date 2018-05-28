package com.github.developframework.mock;

/**
 *
 *
 * @author qiuzhenhao
 * @since 0.1
 */
public class MockException extends RuntimeException {

    public MockException(String message) {
        super(message);
    }

    public MockException(String format, Object... args) {
        super(String.format(format, args));
    }
}
