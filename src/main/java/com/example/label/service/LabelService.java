package com.example.label.service;


import com.example.label.dto.label.LabelInput;

/**
 * @author jack
 * @date 2022-06-13
 */
public interface LabelService {

    /**
     * 添加标签
     *
     * @param labelInput labelInput
     * @return label id
     */
    long add(LabelInput labelInput);
}
