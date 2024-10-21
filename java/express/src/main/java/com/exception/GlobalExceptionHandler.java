package com.exception;

import com.alibaba.fastjson.JSON;
import com.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：
 * @Package：com.exception
 * @Project：express
 * @name：GlobalExceptionHandler
 * @Date：2024/9/1 17:10
 * @Description:
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<R> handleCustomException(BizException ex) {
        R error = R.error(500,ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<R> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        R error = R.error(400, errors.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    // 处理其他异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<R> handleException(Exception ex) {
        R error = R.error(500,ex.getMessage());
        log.error(JSON.toJSONString(ex));
        ex.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
