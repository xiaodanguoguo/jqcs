package jq.steel.cs.services.cust.facade.service.millsheet;

import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MillSheetHostsService {
    //分页条件查询
    PageDTO<MillSheetHostsVO> findMillSheetByPage(MillSheetHostsVO millSheetHostsVO);
    //查询质证书文件地址
    List<MillSheetHostsVO> findUrl(List<MillSheetHostsVO> millSheetHostsVO,HttpServletRequest request);
    //下载地址
    List<MillSheetHostsVO> findDownUrl(List<String> list);

    MillSheetHostsVO  rollbackQuery (MillSheetHostsVO millSheetHostsVO);

    //查询是否有质证书编号
    MillSheetHostsVO findIsTrue(MillSheetHostsVO millSheetHostsVO);

    //返回app端质证书下载路径
    MillSheetHostsVO getUrlForApp(JsonRequest<MillSheetHostsVO> jsonRequest);

    //修改打印次数下载次数
    Integer updateNumber(List<MillSheetHostsVO> millSheetHostsVO);
}
