function clsMethodLee(){
    this.requestUrl = {
        "path1":"/orderUnit/findByPage",//订货单位list接口
        "path2":"/unitOfUse/findByPage",//使用单位list接口
        "path3":"/orderUnit/orderUnitInsert",//订货单位新增/修改
        "path4":"/unitOfUse/unitOfUseInsert",//使用单位新增/修改
        "path5":"/orderUnit/orderUnitDelete",//订货单位删除,
        "path6":"/unitOfUse/unitOfUseDelete",//使用单位删除
        "path7":"/orderUnit/customerInfo",//订货单位获取当前登录人信息
        "path8":"/agentInfo/findByPage",//代理公司list接口
        "path9":"/agentInfo/agentInfoInsert",//代理公司新增/修改
        "path10":"/agentInfo/agentInfoDelete"//代理公司删除,


    };
    this.documentLee = null;
    this.operateType = "";//操作类型 0-订货单位新增 1-订货单位编辑  2-使用单位新增  3-使用单位编辑
    this.loginerNews = {//初始化页面会有客户联系编号	客户单位名称
        "customerId":"",//客户联系编号
        "customerName":""//客户单位名称
    };
    this.sid = "";//删除，编辑，添加操作需要sid是否为空判断(订货单位)
    this.lastUserId = "";//删除，编辑，添加操作需要sid是否为空判断（使用单位）
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //tab切换ul
    this.tabSelect = $("#tabSelect");
    //订货单位盒子
    this.orderUnit = $("#orderUnit");
    //订货单位盒子新增按钮
    this.createList1 = $("#orderUnit #createList");
    //订货单位table
    this.tableList1 = $("#tableList1");
    //使用单位盒子
    this.userUnit = $("#userUnit");
    //使用单位盒子新增按钮
    this.createList2 = $("#userUnit #createList");
    //使用单位table
    this.tableList1 = $("#tableList2");
    //弹框的title
    this.textChangeBoxTitle = $("#textChangeBoxTitle");
    //新增订货单位弹框内容
    this.orderUnitPopup = $("#orderUnitPopup");
    //新增使用单位弹框内容
    this.orderUnitPopup = $("#userUnitPopup");
    //弹框确定按钮
    this.newsChageSure = $("#newsChageSure");
    //弹框取消按钮
    this.newsChageCancel = $("#newsChageCancel");
    //代理公司盒子新增按钮
    this.createList3 = $("#agencyUnit #createList");
    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    getAjaxResult(document.body.jsLee.requestUrl.path7,"POST",{},"loginerNewsCallBack(data)");
    this.operate();
}

