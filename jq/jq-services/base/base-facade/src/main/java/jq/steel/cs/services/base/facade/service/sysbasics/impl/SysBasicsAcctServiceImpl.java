package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateUtil;
import com.ebase.utils.secret.Md5Util;
import jq.steel.cs.services.base.api.vo.AcctInfoExcel;
import jq.steel.cs.services.base.api.vo.AcctInfoRoleVO;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.AcctRoleRealVO;
import jq.steel.cs.services.base.api.vo.AcctToRoleInfoVO;
import jq.steel.cs.services.base.api.vo.OrgInfoVO;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.dao.AcctInfoMapper;
import jq.steel.cs.services.base.facade.dao.AcctRoleRealMapper;
import jq.steel.cs.services.base.facade.dao.CompanyInfoMapper;
import jq.steel.cs.services.base.facade.dao.OrgInfoMapper;
import jq.steel.cs.services.base.facade.dao.RoleInfoMapper;
import jq.steel.cs.services.base.facade.model.AcctInfo;
import jq.steel.cs.services.base.facade.model.AcctRoleReal;
import jq.steel.cs.services.base.facade.model.CompanyInfo;
import jq.steel.cs.services.base.facade.model.OrgInfo;
import jq.steel.cs.services.base.facade.model.RoleInfo;
import jq.steel.cs.services.base.facade.service.sysbasics.SysBasicsAcctService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangyu
 */
@Service
public class SysBasicsAcctServiceImpl implements SysBasicsAcctService {

    private static Logger LOG = LoggerFactory.getLogger(SysBasicsAcctServiceImpl.class);



    @Autowired
    private AcctInfoMapper acctInfoMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private AcctRoleRealMapper acctRoleRealMapper;

    @Autowired
    private AcctInfoMapper AcctInfoMapper;

    @Autowired
    private OrgInfoMapper orgInfoMapper;


    /**
     *  模拟登录  zyh
     * @param reqBody
     * @return
     * @throws RuntimeException
     */
    @Override
    public AcctInfoVO LoginAcct(AcctInfoVO reqBody)throws RuntimeException{
        AcctInfo acctInfo= BeanCopyUtil.copy(reqBody,AcctInfo.class);
        AcctInfo resultData = acctInfoMapper.LoginAcct(acctInfo);
        AcctInfoVO acctRoleRealVO= BeanCopyUtil.copy(resultData,AcctInfoVO.class);
        return acctRoleRealVO;

    }

    /**
     * 根据角色查询用户   zyh
     * @param reqBody
     * @return
     * @throws RuntimeException
     */
    @Override
    public List<AcctInfoVO> selectRoleIdAcctInfo(AcctInfoVO reqBody)throws RuntimeException{
        AcctInfo acctInfo= BeanCopyUtil.copy(reqBody,AcctInfo.class);
        List<AcctInfo> resultData = acctInfoMapper.selectRoleIdAcctInfo(acctInfo);
        return BeanCopyUtil.copyList(resultData,AcctInfoVO.class);
    }

