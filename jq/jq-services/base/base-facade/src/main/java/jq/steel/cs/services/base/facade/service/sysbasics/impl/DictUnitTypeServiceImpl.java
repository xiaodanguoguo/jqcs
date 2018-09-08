package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateUtil;
import jq.steel.cs.services.base.api.vo.DictUnitTypeVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.dao.DictUnitTypeMapper;
import jq.steel.cs.services.base.facade.model.DictUnitType;
import jq.steel.cs.services.base.facade.service.sysbasics.DictUnitTypeService;
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
public class DictUnitTypeServiceImpl implements DictUnitTypeService {

    private static Logger LOG = LoggerFactory.getLogger(DictUnitTypeService.class);

    @Autowired
    private DictUnitTypeMapper dictUnitTypeMapper;

    @Override
    public PageDTO<DictUnitTypeVO> dictUnitTypeController(JsonRequest<DictUnitTypeVO> jsonRequest) {
        DictUnitTypeVO reqBody=jsonRequest.getReqBody();
        DictUnitType dictUnitType = new DictUnitType();
        BeanCopyUtil.copy(reqBody,dictUnitType);
        try {
            PageDTOUtil.startPage(reqBody);
            List<DictUnitType> list=this.dictUnitTypeMapper.find(dictUnitType);
            PageDTO<DictUnitType> page = PageDTOUtil.transform(list);
            //转换
            int pageNum = page.getPageNum();
            int pageSize = page.getPageSize();
            PageDTO<DictUnitTypeVO> pageVo = new PageDTO(pageNum,pageSize);
            pageVo.setTotal(page.getTotal());
            List<DictUnitType> resultData=page.getResultData();
            List<DictUnitTypeVO> result = BeanCopyUtil.copyList(resultData, DictUnitTypeVO.class);
            pageVo.setResultData(result);
            System.out.println(pageVo);
            return pageVo;
        } finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public JsonResponse dictUnitTypeKeep(JsonRequest<List<DictUnitTypeVO>> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        List<DictUnitTypeVO> dictUnitTypeVO = jsonRequest.getReqBody();

        List<DictUnitType> reqBody = BeanCopyUtil.copyList(dictUnitTypeVO, DictUnitType.class);
        try{
            if(CollectionUtils.isEmpty(reqBody)){
                jsonResponse.setRetDesc("缺少参数！");
            }

            for(DictUnitType dictUnitType:reqBody){

                String opt = dictUnitType.getOpt();

                if(StringUtils.isEmpty(opt)){
                    jsonResponse.setRetDesc("字段值不正确！");
                    break;
                }

                if(SysPramType.UPDATE.getMsg().equals(opt)){

                    dictUnitTypeMapper.updateByPrimaryKeySelective(dictUnitType);
                }else if(SysPramType.INSERT.getMsg().equals(opt)){
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String format = sdf.format(date);
                    Date date1 = DateUtil.string2Date(format, "yyyy-MM-dd HH:mm:ss");
                    dictUnitType.setCreatedTime(date1);
                    dictUnitTypeMapper.insertSelective(dictUnitType);
                }


            }
        }catch (Exception e){
            LOG.error("keep error = {}",e);
        }
        return jsonResponse;
    }
}
