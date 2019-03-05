function clsMethodLee(){
    this.requestUrl = {
        "path1":"/grumble/findByPage",//客户抱怨list接口
        "path2":"/grumble/findByPage",//客户表扬list接口
        "path3":"/grumble/add",//客户抱怨新增/修改
        "path4":"/grumble/add",//客户表扬新增/修改
        "path5":"/grumble/delete",//客户抱怨删除,
        "path6":"/grumble/delete",//客户表扬删除,
        "path7":"/grumble/update",//客户抱怨/表扬反馈,
        "path8":"/sysAcct/customerType"//初始化页面，判断登陆人是否有删除和反馈的权限
    };
    this.documentLee = null;
    this.operateType = "";//操作类型 0-客户抱怨新增 1-客户抱怨编辑  2-客户表扬新增  3-客户表扬编辑 4——抱怨反馈   5——表扬反馈
    this.loginerNews = {//初始化页面会有客户联系编号	客户单位名称
        "customerId":"",//客户联系编号
        "customerName":""//客户单位名称
    };
    this.cid = "";//删除，编辑，添加操作需要cid是否为空判断(客户抱怨)
    //this.lastUserId = "";//删除，编辑，添加操作需要cid是否为空判断（客户表扬）
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //tab切换ul
    this.tabSelect = $("#tabSelect");
    //客户抱怨盒子
    this.cusComplain = $("#cusComplain");
    //客户抱怨盒子新增按钮
    this.createList1 = $("#cusComplain #createList");
    //客户抱怨table
    this.tableList1 = $("#tableList1");
    //客户表扬盒子
    this.cusPraise = $("#cusPraise");
    //客户表扬盒子新增按钮
    this.createList2 = $("#cusPraise #createList");
    //客户表扬table
    this.tableList1 = $("#tableList2");
    //弹框的title
    this.textChangeBoxTitle = $("#textChangeBoxTitle");
    //新增客户抱怨弹框内容
    this.cusComplainPopup = $("#cusComplainPopup");
    //新增客户表扬弹框内容
    this.cusComplainPopup = $("#cusPraisePopup");
    //弹框确定按钮
    this.newsChageSure = $("#newsChageSure");
    //弹框取消按钮
    this.newsChageCancel = $("#newsChageCancel");
    this.parse();

}
function clsMethodLee$parse(){
    $("[id=condcategoryName]").chosen({
        no_results_text: "暂无结果",
        width: "205px",
        enable_split_word_search: false,
        placeholder_text_single: '请选择'
    });
    $("#textChangeBox [id=categoryName]").chosen({
        no_results_text: "暂无结果",
        width: "352px",
        enable_split_word_search: false,
        placeholder_text_single: '请选择'
    });
    $("#textChangeBox [id=tType]").chosen({
        no_results_text: "暂无结果",
        width: "352px",
        enable_split_word_search: false,
        placeholder_text_single: '请选择'
    });
    getAjaxResult(document.body.jsLee.requestUrl.path8,"POST",{},"loginCallBack(data)");
    limitCodeDeal($("*[limitCode]"),"limitCode");
    initplugPath($("#tableList1")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,{"cType":"抱怨"},"POST");
    this.operate();
}

function clsMethodLee$operate(){
    //tab切换
    this.tabSelect.find("li").on("click",function () {
        if($(this).attr("nidx") == 0){//客户抱怨
            initplugPath($("#tableList1")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,{"cType":"抱怨"},"POST");
        }else{//客户表扬
            $("#tableList2").attr("comType","standardTableCtrl");
            /*var paramJson = JSON.parse($("#tableList2").attr("reqParam"));
            paramJson.customerId = document.body.jsLee.loginerNews.customerId;
            paramJson.customerName = document.body.jsLee.loginerNews.customerName;*/
            $("#tableList2").attr({"reqParam":JSON.stringify({"cType":"表扬"}),"reqPath":document.body.jsLee.requestUrl.path2});
            document.body.jsCtrl.ctrl = $("#tableList2")[0];
            document.body.jsCtrl.init();
            //initplugPath($("#tableList2")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,null,"POST");
        }
    });

    this.newsChageCancel.on("click",function(){//弹框取消操作
        closePopupWin();
    });

    this.newsChageSure.on("click",function(){//弹框确认操作
        if(document.body.jsLee.operateType == 0 || document.body.jsLee.operateType == 1){
            var domCon = $("#cusComplainPopup")[0];
        }else{
            var domCon = $("#cusPraisePopup")[0];
        }
        initValidate(domCon);
        var valiClass=new clsValidateCtrl();
        if(valiClass.validateAll4Ctrl(domCon)){
            ajaxExecute(document.body.jsLee.operateType);
        }
    });

    this.createList1.on("click",function(){//客户抱怨列表新增
        $("#cusComplainPopup .manageNews-main-search-list:last").hide();
        $("#cusPraisePopup .manageNews-main-search-list:last").hide();
        $("#cusComplainPopup #feedbackContent").removeClass("required");
        $("#cusPraisePopup #feedbackContent").removeClass("required");
        openWinShow(0,"");
        $("#popOpe").show();

    });

    this.createList2.on("click",function(){//客户表扬列表新增
        $("#cusComplainPopup .manageNews-main-search-list:last").hide();
        $("#cusPraisePopup .manageNews-main-search-list:last").hide();
        $("#cusComplainPopup #feedbackContent").removeClass("required");
        $("#cusPraisePopup #feedbackContent").removeClass("required");
        openWinShow(2,"");
        $("#popOpe").show();

    });
}
function clsMethodLee$refresh(){

}

