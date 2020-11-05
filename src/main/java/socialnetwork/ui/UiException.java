package socialnetwork.ui;

import java.io.IOException;

public class UiException extends RuntimeException {
    public UiException() {
    }

    public UiException(String message) {
        super(message);
    }

    public UiException(String message, Throwable cause) {
        super(message, cause);
    }

    public UiException(Throwable cause) {
        super(cause);
    }

    public UiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
