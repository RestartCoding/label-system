package com.example.label.dto.label;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class LabelInput {

    @NotEmpty(message = "name can not be empty.")
    @Length(max = 128, message = "name max length is 128.")
    private String name;

    @NotEmpty(message = "parentCode can not be empty.")
    @Length(max = 128, message = "parentCode max length is 128.")
    private String parentCode;

    private String auth;

    private String status;
}
