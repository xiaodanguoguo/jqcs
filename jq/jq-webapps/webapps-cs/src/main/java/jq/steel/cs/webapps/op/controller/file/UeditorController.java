package jq.steel.cs.webapps.op.controller.file;

import com.ebase.utils.file.FileUtil;
import com.google.common.collect.Maps;
import jq.steel.cs.webapps.op.controller.file.ueditor.ActionEnter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: ueditor 文件上传
 */
@RestController
@RequestMapping("/ueditor")
public class UeditorController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UploadConfig uploadConfig;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, String> upload(HttpServletRequest request, MultipartFile upfile,HttpServletResponse resp) throws IOException {

        resp.setContentType("text/plain");
        Map<String, String> result = Maps.newHashMap();
        try {
            byte[] bytes = upfile.getBytes();

            //取得原始文件名
            String originalFilename = upfile.getOriginalFilename();
            // 扩展名
            String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

            // 文件名
            String fileName = FileUtil.getFileName(fileExt);
            // 文件上传目录
            String dirName = FileUtil.getDirName(upfile.getBytes());
            // 文件上传真实物理路径
            String uploadPath = uploadConfig.getUploadPath()+dirName;

            FileUtil.uploadFile(upfile.getBytes(), uploadPath, fileName);


            result.put("url", uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern() + dirName + fileName);
            result.put("size", String.valueOf(bytes.length));
            result.put("type", fileExt);
            result.put("state", "SUCCESS");
            result.put("title",originalFilename);
            result.put("original",originalFilename);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("state", "Fail");
            result.put("error", e.getMessage());
            return result;
        }

    }
}
