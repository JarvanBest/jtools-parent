package ${basepackage}.vo;

import javax.persistence.*;
<#assign myParentDir="entity">
<#assign className=table.className>
<#assign classNameLower=className?uncap_first>

<#include "/copyright_class.include" >
public class ${className}Vo  extends ${className} implements AttachFileEntityVo,AbstractFlowEntityVo,ExtendInfoEntity{

	/**
	 * 待办ID
	 */
	private String taskId;

	/**
	 * 待办名称
	 */
	private String taskName;

	/**
	 * 附件列表
	 */
	private List<Attachment> attachments;

	/**
	 *  是否可以修改
	 */
	private Boolean canModify;

	/**
	 * 创建流程的参数
	 */
	private Map<String, Object> variables;

	/**
	 * 附件类型名称- 附件信息 map
	 */
	private Map<String, Attachment> fileTypeMap;

	/**
	 * 扩展属性名称-属性只
	 */
	private Map<String, Object> attributes;


	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public String getTaskName() {
		return taskName;
	}

	@Override
	public Boolean getCanModify() {
		return canModify;
	}
	@Override
	public void setCanModify(Boolean canModify) {
		this.canModify = canModify;
	}

	@Override
	public List<Attachment> getAttachments() {
		return attachments;
	}

	@Override
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}


	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	@Override
	public Map<String, Object> getVariables() {
		if(variables==null){
			variables=new HashMap<>();
		}
		return variables;
	}

	@Override
	public Map<String, Attachment> getFileTypeMap() {
		if (fileTypeMap == null) {
			fileTypeMap = new HashMap<>();
		}
		return fileTypeMap;
	}

	@Override
	public void setFileTypeMap(Map<String, Attachment> fileTypeMap) {
		this.fileTypeMap = fileTypeMap;
	}

	@Override
	public Map<String, Object> getAttributes() {
		if (attributes == null) {
			attributes =new HashMap<>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(Map<String, Object> values) {
		this.attributes = values;
	}

	@Override
	public String getFileTypeDictCode() {
		return "${className}";
	}

}


