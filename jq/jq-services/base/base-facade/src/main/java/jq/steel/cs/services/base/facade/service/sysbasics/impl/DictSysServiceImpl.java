package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateUtil;
import jq.steel.cs.services.base.api.vo.DictSysVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.dao.DictSysMapper;
import jq.steel.cs.services.base.facade.model.DictSys;
import jq.steel.cs.services.base.facade.service.sysbasics.DictSysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: zhaotairan
 */
@Service
public class DictSysServiceImpl implements DictSysService {

    private static Logger LOG = LoggerFactory.getLogger(DictSysServiceImpl.class);

    @Autowired
    private DictSysMapper dictSysMapper;

    @Override
    public PageDTO<DictSysVO> dictSysList(JsonRequest<DictSysVO> jsonRequest) {
        DictSysVO reqBody = jsonRequest.getReqBody();
        DictSys dictSys = new DictSys();
        BeanCopyUtil.copy(reqBody, dictSys);
        try {
            PageDTOUtil.startPage(reqBody);
            List<DictSys> list = this.dictSysMapper.find(dictSys);
            PageDTO<DictSys> page = PageDTOUtil.transform(list);
            //转换
            int pageNum = page.getPageNum();
            int pageSize = page.getPageSize();
            PageDTO<DictSysVO> pageVo = new PageDTO(pageNum, pageSize);
            pageVo.setTotal(page.getTotal());
            List<DictSys> resultData = page.getResultData();
            List<DictSysVO> result = BeanCopyUtil.copyList(resultData, DictSysVO.class);
            pageVo.setResultData(result);
            return pageVo;
        } finally {
            PageDTOUtil.endPage();
        }
    }


    @Override
    public JsonResponse dictSysKeep(JsonRequest<List<DictSysVO>> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        List<DictSysVO> dictSysVO = jsonRequest.getReqBody();

        List<DictSys> reqBody = BeanCopyUtil.copyList(dictSysVO, DictSys.class);
        try {
            if (CollectionUtils.isEmpty(reqBody)) {
                jsonResponse.setRetDesc("缺少参数！");
            }
            for (DictSys dictSys : reqBody) {

                String opt = dictSys.getOpt();

                if (StringUtils.isEmpty(opt)) {
                    jsonResponse.setRetDesc("字段值不正确！");
                    break;
                }

                if (SysPramType.UPDATE.getMsg().equals(opt)) {
                    dictSysMapper.updateByPrimaryKeySelective(dictSys);
                } else if (SysPramType.INSERT.getMsg().equals(opt)) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String format = sdf.format(date);
                    Date date1 = DateUtil.string2Date(format, "yyyy-MM-dd HH:mm:ss");
                    dictSys.setCreatedTime(date1);
                    dictSysMapper.insertSelective(dictSys);
                }

            }
        } catch (Exception e) {
            LOG.error("keep error = {}", e);
        }
        return jsonResponse;
    }

    @Override
    public PageDTO<DictSysVO> dictSysListByType(JsonRequest<DictSysVO> jsonRequest) {
        DictSysVO reqBody = jsonRequest.getReqBody();
        DictSys dictSys = new DictSys();
        BeanCopyUtil.copy(reqBody, dictSys);
        try {
            PageDTOUtil.startPage(reqBody);
            List<DictSys> list = this.dictSysMapper.findByType(dictSys.getDictType());
            PageDTO<DictSys> page = PageDTOUtil.transform(list);
            //转换
            int pageNum = page.getPageNum();
            int pageSize = page.getPageSize();
            PageDTO<DictSysVO> pageVo = new PageDTO(pageNum, pageSize);
            pageVo.setTotal(page.getTotal());
            List<DictSys> resultData = page.getResultData();
            List<DictSysVO> result = BeanCopyUtil.copyList(resultData, DictSysVO.class);
            pageVo.setResultData(result);
            System.out.println(pageVo);

            return pageVo;
        } finally {
            PageDTOUtil.endPage();
        }
    }




    @Override
    public List<DictSysVO> dictSysByTypeList(DictSysVO jsonRequest) {
        DictSys dictSys = new DictSys();
        BeanCopyUtil.copy(jsonRequest, dictSys);
        try {
            List<DictSys> list = this.dictSysMapper.findByType(dictSys.getDictType());
            List<DictSysVO> result = BeanCopyUtil.copyList(list, DictSysVO.class);
            return result;
        } finally {
            PageDTOUtil.endPage();
        }
    }
}
