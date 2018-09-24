package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmAnnouncement;

import java.util.List;

public interface CrmAnnouncementMapper {
    int deleteByPrimaryKey(Long aid);

    int insert(CrmAnnouncement record);

    int insertSelective(CrmAnnouncement record);

    CrmAnnouncement selectByPrimaryKey(Long aid);

    int updateByPrimaryKeySelective(CrmAnnouncement record);

    int updateByPrimaryKey(CrmAnnouncement record);

    List<CrmAnnouncement> select(CrmAnnouncement record);

    CrmAnnouncement getNewAnnouncement();

    List<CrmAnnouncement> selectByTitle(CrmAnnouncement record);
}