//已有数组，初始化插件;
function initplugData(docm,comType,data){
    $(docm).attr("comType",comType);
    docm.data = {"rspBody":{"resultData":data}};
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

//打开弹框操作
function openWinShow(type,cidStr,isTrue){//type操作类型 0-客户抱怨新增 1-客户抱怨编辑  2-客户表扬新增  3-客户表扬编辑   ||    cid当前编辑行的cid
    //清空内容
    if(isTrue){//isTrue是否置灰
        $("#textChangeBox input[type=text]").attr("disabled",true);
        $("#textChangeBox select").attr("disabled",true);
        $("#textChangeBox textarea:first").attr("disabled",true);
    }else{
        $("#textChangeBox input[type=text]").attr("disabled",false);
        $("#textChangeBox select").attr("disabled",false);
        $("#textChangeBox textarea:first").attr("disabled",false);
    }
    $("#textChangeBox input[type=text]").val("");
    $("#textChangeBox textarea").val("");
    $("#textChangeBox select option[value='']").attr("selected",true);
    $("#textChangeBox select").trigger('chosen:updated');
    //显示ul判断
    if(type == 0 || type == 1){//客户抱怨
        $("#cusComplainPopup").show();
        $("#cusPraisePopup").hide();
    }else{//客户表扬
        $("#cusComplainPopup").hide();
        $("#cusPraisePopup").show();
    };
    //修改title信息
    switch(type){
        case 0 :
            $("#textChangeBoxTitle").html("客户抱怨");
            break;
        case 1 :
            $("#textChangeBoxTitle").html("编辑质量异议提报人员信息");
            break;
        case 2 :
            $("#textChangeBoxTitle").html("新增客户表扬信息");
            break;
        case 3 :
            $("#textChangeBoxTitle").html("编辑客户表扬信息");
            break;
    }
    openWin(950,350,'textChangeBox',true);
    document.body.jsLee.operateType = type;
    document.body.jsLee.cid = cidStr;
}

//插件渲染操作
function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    if(jsonItem.defaultFlag == "Y"){
        $(cloneRow).find("#isDefault").html("是");
    }else if(jsonItem.defaultFlag == "N"){
        $(cloneRow).find("#isDefault").html("否");
    }
    if(this.ctrl.id == "tableList1"){//客户抱怨
        $(cloneRow).find("#detailOpe").on("click",function(){//详情操作
            $("#popOpe").hide();
            $("#cusComplainPopup .manageNews-main-search-list:last").hide();
            $("#cusPraisePopup .manageNews-main-search-list:last").hide();
            $("#cusComplainPopup #feedbackContent").removeClass("required");
            $("#cusPraisePopup #feedbackContent").removeClass("required");
            openWinShow(1,jsonItem.cid,true);
            document.body.jsLee.cid = jsonItem.cid;
            setValue4Desc(jsonItem,$("#cusComplainPopup")[0])//赋值
        });
        $(cloneRow).find("#feedbackOpe").on("click",function(){//反馈操作
            $("#popOpe").show();
            $("#cusComplainPopup .manageNews-main-search-list:last").show();
            $("#cusComplainPopup #feedbackContent").addClass("required");
            openWinShow(1,jsonItem.cid,true);
            document.body.jsLee.cid = jsonItem.cid;
            setValue4Desc(jsonItem,$("#cusComplainPopup")[0])//赋值
            document.body.jsLee.operateType = 4;
        });
        $(cloneRow).find("#deleteOpe").on("click",function(){//删除操作
            document.body.jsLee.cid = jsonItem.cid;
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("确定删除？","警告提示",1,"","ope1");
        });
    }else if(this.ctrl.id == "tableList2"){//客户表扬
        $(cloneRow).find("#detailOpe").on("click",function(){//详情操作
            $("#popOpe").hide();
            $("#cusComplainPopup .manageNews-main-search-list:last").hide();
            $("#cusPraisePopup .manageNews-main-search-list:last").hide();
            $("#cusComplainPopup #feedbackContent").removeClass("required");
            $("#cusPraisePopup #feedbackContent").removeClass("required");
            openWinShow(3,jsonItem.lastUserId,true);
            document.body.jsLee.lastUserId = jsonItem.cid;
            setValue4Desc(jsonItem,$("#cusPraisePopup")[0])//赋值
        });
        $(cloneRow).find("#feedbackOpe").on("click",function(){//反馈操作
            $("#popOpe").show();
            $("#cusPraisePopup .manageNews-main-search-list:last").show();
            $("#cusPraisePopup #feedbackContent").addClass("required");
            openWinShow(3,jsonItem.cid,true);
            document.body.jsLee.cid = jsonItem.cid;
            setValue4Desc(jsonItem,$("#cusComplainPopup")[0])//赋值
            document.body.jsLee.operateType = 5;
        });
        $(cloneRow).find("#deleteOpe").on("click",function(){//删除操作
            document.body.jsLee.cid = jsonItem.cid;
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("确定删除？","警告提示",1,"","ope2");
        });
    }
}

