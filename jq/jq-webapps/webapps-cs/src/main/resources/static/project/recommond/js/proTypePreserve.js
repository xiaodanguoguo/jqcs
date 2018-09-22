function clsMethodLee(){
    this.requestUrl = {
        "path1":"/product/category/list",//列表接口
        "path2":"/product/category/save",//保存接口
        "path3":"/product/category/submit"//提交接口
    };
    this.documentLee = null;
    this.opeType = "";//操作类型  1——保存  2——提交
    this.init = clsMethodLee$init;//初始化页面的展示内容,绑定dom节点
    this.parse = clsMethodLee$parse;//初始化页面的数据
    this.operate = clsMethodLee$operate;//初始化页面的数据
    this.refresh = clsMethodLee$refresh;//刷新页面的数据
}

function clsMethodLee$init(){
    //保存按钮
    this.btnSave = $("#btnSave");
    //按钮提交
    this.btnSubmit = $("#btnSubmit");
    this.parse();

}
function clsMethodLee$parse(){
    initplugPath($("#tableList")[0],"standardEditTableCtrl",this.requestUrl.path1,null,"POST");
    this.operate();
}

function clsMethodLee$operate(){
    //保存按钮
    this.btnSave.on("click",function () {
        initValidateNoBind();
        var obj = new clsValidateCtrl();
        if(obj.validateAll())
        {
            var jsonData = [];
            $("tr[id='cloneRow']").each(function(){
                jsonData.push(this.jsonData);
            });
            document.body.jsLee.opeType = 1;
            getAjaxResult(document.body.jsLee.requestUrl.path2,"POST",jsonData,"saveCallBack(data)")
        }
    });
    //按钮提交
    this.btnSubmit.on("click",function () {
        initValidateNoBind();
        var obj = new clsValidateCtrl();
        if(obj.validateAll())
        {
            var jsonData = [];
            $("tr[id='cloneRow']").each(function(){
                jsonData.push(this.jsonData);
            });
            document.body.jsLee.opeType = 2;
            getAjaxResult(document.body.jsLee.requestUrl.path3,"POST",jsonData,"saveCallBack(data)")
        }
    });
    $("#btnDelete").on("click",function(){
        if($("#tableList *[id=cloneRow]:visible").length == 0){
            $("#allChk").removeAttr("checked");
        }
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

function clsStandardEditTableCtrl$progress(jsonItem,cloneRow)
{
    if(jsonItem == null)
    {
        return {"opt":"insert","categoryName":"","categoryDesc":""};
    }
    else
    {

    }
    //alert("自己业务定义！");
    if(jsonItem.status == 2){
        $(cloneRow).find("#categoryName").attr("disabled",true);
        $(cloneRow).find("#categoryDesc").attr("disabled",true);
        $(cloneRow).find("#chkCoding").remove()
        $(cloneRow).find("#statusA").html("已提交");
    }else{
        $(cloneRow).find("#statusA").html("待提交");
    }

}

function  saveCallBack(data) {
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        if(document.body.jsLee.opeType == 1){
            alertBox.Alert("保存成功","成功提示",1,"","jumpUrlTip");
        }else {
            alertBox.Alert("提交成功","成功提示",1,"","jumpUrlTip");
        }
    }else{
        initplugPath($("#tableList")[0],"standardEditTableCtrl",this.requestUrl.path1,null,"POST");

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

/*
function clsStandardEditTableCtrl$before() {
    this.jsonData ={"resultData":[
            {
                "categoryDesc":"产品分类描述",
                "createByid":1,
                "updateDt":1,
                "cid":1,
                "parentCid":1,
                "updateByid":1,
                "status":1,
                "createDt":1,
                "updateBy":1,
                "categoryName":"热轧产品",
                "createBy":1
            }
        ]}
}*/
