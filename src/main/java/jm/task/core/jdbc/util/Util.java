package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // JDBC
    private static final String URL = "jdbc:mysql://localhost:3306/dbtest";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Hibernate
    private static final SessionFactory sf = buildSF();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static SessionFactory buildSF() {
        try {
            Configuration conf = new Configuration();

            conf.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            conf.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            conf.setProperty("hibernate.connection.url", URL);
            conf.setProperty("hibernate.connection.username", USER);
            conf.setProperty("hibernate.connection.password", PASSWORD);
            conf.setProperty("hibernate.hbm2ddl.auto", "update");
            conf.setProperty("hibernate.show_sql", "true");

            conf.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

            ServiceRegistry serviceR = new StandardServiceRegistryBuilder()
                    .applySettings(conf.getProperties()).build();

            return conf.buildSessionFactory(serviceR);

        } catch (Throwable e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSF() {
        return sf;
    }

    public static void shutdown() {
        getSF().close();
    }
}