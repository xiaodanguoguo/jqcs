package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.file.ZipUtils;
import jq.steel.cs.services.base.api.controller.RoleInfoAPI;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.cust.api.controller.ObjectionChuLiAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import jq.steel.cs.webapps.cs.controller.objection.CreatePdf;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app/objectionChuli")
public class AppObjectionChuLiController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private ObjectionChuLiAPI objectionChuLiAPI;

    @Autowired
    UploadConfig uploadConfig;

    @Autowired
    private RoleInfoAPI roleInfoAPI;


    @RequestMapping(value = "/findByPageForApp",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionChuLiVO>> findByPage(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        JsonResponse<PageDTO<ObjectionChuLiVO>> jsonResponse = new JsonResponse<>();
        logger.info("异议处理列表", JsonUtil.toJson(jsonRequest));
        try {
            /**
             * 异议状态	 CLAIM_STATE CRM_CLAIM_INFO
             NEW 新建,PRESENT已提报 ,
             ACCEPTANCE 已受理 ,
             BEJECT 已驳回 ,
             INVESTIGATION调查中,
             HANDLE处理中 ,
             END已结案 ,
             EVALUATE 已评价 ,
             ADOPT销售审核通过
             */
            jsonRequest.getReqBody().setClaimState("HANDLE");
            /**
             * 调查报告状态 INQUIRE_STATE CRM_CLAIM_INFO
             OUTSTART 外部调查开始 ,
             TRACK 已跟踪 ,
             OUTEND 外部调查结束 ,
             INSTART 内部调查开始 ,
             INEND调查结束 ,
             CONFIRM 已确认
             */
            jsonRequest.getReqBody().setInquireState("CONFIRM");

            /**
             * 协议书状态   AGREEMENT_STATE  CRM_AGREEMENT_INFO
             <
             编辑中:EDIT,
             已完成:COMPLETE,
             已审核:EXAMINE
             >
             */
            jsonRequest.getReqBody().setAgreementState("COMPLETE");



            //2019-03-21 wushibin 增加厂权限
            String acctId = AssertContext.getAcctId();
            ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
            List<String> list = new ArrayList<>();
            for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
                list.add(roleInfoVO.getRoleCode());
            }
            if (list.size()>0){
                jsonRequest.getReqBody().setDeptCodes(list);
            }else {
                jsonRequest.getReqBody().setDeptCodes(null);
            }

            ServiceResponse<PageDTO<ObjectionChuLiVO>> byPage = objectionChuLiAPI.findByPageForApp(jsonRequest);
            PageDTO<ObjectionChuLiVO> retContent = byPage.getRetContent();
            jsonResponse.setRspBody(retContent);


        } catch (BusinessException e) {
            logger.error("异议处理列表", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     *  协议书保存/提交/审核
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/agreementUpdate",method = RequestMethod.POST)
    public JsonResponse<Integer> agreementUpdate(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        logger.info("协议书保存/提交/审核", JsonUtil.toJson(jsonRequest));
        try {
            ServiceResponse<Integer> result = objectionChuLiAPI.agreementUpdate(jsonRequest);
            Integer retContent = result.getRetContent();
            jsonResponse.setRspBody(retContent);
        } catch (BusinessException e) {
            logger.error("协议书保存/提交/审核", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    /**
     * 预览润乾 实时生成pdf并且返回url地址
     *
     * 1、质量异议报告--新建异议保存后，就出现预览报告按钮，可以调用异议报告模板，查看报告及数据。
     2、外部调查报告--外部调查保存后，就出现预览报告按钮，可以调用外部调查报告模板，查看报告及数据。
     3、内部调查报告-1-内部调查保存后，就出现预览报告按钮，可以调用内部调查报告模板，查看报告及数据。
     4、确认书--确认书审核界面，加预览按钮，可以直接查看确认书报告。
     5、协议书--协议书编辑和协议书审核界面，可以直接直接点击“预览”按钮来查看协议书模板及数据。
     * */
    @RequestMapping(value = "/look",method = RequestMethod.GET)
        public void look(@RequestParam("claimNo") String claimNo ,  @RequestParam("templateType") int templateType, HttpServletResponse response){

        ObjectionChuLiVO vo = new ObjectionChuLiVO();
        vo.setClaimNo(claimNo);
        vo.setTemplateType(templateType);
        JsonRequest<ObjectionChuLiVO> jsonRequest = new JsonRequest();
        jsonRequest.setReqBody(vo);

        logger.info("参数", JsonUtil.toJson(jsonRequest));
        CreatePdf createPdf = new CreatePdf();
        String pathPattern = uploadConfig.getPathPattern();
        JsonResponse<ObjectionChuLiVO>  jsonResponse = new JsonResponse<>();
        String createPdfPath = uploadConfig.getModelUrl();
        jsonRequest.getReqBody().setReport(createPdfPath);
        try {
            ServiceResponse<ObjectionChuLiVO> serviceResponse = objectionChuLiAPI.look(jsonRequest);
            String report = "";
            if (jsonRequest.getReqBody().getTemplateType()==1){
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "Y.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"yiyibaogao");
                String hh1 = report1.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
            }else if(jsonRequest.getReqBody().getTemplateType()==2) {
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "W.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"waibudiaocha");
                String hh1 = report1.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
            }else if(jsonRequest.getReqBody().getTemplateType()==3){
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "N.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"neibudiaocha");
                String hh1 = report1.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
            }else if(jsonRequest.getReqBody().getTemplateType()==4){
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "Q.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"waibudiaocha");
                String hh1 = report1.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
                download(report , response);
            }else if(jsonRequest.getReqBody().getTemplateType()==5){
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "X.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"xieyishu");
                String hh1 = report1.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
                download(report , response);
            }
        } catch (BusinessException e) {
            logger.error("打印报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
    }

    public static void download(String report , HttpServletResponse response ){
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫zms.jpg,这里是设置名称)
        ServletOutputStream out=null;
        FileInputStream inputStream=null;
        File file = new File(report);
        try {
            inputStream  = new FileInputStream(file);

            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();

            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1){
                b = inputStream.read(buffer);
                if(b != -1){
                    out.write(buffer,0,b);//4.写到输出流(out)中
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(inputStream!=null){
                    inputStream.close();
                }
                if(out!=null){
                    out.close();
                    out.flush();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
