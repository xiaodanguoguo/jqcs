package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.dao.CrmMillSheetSplitApplyMapper;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHostsMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetRebackApply;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetSplitApply;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSheetHostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MillSheetHostsServiceImpl implements MillSheetHostsService{

    @Autowired
    private  MillSheetHostsMapper millSheetHostsMapper;
    @Autowired
    private CrmMillSheetSplitApplyMapper crmMillSheetSplitApplyMapper;
    @Autowired
    private MillCoilInfoMapper millCoilInfoMapper;


    @Override
    public PageDTO<MillSheetHostsVO> findMillSheetByPage(MillSheetHostsVO millSheetHostsVO) {
        try {
        //转换mdel
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
        if(millSheetHosts.getZcharg()!=null && millSheetHosts.getZcharg()!=""){
            MillCoilInfo coilInfo = new MillCoilInfo();
            coilInfo.setZcharg(millSheetHosts.getZcharg());
            List<MillCoilInfo> list = millCoilInfoMapper.findMillsheetNumber(coilInfo);
            List<String> idall = new ArrayList<>();
           for (int i = 0; i < list.size(); i++){
               idall.add(list.get(i).getMillsheetNo());
           }

            millSheetHosts.setMillSheetNos(idall);
        }
        PageDTOUtil.startPage(millSheetHostsVO);
        String startDtStr = DateFormatUtil.getStartDateStr(millSheetHosts.getStartDt());
        millSheetHosts.setStartDtStr(startDtStr);
        String endDtStr = DateFormatUtil.getEndDateStr(millSheetHosts.getEndDt());
        millSheetHosts.setEndDtStr(endDtStr);
        List<MillSheetHosts> millSheetByPage = millSheetHostsMapper.findMillSheetByPage(millSheetHosts);
        // 分页对象
        PageDTO<MillSheetHostsVO> transform = PageDTOUtil.transform(millSheetByPage, MillSheetHostsVO.class);
            for (MillSheetHostsVO millSheetHosts2:transform.getResultData()){
                millSheetHosts = new MillSheetHosts();
                BeanCopyUtil.copy(millSheetHosts2, millSheetHosts);
                List<CrmMillSheetSplitApply> crmMillSheetSplitApplies = crmMillSheetSplitApplyMapper.findFmillSheet(millSheetHosts);
                if (crmMillSheetSplitApplies.size()>0){
                    millSheetHosts2.setIsSplit(1);
                }else {
                    millSheetHosts2.setIsSplit(0);
                }
            }
        return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public List<MillSheetHostsVO> findUrl(List<MillSheetHostsVO> millSheetHostsVOS) {
        for(MillSheetHostsVO millSheetHostsVO:millSheetHostsVOS){
            //转换mdel
            MillSheetHosts millSheetHosts = new MillSheetHosts();
            BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
            MillSheetHosts millSheetByPage = millSheetHostsMapper.findUrl(millSheetHosts);
            millSheetByPage.setMillSheetPath(millSheetByPage.getMillSheetUrl() +"/"+millSheetByPage.getMillSheetName());
            BeanCopyUtil.copy(millSheetByPage,millSheetHostsVO);
        }
        return millSheetHostsVOS;
    }

    @Override
    public List<MillSheetHostsVO> findDownUrl(List<String> list) {
        List<MillSheetHosts> millSheetHosts = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            MillSheetHosts millSheetHosts1 = new MillSheetHosts();
            millSheetHosts1.setMillSheetNo(list.get(i));
            millSheetHosts.add(millSheetHosts1);
        }
        for (MillSheetHosts millSheetHosts1:millSheetHosts){
            MillSheetHosts url = millSheetHostsMapper.findUrl(millSheetHosts1);
            millSheetHosts1.setMillSheetPath(url.getMillSheetUrl()+"/"+url.getMillSheetName());

            //修改下载次数
            millSheetHosts1.setDownableNum(url.getDownableNum()-1);
            millSheetHosts1.setDownNum(url.getDownNum()+1);
            //millSheetHosts1.setUpdatedBy(orgName);
            millSheetHosts1.setUpdatedDt(new Date());
            millSheetHostsMapper.updateNum(millSheetHosts1);
            millSheetHosts1.setMillSheetName(url.getMillSheetName());
        }
        //转换返回对象
        List<MillSheetHostsVO> millSheetHostsVOS = BeanCopyUtil.copyList(millSheetHosts, MillSheetHostsVO.class);
        return millSheetHostsVOS;
    }


    @Override
    public MillSheetHostsVO rollbackQuery(MillSheetHostsVO millSheetHostsVO) {
        //转换mdel
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
        List<MillSheetHosts> list = millSheetHostsMapper.findMillsheetNo(millSheetHosts);
        if (list.size()>0){
            millSheetHostsVO.setTrue(true);
            millSheetHostsVO.setMillSheetPath(list.get(0).getMillSheetUrl()+"/"+list.get(0).getMillSheetName());
        }else {
            //校验质证书编号
            millSheetHostsVO.setTrue(false);
        }
        return millSheetHostsVO;
    }

    //查询质证书编号是否有效
    @Override
    public MillSheetHostsVO findIsTrue(MillSheetHostsVO millSheetHostsVO) {
        //转换mdel
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
        List<MillSheetHosts> list =millSheetHostsMapper.findIsTrue(millSheetHosts);
        if (list.size()>0){
            millSheetHosts.setTrue(true);
        }else {
            millSheetHosts.setTrue(false);
            millSheetHosts.setCheckInstructions("请核实质证书编号"+millSheetHostsVO.getMillSheetNo());
        }

        BeanCopyUtil.copy(millSheetHosts,millSheetHostsVO);
        return millSheetHostsVO;
    }
}
