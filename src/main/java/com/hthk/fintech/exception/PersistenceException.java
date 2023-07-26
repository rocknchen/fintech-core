package com.hthk.fintech.exception;

import java.io.IOException;

public class PersistenceException extends IOException {

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }

}
