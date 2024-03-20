package com.hthk.common.utils;

import com.hthk.fintech.exception.InvalidRequestException;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/20 14:17
 */
public class DateTimeUtilsTest {

    private final static Logger logger = LoggerFactory.getLogger(DateTimeUtilsTest.class);

    @Test
    public void testGenerateBusDate_byRange_DEFAULT() throws InvalidRequestException {

        String startStr = "2023-01-01";
        String endStr = "2024-01-01";

        LocalDate start = LocalDate.parse(startStr, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endStr, DateTimeFormatter.ISO_DATE);
        List<LocalDate> busDateList = DateTimeUtils.generateBusinessDate(start, end, null, null);

        logger.info(LOG_DEFAULT, "1st", CollectionUtils.isEmpty(busDateList) ? null : busDateList.get(0));
        logger.info(LOG_DEFAULT, "end", CollectionUtils.isEmpty(busDateList) ? null : busDateList.get(busDateList.size() - 1));
        logger.info(LOG_DEFAULT, "size", CollectionUtils.isEmpty(busDateList) ? 0 : busDateList.size());
    }

    @Test
    public void testGetUpToToday() throws InvalidRequestException {

        String startStr = "2024-01-01";
        LocalDate today = LocalDate.now();

        LocalDate start = LocalDate.parse(startStr, DateTimeFormatter.ISO_DATE);
        List<LocalDate> busDateList = DateTimeUtils.generateBusinessDate(start, today, null, null);

        logger.info(LOG_DEFAULT, "1st", CollectionUtils.isEmpty(busDateList) ? null : busDateList.get(0));
        logger.info(LOG_DEFAULT, "end", CollectionUtils.isEmpty(busDateList) ? null : busDateList.get(busDateList.size() - 1));
        logger.info(LOG_DEFAULT, "size", CollectionUtils.isEmpty(busDateList) ? 0 : busDateList.size());
    }

}