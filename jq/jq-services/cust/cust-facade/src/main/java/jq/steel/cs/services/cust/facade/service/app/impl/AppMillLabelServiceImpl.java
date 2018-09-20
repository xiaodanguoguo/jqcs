package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillLabelVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillLabelMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHostsMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillLabel;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import jq.steel.cs.services.cust.facade.service.app.AppMillLabelService;
import jq.steel.cs.services.cust.facade.service.millsheet.MillCoilInfoService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * dal Interface:MillLabel
 *
 * @author
 * @date 2018-9-19
 */
@Service
@Transactional
public class AppMillLabelServiceImpl implements AppMillLabelService {

    @Autowired
    private MillLabelMapper millLabelMapper;
    @Autowired
    private MillCoilInfoService millCoilInfoService;


    public List<MillLabelVO> selectAll() {
        //List<MillLabel> millLabels = millLabelMapper.selectAll();
        //List<MillLabelVO> millLabelVOs = BeanCopyUtil.copyList(millLabels, MillLabelVO.class);
        //return millLabelVOs;
        return null;
    }

    public List<MillLabelVO> select(MillLabelVO record) {

        MillLabel model = BeanCopyUtil.copy(record, MillLabel.class);
        List<MillLabel> millLabels = millLabelMapper.select(model);
        List<MillLabelVO> millLabelVOs = BeanCopyUtil.copyList(millLabels, MillLabelVO.class);
        return millLabelVOs;
    }

    public MillLabelVO selectByPrimaryKey(Long key) {
        MillLabel millLabel = millLabelMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(millLabel, MillLabelVO.class);
    }

    public Integer insert(MillLabelVO record) {
        MillLabel millLabel = BeanCopyUtil.copy(record, MillLabel.class);
        return millLabelMapper.insert(millLabel);

    }

    public Integer insertSelective(MillLabelVO record) {
        MillLabel millLabel = BeanCopyUtil.copy(record, MillLabel.class);
        return millLabelMapper.insertSelective(millLabel);
    }

    public Integer updateByPrimaryKey(MillLabelVO record) {
        MillLabel millLabel = BeanCopyUtil.copy(record, MillLabel.class);
        return millLabelMapper.updateByPrimaryKey(millLabel);
    }

    public Integer updateByPrimaryKeySelective(MillLabelVO record) {
        MillLabel millLabel = BeanCopyUtil.copy(record, MillLabel.class);
        return millLabelMapper.updateByPrimaryKeySelective(millLabel);
    }

    public Integer deleteByPrimaryKey(Long key) {
        return millLabelMapper.deleteByPrimaryKey(key);
    }

    /**
     * IN
     * <foreach collection="keys" open="(" close=")" item="key" separator=",">
     * {key}
     * </foreach>
     */
    public Integer deleteByPrimaryKeys(Set<Long> keys) {
        //return millLabelMapper.deleteByPrimaryKeys(keys);
        return null;
    }


	/*public Integer keep(List<MillLabelVO> millLabelVOs) {
		int result = 0;
//		Set<Long> keys = new HashSet<>();
		for (MillLabelVO millLabelVO : millLabelVOs) {
			String opt = millLabelVO.getOpt();
			if (ParamType.DELETE.getMsg().equals(opt)) {
//				keys.add(millLabelVO.getId());
				int i = deleteByPrimaryKey(millLabelVO.getId());
				if (i > 0) {
					result++;
				}
			} else if (ParamType.UPDATE.getMsg().equals(opt)) {
				int i = updateByPrimaryKeySelective(millLabelVO);
				if (i > 0) {
					result++;
				}
			} else if (ParamType.INSERT.getMsg().equals(opt)) {
				int i = insertSelective(millLabelVO);
				if (i > 0) {
					result++;
				}
			}
		}
//		if(!keys.isEmpty())
//			result = result + deleteByPrimaryKeys(keys);
		return result;
	}*/

    /**
     * 根据二维码信息查询数据
     *
     * @param jsonRequest " 榆中县 04 甲\n2017-07-15 13:17\nHRB400E\nф14\n170708101  46\n123支"
     * @return
     */
    @Transactional(readOnly = true)
    public List<CrmMillCoilInfoVO> queryByQrCode(JsonRequest<String> jsonRequest) {
        String str = jsonRequest.getReqBody();
        String[] strs = str.split("\n");
        int l = strs.length;
        MillLabelVO vo = new MillLabelVO();
        for (int i = 1; i < l; i++) {
            //生产时间
            if (i == 1) {
                String strDate = strs[i];
                vo.setProductionTimeStr(strDate);
            }
            //牌号
            if (i == 2) {
                String strZph = strs[i];
                vo.setZph(strZph);
            }
            //规格
            if (i == 3) {
                String strSpecs = strs[i];
                vo.setSpecs(strSpecs);
            }
            //批次/卷号
            if (i == 4) {
                String strZcharg1 = strs[i];
                String[] strs2 = strZcharg1.split("  ");
                String strZcharg2 = strs2[0];
                vo.setZcharg(strZcharg2);
            }
        }
        MillLabel millLabel = millLabelMapper.queryByQrcode(vo);

        CrmMillCoilInfo crmMillCoilInfo = new CrmMillCoilInfo();
        //假信息,如果没有对应数据返回一个状态
        CrmMillCoilInfoVO crmMillCoilInfoVO = BeanCopyUtil.copy(crmMillCoilInfo, CrmMillCoilInfoVO.class);
        if (millLabel == null) {
            crmMillCoilInfoVO.setState("0");
            List<CrmMillCoilInfoVO> list = new ArrayList<>();
            list.add(crmMillCoilInfoVO);
            return list;
        }
        //真信息,如果有数据执行另外一条sql,用于返回质证书结构化数据
        crmMillCoilInfoVO.setZcharg(millLabel.getZcharg());
        crmMillCoilInfoVO.setShowFlag(1);
        List<CrmMillCoilInfoVO> list = millCoilInfoService.getCoilDetail(crmMillCoilInfoVO);
        for (CrmMillCoilInfoVO voForList : list) {
            voForList.setState("1");
        }
        return list;
    }
}