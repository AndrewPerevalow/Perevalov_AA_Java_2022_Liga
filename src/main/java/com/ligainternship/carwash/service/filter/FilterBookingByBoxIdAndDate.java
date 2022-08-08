package com.ligainternship.carwash.service.filter;

import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Booking_;
import com.ligainternship.carwash.model.entitiy.Box;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilterBookingByBoxIdAndDate {

    public Specification<Booking> getSpec(Box box, LocalDate date, LocalTime time) {

        return new Specification<>() {

            final List<Predicate> predicateList = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (box != null) {
                    predicateList.add(criteriaBuilder.equal(root.get(Booking_.box), box));
                }
                if (date != null) {
                    predicateList.add(criteriaBuilder.equal(root.get(Booking_.date), date));
                }
                if (time != null) {
                    predicateList.add(criteriaBuilder.equal(root.get(Booking_.startTime), time));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
