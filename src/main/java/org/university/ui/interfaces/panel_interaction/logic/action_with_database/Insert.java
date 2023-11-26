package org.university.ui.interfaces.panel_interaction.logic.action_with_database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.university.ui.realization.panel_interaction.logic.HibernateUtil;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface Insert<T> {
    Class<T> resolveEntityClass();

    default void saveAll(@NotNull List<T> entities){
        for(T entity : entities){
            save(entity);
        }
    }

    default Optional<T> save(T entity){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Optional<T> obj = Optional.of(session.merge(entity));
            transaction.commit();

            return obj;
        }catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }

            throw new IllegalArgumentException(String.format("An error occurred while adding an %s object.", entity));
        }
    }
}
