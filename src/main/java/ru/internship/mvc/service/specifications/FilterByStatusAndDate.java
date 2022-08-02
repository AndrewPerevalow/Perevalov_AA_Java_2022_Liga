package ru.internship.mvc.service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.internship.mvc.dto.output.UserFindMaxTasksDto;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.Task_;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.User_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FilterByStatusAndDate {

    @PersistenceContext
    EntityManager entityManager;

    public List<Tuple> findByMaxTasksCount(String status, Date first, Date second) {
        Specification<Task> specificationUserFilter = (root, query, cBuilder) -> {
            CriteriaQuery<User> criteriaQuery = cBuilder.createQuery(User.class);
            Root<Task> taskRoot = criteriaQuery.from(Task.class);
            Join<Task, User> taskJoin = taskRoot.join(Task_.user);
            criteriaQuery.select(taskJoin);
            criteriaQuery.groupBy(taskJoin.get(User_.id));
            criteriaQuery.orderBy(cBuilder.desc(cBuilder.count(taskRoot.get(Task_.id))));
            TypedQuery<User> taskTypedQuery = entityManager.createQuery(criteriaQuery);
            return cBuilder.equal(root.get(Task_.user), taskTypedQuery.setMaxResults(1).getSingleResult().getId());
        };
        Specification<Task> specificationTaskFilter = getSpec(status, first, second);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Task> taskRoot = criteriaQuery.from(Task.class);
        Join<Task, User> taskJoin = taskRoot.join(Task_.user);

        criteriaQuery.select(criteriaBuilder.tuple(taskRoot, taskJoin));
        criteriaQuery.where(criteriaBuilder.and(specificationUserFilter.toPredicate(taskRoot, criteriaQuery, criteriaBuilder),
                specificationTaskFilter.toPredicate(taskRoot, criteriaQuery, criteriaBuilder)));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }


    public Specification<Task> getSpec(String status, Date first, Date second) {

        return new Specification<>() {

            final List<Predicate> predicateList = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (status != null) {
                    predicateList.add(criteriaBuilder.equal(root.get(Task_.status), status));
                }
                if (first != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Task_.deadline), first));
                }
                if (second != null) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(Task_.deadline), second));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
