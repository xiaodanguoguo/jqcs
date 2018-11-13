package jq.steel.cs.webapps.cs.controller.millsheet;


import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.file.ZipUtils;
import com.lowagie2.text.Document;
import com.lowagie2.text.DocumentException;
import com.lowagie2.text.pdf.PdfCopy;
import com.lowagie2.text.pdf.PdfImportedPage;
import com.lowagie2.text.pdf.PdfReader;
import jq.steel.cs.services.base.api.controller.RoleInfoAPI;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.cust.api.controller.MillSheetHostsAPI;
import jq.steel.cs.services.cust.api.controller.MillSheetNeedsAPI;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.api.vo.MillSheetNeedsVO;
import jq.steel.cs.webapps.cs.controller.PdfToPng;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;

/*
* 质证书管理
* by wushibin
* */
@RestController
@RequestMapping("/millsheet")
public class MillSheetHostsController {
    private final static Logger logger = LoggerFactory.getLogger(MillSheetHostsController.class);


    @Autowired
    UploadConfig uploadConfig;

    @Autowired
    private MillSheetHostsAPI millSheetHostsAPI;

    @Autowired
    private RoleInfoAPI roleInfoAPI;

    @Autowired
    private MillSheetNeedsAPI millSheetNeedsAPI;
    /**
     *条件分页查询
     * @param  jsonRequest
     * @return
     *
    * */
    @RequestMapping(value = "/findMillSheetByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<MillSheetHostsVO>>  findMillSheetByPage(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        JsonResponse<PageDTO<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
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

        try {
        ServiceResponse<PageDTO<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findMillSheetByPage(jsonRequest);
        if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } else {
            if (serviceResponse.isHasError()) {
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            }else {
                jsonResponse.setRetCode(serviceResponse.getRetCode());
                jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                return jsonResponse;
            }
        }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    /**
     *条件分页查询（酒钢）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findMillSheetByPage1",method = RequestMethod.POST)
    public JsonResponse<PageDTO<MillSheetHostsVO>>  findMillSheetByPage1(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        JsonResponse<PageDTO<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
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

        try {
            ServiceResponse<PageDTO<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findMillSheetByPage1(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
    /**
     *预览 返回对象文件地址
     * @param
     * @return
     *
     * */
    /*@RequestMapping(value = "/preview",method = RequestMethod.POST)
    public JsonResponse<List<MillSheetHostsVO>>  preview(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest){
        JsonResponse<List<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
        for (MillSheetHostsVO millSheetHostsVO: jsonRequest.getReqBody()){
            millSheetHostsVO.setOrgCode(AssertContext.getOrgCode());
            millSheetHostsVO.setOrgName(AssertContext.getOrgName());
        }
        try {
            ServiceResponse<List<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findUrl(jsonRequest);
            String millSheetUrlL ="";
            String createPdfPath = uploadConfig.getDomain();
            //1是预览  2是打印
            if (jsonRequest.getReqBody().get(0).getOperationType().equals(1)){
                if (jsonRequest.getReqBody().size()>1){
                    //批量预览
                    //从质证书服务器获取文件到本地   重新生成文件
                    String millSheetUrlName = "";
                    for(MillSheetHostsVO millSheetHostsVO :serviceResponse.getRetContent()){
                        //判断是否有特殊需求文件
                        if (millSheetHostsVO.getSpecialNeed().equals("Y")){

                        }
                        String millSheetPath =  millSheetHostsVO.getMillSheetPath();
                        String millSheetName =  millSheetHostsVO.getMillSheetName();
                        String url = createPdfPath + millSheetPath;
                        millSheetUrlL =millSheetHostsVO.getMillSheetUrl();
                        this.saveUrlAs(url,millSheetUrlL,"GET",millSheetName);
                        //转换png
                        String pngName =PdfToPng.pdf2Image(millSheetPath,"/data/kf_web",300);
                        System.out.println("转换png路径"+pngName);
                        String hh1 = pngName.replace("/data/kf_web","/res");
                        String hh = createPdfPath+"/"+hh1;
                        System.out.println("*********************millSheetPath路径*************************************"+hh);
                        millSheetHostsVO.setMillSheetPath(hh);
                    }
                }else {
                    //判断是否有特殊需求文件
                    if( serviceResponse.getRetContent().get(0).getSpecialNeed().equals("Y")){
                        System.out.println("特殊需求文档下载中");
                        //查询特殊需求表（质证书+type为1的）
                        JsonRequest<MillSheetNeedsVO> jsonRequest1 = new JsonRequest<>();
                        MillSheetNeedsVO millSheetNeedsVO =new MillSheetNeedsVO();
                        millSheetNeedsVO.setMillSheetNo(serviceResponse.getRetContent().get(0).getMillSheetNo());
                        millSheetNeedsVO.setType("1");;
                        jsonRequest1.setReqBody(millSheetNeedsVO);
                        ServiceResponse<List<MillSheetNeedsVO>> serviceResponse1 =  millSheetNeedsAPI.findByType(jsonRequest1);
                        //远程下载
                        String millSheetUrl= serviceResponse1.getRetContent().get(0).getSpeNeedUrl();
                        String millSheetPath = serviceResponse1.getRetContent().get(0).getSpeNeedUrl()+"/"+serviceResponse1.getRetContent().get(0).getSpeNeedName();
                        String url = createPdfPath + millSheetPath;
                        String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                        this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);

                        //转换png
                        String pngName =PdfToPng.pdf2Image(millSheetPath,"/data/kf_web",300);
                        System.out.println("转换png路径"+pngName);
                        String hh1 = pngName.replace("/data/kf_web","/res");
                        String hh = createPdfPath+"/"+hh1;
                        serviceResponse.getRetContent().get(0).setMillSheetPath(hh);

                    }else {
                        //从质证书服务器获取文件到本地 返回url
                        String millSheetPath =  serviceResponse.getRetContent().get(0).getMillSheetPath();
                        String millSheetUrl =   serviceResponse.getRetContent().get(0).getMillSheetUrl();
                        String url = createPdfPath + millSheetPath;
                        String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                        this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);

                        //转换png
                        String pngName =PdfToPng.pdf2Image(millSheetPath,"/data/kf_web",300);
                        System.out.println("转换png路径"+pngName);
                        String hh1 = pngName.replace("/data/kf_web","/res");
                        String hh = createPdfPath+"/"+hh1;
                        serviceResponse.getRetContent().get(0).setMillSheetPath(hh);
                    }
                }
            }else {
                //打印
                if (jsonRequest.getReqBody().size()>1){
                    //从质证书服务器获取文件到本地   重新生成文件
                    String millSheetUrlName = "";
                    for(MillSheetHostsVO millSheetHostsVO :serviceResponse.getRetContent()){
                        String millSheetPath =  millSheetHostsVO.getMillSheetPath();
                        String millSheetName =  millSheetHostsVO.getMillSheetName();
                        millSheetUrlName += ";" + millSheetHostsVO.getMillSheetPath();
                        String url = createPdfPath + millSheetPath;
                        millSheetUrlL =millSheetHostsVO.getMillSheetUrl();
                        this.saveUrlAs(url,millSheetUrlL,"GET",millSheetName);
                        millSheetHostsVO.setMillSheetPath(url);
                    }
                    //合并文件
                    millSheetUrlName = millSheetUrlName.substring(1);
                    String savepath =this.sheetNameUrl(millSheetUrlName,millSheetUrlL);
                    String mPath = createPdfPath+savepath;
                    serviceResponse.getRetContent().get(0).setReport(mPath);
                }else {
                    //从质证书服务器获取文件到本地 返回url
                    String millSheetPath =  serviceResponse.getRetContent().get(0).getMillSheetPath();
                    String millSheetUrl =   serviceResponse.getRetContent().get(0).getMillSheetUrl();
                    String url = createPdfPath + millSheetPath;
                    String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                    serviceResponse.getRetContent().get(0).setReport(url);
                }


            }
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }*/

    /*打印减去下载次数*/
    @RequestMapping(value = "/print",method = RequestMethod.POST)
    public JsonResponse<List<MillSheetHostsVO>>  print(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest){
        JsonResponse<List<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
        for (MillSheetHostsVO millSheetHostsVO: jsonRequest.getReqBody()){
            millSheetHostsVO.setOrgCode(AssertContext.getOrgCode());
            millSheetHostsVO.setOrgName(AssertContext.getOrgName());
            millSheetHostsVO.setAcctName(AssertContext.getAcctName());
        }
        try {
            ServiceResponse<List<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findUrl(jsonRequest);
          /*  String millSheetUrlL ="";
            String createPdfPath = uploadConfig.getDomain();
                //打印
                if (jsonRequest.getReqBody().size()>1){
                    //从质证书服务器获取文件到本地   重新生成文件
                    String millSheetUrlName = "";
                    for(MillSheetHostsVO millSheetHostsVO :serviceResponse.getRetContent()){
                        String millSheetPath =  millSheetHostsVO.getMillSheetPath();
                        String millSheetName =  millSheetHostsVO.getMillSheetName();
                        millSheetUrlName += ";" + millSheetHostsVO.getMillSheetPath();
                        String url = createPdfPath + millSheetPath;
                        millSheetUrlL =millSheetHostsVO.getMillSheetUrl();
                        this.saveUrlAs(url,millSheetUrlL,"GET",millSheetName);
                        millSheetHostsVO.setMillSheetPath(url);
                    }
                    //合并文件
                    millSheetUrlName = millSheetUrlName.substring(1);
                    String savepath =this.sheetNameUrl(millSheetUrlName,millSheetUrlL);
                    String mPath = createPdfPath+savepath;
                    serviceResponse.getRetContent().get(0).setReport(mPath);
                }else {
                    //从质证书服务器获取文件到本地 返回url
                    String millSheetPath =  serviceResponse.getRetContent().get(0).getMillSheetPath();
                    String millSheetUrl =   serviceResponse.getRetContent().get(0).getMillSheetUrl();
                    String url = createPdfPath + millSheetPath;
                    String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                    serviceResponse.getRetContent().get(0).setReport(url);
                }*/
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }




    /**
     *预览 返回对象文件地址
     * @param
     * @return
     *
     * */
    @RequestMapping(value = "/preview/{PARAM}")
    public void pdfStreamHandler(HttpServletRequest request, HttpServletResponse response,@PathVariable("PARAM") String param) {
        String acctName =AssertContext.getAcctName();
        List<String> list = JsonUtil.parseObject(param, List.class);
        JsonRequest<List<String>> jsonRequest1 = new JsonRequest();
        jsonRequest1.setReqBody(list);
        logger.info("preview--------------------------------------------------" );
        ServiceResponse<List<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findUrl1(jsonRequest1);
        String millSheetUrlL ="";
        String createPdfPath = uploadConfig.getDomain();
        //打印
        if (jsonRequest1.getReqBody().size()>1){
            //从质证书服务器获取文件到本地   重新生成文件
            String millSheetUrlName = "";
            for(MillSheetHostsVO millSheetHostsVO :serviceResponse.getRetContent()){
                //判断数据是否为null 导致下载文件出错
                if(millSheetHostsVO.getMillSheetName()!=null&&millSheetHostsVO.getMillSheetName()!=null){
                    //修改为已打印并记录日志
                    JsonRequest<List<MillSheetHostsVO>> gg = new JsonRequest<>();
                    List<MillSheetHostsVO> list1 =new  ArrayList<MillSheetHostsVO>();
                    MillSheetHostsVO hh = new MillSheetHostsVO();
                    hh.setMillSheetNo(list.get(0));
                    hh.setOrgCode(AssertContext.getOrgCode());
                    hh.setOrgName(AssertContext.getOrgName());
                    hh.setAcctName(AssertContext.getAcctName());
                    hh.setOperationType(1);
                    list1.add(hh);
                    gg.setReqBody(list1);
                    millSheetHostsAPI.findUrl(gg);

                    String millSheetPath =  millSheetHostsVO.getMillSheetPath();
                    String millSheetName =  millSheetHostsVO.getMillSheetName();
                    if (millSheetHostsVO.getSpecialNeed().equals("Y")){
                        System.out.println("特殊需求文档下载中");
                        JsonRequest<MillSheetNeedsVO> jsonRequest = new JsonRequest<>();
                        MillSheetNeedsVO millSheetNeedsVO =new MillSheetNeedsVO();
                        millSheetNeedsVO.setMillSheetNo(serviceResponse.getRetContent().get(0).getMillSheetNo());
                        millSheetNeedsVO.setType("1");;
                        jsonRequest.setReqBody(millSheetNeedsVO);
                        ServiceResponse<List<MillSheetNeedsVO>> serviceResponse1 =  millSheetNeedsAPI.findByType(jsonRequest);
                        //远程下载
                        String millSheetUrl= serviceResponse1.getRetContent().get(0).getSpeNeedUrl();
                        String millSheetPath1 = serviceResponse1.getRetContent().get(0).getSpeNeedUrl()+"/"+serviceResponse1.getRetContent().get(0).getSpeNeedName();
                        String url = createPdfPath + millSheetPath1;
                        String millSheetName1 =  serviceResponse.getRetContent().get(0).getMillSheetName();
                        this.saveUrlAs(url,millSheetUrl,"GET",millSheetName1);
                        millSheetUrlName += ";" + millSheetPath1;
                    }else {
                        millSheetUrlName += ";" + millSheetHostsVO.getMillSheetPath();
                        String url = createPdfPath + millSheetPath;
                        millSheetUrlL =millSheetHostsVO.getMillSheetUrl();
                        this.saveUrlAs(url,millSheetUrlL,"GET",millSheetName);
                        millSheetHostsVO.setMillSheetPath(url);
                    }
                }

            }
            //合并文件
            millSheetUrlName = millSheetUrlName.substring(1);
            String savepath =this.sheetNameUrl(millSheetUrlName,millSheetUrlL);
            String mPath = createPdfPath+savepath;
            serviceResponse.getRetContent().get(0).setReport(savepath);
        }else {
            if (serviceResponse.getRetContent().get(0).getMillSheetUrl()!=null&& serviceResponse.getRetContent().get(0).getMillSheetName()!=null){
                //修改为已打印并记录日志
                JsonRequest<List<MillSheetHostsVO>> jsonRequest = new JsonRequest<>();
                List<MillSheetHostsVO> list1 =new  ArrayList<MillSheetHostsVO>();
                MillSheetHostsVO millSheetHostsVO = new MillSheetHostsVO();
                millSheetHostsVO.setMillSheetNo(list.get(0));
                millSheetHostsVO.setOrgCode(AssertContext.getOrgCode());
                millSheetHostsVO.setOrgName(AssertContext.getOrgName());
                millSheetHostsVO.setOperationType(1);
                millSheetHostsVO.setAcctName(AssertContext.getAcctName());
                list1.add(millSheetHostsVO);
                jsonRequest.setReqBody(list1);
                millSheetHostsAPI.findUrl(jsonRequest);
                if(serviceResponse.getRetContent().get(0).getSpecialNeed().equals("Y")){
                    System.out.println("特殊需求文档下载中");
                    //查询特殊需求表（质证书+type为1的）
                    MillSheetNeedsVO millSheetNeedsVO =new MillSheetNeedsVO();
                    millSheetNeedsVO.setMillSheetNo(serviceResponse.getRetContent().get(0).getMillSheetNo());
                    millSheetNeedsVO.setType("1");
                    JsonRequest<MillSheetNeedsVO> jsonRequest11 = new JsonRequest<>();
                    jsonRequest11.setReqBody(millSheetNeedsVO);
                    ServiceResponse<List<MillSheetNeedsVO>> serviceResponse1 =  millSheetNeedsAPI.findByType(jsonRequest11);
                    //远程下载
                    String millSheetUrl= serviceResponse1.getRetContent().get(0).getSpeNeedUrl();
                    String millSheetPath = serviceResponse1.getRetContent().get(0).getSpeNeedUrl()+"/"+serviceResponse1.getRetContent().get(0).getSpeNeedName();
                    String url = createPdfPath + millSheetPath;
                    String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                    serviceResponse.getRetContent().get(0).setReport(millSheetPath);
                }else {
                    //从质证书服务器获取文件到本地 返回url
                    String millSheetPath =  serviceResponse.getRetContent().get(0).getMillSheetPath();
                    String millSheetUrl =   serviceResponse.getRetContent().get(0).getMillSheetUrl();
                    String url = createPdfPath + millSheetPath;
                    String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                    serviceResponse.getRetContent().get(0).setReport(millSheetPath);
                }
            }

        }
        String fPath =  serviceResponse.getRetContent().get(0).getReport();
        File file = new File(fPath);
        if (file.exists()){
            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                logger.error("pdf文件处理异常：" + e.getMessage());
            }

        }else{
            return;
        }
    }

    //打印跳转界面返回pdf文件流
    @RequestMapping(value = "/preview1/{PARAM}")
    public void pdfStreamHandler1(HttpServletRequest request, HttpServletResponse response,@PathVariable("PARAM") String param) {
        String acctName =AssertContext.getAcctName();
        List<String> list = JsonUtil.parseObject(param, List.class);
        JsonRequest<List<String>> jsonRequest1 = new JsonRequest();
        jsonRequest1.setReqBody(list);
        logger.info("print--------------------------------------------------" );
        ServiceResponse<List<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findUrl1(jsonRequest1);
        String millSheetUrlL ="";
        String createPdfPath = uploadConfig.getDomain();
        //打印
        if (jsonRequest1.getReqBody().size()>1){
            //从质证书服务器获取文件到本地   重新生成文件
            String millSheetUrlName = "";
            for(MillSheetHostsVO millSheetHostsVO :serviceResponse.getRetContent()){
                String millSheetPath =  millSheetHostsVO.getMillSheetPath();
                String millSheetName =  millSheetHostsVO.getMillSheetName();
                if (millSheetHostsVO.getSpecialNeed().equals("Y")){
                    System.out.println("特殊需求文档下载中");
                    JsonRequest<MillSheetNeedsVO> jsonRequest = new JsonRequest<>();
                    MillSheetNeedsVO millSheetNeedsVO =new MillSheetNeedsVO();
                    millSheetNeedsVO.setMillSheetNo(serviceResponse.getRetContent().get(0).getMillSheetNo());
                    millSheetNeedsVO.setType("1");;
                    jsonRequest.setReqBody(millSheetNeedsVO);
                    ServiceResponse<List<MillSheetNeedsVO>> serviceResponse1 =  millSheetNeedsAPI.findByType(jsonRequest);
                    //远程下载
                    String millSheetUrl= serviceResponse1.getRetContent().get(0).getSpeNeedUrl();
                    String millSheetPath1 = serviceResponse1.getRetContent().get(0).getSpeNeedUrl()+"/"+serviceResponse1.getRetContent().get(0).getSpeNeedName();
                    String url = createPdfPath + millSheetPath1;
                    String millSheetName1 =  serviceResponse.getRetContent().get(0).getMillSheetName();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName1);
                    millSheetUrlName += ";" + millSheetPath1;
                }else {
                    millSheetUrlName += ";" + millSheetHostsVO.getMillSheetPath();
                    String url = createPdfPath + millSheetPath;
                    millSheetUrlL =millSheetHostsVO.getMillSheetUrl();
                    this.saveUrlAs(url,millSheetUrlL,"GET",millSheetName);
                    millSheetHostsVO.setMillSheetPath(url);
                }
            }
            //合并文件
            millSheetUrlName = millSheetUrlName.substring(1);
            String savepath =this.sheetNameUrl(millSheetUrlName,millSheetUrlL);
            String mPath = createPdfPath+savepath;
            serviceResponse.getRetContent().get(0).setReport(savepath);
        }else {
            if(serviceResponse.getRetContent().get(0).getSpecialNeed().equals("Y")){
                System.out.println("特殊需求文档下载中");
                //查询特殊需求表（质证书+type为1的）
                MillSheetNeedsVO millSheetNeedsVO =new MillSheetNeedsVO();
                millSheetNeedsVO.setMillSheetNo(serviceResponse.getRetContent().get(0).getMillSheetNo());
                millSheetNeedsVO.setType("1");
                JsonRequest<MillSheetNeedsVO> jsonRequest11 = new JsonRequest<>();
                jsonRequest11.setReqBody(millSheetNeedsVO);
                ServiceResponse<List<MillSheetNeedsVO>> serviceResponse1 =  millSheetNeedsAPI.findByType(jsonRequest11);
                //远程下载
                String millSheetUrl= serviceResponse1.getRetContent().get(0).getSpeNeedUrl();
                String millSheetPath = serviceResponse1.getRetContent().get(0).getSpeNeedUrl()+"/"+serviceResponse1.getRetContent().get(0).getSpeNeedName();
                String url = createPdfPath + millSheetPath;
                String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                serviceResponse.getRetContent().get(0).setReport(millSheetPath);
            }else {
                //从质证书服务器获取文件到本地 返回url
                String millSheetPath =  serviceResponse.getRetContent().get(0).getMillSheetPath();
                String millSheetUrl =   serviceResponse.getRetContent().get(0).getMillSheetUrl();
                String url = createPdfPath + millSheetPath;
                String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                serviceResponse.getRetContent().get(0).setReport(millSheetPath);
            }
        }
        String fPath =  serviceResponse.getRetContent().get(0).getReport();
        File file = new File(fPath);
        if (file.exists()){
            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                logger.error("pdf文件处理异常：" + e.getMessage());
            }

        }else{
            return;
        }
    }

    private String sheetNameUrl(String millSheetUrlName,String millSheeturl) {
        String[] names = millSheetUrlName.split(";");
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).toString();
        // 后续需要优化合成后的pdf文件路径问题
        String savepath = millSheeturl +"/"+ now + ".pdf";
        mergePdfFiles(names, savepath);
        return savepath;
    }
    /**
     * 将多个pdf文件合并成1个pdf文件
     *
     * @param files
     *            文件路径数组
     * @param savepath
     *            新生成的pdf文件路径
     */
    private void mergePdfFiles(String[] files, String savepath) {
        try {
            Document document = new Document(new PdfReader(files[0]).getPageSize(1));

            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));

            document.open();

            for (int i = 0; i < files.length; i++) {
                PdfReader reader = new PdfReader(files[i]);

                int n = reader.getNumberOfPages();

                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * @功能 下载临时素材接口
     * @param filePath 文件将要保存的目录
     * @param method 请求方法，包括POST和GET
     * @param url 请求的路径
     * @return
     */

    public static File saveUrlAs(String url,String filePath,String method,String fileName){
        System.out.println("filePath---->"+filePath);
        //创建不同的文件夹目录
        File file=new File(filePath);
        //判断文件夹是否存在
        if (!file.exists())
        {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try
        {
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {

                filePath += "/";

            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath+"/"+fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }

        return file;

    }

   /* public static void main(String[] args)
    {
        String photoUrl = "http://10.1.213.138/data/millpath/2018-09-19/Y1809050201.pdf";
        String fileName = photoUrl.substring(photoUrl.lastIndexOf("/"));
        System.out.println("fileName---->"+fileName);
        String filePath = "e:";
        File file = saveUrlAs(photoUrl, filePath + fileName,"GET");
        System.out.println("Run ok!/n<BR>Get URL file " + file);

    }*/


    /**
     *  回退查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/rollbackQuery",method = RequestMethod.POST)
    public JsonResponse<MillSheetHostsVO>  rollbackQuery(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        JsonResponse<MillSheetHostsVO> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<MillSheetHostsVO> serviceResponse = millSheetHostsAPI.rollbackQuery(jsonRequest);
            if (serviceResponse.getRetContent().getTrue()){

                if (serviceResponse.getRetContent().getIsReback().equals("Y")){
                    jsonResponse.setRetCode("1111111");
                    jsonResponse.setRetDesc("此质证书已回退过，不可再次回退");
                }else {
                    //从质证书服务器获取文件到本地 返回url
                    String createPdfPath = uploadConfig.getDomain();
                    String millSheetPath =  serviceResponse.getRetContent().getMillSheetPath();
                    String url = createPdfPath + millSheetPath;
                    String millSheetName =  serviceResponse.getRetContent().getMillSheetName();
                    String millSheetUrl = serviceResponse.getRetContent().getMillSheetUrl();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                    serviceResponse.getRetContent().setMillSheetPath(url);
                    jsonResponse.setRspBody(serviceResponse.getRetContent());
                }
            }else {
                jsonResponse.setRetCode("1111111");
                jsonResponse.setRetDesc("请验证质证书编号"+serviceResponse.getRetContent().getMillSheetNo());
            }

        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     * 质证书下载
     * @param  jsonRequest
     * @return
     *
     * */
  @RequestMapping(value = "/downFile",method = RequestMethod.POST)
    public void downFile(@RequestParam("name") String jsonRequest,HttpServletResponse response){
        try {
            List<String> list = JsonUtil.parseObject(jsonRequest,List.class);
            JsonRequest<List<String>> jsonRequest1 = new JsonRequest();
            jsonRequest1.setReqBody(list);
            String orgName = AssertContext.getOrgName();
            String orgCode = AssertContext.getOrgCode();
            //ServiceResponse<List<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.downFile(jsonRequest1);
            ServiceResponse<List<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.downFile(jsonRequest1);
            String millSheetPath ="";
            if (serviceResponse.getRetContent().size()>1){
                for(MillSheetHostsVO millSheetHostsVO :serviceResponse.getRetContent()){

                    //下载记录日志
                    JsonRequest<List<MillSheetHostsVO>> hh = new JsonRequest<>();
                    List<MillSheetHostsVO> list1 =new  ArrayList<MillSheetHostsVO>();
                    MillSheetHostsVO gg = new MillSheetHostsVO();
                    gg.setMillSheetNo(millSheetHostsVO.getMillSheetNo());
                    gg.setAcctName(AssertContext.getAcctName());
                    gg.setOperationType(3);
                    list1.add(gg);
                    hh.setReqBody(list1);
                    millSheetHostsAPI.findUrl(hh);

                    if (millSheetHostsVO.getSpecialNeed().equals("Y")){
                        System.out.println("特殊需求文档下载中");
                        String createPdfPath = uploadConfig.getDomain();
                        MillSheetNeedsVO millSheetNeedsVO =new MillSheetNeedsVO();
                        millSheetNeedsVO.setMillSheetNo(serviceResponse.getRetContent().get(0).getMillSheetNo());
                        millSheetNeedsVO.setType("1");
                        JsonRequest<MillSheetNeedsVO> jsonRequest12 = new JsonRequest<>();
                        jsonRequest12.setReqBody(millSheetNeedsVO);
                        ServiceResponse<List<MillSheetNeedsVO>> serviceResponse1 =  millSheetNeedsAPI.findByType(jsonRequest12);
                        //远程下载
                        String millSheetUrl= serviceResponse1.getRetContent().get(0).getSpeNeedUrl();
                        String millSheetPath1 = serviceResponse1.getRetContent().get(0).getSpeNeedUrl()+"/"+serviceResponse1.getRetContent().get(0).getSpeNeedName();
                        String url = createPdfPath + millSheetPath1;
                        String millSheetName1 =  serviceResponse.getRetContent().get(0).getMillSheetName();
                        this.saveUrlAs(url,millSheetUrl,"GET",millSheetName1);
                        millSheetHostsVO.setMillSheetPath(millSheetPath1);
                    }else {
                        String createPdfPath = uploadConfig.getDomain();
                        String millSheetPathB =  millSheetHostsVO.getMillSheetPath();
                        String url = createPdfPath + millSheetPathB;
                        String millSheetName =  millSheetHostsVO.getMillSheetName();
                        String millSheetUrl = serviceResponse.getRetContent().get(0).getMillSheetUrl();
                        this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                    }
                }
                List<File> fileList = new ArrayList<>();
                for (int i=0;i<serviceResponse.getRetContent().size();i++){
                    fileList.add(new File(serviceResponse.getRetContent().get(i).getMillSheetPath()));
                }
                try {
                    response.setHeader("Content-Disposition", "attachment;fileName="+"zhibaoshu.zip");
                    FileOutputStream fos2 = new FileOutputStream(new File("zhibaoshu.zip"));
                    ZipUtils.toZip(fileList, fos2);
                }catch (Exception e){
                    e.printStackTrace();
                }
                millSheetPath = "zhibaoshu.zip";

            }else {

                //下载记录日志
                JsonRequest<List<MillSheetHostsVO>> hh = new JsonRequest<>();
                List<MillSheetHostsVO> list1 =new  ArrayList<MillSheetHostsVO>();
                MillSheetHostsVO millSheetHostsVO = new MillSheetHostsVO();
                millSheetHostsVO.setMillSheetNo(serviceResponse.getRetContent().get(0).getMillSheetNo());
                millSheetHostsVO.setAcctName(AssertContext.getAcctName());
                millSheetHostsVO.setOperationType(3);
                list1.add(millSheetHostsVO);
                hh.setReqBody(list1);
                millSheetHostsAPI.findUrl(hh);

                //单个文件下载
                String createPdfPath = uploadConfig.getDomain();
                if ( serviceResponse.getRetContent().get(0).getSpecialNeed().equals("Y")){
                    System.out.println("特殊需求文档下载中");
                    MillSheetNeedsVO millSheetNeedsVO =new MillSheetNeedsVO();
                    millSheetNeedsVO.setMillSheetNo(serviceResponse.getRetContent().get(0).getMillSheetNo());
                    millSheetNeedsVO.setType("1");
                    JsonRequest<MillSheetNeedsVO> jsonRequest12 = new JsonRequest<>();
                    jsonRequest12.setReqBody(millSheetNeedsVO);
                    ServiceResponse<List<MillSheetNeedsVO>> serviceResponse1 =  millSheetNeedsAPI.findByType(jsonRequest12);
                    //远程下载
                    String millSheetUrl= serviceResponse1.getRetContent().get(0).getSpeNeedUrl();
                    String millSheetPath1 = serviceResponse1.getRetContent().get(0).getSpeNeedUrl()+"/"+serviceResponse1.getRetContent().get(0).getSpeNeedName();
                    String url = createPdfPath + millSheetPath1;
                    String millSheetName1 =  serviceResponse.getRetContent().get(0).getMillSheetName();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName1);

                    millSheetPath = millSheetPath1;
                    String  millSheetName = serviceResponse.getRetContent().get(0).getMillSheetName();
                    response.setHeader("Content-Disposition", "attachment;fileName="+millSheetName);
                }else {
                    String millSheetPathA =  serviceResponse.getRetContent().get(0).getMillSheetPath();
                    String url = createPdfPath + millSheetPathA;
                    String millSheetUrl = serviceResponse.getRetContent().get(0).getMillSheetUrl();
                    String fileName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                    this.saveUrlAs(url,millSheetUrl,"GET",fileName);
                    //配置请求头
                    millSheetPath = serviceResponse.getRetContent().get(0).getMillSheetPath();
                    String  millSheetName = serviceResponse.getRetContent().get(0).getMillSheetName();
                    response.setHeader("Content-Disposition", "attachment;fileName="+millSheetName);
                }
            }
            //String path = servletContex.getRealPath("/");

            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");

            //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫zms.jpg,这里是设置名称)

            ServletOutputStream out=null;
            FileInputStream inputStream=null;
            File file = new File(millSheetPath);
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
        } catch (BusinessException e) {
            logger.error("下载报错", e);
            e.printStackTrace();
        }

    }


    //诉赔提报界面校验质证书编号是否正确
    @RequestMapping(value = "/findIsTrue",method = RequestMethod.POST)
    public JsonResponse<MillSheetHostsVO>  findIsTrue(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        logger.info("参数",JsonUtil.toJson(jsonRequest));
        JsonResponse<MillSheetHostsVO> jsonResponse = new JsonResponse<>();
        try {

            ServiceResponse<MillSheetHostsVO> serviceResponse = millSheetHostsAPI.findIsTrue(jsonRequest);
            if(serviceResponse.getRetContent().getTrue()){
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            }else {
                jsonResponse.setRetCode("1111111");
                jsonResponse.setRetDesc(serviceResponse.getRetContent().getCheckInstructions());
            }

        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    //查询条件校验钢卷编号是否正确
    @RequestMapping(value = "/checkCoil",method = RequestMethod.POST)
    public JsonResponse<MillSheetHostsVO>  checkCoil(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        logger.info("参数",JsonUtil.toJson(jsonRequest));
        //zcharg-----批/板/卷号
        JsonResponse<MillSheetHostsVO> jsonResponse = new JsonResponse<>();
        try {

            ServiceResponse<MillSheetHostsVO> serviceResponse = millSheetHostsAPI.checkCoil(jsonRequest);
            if(serviceResponse.getRetContent().getTrue()){
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            }else {
                jsonResponse.setRetCode("1111111");
                jsonResponse.setRetDesc(serviceResponse.getRetContent().getCheckInstructions());
            }

        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }



    //下载操作手册
    @RequestMapping(value = "/downOperationManual",method = RequestMethod.POST)
    public void downOperationManual(@RequestParam("name") String jsonRequest,HttpServletResponse response){
        try {

            //String fileName = "酒钢客服平台用户操作手册.pdf";
            String fileName = URLEncoder.encode("酒钢客服平台用户操作手册.pdf", "UTF-8");
            //orgTypr 1销售公司 2 一级代理 3贸易商 4终端客户
            String operationManual="";
            System.out.println("AssertContext.getOrgType()"+AssertContext.getOrgType());
            if (AssertContext.getOrgType().equals("1")){
                  operationManual = "/data/model/酒钢客服平台用户操作手册-销售公司-1.pdf";
            }else if(AssertContext.getOrgType().equals("3") || AssertContext.getOrgType().equals("2")||AssertContext.getOrgType().equals("4")){
                  operationManual = "/data/model/酒钢客服平台用户操作手册-终端客户-1.pdf";
            }else if (AssertContext.getOrgType().equals("5")){
                  operationManual = "/data/model/酒钢客服平台用户操作手册-全-1.pdf";
            }

            //配置请求头
            response.setHeader("Content-Disposition", "attachment;fileName="+fileName);

            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            //response.setCharacterEncoding("ISO8859-1");

            //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫zms.jpg,这里是设置名称)

            ServletOutputStream out=null;
            FileInputStream inputStream=null;
            File file = new File(operationManual);
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
        } catch (BusinessException e) {
            logger.error("下载报错", e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    /**
     * 打印次数/下载次数+1
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/updateNumber",method = RequestMethod.POST)
    public JsonResponse<Integer>  updateNumber(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        for (MillSheetHostsVO millSheetHostsVO: jsonRequest.getReqBody()){
            millSheetHostsVO.setOrgCode(AssertContext.getOrgCode());
            millSheetHostsVO.setOrgName(AssertContext.getOrgName());
            millSheetHostsVO.setAcctName(AssertContext.getAcctName());
        }
        try {
            ServiceResponse<Integer> serviceResponse = millSheetHostsAPI.updateNumber(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