//新增或者编辑回调函数
function updateSubmitCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        if(document.body.jsLee.operateType == 0 || document.body.jsLee.operateType == 1 || document.body.jsLee.operateType == 4) {//客户抱怨
            initplugPath($("#tableList1")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,{"cType":"抱怨"},"POST");
            document.body.jsLee.cid = "";
        }else if(document.body.jsLee.operateType == 2 || document.body.jsLee.operateType == 3 || document.body.jsLee.operateType == 5){
            initplugPath($("#tableList2")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,{"cType":"表扬"},"POST");
            document.body.jsLee.cid = "";
        }
    }
}

//弹框确认添加或者编辑
function ajaxExecute(type){//type区分客户抱怨和客户表扬
    if(type == 0 || type == 1){//客户抱怨
        var jsonParam = {"tType":"","categoryName":"","productName":"","grumbleContent":"","cType":"抱怨"};
        getValue4Desc(jsonParam,$("#cusComplainPopup")[0])
        getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",jsonParam,"updateSubmitCallBack(data)");
    }else if(type == 2 || type == 3){//客户表扬
        var jsonParam = {"tType":"","categoryName":"","productName":"","grumbleContent":"","cType":"表扬"};
        getValue4Desc(jsonParam,$("#cusPraisePopup")[0]);
        getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",jsonParam,"updateSubmitCallBack(data)");
    }else if(type == 4){//抱怨反馈
        var jsonParam = {"cid":document.body.jsLee.cid,"feedbackContent":$("#cusComplainPopup #feedbackContent").val()};
        getAjaxResult(document.body.jsLee.requestUrl.path7,"POST",jsonParam,"updateSubmitCallBack(data)");
    }else if(type == 5){//表扬反馈
        var jsonParam = {"cid":document.body.jsLee.cid,"feedbackContent":$("#cusPraisePopup #feedbackContent").val()};
        getAjaxResult(document.body.jsLee.requestUrl.path7,"POST",jsonParam,"updateSubmitCallBack(data)");

    }
}

//客户抱怨删除回调
function delete1CallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        initplugPath($("#tableList1")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,{"cType":"抱怨"},"POST");
    }
}

//客户表扬删除回调
function delete2CallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        initplugPath($("#tableList2")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,{"cType":"表扬"},"POST");
        document.body.jsLee.cid = "";
        document.body.jsLee.cid = "";
    }
}

//弹框点击确定返回函数
function clsAlertBoxCtrl$sure() {
    if(this.id == "ope1"){//删除操作
        closePopupWin();
        getAjaxResult(document.body.jsLee.requestUrl.path5,"POST",{"cid":document.body.jsLee.cid},"delete1CallBack(data)");
    }else if(this.id == "ope2"){
        closePopupWin();
        getAjaxResult(document.body.jsLee.requestUrl.path6,"POST",{"cid":document.body.jsLee.cid},"delete2CallBack(data)");
    }
}

//搜索回调函数
function clsSearchBtnCtrl$after(jsonCond) {
    /*if(this.ctrl.id == "searchBtn2"){
        jsonCond.customerId = document.body.jsLee.loginerNews.customerId;
    }*/
    return jsonCond;
}

function loginCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        if(data.rspBody.acctType != 5){//具有反馈权限
            $("#tableList1 #feedbackOpe").remove();
            $("#tableList2 #feedbackOpe").remove();
        }else{
            $("#createList").remove();
        }
        if(data.rspBody.orgName != "酒泉钢铁（集团）有限责任公司" && data.rspBody.orgName != "超级管理员"){
            $("#tableList1 #deleteOpe").remove();
            $("#tableList2 #deleteOpe").remove();
        }
    }
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});