package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmCustGrumble;
import net.sf.jsqlparser.statement.update.Update;

import java.util.List;

public interface CrmCustGrumbleMapper {
    int deleteByPrimaryKey(Long cid);

    int insert(CrmCustGrumble record);

    int insertSelective(CrmCustGrumble record);

    CrmCustGrumble selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(CrmCustGrumble record);

    List<CrmCustGrumble> findByPage(CrmCustGrumble record);

    List<CrmCustGrumble> findCount(CrmCustGrumble record);

    List<CrmCustGrumble> findCount1(CrmCustGrumble record);

    void updateState(CrmCustGrumble record);

}