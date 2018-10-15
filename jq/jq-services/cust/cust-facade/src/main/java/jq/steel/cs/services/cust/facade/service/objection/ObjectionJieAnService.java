package jq.steel.cs.services.cust.facade.service.objection;

import jq.steel.cs.services.cust.api.vo.ObjectionJieAnVO;

public interface ObjectionJieAnService {

    //上传协议书文件
    Integer upload(ObjectionJieAnVO record);

    //过期原因
    Integer expiren(ObjectionJieAnVO record);

    //异议结案撤销
    Integer revoke(ObjectionJieAnVO record);

    //打印通知单
    ObjectionJieAnVO print(ObjectionJieAnVO record);

    //查看文件
    ObjectionJieAnVO look(ObjectionJieAnVO record);

}
