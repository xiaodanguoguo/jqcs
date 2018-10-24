package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillSheetHead;

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

    MillSheetHead selectByMillSheetNO(String millSheetNo);
}