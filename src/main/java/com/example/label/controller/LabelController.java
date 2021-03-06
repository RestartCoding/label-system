package com.example.label.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.example.label.dto.label.ImportExportDTO;
import com.example.label.dto.label.LabelInput;
import com.example.label.dto.label.UpdateLabelDTO;
import com.example.label.entity.Department;
import com.example.label.entity.Label;
import com.example.label.excel.LabelReadListener;
import com.example.label.repository.DeptRepository;
import com.example.label.repository.LabelRepository;
import com.example.label.service.LabelService;
import com.example.label.util.Labels;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** @author jack */
@RestController
@RequestMapping("/label")
public class LabelController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LabelController.class);

  @Resource private LabelRepository labelRepository;

  @Resource private LabelService labelService;

  @Resource private DeptRepository deptRepository;

  @PreAuthorize("hasAnyAuthority('label:page')")
  @GetMapping("/page")
  public Page<Label> page() {
    return labelRepository.findAll(PageRequest.of(0, 10));
  }

  @PreAuthorize("hasAnyAuthority('label:create')")
  @PostMapping
  public String add(@Validated @RequestBody LabelInput labelInput) {
    return labelService.add(labelInput);
  }

  @PreAuthorize("hasAuthority('label:export')")
  @GetMapping("/export")
  public void export(HttpServletResponse response) {
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=label.xlsx");
    List<Label> labels = labelRepository.selectExcelData();
    List<ImportExportDTO> excelData = Labels.convert(labels);
    // ??????????????????
    List<String> deptCodes =
        labels.stream()
            .map(Label::getDeptCode)
            .filter(StringUtils::hasText)
            .distinct()
            .collect(Collectors.toList());
    if (deptCodes.size() > 0) {
      List<Department> departments = deptRepository.findAllByCodeIn(deptCodes);
      Map<String, Department> map =
          departments.stream().collect(Collectors.toMap(Department::getCode, Function.identity()));
      for (ImportExportDTO importExportDTO : excelData) {
        Department department = map.get(importExportDTO.getDeptCode());
        if (department != null) {
          importExportDTO.setDeptName(department.getName());
        }
      }
    }

    try (InputStream template =
        getClass().getClassLoader().getResourceAsStream("template/label_template.xlsx")) {
      ExcelWriter writer =
          EasyExcel.write(response.getOutputStream(), ImportExportDTO.class)
              .withTemplate(template)
              .needHead(false)
              .autoCloseStream(false)
              .build();
      writer.write(excelData, EasyExcel.writerSheet().sheetNo(0).build());
      writer.finish();
    } catch (IOException e) {
      LOGGER.error("Failed to export label.", e);
    }
  }

  @PreAuthorize("hasAuthority('label:import')")
  @PostMapping("/import")
  @JsonView(ImportExportDTO.ImportResultView.class)
  public List<ImportExportDTO> importLabel(MultipartFile file) throws IOException {
    List<ImportExportDTO> importResult = new ArrayList<>();
    EasyExcel.read(
            file.getInputStream(), ImportExportDTO.class, new LabelReadListener(2, importResult))
        .doReadAll();
    return importResult;
  }

  /**
   * ????????????
   *
   * @param updateLabelDTO updateLabelDTO
   */
  @PreAuthorize("hasAuthority('label:update')")
  @PutMapping
  public void updateLabel(@RequestBody @Validated UpdateLabelDTO updateLabelDTO) {
    labelService.update(updateLabelDTO);
  }
}
