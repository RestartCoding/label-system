package com.example.label.dto.label;


import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author jack
 */
@Data
public class LabelInput {

    @NotEmpty(message = "name can not be empty.")
    @Length(max = 128, message = "name max length is 128.")
    private String name;

    @NotEmpty(message = "parentCode can not be empty.")
    @Length(max = 128, message = "parentCode max length is 128.")
    private String parentCode;

    @NotNull(message = "Auth can not be null.")
    @Range(max = 2, message = "Auth is invalid.")
    private Integer auth;

    @Length(max = 1024, message = "Description max length is 1024 character.")
    private String description;
}
