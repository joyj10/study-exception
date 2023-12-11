package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    public static final String EX = "[exceptionHandler] ex";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error(EX, e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler // 설정값은 파라미터와 완전히 동일한 경우 생략 가능
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error(EX, e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    // 정의된 오류가 없는 경우 아래 메서드에서 처리 됨 (Exception 은 최상위 예외)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error(EX, e);
        return new ErrorResult("EX", "내부 오류");
    }
}
