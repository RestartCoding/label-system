package com.example.label.service.impl;

import com.example.label.dto.label.LabelInput;
import com.example.label.dto.label.UpdateLabelDTO;
import com.example.label.entity.Label;
import com.example.label.entity.User;
import com.example.label.enums.LabelStatus;
import com.example.label.exception.ServiceException;
import com.example.label.repository.LabelRepository;
import com.example.label.service.LabelService;
import com.example.label.util.Labels;
import com.example.label.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/** @author jack */
@Service
public class LabelServiceImpl implements LabelService {

  private static final Logger logger = LoggerFactory.getLogger(LabelServiceImpl.class);

  @Resource private LabelRepository labelRepository;

  @Override
  public String add(LabelInput labelInput) {

    // whether name is regular
    if (!Labels.isRegularName(labelInput.getName())) {
      throw new ServiceException("Label name is not regular.");
    }

    Optional<Label> optionalLabel =
        labelRepository.findByParentCodeAndName(labelInput.getParentCode(), labelInput.getName());
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
    User user = UserUtils.currUser();
    Assert.notNull(user, "User is null.");
    label.setDeptCode(user.getDeptCode());
    labelRepository.save(label);
    return label.getCode();
  }

  @Override
  public Optional<Label> getByFullName(String fullName) {
    return labelRepository.selectByFullName(fullName);
  }

  @Override
  public void update(UpdateLabelDTO updateLabelDTO) {

    Optional<Label> optionalLabel = labelRepository.findById(updateLabelDTO.getId());
    if (!optionalLabel.isPresent()) {
      logger.info("param: {}", updateLabelDTO);
      throw new ServiceException("Label not found.");
    }
    Label label = optionalLabel.get();
    BeanUtils.copyProperties(updateLabelDTO, label);
    optionalLabel = labelRepository.findByParentCodeAndName(label.getParentCode(), label.getName());
    if (optionalLabel.isPresent() && optionalLabel.get().getId().equals(label.getId())) {
      logger.info("param: {}", updateLabelDTO);
      throw new ServiceException("Label already exists.");
    }

    label.setUpdateTime(new Date());

    labelRepository.save(label);
  }
}
