package com.example.label.service;


import com.example.label.dto.label.LabelInput;
import com.example.label.entity.Label;

import java.util.Optional;

/**
 * @author jack
 */
public interface LabelService {

    /**
     * 添加标签
     *
     * @param labelInput labelInput
     * @return label id
     */
    String add(LabelInput labelInput);

    /**
     * 根据标签全名查询标签信息
     *
     * @param fullName 标签全名 从根标签开始。e.g  /标签/a/b/c
     * @return label
     */
    Optional<Label> getByFullName(String fullName);

    /**
     * 保存标签
     *
     * @param label label
     */
    void save(Label label);
}
