package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.HandleReq;
import com.sijibao.oa.modules.intfz.request.employeedaily.EmployeeDailyHandleReq;
import com.sijibao.oa.modules.intfz.request.employeedaily.EmployeeDailySaveReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.employeedaily.EmployeeDailyDetailResponse;
import com.sijibao.oa.modules.intfz.response.employeedaily.EmployeeDailyResponse;
import com.sijibao.oa.modules.intfz.service.intfzweb.IntfzWebEmployeeDailyService;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.oa.entity.EmployeeDaily;
import com.sijibao.oa.modules.oa.service.EmployeeDailyService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 工作日志controller
 * @author wanxb
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/employeeDaily")
@Api(value="WEB工作日志服务",tags="WEB工作日志服务")
public class IntfzWebEmployeeDailyController extends BaseController {
	
	@Autowired
	private IntfzWebEmployeeDailyService intfzWebEmployeeDailyService;
	@Autowired
	private EmployeeDailyService employeeDailyService;
	/**
	 * 工作日志查询列表
	 * @param req
	 * @param sjboacert
	 * @param clientType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "employeeDailyList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB工作日志-查询列表")
	BaseResp<PageResponse<List<EmployeeDailyResponse>>> employeeDailyList(
			@RequestBody EmployeeDailyHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		Page<EmployeeDailyResponse> page = intfzWebEmployeeDailyService.findPage(new Page<EmployeeDaily>(req.getPageNo(), req.getPageSize()), req, user);
		return new BaseResp<PageResponse<List<EmployeeDailyResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<EmployeeDailyResponse>>(page.getList(), page.getPageNo(), page.getCount()));
	}
	/**
	 * 工作日志信息-不带分页查询
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "employeeDailys")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB工作日志-不带分页查询")
	BaseResp<List<EmployeeDailyResponse>> employeeDailys(
			@RequestBody EmployeeDailyHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		return null;
	}

	/**
	 * 保存工作日志信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveEmployeeDaily")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB工作日志-保存工作日志信息")
	public BaseResp<String> saveEmployeeDaily(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
											  @RequestBody EmployeeDailySaveReq req,
											  HttpServletRequest request, HttpServletResponse response){
		CharChangeUtils.CharChange(req);//替换英文字符
		try {
			User user = UserUtils.getUser(sessionid);
			intfzWebEmployeeDailyService.saveEmployeeDaily(req, user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new BaseResp<String>(IntfzRs.ERROR,"保存工作日志信息失败!",null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"保存工作日志信息成功!",null);
	}
	
	/**
	 * 工作日志详情查询
	 * @param id
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "employeeDailyDetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB工作日志-工作日志详情查询")
	BaseResp<EmployeeDailyDetailResponse> employeeDailyDetail(
			@RequestBody HandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<EmployeeDailyDetailResponse>(IntfzRs.ERROR, "id不能为空",null);
		}
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		if(user == null){
			return new BaseResp<EmployeeDailyDetailResponse>(IntfzRs.ERROR, "未找到用户信息",null);
		}
		EmployeeDailyDetailResponse employeeDaily = intfzWebEmployeeDailyService.getEmployeeDailyById(req.getId());

		return new BaseResp<EmployeeDailyDetailResponse>(IntfzRs.SUCCESS, "ok", employeeDaily);
	}

	/**
	 * 删除工作日志信息
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteEmployeeDaily")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB工作日志-删除工作日志信息")
	public BaseResp<String> deleteEmployeeDaily(@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
												@RequestBody HandleReq req,
												HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(req.getId())){
			return new BaseResp<String>(IntfzRs.ERROR,"主键ID不能为空!",null);
		}
		try {
			EmployeeDaily employeeDaily = employeeDailyService.get(req.getId());
			if(employeeDaily == null){
				return new BaseResp<String>(IntfzRs.ERROR,"删除失败,未找到需要删除的客户信息!",null);
			}
			intfzWebEmployeeDailyService.deleteEmployeeDaily(employeeDaily);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new BaseResp<String>(IntfzRs.ERROR,"删除客户信息失败!",null);
		}
		return new BaseResp<String>(IntfzRs.SUCCESS,"删除客户信息成功!",null);
	}
}
