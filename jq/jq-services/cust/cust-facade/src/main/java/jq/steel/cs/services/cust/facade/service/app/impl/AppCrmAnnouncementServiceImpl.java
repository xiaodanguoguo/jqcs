package jq.steel.cs.services.cust.facade.service.app.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmAnnouncementVO;
import jq.steel.cs.services.cust.facade.dao.CrmAnnouncementMapper;
import jq.steel.cs.services.cust.facade.model.CrmAnnouncement;
import jq.steel.cs.services.cust.facade.service.app.AppCrmAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:CrmAnnouncement
 *
 * @author
 * @date 2018-9-14
 */
@Service
@Transactional
public class AppCrmAnnouncementServiceImpl implements AppCrmAnnouncementService {

    @Autowired
    private CrmAnnouncementMapper crmAnnouncementMapper;

    public List<CrmAnnouncementVO> selectAll() {
        //List<CrmAnnouncement> crmAnnouncements = crmAnnouncementMapper.selectAll();
        //List<CrmAnnouncementVO> crmAnnouncementVOs = BeanCopyUtil.copyList(crmAnnouncements, CrmAnnouncementVO.class);
        //return crmAnnouncementVOs;
        return null;
    }

    public List<CrmAnnouncementVO> select(CrmAnnouncementVO record) {

        CrmAnnouncement model = BeanCopyUtil.copy(record, CrmAnnouncement.class);
        List<CrmAnnouncement> crmAnnouncements = crmAnnouncementMapper.select(model);
        List<CrmAnnouncementVO> crmAnnouncementVOs = BeanCopyUtil.copyList(crmAnnouncements, CrmAnnouncementVO.class);
        return crmAnnouncementVOs;
    }

    public CrmAnnouncementVO selectByPrimaryKey(Long key) {
        CrmAnnouncement crmAnnouncement = crmAnnouncementMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(crmAnnouncement, CrmAnnouncementVO.class);
    }

    public Integer insert(CrmAnnouncementVO record) {
        CrmAnnouncement crmAnnouncement = BeanCopyUtil.copy(record, CrmAnnouncement.class);
        return crmAnnouncementMapper.insert(crmAnnouncement);

    }

    public Integer insertSelective(CrmAnnouncementVO record) {
        List<CrmAnnouncement> list = crmAnnouncementMapper.selectByTitle(BeanCopyUtil.copy(record, CrmAnnouncement.class));

        if(CollectionUtils.isNotEmpty(list)) {
            return 0;
        }
        CrmAnnouncement crmAnnouncement = BeanCopyUtil.copy(record, CrmAnnouncement.class);
        return crmAnnouncementMapper.insertSelective(crmAnnouncement);
    }

    public Integer updateByPrimaryKey(CrmAnnouncementVO record) {
        CrmAnnouncement crmAnnouncement = BeanCopyUtil.copy(record, CrmAnnouncement.class);
        return crmAnnouncementMapper.updateByPrimaryKey(crmAnnouncement);
    }

    public Integer updateByPrimaryKeySelective(CrmAnnouncementVO record) {
        CrmAnnouncement crmAnnouncement = BeanCopyUtil.copy(record, CrmAnnouncement.class);
        return crmAnnouncementMapper.updateByPrimaryKeySelective(crmAnnouncement);
    }

    public Integer deleteByPrimaryKey(Long key) {
        return crmAnnouncementMapper.deleteByPrimaryKey(key);
    }

    /**
     * IN
     * <foreach collection="keys" open="(" close=")" item="key" separator=",">
     * {key}
     * </foreach>
     */

    public Integer deleteByPrimaryKeys(Set<Long> keys) {
        //return crmAnnouncementMapper.deleteByPrimaryKeys(keys);
        return null;
    }

    public Integer keep(List<CrmAnnouncementVO> crmAnnouncementVOs) {
        int result = 0;
//		Set<Long> keys = new HashSet<>();
        for (CrmAnnouncementVO crmAnnouncementVO : crmAnnouncementVOs) {
            String opt = crmAnnouncementVO.getOpt();
            if ("delete".equals(opt)) {
//				keys.add(crmAnnouncementVO.getId());
                int i = deleteByPrimaryKey(crmAnnouncementVO.getAid());
                if (i > 0) {
                    result++;
                }
            } else if ("update".equals(opt)) {
                int i = updateByPrimaryKeySelective(crmAnnouncementVO);
                if (i > 0) {
                    result++;
                }
            } else if ("insert".equals(opt)) {
                int i = insertSelective(crmAnnouncementVO);
                if (i > 0) {
                    result++;
                }
            }
        }
//		if(!keys.isEmpty())
//			result = result + deleteByPrimaryKeys(keys);
        return result;
    }


    public CrmAnnouncementVO getNewAnnouncement() {
       CrmAnnouncement cam = crmAnnouncementMapper.getNewAnnouncement();
        CrmAnnouncementVO vo = BeanCopyUtil.copy(cam, CrmAnnouncementVO.class);
        return vo;
    }
}