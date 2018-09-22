var millSheetNo = "";
$(function () {
    limitCodeDeal($("*[limitCode]"),"limitCode");
    $("#searchBtn").on("click",function(){//质证书编码查询
        var jsonParam = {"millSheetNo":$("#searchBtnText").val()};
        getAjaxResult("/millsheet/rollbackQuery","POST",jsonParam,"searchCallBack(data)");
    });

    $("#backSure").on("click",function(){//确认退回操作
        if(checkBackSure()){
            var jsonParam = {"millSheetNo":millSheetNo,"regresses":$("#backSureText").val()};
            getAjaxResult("/rebackApply/applyForRetreat","POST",jsonParam,"backSureCallBack(data)");
        }
    });
});
//查询接口返回
function searchCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        millSheetNo = $("#searchBtnText").val();
        $("#searchSuccess").show();
        initValidate($("#searchSuccess")[0]);
        $("#backSureText").val("");
        $("#pdfBox").attr("href",data.rspBody.millSheetPath);
        $("#pdfBox").media({width:600, height:422});
    }
}

//退回接口回调函数
function backSureCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert("电子质证书，编号："+ millSheetNo +"，已经回退","成功提示");
        $("#searchSuccess").hide();
        $("#searchBtnText").val("");
    }
}

//校验退回操作
function checkBackSure(){
    initValidate($("#searchSuccess")[0]);
    var valiClass=new clsValidateCtrl();
    if(!valiClass.validateAll4Ctrl($("#searchSuccess")[0])){
        return false;
    }
    return true;
}

