function clsMethodLee(){
    this.requestUrl = {
        "path1":"/objectionDiaoCha/findByPage",//异议调查list列表
        "path2":"/objectionDiaoCha/reject",//异议调查报告驳回接口
        "path3":"/objectionChuLi/download",//异议调查报告下载pdf接口
        "path4":"/objectionDiaoCha/updateState",//异议调查内部外部开始状态改变接口  受理接口
        "path5":"/objectionDiaoCha/export",//异议调查导出接口
        "path6":"/",//受理接口
        "path7":"/md/findItemsByTypeId",//产品大类下拉接口
        "path8":"/objectionChuLi/preview"//查看pdf接口
    };
    this.documentLee = null;
    this.claimNo = "";//驳回时使用的诉赔编号
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //导出
    this.exportOpe = $("#exportOpe");
    //驳回拒绝按钮
    this.rejectSureOpe = $("#rejectSureOpe");
    //驳回取消按钮
    this.rejectCancelOpe = $("#rejectCancelOpe");
    //确认书审核按钮
    this.bookAuditOpe = $("#bookAuditOpe");
    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    $("#tableList")[0].cacheArr = [];
    initplugPath($("#condsid")[0],"singleSelectCtrl",this.requestUrl.path7,{"typeId": "MILL_BIG_TYPE"},"POST");
    initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
    // 初始化搜索框下拉
    $("#condclaimState").chosen({
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
            $.download(requestUrl + document.body.jsLee.requestUrl.path5, importParam, "POST");
        }
    });

    this.rejectSureOpe.on("click",function(){//驳回确认操作
        if($("#rejectText").val().length >= 5){
            var jsonParam = {"claimNo":document.body.jsLee.claimNo,"reasonsForCompulsoryClosure":$("#rejectText").val()};
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",jsonParam,"rejectOpeCallBack(data)");
        }else if($("#rejectText").val().length > 0){
            showErrInfoByCustomDiv($("#rejectText")[0],"强制结案理由至少5个字！");
        }else{
            showErrInfoByCustomDiv($("#rejectText")[0],"请输入强制结案理由！");
        }
    });

    this.rejectCancelOpe.on("click",function(){//驳回取消操作
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
                $(cloneRow).find("#acceptOpe").show();
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
                break;
            case "EVALUATE":
                $(cloneRow).find("#claimStateA").html("已评价");
                break;
        }

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
        }

        //调查状态判断 0外部调查开始，1已跟踪，2外部调查结束3内部调查开始4内部调查结束
        switch (jsonItem.inquireState){
            case "OUTSTART":
                $(cloneRow).find("#inquireStateA").html("外部调查开始");
                $(cloneRow).find("#researchOutOpe").show();
                break;
            case "TRACK":
                $(cloneRow).find("#inquireStateA").html("已跟踪");
                $(cloneRow).find("#researchOutOpe").show();
                break;
            case "OUTEND":
                $(cloneRow).find("#inquireStateA").html("外部调查结束");
                $(cloneRow).find("#researchInOpe").show();
                $(cloneRow).find("#rejectOpe").show();
                break;
            case "INSTART":
                $(cloneRow).find("#inquireStateA").html("内部调查开始");
                $(cloneRow).find("#researchInOpe").show();
                break;
            case "INEND":
                $(cloneRow).find("#inquireStateA").html("内部调查结束");
                $(cloneRow).find("#rejectOpe").show();
                $(cloneRow).find("#bookAuditOpe").show();
                break;
            case "CONFIRM":
                $(cloneRow).find("#inquireStateA").html("已确认");
                $(cloneRow).find("#rejectOpe").show();
                $(cloneRow).find("#bookAuditOpe").show();
                break;
            default:
                $(cloneRow).find("#inquireStateA").html("");
                $(cloneRow).find("#researchOutOpe").show();
                break;
        }
        //打印受理单
        $(cloneRow).find("#printOpe").on("click",function () {//打印受理单操作
            getAjaxResult(document.body.jsLee.requestUrl.path8,"POST",{"templateType":3,"claimNo":jsonItem.claimNo},"pdfViewCallBack(data)");
        });
        //下载内部调查
        $(cloneRow).find("#downloadInOpe").on("click",function(){
            var importParam = "name=" + JSON.stringify({"templateType":4,"claimNos":[jsonItem.claimNo]});
            $.download(requestUrl + document.body.jsLee.requestUrl.path3, importParam, "POST");
        });
        //下载外部调查
        $(cloneRow).find("#downloadOutOpe").on("click",function(){
            var importParam = "name=" + JSON.stringify({"templateType":5,"claimNos":[jsonItem.claimNo]});
            $.download(requestUrl + document.body.jsLee.requestUrl.path3, importParam, "POST");
        });
        //查看详情操作
        $(cloneRow).find("#detailOpe").on("click",function(){
            //跳转销售审核详情页面
            jumpUrl("objectionSubDetail.html?htmlType=7&claimNo="+jsonItem.claimNo,"0000000",0);
        });
        //外部调查操作
        $(cloneRow).find("#researchOutOpe").on("click",function(){
            document.body.jsLee.claimNo = jsonItem.claimNo;
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":jsonItem.claimNo,"type":1},"statusOutOpeCallBack(data)");
        });
        //内部调查操作
        $(cloneRow).find("#researchInOpe").on("click",function(){
            document.body.jsLee.claimNo = jsonItem.claimNo;
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":jsonItem.claimNo,"type":2},"statusInOpeCallBack(data)");

        });
        //驳回操作
        $(cloneRow).find("#rejectOpe").on("click",function(){
            openWin('360', '245', 'inputAnswer', true);
            $("#rejectText").val("");
            document.body.jsLee.claimNo = jsonItem.claimNo;
        });
        //确认书审核操作
        $(cloneRow).find("#bookAuditOpe").on("click",function () {
            //跳转确认书审核页面
            jumpUrl("objectionSubDetail.html?htmlType=6&claimNo="+jsonItem.claimNo,"0000000",0);
        });
        //受理操作
        $(cloneRow).find("#acceptOpe").on("click",function(){
            document.body.jsLee.claimNo = jsonItem.claimNo;
            //弹框
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("是否受理此条异议？","警告提示",1,"","acceptOpeTip");
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

//驳回成功回调
function rejectOpeCallBack(data) {
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("驳回成功","成功提示",1,"","rejectSuccess");
    }
}

//点击外部调查状态改变回调
function statusOutOpeCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        //外部调查页面
        jumpUrl("objectionSubDetail.html?htmlType=4&claimNo="+document.body.jsLee.claimNo,"0000000",0);
    }
}

//点击内部调查状态改变回调
function statusInOpeCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        //跳转内部调查页面
        jumpUrl("objectionSubDetail.html?htmlType=5&claimNo="+document.body.jsLee.claimNo,"0000000",0);
    }
}

//受理成功回调函数
function acceptOpeCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
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

function clsAlertBoxCtrl$sure() {//成功弹框确定
    if (this.id == "rejectSuccess") {
        initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,null,"POST");
        closePopupWin();
    }else if(this.id == "acceptOpeTip"){
        closePopupWin();
        getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":document.body.jsLee.claimNo,"type":3},"acceptOpeCallBack(data)");
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