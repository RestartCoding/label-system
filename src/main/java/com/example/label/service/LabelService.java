package com.example.label.service;


import com.example.label.dto.LabelInput;

public interface LabelService {

    /**
     * 添加标签
     *
     * @param labelInput labelInput
     * @return label id
     */
    long add(LabelInput labelInput);
}
