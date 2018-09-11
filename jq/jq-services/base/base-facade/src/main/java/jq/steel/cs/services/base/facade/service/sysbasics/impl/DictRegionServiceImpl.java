package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.base.api.vo.DictRegionVO;
import jq.steel.cs.services.base.facade.dao.DictRegionMapper;
import jq.steel.cs.services.base.facade.model.DictRegion;
import jq.steel.cs.services.base.facade.service.sysbasics.DictRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhaoyichen
 */

@Service
public class DictRegionServiceImpl implements DictRegionService {

    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingServiceImpl.class);

    @Autowired
    private DictRegionMapper dictRegionMapper;



    /**
     *  结构树 模糊查询
     * @param jsonRequest
     * @return
     */
    @Override
    public List<DictRegionVO> listDictRegion(JsonRequest<DictRegionVO> jsonRequest) {
        DictRegionVO  reqBody = jsonRequest.getReqBody();

        DictRegion dictRegion = new DictRegion();

        BeanCopyUtil.copy(reqBody,dictRegion);

        //原始数据
        List<DictRegion> dictRegionList = dictRegionMapper.findDictRegion(dictRegion);

        //查看结果
        for (DictRegion region : dictRegionList) {
            System.out.println(region);
        }

        // 最后的结果
        List<DictRegion> regionList = new ArrayList<DictRegion>();

        // 先找到所有的一级菜单
        for (int i = 0; i < dictRegionList.size(); i++) {
            // 一级菜单没有parentId
            /*if (dictRegionList.get(i).getParentId() != null) {
               regionList.add(dictRegionList.get(i));
            }*/
            regionList.add(dictRegionList.get(i));
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (DictRegion region : regionList) {
            region.setChildMenus(getChild(region.getId(), dictRegionList));
        }

        List<DictRegionVO> result = BeanCopyUtil.copyList(regionList, DictRegionVO.class);

        return result;
    }

    /**
     * 地区结构树 父查子 查询
     * @param jsonRequest
     * @return
     */
    @Override
    public List<DictRegionVO> listDictRegionTree(JsonRequest<DictRegionVO> jsonRequest) {
        DictRegionVO  reqBody = jsonRequest.getReqBody();

        DictRegion dictRegion = new DictRegion();

        BeanCopyUtil.copy(reqBody,dictRegion);

        //原始数据
        List<DictRegion> dictRegionList = dictRegionMapper.findDictRegionTree(dictRegion);

        //查看结果
        for (DictRegion region : dictRegionList) {
            System.out.println(region);
        }

        // 最后的结果
        List<DictRegion> regionList = new ArrayList<DictRegion>();


        // 先找到所有的一级菜单
        for (int i = 0; i < dictRegionList.size(); i++) {
            // 一级菜单没有parentId
            if (dictRegionList.get(i).getParentId() != null) {
                regionList.add(dictRegionList.get(i));
            }

        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (DictRegion region : regionList) {
            region.setChildMenus(getChild(region.getId(), dictRegionList));
        }

        List<DictRegionVO> result = BeanCopyUtil.copyList(regionList, DictRegionVO.class);

        return result;
    }

    /**
     * 地区结构树 子查父 查询
     * @param jsonRequest
     * @return
     */
    @Override
    public List<DictRegionVO> listDictRegionTreeSon(JsonRequest<DictRegionVO> jsonRequest) {
        DictRegionVO  reqBody = jsonRequest.getReqBody();

        DictRegion dictRegion = new DictRegion();

        BeanCopyUtil.copy(reqBody,dictRegion);

        //原始数据
        List<DictRegion> dictRegionList = dictRegionMapper.findDictRegionTreeSon(dictRegion);

        //查看结果
        for (DictRegion region : dictRegionList) {
            System.out.println(region);
        }

        // 最后的结果
        List<DictRegion> regionList = new ArrayList<DictRegion>();


        // 先找到所有的一级菜单
        for (int i = 0; i < dictRegionList.size(); i++) {
            // 一级菜单没有parentId
            /*if (dictRegionList.get(i).getParentId() != null) {
                regionList.add(dictRegionList.get(i));
            }*/
            regionList.add(dictRegionList.get(i));

        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (DictRegion region : regionList) {
            region.setChildMenus(getChild(region.getId(),dictRegionList));
        }

        List<DictRegionVO> result = BeanCopyUtil.copyList(regionList, DictRegionVO.class);

        return result;
    }

    /**
     * 递归查找子菜单
     *
     *
     * @param id  当前菜单id
     * @param dictRegionList  要查找的列表
     * @return
     *
     */
    private List<DictRegion> getChild(long id, List<DictRegion> dictRegionList) {
        // 子菜单
        List<DictRegion> childList = new ArrayList<>();
        for (DictRegion region : dictRegionList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (region.getParentId() != null) {
                if (region.getParentId() == id) {
                    childList.add(region);
                }
            }
        }
        /*// 把子菜单的子菜单再循环一遍
        for (DictRegion region : childList) {
            if (region.getStatus() == null) {
                // 递归
                region.setChildMenus(getChild(region.getId(), dictRegionList));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }*/
        return childList;
    }
}
