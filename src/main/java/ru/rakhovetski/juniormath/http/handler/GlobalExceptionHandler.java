package ru.rakhovetski.juniormath.http.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.rakhovetski.juniormath.exception.*;
import ru.rakhovetski.juniormath.util.ResponseEntityUtil;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            SubjectNotFoundException.class,
            IncorrectTaskDataException.class,
            TeacherNotFoundException.class,
            TaskDoesNotExistException.class,
            IncorrectRoleException.class,
            IncorrectLogoutTokenException.class,
            TeacherNotFoundException.class,
            UserNotFoundException.class,
            RoomNotFoundException.class,
            ChangeRoomException.class})
    public ResponseEntity<?> handleBadRequestExceptions(
            Exception e
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntityUtil.responseResultGenerate(status, e.getMessage());
    }

    @ExceptionHandler({
            RegistrationInternalException.class
    })
    public ResponseEntity<?> handleInternalServerExceptions(
            Exception e
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntityUtil.responseResultGenerate(status, e.getMessage());
    }
}
