package com.github.developframework.mock.db;

import com.github.developframework.mock.MockPlaceholder;
import com.github.developframework.mock.random.RandomFactory;
import com.github.developframework.mock.random.RandomGenerator;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * @author qiuzhenhao
 */
public class MysqlInsertSQLSubmitter extends InsertSQLSubmitter {

    public MysqlInsertSQLSubmitter(RandomFactory randomFactory) {
        super(randomFactory);
    }

    private String build() {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        if (database != null) {
            sb.append("`").append(database).append("`.");
        }
        if (table == null) {
            throw new DBMockException("table is undefined");
        }
        sb.append('`').append(table).append("`(");
        if (fields.isEmpty()) {
            throw new DBMockException("fields is empty.");
        }
        sb.append(StringUtils.join(fields.stream().map(pair -> "`" + pair.getName() + "`").collect(Collectors.toList()), ", "));
        sb.append(") VALUES(");
        sb.append(StringUtils.join(fields.stream().map(pair -> "?").collect(Collectors.toList()), ", "));
        sb.append(')');
        return sb.toString();
    }

    @Override
    public int submit(String driver, String url, String user, String password) throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement(build());
        for (int i = 0; i < fields.size(); i++) {
            MockPlaceholder mockPlaceholder = fields.get(i).getValue();
            RandomGenerator randomGenerator = randomFactory.getRandomGenerator(mockPlaceholder.getName());
            Object value = randomGenerator.randomValue(mockPlaceholder, null);
            preparedStatement.setString(i + 1, value.toString());
        }
        int r = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return r;
    }

    @Override
    public int submitBatch(String driver, String url, String user, String password, int quantity) throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(build());
        int r = 0;
        try {
            for (int i = 0; i < quantity; i++) {
                for (int j = 0; j < fields.size(); j++) {
                    MockPlaceholder mockPlaceholder = fields.get(j).getValue();
                    RandomGenerator randomGenerator = randomFactory.getRandomGenerator(mockPlaceholder.getName());
                    Object value = randomGenerator.randomValue(mockPlaceholder, null);
                    preparedStatement.setString(j + 1, value.toString());
                }
                r += preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
            connection.rollback();
        }
        connection.commit();
        preparedStatement.close();
        connection.close();
        return r;
    }
}
