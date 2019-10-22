/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.web;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sijibao.fileclient.client.FileUpload;
import com.sijibao.fileclient.client.HttpFileUploadClient;
import com.sijibao.fileservercommon.constant.FileTypeEnum;
import com.sijibao.fileservercommon.entity.FileInfoReq;
import com.sijibao.fileservercommon.entity.ResponseResult;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.FileUploadRes;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.entity.*;
import com.sijibao.oa.modules.flow.service.*;
import com.sijibao.oa.modules.intfz.response.expense.SubInfoResponse;
import com.sijibao.oa.modules.intfz.service.expense.IntfzExpenseFlowService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.ExpensesSubConf;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.ExpensesSubConfService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.PostService;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 费用报销Controller
 * @author Petter
 * @version 2017-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/expenseFlow")
public class ExpenseFlowController extends BaseController {

	@Autowired
	private ExpenseFlowService expenseFlowService;
	@Autowired
	private ExpenseDetailService expenseDetailService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ExpenseAttachmentService expenseAttachmentService;
	@Autowired
	private IntfzExpenseFlowService wxExpenseFlowService;
	@Autowired
	private PostService postService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RecpParamsService recpParamsService;
	@Autowired
	private RecpFlowService recpFlowService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private ExpensesSubConfService expensesSubConfService;
	@ModelAttribute
	public ExpenseFlow get(@RequestParam(required=false) String id) {
		ExpenseFlow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = expenseFlowService.get(id);
		}
		if (entity == null){
			entity = new ExpenseFlow();
		}
		return entity;
	}
	
	@RequiresPermissions("flow:expenseFlow:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpenseFlow expenseFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expenseFlow.setCreateBy(user);
		}
		Page<ExpenseFlow> page = expenseFlowService.findPage(new Page<ExpenseFlow>(request, response), expenseFlow);
		//查询系统中的项目信息
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectState(Constant.PEOJECT_STATE_ONE);
		List<ProjectInfo> projectInfoList = projectInfoService.findList(projectInfo);
		model.addAttribute("page", page);
		model.addAttribute("projectInfoList", projectInfoList);
		return "modules/flow/expenseFlowList";
	}
	
	/**
	 * 财务提前打款列表
	 * @param expenseFlow
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:view")
	@RequestMapping(value = "bringRemitList")
	public String bringRemitList(ExpenseFlow expenseFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expenseFlow.setCreateBy(user);
		}
		String bringRemitOrg = SysParamsUtils.getParamValue(Global.BRING_REMIT_ORG, Global.SYS_PARAMS, "");
		expenseFlow.setBringRemitOrg(bringRemitOrg);
		expenseFlow.setCurrentTaskAssignee(user.getLoginName());
		Page<ExpenseFlow> page = expenseFlowService.queryBringRemitListForPage(new Page<ExpenseFlow>(request, response), expenseFlow);
		//查询系统中的项目信息
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectState(Constant.PEOJECT_STATE_ONE);
		List<ProjectInfo> projectInfoList = projectInfoService.findList(projectInfo);
		model.addAttribute("projectInfoList", projectInfoList);
		model.addAttribute("page", page);
		return "modules/flow/expenseFlowBringRemitList";
	}
	
	
	/**
	 * 财务提前打款详情
	 * @param expenseFlow
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:edit")
	@RequestMapping(value = "bringRemitForm")
	public String bringRemitForm(ExpenseFlow expenseFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		DecimalFormat df = new DecimalFormat("0.00");
		
		ExpenseDetail query = new ExpenseDetail();
		query.setProcCode(expenseFlow.getProcCode());
		List<ExpenseDetail> detailsList = expenseDetailService.findList(query);
		model.addAttribute("detailsList", detailsList);
		
		//查询附件信息
		ExpenseAttachment expenseAttachment = new ExpenseAttachment();
		expenseAttachment.setExpenseCode(expenseFlow.getProcCode());
		List<ExpenseAttachment> expenseAttachmentList = expenseAttachmentService.findListByProcCode(expenseAttachment);
		
		List<ExpenseDetail> subCodeList = new ArrayList<ExpenseDetail>();
		for(ExpenseAttachment e:expenseAttachmentList){
			String url = SysParamsUtils.getParamValue(Global.FILE_SERVER_URL, Global.SYS_PARAMS, ""); //图片服务器IP
			e.setExpenseAttachmentUrl(url+e.getExpenseAttachmentUrl());
		}
		
		for(ExpenseDetail detail:detailsList){
			//组装科目附件数据结构
			if(StringUtils.isNotBlank(detail.getSecondSub())){
				if(subCodeList.size() == 0){
					subCodeList.add(detail);
				}else{
					int count = 0;
					for(ExpenseDetail ed:subCodeList){
						if(ed.getSecondSub().equals(ed.getSecondSub())){
							count ++;
						}
					}
					if(count == 0){
						subCodeList.add(detail);
					}
				}
			}
		}
		//根据科目编码查询对应的附件信息
		Map<String,List<ExpenseAttachment>> subAttachmentMap = new HashMap<String,List<ExpenseAttachment>>();
		for(ExpenseDetail e:subCodeList){
			List<ExpenseAttachment> aList = expenseAttachmentService.queryAttachmentForEcodeAndSubCode(e);
			if(aList != null && aList.size() > 0){
				subAttachmentMap.put(e.getSecondSub(),aList);
			}
		}
		
		model.addAttribute("subAttachmentMap",subAttachmentMap);
		model.addAttribute("expenseAttachmentList",expenseAttachmentList);
		
		//设置发票所属城市名称
		expenseFlow.setTaxCityName(DictUtils.getDictLabel(expenseFlow.getTaxCityCode(), "tax_city", ""));
		if(expenseFlow.getExpenseTotal() == null){
			expenseFlow.setExpenseTotal(new BigDecimal(df.format(expenseFlow.getExpenseTotal())));
		}
		StringBuilder s = new StringBuilder();
		if(StringUtils.isNotBlank(expenseFlowService.getPostId(expenseFlow))){
			for (String postId : expenseFlowService.getPostId(expenseFlow).split(",")) {
				if(StringUtils.isNotBlank(s.toString())){
					s.append(",");
				}
				s.append(postService.get(postId).getPostName());
			}
		}else{
			s.append(postService.get(user.getPostIds()).getPostName());
		}
		
		model.addAttribute("postName", s.toString());
		model.addAttribute("user", user);
		model.addAttribute("expenseFlow", expenseFlow);
		
		return "modules/flow/expenseFlowBringRemit";
	}
	
	/**
	 * 报销申请跳转
	 * @param expenseFlow
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:view")
	@RequestMapping(value = "form")
	public String form(ExpenseFlow expenseFlow, Model model){
		String view = "expenseFlowForm";
		DecimalFormat df = new DecimalFormat("0.00");
		User user = UserUtils.getUser();
		if(StringUtils.isBlank(user.getPostIds())){
			throw new ServiceException("申请失败,当前人员没有分配岗位，请联系管理员!"); 
		}
		List<User> userList = systemService.findUserNotAccess(new User());
		model.addAttribute("employeeList",userList);
		// 查看审批申请单
		if (StringUtils.isNotBlank(expenseFlow.getId())){//.getAct().getProcInsId())){
			// 环节编号
			String taskDefKey = expenseFlow.getAct().getTaskDefKey();
			// 查看工单
			if(expenseFlow.getAct().isFinishTask()){
				/*************判断是否可以撤销流程start*************/
				if(StringUtils.isBlank(expenseFlow.getAct().getTaskId())){
					model.addAttribute("noRepeal", true);
				}else{
					//判断单据状态是否为审批结束
					if(Constant.expense_approve_end.equals(expenseFlow.getExpenseStatus())){
						model.addAttribute("noRepeal", true);
					}else{
						//判断当前任务是否已经在本人待办任务中
						Act act = actTaskService.queryThisRunTaskId(expenseFlow.getAct());
						if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
							expenseFlow.getAct().setStatus("finish");
							model.addAttribute("noRepeal", true);
						}
					}
				}
				/*************判断是否可以撤销流程end*************/
				
				/***************判断是否可以删除报销申请start****************/
				if (StringUtils.isNotBlank(expenseFlow.getApplyPerCode())
						&& user.getLoginName().equals(expenseFlow.getApplyPerCode())) {
					model.addAttribute("delete", true);
				}
				/***************判断是否可以删除报销申请end****************/
				view = "expenseFlowView";
			}
			// 修改环节
			else if ("modify".equals(taskDefKey)){
				view = "expenseFlowForm";
			}
			// 审核环节
			else if (taskDefKey.startsWith("audit")){
				//提前打款处理人员
				String bringRemitPersonel = SysParamsUtils.getParamValue(Global.BRING_REMIT_PERSONEL, Global.SYS_PARAMS, "");
				if(bringRemitPersonel.contains(user.getLoginName())){
					model.addAttribute("bringRemitPersonel", true);
				}
				view = "expenseFlowAudit";
			}
			//保存修改
			if(Constant.expense_save.equals(expenseFlow.getExpenseStatus())){
				view = "expenseFlowForm";
			}
			ExpenseDetail query = new ExpenseDetail();
			query.setProcCode(expenseFlow.getProcCode());
			if(StringUtils.isNotBlank(expenseFlow.getDetailOrderBy())){
				query.setDetailOrderBy(expenseFlow.getDetailOrderBy());
			}
			List<ExpenseDetail> detailsList = expenseDetailService.findList(query);
			for(ExpenseDetail d:detailsList){
				if(d.getExpenseAmt() != null){
					d.setExpenseAmt(new BigDecimal(df.format(d.getExpenseAmt())));
				}
			}
			model.addAttribute("detailsList", detailsList);
			//查询附件信息
			ExpenseAttachment expenseAttachment = new ExpenseAttachment();
			expenseAttachment.setExpenseCode(expenseFlow.getProcCode());
			List<ExpenseAttachment> expenseAttachmentList = expenseAttachmentService.findListByProcCode(expenseAttachment);
			
			String url = SysParamsUtils.getParamValue(Global.FILE_SERVER_URL, Global.SYS_PARAMS, ""); //图片服务器IP
			for(ExpenseAttachment e:expenseAttachmentList){
				if(StringUtils.isBlank(e.getExpenseDetailId())){
					e.setExpenseAttachmentUrl(url+e.getExpenseAttachmentUrl());
				}
				e.setImgServerUrl(url);
			}
			if(detailsList != null && detailsList.size() > 0){
				for(ExpenseDetail detail:detailsList){
					List<ExpenseAttachment> subCodeAttachmentList = expenseAttachmentService.queryAttachmentForEcodeAndSubCode(detail);
					if(subCodeAttachmentList != null && subCodeAttachmentList.size() > 0){
						//组装科目附件数据结构
						for(ExpenseAttachment e:subCodeAttachmentList){
							if(detail.getId().equals(e.getExpenseDetailId())){
								e.setSubImgUrl(e.getExpenseAttachmentUrl());
								e.setExpenseAttachmentUrl(url+e.getExpenseAttachmentUrl());
								detail.getExpenseAttachment().add(e);
							}
						}
					}else{
						if(StringUtils.isNotBlank(detail.getSecondSub())){
							ExpensesSubConf expensesSubConf = new ExpensesSubConf();
							expensesSubConf.setSubCode(detail.getSecondSub());
							List<ExpensesSubConf> expensesSubConfList = expensesSubConfService.findList(expensesSubConf);
							if(expensesSubConfList != null && expensesSubConfList.size() > 0){
								for(ExpensesSubConf es:expensesSubConfList){
									ExpenseAttachment e = new ExpenseAttachment();
									e.setIsRequired(es.getIsRequired());
									detail.getExpenseAttachment().add(e);
								}
							}
						}
					}
				}
			}
			model.addAttribute("expenseAttachmentList",expenseAttachmentList);
		}else{
			expenseFlow.setApplyPerCode(user.getLoginName());
			expenseFlow.setApplyPerName(user.getName());
			expenseFlow.setApplyTime(new Date());
			expenseFlow.setOffice(user.getOffice());
		}
		//查询陪客人员信息
		if(StringUtils.isNotBlank(expenseFlow.getProcCode())){
			RecpParams recpParams = new RecpParams();
			recpParams.setProcCode(expenseFlow.getProcCode()); //流程编号
			List<RecpParams> recpParamsList = recpParamsService.findList(recpParams);
			if(recpParamsList != null && recpParamsList.size() > 0){
				String[] recpParticPersonels = new String[recpParamsList.size()];
				for(int i = 0;i < recpParamsList.size();i++){
					recpParticPersonels[i] = recpParamsList.get(i).getParamValue();
				}
				expenseFlow.setEmployees(recpParticPersonels);
			}
		}
		
		//设置发票所属城市名称
		expenseFlow.setTaxCityName(DictUtils.getDictLabel(expenseFlow.getTaxCityCode(), "tax_city", ""));
		if(expenseFlow.getExpenseTotal() != null){
			expenseFlow.setExpenseTotal(new BigDecimal(df.format(expenseFlow.getExpenseTotal())));
		}
		StringBuilder s = new StringBuilder();
		if(StringUtils.isNotBlank(expenseFlowService.getPostId(expenseFlow))){
			for (String postId : expenseFlowService.getPostId(expenseFlow).split(",")) {
				if(StringUtils.isNotBlank(s.toString())){
					s.append(",");
				}
				s.append(postService.get(postId).getPostName());
			}
		}else if(user.getId().equals("1")){
			
		}else{
			s.append(postService.get(user.getPostIds()).getPostName());
		}
		//设置报销类别
		if(StringUtils.isNotBlank(expenseFlow.getApplyType())){
			expenseFlow.setApplyTypeName(DictUtils.getDictLabel(expenseFlow.getApplyType(), "oa_expense_type", ""));
		}
		
		//设置招待申请名称
		if(StringUtils.isNotBlank(expenseFlow.getRecpProcCode())){
			RecpFlow recpFlow = recpFlowService.getByProcCode(expenseFlow.getRecpProcCode());
			if(recpFlow != null){
				expenseFlow.setRecpProcName(recpFlow.getRecpTheme());
			}
		}
		model.addAttribute("postName", s.toString());
		model.addAttribute("user", user);
		model.addAttribute("expenseFlow", expenseFlow);
		return "modules/flow/" + view;
	}

	@RequiresPermissions("flow:expenseFlow:edit")
	@RequestMapping(value = "save")
	public String save(ExpenseFlow expenseFlow, ExpenseFlowDetailListRequest expenseDetailListRequest,
					   Model model, RedirectAttributes redirectAttributes,
					   HttpServletRequest request){
		if (!beanValidator(model, expenseFlow)){
			return form(expenseFlow, model);
		}
		List<ExpenseDetail> expenseDetailList = new ArrayList<>();
		expenseDetailList = expenseDetailListRequest.getExpenseDetail();
		
		List<ExpenseAttachment> expenseAttachmentList = new ArrayList<>();
		expenseAttachmentList = expenseDetailListRequest.getExpenseAttachments();
		Map<String,Object> resultMap = expenseFlowService.save(expenseFlow, expenseDetailList,expenseAttachmentList);
		if(resultMap.get("message") != null && resultMap.get("checkFlag") != null && !(boolean)resultMap.get("checkFlag")){
			logger.info(expenseFlow.getProcCode()+":"+resultMap.get("message"));
			addMessage(model, resultMap.get("message").toString());
			return form(expenseFlow, model);
		}
		String returnMessage = "提交审批成功";
		if(Constant.expense_yes.equals(expenseFlow.getSaveFlag())){
			returnMessage = "保存报销成功";
		}
		addMessage(redirectAttributes, returnMessage);
		return "redirect:"+Global.getAdminPath()+"/flow/expenseFlow/?repage";
	}
	
	/**
	 * 保存提前打款信息
	 * @param expenseFlow
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:edit")
	@RequestMapping(value = "saveBringRemitInfo")
	public String saveBringRemitInfo(ExpenseFlow expenseFlow, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		if(expenseFlow.getBringAmount() == null){
			addMessage(model, "处理失败,请输入提前打款金额!");
			return bringRemitForm(expenseFlow,request,response,model);
		}
		expenseFlow.setBringRemitStatus(Constant.BRING_REMIT_STATUS);
		expenseFlowService.updateBringInfo(expenseFlow);
		addMessage(redirectAttributes, "提前打款处理成功!");
		return "redirect:"+Global.getAdminPath()+"/flow/expenseFlow/bringRemitList?repage";
	}
	
	/**
	 * 工单执行（完成任务）
	 * @param testAudit
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(ExpenseFlow expenseFlow,String title,String assigneeName,Date beginDate,Date endDate , Model model){
		if (Constant.expense_no.equals(expenseFlow.getAct().getFlag())
				&& StringUtils.isBlank(expenseFlow.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(expenseFlow, model);
		}
		expenseFlowService.auditSave(expenseFlow);
		return "redirect:" + adminPath + "/act/task/todo/?title=" + expenseFlow.getAct().getTitle() + "&assigneeName=" +  expenseFlow.getAct().getAssigneeName()
				+ "&beginDate" + expenseFlow.getAct().getBeginDate() + "&endDate" + expenseFlow.getAct().getEndDate();
	}
	
	/**
	 * 任务撤销
	 * @param expenseFlow
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:edit")
	@RequestMapping(value = "repealTask")
	public String repealTask(ExpenseFlow expenseFlow, Model model){
		if(StringUtils.isBlank(expenseFlow.getAct().getTaskId())){
			addMessage(model,"当前任务ID不能为空");
			return form(expenseFlow, model);
		}
		
		if(StringUtils.isBlank(expenseFlow.getAct().getProcInsId())){
			addMessage(model,"当前流程实例ID不能为空");
			return form(expenseFlow, model);
		}
		
		//获取当前登录人
		User user = UserUtils.getUser();
		
		//判断当前单据已经审批结束
		ExpenseFlow e = expenseFlowService.getByProcInsId(expenseFlow.getAct().getProcInsId());
		if(Constant.expense_approve_end.equals(e.getExpenseStatus())){
			addMessage(model,"当前单据已经审批结束，无法撤回");
			return form(expenseFlow, model);
		}
		//发起撤销
		expenseFlowService.repealTask(expenseFlow,user);
		return "redirect:" + adminPath + "/act/task/todo/?title=" + expenseFlow.getAct().getTitle();
	}
	
	/**
	 * 删除报销任务
	 * @param expenseFlow
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:edit")
	@RequestMapping(value = "delete")
	public String delete(ExpenseFlow expenseFlow, RedirectAttributes redirectAttributes, Model model) {
//		expenseFlowService.delete(expenseFlow);
		
//		if(ExpenseConstatnt.expense_save.equals(expenseFlow.getExpenseStatus())){ //保存状态不需要删除任务
//			expenseFlow.getAct().setTaskId(null); //任务ID
//		}else{
//			Act act = new Act();
//			act.setAssignee(new User().getLoginName()); //当前登录用户
//			act.setProcInsId(expenseFlow.getProcInsId()); //流程实例ID
//			act = actTaskService.queryThisRunTaskId(act);
//			if(StringUtils.isBlank(act.getTaskId())){
//				addMessage(redirectAttributes, "taskId不能为空");
//			}else{
//				expenseFlow.getAct().setTaskId(act.getTaskId()); //任务ID
//			}
//		}
		expenseFlowService.repealApply(expenseFlow);
		addMessage(redirectAttributes, "删除费用报销成功");
		return "redirect:"+Global.getAdminPath()+"/flow/expenseFlow/?repage";
	}
	
	
	/**
	 * 查询费用科目
	 * url:subinfo
	 * request subCode:科目编号(非必填), parSubCode:父级科目编号(非必填), subName:科目名称(非必填)
	 * response data:{"list":["subCode":"科目编号", "parSubCode":"父级科目编号", "subName":"科目名称","expenseNormal":"费用标准","unitType":"单位类型"]}
	 */
	@RequestMapping(value = "subInfoList")
	@ResponseBody
	public void subInfolist(ExpensesSubInfo expensesSubInfo,
							HttpServletRequest request, HttpServletResponse response){
		logger.info("=======ExpenseFlowController subInfolist start=============" + expensesSubInfo.toString());
		try {
			List<SubInfoResponse> subInfolist = wxExpenseFlowService.getSubInfoList(expensesSubInfo);
			logger.info("=======ExpenseFlowController subInfolist end=============");
			logger.debug(JSON.toJSONString(subInfolist));
			response.getWriter().write(JSON.toJSONString(subInfolist));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 报销附件上传
	 * @param files
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:expenseFlow:edit")
	@RequestMapping(value = "upload", method= RequestMethod.POST)
	@ResponseBody
    public String upload(@RequestParam MultipartFile[] files, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
		JSONObject resJson = new JSONObject();
		try {
			JSONObject storfiles = new JSONObject();
			for(MultipartFile file:files){
				if(file == null){
					resJson.put("resCode", 0);
					resJson.put("resDesc", "请重新选择文件");
					return resJson.toString();
				}
//				User user =  UserUtils.getUser();
				String fileName = file.getOriginalFilename(); //文件名称
				String fileType = file.getContentType(); //文件类型
				logger.info(fileType);
				if(fileType.startsWith("image") || fileType.indexOf("pdf") != -1){
					storfiles.put("fileType", "0");
				}else{
					storfiles.put("fileType", "1");
				}
				String ip = SysParamsUtils.getParamValue(Global.FILE_SERVER_IP, Global.SYS_PARAMS, "192.168.0.211"); //图片服务器IP
				String port = SysParamsUtils.getParamValue(Global.FILE_SERVER_PORT, Global.SYS_PARAMS, "9076"); //图片服务器端口
				String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址
				
				FileUpload fileUpload = new HttpFileUploadClient(ip, port);
				ByteArrayOutputStream out=new ByteArrayOutputStream();
				InputStream ins = file.getInputStream();
				byte[] buffer=new byte[1024*4];
				int n=0;
				while ( (n=ins.read(buffer)) !=-1) {
					out.write(buffer,0,n);
				}
				ins.close();
				byte[] in2b = out.toByteArray();
				out.close();
				ResponseResult res = null;
				System.setProperty("java.awt.headless", "true");
				
				String fLower = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
				FileInfoReq fileInfo = new FileInfoReq();
				fileInfo.setFileName(fileName);
				fileInfo.setFileTypeEnum(FileTypeEnum.of(fLower));
				res = fileUpload.uploadByteStream(in2b, fileInfo);
				
				FileUploadRes fileUploadRes = new FileUploadRes("",res.getFilePathInfo().getRelativePathUrl());
				
//				FileUploadRes res = null;
//				if(fileType.startsWith("image")){
//					res = fileUpload.uploadImage(fileName, in2b);
//				}else{
//					res = fileUpload.uploadDoc(fileName, in2b);
//				}
				
//						(FromEnum.FROM_OPERATION_SERVER,user.getLoginName() , fileName, in2b, FileTypeEnum.IMAGE);
				
				String storfile = fileUploadRes.getStorageFile();
				storfiles.put("url",storfile);
				storfiles.put("fileName",fileName);
				storfiles.put("serverUrl",serverUrl);
			}
			resJson.put("resCode", 1);
			resJson.put("resDesc", "上传成功.");
			resJson.put("storfiles", storfiles);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resJson.put("resCode", 0);
			resJson.put("resDesc", e.getMessage());
		}
		return resJson.toString();
    }
	
	/**
	 * 删除附件信息
	 * @param expenseAttachment
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "deleteAttachment")
	@ResponseBody
	public void deleteAttachment(ExpenseAttachment expenseAttachment, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(expenseAttachment == null){
			response.getWriter().write("参数缺失，删除失败!");
		}
		
		if(StringUtils.isBlank(expenseAttachment.getId())){ //判断ID是否为空
			response.getWriter().write("附件ID为空，删除失败!");
		}
		expenseAttachmentService.delete(expenseAttachment); //删除附件信息
	}
	
	/**
	 * 下载文件并重新赋值文件名
	 * @param response
	 * @param url 附件地址
	 * @param fileName 附件名称
	 */
	@RequestMapping(value = "downFiles")
	public void downFiles(HttpServletResponse response, HttpServletRequest request, String url, String fileName){
		HttpURLConnection httpUrl = null;
		InputStream in = null;
		OutputStream out = null;
		try{
	      URL urlfile = new URL(url);
	      httpUrl = (HttpURLConnection)urlfile.openConnection();
	     
	      byte[] buffer = new byte[1024];
	      in =  new BufferedInputStream(httpUrl.getInputStream());
	      response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
	      out = response.getOutputStream();
	      
          int len = 0;
          while ((len = in.read(buffer)) > 0) {
              out.write(buffer, 0, len);
          }
	      out.close();
	      in.close();
	      httpUrl.disconnect();
	    }catch (Exception e){
	      e.printStackTrace();
	    }finally {
	    	try {
				out.close();
				in.close();
				httpUrl.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  }
}