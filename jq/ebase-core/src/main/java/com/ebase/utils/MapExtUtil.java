/*
 * Copyright: Copyright (c) 2017-2020
 * Company: ygego
 *
 */

package com.ebase.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MapExt
 * Description: 简单Map工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class MapExtUtil implements Serializable {

    private Map<String, Object> map = null;

    public MapExtUtil() {
        init(8);
    }

    public MapExtUtil(int len) {
        init(len);
    }

    private void init(int len) {
        if (null == map) {
            map = new HashMap<>(len);
        }
    }

    public MapExtUtil of(String key, Object val) {
        if (StringUtils.isNotBlank(key)) {
            map.put(key, val);
        }
        return this;
    }

    public void put(String key, Object val) {
        if (StringUtils.isNotBlank(key)) {
            map.put(key, val);
        }
    }

    public Map<String, Object> getMap() {
        return map;
    }

}
