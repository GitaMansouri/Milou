package session;


import org.hibernate.cfg.Configuration;

public class SessionFactory {
    private static org.hibernate.SessionFactory sessionFactory = null;

    public static SessionFactory get() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        }

        return (SessionFactory) sessionFactory;
    }

    public static void close() {
        if (sessionFactory == null) {
            return;
        }

        sessionFactory.close();
    }
}
