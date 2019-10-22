/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.web;

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
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.sys.entity.Area;
import com.sijibao.oa.modules.sys.service.AreaService;

/**
 * 区域表Controller
 * @author wanxiongbo
 * @version 2018-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/area")
public class AreaController extends BaseController {

	@Autowired
	private AreaService areaService;
	
	@ModelAttribute
	public Area get(@RequestParam(required=false) String id) {
		Area entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = areaService.get(id);
		}
		if (entity == null){
			entity = new Area();
		}
		return entity;
	}
	/**
	 * 区域列表显示
	 * @param area
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(Area area, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Area> list = areaService.findList(area); 
		model.addAttribute("list", list);
		return "/modules/sys/areaList";
	}
	/**
	 * 区域单表添加
	 * @param area
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(Area area, Model model) {
		if (area.getParent()!=null && StringUtils.isNotBlank(area.getParent().getId())){
			area.setParent(areaService.get(area.getParent().getId()));
			if (StringUtils.isBlank(area.getId())){
				Area areaChild = new Area();
				areaChild.setParent(new Area(area.getParent().getId()));
				List<Area> list = areaService.findList(area); 
				if (list.size() > 0){
					area.setSort(list.get(list.size()-1).getSort());
					if (area.getSort() != null){
						area.setSort(area.getSort() + 30);
					}
				}
			}
		}
		if (area.getSort() == null){
			area.setSort(30);
		}
		
		model.addAttribute("area", area);
		return "/modules/sys/areaForm";
	}
	/**
	 * 区域单表保存
	 * @param area
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(Area area, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, area)){
			return form(area, model);
		}
//		area.setId(area.getCode());
		areaService.save(area);
		addMessage(redirectAttributes, "保存成功！");
		return "redirect:"+Global.getAdminPath()+"/sys/area?repage";
	}
	/**
	 * 区域删除
	 * @param area
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(Area area, RedirectAttributes redirectAttributes) {
		areaService.delete(area);
		addMessage(redirectAttributes, "删除成功！");
		return "redirect:"+Global.getAdminPath()+"/sys/area?repage";
	}
	/**
	 * 区域树形菜单
	 * @param extId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> list = areaService.findList(new Area());
		for (int i=0; i<list.size(); i++){
			Area e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}