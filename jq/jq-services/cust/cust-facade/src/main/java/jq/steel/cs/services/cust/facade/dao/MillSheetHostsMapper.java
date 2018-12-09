package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.model.MillSheetHead;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import java.util.List;

public interface MillSheetHostsMapper {
    int insert(MillSheetHosts record);

    int insertSelective(MillSheetHosts record);

    //分页查询
    List<MillSheetHosts> findMillSheetByPage(MillSheetHosts record);

    //分页查询（酒钢）
    List<MillSheetHosts> findMillSheetByPage1(MillSheetHosts record);

    //查询pdf地址
    List<MillSheetHosts> findUrlList(MillSheetHosts record);

    MillSheetHosts findUrl(MillSheetHosts record);

    //质证书回退查询
    List<MillSheetHosts> findMillsheetNo(MillSheetHosts record);

    List<MillSheetHosts> findList(MillSheetHosts record);

    List<MillSheetHosts> findIsTrue (MillSheetHosts record);

    List<MillSheetHosts> findExist (MillSheetHosts record);
    List<MillSheetHosts> findAllow (MillSheetHosts record);
    List<MillSheetHosts> findType (MillSheetHosts record);
    List<MillSheetHosts> findMillSheetType (MillSheetHosts record);
    List<MillSheetHosts> findMillSheetZkunner (MillSheetHosts record);





    //校验checkCoil
    List<MillSheetHosts> checkCoil (MillSheetHosts record);
    //校验checkCoil
    List<MillSheetHosts> checkCoil1 (MillSheetHosts record);


    //修改次数
    Integer updateNum(MillSheetHosts record);

    List<MillSheetHosts> findDeptCode(MillSheetHosts record);

    //返回app端质证书下载路径
    MillSheetHosts getUrlForApp(MillSheetHosts model);

    //APP质证书条件查询
    List<MillSheetHosts> getSheetHostsMsgHaveZcharg(MillCoilInfoVO vo);

    MillSheetHead selectByMillSheetNOWithCreateOrUpdate(String millSheetNo);

    Integer updateStateAndPrintNum(String millSheetNo);

    MillSheetHosts selectByMillSheetNo(String millSheetNo);

    List<MillSheetHosts> getSheetHostsMsgNoZcharg(MillCoilInfoVO vo);

    //删除拆分撤销数据
    void  deleteMillSheetNo(MillSheetHosts re);
}