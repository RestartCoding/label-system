package com.example.label.exception;

import com.example.label.dto.base.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiabiao
 * @date 2022-06-16
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ServiceException.class)
  public Result<Void> handle(ServiceException e) {
    logger.error("", e);
    return Result.fail(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public Result<Void> handle(Exception e) {
    logger.error("", e);
    return Result.fail(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result<Void> handle(MethodArgumentNotValidException e) {
    List<ObjectError> errors = e.getBindingResult().getAllErrors();
    String msg =
        errors.stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(";"));
    return Result.fail(msg);
  }
}
