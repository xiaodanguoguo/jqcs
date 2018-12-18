function clsMethodLee(){
    this.requestUrl = {
        "path1":"/coilinfo/splitFind",//主数据列表接口
        "path2":"/split/splitInsert",//拆分提交接口
        "path3":"/orgInfo/selectOrgName"//拆分 送达方input失焦接口
    };
    this.documentLee = null;
    this.jsonData = [];//缓存主数据json数据，拆分操作时复用数据
    this.submitBox = "";
    this.millsheetNo = GetQueryString("millSheetNo");
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //拆分操作
    this.splitOpe = $("#splitOpe");
    //拆分模版box
    this.splitTableTemple = $("#splitTableTemple");
    //返回质证书管理
    this.returnBookList = $("#returnBookList");
    this.parse();

}
function clsMethodLee$parse(){
    limitCodeDeal($("*[limitCode]"),"limitCode");
    $("#tableList")[0].cacheArr = [];
    initplugPath($("#tableList")[0],"standardTableCtrl",this.requestUrl.path1,{"millsheetNo":this.millsheetNo},"POST");
    this.operate();
}

function clsMethodLee$operate(){
    this.splitOpe.on("click",function(){//拆分操作
        initValidate($("#splitBefore")[0]);
        var valiClass=new clsValidateCtrl();
        if(valiClass.validateAll4Ctrl($("#splitBefore")[0]) && $("#tableList")[0].cacheArr.length > 0){
            document.body.jsLee.jsonData = [];
            for(var nI = 0 ; nI < $("#tableList")[0].cacheArr.length; nI++ ){
                var jsonCopy = CopyJson($("#tableList")[0].cacheArr[nI]);
                document.body.jsLee.jsonData.push(jsonCopy);
            }
            splitTableInit();
        }else{
            if($("#tableList")[0].cacheArr.length == 0){
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("请勾选质证书","失败提示");
            }
        }
    });
    this.returnBookList.on("click",function(){//返回质证书管理操作
        if(GetQueryString("jumpType") == 1){
            jumpUrl("qualityBookList.html","0000000",0);
        }else{
            jumpUrl("qualityBookList2.html","0000000",0);

        }
        //jumpUrl("qualityBookList.html","0000000",0);
    });
    //拆分单位失焦事件
    /*$("#spiltCustomerText").on("change",function(){
        getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",{"orgName":$(this).val()},"spiltCustomerTextCheckCallBack(data)");
    });*/
}
function clsMethodLee$refresh(){

}

function clsStandardTableCtrl$before() {//组件渲染数据前操作
    /*if(this.ctrl.id == "tableList"){
        for(var nI = 0 ; nI < this.jsonData.resultData.length; nI++ ){
            var jsonCopy = CopyJson(this.jsonData.resultData[nI]);
            document.body.jsLee.jsonData.push(jsonCopy);
        }
    }*/
}

