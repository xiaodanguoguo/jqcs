package jq.steel.cs.services.base.facade.service.sysbasics.impl;


import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.base.api.vo.SysParamVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.dao.SysParamMapper;
import jq.steel.cs.services.base.facade.model.SysParam;
import jq.steel.cs.services.base.facade.service.sysbasics.SysParamCodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangyu
 */
@Service
public class SysParamCodingServiceImpl implements SysParamCodingService {

    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingServiceImpl.class);

    @Autowired
    private SysParamMapper sysParamMapper;

    @Override
    public PageDTO<SysParamVO> listSysParam(SysParamVO reqBody)throws RuntimeException {
        SysParam sysParam = new SysParam();

        reqBody.setParamName(reqBody.getParamCode());
        BeanCopyUtil.copy(reqBody,sysParam);
        try {
            PageDTOUtil.startPage(reqBody);
            List<SysParam> list = sysParamMapper.find(sysParam);
            PageDTO<SysParam> page = PageDTOUtil.transform(list);

            //转换
            PageDTO<SysParamVO> pageVo = new PageDTO(page.getPageNum(),page.getPageSize());
            pageVo.setTotal(page.getTotal());
            List<SysParam> resultData = page.getResultData();

            List<SysParamVO> result = BeanCopyUtil.copyList(resultData, SysParamVO.class);
            pageVo.setResultData(result);
            return pageVo;
        } finally {
            PageDTOUtil.endPage();
        }
    }



    @Override
    public Boolean keepSysParam(List<SysParamVO> sysParamVO) throws RuntimeException {

        List<SysParam> reqBody = BeanCopyUtil.copyList(sysParamVO, SysParam.class);

        for(SysParam sysParam:reqBody){

            String opt = sysParam.getOpt();

            if(SysPramType.DELETE.getMsg().equals(opt)){

                sysParamMapper.deleteByPrimaryKey(sysParam.getId());
            }else if(SysPramType.UPDATE.getMsg().equals(opt)){

                sysParamMapper.updateByPrimaryKeySelective(sysParam);
            }else if(SysPramType.INSERT.getMsg().equals(opt)){

                sysParam.setId(null);
                sysParamMapper.insertSelective(sysParam);
            }
        }

        return true;
    }

    @Override
    public Integer delSysParam(SysParamVO reqBody)throws RuntimeException  {


        int i = sysParamMapper.deleteByPrimaryKey(reqBody.getId());


        return i;
    }
}
