/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.oa.service.ExpensesSubInfoService;

/**
 * 费用科目信息管理Controller
 * @author Petter
 * @version 2017-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/expensesSubInfo")
public class ExpensesSubInfoController extends BaseController {

	@Autowired
	private ExpensesSubInfoService expensesSubInfoService;
	
	@ModelAttribute
	public ExpensesSubInfo get(@RequestParam(required=false) String id) {
		ExpensesSubInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = expensesSubInfoService.get(id);
		}
		if (entity == null){
			entity = new ExpensesSubInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:expensesSubInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpensesSubInfo expensesSubInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExpensesSubInfo> page = expensesSubInfoService.findPage(new Page<ExpensesSubInfo>(request, response), expensesSubInfo); 
		model.addAttribute("page", page);
		return "modules/oa/expensesSubInfoList";
	}

	/**
	 * 停用启用
	 * @return
	 */
	@RequestMapping(value = "enableChange")
	public String enableChange(ExpensesSubInfo expensesSubInfo, RedirectAttributes redirectAttributes) {
		expensesSubInfoService.enableChange(expensesSubInfo);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/oa/expensesSubInfo/?repage";
	}

	@RequiresPermissions("oa:expensesSubInfo:view")
	@RequestMapping(value = "form")
	public String form(ExpensesSubInfo expensesSubInfo, Model model) {
		model.addAttribute("expensesSubInfo", expensesSubInfo);
		if(StringUtils.isNotBlank(expensesSubInfo.getParSubCode())){
			ExpensesSubInfo e=expensesSubInfoService.findBySubCode(expensesSubInfo.getParSubCode());
			if("1".equals(e.getEnable())){
				model.addAttribute("parSubCodeName", e.getSubName() + "[" + e.getSubCode() + "]" + "(停用)");
			}else{
				model.addAttribute("parSubCodeName", e.getSubName() + "[" + e.getSubCode() + "]");
			}
		}else{
			model.addAttribute("parSubCodeName","");
		}
		return "modules/oa/expensesSubInfoForm";
	}

	@RequiresPermissions("oa:expensesSubInfo:edit")
	@RequestMapping(value = "save")
	public String save(ExpensesSubInfo expensesSubInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expensesSubInfo)){
			return form(expensesSubInfo, model);
		}
		expensesSubInfoService.save(expensesSubInfo);
		addMessage(redirectAttributes, "保存费用科目信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/expensesSubInfo/?repage";
	}
	
	@RequiresPermissions("oa:expensesSubInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ExpensesSubInfo expensesSubInfo, RedirectAttributes redirectAttributes) {
		expensesSubInfoService.delete(expensesSubInfo);
		addMessage(redirectAttributes, "删除费用科目信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/expensesSubInfo/?repage";
	}
	
	/**
	 * 获取科目JSON数据。
	 * @param显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("oa:expensesSubInfo:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(ExpensesSubInfo expensesSubInfo, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		
		List<ExpensesSubInfo> list = expensesSubInfoService.findList(expensesSubInfo);
		for (int i=0; i<list.size(); i++){
			ExpensesSubInfo e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getpId());
			map.put("pIds", "");
			if("1".equals(e.getEnable())){
				map.put("name", e.getSubName() + "[" + e.getSubCode() + "]" + "(停用)");
			}else{
				map.put("name", e.getSubName() + "[" + e.getSubCode() + "]");
			}

			mapList.add(map);
		}
		return mapList;
	}


	/**
	 * 获取科目JSON数据。
	 * @param显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("oa:expensesSubInfo:view")
	@ResponseBody
	@RequestMapping(value = "treeDataByCode")
	public List<Map<String, Object>> treeDataByCode(ExpensesSubInfo expensesSubInfo, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();

		List<ExpensesSubInfo> list = expensesSubInfoService.findList(expensesSubInfo);
		for (int i=0; i<list.size(); i++){
			ExpensesSubInfo e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getSubCode());
			map.put("pId", e.getParSubCode());
			map.put("pIds", "");
			if("1".equals(e.getEnable())){
				map.put("name", e.getSubName() + "[" + e.getSubCode() + "]" + "(停用)");
			}else{
				map.put("name", e.getSubName() + "[" + e.getSubCode() + "]");
			}

			mapList.add(map);
		}
		return mapList;
	}
}