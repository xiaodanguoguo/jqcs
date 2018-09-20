var titleJson = {
    "project":{
        /******************************************************************************
         * 采购端
         ***************************************************************************/
        "login":{//用户登陆模块
            "login.html":"登陆",
            "register.html":"注册",
            "forgetPassword.html":"忘记密码-确认账号",
            "forgetEdit.html":"忘记密码-选择方式修改密码",
            "passwordNew.html":"忘记密码-重置密码"
        },
        "limitManage":{//权限管理
            "userManage.html":"人员管理",
            "orgFrame.html":"组织管理",
            "resourceManage.html":"资源管理",
            "roleManage.html":"角色管理",
            "accountAudit.html":"账户审核",
            "annDetailPreserve.html":"公告信息维护",
            "annListPreserve.html":"公告维护信息-列表"
        },
        "myBussiness":{//系统管理
            "questionDesc.html":"问卷调查界面展示",
            "questionList.html":"创建调查问卷",
            "questionWrite.html":"填写调查问卷"
        },
        "quality":{//质证书
            "applyBack.html":"申请回退",
            "bookSplit.html":"质证书拆分",
            "checkTrue.html":"防伪验真",
            "historySplit.html":"质证书拆分历史",
            "qualityBookList.html":"质证书管理"
        },
        "appealCompensate":{//产品诉赔
            "agreementBook.html":"协议书页面",
            "objectionDeal.html":"异议处理",
            "objectionFinish.html":"异议结案",
            "objectionReasearch.html":"异议调查",
            "objectionSubmit.html":"异议提报",
            "objectionSubDetail.html":"异议提报-新建",
            "personManage.html":"管理联系信息"
        },
        "recommond":{//产品推介
            "productNewsList.html":"产品推介信息维护",
            "productUpdate.html":"产品推介信息-新增",
            "proTypePreserve.html":"产品类别维护",
            "proTypeList.html":"产品按类别展示",
            "productShow.html":"产品列表展示",
            "productDetail.html":"产品详情展示"
        },
        "homePage":{
            "homePage.html":"",
            "announceMentList.html":"公告列表",
            "announceMentDetail.html":"公告详情"
        }
    }
}

function titleEval(titleJson){//赋值每一个页面的title
    var urlText = document.location.href;
    var titleVar = {};
   /* if(urlText.indexOf("/purchaser/") != -1 && urlText.indexOf("/operater/") == -1){//采购端
        titleVar = titleJson.purchaser;
    }else if(urlText.indexOf("/purchaser/") == -1 && urlText.indexOf("/operater/") != -1){//运营端
        titleVar = titleJson.operater;
    }*/
    titleVar = titleJson.project;
    for(var key in titleVar){
        if(urlText.indexOf(key) != -1){
            var titleVarJson = titleVar[key];
            for(var key2 in titleVarJson){
                if(urlText.indexOf(key2) != -1) {
                    // $("title").eq(0).html("酒钢客户服务平台-" + titleVarJson[key2]);ie8及一下不支持用这种方法给title赋值
                    document.title = "酒钢客户服务平台-" + titleVarJson[key2];
                    if(titleVarJson[key2] == ""){
                        document.title = "酒钢客户服务平台";
                    }
                    //异议提报新建。详情。销售审核等
                    if(urlText.indexOf("htmlType=0") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "酒钢客户服务平台-" +"异议提报-新建";
                    }else if(urlText.indexOf("htmlType=1") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "酒钢客户服务平台-" +"异议提报-修改";
                    }else if(urlText.indexOf("htmlType=2") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "酒钢客户服务平台-" +"异议提报-详情";
                    }else if(urlText.indexOf("htmlType=3") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "酒钢客户服务平台-" +"销售审核";
                    }else if(urlText.indexOf("htmlType=4") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "酒钢客户服务平台-" +"外部调查-录入界面";
                    }else if(urlText.indexOf("htmlType=5") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "酒钢客户服务平台-" +"内部调查";
                    }else if(urlText.indexOf("htmlType=6") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "酒钢客户服务平台-" +"确认书审核";
                    }else if(urlText.indexOf("htmlType=7") != -1 && key2 == "objectionSubDetail.html"){
                        document.title = "酒钢客户服务平台-" +"销售审核详情";
                    }
                    //异议处理协议书
                    if(urlText.indexOf("htmlType=1") != -1 && key2 == "agreementBook.html"){
                        document.title = "酒钢客户服务平台-" +"异议处理-协议书编辑";
                    }else if(urlText.indexOf("htmlType=2") != -1 && key2 == "agreementBook.html"){
                        document.title = "酒钢客户服务平台-" +"协议书审核";
                    }

                    //产品信息维护新增。修改。
                    if(urlText.indexOf("htmlType=1") != -1 && key2 == "productUpdate.html"){
                        document.title = "酒钢客户服务平台-" +"产品推介信息-新增";
                    }else if(urlText.indexOf("htmlType=2") != -1 && key2 == "productUpdate.html"){
                        document.title = "酒钢客户服务平台-" +"产品推介信息-修改";
                    }

                }
            }
        }
    }
}

$(function(){
    titleEval(titleJson)
});