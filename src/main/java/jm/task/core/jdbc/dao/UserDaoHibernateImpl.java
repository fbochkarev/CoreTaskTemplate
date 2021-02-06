package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.toString());

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE users(\n" +
                "   id INT NOT NULL AUTO_INCREMENT,\n" +
                "   name VARCHAR(20) NOT NULL,\n" +
                "   lastName VARCHAR(20) NOT NULL,\n" +
                "   age INT NOT NULL ,\n" +
                "   PRIMARY KEY ( id )\n" +
                ");";
        try {
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (IllegalStateException | PersistenceException e) {
            logger.warning("Содание таблицы, предупреждение - " + e);
        }

        session.close();
        System.out.println("Таблица создана");
    }

    @Override
    @Transactional
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE users;";

        try {
            session.createSQLQuery(sql).executeUpdate();
        } catch (Exception e) {
            System.out.println("Таблицы Users не существует." + e);
        }

        transaction.commit();
        session.close();
        System.out.println("Таблица удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        session.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("FROM User").list();

        transaction.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        transaction.commit();
        session.close();

        System.out.println("Table is cleaner");
    }

    @Override
    public void connectionClose() throws SQLException {

    }
}
