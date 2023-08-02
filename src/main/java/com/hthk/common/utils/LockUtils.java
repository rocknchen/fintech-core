package com.hthk.common.utils;

import java.io.File;
import java.io.IOException;

public class LockUtils {

    private static final String LOCK_FILE = "C:/temp/lock";

    private static byte[] lock = new byte[0];

    public static void lock() {

        if (true) {
            return;
        }

        File lockFile = new File(LOCK_FILE);
        synchronized (lock) {
            while (lockFile.exists()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                lockFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void unLock() {

        if (true) {
            return;
        }

        synchronized (lock) {
            File lockFile = new File(LOCK_FILE);
            lockFile.delete();
        }
    }
}
