package com.maoniunet.exception;

import com.maoniunet.result.Result;
import io.swagger.annotations.ResponseHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> exception(Exception e) {
        return makeMsg(e);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Result> exception(UserNotFoundException e) {
        return makeMsg(e, HttpStatus.BAD_REQUEST);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String errors = allErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));
        return (ResponseEntity) Result.error(errors, status);
    }

    private ResponseEntity<Result> makeMsg(Throwable t) {
        return makeMsg(t, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Result> makeMsg(Throwable t, HttpStatus httpStatus) {
        log.error(t.getMessage(), t);
        return makeMsg(t.getMessage() != null ? t.getMessage() : "程序异常请稍候重试", httpStatus);
    }

    private ResponseEntity<Result> makeMsg(String msg, HttpStatus httpStatus) {
        return Result.error(msg, httpStatus);
    }

}