function clsStandardTableCtrl$progress(jsonItem, cloneRow) {//组件渲染数据循环操作
    if(this.ctrl.id == "tableSplitList"){
        jsonItem.zjishu = "";
        jsonItem.zlosmenge = "";
        if(jsonItem.millsheetType == "M"){
            jsonItem.millsheetType = "Z";
        }else if(jsonItem.millsheetType == "Z"){
            jsonItem.millsheetType = "S";
        }

        //件次失焦操作
        $(cloneRow).find("#zjishu").on("change",function(){
            $(this).val(Number($(this).val()));
            var valiClass=new clsValidateCtrl();
            if(!valiClass.validateAll4Ctrl(this.parentNode)){
                $(this).val("");
            }
            //获取当前行唯一标识
            var markRow = $(this).parents("#cloneRow")[0].jsonData.sid;
            blurCheck($(this),$("*[id=cloneRow] #zjishu"),markRow);
        })

        //重量失焦操作
        $(cloneRow).find("#zlosmenge").on("change",function(){
            var valiClass=new clsValidateCtrl();
            if(!valiClass.validateAll4Ctrl(this.parentNode)){
                $(this).val(0);
            }
            //获取当前行唯一标识
            var markRow = $(this).parents("#cloneRow")[0].jsonData.sid;
            blurCheck($(this),$("*[id=cloneRow] #zlosmenge"),markRow);
        })
    }else if(this.ctrl.id == "tableList"){

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

//拆分操作初始化拆分table
function splitTableInit(){
    var splitTableClone = $("#splitTableTemple").clone(true);
    splitTableClone.attr("id","splitTableClone").show();
    splitTableClone.find("#zchehaoA").val($("#zchehao").val());
    splitTableClone.find("#spiltCustomerTextA").val($("#spiltCustomerText").val());
    $("#splitTableTemple").before(splitTableClone);
    initplugData(splitTableClone.find("#tableSplitList")[0],"standardTableCtrl",document.body.jsLee.jsonData);
    initValidate(splitTableClone[0]);//初始化校验组件
    //initplugPath(splitTableClone.find("#spiltCustomerText")[0],"singleSelectCtrl",document.body.jsLee.requestUrl.path3,{},"POST")//初始化下拉框
    //拆分后提交操作
    splitTableClone.find("#submitSplit").on("click",function(){
        splitSubmit(splitTableClone);
    });
    //取消此拆分后操作
    splitTableClone.find("#resetInput").on("click",function(){
        splitTableClone.find("*[id=cloneRow] #zjishu").each(function () {
            $(this).val(0);
            //获取当前行唯一标识
            var markRow = $(this).parents("#cloneRow")[0].jsonData.sid;
            blurCheck($(this),$("*[id=cloneRow] #zjishu"),markRow);
        });
        splitTableClone.remove();
    });
    /*splitTableClone.find("#spiltCustomerText").on("change",function(){
        document.body.jsLee.spiltCustomerTextDom = $(this);
        getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",{"orgName":$(this).val()},"spiltCustomerTextCheckCallBack(data)");
    });*/
}

function checkForm(dom){//提交tab1
    var boolLock = true;
    initValidate(dom);
    var valiClass=new clsValidateCtrl();
    if(!valiClass.validateAll4Ctrl(dom)){
        /*if(!$(dom).find("#spiltCustomerText option:selected").val()){
            showErrInfoByCustomDiv($(dom).find(".chosen-container"),"请选择送达方");
        }*/
        boolLock = false;
    }else{//校验是否不拆分当前行
        $(dom).find("tr[id=cloneRow]").each(function(){
            if(($(this).find("#zjishu").val() == 0 && $(this).find("#zlosmenge").val() != 0) || ($(this).find("#zjishu").val() != 0 && $(this).find("#zlosmenge").val() == 0)){//如果有一项不拆分，另一项同样不可拆分
                var alertBox=new clsAlertBoxCtrl();
                alertBox.Alert("提交数据中拆分项填写错误(温馨提示：拆分项中件次和重量必须同时为0)","错误提示");
                boolLock = false;
            }
        });
    }
    return boolLock;
}

function blurCheck(dom,doms,markRow){//dom当前事件节点，doms所有这个数据集合，markRow当前节点的唯一标识
    var numTotal = 0;//主数据总数量
    var weightTotal = 0;//主数据总重量
    var numAdd = 0;//拆分总和数量
    var domMain = "";//主数据相关dom节点
    var keyName = dom.attr("id");//当前点击的是件次还是重量的id
    var unitWeight = 0;//单个重量;
    doms.each(function () {
        if($(this).parents("#cloneRow")[0].jsonData.sid == markRow){//找到所有当前的批次或者重量
            if(this.tagName.toLowerCase() == "td"){//如果元素类型是td，则说明是主数据
                numTotal = parseFloat($(this).parents("#cloneRow")[0].jsonData[keyName]);
                weightTotal = parseFloat($(this).parents("#cloneRow")[0].jsonData.zlosmenge);
                if(keyName == "zjishu")
                    unitPrice = (parseFloat($(this).parents("#cloneRow")[0].jsonData.zlosmenge) / parseFloat($(this).parents("#cloneRow")[0].jsonData.zjishu));
                domMain = $(this);
            }else{
                numAdd += parseFloat($(this).val() == "" ? 0 : $(this).val());
            }
        }
    })
    if(numTotal < numAdd){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("拆分子质证书的件次不得大于母质证书件次!","错误提示");
        dom.val(0);
        numAdd = 0;
        doms.each(function () {
            if($(this).parents("#cloneRow")[0].jsonData.sid == markRow){//找到所有当前的批次或者重量
                if(this.tagName.toLowerCase() == "td"){//如果元素类型是td，则说明是主数据
                    numTotal = parseFloat($(this).parents("#cloneRow")[0].jsonData[keyName]);
                    domMain = $(this);
                }else{
                    numAdd += parseFloat($(this).val() == "" ? 0 : $(this).val());
                }
            }
        })
    }
    domMain.html((numTotal - numAdd).toFixed(0));
    domMain.parents("#cloneRow").find("#zlosmenge").html(weightTotal);
    if(keyName == "zjishu"){
        var numNext = dom.val();
        if(dom.val() == "" || dom.val() == null) {
            numNext = 0;
        }
        dom.parents("#cloneRow").find("#zlosmenge").val((unitPrice * parseFloat(numNext)).toFixed(3));
        domMain.parents("#cloneRow").find("#zlosmenge").html((weightTotal - unitPrice*parseFloat(numNext)).toFixed(3));
    }
}

//拆分操作成功回掉函数
function submitSplitCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        //赋值质证书编号   输入框置灰
        document.body.jsLee.submitBox.find("tr[id=cloneRow]").each(function(){
            $(this).find("#millsheetNoName").html(data.rspBody.millsheetNo);
            $(this).find("#zjishu").attr("disabled",true);
            $(this).find("#zlosmenge").attr("disabled",true);
            $(this).find("#submitSplit").attr("disabled",true);
            $(this).find("#resetInput").attr("disabled",true);
        });
       /* document.body.jsLee.submitBox.find("#spiltCustomerText").attr("disabled",true).addClass("changeGary");*/
        //document.body.jsLee.submitBox.find("#spiltCustomerText").attr("initValue",document.body.jsLee.submitBox.find("#spiltCustomerText option:selected").val());
        //initplugPath(document.body.jsLee.submitBox.find("#spiltCustomerText")[0],"singleSelectCtrl",document.body.jsLee.requestUrl.path3,null,"POST")//初始化下拉框
        document.body.jsLee.submitBox.find("#submitSplit").attr("disabled",true).addClass("changeGary");
        document.body.jsLee.submitBox.find("#resetInput").attr("disabled",true).addClass("changeGary");
    }
}

