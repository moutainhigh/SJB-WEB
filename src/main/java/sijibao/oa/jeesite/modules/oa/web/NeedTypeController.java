/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.NeedProgress;
import com.sijibao.oa.modules.oa.entity.NeedType;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzNeedTypeService;
import com.sijibao.oa.office.api.exception.ServiceException;
import com.sijibao.oa.office.api.request.need.NeedProgressReq;
import com.sijibao.oa.office.api.request.need.NeedTypeReq;
import com.sijibao.oa.office.api.request.need.NeedTypeSaveReq;
import com.sijibao.oa.office.api.response.need.NeedProgressResponse;
import com.sijibao.oa.office.api.response.need.NeedTypeResponse;

/**
 * 协作类型管理Controller
 * @author wanxb
 * @version 2018-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/needType")
public class NeedTypeController extends BaseController {

	@Autowired
	private IntfzNeedTypeService needTypeService;



	public NeedType get(@RequestParam(required=false) String id) {
		NeedType entity = null;
		if (StringUtils.isNotBlank(id)){
			NeedTypeResponse resp = needTypeService.get(id);
			entity = change(resp, NeedType.class);
			ArrayList<NeedProgress> list = Lists.newArrayList();
			for (NeedProgressResponse s : resp.getNeedProgressList()) {
				NeedProgress ss = change(s, NeedProgress.class);
				ss.setCreateBy(UserUtils.get(s.getCreateBy()));
				list.add(ss);
			}
			entity.setNeedProgressList(list);
		}
		if (entity == null){
			entity = new NeedType();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:needType:view")
	@RequestMapping(value = {"list", ""})
	public String list(NeedType needType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NeedType> paa = new Page<NeedType>(request, response);
		NeedTypeReq change = change(needType, NeedTypeReq.class);
		change.setDelFlag(needType.getDel());
		change.setPageNo(paa.getPageNo());
		change.setPageSize(paa.getPageSize());
		if(needType.getCreateBy() != null){
			change.setCreateBy(needType.getCreateBy().getId());
		}
		if(needType.getUpdateBy() != null){
			change.setUpdateBy(needType.getUpdateBy().getId());
		}

		PagerResponse<NeedTypeResponse> resp = needTypeService.findPage(change); 
		Page<NeedType> page = new Page<NeedType>(request, response);
		ArrayList<NeedType> list = Lists.newArrayList();
		for (NeedTypeResponse s : resp.getList()) {
			NeedType ss = change(s,NeedType.class);
			ArrayList<NeedProgress> li = Lists.newArrayList();
			for (NeedProgressResponse sss : s.getNeedProgressList()) {
				NeedProgress ssss = change(sss, NeedProgress.class);
				if(StringUtils.isNotBlank(sss.getCreateBy())){
					ssss.setCreateBy(UserUtils.get(sss.getCreateBy()));
				}

				li.add(ssss);
			}
			ss.setNeedProgressList(li);
			list.add(ss);
		}
		page.setCount(resp.getCount());
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/oa/needTypeList";
	}

	@RequiresPermissions("oa:needType:view")
	@RequestMapping(value = "form")
	public String form(NeedType needType, Model model) {
		needType = get(needType.getId());
		ArrayList<NeedProgress> list = Lists.newArrayList();
		ArrayList<NeedProgress> list1 = Lists.newArrayList();
		List<NeedProgress> progressList = needType.getNeedProgressList();
		if(progressList != null){
			if(progressList.size() > 5){
				for (int i = 0; i < 6; i++) {
					list.add(progressList.get(i));
				}
				for (int i = 6; i < progressList.size(); i++) {
					list1.add(progressList.get(i));
				}
			}else{
				for (int i = 0; i < progressList.size(); i++) {
					list.add(progressList.get(i));
				}
			}
		}
		needType.setNeedProgressFirst(list);
		needType.setNeedProgressSecond(list1);
		model.addAttribute("needType", needType);
		return "modules/oa/needTypeForm";
	}

	@RequiresPermissions("oa:needType:edit")
	@RequestMapping(value = "save")
	public String save(NeedType needType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, needType)){
			needType = get(needType.getId());
			return form(needType, model);
		}
		if(getNeedTypeByName(needType.getOldTypeName(),needType.getTypeName())){
			addMessage(model, "类型名称已存在！");
			return form(needType, model);
		}
		if(needType.getNeedProgressList() == null || needType.getNeedProgressList().size() < 1){
			addMessage(model, "进度不能为空！");
			return form(needType, model);
		}
		boolean flags = false;
		List<NeedProgress> li = needType.getNeedProgressList();
		for (int i = 0; i < li.size() - 1; i++){
            String name = li.get(i).getProgressName();
            for (int j = i + 1; j < li.size(); j++){
                if (name.equals(li.get(j).getProgressName())){
                	flags = true;
                }
            }
        }
		if(flags){
			addMessage(model, "进度名不能为重复！");
			return form(needType, model);
		}
		User user = UserUtils.getUser();
		NeedTypeSaveReq change = change(needType, NeedTypeSaveReq.class);
		change.setUserId(user.getId());
		ArrayList<NeedProgressReq> list = Lists.newArrayList();
		if(needType.getNeedProgressList() != null){
			for (NeedProgress s : needType.getNeedProgressList()) {
				NeedProgressReq ss = change(s, NeedProgressReq.class);
				if(StringUtils.isBlank(s.getIsEnd()) || "0".equals(s.getIsEnd())){
					ss.setIsEnd("0");
				}else{
					ss.setIsEnd("1");
				}
				if("on".equals(s.getIsSelected()) || "1".equals(s.getIsSelected())){
					ss.setIsSelected("1");
				}else{
					ss.setIsSelected("0");
				}
				list.add(ss);
			}
		}
		change.setNeedProgressList(list);
		change.setTypeCode(CodeUtils.genCode("LX", DateUtils.getDate("yyyyMMdd"), 3));
		try {
			needTypeService.save(change);
		} catch (ServiceException e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		addMessage(redirectAttributes, "保存协作类型管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/needType/?repage";
	}
	
	@RequiresPermissions("oa:needType:edit")
	@RequestMapping(value = "delete")
	public String delete(NeedType needType, RedirectAttributes redirectAttributes) {
		NeedTypeReq change = change(needType, NeedTypeReq.class);
		User user = UserUtils.getUser();
		change.setUserId(user.getId());
		needTypeService.delete(change);
		addMessage(redirectAttributes, "删除协作类型管理成功");
		return "redirect:"+Global.getAdminPath()+"/oa/needType/?repage";
	}
	
	
	/**
	 * 验证不能重名
	 * @param 
	 * @param 
	 * @return
	 */
	public boolean getNeedTypeByName(String oldName, String name) {
		List<NeedTypeResponse> list = needTypeService.getNeedTypeByName(name);
		if (name !=null && name.equals(oldName)) {
			return false;
		} else if(name !=null && list == null ){
			return false;
		}else if(list.size() == 0){
			return false;
		}
		return true;
	}

}