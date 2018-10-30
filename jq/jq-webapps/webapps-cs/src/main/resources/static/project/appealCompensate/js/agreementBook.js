function clsMethodLee(){
    this.requestUrl = {
        "path1":"/objectionChuLi/findAll",//数据回显接口
        "path2":"/objectionChuLi/agreementUpdate",//协议书保存/提交/审核 1是保存2是提交3是驳回4是通过
        "path3":"/objectionChuLi/look"//预览接口
    };
    this.documentLee = null;
    this.claimNo = GetQueryString("claimNo") == null ? "":GetQueryString("claimNo");//异议编号
    this.htmlType = GetQueryString("htmlType");//页面类型 1-编辑  2——审核
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //编辑页面保存按钮
    this.firstSave = $("#firstSave");
    //编辑页面提交按钮
    this.firstSubmit = $("#firstSubmit");
    //审核页面拒绝按钮
    this.secondReject = $("#secondReject");
    //审核页面通过按钮
    this.secondPromise = $("#secondPromise");
    //金额输入框
    this.agreementAmount = $("#agreementAmount");


    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    if(this.htmlType == 1){//编辑页面
        $("#box1").show();
        $("#agreementAmountUpper").attr("disabled",true).addClass("changeGary");
        var ue = UE.getEditor('editor');
    }else if(this.htmlType == 2){//审核页面
        $("#secondBox input").attr("disabled",true).addClass("changeGary");
        $("#secondBox textarea").attr("disabled",true).addClass("changeGary");
        $("#box2").show();
        var ue = UE.getEditor('editor');
        ue.ready(function() {
            //不可编辑
            ue.setDisabled();
        });
        //$("#returnPrev").show();
    }else if(this.htmlType == 3){//协议书详情页面
        $("#htmlBox input").attr("disabled",true).addClass("changeGary");
        $("#htmlBox textarea").attr("disabled",true).addClass("changeGary");
        var ue = UE.getEditor('editor');
        ue.ready(function() {
            //不可编辑
            ue.setDisabled();
        });
        //$("#returnPrev").show();
    }
    getAjaxResult(document.body.jsLee.requestUrl.path1,"POST",{"claimNo":this.claimNo},"initHtmlCallBack(data)")
    $("#claimNo").html(this.claimNo);
    $("*[id=wordCheck]").each(function(){
        var a = $(this).next().html();
        var b = $(this).parent().prev().val().length;
        $(this).html(a-b);
    });
    this.operate();
}

function clsMethodLee$operate(){
    //编辑页面保存操作
    this.firstSave.on("click",function(){
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 1;
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",jsonParam,"operateSucCallBack(data)")
        }
    });
    //编辑页面提交操作
    this.firstSubmit.on("click",function(){
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 2;
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",jsonParam,"operateSucCallBack(data)")
        }
    });
    //审核页面拒绝操作
    this.secondReject.on("click",function(){
        $("#rejectReason").addClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 3;
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",jsonParam,"operateSucCallBack(data)")
        }
    });
    //审核页面通过操作
    this.secondPromise.on("click",function(){
        $("#rejectReason").removeClass("required");
        if(boxChecked()){
            var jsonParam = paramJson();
            jsonParam.optionStuts = 4;
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",jsonParam,"operateSucCallBack(data)")
        }
    });
    //金额输入校验
    this.agreementAmount.on("change",function(){
        initValidate(this.parentNode);
        var valiClass=new clsValidateCtrl();
        if(valiClass.validateAll4Ctrl(this.parentNode)){
            $("#agreementAmountUpper").val(changeMoneyToChinese($(this).val()));
        }else{
            $(this).val(0);
            $("#agreementAmountUpper").val(changeMoneyToChinese($(this).val()));
        }
    });

    //返回上一页
    $("#returnPrev").on("click",function(){
        jumpUrl("objectionDeal.html","0000000",0);
    });

    //预览
    $("*[id=firstPreview]").on("click",function(){
        var jsonParam = {"claimNo":"","templateType":5};
        getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",jsonParam,"firstPreviewCallBack(data)")
    });

}
function clsMethodLee$refresh(){

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
function initHtmlCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        setValue4Desc(data.rspBody,$("#htmlBox")[0])//回显数据赋值
       // UE.getEditor('editor').setContent(data.rspBody.fieldConclusion);
        var ue = UE.getEditor('editor');
        ue.ready(function() {//编辑器初始化完成再赋值
            // data.rspBody.inquireInfo = '<p>123123<img style="max-width: 400px; width: 220px; height: 145px;" src="http://192.168.1.115:20183/res/2018/08/jpg/20180830105725_8759.jpg" title="abc.jpg" alt="abc.jpg" width="220" height="145"/></p>';
            ue.setContent(data.rspBody.fieldConclusion);  //赋值给UEditor
        });
    }
}

