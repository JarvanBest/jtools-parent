package ${basepackage}.entity;



import javax.persistence.*;


<#assign myParentDir="entity">


<#assign className=table.className>


<#assign classNameLower=className?uncap_first>



<#include "/copyright_class.include" >
@Table(name = "${table.sqlName}")
public class ${className}  implements Serializable,${table.entityBaseName}{
	
	//columns START
	<#list table.columns as column>
	/**
	 * ${column.columnAlias}
	 */
	 <#if column.isPk()>
	@Id
	</#if>
	private ${column.simpleJavaType} ${column.camelName};
	</#list>
	//columns END 数据库字段结束

	//get and set
	<@generateJavaColumns/>


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		${className} otherObj=(${className})o;
		<#list table.columns as column>
		if (${column.camelName}!=null && !${column.camelName}.equals(otherObj.${column.camelName})) {
			return false;
		}else if(${column.camelName}==null && otherObj.${column.camelName}!=null){
			return false;
		}
		</#list>
		return true;
	}

	@Override
	public int hashCode() {
		int result= (${table.getPkColumn().camelName}==null? (Long)0L:${table.getPkColumn().camelName}).hashCode();
		<#list table.columns as column>
		<#if !column.isPk()>
		if(${column.camelName}!=null) {
			result = 31 * result + ${column.camelName}.hashCode();
		}
		</#if>
		</#list>
		return result;
	}

	@Override
	public String toString() {
		return new StringBuffer()
		<#list table.columns as column>
			.append("${column.camelName}=").append(get${column.firstUperCamelName}()).append(",")
		</#list>
			.toString();
	}
	
	
}

	
<#macro generateJavaColumns>
	<#list table.columns as column>
		<#if column.isDateTimeColumn>

		</#if>
	/**
	 * ${column.columnAlias}
	 */
	<#if column.isPk() || column.hasOverride>
	@Override
	</#if>
	public void set${column.firstUperCamelName}(${column.simpleJavaType} ${column.camelName}) {
	     <#if column.simpleJavaType=="String">
		    if(StringUtils.isNotEmpty(${column.camelName})){
			 ${column.camelName}=${column.camelName}.trim();
			}
		 </#if>
		this.${column.camelName} = ${column.camelName};
	}

	<#if column.isPk() || (column.hasOverride) >
	@Override
	</#if>
	@Column(name = "${column.sqlName}")
	public ${column.simpleJavaType} get${column.firstUperCamelName}() {
		return this.${column.camelName};
	}
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>
	
	

