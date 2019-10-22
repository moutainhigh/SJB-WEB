package sijibao.oa.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.sys.entity.SysParams;
import com.sijibao.oa.modules.sys.service.SysParamsService;

/**
 * 系统参数管理Controller
 * @author Petter
 * @version 2016-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysParams")
public class SysParamsController extends BaseController {

	@Autowired
	private SysParamsService sysParamsService;
	
	@ModelAttribute
	public SysParams get(@RequestParam(required=false) String id) {
		SysParams entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysParamsService.get(id);
		}
		if (entity == null){
			entity = new SysParams();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysParams:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysParams sysParams, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysParams> page = sysParamsService.findPage(new Page<SysParams>(request, response), sysParams); 
		model.addAttribute("page", page);
		return "modules/sys/sysParamsList";
	}

	@RequiresPermissions("sys:sysParams:view")
	@RequestMapping(value = "form")
	public String form(SysParams sysParams, Model model) {
		model.addAttribute("sysParams", sysParams);
		return "modules/sys/sysParamsForm";
	}
	
	@RequiresPermissions("sys:sysParams:view")
	@RequestMapping(value = {"syn"})
	public String syn(HttpServletRequest request, HttpServletResponse response, Model model) {
		CacheUtils.remove(CacheUtils.SYS_CACHE_PARAMS_MAP);
        return "redirect:"+Global.getAdminPath()+"/sys/sysParams/list/?repage";
	}

	@RequiresPermissions("sys:sysParams:edit")
	@RequestMapping(value = "save")
	public String save(SysParams sysParams, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysParams)){
			return form(sysParams, model);
		}
		sysParamsService.save(sysParams);
		addMessage(redirectAttributes, "保存系统参数信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysParams/?repage";
	}
	
	@RequiresPermissions("sys:sysParams:edit")
	@RequestMapping(value = "delete")
	public String delete(SysParams sysParams, RedirectAttributes redirectAttributes) {
		sysParamsService.delete(sysParams);
		addMessage(redirectAttributes, "删除系统参数信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysParams/?repage";
	}

}