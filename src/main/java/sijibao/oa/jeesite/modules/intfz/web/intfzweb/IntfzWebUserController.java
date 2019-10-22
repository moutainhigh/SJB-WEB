package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.ModifyPwdReq;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * web用户接口服务 
 * @author wanxb
 */
@Controller
@RequestMapping(value = "${frontPath}/user")
@Api(value="WEB用户服务",tags="WEB用户服务")
public class IntfzWebUserController extends BaseController {
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(value = "modifyPwd")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB用户服务-修改密码")
	public BaseResp<String> modifyPwd(
			@RequestBody ModifyPwdReq req,
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = UserUtils.getUser(sessionid); //获取当前申请人信息
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息！", "");
		}
		if(StringUtils.isBlank(req.getNewPassword())){
			return new BaseResp<String>(IntfzRs.ERROR, "新密码不能为空！", "");
		}
		try {
			if (SystemService.validatePassword(req.getOldPassword(), user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), req.getNewPassword());
			}else{
				return new BaseResp<String>(IntfzRs.ERROR, "修改密码失败，旧密码错误", "");
			}
		} catch (ServiceException e) {
			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
		}
		UserUtils.clearCache(user);
		return new BaseResp<String>(IntfzRs.SUCCESS, "修改成功", "");
		
	}
	
}
