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
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.asset.api.ConsumablesTypeService;
import com.sijibao.oa.asset.api.exception.ServiceException;
import com.sijibao.oa.asset.api.request.consumables.ConsumablesTypeReq;
import com.sijibao.oa.asset.api.response.assettype.consumables.TypeResp;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.ConsumablesType;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;


/**
 * 消耗品类别管理Controller
 * @author wanxb
 * @version 2019-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/consumablesType")
public class ConsumablesTypeController extends BaseController {

	@Autowired
	private ConsumablesTypeService consumablesTypeService;
	
	@ModelAttribute
	public ConsumablesType get(@RequestParam(required=false) String id) {
		ConsumablesType entity = null;
		if (StringUtils.isNotBlank(id)){
			TypeResp resp = consumablesTypeService.get(id);
			if(resp != null){
				entity = change(resp,ConsumablesType.class);
				entity.setParent(get(resp.getParent()));
			}
		}
		if (entity == null){
			entity = new ConsumablesType();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ConsumablesType consumablesType, HttpServletRequest request, HttpServletResponse response, Model model) {
		ConsumablesTypeReq req = change(consumablesType,ConsumablesTypeReq.class);
		PagerResponse<TypeResp> resp = consumablesTypeService.findList(req);
		Page<ConsumablesType> page = new Page<ConsumablesType>(request, response);
		List<ConsumablesType> list = Lists.newArrayList();
		for(TypeResp re :resp.getList()){
			ConsumablesType tyu = change(re,ConsumablesType.class);
			tyu.setParent(this.get(re.getParent()));
			list.add(tyu);
		}
		page.setCount(resp.getCount());
		page.setPageNo(page.getPageNo());
		page.setPageSize(page.getPageSize());
		page.setList(list);
		model.addAttribute("page",page);
		return "modules/oa/consumablesTypeList";
	}

	@RequestMapping(value = "form")
	public String form(ConsumablesType consumablesType, Model model) {


		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(consumablesType.getId())){
			if(consumablesType.getParent() != null){
				TypeResp type = consumablesTypeService.get(consumablesType.getParent().getId());
				ConsumablesType ty = change(type ,ConsumablesType.class);
				consumablesType.setParent(ty);
			}
			ConsumablesType consumablesTypeChild = new ConsumablesType();
			if(consumablesType.getParent() != null){
				consumablesTypeChild.setParent(new ConsumablesType(consumablesType.getParent().getId()));
			}
			ConsumablesTypeReq con = change(consumablesType,ConsumablesTypeReq.class);
			List<TypeResp> li = consumablesTypeService.findList(con).getList();
			List<ConsumablesType> list = Lists.newArrayList();
			for(TypeResp re :li){
				ConsumablesType typ = change(re,ConsumablesType.class);
				typ.setParent(this.get(re.getParent()));
				list.add(typ);
			}
			if (list.size() > 0){
				consumablesType.setSort(list.get(list.size()-1).getSort());
				if (consumablesType.getSort() != null){
					consumablesType.setSort(consumablesType.getSort() + 30);
				}
			}
		}
		if (consumablesType.getSort() == null){
			consumablesType.setSort(30);
		}
		model.addAttribute("consumablesType", consumablesType);
		return "modules/oa/consumablesTypeForm";
	}

	@RequestMapping(value = "save")
	public String save(ConsumablesType consumablesType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, consumablesType)){
			return form(consumablesType, model);
		}
		User user = UserUtils.getUser();
		ConsumablesTypeReq req = change(consumablesType,ConsumablesTypeReq.class);
		req.setParent(consumablesType.getParent().getId());
		req.setUserId(user.getId());
		try {
			consumablesTypeService.save(req);
		}catch (ServiceException e){
			model.addAttribute("message",e.getMessage());
			return form(consumablesType, model);
		}
		addMessage(redirectAttributes, "保存消耗品类别管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/consumablesType/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ConsumablesType consumablesType, RedirectAttributes redirectAttributes) {
		try{
			consumablesTypeService.delete(consumablesType.getId());
		}catch (ServiceException e){
			addMessage(redirectAttributes, e.getMessage());
			return "redirect:"+Global.getAdminPath()+"/oa/consumablesType/?repage";
		}
		addMessage(redirectAttributes, "删除消耗品类别管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/consumablesType/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		PagerResponse<TypeResp> page = consumablesTypeService.findList(new ConsumablesTypeReq());
		List<TypeResp> li = page.getList();
		for (int i=0; i<li.size(); i++){
			TypeResp e = li.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId",e.getParent());
				map.put("name", e.getCtName());
				map.put("pIds", e.getParentIds());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}