    public PageDTO<AcctInfoVO> selectOrgIdAcctInfo(AcctInfoVO jsonRequest)throws RuntimeException {
        AcctInfo acctInfo= BeanCopyUtil.copy(jsonRequest,AcctInfo.class);
        PageDTOUtil.startPage(jsonRequest);
        List<AcctInfo> list = acctInfoMapper.selectOrgIdAcctInfo(acctInfo);
        PageDTO<AcctInfo> page = PageDTOUtil.transform(list);

        //转换
        PageDTO<AcctInfoVO> pageVo = new PageDTO(page.getPageNum(),page.getPageSize());
        pageVo.setTotal(page.getTotal());
        List<AcctInfo> resultData = page.getResultData();

        List<AcctInfoVO> result = BeanCopyUtil.copyList(resultData, AcctInfoVO.class);
        pageVo.setResultData(result);
        return pageVo;
    }
   /* @Override
    public PageDTO<AcctInfoVO> listSysAcct(JsonRequest<AcctInfoVO> jsonRequest)throws RuntimeException {

        //这个对象 可直接操作数据库
        AcctInfoVO reqBody = jsonRequest.getReqBody();

        try{
            PageDTOUtil.startPage(reqBody);
            //可能还有更多参数
            List<AcctInfo> list = acctInfoMapper.find(reqBody);
            PageDTO<AcctInfo> page = PageDTOUtil.transform(list);

            //转成 vo 对象
            PageDTO<AcctInfoVO> pageVo = new PageDTO(page.getPageNum(),page.getPageSize());
            pageVo.setTotal(page.getTotal());
            List<AcctInfo> resultData = page.getResultData();

            List<AcctInfoVO> result = new ArrayList<>(resultData.size());
            for(AcctInfo acctInfo:resultData){
                AcctInfoVO acctInfoVO = new AcctInfoVO();

                //时间
                if(acctInfo.getStartTime() != null){
                    acctInfoVO.setStartTimeView(DateFormatUtils.format(acctInfo.getStartTime(),"yyyy-MM-dd"));
                }
                if(acctInfo.getEntTime() != null){
                    acctInfoVO.setEntTimeView(DateFormatUtils.format(acctInfo.getEntTime(),"yyyy-MM-dd"));
                }

                BeanCopyUtil.copy(acctInfo,acctInfoVO);

                //光copy 还不行
                CompanyInfo companyInfo = acctInfo.getCompanyInfo();
                if(companyInfo != null){
                    acctInfoVO.setaCompanyId(companyInfo.getCompanyId());
                    acctInfoVO.setCompanyCode(companyInfo.getCompanyCode());
                    acctInfoVO.setCompanyName(companyInfo.getCompanyName());
                }
                result.add(acctInfoVO);
            }

            pageVo.setResultData(result);
            return pageVo;
        } finally {
            PageDTOUtil.endPage();

        }


    }*/

    @Override
    public List<AcctInfoExcel> sysAcctListExcel(AcctInfoVO reqBody)throws RuntimeException{
        //先按 page 版查询
//        AcctInfoVO reqBody = jsonRequest.getReqBody();
        if(reqBody == null){
            reqBody = new AcctInfoVO();
        }
//            PageDTOUtil.startPage(reqBody);
        //可能还有更多参数
        List<AcctInfo> resultData = acctInfoMapper.find(reqBody);
//            PageDTO<AcctInfo> page = PageDTOUtil.transform(list);

        //转成 vo 对象
//            List<AcctInfo> resultData = page.getResultData();

        List<AcctInfoExcel> acctInfoExcels = BeanCopyUtil.copyList(resultData, AcctInfoExcel.class);

        int i = 0;
        for(AcctInfo acctInfo:resultData){

            AcctInfoExcel acctInfoExcel = acctInfoExcels.get(i);
            acctInfoExcel.setCompanyCode(acctInfo.getCompanyInfo().getCompanyCode());
            acctInfoExcel.setCompanyName(acctInfo.getCompanyInfo().getCompanyName());
            acctInfoExcel.setLine(i + 1);
            if(acctInfo.getEntTime() != null){
                String view = DateUtil.formatDate(acctInfo.getEntTime(), DateUtil.DATE_PATTERN);
                acctInfoExcel.setEntTimeView(view);
            }

            if(acctInfo.getStartTime() != null){
                String view = DateUtil.formatDate(acctInfo.getStartTime(), DateUtil.DATE_PATTERN);
                acctInfoExcel.setStartTimeView(view);
            }
            i ++;
        }
        return acctInfoExcels;

//            PageDTOUtil.endPage();


    }

    //角色  账号 关系  保存
    @Override
    public JsonResponse keepSysAcct(JsonRequest<List<AcctRoleRealVO>> jsonRequest)throws RuntimeException {
        JsonResponse jsonResponse = new JsonResponse();

        List<AcctRoleRealVO> reqBody = jsonRequest.getReqBody();

        for(AcctRoleRealVO acctRoleRealVO:reqBody){
            String opt = acctRoleRealVO.getOpt();
            if(SysPramType.DELETE.getMsg().equals(opt)){

                acctRoleRealMapper.deleteByPrimaryKey(acctRoleRealVO.getRelaId());
            }else if(SysPramType.UPDATE.getMsg().equals(opt)){
                AcctRoleReal acctRoleReal = new AcctRoleReal();

                BeanCopyUtil.copy(acctRoleRealVO,acctRoleReal);
                acctRoleRealMapper.updateByPrimaryKeySelective(acctRoleReal);
            }else if(SysPramType.INSERT.getMsg().equals(opt)){

                AcctRoleReal acctRoleReal = new AcctRoleReal();

                BeanCopyUtil.copy(acctRoleRealVO,acctRoleReal);

                acctRoleRealMapper.insertSelective(acctRoleReal);
            }

        }

        return jsonResponse;
    }

