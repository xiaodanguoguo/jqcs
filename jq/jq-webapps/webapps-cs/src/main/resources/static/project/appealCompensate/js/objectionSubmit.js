function clsMethodLee(){
    this.requestUrl = {
        "path1":"/objectionTiBao/findByPage",//异议提报list列表
        "path2":"/objectionTiBao/submit",//删除接口||提报接口
        "path3":"/objectionTiBao/export",//导出接口
        "path4":"/claimComments/evaluate",//异议处理评价接口
        "path5":"/objectionTiBao/down",//异议处理评价接口
        "path6":"/objectionTiBao/lookPhoto",//协议书查看接口
        "path7":"/md/findItemsByTypeId",//产品大类下拉接口
        "path8":"/objectionChuLi/preview"//查看pdf接口
    };
    this.documentLee = null;
    this.claimNo = "";//异议处理评价的异议编号
    this.previewArr = [];//缓存预览数组
    this.previewArrCurrent = "";//当前预览图片
    this.claimNoArr = [];//删除，提交入参
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //新建
    this.newOpe = $("#newOpe");
    //导出
    this.exportOpe = $("#exportOpe");
    //删除
    this.deleteOpe = $("#deleteOpe");
    //提报
    this.submitOpe = $("#submitOpe");
    //确定选择星星完成评价
    this.starSure = $("#starSure");

    this.parse();

}
function clsMethodLee$parse(){
    $("#tableList")[0].cacheArr = [];
    initplugPath($("#condsid")[0],"singleSelectCtrl",this.requestUrl.path7,{"typeId": "MILL_BIG_TYPE"},"POST");
    initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    // 初始化搜索框下拉
    $("#condclaimState").chosen({
        no_results_text: "暂无结果",
        width: "180PX",
        enable_split_word_search: false,
        placeholder_text_single: ""
    });
    $("#condclaimType").chosen({
        no_results_text: "暂无结果",
        width: "180PX",
        enable_split_word_search: false,
        placeholder_text_single: ""
    });
    this.operate();
}

