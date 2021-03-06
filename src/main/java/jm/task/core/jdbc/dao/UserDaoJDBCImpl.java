package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void connectionClose() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void createUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        connection.setAutoCommit(false);

        String sql = "CREATE TABLE users(\n" +
                "   id INT NOT NULL AUTO_INCREMENT,\n" +
                "   name VARCHAR(20) NOT NULL,\n" +
                "   lastName VARCHAR(20) NOT NULL,\n" +
                "   age INT NOT NULL ,\n" +
                "   PRIMARY KEY ( id )\n" +
                ");";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблица Users уже" +
                    " существует.");;
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        connection.setAutoCommit(true);
    }

    public void dropUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        connection.setAutoCommit(false);

        String sql = "DROP TABLE users;";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблицы Users не существует.");;
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        connection.setAutoCommit(true);
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;
        connection.setAutoCommit(false);

        String sql = "INSERT INTO jm_db.users (NAME, LASTNAME, AGE) " +
                "VALUES(?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setLong(3, age);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        }
        connection.setAutoCommit(true);
    }

    public void removeUserById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        connection.setAutoCommit(false);

        String sql = "DELETE FROM jm_db.users WHERE ID=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        connection.setAutoCommit(true);
    }

    public List<User> getAllUsers() throws SQLException {
        connection.setAutoCommit(false);
        List<User> userList = new ArrayList<>();

        String sql = "SELECT ID, NAME, LASTNAME, AGE " +
                "FROM jm_db.users";

        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge((byte) resultSet.getLong("AGE"));

                userList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        connection.setAutoCommit(true);
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        connection.setAutoCommit(false);

        String sql = "TRUNCATE TABLE users;";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            System.out.println("Таблица очищена");
        }
        connection.setAutoCommit(true);
    }
}
