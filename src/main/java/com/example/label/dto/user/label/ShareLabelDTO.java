package com.example.label.dto.user.label;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author xiabiao
 * @date 2022-06-16
 */
@Data
public class ShareLabelDTO {

  @NotEmpty(message = "Label code list can not be empty.")
  private List<String> labelCodeList;

  @NotEmpty(message = "User name list can not be empty.")
  private List<String> usernameList;
}
