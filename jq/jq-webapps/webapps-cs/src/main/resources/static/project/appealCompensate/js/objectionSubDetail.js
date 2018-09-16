function clsMethodLee(){
    this.requestUrl = {
        "path1":"/md/findItemsByTypeId",//异议产品下拉接口（产品大类接口）
        "path2":"/orderUnit/findByPage",//订货单位列表
        "path3":"/unitOfUse/findByPage",//使用单位列表
        "path4":"/objectionTiBao/findDetails",//异议提报新建/修改/详情/销售审核数据回显 1是新建2是修改3是详情4销售审核
        "path5":"/millsheet/findIsTrue",//质证书编号检验
        "path6":"/objectionTiBao/update",//异议提报新增/修改/保存/销售审核（保存、驳回、通过）1审核保存操作2驳回操作3通过操作4修改保存5新增保存
        "path7":"/objectionTiBao/down",//详情页面下载功能
        "path8":"/objectionTiBao/printing",//详情页面打印功能
        "path9":"/objectionDiaoCha/findDetails",//异议调查外部调查内部调查回显数据|确认书审核   1是外部调查 2是内部调查 3确认书审核
        "path10":"/objectionDiaoCha/update",//内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）
                                            //1外部保存2外部跟踪3外部提交4内部保存5内部提交6确认书通过7确认书审核
        "path11":"/file/upload",//上传
        "path12":"/coilinfo/findIsTrue"//批板卷号填写校验操作，校验成功带出数据
    };
    this.documentLee = null;
    this.htmlType = GetQueryString("htmlType");//判断页面类型0——新建 1修改 2——详情  3——销售审核  4——外部调查  5——内部调查 6-确认书审核  7-销售审核详情页面
    this.claimNo = GetQueryString("claimNo") == null ? "":GetQueryString("claimNo");//异议编号
    this.filePath = [];//异议产品图片:
    this.reportPictures = [];//质量异议报告图片:
    this.selectedMark = {//判断发生异议单位和异议类别是否选中
        "selMark1":true,
        "selMark2":true
    }
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //订货单位添加
    this.dinghuoCreate = $("#dinghuoCreate");
    //使用单位添加
    this.shiyongCreate = $("#shiyongCreate");
    //质证书编号
    this.millSheetNo = $("#millSheetNo");
    //批板卷号
    this.battenPlateNo = $("#battenPlateNo");
    //新建|修改  页面清空按钮
    this.firstReset = $("#firstReset");
    //新建|修改  页面保存按钮
    this.firstSave = $("#firstSave");
    //详情页面打印按钮
    this.secondPrint = $("#secondPrint");
    //详情页面下载按钮
    this.secondDownload = $("#secondDownload");
    //销售管理保存按钮
    this.thirdSave = $("#thirdSave");
    //销售管理通过按钮
    this.thirdPromise = $("#thirdPromise");
    //销售管理驳回按钮
    this.thirdReject = $("#thirdReject");
    //外部调查保存按钮
    this.forthsubmit = $("#forthsubmit");
    //外部调查跟踪按钮
    this.forthFoolow = $("#forthFoolow");
    //外部调查提交按钮
    this.forthSave = $("#forthSave");
    //内部调查保存按钮
    this.fifthSave = $("#fifthSave");
    //内部调查提交按钮
    this.fifthSubmit = $("#fifthSubmit");
    //保证书审核通过按钮
    this.sixthPromise = $("#sixthPromise");
    //保证书审核驳回按钮
    this.sixthReject = $("#sixthReject");

    this.parse();

}
function clsMethodLee$parse(){
    //判断页面类型（进行显示隐藏dom节点）0——新建 1修改 2——详情  3——销售审核  4——外部调查  5——内部调查 6-确认书审核（外部调查不可编辑）7-销售审核详情（销售审核不可编辑）
    switch (Number(this.htmlType)){
        //差异class  box01 box2  box3  box23 box4  box5  box013  box237 box6
        case 0://新建
            $("#boxSecond").remove();
            //$(".box01").remove();
            //$(".box013").remove();x
            $(".box2").remove();
            $(".box23").remove();
            $(".box237").remove();
            $(".box3").remove();
            $(".box4").remove();
            $(".box5").remove();
            $(".box6").remove();
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":this.claimNo,"optionType":1},"htmlInit(data)");//数据回显操作
            break;
        case 1://修改
            $("#boxSecond").remove();
            //$(".box01").remove();
            //$(".box013").remove();
            $(".box2").remove();
            $(".box23").remove();
            $(".box237").remove();
            $(".box3").remove();
            $(".box4").remove();
            $(".box5").remove();
            $(".box6").remove();
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":this.claimNo,"optionType":2},"htmlInit(data)");//数据回显操作
            break;
        case 2://详情
            $("#boxSecond").remove();
            $(".box01").remove();
            $(".box013").remove();
            //$(".box2").remove();
            //$(".box23").remove();
            //$(".box237").remove();
            $(".box3").remove();
            $(".box4").remove();
            $(".box5").remove();
            $(".box6").remove();
            $("#submitBox input").attr("disabled",true).addClass("changeGary");
            $("#submitBox textarea").attr("disabled",true).addClass("changeGary");
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":this.claimNo,"optionType":3},"htmlInit(data)");//数据回显操作
            break;
        case 3://销售审核
            $("#boxSecond").remove();
            $(".box01").remove();
            //$(".box013").remove();
            $(".box2").remove();
            //$(".box23").remove();
            //$(".box237").remove();
            //$(".box3").remove();
            $(".box4").remove();
            $(".box5").remove();
            $(".box6").remove();
            $("#submitBox input").attr("disabled",true).addClass("changeGary");
            $("#submitBox textarea").attr("disabled",true).addClass("changeGary");
            $("#proProblem").removeAttr("disabled").removeClass("changeGary");
            $("#claimTypeA input").removeAttr("disabled").removeClass("changeGary");
           // this.selectedMark.selMark1 = false;
            this.selectedMark.selMark2 = false;
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":this.claimNo,"optionType":4},"htmlInit(data)");//数据回显操作
            break;
        case 4://外部调查
            $("#boxFirst").remove();
            $(".box01").remove();
            $(".box013").remove();
            $(".box2").remove();
            $(".box23").remove();
            $(".box237").remove();
            $(".box3").remove();
            //$(".box4").remove();
            $(".box5").remove();
            $(".box6").remove();
            $("#amountOfUse").removeAttr("disabled").removeClass("changeGary")
            getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",{"claimNo":this.claimNo,"optionType":1},"htmlInit2(data)");//数据回显操作
            break;
        case 5://内部调查
            $("#boxFirst").remove();
            $(".box01").remove();
            $(".box013").remove();
            $(".box2").remove();
            $(".box23").remove();
            $(".box237").remove();
            $(".box3").remove();
            $(".box4").parents("#parentBox").css("height","138px");
            $(".box4").remove();
            //$(".box5").remove();
            $(".box6").remove();
            $("#logisticsProcess").attr("disabled",true).addClass("changeGary");
            $("#endProcessingTech").attr("disabled",true).addClass("changeGary");
            $("#inSearchBox input").attr("disabled",true).addClass("changeGary");
            $("#fieldConclusionA").attr("disabled",true).addClass("changeGary");
            $("#parentBox").prev().html("异议投诉描述:");
            getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",{"claimNo":this.claimNo,"optionType":2},"htmlInit2(data)");//数据回显操作
            break;
        case 6://确认书审核
            $("#boxFirst").remove();
            $(".box01").remove();
            $(".box013").remove();
            $(".box2").remove();
            $(".box23").remove();
            $(".box237").remove();
            $(".box3").remove();
            //$(".box4").remove();
            $(".box5").remove();
            //$(".box6").remove();
            $("#submitBox input").attr("disabled",true).addClass("changeGary");
            $("#submitBox textarea").attr("disabled",true).addClass("changeGary");
            getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",{"claimNo":this.claimNo,"optionType":3},"htmlInit2(data)");//数据回显操作
            break;
        case 7://销售审核详情
            $("#boxSecond").remove();
            $(".box01").remove();
            //$(".box013").remove();
            $(".box2").remove();
            //$(".box23").remove();
            //$(".box237").remove();
            //$(".box3").remove();
            $(".box4").remove();
            $(".box5").remove();
            $(".box6").remove();
            $(".box3:last").remove();
            $("#submitBox input").attr("disabled",true).addClass("changeGary");
            $("#submitBox textarea").attr("disabled",true).addClass("changeGary");
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":this.claimNo,"optionType":4},"htmlInit(data)");//数据回显操作
            break;
    }
    initValidate($("#submitBox")[0]);
    $("#claimNo").val(document.body.jsLee.claimNo);
    this.operate();
}