function clsMethodLee$operate(){
    //tab切换
    this.tabSelect.find("li").on("click",function () {
        if($(this).attr("nidx") == 0){//订货单位
            initplugPath($("#tableList1")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
        }else if($(this).attr("nidx") == 1){//使用单位
            $("#tableList2").attr("comType","standardTableCtrl");
            var paramJson = JSON.parse($("#tableList2").attr("reqParam"));
            paramJson.customerId = document.body.jsLee.loginerNews.customerId;
            paramJson.customerName = document.body.jsLee.loginerNews.customerName;
            $("#tableList2").attr({"reqParam":JSON.stringify(paramJson),"reqPath":document.body.jsLee.requestUrl.path2});
            document.body.jsCtrl.ctrl = $("#tableList2")[0];
            document.body.jsCtrl.init();
            //initplugPath($("#tableList2")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,null,"POST");
        }else{//代理公司
            initplugPath($("#tableList3")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path8,null,"POST");
        }
    });

    this.newsChageCancel.on("click",function(){//弹框取消操作
        closePopupWin();
    });

    this.newsChageSure.on("click",function(){//弹框确认操作
        if(document.body.jsLee.operateType == 0 || document.body.jsLee.operateType == 1){
            var domCon = $("#orderUnitPopup")[0];
        }else if(document.body.jsLee.operateType == 2 || document.body.jsLee.operateType == 3){
            var domCon = $("#userUnitPopup")[0];
        }else if(document.body.jsLee.operateType == 4 || document.body.jsLee.operateType == 5){
            var domCon = $("#agencyUnitPopup")[0];
        }
        initValidate(domCon);
        var valiClass=new clsValidateCtrl();
        if(valiClass.validateAll4Ctrl(domCon)){
            ajaxExecute(document.body.jsLee.operateType);
        }
    });

    this.createList1.on("click",function(){//订货单位列表新增
        openWinShow(0,"");
    });

    this.createList2.on("click",function(){//使用单位列表新增
        openWinShow(2,"");
    });

    //代理公司盒子新增按钮
    this.createList3.on("click",function(){
        openWinShow(4,"");
    })
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

function loginerNewsCallBack(data){//获取登陆人信息回调
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        document.body.jsLee.loginerNews.customerId = data.rspBody.customerId;
        document.body.jsLee.loginerNews.customerName = data.rspBody.customerName;
        $("#orderUnit #condcustomerName").val(document.body.jsLee.loginerNews.customerName);
        $("#agencyUnit #condagentName").val(document.body.jsLee.loginerNews.customerName);
        initplugPath($("#tableList1")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,document.body.jsLee.loginerNews,"POST");
        initplugPath($("#tableList3")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path8,document.body.jsLee.loginerNews,"POST");
        if($("#tabSelect li").eq(0).attr("limitCode") == "M30102"){//说明是订货单位
            initplugPath($("#tableList2")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,document.body.jsLee.loginerNews,"POST");
            $("#orderUnit").remove();
            $("#userUnit").show();
        }
    }
}

//打开弹框操作
function openWinShow(type,sidStr){//type操作类型 0-订货单位新增 1-订货单位编辑  2-使用单位新增  3-使用单位编辑   ||    sid当前编辑行的sid
    //清空内容
    $("#textChangeBox input[type=text]").val("");
    $("#textChangeBox input[type=checkbox]").removeAttr("checked");
    //显示ul判断
    if(type == 0 || type == 1){//订货单位
        $("#userUnitPopup").hide();
        $("#agencyUnitPopup").hide();
        $("#orderUnitPopup").show();
        $("#orderUnitPopup #customerName").val(document.body.jsLee.loginerNews.customerName);
    }else if(type == 2 || type == 3){//使用单位
        $("#orderUnitPopup").hide();
        $("#agencyUnitPopup").hide();
        $("#userUnitPopup").show();
    }else if(type == 4 || type == 5){//代理公司
        $("#orderUnitPopup").hide();
        $("#userUnitPopup").hide();
        $("#agencyUnitPopup").show();
        $("#agencyUnitPopup #agentName").val(document.body.jsLee.loginerNews.customerName);

    };
    //修改title信息
    switch(type){
        case 0 :
            $("#textChangeBoxTitle").html("新增质量异议提报人员信息");
            break;
        case 1 :
            $("#textChangeBoxTitle").html("编辑质量异议提报人员信息");
            break;
        case 2 :
            $("#textChangeBoxTitle").html("新增使用单位信息");
            break;
        case 3 :
            $("#textChangeBoxTitle").html("编辑使用单位信息");
            break;
        case 4 :
            $("#textChangeBoxTitle").html("新增代理公司信息");
            break;
        case 5 :
            $("#textChangeBoxTitle").html("编辑代理公司信息");
            break;
    }
    openWin(950,350,'textChangeBox',true);
    document.body.jsLee.operateType = type;
    document.body.jsLee.sid = sidStr;
}

//插件渲染操作
function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    if(jsonItem.defaultFlag == "Y"){
        $(cloneRow).find("#isDefault").html("是");
    }else if(jsonItem.defaultFlag == "N"){
        $(cloneRow).find("#isDefault").html("否");
    }
    if(this.ctrl.id == "tableList1"){//订货单位
        $(cloneRow).find("#editOpe").on("click",function(){//编辑操作
            openWinShow(1,jsonItem.sid);
            document.body.jsLee.sid = jsonItem.sid;
            setValue4Desc(jsonItem,$("#orderUnitPopup")[0])//赋值
            if(jsonItem.defaultFlag == "Y"){//默认联系人
                $("#orderUnitPopup #defaultFlag").attr("checked",true);
            }
        });
        $(cloneRow).find("#deleteOpe").on("click",function(){//删除操作
            document.body.jsLee.sid = jsonItem.sid;
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("确定删除？","警告提示",1,"","ope1");
        });
    }else if(this.ctrl.id == "tableList2"){//使用单位
        $(cloneRow).find("#editOpe").on("click",function(){//编辑操作
            openWinShow(3,jsonItem.lastUserId);
            document.body.jsLee.lastUserId = jsonItem.sid;
            setValue4Desc(jsonItem,$("#userUnitPopup")[0])//赋值
            if(jsonItem.defaultFlag == "Y"){//默认联系人
                $("#userUnitPopup #defaultFlag").attr("checked",true);
            }
        });
        $(cloneRow).find("#deleteOpe").on("click",function(){//删除操作
            document.body.jsLee.lastUserId = jsonItem.sid;
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("确定删除？","警告提示",1,"","ope2");
        });
    }else if(this.ctrl.id == "tableList3"){//代理公司
        $(cloneRow).find("#editOpe").on("click",function(){//编辑操作
            openWinShow(5,jsonItem.sid);
            document.body.jsLee.sid = jsonItem.sid;
            setValue4Desc(jsonItem,$("#agencyUnitPopup")[0])//赋值
            if(jsonItem.defaultFlag == "Y"){//默认联系人
                $("#agencyUnitPopup #defaultFlag").attr("checked",true);
            }
        });
        $(cloneRow).find("#deleteOpe").on("click",function(){//删除操作
            document.body.jsLee.sid = jsonItem.sid;
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("确定删除？","警告提示",1,"","ope3");
        });
    }
}

