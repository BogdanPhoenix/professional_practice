package org.university.ui.realization.panel_interaction.logic;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    private HibernateUtil() {
        throw new IllegalStateException("Utility class HibernateUtil");
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