    /**
     * 保存接口
     * @param jsonRequest
     * @return
     */
    @Override
    public JsonResponse addSysAcct(JsonRequest<AcctInfoVO> jsonRequest) throws RuntimeException{

        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = new AcctInfo();

        BeanCopyUtil.copy(reqBody,acctInfo);

        if(SysPramType.INSERT.getMsg().equals(reqBody.getOpt())){
            acctInfo.setAcctId(null);
            acctInfoMapper.insertSelective(acctInfo);
        }else if(SysPramType.UPDATE.getMsg().equals(reqBody.getOpt())){
            acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
        }


        return jsonResponse;
    }

    /**
     * 获得详情接口
     * @param reqBody
     * @return
     */
    @Override
    public AcctInfoRoleVO getSysAcct(AcctInfoVO reqBody)throws RuntimeException {

        AcctInfoRoleVO acctInfoRoleVO = new AcctInfoRoleVO();

        Long acctId = reqBody.getAcctId(); //用这个id 去库里 楼数据



        List<AcctInfo> acctInfos = acctInfoMapper.find(reqBody);

        if(CollectionUtils.isEmpty(acctInfos)){
            LOG.info("未查询到数据！！");
            return acctInfoRoleVO;
        }
        AcctInfo acctInfo = acctInfos.get(0);

        BeanCopyUtil.copy(acctInfo,acctInfoRoleVO);

        BeanCopyUtil.copy(acctInfo.getCompanyInfo(),acctInfoRoleVO);
        //所有的角色
        List<RoleInfo> roleInfos = roleInfoMapper.find(new RoleInfo());
        if(CollectionUtils.isNotEmpty(acctInfos)){
            List<RoleInfoVO> roleInfoVOS = BeanCopyUtil.copyList(roleInfos, RoleInfoVO.class);
            acctInfoRoleVO.setRoleInfoVOs(roleInfoVOS);
        }

        return acctInfoRoleVO;
    }

    @Override
    public JsonResponse getSysAcctInfo(JsonRequest<AcctInfoVO> jsonRequest) throws RuntimeException{
        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = acctInfoMapper.selectByPrimaryKey(reqBody.getAcctId());

        if(acctInfo != null){
            AcctInfoVO acctInfoVO = new AcctInfoVO();
            BeanCopyUtil.copy(acctInfo,acctInfoVO);

            jsonResponse.setRspBody(acctInfoVO);
        }
        return jsonResponse;
    }




    //用户下角色管理 - 查询
    @Override
    public List<AcctInfoVO> listSysAcct2Role(JsonRequest<AcctInfoVO> jsonRequest) {
        AcctInfoVO  reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = new AcctInfo();

        BeanCopyUtil.copy(reqBody,acctInfo);
        //原始数据
        List<AcctInfo> acctInfoList = AcctInfoMapper.listSysAcct2Role(acctInfo);

//        List<AcctInfoVO> result = BeanCopyUtil.copyList(acctInfoList, AcctInfoVO.class);
        List<AcctInfoVO> result = new ArrayList<>(acctInfoList.size());
        for(AcctInfo acctInfoMd:acctInfoList){
            AcctInfoVO acctInfoVO = new AcctInfoVO();
            BeanCopyUtil.copy(acctInfoMd,acctInfoVO);
            result.add(acctInfoVO);

            acctInfoVO.setRoleInfo(BeanCopyUtil.copyList(acctInfoMd.getRoleInfo(),AcctInfoVO.class));

        }

        return result;
    }


    //用户下角色管理 - 中间表添加 zhaoyichen
    @Override
    public JsonResponse addSysAcct2Role(JsonRequest<AcctRoleRealVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();

        AcctRoleRealVO acctRoleRealVO = jsonRequest.getReqBody();

        AcctRoleReal reqBody = BeanCopyUtil.copy(acctRoleRealVO, AcctRoleReal.class);

        String existence = reqBody.getExistence();

        if (StringUtils.isEmpty(existence)) {
            jsonResponse.setRetDesc("acct缺少参数！");
            jsonResponse.setRetDesc("acct字段值不正确！");
            return jsonResponse;
        }
        if (SysPramType.INSERT.getMsg().equals(existence)) {


            reqBody.setAcctId(reqBody.getAcctId());
            reqBody.setRelaId(reqBody.getRoleId());

            acctRoleRealMapper.addSysAcct2Role(reqBody);
        }

        return jsonResponse;
    }

