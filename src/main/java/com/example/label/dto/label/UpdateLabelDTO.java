package com.example.label.dto.label;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author xiabiao
 * @date 2022-06-23
 */
@Data
public class UpdateLabelDTO {

  @NotNull(message = "id can not be null.")
  private Long id;

  @NotEmpty(message = "name can not be empty.")
  @Length(max = 128, message = "name max length is 128.")
  private String name;

  @Length(max = 1024, message = "description max length is 1024.")
  private String description;
}
