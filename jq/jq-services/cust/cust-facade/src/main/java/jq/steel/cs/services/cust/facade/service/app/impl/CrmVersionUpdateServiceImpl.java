package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmVersionUpdateVO;
import jq.steel.cs.services.cust.facade.dao.CrmVersionUpdateMapper;
import jq.steel.cs.services.cust.facade.model.CrmVersionUpdate;
import jq.steel.cs.services.cust.facade.service.app.AppCrmVersionUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:   CrmVersionUpdate
 * @author          lujiawei
 * @date            2018-9-13 10:00
 */
@Service
public class CrmVersionUpdateServiceImpl implements AppCrmVersionUpdateService {

    @Autowired
    private CrmVersionUpdateMapper crmVersionUpdateMapper;

    public List<CrmVersionUpdateVO> selectAll() {
        //List<CrmVersionUpdate> crmVersionUpdates = crmVersionUpdateMapper.selectAll();
        //List<CrmVersionUpdateVO> crmVersionUpdateVOs = BeanCopyUtil.copyList(crmVersionUpdates, CrmVersionUpdateVO.class);
        //return crmVersionUpdateVOs;
		return null;
    }

    public List<CrmVersionUpdateVO> select(CrmVersionUpdateVO record){
		
		CrmVersionUpdate model = BeanCopyUtil.copy(record, CrmVersionUpdate.class);
		List<CrmVersionUpdate> crmVersionUpdates = crmVersionUpdateMapper.select(model);
		List<CrmVersionUpdateVO> crmVersionUpdateVOs = BeanCopyUtil.copyList(crmVersionUpdates, CrmVersionUpdateVO.class);
		return crmVersionUpdateVOs;
    }

	public CrmVersionUpdateVO selectByPrimaryKey(Long key){
    	CrmVersionUpdate crmVersionUpdate = crmVersionUpdateMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(crmVersionUpdate, CrmVersionUpdateVO.class);
    }

    public Integer insert(CrmVersionUpdateVO record){
    	CrmVersionUpdate crmVersionUpdate = BeanCopyUtil.copy(record, CrmVersionUpdate.class);
        return crmVersionUpdateMapper.insert(crmVersionUpdate);

    }

    public Integer insertSelective(CrmVersionUpdateVO record){
        CrmVersionUpdate crmVersionUpdate = BeanCopyUtil.copy(record, CrmVersionUpdate.class);
        return crmVersionUpdateMapper.insertSelective(crmVersionUpdate);
    }
    
    public Integer updateByPrimaryKey(CrmVersionUpdateVO record){
    	CrmVersionUpdate crmVersionUpdate = BeanCopyUtil.copy(record, CrmVersionUpdate.class);
        return crmVersionUpdateMapper.updateByPrimaryKey(crmVersionUpdate);
    }

    public Integer updateByPrimaryKeySelective(CrmVersionUpdateVO record){
        CrmVersionUpdate crmVersionUpdate = BeanCopyUtil.copy(record, CrmVersionUpdate.class);
        return crmVersionUpdateMapper.updateByPrimaryKeySelective(crmVersionUpdate);
    }

    public Integer deleteByPrimaryKey(Long key){
        return crmVersionUpdateMapper.deleteByPrimaryKey(key);
    }
    
    /**
	 *  IN
	 *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
	 *		{key}
	 *	</foreach>
	 */
    public Integer deleteByPrimaryKeys(Set<Long> keys){
    	//return crmVersionUpdateMapper.deleteByPrimaryKeys(keys);
        return null;
    }
    
	public Integer keep(List<CrmVersionUpdateVO> crmVersionUpdateVOs) {
		int result = 0;
//		Set<Long> keys = new HashSet<>();
		for (CrmVersionUpdateVO crmVersionUpdateVO : crmVersionUpdateVOs) {
			String opt = crmVersionUpdateVO.getOpt();
			if ("delete".equals(opt)) {
//				keys.add(crmVersionUpdateVO.getId());
				int i = deleteByPrimaryKey(crmVersionUpdateVO.getVid());
				if (i > 0) {
					result++;
				}
				//ParamType.update.getMsg()
			} else if ("update".equals(opt)) {
				int i = updateByPrimaryKeySelective(crmVersionUpdateVO);
				if (i > 0) {
					result++;
				}
			} else if ("insert".equals(opt)) {
				int i = insertSelective(crmVersionUpdateVO);
				if (i > 0) {
					result++;
				}
			}
		}
//		if(!keys.isEmpty())
//			result = result + deleteByPrimaryKeys(keys);
		return result;
	}

	public CrmVersionUpdateVO getNewVerson() {
		CrmVersionUpdate cvu = crmVersionUpdateMapper.getNewVersion();
        CrmVersionUpdateVO vo  = BeanCopyUtil.copy(cvu, CrmVersionUpdateVO.class);
        return vo;
	}
}