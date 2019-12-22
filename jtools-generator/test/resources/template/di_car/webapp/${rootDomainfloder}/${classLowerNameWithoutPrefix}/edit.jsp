<#assign myParentDir="entity">
<#assign className = table.className>
<#assign classNameLower = className?lower_case>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>
<#assign clasNameWithoutPrefix = classNameLower?replace(rootPagefloder,'')>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<title>${table.remarks}</title>
	<jsp:include page="/common/common.jsp"/>
</head>
<body style="background:#f9f9f9;" data-spy="scroll" data-target=".left-navbar">
<div class="top-title">
	<div class="title-box">
		<div class="content-head-title">
			<i class="mark-img"></i><span class="label">当前位置：<span class="app-label">${table.remarks}</span></span>
		</div>
	</div>
</div>
<div class="main-content">
	<div class="right-content">
		<form id="myForm" action="${r'${contextPath}'}/${rootDomainfloder}/${classLowerNameWithoutPrefix}/save" method="post">
			<#list table.pkColumns as column>
				<input type="hidden" name="${column.camelName}" id="${column.camelName}" value="${r'${'}data.${column.camelName}${r'}'}"/>
			</#list>
			<#if table.hasFLowEntity >
				<input type="hidden" name="processinstanceid" id="processinstanceid" value="${r'${'}data.processinstanceid${r'}'}"/>
				<!-- 流程 -->
				<input id="taskid" name="taskId" type="hidden" value="${r'${'}data.taskId${r'}'}"/>
				<input id="variables_valid" name="variables['valid']" type="hidden" value="" />
				<input id="variables_commit" name="variables['commit']" type="hidden" value="" />
				<input id="variables_opinion" name="variables['opinion']" type="hidden" value="" />
	        </#if>
			<div class="panel">
				<div  id="basicInfo" class="inner-panel">
					<div class="panel-heading">
						<span class="label">基本信息</span>
						<i class="fold"></i>
					</div>
					<div class="panel-bodying">
						<div class="information-body">
							<ul class="information">
								<#list table.columns as column><#if !column.isPk() && !column.hiddenField >
								<li class="row-li">
								<label><i>*</i>${column.columnAlias}：</label>
								<#if (column.simpleJavaType =="Timestamp" || column.simpleJavaType =="Date")>
									<input class="easyui-datetimebox"  name="${column.camelName}" id="${column.camelName}" value="${r'${'}data.${column.camelName}${r'}'}" />
								<#elseif (column.simpleJavaType =="Integer" || column.simpleJavaType =="Long" || column.simpleJavaType =="Short") >
									<input class="easyui-validatebox text lg-input"  name="${column.camelName}" id="${column.camelName}" value="${r'${'}data.${column.camelName}${r'}'}" data-options="required:true,validType:'integer'"/>
								<#elseif (column.simpleJavaType =="BigDecimal" || column.simpleJavaType =="Double" || column.simpleJavaType =="Float") >
									<input class="easyui-validatebox text lg-input"  name="${column.camelName}" id="${column.camelName}" value="${r'${'}data.${column.camelName}${r'}'}" />
								<#elseif (column.simpleJavaType =="Boolean" ) >
									<input class="easyui-validatebox text lg-input"  name="${column.camelName}" id="${column.camelName}" value="${r'${'}data.${column.camelName}${r'}'}" />
								<#else>
									<input class="easyui-validatebox text lg-input"  name="${column.camelName}" id="${column.camelName}" value="${r'${'}data.${column.camelName}${r'}'}" data-options="required:true,validType:'maxLength[255]'"/>
								</#if>
								</li>
								</#if></#list>
							</ul>
						</div>
					</div>
				</div>
				<div  id="attachInfo" class="inner-panel">
					<div class="panel-heading">
						<span class="label">附件信息</span>
						<i class="fold"></i>
					</div>
					<div class="panel-bodying">
						<div class="information-body">
							<ul class="information">
								<jsp:include page="/common/attachment.jsp"/>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="left-content">
		<div class="left-navbar">
			<ul class="left-nav nav">
				<li class="active"><a href="#basicInfo"><i class="icons-arrow"></i>基本信息</a></li>
				<li class="active"><a href="#attachInfo"><i class="icons-arrow"></i>附件信息</a></li>
			</ul>
			<button class="big-btn big-save radius5" type="button" onclick="save();"><i class="custom-save"></i>保存</button>
			<button class="big-btn big-record radius5" type="button" onclick="submitData();"><i class="custom-record"></i>备案</button>
			<button class="big-btn big-return radius5" type="button" onclick="back();"><i class="custom-return"></i>返回</button>
		</div>
	</div>
</div>
<div id="auditWindow" title="备案信息" data-options="iconCls:'icon-save',modal:true,iconCls:'icon-search',buttons:[{
  text:'保存',
  handler:saveAudit
  },{
  text:'关闭',
  handler:function(){
    $('#auditWindow').dialog('close');
  }
  }]" style="width:550px;display:none;">
	<form id="audit_form" method="post" action="">
		<ul class="information" style="border:0px;padding:20px 0px 0px 0px">
			<input id="audit_commit" name="audit_commit" type="hidden" value=""/>
			<input id="audit_valid" name="audit_valid" type="hidden" value=""/>
			<li class="row-li">
				<label><i>*</i>意见描述：</label>
				<textarea id="audit_opinion" name="audit_opinion" class="easyui-validatebox text"
						  data-options="required:true,validType:'maxLength[256]'" style="width:250px;height:200px"
						  title="请输入送审意见"></textarea>
			</li>
		</ul>
	</form>
</div>
<script type="text/javascript" src="${r'${contextPath}'}/pages/${rootDomainfloder}/${classLowerNameWithoutPrefix}/controller.js?v= ${r'${cssVersion}'}"></script>

</body>
</html>
