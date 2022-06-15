package com.example.label.util;

import org.springframework.context.ApplicationContext;

/**
 * @author xiabiao
 * @date 2022-06-15
 */
public class SpringBeanUtils {

  public static ApplicationContext ac;

  public static <T> T getBean(Class<T> clazz){
    if (ac == null){
      throw new IllegalStateException("Application context is not initialized.");
    }
    return ac.getBean(clazz);
  }
}