    //用户下角色管理 -  中间表删除
    @Override
    public JsonResponse deleteSysAcct2Role2(JsonRequest<AcctRoleRealVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        AcctRoleRealVO acctRoleRealVO = jsonRequest.getReqBody();

        AcctRoleReal reqBody = BeanCopyUtil.copy(acctRoleRealVO, AcctRoleReal.class);

        String existence = reqBody.getExistence();

        if (StringUtils.isEmpty(existence)) {
            jsonResponse.setRetDesc("acct缺少参数！");
            jsonResponse.setRetDesc("acct字段值不正确！");
            return jsonResponse;
        }
        if (SysPramType.DELETE.getMsg().equals(existence)) {

            acctRoleRealMapper.deleteByPrimaryKey3(reqBody);

        }
        return jsonResponse;

    }


    //用户下角色管理 -  全删除
    @Override
    public JsonResponse deleteSysAcct2Role(JsonRequest<AcctInfoVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO acctInfoVO = jsonRequest.getReqBody();

        AcctInfo reqBody = BeanCopyUtil.copy(acctInfoVO, AcctInfo.class);

        String existence = reqBody.getExistence();

        if (StringUtils.isEmpty(existence)) {
            jsonResponse.setRetDesc("acct缺少参数！");
            jsonResponse.setRetDesc("acct字段值不正确！");
            return jsonResponse;
        }
        if (SysPramType.DELETE.getMsg().equals(existence)) {

            acctRoleRealMapper.deleteByPrimaryKey(reqBody.getAcctId());

        }
        return jsonResponse;

    }


    @Override
    public JsonResponse sysAcctDiscontinuation(JsonRequest<AcctInfoVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = new AcctInfo();

        BeanCopyUtil.copy(reqBody,acctInfo);

        acctInfoMapper.updateByPrimaryKeySelective(acctInfo);

        return jsonResponse;
    }

    @Override
    public JsonResponse sysAcctDeleteById(JsonRequest<AcctInfoVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = new AcctInfo();

        BeanCopyUtil.copy(reqBody,acctInfo);
        AcctRoleReal acctRoleReal = new AcctRoleReal();
        acctRoleReal.setAcctId(acctInfo.getAcctId());
        acctRoleRealMapper.deleteAcctRole(acctRoleReal);

        acctInfo.setIsDelete((byte)1);
        acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
        return jsonResponse;
    }


