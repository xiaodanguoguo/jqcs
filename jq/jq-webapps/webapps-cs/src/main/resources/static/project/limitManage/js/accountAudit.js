function clsMethodLee(){
    this.requestUrl = {
        "path1":"/orgInfo/audit/list",//账户列表
        "path3":"/orgInfo/audit",//账户审核提交接口
        "path2":"/orgInfo/getListTreeOrgInfo",//新建用户，获取组织集联列表集合
    };
    this.documentLee = null;
    this.idMark = "";//审核标识
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.organizationlastId = ""//组织选择后的末级id;
    this.organizationjoinId = ""//组织选择后的组合id;
    this.organizationlastIdHistory = ""//组织选择后的末级历史id;
    this.organizationjoinIdHistory = ""//组织选择后的组合历史id;
    this.orgType = ""//组织选择后的orgType,判断客户类型;
    this.orgTypeArr = [];
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //弹框组织点击dom
    this.organizationShow = $("#selectOrganization");
    //新建用户弹框选择组织后的确认按钮
    this.organizationSelectSure = $("#organizationSelectSure");
    //新建用户弹框选择组织后的取消按钮
    this.organizationSelectCancel = $("#organizationSelectCancel");
    //审核通过
    this.auditSure = $("#auditSure");
    //审核取消
    this.auditCancel = $("#auditCancel");
    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    $("#condstatus").chosen({
        no_results_text: "暂无结果",
        enable_split_word_search: false,
        placeholder_text_single: ""
    });

    initplugData($("#orgTypeA")[0],"singleSelectCtrl",this.orgTypeArr);
    this.operate();
}

