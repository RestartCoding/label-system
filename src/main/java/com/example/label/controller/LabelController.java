package com.example.label.controller;

import com.example.label.dto.LabelInput;
import com.example.label.entity.Label;
import com.example.label.repository.LabelRepository;
import com.example.label.service.LabelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/label")
public class LabelController {

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
}
