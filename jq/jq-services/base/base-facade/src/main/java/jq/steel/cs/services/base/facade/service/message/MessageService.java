package jq.steel.cs.services.base.facade.service.message;

import com.ebase.core.exception.BusinessException;
import jq.steel.cs.services.base.api.vo.MessageVO;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MessageService
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/31 9:33
 */
public interface MessageService {

    /**
     * 发送邮件并插入记录（单人）
     * @param templateCode 模板id
     * @param title 主题
     * @param message
     * @param copyTo 抄送人
     * @param attachment 附件
     * @return
     */
    String sendEmailWithAttachment(String templateCode, String title, MessageVO message, List<String> copyTo,
                                   Map<String, File> attachment)
            throws BusinessException;

    String getRegisterContent(String templateCode, String title, MessageVO message);
}