function clsMethodLee$operate(){
    this.organizationShow.on("click",function(){//弹框 组织显示
        if($("#selectOrganizationPopup").is(":hidden")){
            document.body.jsLee.organizationlastIdHistory = document.body.jsLee.organizationlastId;
            document.body.jsLee.organizationjoinIdHistory = document.body.jsLee.organizationjoinId;
            $("#selectOrganizationPopup").show();
            $("#selectRolePopup").hide();
            //复现当前所选组织
            orgistClear();//清空小title组织显示
            var orgIdNameArr = $("#selectOrganization").attr("orgIdNameArr") ? JSON.parse($("#selectOrganization").attr("orgIdNameArr")) : [];
            for(var nI = 0; nI < orgIdNameArr.length; nI++ ){
                createRow(orgIdNameArr[nI].orgId,orgIdNameArr[nI].orgName);
            }
            if($("#organizationList").attr("reqParam")){
                var jsonParam = JSON.parse($("#organizationList").attr("reqParam"));
            }else{
                var jsonParam = {};
            }
            jsonParam.id = document.body.jsLee.organizationlastId;
            initplugPath($("#organizationList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,jsonParam,"POST")
        }else{
            $("#selectOrganizationPopup").hide();
        }
    });
    this.organizationSelectSure.on("click",function(){//新建用户弹框选择完组织后确定按钮
        //修改组织对角色重新赋值
        var arr = JSON.parse($("#selectOrganization").attr("orgidnamearr"));
        if(arr.length > 0){
            if(arr[arr.length-1].orgId != document.body.jsLee.organizationlastId){
                $("#selectRole").attr("roleids",JSON.stringify([])).html("");
            }
        }
        //对组织显示div重新赋值
        var htmlText = "";
        var orgIdNameArr = [];
        $("#selectOrganizationPopup *[id=cloneList]").each(function(){
            htmlText += htmlText == "" ? $(this).find("#orgNameShow").html() : "," + $(this).find("#orgNameShow").html();
            orgIdNameArr.push({"orgId":$(this).attr("idmark"),"orgName":$(this).find("#orgNameShow").html()});
        });
        $("#selectOrganization").html(htmlText);
        //为复现数据  显示弹框内容做数据缓存
        $("#selectOrganization").attr("orgIdNameArr",JSON.stringify(orgIdNameArr));
        $("#selectOrganizationPopup").hide();
        //渲染客服类型选择
        switch (document.body.jsLee.orgType){
            case "1":
                document.body.jsLee.orgTypeArr = [{"orgTypeCode":2,"orgTypeName":"一级代理"},{"orgTypeCode":3,"orgTypeName":"贸易商"},{"orgTypeCode":4,"orgTypeName":"终端用户"}];
                break;
            case "2":
                document.body.jsLee.orgTypeArr = [{"orgTypeCode":3,"orgTypeName":"贸易商"},{"orgTypeCode":4,"orgTypeName":"终端用户"}];
                break;
            case "3":
                document.body.jsLee.orgTypeArr = [{"orgTypeCode":4,"orgTypeName":"终端用户"}];
                break;
            case "4":
            case "":
                document.body.jsLee.orgTypeArr = [];
                break;
        }
        $("#orgTypeA").attr("initValue","");
        initplugData($("#orgTypeA")[0],"singleSelectCtrl",document.body.jsLee.orgTypeArr);
    });
    this.organizationSelectCancel.on("click",function() {//新建用户弹框选择完组织后取消按钮
        $("#selectOrganizationPopup").hide();
        document.body.jsLee.organizationlastId = document.body.jsLee.organizationlastIdHistory;
        document.body.jsLee.organizationjoinId = document.body.jsLee.organizationjoinIdHistory;
    });
    //审核通过
    this.auditSure.on("click",function () {
        if(checkBox()){
            var jsonParam = {"orgName":"","addr":"","email":"","orgCode":"","sapCode":""};
            getValue4Desc(jsonParam,$("#auditPopup")[0]);
            jsonParam.orgType = $("#orgTypeA option:selected").val();
            jsonParam.parentId = document.body.jsLee.organizationlastId;
            jsonParam.id = document.body.jsLee.idMark;
            getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",jsonParam,"submitCallBack(data)");
        }
    });
    //审核取消
    this.auditCancel.on("click",function () {
        closePopupWin();
    });
}
function clsMethodLee$refresh(){

}

//已有数组，初始化插件;
function initplugData(docm,comType,data){
    $(docm).attr("comType",comType);
    docm.data = {"rspBody":data};
    document.body.jsCtrl.ctrl = docm;
    document.body.jsCtrl.init();
}
//未知数组，已有接口，初始化插件;
function initplugPath(docm,comType,reqPath,reqParam,reqMethod){
    if(reqPath != null){
        $(docm).attr("reqPath",reqPath);
    }
    if(reqParam != null){
        $(docm).attr("reqParam",JSON.stringify(reqParam));
    }
    if(reqMethod != null){
        $(docm).attr("reqMethod",reqMethod);
    }
    $(docm).attr("comType",comType);
    document.body.jsCtrl.ctrl = docm;
    document.body.jsCtrl.init();
}

function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    if(this.ctrl.id == "tableList"){
        if(jsonItem.status == 2){
            $(cloneRow).find("#statusA").html("待审核");
            $(cloneRow).find("#auditOpe").show();
        }else if(jsonItem.status == 5){
            $(cloneRow).find("#statusA").html("审核完成");
        }
        //客户类型
        if(jsonItem.orgType == 1){
            $(cloneRow).find("#orgTypeAb").html("销售公司");
        }else if(jsonItem.orgType == 2){
            $(cloneRow).find("#orgTypeAb").html("一级代理");
        }else if(jsonItem.orgType == 3){
            $(cloneRow).find("#orgTypeAb").html("贸易商");
        }else if(jsonItem.orgType == 4){
            $(cloneRow).find("#orgTypeAb").html("终端用户");
        }
        //审核操作
        $(cloneRow).find("#auditOpe").on("click",function () {
            openWin('550', '600', 'inputAnswer', true);
            //清空内容
            $("#selectOrganization").html("");
            //重新赋值请求组织的末级id
            document.body.jsLee.organizationlastId = "";
            document.body.jsLee.organizationjoinId = "";
            $("#selectOrganization").attr("orgidnamearr",JSON.stringify([]));
            setValue4Desc(jsonItem,$("#auditPopup")[0])//赋值
            initplugData($("#orgTypeA")[0],"singleSelectCtrl",[]);
            document.body.jsLee.idMark = jsonItem.id;
        });
    }else if(this.ctrl.id == "organizationList"){//组织弹框列表
        $(cloneRow).find("#orgName").attr("markId",jsonItem.id);
        $(cloneRow).find("#orgName").on("click",function(){//点击组织，进行选择，调取下一级的组织列表；集联操作
            document.body.jsLee.organizationlastId = jsonItem.id;
            if($("#organizationList *[id=cloneList]").length == 0){//如果第一个不加","
                document.body.jsLee.organizationjoinId += jsonItem.id;
            }else{
                document.body.jsLee.organizationjoinId += ("," + jsonItem.id);
            }
            //选择后渲染顶部标题面包屑显示
            createRow(jsonItem.id,jsonItem.orgName);
            var jsonParam = {};
            /*if(!document.body.jsLee.organizationjoinId){
                jsonParam.id = document.body.jsLee.userId;
            }else{
                jsonParam.id = document.body.jsLee.organizationlastId;
            }*/
            if($("#organizationList").attr("reqParam")){
                var jsonParam = JSON.parse($("#organizationList").attr("reqParam"));
            }else{
                var jsonParam = {};
            }
            jsonParam.id2 = document.body.jsLee.userId;
            jsonParam.id = document.body.jsLee.organizationlastId;
            //调取下级集联接口
            initplugPath($("#organizationList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path9,jsonParam,"POST")
            //缓存orgType
            document.body.jsLee.orgType = jsonItem.orgType;
        });
    }
}
//点击选择组织的时候，当前弹框组织清空
function orgistClear(){
    $("#organizationList *[id=cloneList]").remove();
}

//选择后渲染顶部标题面包屑显示
function createRow(idMark,nameMark){//组织id，组织name
    var cloneList = $("#orgSelectDetail").find("#templateList").clone(true);
    cloneList.show().attr({"id":"cloneList","idMark":idMark});
    cloneList.find("#orgNameShow").html(nameMark);
    $("#orgSelectDetail").find("#templateList").before(cloneList);
    //删除操作
    cloneList.find("#orgNameShowDelete").on("click",function(){
        $(this).parents("#cloneList").nextAll("*[id=cloneList]").remove();
        $(this).parents("#cloneList").remove();
        var idsMark = document.body.jsLee.organizationjoinId.split($(this).parents("#cloneList").attr("idMark"))[0];
        if(idsMark[idsMark.length - 1] == ","){
            idsMark = idsMark.substring(0,idsMark.length-1);
        }
        //赋值角色需要的组织组合id
        document.body.jsLee.organizationjoinId = idsMark;
        // 赋值末级id
        document.body.jsLee.organizationlastId = document.body.jsLee.organizationjoinId.split(",")[document.body.jsLee.organizationjoinId.split(",").length-1];
        var jsonParam = {};
        /*if(!document.body.jsLee.organizationjoinId){
            jsonParam.id = document.body.jsLee.userId;
        }else{
            jsonParam.id = document.body.jsLee.organizationlastId;
        }*/
        if($("#organizationList").attr("reqParam")){
            var jsonParam = JSON.parse($("#organizationList").attr("reqParam"));
        }else{
            var jsonParam = {};
        }
        jsonParam.id = document.body.jsLee.organizationlastId;
        initplugPath($("#organizationList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,jsonParam,"POST")
        if(!document.body.jsLee.organizationlastId){
            ocument.body.jsLee.orgType = "";
        }
    });
}

//审核提交校验
function checkBox(){
    if(!$("#selectOrganization").html()){
        showErrInfoByCustomDiv($("#selectOrganization")[0],"请选择所属组织信息!");
        return false;
    }
    return true;
}

//按照组件重新编写div校验
function showErrInfoByCustomDiv(elem,error)
{
    $(elem).poshytip({
        className: 'tip-twitter',
        showOn: 'none',
        alignTo: 'target',
        alignX: 'right',
        alignY: 'inner-right',
        content:error,
        offsetX: 5,
        offsetY: -30,
        autoHide:true,
        hideTimeout:5000
    });
    $(elem).poshytip('hide');
    $(elem).poshytip('show');
}

function submitCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    }
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});