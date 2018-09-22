function clsMethodLee(){
    this.requestUrl = {
        "path1":"/product/info/detail",//回显数据接口
        "path2":"/product/category/down/list",//产品类别下拉接口
        "path3":"/product/info/add",//新增接口
        "path4":"/product/info/update"//修改接口
    };
    this.documentLee = null;
    this.htmlType = GetQueryString("htmlType");//页面类型 1——新建 2——修改
    this.pid = GetQueryString("pid") == null ? "" : GetQueryString("pid");//产品id
    this.thumbnailList = [];//图片数组
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //新建操作
    this.saveOpe = $("#saveOpe");
    //返回操作
    this.returnOpe = $("#returnOpe");


    this.parse();
}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    initplugPath($("#categoryIdA")[0],"singleSelectCtrl",this.requestUrl.path2,null,"POST");
    if(this.htmlType == 2){
        getAjaxResult(this.requestUrl.path1,"POST",{"pid":this.pid},"htmlInit(data)")
    }
    this.operate();
}

function clsMethodLee$operate(){
    //保存操作
    this.saveOpe.on("click",function(){
        if(checkedIsTrue()){
            var jsonParam = paramJson();
            if(document.body.jsLee.htmlType == 1){
                getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",jsonParam,"saveCallBack(data)");
            }else{
                getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",jsonParam,"saveCallBack(data)");
            }
        }
    });
    //返回操作
    this.returnOpe.on("click",function(){
        jumpUrl("productNewsList.html","0000000",0);
    });

}
function clsMethodLee$refresh(){

}

function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    if(this.ctrl.id == "tableList"){
        if(jsonItem.status == 1){//1-新建 2-发布
            $(cloneRow).find("#statusA").html("新建");
        }else{
            $(cloneRow).find("#statusA").html("已发布");
        }
        $(cloneRow).find("#editOpe").on("click",function () {//编辑操作

        });
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

function htmlInit(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        setValue4Desc(data.rspBody,$("#boxContent")[0])//赋值
        document.body.jsLee.thumbnailList = data.rspBody.thumbnailList;
        $("#categoryIdA").attr("initValue",data.rspBody.categoryId);
        initplugPath($("#categoryIdA")[0],"singleSelectCtrl",this.requestUrl.path2,null,"POST");
    }
}

function setValue4DescProcess(jsonItemChild,key,jsonItem){
    for(var nI = 0; nI < jsonItemChild.length; nI++ ){
        $("*[id=uploadBox]").eq(nI).attr("uploadbgsrc",jsonItemChild[nI]);
        document.body.jsCtrl.ctrl = $("*[id=uploadBox]")[nI];
        document.body.jsCtrl.init();
        $("*[id=uploadBox]").eq(nI).addClass("comUploadAfter");
        $("*[id=uploadBox]")[nI].viewUrl = jsonItemChild[nI];
        //$("*[id=uploadBox]")[nI].jsCtrl.setValue(jsonItemChild[nI]);
    }

}

function checkedIsTrue(){
    initValidate($("#boxContent")[0]);
    var valiClass=new clsValidateCtrl();
    if(!valiClass.validateAll4Ctrl($("#boxContent")[0]) || !$("#categoryIdA option:selected").val()){
        if(!$("#categoryIdA option:selected").val()){
            showErrInfoByCustomDiv($(".chosen-container")[0],"请选择产品类别!");
        }
        return false;
    }
    return true;
}

//上传图片成功
function clsUploadCtrl$successAfter(file, response) {
    document.body.jsLee.thumbnailList = [];
    $("*[id=uploadBox] img").each(function(){
        if($(this).attr("src") != "../images/comUploadBg1.png"){
            document.body.jsLee.thumbnailList.push($(this).attr("src"));
        }
    });
    file.viewUrl = response.rspBody.viewUrl;
    // return true;
}

//删除上传图片
function clsUploadCtrl$deleteImg(obj) {
    var a = obj.parents("#uploadBox");
    obj.parents("#uploadBox").removeClass("comUploadAfter");
    obj.attr("src","../images/comUploadBg1.png")
    document.body.jsLee.thumbnailList = [];
    $("*[id=uploadBox] img").each(function(){
        if($(this).attr("src") != "../images/comUploadBg1.png"){
            document.body.jsLee.thumbnailList.push($(this).attr("src"));
        }
    });
}

function paramJson(){
    var jsonParam = {"productName":"","designation":"","standard":"","used":"","productDesc":"","productArea":""};
    getValue4Desc(jsonParam,$("#boxContent")[0]);
    jsonParam.categoryId = $("#categoryIdA option:selected").val();
    jsonParam.thumbnailList = document.body.jsLee.thumbnailList;
    jsonParam.pid = document.body.jsLee.pid;
    return jsonParam;
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

function saveCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("保存成功","成功提示",1,"","jumpUrlTip");
    }
}

function clsAlertBoxCtrl$sure() {//保存成功弹框确定，跳转页面
    if (this.id == "jumpUrlTip") {
        jumpUrl("productNewsList.html","0000000",0);
    }
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});
