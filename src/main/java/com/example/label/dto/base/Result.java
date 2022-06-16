package com.example.label.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author xiabiao
 * @date 2022-06-16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Result<T> {

  private static final String SUCCESS_CODE = "0";

  private static final String SUCCESS_MSG = "success";

  private static final String FAIL_CODE = "1";

  private String code;

  private String msg;

  private T data;

  public static <T> Result<T> success(T data) {
    Result<T> result = new Result<>();
    result.setCode(SUCCESS_CODE);
    result.setMsg(SUCCESS_MSG);
    result.setData(data);
    return result;
  }

  public static <T> Result<T> fail() {
    Result<T> result = new Result<>();
    result.setCode(FAIL_CODE);
    return result;
  }

  public static <T> Result<T> fail(String msg) {
    Result<T> result = new Result<>();
    result.setCode(FAIL_CODE);
    result.setMsg(msg);
    return result;
  }

  @JsonIgnore
  public boolean isSuccess() {
    return SUCCESS_CODE.equals(code);
  }
}
