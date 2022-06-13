package com.example.label.util;

import com.example.label.dto.label.ImportExportDTO;
import com.example.label.entity.Label;
import com.example.label.enums.LabelAuth;
import com.example.label.enums.LabelStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Labels {

    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 转换
     * @param label label
     * @return importExportDto
     */
    public static ImportExportDTO convert(Label label){
        ImportExportDTO dto = new ImportExportDTO();
        dto.setCode(label.getCode());
        dto.setFullTagName(label.getName());
        dto.setParentCode(label.getParentCode());
        LabelAuth auth = LabelAuth.getInstance(label.getAuth());
        if (auth != null){
            dto.setAuth(auth.getDesc());
        }
        LabelStatus status = LabelStatus.getInstance(label.getStatus());
        if (status != null){
            dto.setStatus(status.getDesc());
        }
        return dto;
    }

    /**
     * 批量装换
     * @param labels labels
     * @return importExportDtoList
     */
    public static List<ImportExportDTO> convert(List<Label> labels){
        return labels.stream().map(Labels::convert).collect(Collectors.toList());
    }

    private Labels() {
        throw new AssertionError();
    }
}