//深拷贝json
function CopyJson(src) {
    var dst = {};
    for (var prop in src) {
        if (src.hasOwnProperty(prop)) {
            dst[prop] = src[prop];
        }
    }
    return dst;
}

//拆分提交操作
function splitSubmit(splitTableClone){
    if(checkForm(splitTableClone[0])){
        document.body.jsLee.submitBox = splitTableClone;
        var isTrue = true;
        var arrParam = [];
        splitTableClone.find("tr[id=cloneRow]").each(function(){
            if($(this).find("#zjishu").val() == 0){
                isTrue = false;
            }
            var jsonParam = {"millsheetType":"","specs":"","zchehao":"","zcpmc":"","zjishu":"","zkunnr":"","zlosmenge":"","zcharg":"","createdDt":""};
            getValue4Desc(jsonParam,$(this)[0]);
            jsonParam.millsheetNo = this.jsonData.millsheetNo;
            jsonParam.spiltCustomer = splitTableClone.find("#spiltCustomerTextA").val();
            jsonParam.zchehao = splitTableClone.find("#zchehaoA").val();
            //jsonParam.zkunweCode = splitTableClone.find("#spiltCustomerText option:selected").html();
            arrParam.push(jsonParam);
        });
        if(isTrue){
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",arrParam,"submitSplitCallBack(data)")
        }else{
            var alertBox=new clsAlertBoxCtrl();
            alertBox.Alert("拆分子质证书件次不能为0！","失败提示");
        }
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

//送达方校验回调
function spiltCustomerTextCheckCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        document.body.jsLee.checkname = true;
    }else{
        alert(data.retDesc);
        $("#spiltCustomerText").val("");
    }
}

$(function(){
    //初始化自己封装方法
    var methodLee = new clsMethodLee();
    document.body.jsLee = methodLee;
    methodLee.init();
});