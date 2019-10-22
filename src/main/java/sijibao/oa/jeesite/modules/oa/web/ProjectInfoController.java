/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.service.OfficeService;

/**
 * 项目信息Controller
 * @author Petter
 * @version 2018-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/projectInfo")
public class ProjectInfoController extends BaseController {

	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private OfficeService officeService;
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute
	public ProjectInfo get(@RequestParam(required=false) String id) {
		ProjectInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectInfoService.get(id);
		}
		if (entity == null){
			entity = new ProjectInfo();
		}
		return entity;
	}
	/**
	 * 列表
	 * @param custInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:projectInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectInfo> page = projectInfoService.findPage(new Page<ProjectInfo>(request, response), projectInfo); 
		model.addAttribute("page", page);
		model.addAttribute("projectInfo", projectInfo);
		return "modules/oa/projectInfoList";
	}
	
	/**
	 * 关联列表
	 * @param custInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "relevanceList")
	public String relevanceList(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//		projectInfo.setQueryProjectState("1");
		Page<ProjectInfo> page = projectInfoService.findPage(new Page<ProjectInfo>(request, response), projectInfo); 
		model.addAttribute("page", page);
		return "modules/oa/projectInfoRelevanceList";
	}
	
	
	/**
	 * 添加
	 * @param custInfo 对象信息
	 * @param model	
	 * @return
	 */
	@RequiresPermissions("oa:projectInfo:view")
	@RequestMapping(value = "form")
	public String form(ProjectInfo projectInfo, Model model) {
		if(StringUtils.isBlank(projectInfo.getId())){
			projectInfo.setProjectCode(CodeUtils.genCode("P", DateUtils.getDate("yyyyMMdd"), 3));
		}
		model.addAttribute("projectInfo", projectInfo);
		return "modules/oa/projectInfoForm";
	}
	/**
	 * 保存
	 * @param custInfo
	 * @param model
	 * @param redirectAttributes 页面显示信息
	 * @return
	 */
	@RequiresPermissions("oa:projectInfo:edit")
	@RequestMapping(value = "save")
	public String save(ProjectInfo projectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectInfo)){
			return form(projectInfo, model);
		}
		projectInfoService.save(projectInfo);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/projectInfo/?repage";
	}
	
	/**
	 * 关闭
	 * @param custInfo
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:projectInfo:edit")
	@RequestMapping(value = "close")
	public String close(ProjectInfo projectInfo, RedirectAttributes redirectAttributes) {
		projectInfoService.close(projectInfo);
		addMessage(redirectAttributes, "关闭项目成功");
		return "redirect:"+Global.getAdminPath()+"/oa/projectInfo/?repage";
	}
	
	/**
	 * 当项目类型为“公司级”时，归属部门默认为“武汉物易云通网络科技有限公司”
	 * @param projectInfo
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException 
	 */
	@RequestMapping(value = "officeChoose")
	@ResponseBody
	public ProjectInfo officeChoose(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		if("0".equals(projectInfo.getProjectType())){
			projectInfo.setOffice(officeService.get("1"));
		}
		model.addAttribute("projectInfo", projectInfo);
		return projectInfo;
		
	}

}