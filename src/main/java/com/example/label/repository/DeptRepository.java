package com.example.label.repository;

import com.example.label.entity.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author xiabiao
 * @date 2022-06-23
 */
public interface DeptRepository extends CrudRepository<Department, Long> {
  /**
   * find department by code
   *
   * @param code code
   * @return optional department
   */
  Optional<Department> findByCode(String code);

  /**
   * find all by code in
   *
   * @param codes department code list
   * @return department list
   */
  List<Department> findAllByCodeIn(List<String> codes);
}
