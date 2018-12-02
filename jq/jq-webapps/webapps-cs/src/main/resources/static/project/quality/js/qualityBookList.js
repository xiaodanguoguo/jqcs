function clsMethodLee(){
    this.requestUrl = {
        "path1":"/millsheet/findMillSheetByPage",//初始list列表
        "path2":"/millsheet/preview",//预览接口
        "path3":"/millsheet/downFile",//下载接口
        "path4":"/sysAcct/customerType",//获取用户信息接口
        "path5":"/millsheet/updateNumber",//增加打印次数接口
        "path6":"/",//增加下载次数接口
        "path7":"/millsheet/findMillSheetByPage1",//初始list列表
        "path8":"/millsheet/preview1",//预览接口
        "path9":"/millsheet/revoke"//撤销接口
    };
    this.documentLee = null;
    this.millSheetNo = "";//回退millSheetNo主键
    this.previewArr = [];//缓存预览数组
    this.previewArrCurrent = "";//当前预览图片
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //预览
    this.previewOpe = $("#previewOpe");
    //打印
    this.printOpe = $("#printOpe");
    //下载
    this.downLoadOpe = $("#downLoadOpe");
    //全选
    this.checkAll = $("#checkAll");
    //预览弹框
    this.previewOpeBox = $("#previewOpeBox");
    //打印次数+1
    this.printJiaOpe = $("#printJiaOpe");
    //下载次数+1
    this.downLoadJiaOpe = $("#downLoadJiaOpe");
    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    $("#condstartDt").val(getNowFormatDate());
    $("#condendDt").val(getNowFormatDate());
    getAjaxResult(this.requestUrl.path4,"POST",{},"getContentCallBack(data)");
    $("#condstates").chosen({
        //disable_search_threshold: 5,
        no_results_text: "没有匹配结果!",
        width:"122px",
        enable_split_word_search: false,
        placeholder_text_single: '请选择'
    });
    $("#condmillSheetType").chosen({
        //disable_search_threshold: 5,
        no_results_text: "没有匹配结果!",
        width:"122px",
        enable_split_word_search: false,
        placeholder_text_single: '请选择'
    });
    //$("#condmillLine").attr("reqParam",JSON.stringify({'typeId': 'MILL_LINE'}));
    initplugPath($("#condmillLine")[0],"singleSelectCtrl","/md/findItemsByTypeId",{"typeId":"MILL_LINE"},"POST");
    this.operate();
}

