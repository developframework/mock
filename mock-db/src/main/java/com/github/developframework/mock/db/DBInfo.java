package com.github.developframework.mock.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qiuzhenhao
 */
@AllArgsConstructor
@Getter
@Setter
public class DBInfo {

    private String driver;

    private String url;

    private String user;

    private String password;
}
