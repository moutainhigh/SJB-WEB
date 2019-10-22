/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsDetail;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsMain;
import com.sijibao.oa.modules.oa.service.ExpensesStandardsDetailService;
import com.sijibao.oa.modules.oa.service.ExpensesStandardsMainService;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.service.PostService;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 费用科目控制标准Controller
 * @author xuby
 * @version 2018-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/expensesStands")
public class ExpensesStandardsController extends BaseController {
	@Autowired
	private ExpensesStandardsMainService expensesStandardsMainService;
	@Autowired
	private ExpensesStandardsDetailService expensesStandardsDetailService;
	@Autowired
	private PostService postService;
	
	@ModelAttribute
	public ExpensesStandardsMain get(@RequestParam(required=false) String id) {
		ExpensesStandardsMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = expensesStandardsMainService.get(id);
		}
		if (entity == null){
			entity = new ExpensesStandardsMain();
		}
		return entity;
	}
	
	/**
	 * 查询控制标准主表列表
	 * @param expensesStandsMain
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:expensesStands:view")
	@RequestMapping(value = "list")
	public String list(ExpensesStandardsMain expensesStandsMain, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<ExpensesStandardsMain> resultPage = expensesStandardsMainService
				.findPage(new Page<ExpensesStandardsMain>(request, response), expensesStandsMain);
		for(ExpensesStandardsMain m:resultPage.getList()){
			if(StringUtils.isNotBlank(m.getStandsType())){
				m.setStandsTypes(m.getStandsType().split(","));
				StringBuffer standsTypeName = new StringBuffer();
				for(int i = 0;i<m.getStandsTypes().length;i++){
					standsTypeName.append(DictUtils.getDictLabel(m.getStandsTypes()[i], "oa_standsType", ""));
					if(i < m.getStandsTypes().length - 1){
						standsTypeName.append(",");
					}
				}
				m.setStandsType(standsTypeName.toString());
			}
			if(m.getProject() != null && "1".equals(m.getProject().getEnable())){
				m.getProject().setSubName(m.getProject().getSubName() + "(停用)");
			}
		}
		model.addAttribute("page", resultPage);
		return "modules/oa/expensesStandardsMainList";
	}
	
	/**
	 * 查询控制标准明细列表
	 * @param expensesStandsDetail
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:expensesStands:view")
	@RequestMapping(value = "listDetail")
	public String listDetail(ExpensesStandardsDetail expensesStandsDetail, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<ExpensesStandardsDetail> resultPage = expensesStandardsDetailService
				.findPage(new Page<ExpensesStandardsDetail>(request, response), expensesStandsDetail);
		
		for(ExpensesStandardsDetail d:resultPage.getList()){
			//控制类别
			if(StringUtils.isNotBlank(d.getStandsType())){
				d.setStandsTypes(d.getStandsType().split(","));
				StringBuffer standsTypeName = new StringBuffer();
				for(int i = 0;i<d.getStandsTypes().length;i++){
					standsTypeName.append(DictUtils.getDictLabel(d.getStandsTypes()[i], "oa_standsType", ""));
					if(i < d.getStandsTypes().length - 1){
						standsTypeName.append(",");
					}
				}
				d.setStandsType(standsTypeName.toString());
			}
			//城市类别
			if(StringUtils.isNotBlank(d.getCityType())){
				d.setCityTypes(d.getCityType().split(","));
				StringBuffer cityTypeName = new StringBuffer();
				for(int i = 0;i<d.getCityTypes().length;i++){
					cityTypeName.append(DictUtils.getDictLabel(d.getCityTypes()[i], "oa_city_type", ""));
					if(i < d.getStandsTypes().length - 1){
						cityTypeName.append(",");
					}
				}
				d.setCityType(cityTypeName.toString());
			}

			//交通方式
			if(StringUtils.isNotBlank(d.getTransMode())){
				d.setTransModes(d.getTransMode().split(","));
				StringBuffer transModesName = new StringBuffer();
				for(int i = 0;i<d.getTransModes().length;i++){
					transModesName.append(DictUtils.getDictLabel(d.getTransModes()[i], "oa_traffic_type", ""));
					if(i < d.getTransModes().length - 1){
						transModesName.append(",");
					}
				}
				d.setTransMode(transModesName.toString());
			}
			//岗位
			if(StringUtils.isNotBlank(d.getPostCode())){
				d.setPostCodes(d.getPostCode().split(","));
				StringBuffer postCodesName = new StringBuffer();
				for(int i = 0;i<d.getPostCodes().length;i++){
					//获取岗位名称
					PostInfo postInfo = new PostInfo();
					postInfo.setPostCode(d.getPostCodes()[i]);
					List<PostInfo> postInfoList = postService.findList(postInfo);
					if(postInfoList != null && postInfoList.size() > 0){
						postCodesName.append(postInfoList.get(0).getPostName());
						if(i < d.getPostCodes().length - 1){
							postCodesName.append(",");
						}
					}
				}
				d.setPostCode(postCodesName.toString());
			}
			
			//金额单位
			if(StringUtils.isNotBlank(d.getAmountUnit())){
				d.setAmountUnit(DictUtils.getDictLabel(d.getAmountUnit(), "oa_amountUnit", ""));
			}
		}
		
		model.addAttribute("page", resultPage);
		model.addAttribute("standsMainId", expensesStandsDetail.getStandsMainId());
		return "modules/oa/expensesStandardsDetailList";
	}
	
	
	/**
	 * 控制标准添加跳转
	 * @param expensesStandsMain
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:expensesStands:edit")
	@RequestMapping(value = "form")
	public String form(ExpensesStandardsMain expensesStandsMain, Model model){
		if(StringUtils.isNotBlank(expensesStandsMain.getStandsType())){
			expensesStandsMain.setStandsTypes(expensesStandsMain.getStandsType().split(","));
		}
		if(expensesStandsMain.getProject() != null && "1".equals(expensesStandsMain.getProject().getEnable())){
			expensesStandsMain.getProject().setSubName(expensesStandsMain.getProject().getSubName() + "（停用）");
		}
		model.addAttribute("expensesStandsMain",expensesStandsMain);
		return "modules/oa/expensesStandardsMainForm";
	}
	
	
	/**
	 * 控制标准明细添加跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:expensesStands:edit")
	@RequestMapping(value = "formDetail")
	public String formDetail(ExpensesStandardsDetail expensesStandardsDetail, Model model){
		if(StringUtils.isNoneBlank(expensesStandardsDetail.getId())){
			expensesStandardsDetail = expensesStandardsDetailService.get(expensesStandardsDetail.getId());
			//设置岗位
			if(StringUtils.isNotBlank(expensesStandardsDetail.getPostCode())){
				expensesStandardsDetail.setPostCodes(expensesStandardsDetail.getPostCode().split(","));
			}
			//城市类别
			if(StringUtils.isNotBlank(expensesStandardsDetail.getCityType())){
				expensesStandardsDetail.setCityTypes(expensesStandardsDetail.getCityType().split(","));
			}
			//交通方式
			if(StringUtils.isNotBlank(expensesStandardsDetail.getTransMode())){
				expensesStandardsDetail.setTransModes(expensesStandardsDetail.getTransMode().split(","));
			}
		}
		if(StringUtils.isNoneBlank(expensesStandardsDetail.getStandsMainId())){
			ExpensesStandardsMain main = expensesStandardsMainService.get(expensesStandardsDetail.getStandsMainId());
			expensesStandardsDetail.setStandsTypes(main.getStandsType().split(","));
		}
		model.addAttribute("expensesStandardsDetail",expensesStandardsDetail);
		return "modules/oa/expensesStandardsDetailForm";
	}
	
	/**
	 * 控制标准保存
	 * @param expensesStandsMain
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:expensesStands:edit")
	@RequestMapping(value = "save")
	public String save(ExpensesStandardsMain expensesStandsMain, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		
		/********************根据科目+组织机构ID校验唯一********************/
		if(StringUtils.isBlank(expensesStandsMain.getId())){
			ExpensesStandardsMain main = new ExpensesStandardsMain();
			main.setProject(expensesStandsMain.getProject());
			main.setOfficeId(expensesStandsMain.getOfficeId());
			List<ExpensesStandardsMain> mainList = expensesStandardsMainService.findList(main);
			if(mainList != null && mainList.size() > 0){
				addMessage(redirectAttributes, "保存控制标准失败,同一部门和科目只能设置一条控制标准");
				return "redirect:"+Global.getAdminPath()+"/oa/expensesStands/list?repage";
			}
		}
		
		if(expensesStandsMain != null && expensesStandsMain.getStandsTypes() != null){
			StringBuffer standsType = new StringBuffer();
			for(int i = 0;i<expensesStandsMain.getStandsTypes().length;i++){
				standsType.append(expensesStandsMain.getStandsTypes()[i]);
				if(i < expensesStandsMain.getStandsTypes().length - 1){
					standsType.append(",");
				}
			}
			expensesStandsMain.setStandsType(standsType.toString());
		}
		/******保存控制标准主表信息******/
		expensesStandardsMainService.save(expensesStandsMain);
		
		/******更新对应的标准明细信息*******/
		ExpensesStandardsDetail detail = new ExpensesStandardsDetail();
		detail.setStandsMainId(expensesStandsMain.getId());
		List<ExpensesStandardsDetail> detailList = expensesStandardsDetailService.findList(detail);
		if(detailList != null && detailList.size() > 0){
			for(ExpensesStandardsDetail d:detailList){
				d.setStandsType(expensesStandsMain.getStandsType());
				expensesStandardsDetailService.save(d);
			}
		}
		addMessage(redirectAttributes, "保存控制标准成功!");
		return "redirect:"+Global.getAdminPath()+"/oa/expensesStands/list?repage";
	}
	
	
	/**
	 * 控制标准明细保存
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:expensesStands:edit")
	@RequestMapping(value = "saveDetail")
	public String saveDetail(ExpensesStandardsDetail expensesStandardsDetail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model){
		//如果交通方式超过15个，将不能保存
		if(expensesStandardsDetail.getTransModes() != null && expensesStandardsDetail.getTransModes().length > 15){
			addMessage(redirectAttributes, "控制标准明细保存失败,交通方式最多允许选择15个科目!");
			return "redirect:"+Global.getAdminPath()+"/oa/expensesStands/formDetail?id="
					+expensesStandardsDetail.getId() + "&standsMainId=" + expensesStandardsDetail.getStandsMainId();
		}
		//如果控制方式不存在，则直接从主表读取
		if(expensesStandardsDetail != null 
				&& (expensesStandardsDetail.getStandsTypes() == null ||  expensesStandardsDetail.getStandsTypes().length == 0)
					&& StringUtils.isNotBlank(expensesStandardsDetail.getStandsMainId())){
			ExpensesStandardsMain expensesStandardsMain = expensesStandardsMainService.get(expensesStandardsDetail.getStandsMainId());
			expensesStandardsDetail.setStandsType(expensesStandardsMain.getStandsType().toString());
		}
		
		if(expensesStandardsDetail != null ){
//			//设置控制类别
//			StringBuffer standsType = new StringBuffer();
//			for(int i = 0;i<expensesStandardsDetail.getStandsTypes().length;i++){
//				standsType.append(expensesStandardsDetail.getStandsTypes()[i]);
//				if(i < expensesStandardsDetail.getStandsTypes().length - 1){
//					standsType.append(",");
//				}
//			}
//			expensesStandardsDetail.setStandsType(standsType.toString());
			//设置城市类别
			if(expensesStandardsDetail.getCityTypes() != null){
				//设置控制类别
				StringBuffer cityType = new StringBuffer();
				for(int i = 0;i<expensesStandardsDetail.getCityTypes().length;i++){
					cityType.append(expensesStandardsDetail.getCityTypes()[i]);
					if(i < expensesStandardsDetail.getCityTypes().length - 1){
						cityType.append(",");
					}
				}
				expensesStandardsDetail.setCityType(cityType.toString());
			}
			
			//设置交通方式
			if(expensesStandardsDetail.getCityTypes() != null){
				//设置控制类别
				StringBuffer transMode = new StringBuffer();
				for(int i = 0;i<expensesStandardsDetail.getTransModes().length;i++){
					transMode.append(expensesStandardsDetail.getTransModes()[i]);
					if(i < expensesStandardsDetail.getTransModes().length - 1){
						transMode.append(",");
					}
				}
				expensesStandardsDetail.setTransMode(transMode.toString());
			}
			
			//交通方式
			if(expensesStandardsDetail.getPostCodes() != null){
				//设置控制类别
				StringBuffer postCode = new StringBuffer();
				for(int i = 0;i<expensesStandardsDetail.getPostCodes().length;i++){
					postCode.append(expensesStandardsDetail.getPostCodes()[i]);
					if(i < expensesStandardsDetail.getPostCodes().length - 1){
						postCode.append(",");
					}
				}
				expensesStandardsDetail.setPostCode(postCode.toString());
			}
		}
		/****保存控制标准明细信息****/
		expensesStandardsDetailService.save(expensesStandardsDetail);
		model.addAttribute("expensesStandsDetail",expensesStandardsDetail);
		addMessage(redirectAttributes, "保存控制标准成功!");
		return "redirect:"+Global.getAdminPath()+"/oa/expensesStands/listDetail?repage&standsMainId="+expensesStandardsDetail.getStandsMainId();
	}
	
	/**
	 * 删除控制标准
	 * @param expensesStandsMain
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:expensesStands:edit")
	@RequestMapping(value = "delete")
	public String delete(ExpensesStandardsMain expensesStandsMain, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		if(StringUtils.isNotBlank(expensesStandsMain.getId())){
			expensesStandardsMainService.delete(expensesStandsMain);
			addMessage(redirectAttributes, "删除控制标准成功!");
		}else{
			addMessage(redirectAttributes, "删除控制标准失败!");
		}
		
		return "redirect:"+Global.getAdminPath()+"/oa/expensesStands/list?repage";
	}
	
	/**
	 * 删除控制标准
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:expensesStands:edit")
	@RequestMapping(value = "deleteDetail")
	public String delete(ExpensesStandardsDetail expensesStandsDetail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		if(StringUtils.isNotBlank(expensesStandsDetail.getId())){
			expensesStandardsDetailService.delete(expensesStandsDetail);
			addMessage(redirectAttributes, "删除控制标准成功!");
		}else{
			addMessage(redirectAttributes, "删除控制标准失败!");
		}
		
		return "redirect:"+Global.getAdminPath()+"/oa/expensesStands/listDetail?repage&standsMainId="+expensesStandsDetail.getStandsMainId();
	}
	
	
}