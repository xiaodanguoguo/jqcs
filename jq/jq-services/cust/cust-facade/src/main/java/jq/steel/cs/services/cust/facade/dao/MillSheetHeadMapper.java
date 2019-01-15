package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.model.MillSheetHead;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;

import java.util.List;

public interface MillSheetHeadMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillSheetHead record);

    int insertSelective(MillSheetHead record);

    MillSheetHead selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillSheetHead record);

    int updateByPrimaryKey(MillSheetHead record);

    List<MillSheetHead> findMillSheetByPage(MillSheetHead record);

    MillSheetHead findCategoryCode(MillSheetHead record);

    List<MillSheetHead> findZchehao(MillSheetHead record);

    MillSheetHead selectByMillSheetNO(String millSheetNo);

    List<MillSheetHead>  selectAll(MillSheetHead record);

    //删除拆分撤销数据
    void  deleteMillSheetNo(MillSheetHead re);
}