package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sijibao.oa.modules.intfz.request.common.SysVersionHandleReq;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.common.SysVersionResponse;
import com.sijibao.oa.modules.intfz.service.common.IntfzSysVersionService;
import com.sijibao.oa.modules.sys.entity.SysVersion;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 微信报销流程相关 废弃
 * @author Petter
 */
@Controller
@RequestMapping(value = "${frontPath}/sysVersion")
@Api(value="系统版本维护服务",tags="系统版本维护服务")
public class IntfzSysVersionController extends BaseController {
	@Autowired
	private IntfzSysVersionService intfzSysVersionService;
	
	
	/**
	 * 查询列表
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value = "sysVersionList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "系统版本维护-查询列表")
	BaseResp<PageResponse<List<SysVersionResponse>>> sysVersionList(
			@RequestBody SysVersionHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid);
		Page<SysVersionResponse> page = intfzSysVersionService.findPage(new Page<SysVersion>(req.getPageNo(), req.getPageSize()), req, user);
		return new BaseResp<PageResponse<List<SysVersionResponse>>>(IntfzRs.SUCCESS, "ok",
				new PageResponse<List<SysVersionResponse>>(page.getList(), page.getPageNo(), page.getCount()));
	}
	/**
	 * 系统版本维护-查询列表(不带分页)
	 * @param req
	 * @param sessionid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sysVersionLists")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "系统版本维护-查询列表(不带分页)")
	BaseResp<List<SysVersionResponse>> sysVersionLists(
			@RequestBody SysVersionHandleReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		List<SysVersionResponse> list = intfzSysVersionService.findList(req);
		return new BaseResp<List<SysVersionResponse>>(IntfzRs.SUCCESS, "",list);
	}
	
}