function clsMethodLee$operate(){
    this.newOpe.on("click",function(){//新建操作
        jumpUrl("objectionSubDetail.html?htmlType=0","0000000",0);
    });

    this.exportOpe.on("click",function(){//导出操作
        document.body.jsLee.claimNoArr = [];
        if(isChecked()){
            var claimNoArr = [];
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                claimNoArr.push($("#tableList")[0].cacheArr[nI].claimNo);
            }
            var importParam = "name=" + JSON.stringify(claimNoArr);
            $.download(requestUrl + document.body.jsLee.requestUrl.path3, importParam, "POST");
        }
    });

    this.deleteOpe.on("click",function(){//删除操作
        if(isChecked()){
            document.body.jsLee.claimNoArr = [];
            var claimNoArr = document.body.jsLee.claimNoArr;
            var markLock = true;
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                if($("#tableList")[0].cacheArr[nI].claimState != "NEW"){
                    markLock = false;
                }
                var claimNoJson = {"claimNo":$("#tableList")[0].cacheArr[nI].claimNo,"optionType":2}
                claimNoArr.push(claimNoJson);
            }
            if(markLock){
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("确定是否删除？","警告提示",1,"","ope1");
            }else {
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("只能删除新建状态的异议，请取消勾选其他状态的异议","删除失败提示");
            }
        }
    });
    this.submitOpe.on("click",function(){//提报操作
        if(isChecked()){
            document.body.jsLee.claimNoArr = [];
            var claimNoArr = document.body.jsLee.claimNoArr;
            var markLock = true;
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                if($("#tableList")[0].cacheArr[nI].claimState != "NEW"){
                    markLock = false;
                }
                var claimNoJson = {"claimNo":$("#tableList")[0].cacheArr[nI].claimNo,"optionType":1}
                claimNoArr.push(claimNoJson);
            }
            if(markLock){
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("确定是否提报？","警告提示",1,"","ope2");
            }else {
                var alertBox=new clsAlertBoxCtrl();
                alertBox    .Alert("只能提报新建状态的异议，请取消勾选其他状态的异议","提报失败提示");
            }
        }
    });

    $(".starClick div").on("click",function () {//点击星星
        var indexMark = $(this).index();
        $(this).parents(".starClick").find("div").removeClass("content-images-imgActive");
        $(this).parents(".starClick").find("div").each(function(index,val){
            if(index <= indexMark){
                $(val).addClass("content-images-imgActive");
            }
        });
    });

    this.starSure.on("click",function () {//选择完星星，确定提交
        var jsonParam = {"claimNo":document.body.jsLee.claimNo};
        $(".starClick").each(function(index,val){
            switch (index){
                case 0:
                    jsonParam.handlerUser = $(this).find(".content-images-imgActive").length;
                    break;
                case 1:
                    jsonParam.handlerTime = $(this).find(".content-images-imgActive").length;
                    break;
                case 2:
                    jsonParam.handlerResults = $(this).find(".content-images-imgActive").length;
                    break;
            }
        });
        getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",jsonParam,"starSureCallBack(data)");
    });

    //预览弹框点击上一页下一页
    $("#previewPrev").on("click",function () {//上一页
        previewPage(0);
    });
    $("#previewNext").on("click",function () {//下一页
        previewPage(1);
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

//插件渲染前操作
function clsStandardTableCtrl$before() {
    if(this.ctrl.id == "tableList"){
        $("#checkAll").removeAttr("checked");
    }
}


//插件渲染操作
function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    if(this.ctrl.id == "tableList"){
        checkboxIsTrue(jsonItem,cloneRow)//勾选判断
        //判断异议状态  0新建 1已提报  2已受理  3已驳回  4调查中  5处理中  6已结案  7已评价
        switch (jsonItem.claimState){
            case "NEW":
                $(cloneRow).find("#claimStateA").html("新建");
                break;
            case "PRESENT":
                $(cloneRow).find("#claimStateA").html("已提报");
                $(cloneRow).find("#auditOpe").show();
                break;
            case "ADOPT":
                $(cloneRow).find("#claimStateA").html("销售审核通过");
                break;
            case "ACCEPTANCE":
                $(cloneRow).find("#claimStateA").html("已受理");
                break;
            case "REJECT":
                $(cloneRow).find("#claimStateA").html("已驳回");
                break;
            case "INVESTIGATION":
                $(cloneRow).find("#claimStateA").html("调查中");
                break;
            case "HANDLE":
                $(cloneRow).find("#claimStateA").html("处理中");
                break;
            case "END":
                $(cloneRow).find("#claimStateA").html("已结案");
                $(cloneRow).find("#evaluateOpe").show();
                $(cloneRow).find("#downloadOpe").show();
                break;
            case "EVALUATE":
                $(cloneRow).find("#claimStateA").html("已评价");
                break;
        }

/*
        //判断异议类别  0表面外观 1理化功能  2加工使用  3尺寸公差  4实物不符  5计量  6其他
        switch (jsonItem.claimType) {
            case "0":
                $(cloneRow).find("#claimTypeA").html("表面外观");
                break;
            case "1":
                $(cloneRow).find("#claimTypeA").html("理化功能");
                break;
            case "2":
                $(cloneRow).find("#claimTypeA").html("加工使用");
                break;
            case "3":
                $(cloneRow).find("#claimTypeA").html("尺寸公差");
                break;
            case "4":
                $(cloneRow).find("#claimTypeA").html("实物不符");
                break;
            case "5":
                $(cloneRow).find("#claimTypeA").html("计量");
                break;
            case "6":
                $(cloneRow).find("#claimTypeA").html("其他");
                break;
            case "7":
                $(cloneRow).find("#claimTypeA").html("已评价");
                break;
        }*/
        //修改
        $(cloneRow).find("#editOpe").on("click",function(){
            jumpUrl("objectionSubDetail.html?htmlType=1&claimNo="+ jsonItem.claimNo,"0000000",0);
        });
        //详情
        $(cloneRow).find("#detailOpe").on("click",function(){
            jumpUrl("objectionSubDetail.html?htmlType=2&claimNo="+ jsonItem.claimNo,"0000000",0);
        });
        //销售审核
        $(cloneRow).find("#auditOpe").on("click",function(){
            jumpUrl("objectionSubDetail.html?htmlType=3&claimNo="+ jsonItem.claimNo,"0000000",0);
        });
        //异议处理评价
        $(cloneRow).find("#evaluateOpe").on("click",function(){
            openWin('410', '200', 'inputAnswer', true);
            document.body.jsLee.claimNo = jsonItem.claimNo;
        });
        //查看附件
        $(cloneRow).find("#viewOpe").on("click",function(){
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POST",{"claimNo":jsonItem.claimNo},"viewOpeCallBack(data)");
        });
        //下载协议书
        $(cloneRow).find("#downloadOpe").on("click",function(){
           getAjaxResult(document.body.jsLee.requestUrl.path8,"POST",{"templateType":1,"claimNo":jsonItem.claimNo},"pdfViewCallBack(data)");
        });
    }
}

function clsStandardTableCtrl$after() {
    isAllCheck();
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

//异议处理评价成功回调

function starSureCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("感谢您的评价，我们将继续努力！","成功提示");
    }
}

