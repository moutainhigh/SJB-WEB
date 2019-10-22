/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.FinanceSubInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzFinanceSubInfoService;
import com.sijibao.oa.office.api.exception.ServiceException;
import com.sijibao.oa.office.api.request.finance.FinanceSubInfoReq;
import com.sijibao.oa.office.api.response.finance.FinanceSubInfoResp;

/**
 * 合同模版配置Controller
 * @author wanxb
 * @version 2018-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/financeSubInfo")
public class FinanceSubController extends BaseController {

	@Autowired
	private IntfzFinanceSubInfoService intfzFinanceSubInfoService;
	
	public FinanceSubInfo get(@RequestParam(required=false) String id) {
		FinanceSubInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			FinanceSubInfoResp subResp = intfzFinanceSubInfoService.get(id);
			//拼接字符串 expensesSubIdStr expensesSubNameStr
			entity = change(subResp, FinanceSubInfo.class);
			String expensesSubIdStr = "";
			String expensesSubNameStr = "";
            String costCenterIdStr = "";
			String costCenterNameStr = "";
			if(subResp.getExpensesSubIds() != null && subResp.getExpensesSubIds().size() > 0){//拼接费用科目id
                for(String s :subResp.getExpensesSubIds()){
                    expensesSubIdStr = expensesSubIdStr + s + ",";
                }
            }
			if(subResp.getExpensesSubNames() != null && subResp.getExpensesSubNames().size() > 0){//拼接费用科目名称
				StringBuilder expensesSubName = new StringBuilder();
				for (int i=0; i<subResp.getExpensesSubNames().size(); i++){
					if(subResp.getEnables().size() >= i && "1".equals(subResp.getEnables().get(i))){
						expensesSubName.append(subResp.getExpensesSubNames().get(i))
								.append("(").append(subResp.getExpensesSubCodes().get(i)).append(",停用),");
					}else{
						expensesSubName.append(subResp.getExpensesSubNames().get(i))
								.append("(").append(subResp.getExpensesSubCodes().get(i)).append("),");
					}

				}
				expensesSubNameStr = expensesSubName.toString();
            }
            if(subResp.getCostCenterIds() != null && subResp.getCostCenterIds().size() > 0){//拼接成本中心id
                for(String s :subResp.getCostCenterIds()){
                    costCenterIdStr = costCenterIdStr + s + ",";
                }
            }
            if(subResp.getCostCenterNames() != null && subResp.getCostCenterNames().size() > 0){//拼接成本中心名称
                for(String s :subResp.getCostCenterNames()){
                    costCenterNameStr = costCenterNameStr + s + ",";
                }
            }

			if(StringUtils.isNotBlank(expensesSubIdStr)){
                entity.setExpensesSubIdStr(expensesSubIdStr.substring(0,expensesSubIdStr.length()-1));
            }
			if(StringUtils.isNotBlank(expensesSubNameStr)){
                entity.setExpensesSubNameStr(expensesSubNameStr.substring(0,expensesSubNameStr.length()-1));
            }
            if(StringUtils.isNotBlank(costCenterIdStr)){
                entity.setCostCenterIdStr(costCenterIdStr.substring(0,costCenterIdStr.length()-1));
            }
            if(StringUtils.isNotBlank(costCenterNameStr)){
                entity.setCostCenterNameStr(costCenterNameStr.substring(0,costCenterNameStr.length()-1));
            }

		}
		if (entity == null){
			entity = new FinanceSubInfo();
		}
		return entity;
	}
	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(FinanceSubInfo financeSubInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		FinanceSubInfoReq change = change(financeSubInfo, FinanceSubInfoReq.class);
		User user = UserUtils.getUser();
		change.setUserId(user.getId());
		Page<FinanceSubInfo> page = new Page<FinanceSubInfo>(request, response);
		change.setPageNo(page.getPageNo());
		change.setPageSize(page.getPageSize());
		PagerResponse<FinanceSubInfoResp> resp = intfzFinanceSubInfoService.findPage(change);
		ArrayList<FinanceSubInfo> list = Lists.newArrayList();
		for (FinanceSubInfoResp s : resp.getList()) {
			FinanceSubInfo ss = change(s, FinanceSubInfo.class);
			StringBuilder expensesSubNameStr = new StringBuilder();
			if(s.getExpensesSubNames() != null && s.getExpensesSubNames().size() > 0){//拼接费用科目名称
				List<String> names = s.getExpensesSubNames();
				List<String> codes = s.getExpensesSubCodes();
				for (int i=0; i<names.size(); i++){
					expensesSubNameStr.append(names.get(i))
					.append("(").append(codes.get(i)).append("),");
				}
				ss.setExpensesSubNameStr(expensesSubNameStr.toString().substring(0,expensesSubNameStr.toString().length()-1));
			}
			list.add(ss);
		}
		page.setCount(resp.getCount());
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/oa/financeSubInfoList";
	}
	/**
	 * 表单页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(FinanceSubInfo financeSubInfo, Model model) {
		financeSubInfo = this.get(financeSubInfo.getId());
		model.addAttribute("financeSubInfo", financeSubInfo);
		return "modules/oa/financeSubInfoForm";
	}
	/**
	 * 保存、编辑页面
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(FinanceSubInfo financeSubInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, financeSubInfo)){
			return form(financeSubInfo, model);
		}
		FinanceSubInfoReq change = change(financeSubInfo, FinanceSubInfoReq.class);
		User user = UserUtils.getUser();
		change.setUserId(user.getId());
		if(StringUtils.isNotBlank(financeSubInfo.getSubCode())){//校验财务编号不能重复
			int count = intfzFinanceSubInfoService.queryFinanceSubById(change);
			if(count > 0){
				model.addAttribute("message", "财务编号不能重复！");
				model.addAttribute("financeSubInfo", financeSubInfo);
				return "modules/oa/financeSubInfoForm";
			}
		}
		try{
			intfzFinanceSubInfoService.save(change);
		}catch (ServiceException e){
			model.addAttribute("message", e.getMessage());
			model.addAttribute("financeSubInfo", financeSubInfo);
			return "modules/oa/financeSubInfoForm";
		}

		addMessage(redirectAttributes, "保存财务科目成功");
		return "redirect:"+Global.getAdminPath()+"/oa/financeSubInfo/?repage";
	}
	/**
	 * 删除
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(FinanceSubInfo inanceSubInfo, RedirectAttributes redirectAttributes) {
		FinanceSubInfoReq change = change(inanceSubInfo,FinanceSubInfoReq.class);
		intfzFinanceSubInfoService.delete(change);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/oa/financeSubInfo/?repage";
	}


	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,
											  @RequestParam(required=false) Long grade,
											  @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = com.google.common.collect.Lists.newArrayList();

		if(StringUtils.isNotBlank(extId)){
			extId = extId.substring(0, extId.length()-1);
		}
		List<FinanceSubInfoResp> list = intfzFinanceSubInfoService.findList(new FinanceSubInfoReq());
		for (int i=0; i<list.size(); i++){
			FinanceSubInfoResp e = list.get(i);
			if (e.getParIds().split(",").length < 5 &&
					(StringUtils.isBlank(extId) || (extId!=null &&
					!extId.equals(e.getId()) &&
					e.getParIds().indexOf(","+extId+",")==-1))){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParId());
				map.put("pIds", e.getParIds());
				map.put("name",e.getSubName() + "[" + e.getSubCode() + "]");
				map.put("extId", extId);
				mapList.add(map);
			}
		}
		return mapList;
	}
}