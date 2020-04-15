package com.softserve.maklertaboo.exception.handler;

import com.softserve.maklertaboo.exception.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler.
 *
 * @author Roman Blavatskyi
 */
@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ErrorAttributes errorAttributes;

    /**
     * Method that intercepts exception {@link RuntimeException}.
     *
     * @param exception Exception witch should be intercepted
     * @param request   contains details about occurred exception
     * @return {@link ResponseEntity} witch contains http status
     * and body with message of the exception.
     * @author Roman Blavatskyi
     */
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleRuntimeException(
            RuntimeException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        exceptionResponse.setMessage("Runtime exception");
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Method that intercepts exception {@link BadEmailOrPasswordException}.
     *
     * @param exception Exception witch should be intercepted
     * @param request   contains details about occurred exception
     * @return {@link ResponseEntity} witch contains http status
     * and body with message of the exception.
     * @author Roman Blavatskyi
     */
    @ExceptionHandler(BadEmailOrPasswordException.class)
    public final ResponseEntity<Object> handleBadEmailOrPasswordException(
            BadEmailOrPasswordException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<Object> handleUserAlreadyExistsException(
            UserAlreadyExistsException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(PasswordsDoNotMatchesException.class)
    public final ResponseEntity<Object> handlePasswordDoNotMatchesException(PasswordsDoNotMatchesException exception, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Method that intercepts exception {@link FlatAlreadyInTheFavoriteListException}.
     *
     * @param exception Exception witch should be intercepted
     * @param request   contains details about occurred exception
     * @return {@link ResponseEntity} witch contains http status
     * and body with message of the exception.
     * @author Roman Blavatskyi
     */
    @ExceptionHandler(FlatAlreadyInTheFavoriteListException.class)
    public final ResponseEntity<Object> handleFlatAlreadyInTheFavoriteListException(
            FlatAlreadyInTheFavoriteListException exception,
            WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Method that intercepts exception {@link RequestAlreadyExistsException}.
     *
     * @param exception Exception witch should be intercepted
     * @param request   contains details about occurred exception
     * @return {@link ResponseEntity} witch contains http status
     * and body with message of the exception.
     * @author Roman Blavatskyi
     */
    @ExceptionHandler(RequestAlreadyExistsException.class)
    public final ResponseEntity<Object> handleRequestAlreadyExistsException(
            RequestAlreadyExistsException exception,
            WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Method that intercepts exception {@link AccessDeniedException}.
     *
     * @param exception Exception witch should be intercepted
     * @param request   contains details about occurred exception
     * @return {@link ResponseEntity} witch contains http status
     * and body with message of the exception.
     * @author Roman Blavatskyi
     */
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException exception,
            WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Method that intercepts exception {@link FavoriteFlatNotFoundException}.
     *
     * @param exception Exception witch should be intercepted
     * @param request   contains details about occurred exception
     * @return {@link ResponseEntity} witch contains http status
     * and body with message of the exception.
     * @author Roman Blavatskyi
     */
    @ExceptionHandler(FavoriteFlatNotFoundException.class)
    public final ResponseEntity<Object> handleFavoriteFlatNotFoundException(
            FavoriteFlatNotFoundException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(FlatNotFoundException.class)
    public ResponseEntity<Object> handleFlatNotFoundException(
            FlatNotFoundException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<Object> handleRequestNotFoundException(
            RequestNotFoundException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(DuplicateRenterRequest.class)
    public ResponseEntity<Object> handleRequestNotFoundException(DuplicateRenterRequest exception, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(DuplicateLandlordRequest.class)
    public ResponseEntity<Object> handleRequestNotFoundException(DuplicateLandlordRequest exception, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(BadRefreshTokenException.class)
    public ResponseEntity<Object> handleBadRefreshTokenException(
            BadRefreshTokenException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<Object> handleAuthenticationException(
            AuthenticationException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(
                HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(CustomExpiredJwtException.class)
    public final ResponseEntity<Object> handleCustomExpiredJwtException(
            CustomExpiredJwtException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(
                HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    /**
     * Method that intercepts exception {@link RequestForFlatBookingException}.
     *
     * @param exception Exception witch should be intercepted
     * @param request   contains details about occurred exception
     * @return {@link ResponseEntity} witch contains http status
     * and body with message of the exception.
     * @author Roman Blavatskyi
     */
    @ExceptionHandler(RequestForFlatBookingException.class)
    public final ResponseEntity<Object> handleRequestForFlatBookingException(
            RequestForFlatBookingException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(
                HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    /**
     * Method that intercepts exceptions of {@link Valid} annotation.
     *
     * @param exception Exception witch should be intercepted
     * @param headers   contains details of the occurred exception
     * @param status    contains status of the occurred exception
     * @param request   contains details of the occurred exception
     * @return {@link ResponseEntity} witch contains http status, body
     * and header with message of the exception.
     * @author Roman Blavatskyi
     */
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                 HttpHeaders headers,
                                 HttpStatus status,
                                 WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        body.put("message", errors);
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Method that returns attributes of the occurred error.
     *
     * @param webRequest contains details of the occurred error
     * @return {@link Map<String, Object>}
     * @author Roman Blavatskyi
     */
    private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
        return new HashMap<>(
                errorAttributes.getErrorAttributes(webRequest, true));
    }

    @ExceptionHandler(FlatCommentNotFoundException.class)
    public ResponseEntity<Object> handleFlatCommentNotFoundException(
            FlatCommentNotFoundException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(UserCommentNotFoundException.class)
    public ResponseEntity<Object> handleUserCommentNotFoundException(
            UserCommentNotFoundException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(ComplaintExistsException.class)
    public ResponseEntity<Object> handleComplaintExistsException(
            ComplaintExistsException exception, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(getErrorAttributes(request));
        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
}
