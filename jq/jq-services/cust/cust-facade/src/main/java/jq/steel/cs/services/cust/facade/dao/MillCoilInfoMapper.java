package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.model.CrmMillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MillCoilInfoMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillCoilInfo record);

    int insertSelective(MillCoilInfo record);

    MillCoilInfo selectByPrimaryKey(Long sid);

    List<MillCoilInfo>  selectZchargs(MillCoilInfo record);

    int updateByPrimaryKeySelective(MillCoilInfo record);

    int updateByPrimaryKey(MillCoilInfo record);

    //分页查询
    List<MillCoilInfo> splitFind(MillCoilInfo record);


    List<MillCoilInfo> findInfo(MillCoilInfo record);


    List<MillCoilInfo> findMillsheetNumber (MillCoilInfo record);

    //查询质证书明细_物理
    List<CrmMillCoilInfo> getPhysicsListByMillSheet(CrmMillSheetDetailVO vo);
    //查询质证书明细_化学
    List<CrmMillCoilInfo> getChemistryListByMillSheet(CrmMillSheetDetailVO vo);

    //通过当前用户信息查询对应所有对应的钢卷
    List<MillCoilInfo> queryForListByCurrentUser(MillCoilInfo millCoilInfo);

    //通过钢卷编号查询物理信息
    List<CrmMillCoilInfo> getPhysicsInfoListByCoil(CrmMillCoilInfo millCoilInfo);
    //通过钢卷编号查询化学信息
    List<CrmMillCoilInfo> getChemistryListByCoil(CrmMillCoilInfo millCoilInfo);

    List<MillCoilInfo> findIsTrue (MillCoilInfo record);

    //    //扫描二维码,查询质证书信息和质证书下的钢卷信息以及对应的钢卷物理,化学信息
    List<CrmMillCoilInfo> getChemistryListByByQrCode(CrmMillSheetDetailVO vo1);

    List<CrmMillCoilInfo> getPhysicsListByQrCode(CrmMillSheetDetailVO vo1);


    List<MillCoilInfo> findCoil(MillCoilInfo millCoilInfo);

    //质证书查询规格
    List<MillCoilInfo> findSpecs(MillCoilInfo millCoilInfo);


    List<MillCoilInfo> findZchehao(MillCoilInfo millCoilInfo);


    List<MillCoilInfo> findVolume(MillCoilInfo millCoilInfo);

    List<MillCoilInfo> findVolumeNeed(MillCoilInfo millCoilInfo);



    //通过卷号查询卷相关信息
    MillCoilInfo selectByZcharg(MillCoilInfoVO vo);

    //拆分撤销查询
    MillCoilInfo findDate(MillCoilInfo record);


    //修改剩余重量剩余件数
    void updateDate(MillCoilInfo record);

    //删除拆分质证书
    void deleteMillSheetNo(MillCoilInfo record);
}