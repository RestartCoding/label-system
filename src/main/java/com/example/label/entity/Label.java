package com.example.label.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author jack
 */
@Data
public class Label {

    @Id
    private Long id;

    @NotEmpty(message = "Code can not be empty.")
    @Length(max = 128, message = "Code max length is 128")
    private String code;

    @NotEmpty(message = "Name can not be empty.")
    @Length(max = 128, message = "Name max length is 128.")
    private String name;

    @NotEmpty(message = "Parent code can not be empty.")
    @Length(max = 128, message = "Parent code max length is 128")
    private String parentCode;

    @NotNull(message = "Auth can not be null.")
    @Range(max = 2, message = "Invalid auth.")
    private Integer auth;

    @NotNull(message = "Status can not be null.")
    @Range(max = 3, message = "Invalid status.")
    private Integer status;

    @Length(max = 1024, message = "Description max length is 1024.")
    private String description;

    /**
     * 应用部门
     */
    private String deptCode;

    private String creator;

    private Date createTime;

    private Date updateTime;
}
