function clsMethodLee(){
    this.requestUrl = {
        "path1":"/product/info/list",//列表接口
        "path2":"/product/info/delete",//删除接口
        "path3":"/product/info/issue"//发布接口
    };
    this.documentLee = null;
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //新建操作
    this.newOpe = $("#newOpe");
    //删除操作
    this.deleteOpe = $("#deleteOpe");
    //发布操作
    this.releaseOpe = $("#releaseOpe");
    //维护操作
    this.preserveOpe = $("#preserveOpe");
    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    $("#tableList")[0].cacheArr = [];
    initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    this.operate();
}

function clsMethodLee$operate(){
    //新建操作
    this.newOpe.on("click",function(){
        jumpUrl("productUpdate.html?htmlType=1","0000000",0);
    });
    //删除操作
    this.deleteOpe.on("click",function(){
        if(isChecked()){
            var pidArr = [];
            var markLock = true;
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                if($("#tableList")[0].cacheArr[nI].status != 1){
                    markLock = false;
                }
                var pidJson = $("#tableList")[0].cacheArr[nI].pid;
                pidArr.push(pidJson);
            }
            if(markLock){
                getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",{"pids":pidArr},"deleteOpeCallBack(data)");
            }else {
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("只能删除新建状态的产品，请取消勾选其他状态的产品","删除失败提示");
            }
        }
    });
    //发布操作
    this.releaseOpe.on("click",function(){
        if(isChecked()){
            var pidArr = [];
            var markLock = true;
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                if($("#tableList")[0].cacheArr[nI].status != 1){
                    markLock = false;
                }
                var pidJson = $("#tableList")[0].cacheArr[nI].pid;
                pidArr.push(pidJson);
            }
            if(markLock){
                getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",{"pids":pidArr},"deleteOpeCallBack(data)");
            }else {
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("只能发布新建状态的产品，请取消勾选其他状态的产品","发布失败提示");
            }
        }
    });
    //维护操作
    this.preserveOpe.on("click",function(){
        jumpUrl("proTypePreserve.html","0000000",0);
    });

}
function clsMethodLee$refresh(){

}

//插件渲染前操作
function clsStandardTableCtrl$before() {
    if(this.ctrl.id == "tableList"){
        $("#checkAll").removeAttr("checked");
    }
}

function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    if(this.ctrl.id == "tableList"){
        checkboxIsTrue(jsonItem,cloneRow)//勾选判断
        if(jsonItem.status == 1){//1-新建 2-发布
            $(cloneRow).find("#statusA").html("新建");
        }else{
            $(cloneRow).find("#statusA").html("已发布");
        }
        $(cloneRow).find("#editOpe").on("click",function () {//编辑操作
            jumpUrl("productUpdate.html?htmlType=2&pid="+ jsonItem.pid,"0000000",0);
        });
    }
}

function clsStandardTableCtrl$after() {
    isAllCheck();
}

//刷新列表数据勾选初始化
function checkboxIsTrue(jsonItem,cloneRow){
    var arrCache = $("#tableList")[0].cacheArr;
    for(var nI = 0; nI < arrCache.length; nI++ ){
        if(jsonItem.pid == arrCache[nI].pid){
            $(cloneRow).find("#chkCoding").attr("checked",true);
        }
    }
}

//判断插件是否全选
function isAllCheck(){
//判断是否全选
    var listCheck = $("#tableList [id=cloneRow] #chkCoding");
    var numLength = 0;
    for (var mI = 0; mI < listCheck.length; mI++){
        if(listCheck.eq(mI).is(":checked")){
            numLength++;
        }
    }
    if(numLength == listCheck.length && numLength != 0){
        $("#checkAll").attr("checked",true);
    }
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

//校验表格是否勾选
function isChecked(){
    if($("#tableList")[0].cacheArr.length == 0){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("未勾选任何内容，操作失败","失败提示");
        return false;
    }
    return true;
}

//删除成功回调
function deleteOpeCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    }
}

function clsSearchBtnCtrl$after(jsonCond) {
    $("#tableList")[0].cacheArr = [];
    return jsonCond;
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});