//预览回调
function firstPreviewCallBack(data) {
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        jumpUrl("../../appealCompensate/html-gulp-www/pdfView.html?pdfUrl=" + data.rspBody.report,"0000000","1");
    }
}

// 金额数字转换大写
function changeMoneyToChinese(money){
    var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); //汉字的数字
    var cnIntRadice = new Array("","拾","佰","仟"); //基本单位
    var cnIntUnits = new Array("","万","亿","兆"); //对应整数部分扩展单位
    var cnDecUnits = new Array("角","分","毫","厘"); //对应小数部分单位
    //var cnInteger = "整"; //整数金额时后面跟的字符
    var cnIntLast = "元"; //整型完以后的单位
    var maxNum = 999999999999999.9999; //最大处理的数字

    var IntegerNum; //金额整数部分
    var DecimalNum; //金额小数部分
    var ChineseStr=""; //输出的中文金额字符串
    var parts; //分离金额后用的数组，预定义
    if( money == "" ){
        return "";
    }
    money = parseFloat(money);
    if( money >= maxNum ){
        $.alert('超出最大处理数字');
        return "";
    }
    if( money == 0 ){
        //ChineseStr = cnNums[0]+cnIntLast+cnInteger;
        ChineseStr = cnNums[0]+cnIntLast
        //document.getElementById("show").value=ChineseStr;
        return ChineseStr;
    }
    money = money.toString(); //转换为字符串
    if( money.indexOf(".") == -1 ){
        IntegerNum = money;
        DecimalNum = '';
    }else{
        parts = money.split(".");
        IntegerNum = parts[0];
        DecimalNum = parts[1].substr(0,4);
    }
    if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换
        zeroCount = 0;
        IntLen = IntegerNum.length;
        for( i=0;i<IntLen;i++ ){
            n = IntegerNum.substr(i,1);
            p = IntLen - i - 1;
            q = p / 4;
            m = p % 4;
            if( n == "0" ){
                zeroCount++;
            }else{
                if( zeroCount > 0 ){
                    ChineseStr += cnNums[0];
                }
                zeroCount = 0; //归零
                ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];
            }
            if( m==0 && zeroCount<4 ){
                ChineseStr += cnIntUnits[q];
            }
        }
        ChineseStr += cnIntLast;
        //整型部分处理完毕
    }
    if( DecimalNum!= '' ){//小数部分
        decLen = DecimalNum.length;
        for( i=0; i<decLen; i++ ){
            n = DecimalNum.substr(i,1);
            if( n != '0' ){
                ChineseStr += cnNums[Number(n)]+cnDecUnits[i];
            }
        }
    }
    if( ChineseStr == '' ){
        //ChineseStr += cnNums[0]+cnIntLast+cnInteger;
        ChineseStr += cnNums[0]+cnIntLast;
    }/* else if( DecimalNum == '' ){
		        ChineseStr += cnInteger;
		        ChineseStr += cnInteger;
		    } */
    return ChineseStr;
}

//校验提交表格
function boxChecked(){
    initValidate($("#htmlBox")[0]);
    var valiClass=new clsValidateCtrl();
    if(!valiClass.validateAll4Ctrl($("#htmlBox")[0])){
        return false;
    }
    return true;
}

//保存提交成功回调
function operateSucCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.retDesc,"成功提示",1,"","successTip");
    }
}

function clsAlertBoxCtrl$sure() {//成功弹框确定
    if (this.id == "successTip") {
        closePopupWin();
        jumpUrl("objectionDeal.html","0000000",0);
    }
}

//提交参数拼接
function paramJson(){
    var jsonParam = {"claimNo":"","agreementAmount":"","agreementAmountUpper":"","agreementContent":"","fiekdConclusion":"","claimDesc":"","agreementNum":"","rejectReason":""};
    getValue4Desc(jsonParam,$("#htmlBox")[0]);
    jsonParam.fieldConclusion = UE.getEditor('editor').getContent();
    return jsonParam;
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