function clsMethodLee$operate(){
    this.dinghuoCreate.on("click",function () {//订货单位添加操作
        openWin('650', '500', 'listPopup', true);
        $("#dinghuoListPopup").show();
        $("#shiyongListPopup").hide();
        $("#listPopupTitle").html("订货单位列表");
        initplugPath($("#dinghuoListPopupBox")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,null,"POST");
    });
    this.shiyongCreate.on("click",function () {//使用单位添加操作
        openWin('650', '500', 'listPopup', true);
        $("#dinghuoListPopup").hide();
        $("#shiyongListPopup").show();
        $("#listPopupTitle").html("使用单位列表");
        initplugPath($("#shiyongListPopupBox")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path3,null,"POST");

    });
    //质证书编号改变，进行接口后台判断
    this.millSheetNo.on("change",function () {
        getAjaxResult(document.body.jsLee.requestUrl.path5,"POST",{"millSheetNo":$(this).val()},"millSheetNoCheckCallBack(data)");
    });

    //批板卷号改编，对应质证书号进行校验
    this.battenPlateNo.on("change",function(){
        if(!$("#millSheetNo").val()){
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请输入质证书号！","失败提示");
        }else{
            getAjaxResult(document.body.jsLee.requestUrl.path12,"POST",{"millSheetNo":$("#millSheetNo").val(),"zcharg":$(this).val()},"battenPlateNoCheckCallBack(data)");
        }
    });
    
    //新建 || 修改页面清空操作
    this.firstReset.on("click",function () {
        $("#submitBox input[type=text]").val("");
        $("#submitBox textarea").val("");
    });
    //新建 || 修改页面保存操作
    this.firstSave.on("click",function () {
        if(boxChecked()){
            var jsonParam = paramJson();
            if(document.body.jsLee.htmlType == 0){//新建
                jsonParam.optionStuts = 5;
            }else if(document.body.jsLee.htmlType == 1){//修改
                jsonParam.optionStuts = 4;
            }
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POSt",jsonParam,"firstSaveCallBack(data)")
        }else{

        }
    });

    //详情页面打印操作
    this.secondPrint.on("click",function(){

    });
    //详情页面下载操作
    this.secondDownload.on("click",function(){
        var importParam = "name=" + JSON.stringify(document.body.jsLee.claimNo);
        $.download(requestUrl + document.body.jsLee.requestUrl.path7, importParam, "POST");
    });
    //销售管理保存操作
    this.thirdSave.on("click",function(){
        $("#rejectReason").removeClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 1;
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POSt",jsonParam,"firstSaveCallBack(data)")
        }
    });
    //销售管理通过操作
    this.thirdPromise.on("click",function(){
        $("#rejectReason").removeClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 3;
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POSt",jsonParam,"firstSaveCallBack(data)")

        }
    });
    //销售管理驳回操作
    this.thirdReject.on("click",function(){
        $("#rejectReason").addClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 2;
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POSt",jsonParam,"firstSaveCallBack(data)")
        }
    });

    //外部调查保存按钮
    this.forthsubmit.on("click",function () {
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 1;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POSt",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //外部调查跟踪按钮
    this.forthFoolow.on("click",function () {
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 2;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POSt",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //外部调查提交按钮
    this.forthSave.on("click",function () {
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 3;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POSt",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //内部调查保存按钮
    this.fifthSave.on("click",function () {
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 4;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POSt",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //内部调查提交按钮
    this.fifthSubmit.on("click",function () {
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 5;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POSt",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //通知书审核通过操作
    this.sixthPromise.on("click",function(){
        $("#rejectReason").removeClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 6;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POSt",jsonParam,"secondSaveCallBack(data)")

        }
    });
    //通知书审核驳回操作
    this.sixthReject.on("click",function(){
        $("#rejectReason").addClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 7;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POSt",jsonParam,"secondSaveCallBack(data)")
        }
    });
    /*//发生异议单位选中改变事件
    $("#dissentingUnitA input").on("click",function(){
        document.body.jsLee.selectedMark.selMark1 = true;
    });*/
    //异议类别选中改变事件
    $("#claimTypeA input").on("click",function(){
        document.body.jsLee.selectedMark.selMark2 = true;
    });

}
function clsMethodLee$refresh(){

}

//插件渲染操作
function clsStandardTableCtrl$progress(jsonItem, cloneRow) {
    if (this.ctrl.id == "dinghuoListPopupBox") {//订货单位列表
        $(cloneRow).find("#createWay").on("click",function () {
            closePopupWin();
            setValue4Desc(jsonItem,$("#firstDetail")[0])//赋值
        });
    }else if (this.ctrl.id == "shiyongListPopupBox"){//使用单位列表
        $(cloneRow).find("#createWay").on("click",function () {
            closePopupWin();
            setValue4Desc(jsonItem,$("#secondDetail")[0])//赋值
        });
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

function htmlInit(data){//数据回显回调
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        setValue4Desc(data.rspBody,$("#submitBox")[0])//赋值
        if(document.body.jsLee.htmlType == 0 || document.body.jsLee.htmlType == 1){//新建||修改
            //回显上传图片地址
            filePathShow(data.rspBody.filePath,data.rspBody.reportPictures,1);
        }else if(document.body.jsLee.htmlType == 2){//详情
            //回显上传图片地址
            filePathShow(data.rspBody.filePath,data.rspBody.reportPictures,2);
        }else if(document.body.jsLee.htmlType == 3){//销售审核页面
            //异议类别赋值
            if(data.rspBody.claimType){
                $("#claimTypeA").find("input").eq(data.rspBody.claimType - 1).attr("checked",true);
            };
            //异议单位赋值
            $("#dissentingUnitA").find("input[value=" + data.rspBody.dissentingUnit + "]").attr("checked",true);
            //异议类别赋值
            $("#productNameA").find("input[value=" + data.rspBody.productName + "]").attr("checked",true);
            //$(".disNone").show().parent().next().addClass("required");
            //回显上传图片地址
            filePathShow(data.rspBody.filePath,data.rspBody.reportPictures,2);
        }else if(document.body.jsLee.htmlType == 7){//销售审核详情页面
            //异议类别赋值
            if(data.rspBody.claimType){
                $("#claimTypeA").find("input").eq(data.rspBody.claimType - 1).attr("checked",true);
            };
            //异议单位赋值
            $("#dissentingUnitA").find("input[value=" + data.rspBody.dissentingUnit + "]").attr("checked",true);
            //异议类别赋值
            $("#productNameA").find("input[value=" + data.rspBody.productName + "]").attr("checked",true);
            //回显上传图片地址
            filePathShow(data.rspBody.filePath,data.rspBody.reportPictures,2);
        }
    }
}

function htmlInit2(data){//数据回显回调
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        setValue4Desc(data.rspBody,$("#submitBox")[0])//赋值
        switch(Number(data.rspBody.claimType)){
            case 1:
                $("#claimTypeA").val("表面外观").attr("markCode",1);
                break;
            case 2:
                $("#claimTypeA").val("理化性能").attr("markCode",2);
                break;
            case 3:
                $("#claimTypeA").val("加工使用").attr("markCode",3);
                break;
            case 4:
                $("#claimTypeA").val("尺寸公差").attr("markCode",4);
                break;
            case 5:
                $("#claimTypeA").val("实物不符").attr("markCode",5);
                break;
            case 6:
                $("#claimTypeA").val("计量").attr("markCode",6);
                break;
            case 7:
                $("#claimTypeA").val("其他").attr("markCode",7);
                break;
        };
        if(document.body.jsLee.htmlType == 4){//外部调查
            //富文本数据回显
            var ue = UE.getEditor('editor');
            ue.ready(function() {//编辑器初始化完成再赋值
                data.rspBody.inquireInfo = '<p>123123<img style="max-width: 400px; width: 220px; height: 145px;" src="http://192.168.1.115:20183/res/2018/08/jpg/20180830105725_8759.jpg" title="abc.jpg" alt="abc.jpg" width="220" height="145"/></p>';
                ue.setContent(data.rspBody.inquireInfo);  //赋值给UEditor
            });
            var ue2 = UE.getEditor('editor2');
            ue2.ready(function() {//编辑器初始化完成再赋值
                data.rspBody.fieldConclusion = '<p>123123<img style="max-width: 400px; width: 220px; height: 145px;" src="http://192.168.1.115:20183/res/2018/08/jpg/20180830105725_8759.jpg" title="abc.jpg" alt="abc.jpg" width="220" height="145"/></p>';
                ue2.setContent(data.rspBody.fieldConclusion);  //赋值给UEditor
            });
        }else if(document.body.jsLee.htmlType == 5){//内部调查
            //富文本数据回显
            var ue = UE.getEditor('editor');
            ue.ready(function() {//编辑器初始化完成再赋值
                data.rspBody.inquireInfo = '<p>123123<img style="max-width: 400px; width: 220px; height: 145px;" src="http://192.168.1.115:20183/res/2018/08/jpg/20180830105725_8759.jpg" title="abc.jpg" alt="abc.jpg" width="220" height="145"/></p>';
                ue.setContent(data.rspBody.inquireInfo);  //赋值给UEditor
            });
        }else if(document.body.jsLee.htmlType == 6){//确认书审核
            //富文本数据回显
            var ue = UE.getEditor('editor');
            ue.ready(function() {//编辑器初始化完成再赋值
                data.rspBody.inquireInfo = '<p>123123<img style="max-width: 400px; width: 220px; height: 145px;" src="http://192.168.1.115:20183/res/2018/08/jpg/20180830105725_8759.jpg" title="abc.jpg" alt="abc.jpg" width="220" height="145"/></p>';
                ue.setContent(data.rspBody.inquireInfo);  //赋值给UEditor
            });
            var ue2 = UE.getEditor('editor2');
            ue2.ready(function() {//编辑器初始化完成再赋值
                data.rspBody.fieldConclusion = '<p>123123<img style="max-width: 400px; width: 220px; height: 145px;" src="http://192.168.1.115:20183/res/2018/08/jpg/20180830105725_8759.jpg" title="abc.jpg" alt="abc.jpg" width="220" height="145"/></p>';
                ue2.setContent(data.rspBody.fieldConclusion);  //赋值给UEditor
            });
        }
    }
}

//质证书编号校验回调
function millSheetNoCheckCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        setValue4Desc(data.rspBody,$("#submitBox")[0])//赋值
        $("#battenPlateNo").val("");
    }else{
        $("#millSheetNo").val("");
        $("#battenPlateNo").val("");
    }
}

//批板卷号校验回调
function battenPlateNoCheckCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        setValue4Desc(data.rspBody,$("#submitBox")[0])
    }else{
        $("#battenPlateNo").val("");
    }
}

function boxChecked(){
    initValidate($("#submitBox")[0]);
    var valiClass=new clsValidateCtrl();
    if(document.body.jsLee.htmlType == 4 || document.body.jsLee.htmlType == 6){//外部调查  确认书审核
        if(!valiClass.validateAll4Ctrl($("#submitBox")[0]) || !document.body.jsLee.selectedMark.selMark2 || !UE.getEditor('editor').getContent() || !UE.getEditor('editor2').getContent();){
            if(!document.body.jsLee.selectedMark.selMark2){
                showErrInfoByCustomDiv($("#claimTypeA")[0],"请选择异议类别!");
            }
            if(!UE.getEditor('editor').getContent()){
                showErrInfoByCustomDiv($("#inquireInfoAllA")[0],"请输入调查事实阐述图文描述!");
            }
            if(!UE.getEditor('editor2').getContent()){
                showErrInfoByCustomDiv($("#fieldConclusionA")[0],"请输入现场结论!");
            }
            return false;
        }
    }else if(document.body.jsLee.htmlType == 5){//内部调查
        if(!valiClass.validateAll4Ctrl($("#submitBox")[0]) || !document.body.jsLee.selectedMark.selMark2 || !UE.getEditor('editor').getContent()){
            if(!document.body.jsLee.selectedMark.selMark2){
                showErrInfoByCustomDiv($("#claimTypeA")[0],"请选择异议类别!");
            }
            if(!UE.getEditor('editor').getContent()){
                showErrInfoByCustomDiv($("#fieldConclusionA")[0],"请输入异议投诉描述!");
            }
            return false;
        }
    }else{
        if(!valiClass.validateAll4Ctrl($("#submitBox")[0]) || !document.body.jsLee.selectedMark.selMark2){
            if(!document.body.jsLee.selectedMark.selMark2){
                showErrInfoByCustomDiv($("#claimTypeA")[0],"请选择异议类别!");
            }
            return false;
        }
    }
    return true;
}

//完成提交参数拼接
function paramJson(){
    var jsonParam = {"claimNo":"","productName":"","millSheetNo":"","customerId":"","customerName":"","custAddr":"","custEmpNo":"","custTel":"","lastUserId":"","lastUser":"","lastUserAddr":"","createEmpNo":"","lastUserTel":"","battenPlateNo":"","designation":"","used":"","contractNo":"","contractVolume":"","specs":"","originalWeight":"","orderNo":"","originalCarNo":"","contractUnitPrice":"","objectionNum":"","endProcessingTech":"","claimDesc":"","claimReason":"","rejectReason":"","productDt":"","shift":"","userRequirement":"","handingSuggestion":"","inquireInfo":"","fieldConclusion":"","claimVerdict":"","improvement":"","amountOfUse":"","proProblem":""};
    getValue4Desc(jsonParam,$("#submitBox")[0]);
    //上传图片
    jsonParam.filePath = document.body.jsLee.filePath.join(";");
    jsonParam.reportPictures = document.body.jsLee.reportPictures;
    //富文本编辑器
    if(document.body.jsLee.htmlType == 3){//销售审核
        //异议类别
        jsonParam.claimType = $("#claimTypeA input:checked").val();
        //异议单位
        jsonParam.dissentingUnit = $("#dissentingUnitA input:checked").val();
    }else if(document.body.jsLee.htmlType == 4){//外部调查
        jsonParam.inquireInfo = UE.getEditor('editor').getContent();
        jsonParam.fieldConclusion = UE.getEditor('editor2').getContent();
        //异议分类和缺陷分类获取值（同一个字段）
        jsonParam.claimType = $("#claimTypeA").attr("markCode");
    }else if(document.body.jsLee.htmlType == 5){//内部调查
        jsonParam.inquireInfo = UE.getEditor('editor').getContent();
        //异议分类和缺陷分类获取值（同一个字段）
        jsonParam.claimType = $("#claimTypeA").attr("markCode");
    }else if(document.body.jsLee.htmlType == 6){//通知书审核
        //异议分类和缺陷分类获取值（同一个字段）
        jsonParam.claimType = $("#claimTypeA").attr("markCode");
    }else if(document.body.jsLee.htmlType == 7){//销售审核详情
        //异议分类和缺陷分类获取值（同一个字段）
        jsonParam.claimType = $("#claimTypeA input:checked").val();
        //异议单位
        jsonParam.dissentingUnit = $("#dissentingUnitA input:checked").val();
    }
    return jsonParam;
}

function firstSaveCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示",1,"","successJump");
    }
}

function secondSaveCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示",1,"","successJump2");
    }
}
function clsUploadCtrl$successAfter(ctrl, response)
{
    if($(ctrl).parents("#filePathA").length == 1){
        document.body.jsLee.filePath = [];
        $("#filePathA *[id=uploadBox] img").each(function(){
            if($(this).attr("src") != "../images/comUploadBg1.png"){
                document.body.jsLee.filePath.push($(this).attr("src"));
            }
        });
    }else{
        document.body.jsLee.reportPictures = $(ctrl).find("img").attr("src");
    }

}

