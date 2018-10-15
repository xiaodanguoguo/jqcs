package jq.steel.cs.services.cust.facade.dao;

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

    //修改次数
    Integer updateNum(MillSheetHosts record);

    List<MillSheetHosts> findDeptCode(MillSheetHosts record);

    //返回app端质证书下载路径
    MillSheetHosts getUrlForApp(String millSheetNo);
}