    /**
     * 用户添加
     * @param jsonRequest
     * @return
     */
    @Override
    public JsonResponse sysAcctAddUser(JsonRequest<AcctToRoleInfoVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();
        AcctToRoleInfoVO reqBody = jsonRequest.getReqBody();
        AcctInfo acctInfo = new AcctInfo();
        acctInfo.setAcctTitle(reqBody.getAcctTitle());

        BeanCopyUtil.copy(reqBody,acctInfo);
        List<AcctInfo> list = acctInfoMapper.selectAll(acctInfo);
        if(acctInfo.getAcctId()==null||acctInfo.getAcctId()==' ') {

            if (CollectionUtils.isNotEmpty(list)) {
                for (AcctInfo acctInfo1 : list) {
                    if (reqBody.getAcctTitle().equals(acctInfo1.getAcctTitle())) {
                        jsonResponse.setRetCode("0701006");
                        return jsonResponse;
                    }

                    if (reqBody.getEmail().equals(acctInfo1.getEmail())) {
                        jsonResponse.setRetCode("0401004");
                        return jsonResponse;
                    }

                    if (reqBody.getMobilePhone().equals(acctInfo1.getMobilePhone())) {
                        jsonResponse.setRetCode("0401005");
                        return jsonResponse;
                    }
                }
            }

            acctInfo.setStatus((byte)1);
            acctInfo.setIsDelete((byte)0);
            acctInfo.setAcctPassword(Md5Util.encrpt(acctInfo.getAcctPassword()));
//            if(StringUtils.isEmpty(acctInfo.getoInfoId())) {
//                String id=getOrgInfoId();
//                OrgInfo orgInfo=new OrgInfo();
//                orgInfo.setId(id);
//                orgInfo.setStatus("1");
//                orgInfo.setCreatedBy("创建人");
//                orgInfo.setCreatedTime(new Date());
//                orgInfo.setParentId("1");
//                orgInfoMapper.insertOrgInfo(orgInfo);
//                acctInfo.setoInfoId(id);
//            }
            //添加 用户表
            acctInfoMapper.insertAcctInfo(acctInfo);

//            if(acctInfo.getAcctType()==1){
//                //创建默认管理员角色
//                RoleInfo roleInfo=new RoleInfo();
//                roleInfo.setCreatedBy("创建人");
//                roleInfo.setCreatedTime(new Date());
//                roleInfo.setIsDelete("0");
//                roleInfo.setStatus("1");
//                roleInfo.setRoleTitle("管理员");
//                roleInfo.setOrgId(acctInfo.getoInfoId());
//                roleInfoMapper.insertSelective(roleInfo);
//                //创建默认角色与用户关联
//                AcctRoleReal acctRoleReal = new AcctRoleReal();
//                acctRoleReal.setAcctId(acctInfo.getAcctId());
//                acctRoleReal.setRoleId(roleInfo.getRoleId());
//                acctRoleRealMapper.insertSelective(acctRoleReal);
//            }

            List<Long> roleIds = reqBody.getRoleIds();
            for(Long r: roleIds){
                AcctRoleReal acctRoleReal = new AcctRoleReal();
                acctRoleReal.setAcctId(acctInfo.getAcctId());
                acctRoleReal.setRoleId(r);
                acctRoleRealMapper.insertSelective(acctRoleReal);
            }
        }else if(acctInfo.getAcctId()!=null){
            if (CollectionUtils.isNotEmpty(list)) {
                for (AcctInfo acctInfo1 : list) {
                    if (!acctInfo1.getAcctId().equals(reqBody.getAcctId())) {
                        if (!acctInfo1.getAcctId().equals(reqBody.getAcctId()) && reqBody.getAcctTitle().equals(acctInfo1.getAcctTitle())) {
                            jsonResponse.setRetCode("0701006");
                            return jsonResponse;
                        }

                        if (!acctInfo1.getAcctId().equals(reqBody.getAcctId()) && reqBody.getEmail().equals(acctInfo1.getEmail())) {
                            jsonResponse.setRetCode("0401004");
                            return jsonResponse;
                        }

                        if (!acctInfo1.getAcctId().equals(reqBody.getAcctId()) && reqBody.getMobilePhone().equals(acctInfo1.getMobilePhone())) {
                            jsonResponse.setRetCode("0401005");
                            return jsonResponse;
                        }
                    }

                }
            }
            //用户表修改
            AcctInfo acctInfo1 = acctInfoMapper.selectByPrimaryKey(acctInfo.getAcctId());
            if (!acctInfo.getAcctPassword().equals(acctInfo1.getAcctPassword())) {
                acctInfo.setAcctPassword(Md5Util.encrpt(acctInfo.getAcctPassword()));
            }

            this.acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
            List<Long> roleIds = reqBody.getRoleIds();
            AcctRoleReal acctRoleReal = new AcctRoleReal();
            acctRoleReal.setAcctId(acctInfo.getAcctId());
            acctRoleRealMapper.deleteAcctRole(acctRoleReal);
            for(Long r: roleIds){
                AcctRoleReal acctRoleRea2 = new AcctRoleReal();
                acctRoleRea2.setAcctId(acctInfo.getAcctId());
                acctRoleRea2.setRoleId(r);
                acctRoleRealMapper.insertSelective(acctRoleRea2);
            }
        }


        /* acctToRoleInfoVO.setRoleIds(reqBody.getRoleIds());*/
        //添加角色 用户  中间表



        return jsonResponse;
    }


    //创建虚拟组织ID
    public String getOrgInfoId() {
        String id = orgInfoMapper.getOrgInfoId("1");
        //获取最高节点如果存在加一，否则创建一个
        if(id != null) {
            BigInteger result1=new BigInteger(id);
            BigInteger result2=new BigInteger("1");
            id=result1.add(result2).toString();
        }else {
            id = "10000000000";
        }
        return id;
    }

    /**
     * 用户分页查询
     * @param jsonRequest
     * @return
     * @throws RuntimeException
     */

