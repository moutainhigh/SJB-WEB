package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.IntfzWebProjectInfoService;
import com.sijibao.oa.crm.api.exception.ServiceException;
import com.sijibao.oa.crm.api.request.project.ProjectContact;
import com.sijibao.oa.crm.api.request.project.ProjectInfoSaveReq;
import com.sijibao.oa.crm.api.request.project.ProjectNodeReq;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.projectinfo.*;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.oa.dao.CustInfoDao;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP协作流程相关
 *
 * @author Jianghao Zhang
 */
@Controller
@RequestMapping(value = "wechat/projectInfo")
@Api(value = "APP项目管理服务", tags = "APP项目管理服务")
public class IntfzProjectInfoController extends BaseController {

    @Autowired
    private IntfzWebProjectInfoService intfzWebProjectInfoService;
    @Autowired
    private CustInfoDao custInfoDao;

    /**
     * APP项目管理-保存项目信息
     */
    @RequestMapping(value = "saveProjectInfo")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "APP项目管理-保存项目信息")
    @AppAuthority
    public BaseResp<String> saveProjectInfo(@Valid @RequestBody MainAppProjectInfoSaveReq projectInfoSaveReq,
											@ApiParam(value = "clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											@ApiParam(value = "客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											HttpServletRequest request, HttpServletResponse response) {
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前操作用户信息
        CharChangeUtils.CharChange(projectInfoSaveReq);//替换英文字符
        projectInfoSaveReq.setProducSide("APP");
        ArrayList<ProjectNodeReq> list = Lists.newArrayList();
        for(MainProjectNodeReq req :projectInfoSaveReq.getProjectNodeReqs()){
            CharChangeUtils.CharChange(req);//替换英文字符
            ProjectNodeReq cc = change(req, ProjectNodeReq.class);
            list.add(cc);
        }
        try {
            //校验重复名称
            int byNameCount = intfzWebProjectInfoService.findByNameCount(projectInfoSaveReq.getId(),projectInfoSaveReq.getProjectName());
            if(byNameCount > 0){
                return new BaseResp<>(IntfzRs.ERROR,"项目名称重复", null);
            }
            int projectByNameCount = intfzWebProjectInfoService.findProjectByNameCount(projectInfoSaveReq.getProcessFlag(),projectInfoSaveReq.getProjectName());
            if(projectByNameCount > 0){
                return new BaseResp<>(IntfzRs.ERROR,"项目名称已在流程立项中重复", null);
            }

            ProjectInfoSaveReq saveReq = change(projectInfoSaveReq, ProjectInfoSaveReq.class);
            //企业只能单选了，web端接收的时候还是用list接收的
            saveReq.setHolderCode(Arrays.asList(projectInfoSaveReq.getHolderCode()));
            saveReq.setProjectNodeReqs(list);
            if (StringUtils.isNotBlank(projectInfoSaveReq.getCustInfoId()) && custInfoDao.get(projectInfoSaveReq.getCustInfoId()) != null) {
                saveReq.setCustInfoName(custInfoDao.get(projectInfoSaveReq.getCustInfoId()).getCustName());
            }

            if (StringUtils.isNotBlank(projectInfoSaveReq.getImpleLeaderId()) && UserUtils.get(projectInfoSaveReq.getImpleLeaderId()) != null) {
                saveReq.setImpleLeaderName(UserUtils.get(projectInfoSaveReq.getImpleLeaderId()).getName());
            }
            if(StringUtils.isNotBlank(projectInfoSaveReq.getProjectManagerId()) && UserUtils.get(projectInfoSaveReq.getProjectManagerId()) != null){
                saveReq.setProjectManager(UserUtils.get(projectInfoSaveReq.getProjectManagerId()).getName());
            }
            if(projectInfoSaveReq.getCarrierGoods()!=null&&projectInfoSaveReq.getCarrierGoods().size()>0){
                saveReq.setCarrierGoods(StringUtils.join(projectInfoSaveReq.getCarrierGoods(),","));
            }
            //一般需求
            if(projectInfoSaveReq.getGeneralRequire()!=null){
                MainProjectGeneralRequire generalRequire = projectInfoSaveReq.getGeneralRequire();
                if(0==generalRequire.getProjectTrusteeshipt()){
                    generalRequire.setTrusteeshiptChannel(null);
                }
                saveReq.setGeneralRequires(JSON.toJSONString(generalRequire));
                logger.debug("【项目管理】项目一般需求：{}", JSON.toJSONString(generalRequire));
            }
            //特殊需求
            if(projectInfoSaveReq.getSpecialRequire()!=null){
                MainProjectSpecialRequire specialRequire = projectInfoSaveReq.getSpecialRequire();
                if(0==specialRequire.getReturnPoint()){
                    specialRequire.setReturnPointProportion(null);
                }
                saveReq.setSpecialRequires(JSON.toJSONString(specialRequire));
                logger.debug("【项目管理】项目特殊需求：{}", JSON.toJSONString(specialRequire));
            }
            ArrayList<ProjectContact> contacts=Lists.newArrayList();
            for(MainProjectContact contact:projectInfoSaveReq.getMainProjectContacts()){
                ProjectContact projectContact=change(contact,ProjectContact.class);
                contacts.add(projectContact);
            }
            saveReq.setProjectContacts(contacts);
            intfzWebProjectInfoService.saveProjectInfo(saveReq, user.getId());
        } catch (ServiceException e) {
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), null);
        }
        return new BaseResp<>(IntfzRs.SUCCESS, "新建项目成功", null);
    }
}