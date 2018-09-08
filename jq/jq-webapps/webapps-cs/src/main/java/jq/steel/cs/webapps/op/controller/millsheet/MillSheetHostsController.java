package jq.steel.cs.webapps.op.controller.millsheet;


import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.file.ZipUtils;
import jq.steel.cs.services.cust.api.controller.MillSheetHostsAPI;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.webapps.op.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    /**
     *条件分页查询
     * @param  jsonRequest
     * @return
     *
    * */
    @RequestMapping(value = "/findMillSheetByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<MillSheetHostsVO>>  findMillSheetByPage(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        JsonResponse<PageDTO<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
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
     *预览 返回对象文件地址
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/preview",method = RequestMethod.POST)
    public JsonResponse<List<MillSheetHostsVO>>  preview(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest){
        JsonResponse<List<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<List<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findUrl(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


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
                jsonResponse.setRspBody(serviceResponse.getRetContent());
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
                millSheetPath = serviceResponse.getRetContent().get(0).getMillSheetPath();
                String  millSheetName = serviceResponse.getRetContent().get(0).getMillSheetName();
                response.setHeader("Content-Disposition", "attachment;fileName="+millSheetName);
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
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

}
