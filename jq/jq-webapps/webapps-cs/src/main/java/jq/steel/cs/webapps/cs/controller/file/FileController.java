package jq.steel.cs.webapps.cs.controller.file;

import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import com.ebase.utils.file.FileUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.web.json.JsonResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@RestController
@RequestMapping("/file")
@MultipartConfig
public class FileController {
    static final Logger logger = SearchableLoggerFactory.getDefaultLogger();
    @Autowired
    UploadConfig uploadConfig;

    //@CrossOrigin
    @PostMapping("/upload")
    public JsonResponse<FileInfo> upload(@RequestParam("file") MultipartFile[] file, HttpServletRequest request) throws IOException {
        JsonResponse<FileInfo> jsonResponse = new JsonResponse<FileInfo>();
        if (null != file && file.length > 0) {
            //遍历并保存文件
            for (MultipartFile files : file) {
                if (file != null) {
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
}
