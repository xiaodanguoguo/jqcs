function clsMethodLee(){
    this.requestUrl = {
        "path1":"/objectionChuLi/findByPage",//异议调查list列表
        "path2":"/objectionChuLi/export",//异议调查导出接口
        "path3":"/objectionChuLi/compulsorySettlement",//强制结案接口
        "path4":"/objectionJieAn/revoke",//撤销接口
        "path5":"/objectionJieAn/look",//查看协议书接口
        "path7":"/md/findItemsByTypeId",//产品大类下拉接口
        "path8":"/objectionJieAn/upload",//上传之后把上传图片传给后台
        "path9":"/objectionChuLi/preview",//查看pdf接口
        "path10":"/objectionJieAn/expiren"//过期原因确认接口
    };
    this.documentLee = null;
    this.claimNo = "";//强制结案的当前缓存标识
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //导出
    this.exportOpe = $("#exportOpe");
    //强制结案确认按钮
    this.rejectSureOpe = $("#rejectSureOpe");
    //强制结案取消按钮
    this.rejectCancelOpe = $("#rejectCancelOpe");
    //撤销确认按钮
    this.revokeSureOpe = $("#revokeSureOpe");
    //撤销取消按钮
    this.revokeCancelOpe = $("#revokeCancelOpe");
    //过期原因确认按钮
    this.pastDateOpe = $("#pastDateOpe");

    //过期原因取消按钮
    this.pastDateCancelOpe = $("#pastDateCancelOpe");

    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    $("#tableList")[0].cacheArr = [];
    initplugPath($("#condsid")[0],"singleSelectCtrl",this.requestUrl.path7,{"typeId": "MILL_BIG_TYPE"},"POST");
    initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    // 初始化搜索框下拉
    $("#condclaimState1").chosen({
        no_results_text: "暂无结果",
        width: "192PX",
        enable_split_word_search: false,
        placeholder_text_single: ""
    });
    $("#condclaimType").chosen({
        no_results_text: "暂无结果",
        width: "192PX",
        enable_split_word_search: false,
        placeholder_text_single: ""
    });
    this.operate();
}

