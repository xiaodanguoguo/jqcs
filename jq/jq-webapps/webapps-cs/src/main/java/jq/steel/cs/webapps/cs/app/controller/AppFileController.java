package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.file.FileUtil;
import jq.steel.cs.webapps.cs.controller.file.FileInfo;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@RestController
@RequestMapping("/app/file")
@MultipartConfig
public class AppFileController {
    static final Logger logger = SearchableLoggerFactory.getDefaultLogger();
    @Autowired
    UploadConfig uploadConfig;

    private static final long SIZE = 5 * 1048576;

    //@CrossOrigin
    @PostMapping("/upload")
    public JsonResponse<FileInfo> upload(@RequestParam("file") MultipartFile[] file, HttpServletRequest request) throws IOException {
        JsonResponse<FileInfo> jsonResponse = new JsonResponse<FileInfo>();
        if (null != file && file.length > 0) {
            //遍历并保存文件
            for (MultipartFile files : file) {
                if (file != null) {
                    if (files.getSize() > SIZE) {
                        jsonResponse.setRetCode("0000002");
                        return jsonResponse;
                    }
                    try {
                        //取得原始文件名
                        String originalFilename = files.getOriginalFilename();
                        // 扩展名
                        String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

                        // 文件名
                        String fileName = FileUtil.getFileName(fileExt);
                        // 文件上传目录
                        String dirName = FileUtil.getDirName(files.getBytes());
                        // 文件上传真实物理路径
                        String uploadPath = uploadConfig.getUploadPath()+dirName;

                        FileUtil.uploadFile(files.getBytes(), uploadPath, fileName);

                        // 域名 + res + 图片路径
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setOriginalName(originalFilename);
                        // 数据库保存的相对路径
                        fileInfo.setFilePath(dirName+fileName);
                        // 文件访问绝对路径
                        fileInfo.setViewUrl(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern() + dirName+fileName);

                        jsonResponse.setRspBody(fileInfo);
                    } catch (Exception e) {
                        logger.error("上传异常：",e);
                        jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                    }
                }
            }
        } else {
            logger.info("上传文件不能为空");
        }

        return jsonResponse;
    }

    @PostMapping("/uploads")
    public JsonResponse<List<FileInfo>> uploads(MultipartFile[] file, HttpServletRequest request) throws IOException {
        JsonResponse<List<FileInfo>> jsonResponse = new JsonResponse<List<FileInfo>>();
        List<FileInfo> list = new ArrayList<>();
        if (null != file && file.length > 0) {
            //遍历并保存文件
            for (MultipartFile files : file) {
                if (file != null) {
                    if (files.getSize() > SIZE) {
                        jsonResponse.setRetCode("0000002");
                        return jsonResponse;
                    }
                    try {
                        //取得原始文件名
                        String originalFilename = files.getOriginalFilename();
                        // 扩展名
                        String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

                        // 文件名
                        String fileName = FileUtil.getFileName(fileExt);
                        // 文件上传目录
                        String dirName = FileUtil.getDirName(files.getBytes());
                        // 文件上传真实物理路径
                        String uploadPath = uploadConfig.getUploadPath()+dirName;

                        FileUtil.uploadFile(files.getBytes(), uploadPath, fileName);

                        // 域名 + res + 图片路径
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setOriginalName(originalFilename);
                        // 数据库保存的相对路径
                        fileInfo.setFilePath(dirName+fileName);
                        // 文件访问绝对路径
                        fileInfo.setViewUrl(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern() + dirName+fileName);

                        list.add(fileInfo);
                    } catch (Exception e) {
                        logger.error("上传异常：",e);
                        jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                    }
                }
            }
        } else {
            logger.info("上传文件不能为空");
        }

        jsonResponse.setRspBody(list);
        logger.info(JsonUtil.toJson(list));
        return jsonResponse;
    }

//    @RequestMapping("/uploads")
//    public static void mutiFileUpload(HttpServletRequest req){
//        String fileName="";
//         Collection<String> list = new ArrayList<String>();
//        // 文件上传真实物理路径
//        String uploadPath = uploadConfig.getUploadPath();
//
//        try {
//            //接收多个或者单个的图片
//            Collection<Part>  parts = req.getParts();
//
//            for(Part part:parts){
//                String head = part.getHeader("content-disposition");
//                fileName = getFileName(head);
//
//                //避免中间的文件为空，导致无法写入接收到文件
//                if(fileName == null || fileName.equals("")){
//                    continue;
//                }
//
//                String path = req.getServletContext().getRealPath(File.separator+"img");// 文件上传真实物理路径
//
//
//
//                //String path = "E:"+File.separator+"imges";
//                //写入文件
//                part.write(path+File.separator+fileName);
//                list.add("img"+File.separator+fileName);
//
//
//            }
//
//            req.setAttribute("list", list);
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ServletException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    //过滤part.header得到文件名称
//    private static String getFileName(String disp){
//
//        /*ie 用于ie游览器
//        String str = "form-data; name=\"photo\"; filename=\"C:"+File.separator+"Users"+File.separator+"Administrator"+File.separator+"Desktop"+File.separator+"xxx.png\"";
//        */
//
//
//        /*
//         * goole 和 fixfox游览器
//         * String str = "form-data; name=\"photo\"; filename=\"xxx.png\"";
//         */
//
//        String[] s = disp.split("=");
//
//
//
//        //得到"C:\Users\Administrator\Desktop\xxx.png" 和"xxx.png"
//        String st = s[2];
//        int lastSep =st.lastIndexOf(File.separator)  ;
//        int lastIndex = st.lastIndexOf("\"");
//
//        //当没有File.separator时，获取lastSep=1,其它为lastSep+1
//        lastSep=lastSep==-1 ? lastSep=1:lastSep+1;
//
//        String fileName = st.substring(lastSep,lastIndex);
//
//
//        return fileName;
//
//    }
}
