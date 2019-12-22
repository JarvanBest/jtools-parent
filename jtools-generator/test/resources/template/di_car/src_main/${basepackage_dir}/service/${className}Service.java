package ${basepackage}.service;
import cn.jtool.generator.*;


<#assign myParentDir="service">


<#assign className=table.className>


<#assign classNameLower=className?uncap_first>





<#include "/copyright_service.include" >
public interface ${className}Service extends BusiService<${className}><#if table.hasFLowEntity>,ProcessEntityService</#if>{

    /**
     * 保存或修改数据，项目数据根据主键是否为空来判断，标段数据根据数据库是否存在该ID值来判断数据是否存在
     * @param entity
     * @return
     */
    ApiResult<${className}> saveOrUpdate(${className} entity);
}
