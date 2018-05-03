package com.github.developframework.mock.db;

import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.random.RandomFactory;
import com.github.developframework.toolkit.base.NameValuePair;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author qiuzhenhao
 */
public abstract class InsertSQLSubmitter {

    protected RandomFactory randomFactory;

    protected String database;

    protected String table;

    protected List<NameValuePair<String, MockPlaceholder>> fields = new LinkedList<>();

    public InsertSQLSubmitter(RandomFactory randomFactory) {
        this.randomFactory = randomFactory;
    }

    public InsertSQLSubmitter database(String database) {
        this.database = database;
        return this;
    }

    public InsertSQLSubmitter table(String table) {
        this.table = table;
        return this;
    }

    public InsertSQLSubmitter field(String field, String placeholder) {
        this.fields.add(new NameValuePair<>(field, new MockPlaceholder(placeholder)));
        return this;
    }

    public abstract int submit(String driver, String url, String user, String password) throws SQLException;

    public abstract int submitBatch(String driver, String url, String user, String password, int quantity) throws SQLException;

}