function clsMethodLee$operate(){
    this.exportOpe.on("click",function(){//导出操作
        if(isChecked()){
            var claimNoArr = [];
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                claimNoArr.push($("#tableList")[0].cacheArr[nI].claimNo);
            }
            var importParam = "name=" + JSON.stringify(claimNoArr);
            $.download(requestUrl + document.body.jsLee.requestUrl.path2, importParam, "POST");
        }
    });
    this.rejectSureOpe.on("click",function(){//强制结案确认操作
        if($("#rejectText").val().length >= 5 && $("#rejectText").val().length <= 30){
            var jsonParam = {"claimNo":document.body.jsLee.claimNo,"reasonsForCompulsoryClosure":$("#rejectText").val()};
            getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",jsonParam,"rejectOpeCallBack(data)");
        }else if($("#rejectText").val().length > 0){
            showErrInfoByCustomDiv($("#rejectText")[0],"强制结案理由为5-30个字！");
        }else{
            showErrInfoByCustomDiv($("#rejectText")[0],"请输入强制结案理由！");
        }
    });

    this.rejectCancelOpe.on("click",function(){//强制结案取消操作
        closePopupWin();
    });

    this.revokeSureOpe.on("click",function(){//撤销确认操作
        if($("#revokeText").val().length >= 5 && $("#revokeText").val().length <= 30){
            var jsonParam = {"claimNo":document.body.jsLee.claimNo,"reasonsForCompulsoryClosure":$("#revokeText").val()};
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",jsonParam,"revokeOpeCallBack(data)");
        }else if($("#revokeText").val().length > 0){
            showErrInfoByCustomDiv($("#revokeText")[0],"撤销理由为5-30个字！");
        }else{
            showErrInfoByCustomDiv($("#revokeText")[0],"请输入撤销理由！");
        }
    });

    this.revokeCancelOpe.on("click",function(){//撤销取消操作
        closePopupWin();
    });

    //下载协议书
    $("#downView").on("click",function(){
        var importParam = "name=" + JSON.stringify({"claimNos":[document.body.jsLee.claimNo],"templateType":1});
        $.download(requestUrl + document.body.jsLee.requestUrl.path9, importParam, "POST");
    });
    //下载协议书关闭
    $("#downViewCancel").on("click",function(){
        closePopupWin();
    });

    //过期原因确认操作
    this.pastDateOpe.on("click",function(){
        if($("#pastDateText").val().length >= 5){
            var jsonParam = {"claimNo":document.body.jsLee.claimNo,"expiredReason":$("#pastDateText").val()};
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POST",jsonParam,"pastDateOpeCallBack(data)");
        }else if($("#pastDateText").val().length > 0){
            showErrInfoByCustomDiv($("#pastDateText")[0],"过期原因至少5个字！");
        }else{
            showErrInfoByCustomDiv($("#pastDateText")[0],"请输入过期原因！");
        }
    });

    //过期原因取消操作
    this.pastDateCancelOpe.on("click",function(){
        closePopupWin();
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
    if(this.ctrl.id == "tableList") {
        checkboxIsTrue(jsonItem,cloneRow)//勾选判断
        //判断异议状态  0新建 1已提报  2已受理  3已驳回  4调查中  5处理中  6已结案  7已评价
        switch (jsonItem.claimState){
            case "NEW":
                $(cloneRow).find("#claimStateA").html("新建");
                $(cloneRow).find("#uploadOpe").remove();
                break;
            case "PRESENT":
                $(cloneRow).find("#claimStateA").html("已提报");
                $(cloneRow).find("#uploadOpe").remove();
                break;
            case "ADOPT":
                $(cloneRow).find("#claimStateA").html("销售审核通过");
                $(cloneRow).find("#uploadOpe").remove();
                break;
            case "ACCEPTANCE":
                $(cloneRow).find("#claimStateA").html("已受理");
                $(cloneRow).find("#uploadOpe").remove();
                break;
            case "REJECT":
                $(cloneRow).find("#claimStateA").html("已驳回");
                $(cloneRow).find("#uploadOpe").remove();
                break;
            case "INVESTIGATION":
                $(cloneRow).find("#claimStateA").html("调查中");
                $(cloneRow).find("#uploadOpe").remove();
                break;
            case "HANDLE":
                $(cloneRow).find("#claimStateA").html("处理中");
                //$(cloneRow).find("#uploadOpe").show();
                if(jsonItem.agreementState != "EXAMINE"){
                    $(cloneRow).find("#uploadOpe").remove();
                }
                break;
            case "END":
                $(cloneRow).find("#claimStateA").html("已结案");
                $(cloneRow).find("#strongEndOpe").hide();
                $(cloneRow).find("#printOpe").show();
                //$(cloneRow).find("#uploadOpe").show();
                $(cloneRow).find("#viewUploadOpe").show();
                if(jsonItem.agreementState != "EXAMINE"){
                    $(cloneRow).find("#uploadOpe").remove();
                }

                break;
            case "EVALUATE":
                $(cloneRow).find("#claimStateA").html("已评价");
                $(cloneRow).find("#strongEndOpe").hide();
                $(cloneRow).find("#viewUploadOpe").show();
                $(cloneRow).find("#uploadOpe").remove();
                break;
        }

        //判断异议类别  0表面外观 1理化功能  2加工使用  3尺寸公差  4实物不符  5计量  6其他
        switch (jsonItem.claimType) {
            case "1":
                $(cloneRow).find("#claimTypeA").html("表面外观");
                break;
            case "2":
                $(cloneRow).find("#claimTypeA").html("理化性能");
                break;
            case "3":
                $(cloneRow).find("#claimTypeA").html("加工使用");
                break;
            case "4":
                $(cloneRow).find("#claimTypeA").html("尺寸公差");
                break;
            case "5":
                $(cloneRow).find("#claimTypeA").html("实物不符");
                break;
            case "6":
                $(cloneRow).find("#claimTypeA").html("计量");
                break;
            case "7":
                $(cloneRow).find("#claimTypeA").html("其他");
                break;
            case "7":
                $(cloneRow).find("#claimTypeA").html("已评价");
                break;
        }
        //协议书状态判断 0编辑中 1已审核 2已完成
        switch (jsonItem.agreementState) {
            case "EDIT":
                $(cloneRow).find("#agreementStateA").html("编辑中");
                break;
            case "EXAMINE":
                $(cloneRow).find("#agreementStateA").html("已审核");
                $(cloneRow).find("#cancelOpe").show();
                break;
            case "COMPLETE":
                $(cloneRow).find("#agreementStateA").html("已完成");
                $(cloneRow).find("#cancelOpe").show();
                break;
            default:
                $(cloneRow).find("#agreementStateA").html("");
                break;
        }

        //上传协议书操作
        /*$(cloneRow).find("#uploadOpe").on("click",function(){
            document.body.jsLee.claimNo = jsonItem.claimNo;
        });*/
        //查看协议书操作
        $(cloneRow).find("#viewUploadOpe").on("click",function(){
            document.body.jsLee.claimNo = jsonItem.claimNo;
            getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",{"templateType":1,"claimNo":jsonItem.claimNo},"pdfViewCallBack2(data)");
        });
        //撤销操作
        $(cloneRow).find("#cancelOpe").on("click",function(){
            $("#revokeText").val("");
            openWin('360', '245', 'inputAnswer2', true);
            document.body.jsLee.claimNo = jsonItem.claimNo;
        });
        //强制结案操作
        $(cloneRow).find("#strongEndOpe").on("click",function(){
            $("#rejectText").val("");
            openWin('360', '245', 'inputAnswer', true);
            document.body.jsLee.claimNo = jsonItem.claimNo;
        });
        //打印通知单操作
        $(cloneRow).find("#printOpe").on("click",function () {
            document.body.jsLee.claimNo = jsonItem.claimNo;
            getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",{"templateType":7,"claimNo":jsonItem.claimNo},"pdfViewCallBack(data)");

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

//强制结案成功回调
function rejectOpeCallBack(data) {
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    }
}

//撤销成功回调
function revokeOpeCallBack(data) {
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    }
}

//过期原因确认回调
function pastDateOpeCallBack(data) {
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("请再次上传图片文件","成功提示");
    }
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

//查看协议书成功回调
function viewUploadOpeCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        openWin("800","600","previewOpeBox");
        $("#previewOpeBoxPdf").attr("href",data.rspBody.claimNoUrl);
        $("#previewOpeBoxPdf").media({width:740, height:450});
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

function clsUploadCtrl$successAfter(ctrl, response)
{
    getAjaxResult(document.body.jsLee.requestUrl.path8,"POST",{"claimNo":$(ctrl).parents("tr")[0].jsonData.claimNo,"filePath":response.rspBody.filePath,"originalName":response.rspBody.originalName,"viewUrl":response.rspBody.viewUrl},"uploadCallBack(data)")
}

function uploadCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("上传成功","成功提示");
    }
}

function pdfViewCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        jumpUrl("../../appealCompensate/html-gulp-www/pdfView.html?pdfUrl=" + data.rspBody.report,"0000000","1");
    }
}

