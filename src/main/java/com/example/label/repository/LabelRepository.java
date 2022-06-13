package com.example.label.repository;

import com.example.label.entity.Label;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author jack
 * @date 2022-06-13
 */
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

  /**
   * 查询excel数据用
   *
   * @return excel导出数据
   */
  @Query(
      value =
          "WITH RECURSIVE r(code, name, parent_code, auth, status) AS (\n"
              + "    SELECT code, concat('/', name), parent_code, auth, status\n"
              + "    FROM label\n"
              + "    where parent_code is null\n"
              + "  UNION ALL\n"
              + "    SELECT label.code, concat(r.name, '/', label.name), label.parent_code, label.auth, label.status\n"
              + "    FROM label, r\n"
              + "    WHERE label.parent_code = r.code\n"
              + "  )\n"
              + "SELECT * FROM r;")
  List<Label> selectExcelData();
}
