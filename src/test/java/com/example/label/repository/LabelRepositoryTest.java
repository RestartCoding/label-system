package com.example.label.repository;

import com.example.label.entity.Label;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class LabelRepositoryTest {

  @Resource private LabelRepository labelRepository;

  @Test
  public void testSelectExcelData() {
    List<Label> data = labelRepository.selectExcelData();
    Assert.isTrue(data.size() > 0, "empty.");
  }

  @Test
  public void testSelectByFullName() {
    String fullName = "/标签";
    Optional<Label> labelOptional = labelRepository.selectByFullName(fullName);
    Assert.isTrue(labelOptional.isPresent(), "label not found.");
  }
}
