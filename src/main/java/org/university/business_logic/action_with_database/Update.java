package org.university.business_logic.action_with_database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.university.entities.TableID;
import org.university.business_logic.utils.HibernateUtil;
import org.university.exception.UpdateException;

public interface Update<T extends TableID> {
    Class<T> resolveEntityClass();

    default void update(T entity) throws UpdateException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        }catch (Exception e) {
            throw new UpdateException(String.format("Виникла помилка при оновленні об'єкта %s.%nТекст Помилки: %s", entity, e.getMessage()));
        }
    }
}