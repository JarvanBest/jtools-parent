<#assign myParentDir="entity">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign classNameLowerFull = className?lower_case>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>
<#assign clasNameWithoutPrefix = classNameLowerFull?replace(rootPagefloder,'')>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
	<title>${table.remarks}详细详细</title>
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
		<form id="myView">
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
						<span class="label app-label">${table.remarks}详细详细</span>
						<i class="fold"></i>
					</div>
					<div class="panel-bodying">
						<div class="information-body">
							<ul class="information">
								<#list table.columns as column><#if !column.isPk()  && !column.hiddenField >
								<li>
									<label> ${column.columnAlias}：</label>
									<div class="list-info">
								 		${r'${'}data.${column.camelName}${r'}'}
									</div>
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
								<jsp:include page="/common/attachment_detail.jsp"/>
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
				<li class="active"><a href="#basicInfo"><i></i>基本信息</a></li>
				<li class="active"><a href="#attachInfo"><i class="icons-arrow"></i>附件信息</a></li>
			</ul>
			<button class="big-btn big-return radius5" type="button" onclick="back();"><i class="custom-return"></i>返回</button>
		</div>
	</div>
</div>
</body>

<script type="text/javascript" src="${r'${contextPath}'}/pages/${rootDomainfloder}/${classLowerNameWithoutPrefix}/controller.js?v= ${r'${cssVersion}'}"></script>

</html>
