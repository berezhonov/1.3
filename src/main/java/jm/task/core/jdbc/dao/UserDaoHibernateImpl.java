package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        String db = "CREATE TABLE IF NOT EXISTS User (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT)";
        try (Session session = Util.getSF().openSession()) {
            Transaction trans = session.beginTransaction();
            session.createNativeQuery(db).executeUpdate();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String db = "DROP TABLE IF EXISTS User";
        try (Session session = Util.getSF().openSession()) {
            Transaction trans = session.beginTransaction();
            session.createNativeQuery(db).executeUpdate();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSF().openSession()) {
            Transaction trans = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.getSF().openSession()) {
            users = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSF().openSession()) {
            Transaction trans = session.beginTransaction();
            session.createNativeQuery("DELETE FROM mysql.User").executeUpdate();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