function clsMethodLee$operate(){
    this.previewOpe.on("click",function(){//预览操作  打开弹框 初始化数据
        if($("#tableList")[0].cacheArr.length == 0){
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请勾选将要预览的质证书","失败提示");
        }else{
            /*var millSheetNoArr = [];
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                millSheetNoArr.push({"millSheetNo":$("#tableList")[0].cacheArr[nI].millSheetNo,"operationType":1});
            }
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",millSheetNoArr,"previewCallBack(data)")*/
            var millSheetNoArr = [];
            var millSheetNos = [];
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                millSheetNoArr.push($("#tableList")[0].cacheArr[nI].millSheetNo);
            }
            jumpUrl("../../../generic/web/viewer1.html?file=/millsheet/preview/"+JSON.stringify(millSheetNoArr),"0000000","1");
        }
    });
    //预览弹框点击上一页下一页
    $("#previewPrev").on("click",function () {//上一页
        previewPage(0);
    });
    $("#previewNext").on("click",function () {//下一页
        previewPage(1);
    });

    this.downLoadOpe.on("click",function () {//下载
        if($("#tableList")[0].cacheArr.length == 0){
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请勾选将要下载的质证书","失败提示");
        }else{
            for(var i=0;i<$("#tableList #cloneRow").length;i++){
                var clone=$("#tableList #cloneRow")[i];
                if($(clone).find("#chkCoding").attr("checked")){
                    if(clone.jsonData.state=="SPLITED"){
                        var alertBox = new clsAlertBoxCtrl();
                        alertBox.Alert("已经拆分的不可下载！","错误提示");
                        return;
                    }
                }
            }
            var num = 0;
            for(var nI = 0; nI < $("#tableList")[0].cacheArr.length; nI++){
                if($("#tableList")[0].cacheArr[nI].downableNum != 0){
                    num++;
                }
            }
            if(num == $("#tableList")[0].cacheArr.length){
                if(window.location.href.indexOf("qualityBookList2") != -1){
                    var milName = "";
                    for(var lmI = 0; lmI < $("#tableList")[0].cacheArr.length; lmI++){
                        if($("#tableList")[0].cacheArr[lmI].jcFlag == 0){//jcFlag（number类型）是否是0提示‘建材类不让下载’ 如果是1 再判断字段查询返回的字段isAllow（string类型）
                            milName += milName ? $("#tableList")[0].cacheArr[lmI].millSheetNo : "," + $("#tableList")[0].cacheArr[lmI].millSheetNo;
                        }
                    }
                    if(milName){//说明质证书不能下载
                        var alertBox = new clsAlertBoxCtrl();
                        alertBox.Alert("质证书编号为：" + milName + "。建材类不让下载","错误提示");
                    }else{
                        var milName2 = "";
                        for(var lnI = 0; lnI < $("#tableList")[0].cacheArr.length; lnI++){
                            if($("#tableList")[0].cacheArr[lnI].isAllow == "N"){//jcFlag（number类型）是否是0提示‘建材类不让下载’ 如果是1 再判断字段查询返回的字段isAllow（string类型）
                                milName2 += milName2 ? $("#tableList")[0].cacheArr[lnI].millSheetNo : "," + $("#tableList")[0].cacheArr[lnI].millSheetNo;
                            }
                        }
                        if(milName2){//说明质证书不能下载
                            var alertBox = new clsAlertBoxCtrl();
                            alertBox.Alert("质证书编号为：" + milName + "。您无权下载","错误提示");
                        }else{
                            var millSheetNoArr = [];
                            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                                millSheetNoArr.push($("#tableList")[0].cacheArr[nI].millSheetNo);
                            }
                            var importParam = "name=" + JSON.stringify(millSheetNoArr);
                            $.download(requestUrl + document.body.jsLee.requestUrl.path3, importParam, "POST");
                            $("#tableList")[0].cacheArr = [];
                            setTimeout(function(){
                                $("#tableList")[0].cacheArr = [];
                                //initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
                                if(window.location.href.indexOf("qualityBookList2") != -1){
                                    initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,null,"POST");
                                }else{
                                    initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
                                }
                            },2000);
                        }
                    }
                }else{
                    var millSheetNoArr = [];
                    for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                        millSheetNoArr.push($("#tableList")[0].cacheArr[nI].millSheetNo);
                    }
                    var importParam = "name=" + JSON.stringify(millSheetNoArr);
                    $.download(requestUrl + document.body.jsLee.requestUrl.path3, importParam, "POST");
                    $("#tableList")[0].cacheArr = [];
                    setTimeout(function(){
                        $("#tableList")[0].cacheArr = [];
                        //initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
                        if(window.location.href.indexOf("qualityBookList2") != -1){
                            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,null,"POST");
                        }else{
                            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
                        }
                    },2000);
                }
            }else{
                var alertBox = new clsAlertBoxCtrl();
                alertBox.Alert("存在下载次数已为0的质证书，请取消勾选！","错误提示");
            }
        }
    });

    this.printOpe.on("click",function(){//打印
        if($("#tableList")[0].cacheArr.length == 0){
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请勾选将要打印的质证书","失败提示");
        }else{
            for(var i=0;i<$("#tableList #cloneRow").length;i++){
                var clone=$("#tableList #cloneRow")[i];
                if($(clone).find("#chkCoding").attr("checked")){
                    if(clone.jsonData.state=="SPLITED"){
                        var alertBox = new clsAlertBoxCtrl();
                        alertBox.Alert("已经拆分的不可打印！","错误提示");
                        return;
                    }
                }
            }
            var num = 0;
            for(var nI = 0; nI < $("#tableList")[0].cacheArr.length; nI++){
                if($("#tableList")[0].cacheArr[nI].printableNum != 0){
                    num++;
                }
            }
            if(num == $("#tableList")[0].cacheArr.length){
                if(window.location.href.indexOf("qualityBookList2") != -1) {
                    var milName = "";
                    for (var lmI = 0; lmI < $("#tableList")[0].cacheArr.length; lmI++) {
                        if ($("#tableList")[0].cacheArr[lmI].isAllow == "N") {//isAllow（string类型）  如果是‘Y’ 允许打印   如果是'N' 提示  ‘此质证书您无权打印’
                            milName += milName ? $("#tableList")[0].cacheArr[lmI].millSheetNo : "," + $("#tableList")[0].cacheArr[lmI].millSheetNo;
                        }
                    }
                    if (milName) {//说明质证书不能下载
                        var alertBox = new clsAlertBoxCtrl();
                        alertBox.Alert("质证书编号为：" + milName + "。您无权打印", "错误提示");
                    } else {
                        var millSheetNoArr = [];
                        var millSheetNos = [];
                        for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                            millSheetNoArr.push($("#tableList")[0].cacheArr[nI].millSheetNo);
                            millSheetNos.push({"millSheetNo":$("#tableList")[0].cacheArr[nI].millSheetNo,"operationType":2});
                        }

                        //getAjaxResult(document.body.jsLee.requestUrl.path8,"POST",millSheetNoArr,"printOpeCallBack(data)");
                        if(window.Storage && window.localStorage && window.localStorage instanceof Storage){
                            window.localStorage.millSheetNos = JSON.stringify(millSheetNos);
                        }else{
                            setCookie("millSheetNos",escape(JSON.stringify(millSheetNos)));
                        }
                        jumpUrl("../../../generic/web/viewer.html?file=/millsheet/preview1/"+JSON.stringify(millSheetNoArr),"0000000","1");
                        /*var alertBox = new clsAlertBoxCtrl();
                        alertBox.Alert("确认打印？打印次数减一","警告提示",1,"","printOpeTip");*/
                    }
                }else{
                    var millSheetNoArr = [];
                    var millSheetNos = [];
                    for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                        millSheetNoArr.push($("#tableList")[0].cacheArr[nI].millSheetNo);
                        millSheetNos.push({"millSheetNo":$("#tableList")[0].cacheArr[nI].millSheetNo,"operationType":2});
                    }

                    //getAjaxResult(document.body.jsLee.requestUrl.path8,"POST",millSheetNoArr,"printOpeCallBack(data)");
                    if(window.Storage && window.localStorage && window.localStorage instanceof Storage){
                        window.localStorage.millSheetNos = JSON.stringify(millSheetNos);
                    }else{
                        setCookie("millSheetNos",escape(JSON.stringify(millSheetNos)));
                    }
                    jumpUrl("../../../generic/web/viewer.html?file=/millsheet/preview1/"+JSON.stringify(millSheetNoArr),"0000000","1");
                    /*var alertBox = new clsAlertBoxCtrl();
                    alertBox.Alert("确认打印？打印次数减一","警告提示",1,"","printOpeTip");*/
                }

            }else{
                var alertBox = new clsAlertBoxCtrl();
                alertBox.Alert("存在打印次数已为0的质证书，请取消勾选！","错误提示");
            }
            /*var millSheetNoArr = [];
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                millSheetNoArr.push({"millSheetNo":$("#tableList")[0].cacheArr[nI].millSheetNo,"operationType":1});
            }
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",millSheetNoArr,"previewCallBack2(data)")*/
        }
    });
    $("#rejectSureOpe").on("click",function(){//确认退回操作
        if(checkBackSure()){
            var jsonParam = {"millSheetNo":document.body.jsLee.millSheetNo,"regresses":$("#rejectText").val()};
            getAjaxResult("/rebackApply/applyForRetreat","POST",jsonParam,"backSureCallBack(data)");
        }
    });
    $("#rejectCancelOpe").on("click",function () {
        closePopupWin();
    });

    //打印次+1
    this.printJiaOpe.on("click",function(){
        if($("#tableList")[0].cacheArr.length == 0){
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请勾选将要增加打印次数的质证书","失败提示");
        }else{
            var millSheetNoArr = [];
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                millSheetNoArr.push({"millSheetNo":$("#tableList")[0].cacheArr[nI].millSheetNo,"operationType":1});
            }
            getAjaxResult(document.body.jsLee.requestUrl.path5,"POST",millSheetNoArr,"jiaOneCallBack(data)")
        }
    });

    //下载次数+1
    this.downLoadJiaOpe.on("click",function(){
        if($("#tableList")[0].cacheArr.length == 0){
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请勾选将要增加下载次数的质证书","失败提示");
        }else{
            var millSheetNoArr = [];
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                millSheetNoArr.push({"millSheetNo":$("#tableList")[0].cacheArr[nI].millSheetNo,"operationType":2});
            }
            getAjaxResult(document.body.jsLee.requestUrl.path5,"POST",millSheetNoArr,"jiaOneCallBack(data)")
        }
    });

    //撤销取消
    $("#repealCancelOpe").on("click",function(){
        closePopupWin();
    });
    //撤销确认操作
    $("#repealSureOpe").on("click",function(){
        var paramJson = {"millSheetNo":document.body.jsLee.millSheetNo,"causeOfRevocation":$("#repealText").val()};
        getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",paramJson,"repealSureCallBack(data)")
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

function clsStandardTableCtrl$progress(jsonItem, cloneRow) {//插件渲染操作
    checkboxIsTrue(jsonItem,cloneRow)//勾选判断
    //定义质证书状态
    var statsBook = jsonItem.state;
    /*
    *
    * 0-新建-NEW。 1-已生成-CREATED。2-已审核-EXAMINED。3-已预览-PRIVIEWED。
    * 4-已下载-DOWNLOADED。5-已打印-PRINTED。6-待处理-PENDING。
    * 7-已回退-FALLBACKED。8-已置废-DISSUSED。9-已拆分-SPLITED
    *
    * 1、适用于状态为“已审核”和“已预览”的质证书进行拆分申请。
    * 2。只有质证书状态为已下载和已打印的才能够点击强制拆分。
    *
    * */
    switch (statsBook){
        case "NEW"://新建
            $(cloneRow).find("#stateName").html("新建");
            break;
        case "CREATED"://已生成
            $(cloneRow).find("#stateName").html("已生成");
            break;
        case "EXAMINED"://已审核
            $(cloneRow).find("#stateName").html("已审核");
            $(cloneRow).find("#commonSplit").show();
            break;
        case "PRIVIEWED"://已预览
            $(cloneRow).find("#stateName").html("已预览");
            $(cloneRow).find("#commonSplit").show();
            break;
        case "DOWNLOADED"://已下载
            $(cloneRow).find("#stateName").html("已下载");
            $(cloneRow).find("#strongSplit").show();
            break;
        case "PRINTED"://已打印
            $(cloneRow).find("#stateName").html("已打印");
            $(cloneRow).find("#strongSplit").show();
            break;
        case "PENDING"://待处理
            $(cloneRow).find("#stateName").html("待处理");
            break;
        case "FALLBACKED"://已回退
            $(cloneRow).find("#stateName").html("已回退");
            break;
        case "DISSUSED"://已置废
            $(cloneRow).find("#stateName").html("已置废");
            break;
        case "SPLITED"://已拆分
            $(cloneRow).find("#stateName").html("已拆分");
            $(cloneRow).find("#commonSplit").show();
            break;
    }
    if((jsonItem.millSheetType == "Z" || jsonItem.millSheetType == "S") && statsBook != "SPLITED"){
        $(cloneRow).find("#repealOpe").show();
    }
    if(statsBook == "EXAMINED" || statsBook == "DOWNLOADED" || statsBook == "PRIVIEWED" || statsBook == "PRINTED"){
        $(cloneRow).find("#applyBack").show();
    }
    //赋值title
    $(cloneRow).find("#zchehao").attr("title",jsonItem.zchehao);
    $(cloneRow).find("#zcpmc").attr("title",jsonItem.zcpmc);
    $(cloneRow).find("#zkunnr").attr("title",jsonItem.zkunnr);

    //判断是否拆分过，存在拆分历史
    if(jsonItem.isSplit == 1){
        $(cloneRow).find("#historySplit").show();
    }

    //拆分申请操作
    $(cloneRow).find("#commonSplit").on("click",function () {
        if(window.location.href.indexOf("qualityBookList2") != -1){
            jumpUrl("bookSplit.html?millSheetNo=" + jsonItem.millSheetNo,"0000000",1);
        }else{
            jumpUrl("bookSplit.html?jumpType=1&&millSheetNo=" + jsonItem.millSheetNo,"0000000",1);
        }
        //jumpUrl("bookSplit.html?millsheetNo=" + jsonItem.millSheetNo,"0000000",1);
    });
    //强制拆分申请操作
    $(cloneRow).find("#strongSplit").on("click",function () {
        jumpUrl("bookSplit.html?millsheetNo=" + jsonItem.millSheetNo,"0000000",1);
    });
    //拆分历史查看操作
    $(cloneRow).find("#historySplit").on("click",function(){
        if(window.location.href.indexOf("qualityBookList2") != -1){
            jumpUrl("historySplit.html?millSheetNo=" + jsonItem.millSheetNo,"0000000",1);
        }else{
            jumpUrl("historySplit.html?jumpType=1&&millSheetNo=" + jsonItem.millSheetNo,"0000000",1);
        }
    });
    //申请回退
    $(cloneRow).find("#applyBack").on("click",function(){
        openWin('360', '245', 'applyBackPopup', true);
        $("#rejectText").val("");
        document.body.jsLee.millSheetNo = jsonItem.millSheetNo;
    });
    //撤销操作
    $(cloneRow).find("#repealOpe").on("click",function(){
        document.body.jsLee.millSheetNo = jsonItem.millSheetNo;
        if(jsonItem.state == "EXAMINED"){//如果为“已审核”则直接撤销
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("是否撤销","警告提示",1,"","repealOpeTip");
        }else if(jsonItem.state == "SPLITED"){//如果为“已拆分”则不能操作撤销，并提示“已拆分状态的质证书不能够撤销，如要撤销，请先将下级质证书撤销后再操作."
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("已拆分状态的质证书不能够撤销，如要撤销，请先将下级质证书("+ jsonItem.lowerMillSheetNos +")撤销后再操作","警告提示");
        }else{//如果状态为“已预览、已下载和已打印”状态则需要和用户线下沟通后，录入撤销原因，然后才能够撤销.
            openWin('360', '245', 'repealOpePopup', true);
        }
    });
};

function clsStandardTableCtrl$after() {
    isAllCheck();
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

//预览回调
function previewCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        openWin("1200","600","previewOpeBox");
        document.body.jsLee.previewArr = data.rspBody;
        document.body.jsLee.previewArrCurrent = document.body.jsLee.previewArr[0].millSheetPath;
        $("#previewOpeBoxPdf").attr("src",document.body.jsLee.previewArrCurrent);
        /*$("#previewOpeBoxPdf").attr("href",document.body.jsLee.previewArrCurrent);
        $("#previewOpeBoxPdf").media({width:1150, height:550});*/
        $("#previewPrev").attr("disabled",true).addClass("changeGary");
        if(document.body.jsLee.previewArr.length == 1){
            $("#previewNext").attr("disabled",true).addClass("changeGary");
        }else{
            $("#previewNext").attr("disabled",false).removeClass("changeGary");
        }
        $("#tableList")[0].cacheArr = [];
        if(window.location.href.indexOf("qualityBookList2") != -1){
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,null,"POST");
        }else{
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
        }
        //initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
    }
}

