package ${basepackage}.service.impl;


import cn.pinming.tender.enums.*;
import cn.pinming.tender.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


<#assign myParentDir="service.impl">


<#assign className=table.className>


<#assign classNameLower=className?uncap_first>



<#include "/copyright_serviceimpl.include" >
@Service("${classNameLower}Service")
public class ${className}ServiceImpl  extends AbstractServiceImpl<${className}> implements ${className}Service {

	@Autowired
    private ${className}Mapper ${classNameLower}Mapper;

	public ${className}ServiceImpl(){
		super(${className}.class);
	}

	@Override
	public PagedList<${className}> getPaged(PagedSearchRequest<${className}> request){
		if(request==null){
			request=new PagedSearchRequest<${className}>();
		}
		Pagination pagination=request.toPagination();
		${className} filter=new ${className}();
		ReflectUtils.fillObjectByMap(request.getFilter(),null,filter);
		List<${className}> list = ${classNameLower}Mapper.getPaged(filter,request.getSearchKey(),pagination,request.getFilter());
		return new PagedList<>(list,pagination);
	}

}
