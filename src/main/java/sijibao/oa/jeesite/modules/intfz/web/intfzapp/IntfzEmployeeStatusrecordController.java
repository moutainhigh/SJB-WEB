package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.crm.api.IntfzWebProjectInfoService;
import com.sijibao.oa.crm.api.response.project.ProjectNodeDetailResponse;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.employeestatus.EmployeeStatusListReq;
import com.sijibao.oa.modules.intfz.request.employeestatus.EmployeeStatusSaveReq;
import com.sijibao.oa.modules.intfz.request.intfzwebreq.projectinfo.MainProjectReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.employeestatus.EmployeeStatusrecordResponse;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.projectinfo.MainProjectNodeDetailResponse;
import com.sijibao.oa.modules.intfz.service.employeestatus.IntfzEmployeeStatusrecordService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusrecord;
import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//import com.sijibao.oa.modules.intfz.service.intfzweb.IntfzWebProjectInfoService;

/**
 * APP员工状态变更controller
 * @author xuby
 *
 */
@Controller
@RequestMapping(value = "wechat/employeeStatus")
@Api(value="APP员工状态服务",tags="APP员工状态服务")
public class IntfzEmployeeStatusrecordController extends BaseController {

	private IntfzWebProjectInfoService intfzWebProjectInfoService;
	
	@Autowired
	private IntfzEmployeeStatusrecordService intfzEmployeeStatusrecordService;
	@ModelAttribute
	public void init(){
		if(this.intfzWebProjectInfoService == null){
			this.intfzWebProjectInfoService = SpringContextHolder.getBean("intfzWebProjectInfoService");
		}
	};
	/**
	 * 保存员工状态
	 * @param sjboacert
	 * @param clientType
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveEmployeeStatus")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP员工状态-保存员工状态信息")
    @AppAuthority
	public BaseResp<String> saveEmployeeStatus(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody EmployeeStatusSaveReq req,
			HttpServletRequest request, HttpServletResponse response){
		CharChangeUtils.CharChange(req);//替换英文字符
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		intfzEmployeeStatusrecordService.saveEmployeeStatusrecord(user, req);

		return new BaseResp<String>(IntfzRs.SUCCESS,"保存状态成功!",null);
	}
	
	/**
	 * 查询该项目下节点信息
	 * @param sjboacert
	 * @param clientType
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findProjectNode")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP员工状态-查询节点信息")
    @AppAuthority
	public BaseResp<List<MainProjectNodeDetailResponse>> findProjectNode(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody MainProjectReq req,
			HttpServletRequest request, HttpServletResponse response){
		List<ProjectNodeDetailResponse> list = intfzWebProjectInfoService.findProjectNode(req.getProjectId());
		ArrayList<MainProjectNodeDetailResponse> newArrayList = Lists.newArrayList();
		for (ProjectNodeDetailResponse projectNodeDetailResponse : list) {
			MainProjectNodeDetailResponse change = change(projectNodeDetailResponse,MainProjectNodeDetailResponse.class);
			newArrayList.add(change);
		}

		return new BaseResp<List<MainProjectNodeDetailResponse>>(IntfzRs.SUCCESS,"",newArrayList);
		
//		ProjectInfoDetailResponse resp = intfzWebProjectInfoService.getProjectInfoDetailById(req.getId());
//		MainProjectInfoDetailResponse projectInfoDetailResponse = change(resp, MainProjectInfoDetailResponse.class);
//		List<MainProjectNodeDetailResponse> list = Lists.newArrayList();
//		for (ProjectNodeDetailResponse cc : resp.getProjectNodeDetailResponse()) {
//			MainProjectNodeDetailResponse change = change(cc, MainProjectNodeDetailResponse.class);
//			list.add(change);
//		}
//		projectInfoDetailResponse.setProjectNodeDetailResponse(list);
	}
	
	/**
	 * 查询当前用户员工状态变更List
	 * @param sjboacert
	 * @param clientType
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findEmployeeStatusList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP员工状态-查询人员状态列表")
    @AppAuthority
	public BaseResp<PageResponse<List<EmployeeStatusrecordResponse>>> findEmployeeStatusList(
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			@RequestBody EmployeeStatusListReq req,
			HttpServletRequest request, HttpServletResponse response){
		
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		Page<EmployeeStatusrecordResponse> resultPage = intfzEmployeeStatusrecordService.findEmployeeStatusList(user,
				new Page<EmployeeStatusrecord>(req.getPageNo(), req.getPageSize()),null);

		return new BaseResp<PageResponse<List<EmployeeStatusrecordResponse>>>(IntfzRs.SUCCESS, "",
				new PageResponse<List<EmployeeStatusrecordResponse>>(resultPage.getList(), resultPage.getPageNo(),
						resultPage.getCount()));
	}
	
}
