package com.hthk.common.utils;

import com.hthk.fintech.exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/20 14:16
 */
public class DateTimeUtils {

    private final static Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);

    public static List<LocalDate> generateBusinessDate(
            LocalDate start, LocalDate end,
            List<LocalDate> weekEndBusinessDateList, List<LocalDate> weekDateHolidayList) throws InvalidRequestException {

        check(start, end);

        List<LocalDate> busDateList = generate(start, end);
        busDateList = addIn(busDateList, weekDateHolidayList);
        busDateList = filter(busDateList, weekDateHolidayList);

        return busDateList;
    }

    /**
     * TODO
     *
     * @param busDateList
     * @param weekDateHolidayList
     * @return
     */
    private static List<LocalDate> filter(List<LocalDate> busDateList, List<LocalDate> weekDateHolidayList) {
        return busDateList;
    }

    /**
     * TODO
     *
     * @param busDateList
     * @param weekDateHolidayList
     * @return
     */
    private static List<LocalDate> addIn(List<LocalDate> busDateList, List<LocalDate> weekDateHolidayList) {
        return busDateList;
    }

    private static List<LocalDate> generate(LocalDate start, LocalDate end) {

        List<LocalDate> dateList = new ArrayList<>();
        LocalDate tmp = start;
        while (tmp.compareTo(end) <= 0) {
            if (tmp.getDayOfWeek() != DayOfWeek.SATURDAY && tmp.getDayOfWeek() != DayOfWeek.SUNDAY) {
                dateList.add(tmp);
            }
            tmp = tmp.plusDays(1);
        }
        return dateList;
    }

    private static void check(LocalDate start, LocalDate end) throws InvalidRequestException {
        if (start == null || end == null) throw new InvalidRequestException("start or end is empty");
        if (start.compareTo(end) >= 0) throw new InvalidRequestException("start is on or after end");
    }
}
