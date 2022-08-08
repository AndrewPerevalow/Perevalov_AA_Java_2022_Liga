package com.ligainternship.carwash.service.filter;

import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Booking_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilterBookingByDate {

        public Specification<Booking> getSpec(LocalDate fromDate, LocalDate toDate) {

            return new Specification<>() {

                final List<Predicate> predicateList = new ArrayList<>();

                @Override
                public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    if (fromDate != null) {
                        predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Booking_.date), fromDate));
                    }
                    if (toDate != null) {
                        predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(Booking_.date), toDate));
                    }
                    return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
                }
            };
        }
}
