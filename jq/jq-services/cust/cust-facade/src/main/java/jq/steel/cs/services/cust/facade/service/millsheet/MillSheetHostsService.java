package jq.steel.cs.services.cust.facade.service.millsheet;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;

import java.util.List;

public interface MillSheetHostsService {
    //分页条件查询
    PageDTO<MillSheetHostsVO> findMillSheetByPage(MillSheetHostsVO millSheetHostsVO);
    //查询质证书文件地址
    List<MillSheetHostsVO> findUrl(List<MillSheetHostsVO> millSheetHostsVO);
    //下载地址
    List<MillSheetHostsVO> findDownUrl(List<String> list);

    MillSheetHostsVO  rollbackQuery (MillSheetHostsVO millSheetHostsVO);

    //查询是否有质证书编号
    MillSheetHostsVO findIsTrue(MillSheetHostsVO millSheetHostsVO);

}
