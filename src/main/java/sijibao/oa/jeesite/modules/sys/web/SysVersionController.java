/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.sys.entity.SysVersion;
import com.sijibao.oa.modules.sys.service.SysVersionService;

/**
 * 系统版本维护Controller
 * @author wanxb
 * @version 2018-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/version")
public class SysVersionController extends BaseController {

	@Autowired
	private SysVersionService sysVersionService;
	
	@ModelAttribute
	public SysVersion get(@RequestParam(required=false) String id) {
		SysVersion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysVersionService.get(id);
		}
		if (entity == null){
			entity = new SysVersion();
		}
		return entity;
	}
	/**
	 * 查询列表
	 * @param sysVersion
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:version:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysVersion sysVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysVersion> page = sysVersionService.findPage(new Page<SysVersion>(request, response), sysVersion); 
		model.addAttribute("page", page);
		return "modules/sys/sysVersionList";
	}
	/**
	 * 详情页面
	 * @param sysVersion
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:version:view")
	@RequestMapping(value = "form")
	public String form(SysVersion sysVersion, Model model) {
		model.addAttribute("sysVersion", sysVersion);
		return "modules/sys/sysVersionForm";
	}
	/**
	 * 保存页面
	 * @param sysVersion
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:version:edit")
	@RequestMapping(value = "save")
	public String save(SysVersion sysVersion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysVersion)){
			return form(sysVersion, model);
		}
		sysVersionService.save(sysVersion);
		addMessage(redirectAttributes, "保存系统版本维护成功");
		System.out.println("redirect:"+Global.getAdminPath()+"/modules/sys/sysVersion/?repage");
		return "redirect:"+Global.getAdminPath()+"/sys/version/?repage";
	}
	/**
	 * 删除页面
	 * @param sysVersion
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:version:edit")
	@RequestMapping(value = "delete")
	public String delete(SysVersion sysVersion, RedirectAttributes redirectAttributes) {
		sysVersionService.delete(sysVersion);
		addMessage(redirectAttributes, "删除系统版本维护成功");
		return "redirect:"+Global.getAdminPath()+"/sys/version/?repage";
	}

}