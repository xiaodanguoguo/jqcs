$(function(){
    $("#returnBookList").on("click",function(){//返回质证书管理操作
        jumpUrl("qualityBookList.html","0000000",0);
    });
    var paramJson = {"millSheetNo":GetQueryString("millSheetNo")};
    getAjaxResult("/split/splitFindAll","POST",paramJson,"historyInitCallBack(data)")
});
function historyInitCallBack(data) {//初始化页面
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var jsonData = data.rspBody[0];
        var tableListArr = jsonData.millCoilInfoVOS;
        var splitTableTempleArrs = jsonData.crmMillSheetSplitApplyVOS;
        //初始化主数据
        initplugData($("#tableList")[0],"standardTableCtrl",tableListArr);
        //初始化拆分数据
        for(var nI = 0 ; nI < splitTableTempleArrs.length; nI++ ){
            var splitTableClone = $("#splitTableTemple").clone(true);
            splitTableClone.attr("id","splitTableClone").show();
            $("#splitTableTemple").before(splitTableClone);
            initplugData(splitTableClone.find("#tableSplitList")[0],"standardTableCtrl",splitTableTempleArrs[nI].crmMillSheetSplitInfoVOS);
        }
    }
}

//已有数组，初始化插件;
function initplugData(docm,comType,data){
    $(docm).attr("comType",comType);
    docm.data = {"rspBody":{"resultData":data}};
    document.body.jsCtrl.ctrl = docm;
    document.body.jsCtrl.init();
}