package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
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

    //通过当前用户信息查询对应所有对应的钢卷
    List<MillCoilInfo> queryForListByCurrentUser(MillCoilInfo millCoilInfo);

    //通过钢卷编号查询物理信息
    List<CrmMillCoilInfo> getPhysicsInfoListByCoil(@Param("zcharg") String zcharg);
    //通过钢卷编号查询化学信息
    List<CrmMillCoilInfo> getChemistryListByCoil(@Param("zcharg") String zcharg);

    List<MillCoilInfo> findIsTrue (MillCoilInfo record);
}