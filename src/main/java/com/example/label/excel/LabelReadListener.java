package com.example.label.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.example.label.dto.label.ImportExportDTO;
import com.example.label.entity.Label;
import com.example.label.enums.LabelAuth;
import com.example.label.enums.LabelStatus;
import com.example.label.repository.LabelRepository;
import com.example.label.util.Labels;
import com.example.label.util.SpringBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author xiabiao
 * @date 2022-06-15
 */
public class LabelReadListener implements ReadListener<ImportExportDTO> {

  private static final Logger logger = LoggerFactory.getLogger(LabelReadListener.class);

  private int startRow;

  private List<ImportExportDTO> importResult;

  public LabelReadListener(int startRow, List<ImportExportDTO> importResult) {
    this.startRow = startRow;
    this.importResult = importResult;
  }

  /**
   * TODO: The validation rules may not be complete
   * @param data data
   * @param context context
   */
  @Override
  public void invoke(ImportExportDTO data, AnalysisContext context) {
    importResult.add(data);
    data.setLineNum(startRow++);
    //  标签全名不能为空
    if (ObjectUtils.isEmpty(data.getFullName())) {
      data.setMsg("Full label name can not be empty.");
      return;
    }

    LabelAuth auth;
    LabelStatus status;

    if (ObjectUtils.isEmpty(data.getAuth())) {
      data.setMsg("Auth can not be empty.");
      return;
    }

    if ((auth = LabelAuth.getByDesc(data.getAuth())) == null) {
      data.setMsg("Invalid auth.");
      return;
    }

    if (ObjectUtils.isEmpty(data.getStatus())) {
      data.setMsg("Status can not be empty.");
      return;
    }

    if ((status = LabelStatus.getByDesc(data.getStatus())) == null) {
      data.setMsg("Invalid status.");
      return;
    }

    // 父标签存不存在
    String parentFullName = Labels.getParentFullName(data.getFullName());
    LabelRepository labelRepository = SpringBeanUtils.getBean(LabelRepository.class);
    Optional<Label> parentLabelOptional = labelRepository.selectByFullName(parentFullName);
    if (!parentLabelOptional.isPresent()) {
      data.setMsg("Parent label not found.");
      return;
    }

    // 标签编号是否存在
    if (StringUtils.hasText(data.getCode())) {
      Optional<Label> labelOptional = labelRepository.findByCode(data.getCode());
      if (labelOptional.isPresent()) {
        data.setMsg("Label code is already exists.");
        return;
      }
    }

    //
    Optional<Label> optional = labelRepository.selectByFullName(data.getFullName());
    if (optional.isPresent()) {
      data.setMsg("Label already exists.");
      return;
    }

    Label label = new Label();
    label.setCode(ObjectUtils.isEmpty(data.getCode()) ? Labels.generateCode() : data.getCode());
    label.setName(Labels.getName(data.getFullName()));
    label.setParentCode(parentLabelOptional.get().getCode());
    label.setAuth(auth.getCode());
    label.setStatus(status.getCode());

    Date date = new Date();
    label.setCreateTime(date);
    label.setUpdateTime(date);

    labelRepository.save(label);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    logger.info("End to import label.");
  }
}
