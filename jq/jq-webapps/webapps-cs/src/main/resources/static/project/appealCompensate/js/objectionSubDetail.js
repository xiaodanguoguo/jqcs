function clsMethodLee(){
    this.requestUrl = {
        "path1":"/md/findItemsByTypeId",//异议产品下拉接口（产品大类接口）
        "path2":"/orderUnit/findByPage",//订货单位列表
        "path3":"/unitOfUse/findByPage",//使用单位列表
        "path4":"/objectionTiBao/findDetails",//异议提报新建/修改/详情/销售审核数据回显 1是新建2是修改3是详情4销售审核
        "path5":"/millsheet/findIsTrue",//质证书编号检验
        "path6":"/objectionTiBao/update",//异议提报新增/修改/保存/销售审核（保存、驳回、通过）1审核保存操作2驳回操作3通过操作4修改保存5新增保存
        "path7":"/objectionChuLi/download",//详情页面下载功能
        "path8":"/objectionTiBao/printing",//详情页面打印功能
        "path9":"/objectionDiaoCha/findDetails",//异议调查外部调查内部调查回显数据|确认书审核   1是外部调查 2是内部调查 3确认书审核
        "path10":"/objectionDiaoCha/update",//内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）
                                            //1外部保存2外部跟踪3外部提交4确认书通过5确认书审核
        "path11":"/file/upload",//上传
        "path12":"/coilinfo/findIsTrue",//批板卷号填写校验操作，校验成功带出数据
        "path13":"/objectionDiaoCha/updateInside",//1内部保存2内部提交
        "path14":"/orderUnit/orderUnitInsert",//订货单位新增/修改
        "path15":"/unitOfUse/unitOfUseInsert",//使用单位新增/修改
        "path16":"/orderUnit/customerInfo",//订货单位获取当前登录人信息
        "path17":"/objectionChuLi/look",//预览接口
        "path18":"/objectionDiaoCha/exportExcel"//内部调查下载接口
    };
    this.operateType = "";//操作类型 0-订货单位新增 1-订货单位编辑  2-使用单位新增  3-使用单位编辑
    this.opeDom = "";
    this.documentLee = null;
    this.htmlType = GetQueryString("htmlType");//判断页面类型0——新建 1修改 2——详情  3——销售审核  4——外部调查  5——内部调查 6-确认书审核  7-销售审核详情页面
    this.claimNo = GetQueryString("claimNo") == null ? "":GetQueryString("claimNo");//异议编号
    this.claimNoPreview = GetQueryString("claimNo") == null ? "":GetQueryString("claimNo");//异议编号
    this.filePath = [];//异议产品图片:
    this.reportPictures = "";//质量异议报告图片:
    this.selectedMark = {//判断发生异议单位和异议类别是否选中
        "selMark1":true,
        "selMark2":true
    };
    this.loginerNews = {//初始化页面会有客户联系编号	客户单位名称
        "customerId":"",//客户联系编号
        "customerName":""//客户单位名称
    };
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
    //this.secondPrint = $("#secondPrint");
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
    //新增使用单位
    this.firstCreateperson2 = $("#firstCreateperson2");
    //新增质量异议提报人员信息
    this.firstCreateperson = $("#firstCreateperson");
    //弹框确定按钮
    this.newsChageSure = $("#newsChageSure");
    //弹框取消按钮
    this.newsChageCancel = $("#newsChageCancel");
    //返回上一页
    this.returnPrev = $("#returnPrev");
    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    //判断页面类型（进行显示隐藏dom节点）0——新建 1修改 2——详情  3——销售审核  4——外部调查  5——内部调查 6-确认书审核（外部调查不可编辑）7-销售审核详情（销售审核不可编辑） 8-外部调查报告详情  9-内部调查报告详情
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
            //this.returnPrev.show();
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
            $("#rejectReason").removeAttr("disabled").removeClass("changeGary");
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
            $("#amountOfUse").removeAttr("disabled").removeClass("changeGary");
            $("#productDt").removeClass("required");
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
            $(".box4").parents("#parentBox").css("height","167px");
            $(".box4").remove();
            //$(".box5").remove();
            $(".box6").remove();
            $("#logisticsProcess").attr("disabled",true).addClass("changeGary");
            $("#endProcessingTech").attr("disabled",true).addClass("changeGary");
            $("#inSearchBox input").attr("disabled",true).addClass("changeGary");
            $("#fieldConclusionA").attr("disabled",true).addClass("changeGary");
            $("#parentBox").prev().html("异议投诉描述:");
            $("#productDt").removeAttr("disabled").removeClass("changeGary");
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
            $(".box4:last").remove();
            $("#submitBox input").attr("disabled",true).addClass("changeGary");
            $("#submitBox textarea").attr("disabled",true).addClass("changeGary");
            $("#rejectReason").removeAttr("disabled").removeClass("changeGary");
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
            //this.returnPrev.show();
            getAjaxResult(document.body.jsLee.requestUrl.path4,"POST",{"claimNo":this.claimNo,"optionType":4},"htmlInit(data)");//数据回显操作
            break;
        case 8://外部调查报告详情
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
            $(".box4:last").remove();
            $("#submitBox input").attr("disabled",true).addClass("changeGary");
            $("#submitBox textarea").attr("disabled",true).addClass("changeGary");
            //this.returnPrev.show();
            getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",{"claimNo":this.claimNo,"optionType":1},"htmlInit2(data)");//数据回显操作
            break;
            break;
        case 9://内部调查报告详情
            $("#boxFirst").remove();
            $(".box01").remove();
            $(".box013").remove();
            $(".box2").remove();
            $(".box23").remove();
            $(".box237").remove();
            $(".box3").remove();
            $(".box4").parents("#parentBox").css("height","167px");
            $(".box4").remove();
            $(".box5:last").remove();
            $(".box6").remove();
            $("#parentBox").prev().html("异议投诉描述:");
            $("#submitBox input").attr("disabled",true).addClass("changeGary");
            $("#submitBox textarea").attr("disabled",true).addClass("changeGary");
            //this.returnPrev.show();
            getAjaxResult(document.body.jsLee.requestUrl.path9,"POST",{"claimNo":this.claimNo,"optionType":2},"htmlInit2(data)");//数据回显操作
            break;
    }
    initValidate($("#submitBox")[0]);
    $("#claimNo").val(document.body.jsLee.claimNo);
    getAjaxResult(document.body.jsLee.requestUrl.path16,"POST",{},"loginerNewsCallBack(data)");
    //赋值内容主体高度，显示footer
    if($("#js-leftNavBar").height() > $("#js-loader").height()){
        $(".og-head-main").css("height",$("#js-leftNavBar").height()+"px");
    }else{
        $(".og-head-main").css("height",$("#js-loader").height()+"px");
    }
    $("*[id=wordCheck]").each(function(){
        var a = $(this).next().html();
        var b = $(this).parent().prev().val().length;
        $(this).html(a-b);
    });
    this.operate();
}

