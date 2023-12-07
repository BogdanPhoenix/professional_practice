package org.university.business_logic.action_with_database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.university.business_logic.utils.HibernateUtil;
import org.university.entities.TableID;
import org.university.exception.InsertException;

import java.util.Optional;

@FunctionalInterface
public interface Insert<T extends TableID> {
    Class<T> resolveEntityClass();

    default Optional<T> save(T entity) throws InsertException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Optional<T> obj = Optional.of(session.merge(entity));
            transaction.commit();

            return obj;
        }catch (Exception e) {
            throw new InsertException(
                    String.format("Виникла помилка при додаванні об'єкта %s.%nТекст Помилки: %s",
                            entity,
                            e.getMessage()
                    ));
        }
    }
}
