package ru.internship.mvc.service.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.Task_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterByStatusAndDate {

    public static Specification<Task> getSpec(String status, Date first, Date second) {

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
