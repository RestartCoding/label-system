package com.example.label.repository;

import com.example.label.entity.Label;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LabelRepositoryTest {

  @Resource
  private LabelRepository labelRepository;

  @Test
  public void testSelectExcelData(){
    List<Label> data = labelRepository.selectExcelData();
    Assert.assertTrue(data.size() > 0);
  }

  @Test
  public void testSelectByFullName(){
    String fullName = "/标签";
    Optional<Label> labelOptional = labelRepository.selectByFullName(fullName);
    Assert.assertTrue(labelOptional.isPresent());
  }
}