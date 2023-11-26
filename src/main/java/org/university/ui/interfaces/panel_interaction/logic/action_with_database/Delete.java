package org.university.ui.interfaces.panel_interaction.logic.action_with_database;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.university.business_logic.tables.TableID;
import org.university.ui.realization.panel_interaction.logic.HibernateUtil;

public interface Delete<T extends TableID> {
    Class<T> resolveEntityClass();

    default void delete(T entity){
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
        }
    }
}
