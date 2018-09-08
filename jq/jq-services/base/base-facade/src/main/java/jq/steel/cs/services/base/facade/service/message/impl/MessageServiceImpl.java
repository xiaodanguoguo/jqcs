package jq.steel.cs.services.base.facade.service.message.impl;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.sensitive.SensitiveWordUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jq.steel.cs.services.base.api.vo.MessageVO;
import jq.steel.cs.services.base.facade.common.RecordStatusCode;
import jq.steel.cs.services.base.facade.dao.MailRecordDao;
import jq.steel.cs.services.base.facade.dao.MailTemplateDao;
import jq.steel.cs.services.base.facade.model.MailRecord;
import jq.steel.cs.services.base.facade.model.MailTemplate;
import jq.steel.cs.services.base.facade.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MessageServiceImpl
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/31 9:33
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    public static final String SUCCESS = "0000000";
    public static final String SYS_EXCEPTION = "500";

    @Value("${spring.mail.username}")
    private String senderName;
    @Value("${spring.mail.host}")
    private String smtp;
    @Value("${spring.mail.password}")
    private String password;

    @Autowired
    private MailRecordDao mailRecordDao;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public String sendEmailWithAttachment(String templateCode, String title, MessageVO message, List<String> copyTo,
                                          Map<String, File> attachment) throws BusinessException {
        String code = SYS_EXCEPTION;
        // 获取邮件模板
        MailTemplate template = this.mailTemplateDao.getTemplateByCode(templateCode);
        if (template == null) {
            code =  "0201001";
            return code;
        }

        // 邮件记录
        MailRecord record = new MailRecord();
        MimeMessage mimeMessage = null;
        String html = null;
        try {
            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(senderName);
            helper.setTo(message.getDestination());
            helper.setSubject(title);

            if (!CollectionUtils.isEmpty(copyTo)) {
                for (String copy : copyTo) {
                    //抄送邮件接收人
                    helper.setCc(copy);
                }
            }

            // 模板内容
            String content = template.getTemplateContent();

            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("contract", content);
            configuration.setTemplateLoader(stringLoader);

            Template configurationTemplate = configuration.getTemplate("contract","utf-8");
            Map<String, Object> variables = message.getVariables();
            html = FreeMarkerTemplateUtils.processTemplateIntoString(configurationTemplate, variables);

            helper.setText(html, true);
            // 获取一个实例
//            SensitiveWordUtil instance = SensitiveWordUtil.getInstance();
//            // 是否含有敏感词
//            boolean containsSensitiveWord
//                    = instance.isContaintSensitiveWord(content, SensitiveWordUtil.minMatchTYpe);
//            if (containsSensitiveWord) {
//                code = "0201002";
//                return code;
//            }

            if (!CollectionUtils.isEmpty(attachment)) {
                // 附件
                Iterator<Map.Entry<String, File>> itr = attachment.entrySet().iterator();
                while (itr.hasNext()) {
                    Map.Entry<String, File> entry = itr.next();
                    helper.addAttachment(entry.getKey(), entry.getValue());
                }
            }

            // 发送邮件
            mailSender.send(mimeMessage);

            record.setStatus(RecordStatusCode.SUCCESS.getCode());
            code = SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            record.setStatus(RecordStatusCode.FAILURE.getCode());
            code = "0201003";
        } finally {
            record.setTemplateId(template.getTemplateId());
            record.setEmail(message.getDestination());
            record.setMailTitle(title);
            record.setMailContent(html);
            record.setSendTime(new Date());
            record.setRecvTime(new Date());
            record.setCreateTime(new Date());

            // 插入记录
            this.mailRecordDao.insert(record);
        }

        return code;
    }

    @Override
    public String getRegisterContent(String templateCode, String title, MessageVO message){
        String html = "注册成功";
        // 获取邮件模板
        MailTemplate template = this.mailTemplateDao.getTemplateByCode(templateCode);
        if (template == null) {
            return html;
        }

        // 邮件记录
        MimeMessage mimeMessage = null;

        try {
            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(senderName);
            helper.setTo(message.getDestination());
            helper.setSubject(title);


            // 模板内容
            String content = template.getTemplateContent();

            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("contract", content);
            configuration.setTemplateLoader(stringLoader);

            Template configurationTemplate = configuration.getTemplate("contract","utf-8");
            Map<String, Object> variables = message.getVariables();
            html = FreeMarkerTemplateUtils.processTemplateIntoString(configurationTemplate, variables);

            helper.setText(html, true);


        } catch (Exception e){
            e.printStackTrace();
        }

        return html;
    }
}
