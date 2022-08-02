package ru.internship.mvc.service.specifications;

import org.springframework.stereotype.Component;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.Task_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class FilterAllTasksByStatus {

    @PersistenceContext
    EntityManager entityManager;

    public List<Task> filterAllTasksForUsersByStatus(Long id, String status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = criteriaBuilder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        query.select(root);
        query.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get(Task_.user), id),
                criteriaBuilder.equal(root.get(Task_.status), status)));
        TypedQuery<Task> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
