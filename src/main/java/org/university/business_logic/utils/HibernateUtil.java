package org.university.business_logic.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.university.ui.additional_tools.MessageWindow;

public class HibernateUtil {
    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        } catch (HibernateException e) {
            MessageWindow.errorMessageWindow("Помилка Hibernate", e.getMessage());
        }
    }

    private HibernateUtil() {
        throw new IllegalStateException("Utility class HibernateUtil");
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
