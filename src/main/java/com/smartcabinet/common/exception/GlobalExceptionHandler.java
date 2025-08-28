package com.smartcabinet.common.exception;

import com.smartcabinet.common.result.Result;
import com.smartcabinet.common.result.ResultCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("参数验证失败");
        return Result.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }
    
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("参数绑定失败");
        return Result.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .findFirst()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .orElse("参数校验失败");
        return Result.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }
    
    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public Result<Void> handleBadRequest(Exception ex) {
        return Result.error(ResultCode.BAD_REQUEST.getCode(), ex.getMessage());
    }
    
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntime(RuntimeException ex) {
        return Result.error(ResultCode.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public Result<Void> handleOther(Exception ex) {
        return Result.error(ResultCode.INTERNAL_SERVER_ERROR);
    }
} 