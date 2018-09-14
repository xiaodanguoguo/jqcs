
package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoCountVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;

import java.util.List;

public interface ObjectionTiBaoService {

    //分页条件查询
    PageDTO<ObjectionTiBaoVO> findByPage(ObjectionTiBaoVO objectionTiBaoVO);

    //新增查询修改查询和详情查询和销售审核查询
    ObjectionTiBaoVO findDetails(ObjectionTiBaoVO objectionTiBaoVO);

    //新增保存修改保存销售审核保存驳回通过
    Integer update(ObjectionTiBaoVO objectionTiBaoVO);

    //提报删除
    Integer submit(List<ObjectionTiBaoVO> objectionTiBaoVO);

    //导出
    List<ObjectionTiBaoVO>  export (List<String> objectionTiBaoVO);

    //协议书下载
    ObjectionTiBaoVO findDownUrl(ObjectionTiBaoVO objectionTiBaoVO);

    //报告附件查看
    ObjectionTiBaoVO lookPhoto(ObjectionTiBaoVO objectionTiBaoVO);


    /**
     * @param:
     * @return:
     * @description:  根据不同状态计数
     * @author: lirunze
     * @Date: 2018/9/7
     */
    ObjectionTiBaoCountVO getCount(CrmClaimApply crmClaimApply);


    /**
     * @param:
     * @return:
     * @description:  app提报列表
     * @author: lirunze
     * @Date: 2018/9/13
     */
    PageDTO<ObjectionTiBaoVO> findTiBaoByPage(ObjectionTiBaoVO objectionTiBaoVO);

    /**
     * @param:
     * @return:
     * @description:  app跟踪列表
     * @author: lirunze
     * @Date: 2018/9/13
     */
    PageDTO<ObjectionTiBaoVO> findgenzongByPage(ObjectionTiBaoVO objectionTiBaoVO);
}
