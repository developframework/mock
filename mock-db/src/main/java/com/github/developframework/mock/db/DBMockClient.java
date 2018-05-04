package com.github.developframework.mock.db;

import com.github.developframework.mock.MockClient;
import com.github.developframework.mock.random.RandomGeneratorFactory;
import lombok.Getter;

/**
 * @author qiuzhenhao
 */
public class DBMockClient extends MockClient{

    @Getter
    private RandomGeneratorFactory randomGeneratorFactory = new RandomGeneratorFactory();

    public MysqlInsertSQLSubmitter byMysql() {
        return new MysqlInsertSQLSubmitter(randomGeneratorFactory);
    }
}