function clsMethodLee$operate(){
    this.dinghuoCreate.on("click",function () {//订货单位添加操作
        openWin('650', '500', 'listPopup', true);
        $("#dinghuoListPopup").show();
        $("#shiyongListPopup").hide();
        $("#listPopupTitle").html("订货单位列表");
        initplugPath($("#dinghuoListPopupBox")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path2,{"customerId":$("#customerId").val()},"POST");
    });
    this.shiyongCreate.on("click",function () {//使用单位添加操作
        openWin('650', '500', 'listPopup', true);
        $("#dinghuoListPopup").hide();
        $("#shiyongListPopup").show();
        $("#listPopupTitle").html("使用单位列表");
        initplugPath($("#shiyongListPopupBox")[0],"standardTableCtrl",document.body.jsLee.requestUrl.path3,{"customerId":$("#customerId").val()},"POST");

    });
    //质证书编号改变，进行接口后台判断
    this.millSheetNo.on("change",function () {
        getAjaxResult(document.body.jsLee.requestUrl.path5,"POST",{"millSheetNo":$(this).val()}," millSheetNoCheckCallBack(data)");
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
        document.body.jsLee.opeDom = "firstSave";
        if(boxChecked()){
            var jsonParam = paramJson();
            if(document.body.jsLee.htmlType == 0){//新建
                jsonParam.optionStuts = 5;
            }else if(document.body.jsLee.htmlType == 1){//修改
                jsonParam.optionStuts = 4;
            }
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POST",jsonParam,"firstSaveCallBack(data)")
        }else{

        }
    });

    //详情页面打印操作
   /* this.secondPrint.on("click",function(){

    });*/
    //详情页面下载操作
    this.secondDownload.on("click",function(){
        var importParam = "name=" + JSON.stringify({"claimNos":[document.body.jsLee.claimNo],"templateType":2});
        $.download(requestUrl + document.body.jsLee.requestUrl.path7, importParam, "POST");
    });
    //销售管理保存操作
    this.thirdSave.on("click",function(){
        document.body.jsLee.opeDom = "thirdSave";
        $("#rejectReason").removeClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 1;
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POST",jsonParam,"firstSaveCallBack(data)")
        }
    });
    //销售管理通过操作
    this.thirdPromise.on("click",function(){
        document.body.jsLee.opeDom = "thirdPromise";
        $("#rejectReason").removeClass("required");
        if(boxChecked() && !$("#rejectReason").val()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 3;
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POST",jsonParam,"firstSaveCallBack(data)")

        }else if($("#rejectReason").val()){
            showErrInfoByCustomDiv($("#rejectReason")[0],"请清空驳回原因！");
            /*var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请清空驳回原因！","失败提示");*/
        }
    });
    //销售管理驳回操作
    this.thirdReject.on("click",function(){
        document.body.jsLee.opeDom = "thirdReject";
        $("#rejectReason").addClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 2;
            getAjaxResult(document.body.jsLee.requestUrl.path6,"POST",jsonParam,"firstSaveCallBack(data)")
        }
    });

    //外部调查调查结束按钮
    this.forthsubmit.on("click",function () {
        $("#followReason").removeClass("required");
        document.body.jsLee.opeDom = "forthsubmit";
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 3;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POST",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //外部调查跟踪按钮
    this.forthFoolow.on("click",function () {
        $("#followReason").addClass("required");
        document.body.jsLee.opeDom = "forthFoolow";
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 2;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POST",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //外部调查保存按钮
    this.forthSave.on("click",function () {
        $("#followReason").removeClass("required");
        document.body.jsLee.opeDom = "forthSave";
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 1;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POST",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //内部调查保存按钮
    this.fifthSave.on("click",function () {
        document.body.jsLee.opeDom = "fifthSave";
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 1;
            getAjaxResult(document.body.jsLee.requestUrl.path13,"POST",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //内部调查提交按钮
    this.fifthSubmit.on("click",function () {
        document.body.jsLee.opeDom = "fifthSubmit";
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 2;
            getAjaxResult(document.body.jsLee.requestUrl.path13,"POST",jsonParam,"secondSaveCallBack(data)")
        }
    });
    //确认书审核通过操作
    this.sixthPromise.on("click",function(){
        document.body.jsLee.opeDom = "sixthPromise";
        $("#rejectReason").removeClass("required");
        if(boxChecked() && !$("#rejectReason").val()){
            var jsonParam = paramJson();
            jsonParam.optionType = 4;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POST",jsonParam,"secondSaveCallBack(data)")

        }else if($("#rejectReason").val()){
            showErrInfoByCustomDiv($("#rejectReason")[0],"请清空驳回原因！");
            /*var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("请清空驳回原因！","失败提示");*/
        }
    });
    //确认书审核驳回操作
    this.sixthReject.on("click",function(){
        document.body.jsLee.opeDom = "sixthReject";
        $("#rejectReason").addClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionType = 5;
            getAjaxResult(document.body.jsLee.requestUrl.path10,"POST",jsonParam,"secondSaveCallBack(data)")
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

    this.firstCreateperson.on("click",function(){//质量异议提报人员信息新增
        openWinShow(0);
    });
    this.firstCreateperson2.on("click",function(){//使用单位列表新增
        openWinShow(2);
    });
    this.newsChageCancel.on("click",function(){//人员新增弹框取消操作
        closePopupWin();
    });

    this.newsChageSure.on("click",function(){//人员新增弹框确认操作
        if(document.body.jsLee.operateType == 0 || document.body.jsLee.operateType == 1){
            var domCon = $("#orderUnitPopup")[0];
        }else{
            var domCon = $("#userUnitPopup")[0];
        }
        initValidate(domCon);
        var valiClass=new clsValidateCtrl();
        if(valiClass.validateAll4Ctrl(domCon)){
            ajaxExecute(document.body.jsLee.operateType);
        }
    });

    this.returnPrev.on("click",function () {//返回上一页
        switch (Number(document.body.jsLee.htmlType)) {
            //判断页面类型（进行显示隐藏dom节点）0——新建 1修改 2——详情  3——销售审核  4——外部调查  5——内部调查 6-确认书审核（外部调查不可编辑）7-销售审核详情（销售审核不可编辑） 8-外部调查报告详情  9-内部调查报告详情
            case 0://新建
            case 1://修改
            case 2://详情
            case 3://销售审核
                jumpUrl("objectionSubmit.html","0000000",0);
                break;
            case 4://外部调查
            case 5://内部调查
            case 8://外部调查详情
            case 9://内部调查详情
            case 7://销售审核详情
                jumpUrl("objectionReasearch.html","0000000",0);
                break;
            case 6://确认书审核
                jumpUrl("objectionDeal.html","0000000",0);
                break;
        }
    });

    //预览操作
    $("#firstPreview").on("click",function(){
        if(document.body.jsLee.claimNoPreview == ""){
            alert("请先保存再进行预览");
        }else{
            var jsonParam = {"claimNo":document.body.jsLee.claimNoPreview};
            switch (Number(document.body.jsLee.htmlType)){//类型
                case 0:
                    jsonParam.templateType = 1;
                    break;
                case 1:
                    jsonParam.templateType = 1;
                    break;
                case 4:
                    jsonParam.templateType = 2;
                    break;
                case 5:
                    jsonParam.templateType = 3;
                    break;
                case 6:
                    jsonParam.templateType = 4;
                    break;
            }
            getAjaxResult(document.body.jsLee.requestUrl.path17,"POST",jsonParam,"firstPreviewCallBack(data)")
        }

    });
    //内部调查下载
    $("#downLoadExcel").on("click",function(){
        var arrP = []
        var importParam = "name=" + JSON.stringify(arrP.push(document.body.jsLee.claimNo));
        $.download(requestUrl + document.body.jsLee.requestUrl.path18, importParam, "POST");
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

            if(data.rspBody.claimType){
                document.body.jsLee.selectedMark.selMark2 = true;
            }
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
        if(document.body.jsLee.htmlType == 4 || document.body.jsLee.htmlType == 8){//外部调查  或者外部调查详情
            //富文本数据回显
            var ue = UE.getEditor('editor');
            ue.ready(function() {//编辑器初始化完成再赋值
                ue.setContent(data.rspBody.inquireInfoAll);  //赋值给UEditor
            });
            var ue2 = UE.getEditor('editor2');
            ue2.ready(function() {//编辑器初始化完成再赋值
                ue2.setContent(data.rspBody.fieldConclusion);  //赋值给UEditor
            });
            if(document.body.jsLee.htmlType == 8){
                var ue = UE.getEditor('editor');
                ue.ready(function() {
                    //不可编辑
                    ue.setDisabled();
                    UE.dom.domUtils.setStyles(ue.body, {
                        'opacity': 0.3,'filter': 'alpha(opacity=30)'
                    });
                });
                var ue2 = UE.getEditor('editor2');
                ue2.ready(function() {
                    //不可编辑
                    ue2.setDisabled();
                    UE.dom.domUtils.setStyles(ue2.body, {
                        'opacity': 0.3,'filter': 'alpha(opacity=30)'
                    });
                });
            }
        }else if(document.body.jsLee.htmlType == 5 || document.body.jsLee.htmlType == 9){//内部调查  或者内部调查报告详情
            //富文本数据回显
            var ue = UE.getEditor('editor');
            ue.ready(function() {//编辑器初始化完成再赋值
                if(data.rspBody.productionProcessAll == null){
                    data.rspBody.productionProcessAll = "";
                }
                ue.setContent(data.rspBody.productionProcessAll);  //赋值给UEditor
            });
            if(document.body.jsLee.htmlType == 9){
                var ue = UE.getEditor('editor');
                ue.ready(function() {
                    //不可编辑
                    ue.setDisabled();
                    UE.dom.domUtils.setStyles(ue.body, {
                        'opacity': 0.3,'filter': 'alpha(opacity=30)'
                    });
                });
            }
        }else if(document.body.jsLee.htmlType == 6){//确认书审核
            //富文本数据回显
            var ue = UE.getEditor('editor');
            ue.ready(function() {//编辑器初始化完成再赋值
                ue.setContent(data.rspBody.inquireInfoAll);  //赋值给UEditor
            });
            var ue2 = UE.getEditor('editor2');
            ue2.ready(function() {//编辑器初始化完成再赋值
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

//预览回调
function firstPreviewCallBack(data) {
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        jumpUrl("../../appealCompensate/html-gulp-www/pdfView.html?pdfUrl=" + data.rspBody.report,"0000000","1");
    }
}

function boxChecked(){
    initValidate($("#submitBox")[0]);
    var valiClass=new clsValidateCtrl();
    if(document.body.jsLee.htmlType == 4 || document.body.jsLee.htmlType == 6){//外部调查  确认书审核
        if(!valiClass.validateAll4Ctrl($("#submitBox")[0]) || !document.body.jsLee.selectedMark.selMark2 || !UE.getEditor('editor').getContent() || !UE.getEditor('editor2').getContent()){
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
        if(!valiClass.validateAll4Ctrl($("#submitBox")[0]) || !document.body.jsLee.selectedMark.selMark2 /*|| !UE.getEditor('editor').getContent()*/){
            if(!document.body.jsLee.selectedMark.selMark2){
                showErrInfoByCustomDiv($("#claimTypeA")[0],"请选择异议类别!");
            }
            /*if(!UE.getEditor('editor').getContent()){
                showErrInfoByCustomDiv($("#productionProcessAllA")[0],"请输入生产工艺过程调查!");
            }*/
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
    var jsonParam = {"claimNo":"","productName":"","millSheetNo":"","customerId":"","customerName":"","custAddr":"","custEmpNo":"","custTel":"","lastUserId":"","lastUser":"","lastUserAddr":"","createEmpNo":"","lastUserTel":"","battenPlateNo":"","designation":"","used":"","contractNo":"","contractVolume":"","specs":"","originalWeight":"","orderNo":"","originalCarNo":"","contractUnitPrice":"","objectionNum":"","endProcessingTech":"","claimDesc":"","claimReason":"","rejectReason":"","productDt":"","shift":"","userRequirement":"","handingSuggestion":"","inquireInfo":"","fieldConclusion":"","claimVerdict":"","improvement":"","amountOfUse":"","proProblem":"","logisticsProcess":"","objectionConfirmation":"","salesManagerSuggests":"","productionProcessInvestigati":"","surfaceStructure":"","originalJudgementResult":"","qualityGrade":"","agreementContent":"","agreementAmount":"","arrivalTime":"","followReason":"","investigationUnit":"","responsibilityUnit":""};
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
        //富文本参数拼接
        jsonParam.inquireInfoAll = UE.getEditor('editor').getContent();
        jsonParam.inquireInfoText = UE.getEditor('editor').getPlainTxt();
        jsonParam.inquireInfoPhoto = splitImg(UE.getEditor('editor').getContent());
        jsonParam.fieldConclusion = UE.getEditor('editor2').getContent();
        jsonParam.fieldConclusionText = UE.getEditor('editor2').getPlainTxt();
        jsonParam.fieldConclusionPhoto = splitImg(UE.getEditor('editor2').getContent());
        //异议分类和缺陷分类获取值（同一个字段）
        jsonParam.claimType = $("#claimTypeA").attr("markCode");
    }else if(document.body.jsLee.htmlType == 5){//内部调查
        jsonParam.productionProcessAll = UE.getEditor('editor').getContent();
        jsonParam.productionProcessText = UE.getEditor('editor').getPlainTxt();
        jsonParam.productionProcessPhoto = splitImg(UE.getEditor('editor').getContent());
        //异议分类和缺陷分类获取值（同一个字段）
        jsonParam.claimType = $("#claimTypeA").attr("markCode");
    }else if(document.body.jsLee.htmlType == 6){//确认书审核
        //富文本参数拼接
        jsonParam.inquireInfoAll = UE.getEditor('editor').getContent();
        jsonParam.inquireInfoText = UE.getEditor('editor').getPlainTxt();
        jsonParam.inquireInfoPhoto = splitImg(UE.getEditor('editor').getContent());
        jsonParam.fieldConclusion = UE.getEditor('editor2').getContent();
        jsonParam.fieldConclusionText = UE.getEditor('editor2').getPlainTxt();
        jsonParam.fieldConclusionPhoto = splitImg(UE.getEditor('editor2').getContent());
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
        garyDom(document.body.jsLee.opeDom)
        if(document.body.jsLee.htmlType == 0){
            document.body.jsLee.claimNoPreview = data.rspBody.claimNo;
        }
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示");
        /*var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示",1,"","successJump");*/
        //jumpUrl("objectionSubmit.html","0000000",0);
    }
}

function secondSaveCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        garyDom(document.body.jsLee.opeDom);
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示");
        /*var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示",1,"","successJump2");*/
        /*if(document.body.jsLee.opeDom == "sixthReject" || document.body.jsLee.opeDom == "sixthPromise"){
            jumpUrl("objectionDeal.html","0000000",0);
        }else{
            jumpUrl("objectionReasearch.html","0000000",0);
        }*/
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
        document.body.jsLee.reportPictures = "";
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
        if(str2){
            $("#reportPicturesA #uploadBox").attr("uploadbgsrc",str2);
            document.body.jsCtrl.ctrl = $("#reportPicturesA #uploadBox")[0];
            document.body.jsCtrl.init();
            $("#reportPicturesA #uploadBox").addClass("comUploadAfter");
        }

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

function garyDom(src){//按钮操作后置灰
    document.body.jsLee[src].attr("disabled",true).addClass("changeGary");
}

//富文本提取src
function splitImg(strAll){
    var arrAll = [];
    $("body").append("<span id='spanspan' style='display: none'></span>");
    $("#spanspan").html(strAll);
    $("#spanspan img").each(function(){
        arrAll.push($(this).attr("src"));
    });
    $("#spanspan").remove();
    return arrAll.join(";");
}

//打开弹框操作
function openWinShow(type){//type操作类型 0-订货单位新增 1-订货单位编辑  2-使用单位新增  3-使用单位编辑   ||    sid当前编辑行的sid
    //清空内容
    $("#textChangeBox input[type=text]").val("");
    $("#textChangeBox input[type=checkbox]").removeAttr("checked");
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
    }
    //显示ul判断
    if(type == 0 || type == 1){//订货单位
        $("#orderUnitPopup").show();
        $("#userUnitPopup").hide();
        $("#orderUnitPopup #customerName").val(document.body.jsLee.loginerNews.customerName);
    }else{//使用单位
        $("#orderUnitPopup").hide();
        $("#userUnitPopup").show();
    };
    openWin(950,350,'textChangeBox',true);
    document.body.jsLee.operateType = type;
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
        jsonParam.sid = "";
        document.body.jsLee.firstCreateperson[0].cacheJson = jsonParam;
        getAjaxResult(document.body.jsLee.requestUrl.path14,"POST",jsonParam,"updateSubmitCallBack(data)");
    }else{//使用单位
        var jsonParam = {"createEmpNo":"","lastUser":"","lastUserAddr":"","lastUserTel":"",};
        getValue4Desc(jsonParam,$("#userUnitPopup")[0]);
        if($("#userUnitPopup #defaultFlag").is(":checked")){
            jsonParam.defaultFlag = "Y";
        }else{
            jsonParam.defaultFlag = "N";
        }
        jsonParam.sid = "";
        document.body.jsLee.firstCreateperson2[0].cacheJson = jsonParam;
        getAjaxResult(document.body.jsLee.requestUrl.path15,"POST",jsonParam,"updateSubmitCallBack(data)");
    }
}

//新增人员信息回调函数
function updateSubmitCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        closePopupWin();
        if(document.body.jsLee.operateType == 0 || document.body.jsLee.operateType == 1) {//订货单位
            document.body.jsLee.firstCreateperson[0].cacheJson.customerId = data.rspBody.customerId;
            setValue4Desc(document.body.jsLee.firstCreateperson[0].cacheJson,$("#dinghuoCreate").next()[0])//赋值
        }else{
            document.body.jsLee.firstCreateperson2[0].cacheJson.lastUserId = data.rspBody.lastUserId;
            setValue4Desc(document.body.jsLee.firstCreateperson2[0].cacheJson,$("#shiyongCreate").next()[0])//赋值
        }
    }
}

function loginerNewsCallBack(data){//获取登陆人信息回调
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        document.body.jsLee.loginerNews.customerId = data.rspBody.customerId;
        document.body.jsLee.loginerNews.customerName = data.rspBody.customerName;
    }
}

function checkWords(a,len){
    var str = a.value;
    if(str.length > len){
        a.value=a.value.substring(0,len);
    }
    $(a).next().find("i:first").html(len - a.value.length);
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});