function pdfViewCallBack2(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        openWin("500","600","previewOpeBox2");
        $("#previewOpeBox2 img").attr("src",data.rspBody.report);
        //$("#previewOpeBoxPdf2").media({width:740, height:450});
        //jumpUrl("../../appealCompensate/html-gulp-www/pdfView.html?pdfUrl=" + data.rspBody.report,"0000000","1");
    }
}

function clsUploadCtrl$uploadBefore(thisAjaxupload, file, ext, parent) {
    //console.log($(parent.jsCtrl.ctrl).parents("tr")[0].jsonData)
    var jsonA = $(parent.jsCtrl.ctrl).parents("tr")[0].jsonData;
    document.body.jsLee.claimNo = jsonA.claimNo;
    if(jsonA.inquireState == "TRACK" && dayDistance(jsonA.closingTime,jsonA.admissibilityTime,20) && jsonA.isUpload == "N"){//已跟踪   过期时间是20天
        openWin('360', '245', 'inputAnswer3', true);
        return false;
    }else if(jsonA.inquireState != "TRACK" && dayDistance(jsonA.closingTime,jsonA.admissibilityTime,7) && jsonA.isUpload == "N"){//过期时间是7天
        openWin('360', '245', 'inputAnswer3', true);
        return false;
    }
    return true;
}

//过期时间计算
function dayDistance(a,b,c){//c过期时间段
    var formatTimeA = new Date(a).getTime();//结案时间
    var formatTimeB = new Date(b).getTime();//受理时间
    if((formatTimeA - formatTimeB) > (c *24 * 60 * 60 * 1000)){
        return true;
    }else{
        return false;
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