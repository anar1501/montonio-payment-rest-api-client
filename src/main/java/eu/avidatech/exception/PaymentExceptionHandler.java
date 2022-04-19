package eu.avidatech.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import eu.avidatech.enums.MessageCase;
import eu.avidatech.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentExceptionHandler {



    @ExceptionHandler(WrongFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidPaymentException(WrongFormatException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidPaymentException(JsonParseException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(MessageCase.ONLY_NUMBER_ACCEPTED.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidPaymentException(InvalidFormatException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(MessageCase.ONLY_NUMBER_ACCEPTED.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


//    MethodArgumentNotValidException

}
