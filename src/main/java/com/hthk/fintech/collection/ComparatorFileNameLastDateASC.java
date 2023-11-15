package com.hthk.fintech.collection;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 19:10
 */
public class ComparatorFileNameLastDateASC implements Comparator<File> {

    @Override
    public int compare(File f1, File f2) {

        String fileName1 = f1.getName();
        String fileName2 = f2.getName();
        String date1 = fileName1.substring(fileName1.length() - 12, fileName1.length() - 4);
        String date2 = fileName2.substring(fileName2.length() - 12, fileName2.length() - 4);

        LocalDate lDate1 = LocalDate.parse(date1, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate lDate2 = LocalDate.parse(date2, DateTimeFormatter.BASIC_ISO_DATE);

        return lDate1.compareTo(lDate2) > 0 ? 1 : -1;
    }

}
