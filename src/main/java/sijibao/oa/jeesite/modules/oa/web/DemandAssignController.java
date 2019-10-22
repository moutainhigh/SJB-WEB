/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

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
import com.sijibao.oa.modules.oa.entity.DemandAssign;
import com.sijibao.oa.modules.oa.service.DemandAssignService;

/**
 * 指派记录表Controller
 * @author xuby
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/test/oaDemandAssign")
public class DemandAssignController extends BaseController {

	@Autowired
	private DemandAssignService demandAssignService;
	
	@ModelAttribute
	public DemandAssign get(@RequestParam(required=false) String id) {
		DemandAssign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = demandAssignService.get(id);
		}
		if (entity == null){
			entity = new DemandAssign();
		}
		return entity;
	}
	
	@RequiresPermissions("test:oaDemandAssign:view")
	@RequestMapping(value = {"list", ""})
	public String list(DemandAssign demandAssign, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DemandAssign> page = demandAssignService.findPage(new Page<DemandAssign>(request, response), demandAssign); 
		model.addAttribute("page", page);
		return "modules/test/demandAssignList";
	}

	@RequiresPermissions("test:oaDemandAssign:view")
	@RequestMapping(value = "form")
	public String form(DemandAssign demandAssign, Model model) {
		model.addAttribute("oaDemandAssign", demandAssign);
		return "modules/test/demandAssignForm";
	}

	@RequiresPermissions("test:oaDemandAssign:edit")
	@RequestMapping(value = "save")
	public String save(DemandAssign demandAssign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, demandAssign)){
			return form(demandAssign, model);
		}
		demandAssignService.save(demandAssign);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/test/oaDemandAssign/?repage";
	}
	
	@RequiresPermissions("test:oaDemandAssign:edit")
	@RequestMapping(value = "delete")
	public String delete(DemandAssign demandAssign, RedirectAttributes redirectAttributes) {
		demandAssignService.delete(demandAssign);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/test/oaDemandAssign/?repage";
	}

}