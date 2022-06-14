package com.example.label.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.example.label.dto.label.ImportExportDTO;
import com.example.label.dto.label.LabelInput;
import com.example.label.entity.Label;
import com.example.label.repository.LabelRepository;
import com.example.label.service.LabelService;
import com.example.label.util.Labels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author jack
 */
@RestController
@RequestMapping("/label")
public class LabelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelController.class);

    @Resource
    private LabelRepository labelRepository;

    @Resource
    private LabelService labelService;

    @GetMapping("/page")
    public Page<Label> page() {
        return labelRepository.findAll(PageRequest.of(0, 10));
    }

    @PostMapping
    public Long add(@Validated @RequestBody LabelInput labelInput) {
        return labelService.add(labelInput);
    }

    @PreAuthorize("hasAuthority('label:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=label.xlsx");
        List<Label> labels = labelRepository.selectExcelData();
        List<ImportExportDTO> excelData = Labels.convert(labels);
        try (InputStream template = getClass().getClassLoader().getResourceAsStream("template/label_export_template.xlsx")) {
            ExcelWriter writer = EasyExcel.write(response.getOutputStream(), ImportExportDTO.class)
                    .withTemplate(template)
                    .needHead(false)
                    .autoCloseStream(false).build();
            writer.write(excelData, EasyExcel.writerSheet().sheetNo(0).build());
            writer.finish();
        } catch (IOException e) {
            LOGGER.error("Failed to export label.", e);
        }
    }
}
