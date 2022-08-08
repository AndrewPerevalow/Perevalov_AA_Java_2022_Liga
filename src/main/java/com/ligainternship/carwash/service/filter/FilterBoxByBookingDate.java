/*
package com.ligainternship.carwash.service.filter;

import com.ligainternship.carwash.exception.BoxNotFoundException;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Booking_;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.model.entitiy.Box_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.TimeZone;

@Component
@Slf4j
public class FilterBoxByBookingDate {

    */
/*@PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }*//*


    @PersistenceContext
    EntityManager entityManager;

    public Box findByDate(LocalDateTime startDate, Double totalLeadTimeInSeconds) {
//        LocalTime startTime = startDate.toLocalTime();
        Double startDateInSeconds = (double) startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000;
//        Double startTimeInSeconds = (double) startTime.getSecond();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Box> query = criteriaBuilder.createQuery(Box.class);
        Root<Box> root = query.from(Box.class);
        ListJoin<Box, Booking> join = root.join(Box_.bookings, JoinType.LEFT);

        Expression<Double> prodExpression = criteriaBuilder.prod(root.get(Box_.ratio), totalLeadTimeInSeconds);
        Expression<Double> sumDateExpression = criteriaBuilder.sum(prodExpression, startDateInSeconds);
//        Expression<Double> sumTimeExpression = criteriaBuilder.sum(prodExpression, startTimeInSeconds);
        Expression<LocalDateTime> expressionEndDate = criteriaBuilder.function("to_timestamp", LocalDateTime.class, sumDateExpression);
        Expression<LocalDateTime> expressionEndDate2 = criteriaBuilder.function("to_char", String.class, expressionEndDate, criteriaBuilder.literal("yyyy-MM-dd HH:mm:ss")).as(LocalDateTime.class);
//        Expression<LocalTime> expressionEndTime = criteriaBuilder.function("to_timestamp", LocalTime.class, sumTimeExpression);
//        Expression<LocalTime> expressionEndTime2 = criteriaBuilder.function("to_char", String.class, expressionEndTime, criteriaBuilder.literal("HH:mm:ss")).as(LocalTime.class);

        Predicate startDateGreaterThan = criteriaBuilder.lessThan(join.get(Booking_.startTime), startDate);
        Predicate startDateLessThan = criteriaBuilder.greaterThan(join.get(Booking_.endTime), startDate);
        Predicate endDateGreaterThan = criteriaBuilder.lessThan(join.get(Booking_.startTime), expressionEndDate2);
        Predicate endDateLessThan = criteriaBuilder.greaterThan(join.get(Booking_.endTime), expressionEndDate2);


//        Predicate between1 = criteriaBuilder.between(join.get(Booking_.startTime), startDate, endLocalDateTime);



//        Predicate startTimeGreaterThan = criteriaBuilder.greaterThan(bookingJoin.get(Box_.workFromTime), startTime);
//        Predicate endTimeGreaterThan = criteriaBuilder.greaterThan(bookingJoin.get(Box_.workFromTime), expressionEndTime2);
//        Predicate startTimeLessThan = criteriaBuilder.lessThan(bookingJoin.get(Box_.workToTime), startTime);
//        Predicate endTimeLessThan = criteriaBuilder.lessThan(bookingJoin.get(Box_.workToTime), expressionEndTime2);

        query.select(root).distinct(true);
        query.where(criteriaBuilder.or(
                        */
/*criteriaBuilder.isNull(join.get(Booking_.startTime)),*//*

                criteriaBuilder.and(startDateGreaterThan, startDateLessThan),
                criteriaBuilder.and(endDateGreaterThan, endDateLessThan)*/
/*,
                criteriaBuilder.and(startTimeGreaterThan, endTimeGreaterThan),
                criteriaBuilder.and(startTimeLessThan, endTimeLessThan)*//*


                )
        );
        TypedQuery<Box> typedQuery = entityManager.createQuery(query);
        List<Box> boxList = typedQuery.getResultList();
        if (boxList.isEmpty()) {
            String message = "Not found free boxes on date: " + startDate;
            log.error(message);
            throw new BoxNotFoundException(message);
        }
        return boxList.get(0);
    }
}
*/
