package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.base.api.vo.SysDictMeasUnitInfoVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.dao.DictMeasUnitInfoMapper;
import jq.steel.cs.services.base.facade.model.DictMeasUnitInfo;
import jq.steel.cs.services.base.facade.service.sysbasics.SysDictMeasUnitInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: zhaoyichen
 */
@Service
public class SysDictMeasUnitInfoServiceImpl implements SysDictMeasUnitInfoService {

    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingServiceImpl.class);

    //查询
    @Autowired
    private DictMeasUnitInfoMapper dictMeasUnitInfoMapper;

    @Override
    public PageDTO<SysDictMeasUnitInfoVO> listSysDictMeasUnitInfo(JsonRequest<SysDictMeasUnitInfoVO> jsonRequest) {
        SysDictMeasUnitInfoVO reqBody = jsonRequest.getReqBody(); //参数

        DictMeasUnitInfo dictMeasUnitInfo = new DictMeasUnitInfo();

        BeanCopyUtil.copy(reqBody,dictMeasUnitInfo);
        try {
            PageDTOUtil.startPage(reqBody);
            List<DictMeasUnitInfo> list = dictMeasUnitInfoMapper.find(dictMeasUnitInfo);
            PageDTO<DictMeasUnitInfo> page = PageDTOUtil.transform(list);

            //转换
            PageDTO<SysDictMeasUnitInfoVO> pageVo = new PageDTO(page.getPageNum(),page.getPageSize());
            pageVo.setTotal(page.getTotal());
            List<DictMeasUnitInfo> resultData = page.getResultData();

            List<SysDictMeasUnitInfoVO> result = BeanCopyUtil.copyList(resultData, SysDictMeasUnitInfoVO.class);
            pageVo.setResultData(result);
            return pageVo;
        } finally {
            PageDTOUtil.endPage();
        }
    }

    //保存
    @Override
    public JsonResponse keepSysDictMeasUnitInfo(JsonRequest<List<SysDictMeasUnitInfoVO>> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        List<SysDictMeasUnitInfoVO> sysDictMeasUnitInfoVO = jsonRequest.getReqBody();

        List<DictMeasUnitInfo> reqBody = BeanCopyUtil.copyList(sysDictMeasUnitInfoVO, DictMeasUnitInfo.class);
        try{
            if(CollectionUtils.isEmpty(reqBody)){
                jsonResponse.setRetDesc("缺少参数！");
            }
            for(DictMeasUnitInfo dictMeasUnitInfo:reqBody){

                String opt = dictMeasUnitInfo.getOpt();

                if(StringUtils.isEmpty(opt)){
                    jsonResponse.setRetDesc("字段值不正确！");
                    break;
                }

                if(SysPramType.DELETE.getMsg().equals(opt)){

                    dictMeasUnitInfoMapper.deleteByPrimaryKey(dictMeasUnitInfo.getId());
                }else if(SysPramType.UPDATE.getMsg().equals(opt)){

                    dictMeasUnitInfoMapper.updateByPrimaryKeySelective(dictMeasUnitInfo);
                }else if(SysPramType.INSERT.getMsg().equals(opt)){

                    dictMeasUnitInfo.setId(null);
                    dictMeasUnitInfoMapper.insertSelective(dictMeasUnitInfo);
                }

            }
        }catch (Exception e){
            LOG.error("keep error = {}",e);
        }
        return jsonResponse;
    }

    //删除
    @Override
    public JsonResponse delSysDictMeasUnitInfo(JsonRequest<SysDictMeasUnitInfoVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        SysDictMeasUnitInfoVO reqBody = jsonRequest.getReqBody();
        try{
            int i = dictMeasUnitInfoMapper.deleteByPrimaryKey(reqBody.getId());
            jsonResponse.setRspBody(i);
        }catch (Exception e){
            LOG.error("del error = {}",e);
        }
        return jsonResponse;
    }

}
