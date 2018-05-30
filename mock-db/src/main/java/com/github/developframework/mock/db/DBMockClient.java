package com.github.developframework.mock.db;

import com.github.developframework.mock.MockClient;
import com.github.developframework.mock.RandomGeneratorRegistry;
import lombok.Getter;

/**
 * @author qiuzhenhao
 */
public class DBMockClient extends MockClient{

    @Getter
    private DBInfo dbInfo;

    public DBMockClient(String driver, String url, String user, String password) {
        this.dbInfo = new DBInfo(driver, url, user, password);
    }

    @Getter
    private RandomGeneratorRegistry randomGeneratorRegistry = new RandomGeneratorRegistry();

    public MysqlInsertSQLSubmitter byMysql() {
        return new MysqlInsertSQLSubmitter(randomGeneratorRegistry, dbInfo);
    }
}
