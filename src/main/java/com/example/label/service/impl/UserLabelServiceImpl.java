package com.example.label.service.impl;

import com.example.label.dto.user.label.ShareLabelDTO;
import com.example.label.entity.Label;
import com.example.label.entity.User;
import com.example.label.entity.UserLabel;
import com.example.label.enums.LabelAuth;
import com.example.label.exception.ServiceException;
import com.example.label.repository.LabelRepository;
import com.example.label.repository.UserLabelRepository;
import com.example.label.repository.UserRepository;
import com.example.label.service.UserLabelService;
import com.example.label.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiabiao
 * @date 2022-06-16
 */
@Service
public class UserLabelServiceImpl implements UserLabelService {

  private static final Logger logger = LoggerFactory.getLogger(UserLabelServiceImpl.class);

  @Resource private UserRepository userRepository;

  @Resource private LabelRepository labelRepository;

  @Resource private UserLabelRepository userLabelRepository;

  @Override
  public void share(ShareLabelDTO shareLabelDTO) {
    List<Label> labels = labelRepository.findAllByCodeIn(shareLabelDTO.getLabelCodeList());
    if (labels.size() != shareLabelDTO.getLabelCodeList().size()) {
      throw new ServiceException("Label code not found.");
    }

    long protectLabelCount =
        labels.stream().filter(e -> e.getAuth() == LabelAuth.PROTECT.getCode()).count();
    if (protectLabelCount != labels.size()) {
      throw new ServiceException("Only protect label can share.");
    }

    String currUsername = UserUtils.currUsername();
    long creatorCount = labels.stream().filter(e -> e.getCreator().equals(currUsername)).count();
    if (creatorCount != labels.size()) {
      throw new ServiceException("Can not share other people's label.");
    }

    List<User> users = userRepository.findAllByUsernameIn(shareLabelDTO.getUsernameList());
    if (users.size() != shareLabelDTO.getUsernameList().size()) {
      throw new ServiceException("User not found.");
    }

    // 移除标签之前的授权用户\
    userLabelRepository.removeByLabelCodeIn(shareLabelDTO.getLabelCodeList());
    // 创建人加入授权用户
    if (!shareLabelDTO.getUsernameList().contains(currUsername)) {
      shareLabelDTO.getUsernameList().add(currUsername);
    }

    List<UserLabel> userLabels =
        new ArrayList<>(
            shareLabelDTO.getLabelCodeList().size() * shareLabelDTO.getUsernameList().size());
    Date createTime = new Date();
    for (String labelCode : shareLabelDTO.getLabelCodeList()) {
      for (String username : shareLabelDTO.getUsernameList()) {
        UserLabel userLabel = new UserLabel();
        userLabel.setLabelCode(labelCode);
        userLabel.setUsername(username);
        userLabel.setCreateTime(createTime);
        userLabels.add(userLabel);
      }
    }

    userLabelRepository.saveAll(userLabels);
  }
}