//  打印回调
function previewCallBack2(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        jumpUrl("viewerPng.html?file="+data.rspBody[0].millSheetPath,"0000000",1);
        $("#tableList")[0].cacheArr = [];
        if(window.location.href.indexOf("qualityBookList2") != -1){
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,null,"POST");
        }else{
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
        }
    }
}

//预览中上一页下一页操作
function previewPage(type){//type——0上一页  1下一页
    for(var nI = 0; nI < document.body.jsLee.previewArr.length; nI++){
        if(document.body.jsLee.previewArr[nI].millSheetPath == document.body.jsLee.previewArrCurrent){
            if(type == 0){//上一页操作
                document.body.jsLee.previewArrCurrent = document.body.jsLee.previewArr[nI - 1].millSheetPath;
                $("#previewNext").removeAttr("disabled").removeClass("changeGary");
                if(nI - 1 == 0){//当前点击后是第一页
                    $("#previewPrev").attr("disabled",true).addClass("changeGary");
                }
            }else{//下一页操作
                document.body.jsLee.previewArrCurrent = document.body.jsLee.previewArr[nI + 1].millSheetPath;
                $("#previewPrev").removeAttr("disabled").removeClass("changeGary");
                if(nI + 2 == document.body.jsLee.previewArr.length){//当前点击后是最后一页
                    $("#previewNext").attr("disabled",true).addClass("changeGary");
                }
            }
            $("#previewOpeBoxPdf").attr("src",document.body.jsLee.previewArrCurrent);
            /*$("#previewOpeBoxPdf").attr("href",document.body.jsLee.previewArrCurrent);
            $("#previewOpeBoxPdf").media({width:1150, height:550});*/
            break;
        }
    }
}

