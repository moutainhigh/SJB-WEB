package sijibao.oa.jeesite.modules.intfz.web.intfzweb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.bean.MenuInfo;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.service.common.IntfzWebLoginService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * web登录接口服务 
 * @author Petter
 */
@Controller
@Api(value="WEB登录服务",tags="WEB登录服务")
public class IntfzWebLoginController extends BaseController {

	@Autowired
	private IntfzWebLoginService intfzWebLoginService;
	
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB登录")
	@RequestMapping(value = "${frontPath}/login", method = RequestMethod.POST)
	public BaseResp<Principal> login(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal(sessionid);
		// 如果已经登录，则返回已登录信息
		if(principal != null){
			return new BaseResp<Principal>(IntfzRs.SUCCESS, null, principal);
		}
		return new BaseResp<Principal>(IntfzRs.ERROR, "未获取到用户信息，请重新登录!", null);
	}
	
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "获取菜单")
	@RequestMapping(value = "${frontPath}/menu", method = RequestMethod.POST)
	public BaseResp<List<MenuInfo>> menu(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser(sessionid);
		if(user != null){
			List<MenuInfo> menuList = intfzWebLoginService.getMenuList(user);
			if(menuList != null && menuList.size() > 0){
				return new BaseResp<List<MenuInfo>>(IntfzRs.SUCCESS, null, menuList);
			}
			return new BaseResp<List<MenuInfo>>(IntfzRs.ERROR, "该用户无菜单权限，请配置后获取!", null);
		}
		return new BaseResp<List<MenuInfo>>(IntfzRs.ERROR, "未获取到用户信息，请重新登录!", null);
	}
	
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "获取权限号")
	@RequestMapping(value = "${frontPath}/permission", method = RequestMethod.POST)
	public BaseResp<List<String>> permission(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser(sessionid);
		if(user != null){
			List<String> menuList = intfzWebLoginService.getPermissionList(user);
			if(menuList != null && menuList.size() > 0){
				return new BaseResp<List<String>>(IntfzRs.SUCCESS, null, menuList);
			}
			return new BaseResp<List<String>>(IntfzRs.ERROR, "该用户无权限，请配置后获取!", null);
		}
		return new BaseResp<List<String>>(IntfzRs.ERROR, "未获取到用户信息，请重新登录!", null);
	}
	
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "WEB登出")
	@RequestMapping(value = "${frontPath}/logout", method = RequestMethod.POST)
	public BaseResp<String> logout(
			@ApiParam(value="登录成功时返回的sessionid") @RequestHeader String sessionid,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal(sessionid);
		if(principal != null){
            UserUtils.clearSession(sessionid);
            UserUtils.getSubject().logout();
        }
		return new BaseResp<String>(IntfzRs.SUCCESS, null, "登出成功");
	}
	
}
