<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  
<#assign classNameLowerCase = className?lower_case>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>

<#assign pid="business_manager" />
<#if table.sqlName?starts_with("t_")>
<#assign pid="system_manager" />
</#if>

#INSERT INTO `sys_menus` (`pid`,`code`, `name`, `type`,`sort`, `url`, `permission`, `icon`, `disabled`,`description`)
#VALUES (81,'${rootPagefloder}', '${rootPagefloder}',1,0, '',  '${rootPagefloder}:all', '',0, '');

select @pid :=id  from `sys_menus` where `code`='${rootPagefloder}';

INSERT INTO `sys_menus` (`pid`,`code`, `name`, `type`,`sort`, `url`, `permission`, `icon`, `disabled`,`description`) VALUES (@pid,'${table.sqlName}_list', '${table.tableAlias}管理',0,10, '/${rootPagefloder}/${classNameLowerCase}/list',  '${rootPagefloder}:${classNameLowerCase}:list', '',0, '');

select @pid :=id  from `sys_menus` where `code`='${table.sqlName}_list';
INSERT INTO `sys_menus` (`pid`,`code`, `name`, `type`,`sort`, `url`, `permission`, `icon`, `disabled`,`description`) VALUES (@pid,'${table.sqlName}_edit', '${table.tableAlias}修改',1,20, '',  '${rootPagefloder}:${classNameLowerCase}:edit', '',0, '');
INSERT INTO `sys_menus` (`pid`,`code`, `name`, `type`,`sort`, `url`, `permission`, `icon`, `disabled`,`description`) VALUES (@pid,'${table.sqlName}_delete', '${table.tableAlias}删除',1,99, '',  '${rootPagefloder}:${classNameLowerCase}:delete', '',0, '');

<#if (2>100)>
INSERT INTO `t_role_menu` VALUES ('${table.sqlName}_list_admin', 'admin', '${table.sqlName}_list');
INSERT INTO `t_role_menu` VALUES ('${table.sqlName}_update_admin', 'admin', '${table.sqlName}_update');
INSERT INTO `t_role_menu` VALUES ('${table.sqlName}_look_admin', 'admin', '${table.sqlName}_look');
INSERT INTO `t_role_menu` VALUES ('${table.sqlName}_export_admin', 'admin', '${table.sqlName}_export');
INSERT INTO `t_role_menu` VALUES ('${table.sqlName}_delMulti_admin', 'admin', '${table.sqlName}_delMulti');
INSERT INTO `t_role_menu` VALUES ('${table.sqlName}_delete_admin', 'admin', '${table.sqlName}_delete');
INSERT INTO `t_role_menu` VALUES ('${table.sqlName}_upload_admin', 'admin', '${table.sqlName}_upload');
</#if>