package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import jq.steel.cs.services.cust.facade.model.CrmMillChemistryData;
import jq.steel.cs.services.cust.facade.model.CrmMillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MillCoilInfoMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillCoilInfo record);

    int insertSelective(MillCoilInfo record);

    MillCoilInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillCoilInfo record);

    int updateByPrimaryKey(MillCoilInfo record);

    //分页查询
    List<MillCoilInfo> splitFind(MillCoilInfo record);

    List<MillCoilInfo> findMillsheetNumber (MillCoilInfo record);

    //查询质证书明细_物理
    List<CrmMillCoilInfo> getPhysicsListByMillSheet(CrmMillSheetDetailVO vo);
    //查询质证书明细_化学
    List<CrmMillCoilInfo> getChemistryListByMillSheet(CrmMillSheetDetailVO vo);
}