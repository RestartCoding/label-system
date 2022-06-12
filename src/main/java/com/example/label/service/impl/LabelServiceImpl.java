package com.example.label.service.impl;

import com.example.label.dto.LabelInput;
import com.example.label.entity.Label;
import com.example.label.enums.LabelAuth;
import com.example.label.enums.LabelStatus;
import com.example.label.exception.ServiceException;
import com.example.label.repository.LabelRepository;
import com.example.label.service.LabelService;
import com.example.label.util.Labels;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class LabelServiceImpl implements LabelService {

    @Resource
    private LabelRepository labelRepository;

    @Override
    public long add(LabelInput labelInput) {
        Optional<Label> optionalLabel = labelRepository.findByNameAndParentCode(labelInput.getName(), labelInput.getParentCode());
        if (optionalLabel.isPresent()){
            throw new ServiceException("Label already exists.");
        }
        Label label = new Label();
        label.setName(labelInput.getName());
        label.setParentCode(labelInput.getParentCode());
        label.setCode(Labels.generateCode());
        label.setAuth(LabelAuth.PRIVATE.getCode());
        label.setStatus(LabelStatus.UNAUDITED.getCode());
        Date date = new Date();
        label.setCreateTime(date);
        label.setUpdateTime(date);
        labelRepository.save(label);
        return label.getId();
    }
}
