package jq.steel.cs.webapps.cs.controller;

import com.ebase.core.log.SearchableLoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @ClassName: test
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/10/13 15:28
 */
@Controller
public class test {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @RequestMapping(value = "/preview")
    public void pdfStreamHandler(HttpServletRequest request, HttpServletResponse response) {
        logger.info("preview--------------------------------------------------" );
        File file = new File("E:/Git/code/jq/jqcs/jq/jq-webapps/webapps-cs/src/main/resources/pdf/C20181013001.pdf");
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
}