package ${basepackage}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

<#assign className=table.className>
<#assign classNameLower=className?uncap_first>
<#assign classNameLowerCase=className?lower_case>
<#assign from=basepackage?last_index_of(".")>
<#assign rootPagefloder=basepackage?substring(basepackage?last_index_of(".")+1)>
<#assign rootBasepackage=basepackage?substring(0,basepackage?last_index_of("."))>
<#assign pkJavaType=table.idColumn.javaType>
<#assign clasNameWithoutPrefix=classNameLower?replace(rootPagefloder,'')>

import ${basepackage}.entity.${className};
import ${basepackage}.vo.${className}Vo;
import ${basepackage}.service.${className}Service;
<#assign myParentDir="${table.classNameFirstLower}">


<#include "/copyright_controller.include" >
<#if (basepackage?last_index_of('.') > 0)>
@Controller("${ basepackage?substring((basepackage?last_index_of('.'))+1)}_${classNameLowerCase}Controller")
@RequestMapping(value="/${rootPagefloder}/${classLowerNameWithoutPrefix}")
<#else>
@Controller
@RequestMapping(value="/${rootPagefloder}/${classLowerNameWithoutPrefix}")
</#if>
public class ${className}Controller  extends AbstractAttachFileController{

    private   final Logger logger = LoggerFactory.getLogger(${className}Controller.class);

    @Autowired
	private ${className}Service ${classNameLower}Service;

    /**
    * 首页
    *
    * @return
    */
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/${rootPagefloder}/${classLowerNameWithoutPrefix}/list");
    }

    /**
     * 获取单条记录
     *
     * @return
     */
    @RequestMapping("/get")
    public ModelAndView get(String id, Model model) {
        ${className}Vo data=transform(${className}Vo.class, ${classNameLower}Service.getByKey(id));
        model.addAttribute("data", data);
        if (data.getCanModify()) {
            return new ModelAndView("/${rootPagefloder}/${classLowerNameWithoutPrefix}/edit");
        }else if (StringUtils.isNullOrEmpty(data.getId())) {
            return errorView();
        }
        return new ModelAndView("/${rootPagefloder}/${classLowerNameWithoutPrefix}/detail");
    }

    /**
     *  分页数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public PagedList<${className}Vo> list(PagedSearchRequest<${className}> request) {
        return transform(${className}Vo.class,${classNameLower}Service.getPaged(request));
    }
<#if (!table.hasFLowEntity)>

    /**
     * 保存更新
     *
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ApiResult save( ${className} data, HttpServletRequest request) {
        UserAccount sessionUser = WebContext.getUserAccount( );
        ApiResult result;
        try {
            if (StringUtils.isNullOrEmpty(data.getId())) {
                data.setCreateTime(DateUtils.now());
                result = ${classNameLower}Service.create(data);
            } else {
                result = ${classNameLower}Service.update(data);
            }
        } catch (Exception e) {
            logger.debug("保存信息异常：{}", e.getMessage());
            result=ApiResult.create();
            result.error("保存信息失败");
        }
        return result;
    }

<#else>

    /**
     * 保存更新
     *
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ApiResult save( ${className}Vo data, HttpServletRequest request) {
        ApiResult<${className}> result=null;
        AuthUser user = WebContext.getUserFullinfo();
        try {
            if (!hasTendereeRight(user)) {
                result = new ApiResult<>();
                result.error("无权访问数据");
                return result;
            }
            fillUserInfoByDefault(data,user);
            result = ${classNameLower}Service.saveOrUpdate(data);
            result =processAttachAndFlowInfo(result,data,user,${classNameLower}Service);
        } catch (Exception e) {
            logger.error("保存信息异常：{}", e.getMessage());
            if (result == null) {
              result = new ApiResult<>();
            }
            result.error("保存信息失败");
        }
        return result;
    }

    /**
     * 流程提交
     *
     * @return
     */
    @RequestMapping("/processSubmit")
    @ResponseBody
    public ApiResult processSubmit(${className}Vo data, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        AuthUser user = WebContext.getUserFullinfo();
        try {
            if (!ProcessUtil.submitTask(${classNameLower}Service, data, user)) {
                result.error("流程提交失败!");
            }
        } catch (Exception e) {
            logger.error("流程提交失败：{}", e.getMessage());
            result.error("流程提交失败");
        }
        return result;
    }

</#if>

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ResponseBody
    public ApiResult delete(String ids) {
        ApiResult result =ApiResult.create();
        try {
            List<String> idList = TypeUtils.parseToStrList(ids);
            if (idList.size() == 0) {
                return result.error("请选中一条记录删除");
            }
            for (String id : idList) {
                result = ${classNameLower}Service.deleteByKey(id);
            }
        } catch (Exception e) {
            logger.debug("删除异常：{}", e.getMessage());

            result=ApiResult.create();
            result.error("删除失败");
        }
        return result;
    }
}
