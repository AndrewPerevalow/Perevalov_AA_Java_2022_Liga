package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.UserInfoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

@Service("find_user_by_max_count_tasks")
@RequiredArgsConstructor
public class FindByMaxTasksCountStrategy implements Strategy {

    private static final int COUNT_DATES = 2;

    private final UserInfoService userInfoService;

    @Override
    public String execute(String... args) {
        String status = args[0];
        String[] dates = args[1].split(",");
        if(dates.length == COUNT_DATES) {
            Date first;
            Date second;
            try {
                first = new SimpleDateFormat("yyyy-MM-dd").parse(dates[0]);
                second = new SimpleDateFormat("yyyy-MM-dd").parse(dates[1]);
            } catch (ParseException exception) {
                return "Parse fail: " + exception.getMessage();
            }
            return userInfoService.findByMaxTasksCount(status,first,second).toString();
        } else {
            throw new InputMismatchException("Invalid date interval");
        }
    }
}
