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
import jq.steel.cs.services.cust.api.controller.MillSheetHostsAPI;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
                    //从质证书服务器获取文件到本地   重新生成文件
                    String millSheetUrlName = "";
                    for(MillSheetHostsVO millSheetHostsVO :serviceResponse.getRetContent()){
                        String millSheetPath =  millSheetHostsVO.getMillSheetPath();
                        millSheetUrlName += ";" + millSheetHostsVO.getMillSheetPath();
                        String millSheetName =  millSheetHostsVO.getMillSheetName();
                        String url = createPdfPath + millSheetPath;
                        millSheetUrlL =millSheetHostsVO.getMillSheetUrl();
                        this.saveUrlAs(url,millSheetUrlL,"GET",millSheetName);
                        millSheetHostsVO.setMillSheetPath(url);
                    }
                    //合并文件
                    millSheetUrlName.substring(1);
                    String savepath =this.sheetNameUrl(millSheetUrlName,millSheetUrlL);
                    serviceResponse.getRetContent().get(0).setMillSheetPath(createPdfPath + savepath);
                }else {
                    //从质证书服务器获取文件到本地 返回url
                    String millSheetPath =  serviceResponse.getRetContent().get(0).getMillSheetPath();
                    String millSheetUrl =   serviceResponse.getRetContent().get(0).getMillSheetUrl();
                    String url = createPdfPath + millSheetPath;
                    String millSheetName =  serviceResponse.getRetContent().get(0).getMillSheetName();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
                    serviceResponse.getRetContent().get(0).setMillSheetPath(url);
                    serviceResponse.getRetContent().get(0).setReport(millSheetUrl);
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
                    millSheetUrlName.substring(1);
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
                    String createPdfPath = uploadConfig.getDomain();
                    String millSheetPathB =  millSheetHostsVO.getMillSheetPath();
                    String url = createPdfPath + millSheetPathB;
                    String millSheetName =  millSheetHostsVO.getMillSheetName();
                    String millSheetUrl = serviceResponse.getRetContent().get(0).getMillSheetUrl();
                    this.saveUrlAs(url,millSheetUrl,"GET",millSheetName);
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
                //单个文件下载
                String createPdfPath = uploadConfig.getDomain();
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

}
