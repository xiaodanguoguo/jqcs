package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.MailRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MailRecordDao {
    int deleteByPrimaryKey(Long recordId);

    int insert(MailRecord record);

    int insertSelective(MailRecord record);

    MailRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(MailRecord record);

    int updateByPrimaryKeyWithBLOBs(MailRecord record);

    int updateByPrimaryKey(MailRecord record);

    Integer insertAll(@Param("records") List<MailRecord> records);
}