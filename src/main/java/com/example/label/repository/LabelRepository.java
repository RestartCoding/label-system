package com.example.label.repository;

import com.example.label.entity.Label;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepository extends PagingAndSortingRepository<Label, Long> {

    /**
     * 标签名称和父标签编号查询唯一标签
     *
     * @param name       name
     * @param parentCode parentCode
     * @return optional label
     */
    Optional<Label> findByNameAndParentCode(String name, String parentCode);

    /**
     * 根据parentCode查找
     *
     * @param parentCode parentCode
     * @return optional label
     */
    Optional<Label> findByParentCode(String parentCode);

    /**
     * 根据code查找label
     *
     * @param code code
     * @return label
     */
    Optional<Label> findByCode(String code);
}
