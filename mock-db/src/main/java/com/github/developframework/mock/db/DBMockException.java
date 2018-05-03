package com.github.developframework.mock.db;

import com.github.developframework.toolkit.base.FormatRuntimeException;

/**
 * @author qiuzhenhao
 */
public class DBMockException extends FormatRuntimeException {

    public DBMockException(String message) {
        super(message);
    }

    public DBMockException(String format, Object args) {
        super(format, args);
    }
}
