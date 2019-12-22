<#assign myParentDir="entity">
<#assign className = table.className>
<#assign classNameLower = className?lower_case>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>
<#assign clasNameWithoutPrefix = classNameLower?replace(rootPagefloder,'')>
var mytitle;
var title;
var saveState=false;
$(function(){
    mytitle = parent.giveTitle();
    title = $(".app-label").text();
    var context =$("#mygrid");
    if(context.size()>0) {
        //加载数据
        loadData();
    }
});

//加载数据
function loadData(){
    var size=getGridPageSize('#myBody',1);
    var searchK = $('#searchKey').val();
    jQuery("#mygrid").datagrid({
        url: zy.contextPath+'/${rootPagefloder}/${clasNameWithoutPrefix}/list',
        idField:'id',
        pageList: [size,size*10,size*20,size*30,size*40,size*50,size*100],
        pageSize: size,
        pagination:true,
        rownumbers:true,
        remoteSort: false,
        queryParams:{
            filter:{
            },
            searchKey:searchK
        },
        frozenColumns:[[
            {field:'CK',checkbox: true},
            {field:'id',title:'编号',hidden:'true'},
            {field:'opt',title: '操作',halign:'center', align:'left',width:"115px",formatter :
                function(value, row, index) {
                    var htm='';
                    htm+="<a href='javascript:show(\""+row.id+"\")' title='查看' class='small-btn small-eye'><em class='pm-eye'></em></a>&nbsp;&nbsp;";
                    return htm;
                }
            },
            {field:'taskName',title:'状态',halign:'center',align:'center',width:"100px",sortable:true,formatter:function (value, row, index) {
                    if(row.historyid==-1){
                        return '<a href="javascript:show('+row.buyprojectId+');" title="审核" class="red">'+row.taskName+'</a>';
                    }
                    return value;
                }}
        ]],
        columns:[[
        <#list table.columns as column><#if !column.isPk() && !column.hiddenField >
    {field:'${column.camelName}',title:'${column.columnAlias}',halign:'center',align:'center',width:"150px",sortable:true},
        </#if></#list>
        ]],
        onDblClickRow:function(index,row){
            show(row.id);
        }
    });
}

//点击搜索
function showSearchDialog(){
    var context=$('#searchDialogWraper');
    $("input[type=reset]",context).trigger("click");
    context.dialog('open');
}

//确认搜索
function doSearch(){
    var searchJson=zy.serializeObject($("#searchDialogForm"));
    $('#mygrid').datagrid('load',{filter:searchJson});
    $('#searchDialogWraper').dialog('close');
}

//回车搜索
function searchSubmit(e){
    if(e.keyCode==13){
        doSearch();
    }
}

//添加
function add(){
    var src=zy.contextPath+"/${rootPagefloder}/${clasNameWithoutPrefix}/get";
    window.parent.addPanel("新增基本信息",src);
}

function save(){
    $("#variables_commit").val("")
    dataSubmit();
}

function dataSubmit(callback){
    $("#myForm").form('submit',{
        ajax:true,
        onSubmit: function(callback){
            //防止重复提交
            var isValid = $(this).form('validate') && validAttachmentsRequired();
            if (isValid){
                //等待处理...
                showLoading();
            }
            return isValid;	// 返回false终止表单提交
        },
        success:function(result){
            hidenLoading();
            result = formatToJsonResult(result);
            updateIdAndProcessId(result)
            if(hasSuccess(result)){
                parent.messages(result.msg);
                saveState = true;
                if(callback!=null && typeof callback ==='function'){
                    callback(result)
                }
            }else {
                $.messager.alert('系统消息',result.msg,'error');
            }
        }
    });
}

function submitData() {
    $("#variables_commit").val(true);
    dataSubmit(function (data) {
        back()
    });
}

//返回
function back(){
    parent.backParentWindow(saveState,mytitle);
}

//查看
function show(id){
    var src = getEditSrc(id,false);
    parent.addPanel("基本信息",src);
}

//编辑
function edit(id){
    parent.addPanel("修改基本信息",getEditSrc(id,true));
}

//变更
function change(id){
    parent.addPanel("变更基本信息",getEditSrc(id,true));
    parent.closeTab(title);
}

function getEditSrc(id,edit){
    var src=zy.contextPath+"/${rootPagefloder}/${clasNameWithoutPrefix}/get?id="+id;
    if(edit){
        src+="&edit="+true;
    }
    return src;
}

//删除
function delOne(id){
    var ids = new Array();
    ids.push(id);
    $.messager.confirm('系统消息','确认要删除选中的数据吗？', function(ok){
        if (ok) {
            showLoading();
            $.post(zy.contextPath+'/${rootPagefloder}/${clasNameWithoutPrefix}/delete', {
                ids:ids.toString()
            }, function (result){
                //关闭等待
                hidenLoading();
                if(zy.success  == result.code){
                    parent.messages(result.msg);
                    //重新加载
                    reloadPage();
                }else {
                    parent.messages(result.msg);
                    return;
                }
            },'json');
        }
    });
}

