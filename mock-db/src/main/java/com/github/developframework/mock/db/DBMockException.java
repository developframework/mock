package com.github.developframework.mock.db;


import com.github.developframework.mock.MockException;

/**
 * @author qiuzhenhao
 */
public class DBMockException extends MockException {

    public DBMockException(String message) {
        super(message);
    }

    public DBMockException(String format, Object args) {
        super(format, args);
    }
}
