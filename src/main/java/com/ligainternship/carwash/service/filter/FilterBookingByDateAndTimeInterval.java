package com.ligainternship.carwash.service.filter;

import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Booking_;
import com.ligainternship.carwash.model.enums.Status;
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
public class FilterBookingByDateAndTimeInterval {

    public Specification<Booking> getSpec(LocalDate todayDate, LocalTime firstIntervalTime, LocalTime secondIntervalTime) {

        return new Specification<>() {

            final List<Predicate> predicateList = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (todayDate != null) {
                    predicateList.add(criteriaBuilder.equal(root.get(Booking_.date), todayDate));
                }
                if (firstIntervalTime != null && secondIntervalTime != null) {
                    predicateList.add(criteriaBuilder.between(root.get(Booking_.startTime), firstIntervalTime, secondIntervalTime));
                }
                predicateList.add(criteriaBuilder.equal(root.get(Booking_.status), Status.ACTIVE.getStatus()));
                predicateList.add(criteriaBuilder.equal(root.get(Booking_.userIsCome), false));
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
