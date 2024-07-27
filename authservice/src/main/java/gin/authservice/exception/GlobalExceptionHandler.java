package gin.authservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String TIME_STAMP = "TimeStamp";
    @ExceptionHandler(UserNotFoundEx.class)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(UserNotFoundEx e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setProperty(TIME_STAMP, LocalDateTime.now());
        problemDetail.setTitle("User Not Found");
        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistException(UserAlreadyExist e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setProperty(TIME_STAMP, LocalDateTime.now());
        problemDetail.setTitle("User Already Exist");
        return new ResponseEntity<>(problemDetail, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ProblemDetail> handleUnAuthorizedTokenException(UnAuthorizedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty(TIME_STAMP, LocalDateTime.now());
        problemDetail.setTitle("Un Authorized Token");
        return new ResponseEntity<>(problemDetail, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setProperty(TIME_STAMP, LocalDateTime.now());
        problemDetail.setTitle("Exception Handle");
        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
