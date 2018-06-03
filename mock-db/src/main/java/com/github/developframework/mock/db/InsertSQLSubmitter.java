package com.github.developframework.mock.db;

import com.github.developframework.mock.MockCache;
import com.github.developframework.mock.MockTask;
import com.github.developframework.mock.RandomGeneratorRegistry;
import develop.framework.components.KeyValuePair;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author qiuzhenhao
 */
public abstract class InsertSQLSubmitter {

    protected RandomGeneratorRegistry randomGeneratorRegistry;

    protected String database;

    protected String table;

    protected List<KeyValuePair<String, MockTask>> fields = new LinkedList<>();

    protected DBInfo dbInfo;

    protected MockCache cache;

    public InsertSQLSubmitter(RandomGeneratorRegistry randomGeneratorRegistry, MockCache cache, DBInfo dbInfo) {
        this.randomGeneratorRegistry = randomGeneratorRegistry;
        this.cache = cache;
        this.dbInfo = dbInfo;
    }

    public InsertSQLSubmitter database(String database) {
        this.database = database;
        return this;
    }

    public InsertSQLSubmitter table(String table) {
        this.table = table;
        return this;
    }

    public InsertSQLSubmitter field(String field, String template) {
        this.fields.add(new KeyValuePair<>(field, new MockTask(randomGeneratorRegistry, cache, template)));
        return this;
    }

    public int submit() throws SQLException {
        return submit(1);
    }

    public abstract int submit(int quantity) throws SQLException;

}