//查看附件成功回调
function viewOpeCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        openWin("800","600","previewOpeBox");
        if(data.rspBody.url == "" || data.rspBody.url == null){
            document.body.jsLee.previewArr = "";
            document.body.jsLee.previewArrCurrent = "";
        }else{
            document.body.jsLee.previewArr = data.rspBody.url.split(";");
            document.body.jsLee.previewArrCurrent = document.body.jsLee.previewArr[0];
        }

        $("#previewOpeBoxPdf").attr("href",document.body.jsLee.previewArrCurrent);
        $("#previewOpeBoxPdf").media({width:740, height:450});
        $("#previewPrev").attr("disabled",true).addClass("changeGary");
        if(document.body.jsLee.previewArr.length == 1){
            $("#previewNext").attr("disabled",true).addClass("changeGary");
        }
    }
}

//预览中上一页下一页操作
function previewPage(type){//type——0上一页  1下一页
    for(var nI = 0; nI < document.body.jsLee.previewArr.length; nI++){
        if(document.body.jsLee.previewArr[nI] == document.body.jsLee.previewArrCurrent){
            if(type == 0){//上一页操作
                document.body.jsLee.previewArrCurrent = document.body.jsLee.previewArr[nI - 1];
                if(nI - 1 == 0){//当前点击后是第一页
                    $("#previewPrev").attr("disabled",true).addClass("changeGary");
                    $("#previewNext").removeAttr("disabled").removeClass("changeGary");
                }
            }else{//下一页操作
                document.body.jsLee.previewArrCurrent = document.body.jsLee.previewArr[nI + 1];
                if(nI + 2 == document.body.jsLee.previewArr.length){//当前点击后是最后一页
                    $("#previewNext").attr("disabled",true).addClass("changeGary");
                    $("#previewPrev").removeAttr("disabled").removeClass("changeGary");
                }
            }
            $("#previewOpeBoxPdf").attr("href",document.body.jsLee.previewArrCurrent);
            $("#previewOpeBoxPdf").media({width:740, height:450});
            break;
        }
    }
}

//刷新列表数据勾选初始化
function checkboxIsTrue(jsonItem,cloneRow){
    var arrCache = $("#tableList")[0].cacheArr;
    for(var nI = 0; nI < arrCache.length; nI++ ){
        if(jsonItem.claimNo == arrCache[nI].claimNo){
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

//弹框点击确定返回函数
function clsAlertBoxCtrl$sure() {
    if(this.id == "ope1"){//删除操作
        closePopupWin();
        getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",document.body.jsLee.claimNoArr,"deleteOpeCallBack(data)");
    }else if(this.id == "ope2"){
        closePopupWin();
        getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",document.body.jsLee.claimNoArr,"deleteOpeCallBack(data)");

    }
}

function pdfViewCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        jumpUrl("../../appealCompensate/html-gulp-www/pdfView.html?pdfUrl=" + data.rspBody.report,"0000000","1");
    }
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});