    @Override
    public PageDTO<AcctInfoVO> listSysAcct(JsonRequest<AcctInfoVO> jsonRequest)throws RuntimeException {

        //这个对象 可直接操作数据库
        AcctInfoVO reqBody = jsonRequest.getReqBody();

        try{
            PageDTOUtil.startPage(reqBody);
            if(reqBody.getAcctType()==1){
                reqBody.setAcctType(Long.parseLong("2"));
            }
            if(reqBody.getAcctType()==0){
                reqBody.setAcctType(Long.parseLong("1"));
                reqBody.setoInfoId(null);
            }

            //获取用户信息
            List<AcctInfo> list = acctInfoMapper.findPage(reqBody);

            PageDTO<AcctInfo> page = PageDTOUtil.transform(list);
            for(AcctInfo  l:list){
                AcctInfo acctId=new AcctInfo();
                acctId.setAcctId(l.getAcctId());
                List<AcctInfo> list1=acctInfoMapper.findAcctRoleInfo(acctId);
                List<RoleInfo> roleList =new ArrayList<>();
                if(!CollectionUtils.isEmpty(list1)){
                    if(!CollectionUtils.isEmpty(list1.get(0).getRoleInfo())){
                        roleList=list1.get(0).getRoleInfo();
                    }
                }
                l.setRoleArr(roleList);
                List<RoleInfo> roleArr1 = l.getRoleArr();
                OrgInfo org = getOrgInfoByTree(l.getoInfoId());
                l.setOrgInfo(org);
            }

            //转成 vo 对象
            PageDTO<AcctInfoVO> pageVo = new PageDTO(page.getPageNum(),page.getPageSize());
            pageVo.setTotal(page.getTotal());
            List<AcctInfo> resultData = page.getResultData();

            List<AcctInfoVO> result = new ArrayList<>(resultData.size());

            for(AcctInfo acctInfo:resultData){
                AcctInfoVO acctInfoVO = new AcctInfoVO();

                //时间
                if(acctInfo.getStartTime() != null){
                    acctInfoVO.setStartTimeView(DateFormatUtils.format(acctInfo.getStartTime(),"yyyy-MM-dd"));
                }
                if(acctInfo.getEntTime() != null){
                    acctInfoVO.setEntTimeView(DateFormatUtils.format(acctInfo.getEntTime(),"yyyy-MM-dd"));
                }

                BeanCopyUtil.copy(acctInfo,acctInfoVO);

                //光copy 还不行
                CompanyInfo companyInfo = acctInfo.getCompanyInfo();
                if(companyInfo != null){
                    acctInfoVO.setaCompanyId(companyInfo.getCompanyId());
                    acctInfoVO.setCompanyCode(companyInfo.getCompanyCode());
                    acctInfoVO.setCompanyName(companyInfo.getCompanyName());
                }

                OrgInfoVO orgInfoVO = new OrgInfoVO();
                //前端需要把树改成List结构渲染
                List<OrgInfoVO> orgList = new ArrayList<>();
                copyOrgByTree(orgInfoVO, acctInfo.getOrgInfo(), orgList);
                acctInfoVO.setOrgInfo(orgInfoVO);
                acctInfoVO.setOrgArr(orgList);
                result.add(acctInfoVO);
            }

            pageVo.setResultData(result);
            return pageVo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageDTOUtil.endPage();

        }
        return null;
    }


/**
 * 用户分页查询
 * @param jsonRequest
 * @return
 * @throws RuntimeException
 */
/*@Override
public PageDTO<AcctInfoVO> listSysAcct(JsonRequest<AcctInfoVO> jsonRequest)throws RuntimeException {

    //这个对象 可直接操作数   据库
    AcctInfoVO reqBody = jsonRequest.getReqBody();
    AcctInfo ai=new AcctInfo();
    BeanCopyUtil.copy(reqBody,ai);
    try{
        PageDTOUtil.startPage(ai);
        if(ai.getAcctType()==1){
            ai.setAcctType(Long.parseLong("2"));
        }
        if(ai.getAcctType()==0){
            ai.setAcctType(Long.parseLong("1"));
            ai.setoInfoId(null);
        }
        //获取用户信息
        List<AcctInfo> list = acctInfoMapper.findPage(ai);

        PageDTO<AcctInfo> page = PageDTOUtil.transform(list);
        List<AcctInfo> resultData1 = page.getResultData();
        for(AcctInfo  l:resultData1){

//                AcctInfo acctId=new AcctInfo();
//                acctId.setAcctId(l.getAcctId());
//                List<AcctInfo> list1=acctInfoMapper.findAcctRoleInfo(acctId);
//                List<RoleInfo> roleList =new ArrayList<>();
//                if(!CollectionUtils.isEmpty(list1)){
//                    if(!CollectionUtils.isEmpty(list1.get(0).getRoleInfo())){
//                        roleList=list1.get(0).getRoleInfo();
//                    }
//                }
//                l.setRoleArr(roleList);

//                OrgInfo org = getOrgInfoByTree(l.getoInfoId());
//                l.setOrgInfo(org);
        }
        System.out.println(page.getPageNum()+"-"+page.getPageSize());
        //转成 vo 对象
        PageDTO<AcctInfoVO> pageVo = new PageDTO(reqBody.getPageNum(),reqBody.getPageSize());
        pageVo.setTotal(page.getTotal());
        List<AcctInfo> resultData = page.getResultData();

        List<AcctInfoVO> result = new ArrayList<>(resultData.size());

        for(AcctInfo acctInfo:resultData){
            AcctInfoVO acctInfoVO = new AcctInfoVO();

            //时间
            if(acctInfo.getStartTime() != null){
                acctInfoVO.setStartTimeView(DateFormatUtils.format(acctInfo.getStartTime(),"yyyy-MM-dd"));
            }
            if(acctInfo.getEntTime() != null){
                acctInfoVO.setEntTimeView(DateFormatUtils.format(acctInfo.getEntTime(),"yyyy-MM-dd"));
            }

            BeanCopyUtil.copy(acctInfo,acctInfoVO);

            //光copy 还不行
            CompanyInfo companyInfo = acctInfo.getCompanyInfo();
            if(companyInfo != null){
                acctInfoVO.setaCompanyId(companyInfo.getCompanyId());
                acctInfoVO.setCompanyCode(companyInfo.getCompanyCode());
                acctInfoVO.setCompanyName(companyInfo.getCompanyName());
            }

//                OrgInfoVO orgInfoVO = new OrgInfoVO();
//                //前端需要把树改成List结构渲染
//                List<OrgInfoVO> orgList = new ArrayList<>();
//                copyOrgByTree(orgInfoVO, acctInfo.getOrgInfo(), orgList);
//                acctInfoVO.setOrgInfo(orgInfoVO);
//                acctInfoVO.setOrgArr(orgList);
//                result.add(acctInfoVO);

            result.add(acctInfoVO);
        }

        pageVo.setResultData(result);
        return pageVo;
    } finally {
        PageDTOUtil.endPage();

    }
}*/