//删除上传图片
function clsUploadCtrl$deleteImg(ctrl) {
    if(ctrl.parents("#filePathA").length == 1){
        ctrl.parents("#uploadBox").removeClass("comUploadAfter");
        ctrl.attr("src","../images/comUploadBg1.png");
        document.body.jsLee.filePath = [];
        $("#filePathA *[id=uploadBox] img").each(function(){
            if($(this).attr("src") != "../images/comUploadBg1.png"){
                document.body.jsLee.filePath.push($(this).attr("src"));
            }
        });
    }else{
        document.body.jsLee.reportPictures = [];
    }

}

//回显上传图片地址
function filePathShow(arrStr,str2,type){//type 1是复现数据   2是不可编辑，赋值图片地址
    if(arrStr == null || arrStr == ""){
        document.body.jsLee.filePath = [];
    }else{
        document.body.jsLee.filePath = arrStr.split(";");
    }
    document.body.jsLee.reportPictures = str2;
    if(type == 1){
        //异议产品图片:
        for(var nI = 0; nI < document.body.jsLee.filePath.length; nI++ ){
            $("#filePathA *[id=uploadBox]").eq(nI).attr("uploadbgsrc",document.body.jsLee.filePath[nI]);
            document.body.jsCtrl.ctrl = $("#filePathA *[id=uploadBox]")[nI];
            document.body.jsCtrl.init();
            $("#filePathA *[id=uploadBox]").eq(nI).addClass("comUploadAfter");
        }
        //质量异议报告图片:
        $("#reportPicturesA #uploadBox").attr("uploadbgsrc",str2);
        document.body.jsCtrl.ctrl = $("#reportPicturesA #uploadBox")[0];
        document.body.jsCtrl.init();
        $("#reportPicturesA #uploadBox").addClass("comUploadAfter");
    }else{
        //异议产品图片:
        for(var nI = 0 ; nI < document.body.jsLee.filePath.length; nI++ ){
            $("#filePathA img").eq(nI).attr("src",document.body.jsLee.filePath[nI]);
        }
        //质量异议报告图片:
        $("#reportPicturesA").attr("src",str2);
    }

}

function clsAlertBoxCtrl$sure() {//成功弹框确定
    if (this.id == "successJump") {
        jumpUrl("objectionSubmit.html","0000000",0);
        closePopupWin();
    }else if(this.id == "successJump2"){
        jumpUrl("objectionReasearch.html","0000000",0);
    }
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});