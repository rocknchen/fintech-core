package com.hthk.fintech.action;

import com.hthk.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Rock CHEN
 * @Date: 2024/3/20 15:11
 */
public class FileMigrateMainEntry {

    private final static Logger logger = LoggerFactory.getLogger(FileMigrateMainEntry.class);

    private void process() throws InterruptedException {

        String srcFolder = "Y:/Prod_Files/MarketData/EODQuote";
        String destFolder = "C:/Rock/Datas/Calypso/Prod_v17/MarketData/EODQuote";
        int updateSecOffset = 60 * 7;

        logger.info("{} {} {}", "copy", srcFolder, destFolder);

        while (true) {
            FileUtils.move(srcFolder, destFolder, updateSecOffset);
            Thread.sleep(1000 * 10);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new FileMigrateMainEntry().process();
    }

}
