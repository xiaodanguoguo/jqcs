package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;

import java.util.List;

public interface ObjectionDiaoChaService {

    //分页条件查询
    PageDTO<ObjectionDiaoChaVO> findByPage(ObjectionDiaoChaVO objectionTiBaoVO);

    //异议调查外部调查内部调查回显数据
    ObjectionDiaoChaVO findDetails(ObjectionDiaoChaVO objectionTiBaoVO);

    //内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）
    ObjectionDiaoChaVO update(ObjectionDiaoChaVO record);

    //内部调查报告（保存，提交）
    ObjectionDiaoChaVO updateInside(ObjectionDiaoChaVO record);

    //导出
    List<ObjectionDiaoChaVO> export (List<String> record);

    //内部调查导出
    List<ObjectionDiaoChaVO> exportExcel (List<String> record);


    //异议调查打印受理单+调查报告下载
    ObjectionDiaoChaVO print(ObjectionDiaoChaVO record);

    // 调查报告驳回
    Integer reject(ObjectionDiaoChaVO record);

    // 内部调查/外部调查开始结束状态修改
    Integer updateState(ObjectionDiaoChaVO record);
}
