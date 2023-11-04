package org.university.business_logic.tables.logic.interfaces.action_with_database;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.university.business_logic.tables.logic.util.HibernateUtil;
import org.university.business_logic.tables.logic.interfaces.Condition;
import org.university.business_logic.tables.orm.ExecutionStatus;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface Select<T> {
    Class<T> resolveEntityClass();

    default List<T> selectAll(){
        return selectAll(new ArrayList<>());
    }

    default List<T> selectAll(List<Condition<T>> conditions) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Class<T> entityClass = resolveEntityClass();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root<T> rootEntry = criteriaQuery.from(entityClass);
            CriteriaQuery<T> all = criteriaQuery.select(rootEntry);

            if (conditions != null && !conditions.isEmpty()) {
                Predicate[] predicates = new Predicate[conditions.size()];
                int index = 0;
                for (var condition : conditions) {
                    predicates[index++] = condition.buildPredicate(criteriaBuilder, rootEntry);
                }
                criteriaQuery.where(predicates);
            }

            TypedQuery<T> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        }
    }

    default T selectOne(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try(Session session = sessionFactory.openSession()){
            Class<T> entityClass = resolveEntityClass();
            return session.get(entityClass, id);
        }
    }
}
