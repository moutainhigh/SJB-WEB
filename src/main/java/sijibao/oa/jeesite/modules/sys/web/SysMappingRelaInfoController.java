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
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.sys.entity.SysMappingRelaInfo;
import com.sijibao.oa.modules.sys.service.SysMappingRelaInfoService;
import com.sijibao.oa.modules.sys.utils.MappingRelaUtils;

/**
 * 映射关系信息管理Controller
 * @author Petter
 * @version 2016-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysMappingRelaInfo")
public class SysMappingRelaInfoController extends BaseController {

	@Autowired
	private SysMappingRelaInfoService sysMappingRelaInfoService;
	
	@ModelAttribute
	public SysMappingRelaInfo get(@RequestParam(required=false) String id) {
		SysMappingRelaInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysMappingRelaInfoService.get(id);
		}
		if (entity == null){
			entity = new SysMappingRelaInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysMappingRelaInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysMappingRelaInfo sysMappingRelaInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysMappingRelaInfo> page = sysMappingRelaInfoService.findPage(new Page<SysMappingRelaInfo>(request, response), sysMappingRelaInfo); 
		model.addAttribute("page", page);
		return "modules/sys/sysMappingRelaInfoList";
	}
	@RequiresPermissions("sys:sysMappingRelaInfo:synchronize")
	@RequestMapping(value = {"synchronize"})
	public String synchronize() {
		CacheUtils.remove(MappingRelaUtils.CACHE_MAPPING_RELA_MAP);
		return "redirect:"+Global.getAdminPath()+"/sys/sysMappingRelaInfo/?repage";
	}

	@RequiresPermissions("sys:sysMappingRelaInfo:view")
	@RequestMapping(value = "form")
	public String form(SysMappingRelaInfo sysMappingRelaInfo, Model model) {
		model.addAttribute("sysMappingRelaInfo", sysMappingRelaInfo);
		return "modules/sys/sysMappingRelaInfoForm";
	}

	@RequiresPermissions("sys:sysMappingRelaInfo:edit")
	@RequestMapping(value = "save")
	public String save(SysMappingRelaInfo sysMappingRelaInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysMappingRelaInfo)){
			return form(sysMappingRelaInfo, model);
		}
		sysMappingRelaInfoService.save(sysMappingRelaInfo);
		addMessage(redirectAttributes, "保存映射关系信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysMappingRelaInfo/?repage";
	}
	
	@RequiresPermissions("sys:sysMappingRelaInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SysMappingRelaInfo sysMappingRelaInfo, RedirectAttributes redirectAttributes) {
		sysMappingRelaInfoService.delete(sysMappingRelaInfo);
		addMessage(redirectAttributes, "删除映射关系信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysMappingRelaInfo/?repage";
	}

}