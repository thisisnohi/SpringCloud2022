package nohi.common.web;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * <h3>SpringCloud2022</h3>
 *
 * @author NOHI
 * @description <p>全局异常处理</p>
 * @date 2023/06/27 12:30
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @RequestBody 上校验失败后抛出的异常是 MethodArgumentNotValidException 异常。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException");
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("；"));
        log.error("handleMethodArgumentNotValidException:{}", message);
        return message;
    }

    /**
     * 不加 @RequestBody注解，校验失败抛出的则是 BindException
     */
    @ExceptionHandler(value = BindException.class)
    public String exceptionHandler(BindException e) {
        log.error("exceptionHandler");
        String message = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("；"));
        log.error("exceptionHandler:{}", message);
        return message;
    }

    /**
     * @RequestParam 上校验失败后抛出的异常是 ConstraintViolationException
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public String methodArgumentNotValid(ConstraintViolationException exception) {
        log.error("methodArgumentNotValid");
        String message = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("；"));
        log.error("methodArgumentNotValid:{}", message);
        return message;
    }
}
