package jq.steel.cs.webapps.op.app.filter;

import com.alibaba.fastjson.JSON;
import com.ebase.core.web.json.JsonRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private Logger logger = LoggerFactory.getLogger(BodyReaderHttpServletRequestWrapper.class);
    private String jsonRequestStr;
    private boolean valid = false;

    BodyReaderHttpServletRequestWrapper(HttpServletRequest request,HttpServletResponse response) throws IOException {
        super(request);
        jsonRequestStr = HttpHelper.getBodyString(request);

        String param = request.getParameter("jsonRequest");
        if(StringUtils.isNotBlank(param)){
            jsonRequestStr = param.replace("'","\"");
            logger.info("parmeter:{}", param);
        }
        if(StringUtils.isNotBlank(jsonRequestStr)){
            JsonRequest jsonRequest = null;
            try {
                jsonRequest = JSON.parseObject(jsonRequestStr, JsonRequest.class);
            }catch (Exception e){
                logger.error("parseObject Exception", e);

            }
            if(null != jsonRequest) {
                String sId = jsonRequest.getReqHeader() == null ? null : jsonRequest.getReqHeader().getSid();

                //设置唯一线程名称
                if (StringUtils.isBlank(sId)) {
                    sId = UUID.randomUUID().toString();
                }
                Thread.currentThread().setName("thread-name_UUID:" + sId);
            }
            if(null != jsonRequestStr){
                jsonRequestStr = JSON.toJSONString(jsonRequest);

            }
        }

        logger.info("请求的param :"+jsonRequestStr);

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException{
        final ByteArrayInputStream bais = new ByteArrayInputStream(jsonRequestStr.getBytes());

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String getReqBody(){
        return this.jsonRequestStr;
    }
}
