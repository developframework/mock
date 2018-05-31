package com.github.developframework.mock.db;

import com.github.developframework.mock.MockClient;
import lombok.Getter;

/**
 * 数据库随机数据客户端
 *
 * @author qiuzhenhao
 */
public class DBMockClient extends MockClient{

    @Getter
    private DBInfo dbInfo;

    public DBMockClient(String driver, String url, String user, String password) {
        this.dbInfo = new DBInfo(driver, url, user, password);
    }

    /**
     * mysql insert语句的提交器
     *
     * @return 提交器
     */
    public MysqlInsertSQLSubmitter insertByMysql() {
        return new MysqlInsertSQLSubmitter(randomGeneratorRegistry, mockCache, dbInfo);
    }
}
