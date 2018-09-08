package jq.steel.cs.services.base.facade.service.test.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.utils.JsonUtil;

/**
 * @ClassName: Demo
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/10 10:10
 */
public class Demo {

    public static void main(String[] args) {
        PageDTO pageDTO = new PageDTO(1,10);
        System.out.println(JsonUtil.toJson(pageDTO));
    }
}
