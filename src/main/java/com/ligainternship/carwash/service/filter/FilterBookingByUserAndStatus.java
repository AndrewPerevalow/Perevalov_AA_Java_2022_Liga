package com.ligainternship.carwash.service.filter;

import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Booking_;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.model.enums.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilterBookingByUserAndStatus {

    public Specification<Booking> getSpec(User user) {

        return new Specification<>() {

            final List<Predicate> predicateList = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (user != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Booking_.user), user)));
                }
                predicateList.add(criteriaBuilder.equal(root.get(Booking_.status), Status.ACTIVE.getStatus()));
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
