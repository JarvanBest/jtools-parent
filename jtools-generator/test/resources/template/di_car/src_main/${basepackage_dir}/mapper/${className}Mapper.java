package ${basepackage}.mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

<#assign myParentDir="mapper">
<#assign className=table.className>
<#assign classNameLower=className?uncap_first>

import ${basepackage}.entity.${className};

<#include "/copyright_mapper.include" >
@Repository
public interface ${className}Mapper extends MyMapper<${className}> {
	/**
	 * 分页查找
	 *
	 * @param ${classNameLower}
	 * @param pagination
	 * @param keywords 关键字 提供模糊查询的内容，该字段需要sql注入处理
	 * @param map 扩展字段
	 * @return
	 */
	List<${className}> getPaged(@Param("condition") ${className} condition,
								@Param("keywords") String keywords,
								@Param("page") Pagination pagination,
								@Param("map") HashMap<String, Object> map);
}
