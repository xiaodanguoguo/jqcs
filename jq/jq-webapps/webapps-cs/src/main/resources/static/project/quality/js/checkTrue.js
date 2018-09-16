var checkDrag = false;
$(function(){
    initValidate($("#checkTrueBox")[0]);//初始化校验组件
    $('#drag').html("");
    $('#drag').drag();
    $("#submitOpe").on("click",function(){//提交操作
        if(checkForm()){
            var jsonParam = {"millSheetNo":$("#millSheetNo").val(),"securityCode":$("#securityCode").val()}
            getAjaxResult("/millsheetcheck/fangWeiMa","POST",jsonParam,"inputCheckCallBack(data)");
        }
    });
    $("#resetOpe").on("click",function(){//重置操作
        $("#checkTrueBox input").val("");
        $("#drag").html("");
        $("#drag").drag();
    });
    $("#uploadCheck").on("click",function(){//上传验证操作
        if($("#fileUrl").val()){
            getAjaxResult("/millsheetcheck/fuJian","POST",{"fileUrl":$("#fileUrl").val()},"uploadCheckCallBack(data)");
        }
    });
});

function checkForm(){//提交tab1
    initValidate($("#checkTrueBox")[0]);
    var valiClass=new clsValidateCtrl();
    if(!valiClass.validateAll4Ctrl("#checkTrueBox") || !checkDrag){
        if(!checkDrag){
            showErrInfoByCustomDiv($("#drag"),"请滑动校验");
        }
        return false;
    }
    return true;
}

function validatePass(handler,text,drag){//滑动校验成功返回函数
    if(handler.parents("*[dragMark=loginMark]").length > 0){
        document.body.jsLee.checkMark = true;
       // $('#drag').html("").drag();
    }else{
        checkDrag = true;
        //$('#dragMark').html("").drag();
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

//防伪码验真接口返回成功回调
function inputCheckCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.rspBody.explain,"成功提示");
    }
}
//上传防伪验真成功回调
function uploadCheckCallBack(data){
    data = JSON.parse(data);
    if(data.retCode == "0000000"){
        var alertBox=new clsAlertBoxCtrl();
        alertBox.Alert(data.rspBody.explain,"成功提示");
    }
}

function clsUploadCtrl$successAfter(ctrl, response)
{
    $("#fileUrl").val(response.rspBody.originalName)
}