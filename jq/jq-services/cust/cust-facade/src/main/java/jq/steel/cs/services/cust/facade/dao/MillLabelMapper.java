package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.MillLabelVO;
import jq.steel.cs.services.cust.facade.model.MillLabel;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;

import java.util.List;

public interface MillLabelMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillLabel record);

    int insertSelective(MillLabel record);

    MillLabel selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillLabel record);

    int updateByPrimaryKey(MillLabel record);

    List<MillLabel> select(MillLabel record);

    //根据二维码信息查询数据
    List<MillLabel> queryByQrcode(MillLabelVO vo);
    //用于返回质证书结构化数据
    MillSheetHosts querSheeltHostsBymillLabe(MillLabel millLabel);
}