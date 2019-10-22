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
import com.sijibao.oa.modules.intfz.request.custinfo.CustDetailListRequest;
import com.sijibao.oa.modules.oa.entity.CustInfo;
import com.sijibao.oa.modules.oa.service.CustInfoService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 客户信息管理Controller
 * @author wanxb
 * @version 2018-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/custInfo")
public class CustInfoController extends BaseController {

	@Autowired
	private CustInfoService custInfoService;
	
	@ModelAttribute
	public CustInfo get(@RequestParam(required=false) String id) {
		CustInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = custInfoService.get(id);
		}
		if (entity == null){
			entity = new CustInfo();
		}
		return entity;
	}
	/**
	 * 客户信息列表展示
	 * @param custInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:custInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustInfo custInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		custInfo.setUser(user);
		Page<CustInfo> page = custInfoService.findPage(new Page<CustInfo>(request, response), custInfo); 
		model.addAttribute("page", page);
		return "modules/oa/custInfoList";
	}
	/**
	 * 客户信息添加，修改页面
	 * @param custInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:custInfo:view")
	@RequestMapping(value = "form")
	public String form(CustInfo custInfo, Model model) {
		model.addAttribute("custInfo", custInfo);
		return "modules/oa/custInfoForm";
	}
	/**
	 * 客户信息保存页面
	 * @param custInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:custInfo:edit")
	@RequestMapping(value = "save")
	public String save(CustInfo custInfo, CustDetailListRequest custDetailListRequest , Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, custInfo)){
			return form(custInfo, model);
		}
		custInfoService.save(custInfo);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/custInfo/?repage";
	}
	/**
	 * 客户信息删除
	 * @param custInfo
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:custInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CustInfo custInfo, RedirectAttributes redirectAttributes) {
		custInfoService.delete(custInfo);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/custInfo/?repage";
	}
	
	//挂车选择器
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		
		List<CustInfo> list = custInfoService.findList(new CustInfo());
		
		for (int i=0; i<list.size(); i++){
			CustInfo e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", e.getCustName() + "[" + e.getCustCode()+ "]");
			map.put("custName", e.getCustName());
//			map.put("areaId", e.getArea().getId());
//			map.put("areaName", e.getArea().getName());
			map.put("marketLeaderId", e.getMarketLeaderId());
			map.put("marketLeader", e.getMarketLeaderName());
			mapList.add(map);
		}
		return mapList;
	}
	
}