//新增或者编辑回调函数
function updateSubmitCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        if(document.body.jsLee.operateType == 0 || document.body.jsLee.operateType == 1) {//订货单位
            initplugPath($("#tableList1")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
            document.body.jsLee.sid = "";
        }else if(document.body.jsLee.operateType == 2 || document.body.jsLee.operateType == 3){
            initplugPath($("#tableList2")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,null,"POST");
            document.body.jsLee.lastUserId = "";
        }else if(document.body.jsLee.operateType == 4 || document.body.jsLee.operateType == 5){
            initplugPath($("#tableList3")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path8,null,"POST");
            document.body.jsLee.sid = "";
        }
    }
}

//弹框确认添加或者编辑
function ajaxExecute(type){//type区分订货单位和使用单位
    if(type == 0 || type == 1){//订货单位
        var jsonParam = {"custAddr":"","custEmpNo":"","custTel":"","customerId":"","customerName":""};
        getValue4Desc(jsonParam,$("#orderUnitPopup")[0]);
        if($("#orderUnitPopup #defaultFlag").is(":checked")){
            jsonParam.defaultFlag = "Y";
        }else{
            jsonParam.defaultFlag = "N";
        }
        jsonParam.sid = document.body.jsLee.sid;
        getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",jsonParam,"updateSubmitCallBack(data)");
    }else if(type == 2 || type == 3){//使用单位
        var jsonParam = {"createEmpNo":"","lastUser":"","lastUserAddr":"","lastUserTel":"",};
        getValue4Desc(jsonParam,$("#userUnitPopup")[0]);
        if($("#userUnitPopup #defaultFlag").is(":checked")){
            jsonParam.defaultFlag = "Y";
        }else{
            jsonParam.defaultFlag = "N";
        }
        jsonParam.sid = document.body.jsLee.lastUserId;
        getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",jsonParam,"updateSubmitCallBack(data)");
    }else if(type == 4 || type == 5){
        var jsonParam = {"agentName":"","agentAddr":"","agentEmpNo":"","agentTel":"",};
        getValue4Desc(jsonParam,$("#agencyUnitPopup")[0]);
        if($("#agencyUnitPopup #defaultFlag").is(":checked")){
            jsonParam.defaultFlag = "Y";
        }else{
            jsonParam.defaultFlag = "N";
        }
        jsonParam.sid = document.body.jsLee.sid;
        getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",jsonParam,"updateSubmitCallBack(data)");
    }
}

//订货单位删除回调
function delete1CallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        initplugPath($("#tableList1")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
    }
}

//使用单位删除回调
function delete2CallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        initplugPath($("#tableList2")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,null,"POST");
        document.body.jsLee.sid = "";
        document.body.jsLee.lastUserId = "";
    }
}

//使用单位删除回调
function delete3CallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        initplugPath($("#tableList3")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path8,null,"POST");
        document.body.jsLee.sid = "";
        document.body.jsLee.lastUserId = "";
    }
}

//弹框点击确定返回函数
function clsAlertBoxCtrl$sure() {
    if(this.id == "ope1"){//删除操作
        closePopupWin();
        getAjaxResult(document.body.jsLee.requestUrl.path5,"POST",{"sid":document.body.jsLee.sid},"delete1CallBack(data)");
    }else if(this.id == "ope2"){
        closePopupWin();
        getAjaxResult(document.body.jsLee.requestUrl.path6,"POST",{"sid":document.body.jsLee.lastUserId},"delete2CallBack(data)");
    }if(this.id == "ope3"){//删除操作
        closePopupWin();
        getAjaxResult(document.body.jsLee.requestUrl.path10,"POST",{"sid":document.body.jsLee.sid},"delete3CallBack(data)");
    }
}

//搜索回调函数
function clsSearchBtnCtrl$after(jsonCond) {
    if(this.ctrl.id == "searchBtn2"){
        jsonCond.customerId = document.body.jsLee.loginerNews.customerId;
    }
    return jsonCond;
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});