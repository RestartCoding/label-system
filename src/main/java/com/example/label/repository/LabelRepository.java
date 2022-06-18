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
     * 标签编号批量查询
     *
     * @param codes codes
     * @return label list
     */
    List<Label> findAllByCodeIn(List<String> codes);

    /**
     * 查询excel数据用
     *
     * @return excel导出数据
     */
    @Query(
            value =
                    "WITH RECURSIVE r(code, name, parent_code, auth, status, description) AS (\n"
                            + "    SELECT code, concat('/', name), parent_code, auth, status, description\n"
                            + "    FROM label\n"
                            + "    where parent_code is null\n"
                            + "  UNION ALL\n"
                            + "    SELECT label.code, concat(r.name, '/', label.name), label.parent_code, label.auth, label.status, label.description\n"
                            + "    FROM label, r\n"
                            + "    WHERE label.parent_code = r.code\n"
                            + "  )\n"
                            + "SELECT * FROM r;")
    List<Label> selectExcelData();

    /**
     * 标签全名查询
     *
     * @param fullName fullName
     * @return label
     */
    @Query(
            "with recursive r (code, name) as (\n"
                    + "  select code, concat('/', name) from label\n"
                    + "  where parent_code is null\n"
                    + "  union all\n"
                    + "  select label.code, concat(r.name, '/', label.name) from label, r\n"
                    + "  where label.parent_code = r.code\n"
                    + ")\n"
                    + "select * from label where code = (select code from r where name = :fullName);")
    Optional<Label> selectByFullName(String fullName);
}