//刷新列表数据勾选初始化
function checkboxIsTrue(jsonItem,cloneRow){
    var arrCache = $("#tableList")[0].cacheArr;
    for(var nI = 0; nI < arrCache.length; nI++ ){
        if(jsonItem.millSheetNo == arrCache[nI].millSheetNo){
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
    if(numLength == listCheck.length && numLength > 0){
        $("#checkAll").attr("checked",true);
    }
}

//返回联系人信息
function getContentCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        if(data.rspBody){
            if(data.rspBody.acctType != 5){
                $("#condzkunnr").val(data.rspBody.orgName).attr("disabled",true).addClass("changeGary");
                $("*[comType=clearAllCond]").attr("bindCtrlId","condzhth,condzchehao,condmilSheetNo,condbattenPlateNo,condzph");
                $("*[comType=clearAllCond]")[0].jsCtrl.bindCtrlId = "condzhth,condzchehao,condmilSheetNo,condbattenPlateNo,condzph";
                $("#tableList")[0].cacheArr = [];
                if(window.location.href.indexOf("qualityBookList2") != -1){
                    initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,{"zkunnr":data.rspBody.orgName,"startDt":$("#condstartDt").val(),"endDt":$("#condendDt").val()},"POST");
                }else{
                    initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,{"zkunnr":data.rspBody.orgName,"startDt":$("#condstartDt").val(),"endDt":$("#condendDt").val()},"POST");
                }
                //initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,{"zkunnr":data.rspBody.orgName,"startDt":$("#condstartDt").val(),"endDt":$("#condendDt").val()},"POST");
            }else{
                $("#tableList")[0].cacheArr = [];
                if(window.location.href.indexOf("qualityBookList2") != -1){
                    initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,{"startDt":$("#condstartDt").val(),"endDt":$("#condendDt").val()},"POST");
                }else{
                    initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,{"startDt":$("#condstartDt").val(),"endDt":$("#condendDt").val()},"POST");
                }
                //initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,{"startDt":$("#condstartDt").val(),"endDt":$("#condendDt").val()},"POST");
            }

        }


    }
}

