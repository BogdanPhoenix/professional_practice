package org.university.business_logic.action_with_database;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.business_logic.utils.HibernateUtil;
import org.university.entities.TableID;
import org.university.exception.SelectedException;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface Select<T extends TableID> {
    Class<T> resolveEntityClass();

    default List<Optional<T>> selectAll(SearchCriteria @NotNull ... searchCriteria) throws SelectedException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Class<T> entityClass = resolveEntityClass();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root<T> rootEntry = criteriaQuery.from(entityClass);
            CriteriaQuery<T> all = criteriaQuery.select(rootEntry);

            Predicate[] predicates = new Predicate[searchCriteria.length + 1];

            if (searchCriteria.length != 0) {
                int index = 0;
                for (var search : searchCriteria) {
                    predicates[index++] = buildPredicate(criteriaBuilder, rootEntry, search);
                }
            }

            SearchCriteria searchCurrentData = new SearchCriteria("currentData", true, SearchOperation.EQUAL);

            predicates[predicates.length - 1] = buildPredicate(criteriaBuilder, rootEntry, searchCurrentData);
            criteriaQuery.where(predicates);

            TypedQuery<T> allQuery = session.createQuery(all);
            transaction.commit();

            return allQuery
                    .getResultList()
                    .stream()
                    .sorted()
                    .map(Optional::of)
                    .toList();
        }
        catch (Exception e) {
            throw new SelectedException("Сталася помилка під час вибору даних з таблиці. Зверніться до техпідтримки.");
        }
    }

    private Predicate buildPredicate(CriteriaBuilder builder, Root<T> root, @NotNull SearchCriteria searchCriteria) {
        return switch (searchCriteria.operation()) {
            case EQUAL -> builder.equal(root.get(searchCriteria.key()), searchCriteria.value());
            case LIKE -> builder.like(root.get(searchCriteria.key()), "%" + searchCriteria.value() + "%");
        };
    }
}
