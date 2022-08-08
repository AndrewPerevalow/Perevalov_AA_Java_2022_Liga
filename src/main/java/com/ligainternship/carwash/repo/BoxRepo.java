package com.ligainternship.carwash.repo;

import com.ligainternship.carwash.model.entitiy.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BoxRepo extends JpaRepository<Box, Long> {
    @Query(value = """
                               select distinct b.* from boxes b
                               where
                                   ((make_time(:h, :m,0) between b."work_from_time" and b."work_to_time") and
                                    cast(make_time(:h, :m,0) + interval '1 minute' * cast(ceil(b.ratio * :duration) as int) as time)
                                        between b."work_from_time" and b."work_to_time")
                               except
                               select b2.* from boxes b2 left join bookings o  on b2.id =o.box_id
                               where
                                           o."date" =:date and o.status = 'Active' and
                                           ((o.start_time <= make_time(:h,:m,0) and  make_time(:h,:m,0)<o.end_time)
                                  or
                                   (o.start_time < cast(make_time(:h, :m,0) +cast(ceil(b2.ratio *:duration)  as int) * interval '1 minute' as time)
                                       and cast(make_time(:h, :m,0) +cast(ceil(b2.ratio *:duration) as int) * interval '1 minute'as time) <=o.end_time)
                                   or
                                        (o.start_time > make_time(:h,:m,0) and
                                         cast(make_time(:h, :m,0) +cast(ceil(b2.ratio *:duration) as int) * interval '1 minute'as time) >o.end_time)    
                                       );
                    """ , nativeQuery = true)
    List<Box> findByDateAndOperations(@Param("date") LocalDate date,
                                      @Param("h") int hour,
                                      @Param("m") int minute,
                                      @Param("duration") int duration);
}
