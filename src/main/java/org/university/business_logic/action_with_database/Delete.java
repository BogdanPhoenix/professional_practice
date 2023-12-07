package org.university.business_logic.action_with_database;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.university.entities.TableID;
import org.university.business_logic.utils.HibernateUtil;
import org.university.exception.DeleteException;

import java.util.List;

public interface Delete<T extends TableID> {
    Class<T> resolveEntityClass();

    default void delete(@NotNull List<T> entities) throws DeleteException {
        for(T entity : entities){
            delete(entity);
        }
    }

    default void delete(T entity) throws DeleteException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()){
            Class<T> entityClass = resolveEntityClass();
            session.remove(entity);

            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaDelete<T> criteriaDelete = cb.createCriteriaDelete(entityClass);
            Root<T> root = criteriaDelete.from(entityClass);
            criteriaDelete.where(
                    cb.equal(
                            root.get("id"),
                            entity.getId()
                    ));

            Transaction transaction = session.beginTransaction();
            session.createMutationQuery(criteriaDelete).executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            throw new DeleteException(
                    String.format("Виникла помилка при видаленні об'єкта %s.%nТекст Помилки: %s",
                            entity,
                            e.getMessage()
                    ));
        }
    }
}
