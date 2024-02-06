package net.ahmed.bank.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException extends  RuntimeException{

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
