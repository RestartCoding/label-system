package com.example.label.service.impl;

import com.example.label.dto.label.LabelInput;
import com.example.label.entity.Label;
import com.example.label.enums.LabelStatus;
import com.example.label.exception.ServiceException;
import com.example.label.repository.LabelRepository;
import com.example.label.service.LabelService;
import com.example.label.util.Labels;
import com.example.label.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * @author jack
 */
@Service
public class LabelServiceImpl implements LabelService {

    private static final Logger logger = LoggerFactory.getLogger(LabelServiceImpl.class);

    @Resource
    private LabelRepository labelRepository;

    @Override
    public String add(LabelInput labelInput) {

        // whether name is regular
        if (!Labels.isRegularName(labelInput.getName())) {
            throw new ServiceException("Label name is not regular.");
        }

        Optional<Label> optionalLabel = labelRepository.findByNameAndParentCode(labelInput.getName(), labelInput.getParentCode());
        if (optionalLabel.isPresent()) {
            throw new ServiceException("Label already exists.");
        }
        // 父标签是否存在
        Optional<Label> parentLabel = labelRepository.findByCode(labelInput.getParentCode());
        if (!parentLabel.isPresent()) {
            throw new ServiceException("Parent label not found.");
        }

        Label label = new Label();
        label.setName(labelInput.getName());
        label.setParentCode(labelInput.getParentCode());
        label.setCode(Labels.generateCode());
        label.setAuth(labelInput.getAuth());
        label.setStatus(LabelStatus.UNAUDITED.getCode());
        label.setDescription(labelInput.getDescription());
        label.setCreator(UserUtils.currUsername());
        Date date = new Date();
        label.setCreateTime(date);
        label.setUpdateTime(date);
        labelRepository.save(label);
        return label.getCode();
    }

    @Override
    public Optional<Label> getByFullName(String fullName) {
        return labelRepository.selectByFullName(fullName);
    }

    @Override
    public void save(Label label) {

        Optional<Label> optionalLabel = labelRepository.findByCode(label.getCode());
        if (optionalLabel.isPresent() && !optionalLabel.get().getId().equals(label.getId())) {
            logger.info("param: {}", label);
            throw new ServiceException("Code already exists.");
        }

        Optional<Label> parentOptional = labelRepository.findByCode(label.getParentCode());
        if (!parentOptional.isPresent()) {
            logger.info("param: {}", label);
            throw new ServiceException("Parent label not found.");
        }

        optionalLabel = labelRepository.findByNameAndParentCode(label.getName(), label.getParentCode());
        if (optionalLabel.isPresent() && optionalLabel.get().getId().equals(label.getId())) {
            logger.info("param: {}", label);
            throw new ServiceException("Label already exists.");
        }

        labelRepository.save(label);
    }
}
