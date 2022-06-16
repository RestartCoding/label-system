package com.example.label.controller;

import com.example.label.dto.user.label.ShareLabelDTO;
import com.example.label.service.UserLabelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xiabiao
 * @date 2022-06-16
 */
@RestController
@RequestMapping("/userLabel")
public class UserLabelController {

  @Resource
  private UserLabelService userLabelService;

  @PostMapping("/share")
  public void share(@RequestBody @Validated ShareLabelDTO shareLabelDTO){
    userLabelService.share(shareLabelDTO);
  }

}