function clsAlertBoxCtrl$sure() {//成功弹框确定
    if (this.id == "printOpeTip") {
        var millSheetNoArr = [];
        for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
            millSheetNoArr.push({"millSheetNo":$("#tableList")[0].cacheArr[nI].millSheetNo,"operationType":2});
        }
        getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",millSheetNoArr,"printOpeCallBack(data)");
        closePopupWin();
    }else if(this.id == "repealOpeTip"){
        getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",{"millSheetNo":document.body.jsLee.millSheetNo},"repealSureCallBack(data)");
    }
}

//打印回调
function printOpeCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        $("#tableList")[0].cacheArr = [];
        if(window.location.href.indexOf("qualityBookList2") != -1){
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,null,"POST");
        }else{
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
        }
        jumpUrl("../../../generic/web/viewer.html?file=" + data.rspBody[0].report,"0000000","1");
    }
}

//校验退回操作
function checkBackSure(){
    initValidate($("#applyBackPopup")[0]);
    var valiClass=new clsValidateCtrl();
    if(!valiClass.validateAll4Ctrl($("#applyBackPopup")[0])){
        return false;
    }
    return true;
}

//退回接口回调函数
function backSureCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,null,"POST");
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("电子质证书，编号："+ document.body.jsLee.millSheetNo +"，已经回退","成功提示");
    }
}

//增加次数（打印/下载）回调函数
function jiaOneCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        //initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
        if(window.location.href.indexOf("qualityBookList2") != -1){
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,null,"POST");
        }else{
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
        }
    }
}

function clsSearchBtnCtrl$after(jsonCond) {
    $("#tableList")[0].cacheArr = [];
    return jsonCond;
}

//获取当前日期
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        /*+ " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();*/
    return currentdate;
}

//撤销成功回调函数
function repealSureCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        if(window.location.href.indexOf("qualityBookList2") != -1){
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path7,null,"POST");
        }else{
            initplugPath($("#tableList")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path1,null,"POST");
        }
    }
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});