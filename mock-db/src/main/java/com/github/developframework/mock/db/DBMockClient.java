package com.github.developframework.mock.db;

import com.github.developframework.mock.MockClient;
import com.github.developframework.mock.random.RandomFactory;
import lombok.Getter;

/**
 * @author qiuzhenhao
 */
public class DBMockClient extends MockClient{

    @Getter
    private RandomFactory randomFactory = new RandomFactory();

    public MysqlInsertSQLSubmitter byMysql() {
        return new MysqlInsertSQLSubmitter(randomFactory);
    }
}
