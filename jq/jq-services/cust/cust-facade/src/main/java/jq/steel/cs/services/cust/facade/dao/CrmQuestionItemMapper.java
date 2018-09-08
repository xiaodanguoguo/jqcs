package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.CrmQuestionItemVO;
import jq.steel.cs.services.cust.facade.model.CrmQuestionItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CrmQuestionItemMapper {
    int deleteByPrimaryKey(Long questionItemId);

    int insert(CrmQuestionItem record);

    int insertSelective(CrmQuestionItem record);

    CrmQuestionItem selectByPrimaryKey(Long questionItemId);

    int updateByPrimaryKeySelective(CrmQuestionItem record);

    int updateByPrimaryKey(CrmQuestionItem record);

    List<CrmQuestionItemVO> getList(CrmQuestionItem record);

    int deleteByQid(@Param("qid") Long qid);
}