    /**
     * 根据用户查询此用户关联的角色和组织信息
     * @return
     */
    @Override
    public AcctInfoVO getAcctInfo(AcctInfoVO acctInfoVO) {

        AcctInfo acctInfo=new AcctInfo();
        BeanCopyUtil.copy(acctInfoVO,acctInfo);
        AcctInfo acctId=new AcctInfo();
        acctId.setAcctId(acctInfo.getAcctId());
        List<AcctInfo> list1 = acctInfoMapper.findAcctRoleInfo(acctId);
        acctInfo=list1.get(0);
        if(!CollectionUtils.isEmpty(list1)){
            List<RoleInfo> roleList =acctInfo.getRoleInfo();
            acctInfo.setRoleArr(roleList);

            OrgInfo org = getOrgInfoByTree(acctInfo.getoInfoId());
            acctInfo.setOrgInfo(org);
        }



        AcctInfoVO acctInfoVO1=BeanCopyUtil.copy(acctInfo,AcctInfoVO.class);

        OrgInfoVO orgInfoVO = new OrgInfoVO();
        //前端需要把树改成List结构渲染
        List<OrgInfoVO> orgList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list1)) {
            getParentToCurrent(orgInfoVO, acctInfo.getOrgInfo(), orgList, acctInfoVO.getOrgId());
        }
        acctInfoVO1.setOrgInfo(orgInfoVO);
        acctInfoVO1.setOrgArr(orgList);

        return acctInfoVO1;
    }

    @Override
    public AcctInfoVO getAcctInfoByAcctTitle(AcctInfoVO jsonRequest) {
        AcctInfo acctInfo = new AcctInfo();
        acctInfo.setAcctTitle(jsonRequest.getAcctTitle());

        AcctInfo result = acctInfoMapper.getAcctInfoByAcctTitle(acctInfo);

        return BeanCopyUtil.copy(result, AcctInfoVO.class);
    }

    private OrgInfoVO copyOrgByTree(OrgInfoVO orgInfoVO, OrgInfo orgInfo, List<OrgInfoVO> orgList) {
        BeanCopyUtil.copy(orgInfo, orgInfoVO);
        if (StringUtils.isEmpty(orgInfo)) {
            OrgInfoVO orgInfoVO1=new OrgInfoVO();
            return orgInfoVO1;
        }else {
            OrgInfo parent = orgInfo.getParent();

            if (parent != null) {
                OrgInfoVO parentVO = copyOrgByTree(new OrgInfoVO(), parent, orgList);
                orgInfoVO.setParent(parentVO);
                orgList.add(parentVO);
            }
            return orgInfoVO;
        }
    }

    private OrgInfoVO getParentToCurrent(OrgInfoVO orgInfoVO, OrgInfo orgInfo, List<OrgInfoVO> orgList, String currentOrgId) {
        BeanCopyUtil.copy(orgInfo, orgInfoVO);
        if (StringUtils.isEmpty(orgInfo)) {
            OrgInfoVO orgInfoVO1=new OrgInfoVO();
            return orgInfoVO1;
        }else {
            OrgInfo parent = orgInfo.getParent();

            if (parent != null && parent.getId().equals(currentOrgId)) {
                OrgInfoVO result = new OrgInfoVO();
                BeanCopyUtil.copy(parent, result);
                orgInfoVO.setParent(result);
                orgList.add(result);
                return orgInfoVO;
            }

            if (parent != null) {
                OrgInfoVO parentVO = getParentToCurrent(new OrgInfoVO(), parent, orgList, currentOrgId);
                orgInfoVO.setParent(parentVO);
                orgList.add(parentVO);
            }
            return orgInfoVO;
        }
    }

    /**
     * 递归 子-》父
     * @param orgId
     * @return
     */
    public OrgInfo getOrgInfoByTree(String orgId) {
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setParentId(orgId);
        OrgInfo org = orgInfoMapper.selectOrgInfoAcctInfo(orgInfo);

        getParent(org);
        return org;
    }

    /**
     *  递归 判断parentId是否为空
     * @param orgInfo
     */
    private void getParent(OrgInfo orgInfo) {
        if (StringUtils.isEmpty(orgInfo)) {
            return;
        }else {
            String parentId = orgInfo.getParentId();
            if (parentId == null || parentId == "")
                return;
            OrgInfo parent = orgInfoMapper.selectOrgInfo2(orgInfo);
            orgInfo.setParent(parent);
            getParent(parent);
        }
    }

    //客户类型获取
    @Override
    public AcctInfoVO customerType(AcctInfoVO record) {
        record.setAcctType(Long.valueOf(record.getAcctType()));
        return record;
    }

    @Override
    public ServiceResponse<Integer> updateAcctInfo(AcctInfoVO acctInfoVO) {
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        if (null == acctInfoVO.getAcctId()) {
            serviceResponse.setResponseCode("0000001");
            return serviceResponse;
        }
        AcctInfo acctInfo = new AcctInfo();
        BeanCopyUtil.copy(acctInfoVO, acctInfo);
        List<AcctInfo> list = acctInfoMapper.selectAll(acctInfo);
        if (CollectionUtils.isNotEmpty(list)) {
            for (AcctInfo acctInfo1 : list) {
                if (!acctInfo1.getAcctId().equals(acctInfoVO.getAcctId())) {
                    if ((!acctInfo1.getAcctId().equals(acctInfoVO.getAcctId())) && acctInfoVO.getAcctTitle().equals(acctInfo1.getAcctTitle())) {
                        serviceResponse.setResponseCode("0701006");
                        return serviceResponse;
                    }

                    if ((!acctInfo1.getAcctId().equals(acctInfoVO.getAcctId())) && acctInfoVO.getEmail().equals(acctInfo1.getEmail())) {
                        serviceResponse.setResponseCode("0401004");
                        return serviceResponse;
                    }

                    if ((!acctInfo1.getAcctId().equals(acctInfoVO.getAcctId())) && acctInfoVO.getMobilePhone().equals(acctInfo1.getMobilePhone())) {
                        serviceResponse.setResponseCode("0401005");
                        return serviceResponse;
                    }
                }

            }
        }
        int i = acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
        serviceResponse.setRetContent(i);
        return serviceResponse;
    }
}
