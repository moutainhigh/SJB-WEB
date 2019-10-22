package sijibao.oa.jeesite.modules.intfz.web.intfzapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.activiti.api.exception.ServiceException;
import com.sijibao.oa.activiti.api.request.CommonFlowHandleParam;
import com.sijibao.oa.activiti.api.request.expense.*;
import com.sijibao.oa.activiti.api.response.TaskResult;
import com.sijibao.oa.activiti.api.response.expense.*;
import com.sijibao.oa.activiti.api.service.ExpenseFlowNewService;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.entity.ExpenseFlow;
import com.sijibao.oa.modules.flow.service.ExpenseFlowService;
import com.sijibao.oa.modules.flow.service.RecpFlowService;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.bean.DictInfo;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.request.common.CommonFlowHandleReq;
import com.sijibao.oa.modules.intfz.request.expense.*;
import com.sijibao.oa.modules.intfz.response.common.PageResponse;
import com.sijibao.oa.modules.intfz.response.common.TaskResponse;
import com.sijibao.oa.modules.intfz.response.expense.*;
import com.sijibao.oa.modules.intfz.utils.CharChangeUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.AppAuthority;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.entity.Dict;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.SysParams;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.AreaService;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * APP报销流程相关
 * @author Petter
 */
@Controller
@RequestMapping(value = "wechat/flow")
@Api(value="APP报销流程服务",tags="APP报销流程服务")
public class IntfzExpenseFlowContoller extends BaseController {

//	@Autowired
//	private IntfzExpenseFlowService intfzExpenseFlowService;
    @Autowired
    private ExpenseFlowNewService expenseFlowNewService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ExpenseFlowService expenseFlowService;
	@Autowired
	private AreaService areaInfoService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RecpFlowService recpFlowService;
	/**
	 * 报销申请
	 */
	@RequestMapping(value = "expenseApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-申请")
    @AppAuthority
	public BaseResp<String> expenseApply(
			@RequestBody ExpenseFlowRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		for(ExpenseFlowDetailRequest dr : req.getExpenseDetail()){
			CharChangeUtils.CharChange(dr);//替换英文字符
		}
		if(user == null){
			return new BaseResp<String>(IntfzRs.ERROR, "未找到用户信息", "");
		}
		req.setProducSide("APP");
		//查询手机号
		if(req.getEmployees() != null && req.getEmployees().length > 0){
			List<String> idss = recpFlowService.queryLoginNames(req.getEmployees());
			String[] employees = idss.toArray(new String[idss.size()]);
			req.setEmployees(employees);
		}

        try {
            ExpenseFlowParam expenseFlow = change(req,ExpenseFlowParam.class);
            List<ExpenseFlowDetailParam> detailList = Lists.newLinkedList();
            for(ExpenseFlowDetailRequest i : req.getExpenseDetail()){
                List<ExpenseFlowAttachmentParam> attachmentList = Lists.newLinkedList();
                for(ExpenseFlowAttachmentRequest j : i.getSubConfList()){
                    attachmentList.add(change(j,ExpenseFlowAttachmentParam.class));
                }
                ExpenseFlowDetailParam detail = change(i,ExpenseFlowDetailParam.class);
                detail.setSubConfList(attachmentList);
                detailList.add(detail);
            }
            expenseFlow.setExpenseDetail(detailList);

            expenseFlowNewService.expenseApply(expenseFlow, user.getId(),Constant.CLIENT_TYPE_APP); //报销申请
        } catch (ServiceException e) {
            return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
        }

		return new BaseResp<String>(IntfzRs.SUCCESS, "申请发起成功", "");
	}
	
	/**
	 * 报销单据保存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "saveExpenseInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-保存")
    @AppAuthority
	public BaseResp<String> saveExpenseInfo(@RequestBody ExpenseFlowRequest req,
											@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
											@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
											HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		//查询手机号
		if(req.getEmployees() != null && req.getEmployees().length > 0){
			List<String> idss = recpFlowService.queryLoginNames(req.getEmployees());
			String[] employees = idss.toArray(new String[idss.size()]);
			req.setEmployees(employees);
		}

        try{
            ExpenseFlowParam expenseFlow = change(req,ExpenseFlowParam.class);
            List<ExpenseFlowDetailParam> detailList = Lists.newLinkedList();
            for(ExpenseFlowDetailRequest i : req.getExpenseDetail()){
                List<ExpenseFlowAttachmentParam> attachmentList = Lists.newLinkedList();
                for(ExpenseFlowAttachmentRequest j : i.getSubConfList()){
                    attachmentList.add(change(j,ExpenseFlowAttachmentParam.class));
                }
                ExpenseFlowDetailParam detail = change(i,ExpenseFlowDetailParam.class);
                detail.setSubConfList(attachmentList);
                detailList.add(detail);
            }
            expenseFlow.setExpenseDetail(detailList);

            expenseFlowNewService.saveExpense(expenseFlow, user.getId(),Constant.CLIENT_TYPE_APP); //报销保存
        }catch (ServiceException e){
            return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
        }

		return new  BaseResp<String>(IntfzRs.SUCCESS, "单据保存成功", "");
	}
	
	/**
	 * 单据审批 同意/驳回
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "completeTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-同意/驳回")
    @AppAuthority
	public BaseResp<String> completeTask(@RequestBody ExpenseFlowCompleteTaskReq req,
										 @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
										 @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
										 HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		//接收复杂json格式的参数
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息
		CharChangeUtils.CharChange(req);//替换英文字符
		if (StringUtils.isBlank(req.getComment()) && "no".equals(req.getFlag())) {
			logger.error("=======WxExpenseFlowContoller completeTask end=============请填写审核意见");
			return new BaseResp<String>(IntfzRs.ERROR, "请填写审核意见", "");
		}
		if (StringUtils.isBlank(req.getProcInsId())){
			logger.error("=======WxExpenseFlowContoller completeTask end=============procInsId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "procInsId is not null", "");
		}
		if(StringUtils.isBlank(req.getExpenseFlowId())){
			logger.error("=======WxExpenseFlowContoller completeTask end=============expenseFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "expenseFormId is not null", "");
		}

        try {
            ExpenseFlowCompleteTaskParam expenseFlowCompleteTaskParam = change(req,ExpenseFlowCompleteTaskParam.class);
            expenseFlowNewService.completeTask(expenseFlowCompleteTaskParam,user.getId()); //完成当前任务
        }catch (ServiceException e){
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

		return new BaseResp<String>(IntfzRs.SUCCESS, "审批成功", "");
	}
	
	/**
	 * 废除报销申请
	 */
	@RequestMapping(value = "repealApply")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-删除")
    @AppAuthority
	public BaseResp<String> repealApply(@RequestBody ExpenseFlowHandleReq req,
										@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
										@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
										HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if (StringUtils.isBlank(req.getExpenseFlowId())){
			logger.info("=======WxExpenseFlowContoller repealApply end=============expenseFlowId is not null");
			return new BaseResp<String>(IntfzRs.ERROR, "expenseFlowId is not null", "");
		}
        User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息

        try {
            expenseFlowNewService.deleteExpenseFlowInfo(req.getExpenseFlowId(),user.getId());  //删除单据
        }catch (ServiceException e){
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }
		return new BaseResp<String>(IntfzRs.SUCCESS, "删除费用报销成功", "");
	}
	
	/**
	 * 查询我的单据列表
	 * @param request  pageNo:当前页, pageSize:每页记录数
	 * @param response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryMyExpenseList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-查询列表")
    @AppAuthority
	public BaseResp<PageResponse<List<ExpenseFlow>>> queryMyExpenseList(
			@RequestBody ExpenseFlowListReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			logger.info("===WxExpenseFlowContoller  notifyWechatOrder res===: " + "null");
			return new BaseResp<PageResponse<List<ExpenseFlow>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<ExpenseFlow>>(null, 0, 0));
		}
		User user = super.getCurrWxUser(sjboacert, clientType); //获取当前申请人信息

        PagerResponse<ExpenseFlowPaginationResult> page = new PagerResponse<>();
        List<ExpenseFlow> resultList = new LinkedList<>();
        try {
            ExpenseFlowParam expenseFlowParam = change(req,ExpenseFlowParam.class);
            expenseFlowParam.setProducSide("APP");
            page = expenseFlowNewService.queryMyExpenseList(expenseFlowParam,user.getLoginName());
            Office temp = new Office();
            for(ExpenseFlowPaginationResult item : page.getList()){
                ExpenseFlow expenseFlow = change(item,ExpenseFlow.class);
                temp.setId(item.getOfficeId());
                temp.setName(item.getOfficeName());
                expenseFlow.setOffice(temp);
                resultList.add(expenseFlow);
            }
        }catch (ServiceException e){
            logger.error("===IntfzExpenseFlowController queryMyExpenseList throws ServiceException:{}"+e.getMessage());
        }
		return new BaseResp<PageResponse<List<ExpenseFlow>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<ExpenseFlow>>(resultList, req.getPageNo(), page.getCount()));
	}
	
	/**
	 * 根据字典类型查询数据字典信息
	 * @param  //字典类型tax_city:发票所属城市,oa_project:项目
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryDictInfo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-获取字典信息,tax_city:发票所属城市,oa_project:项目,oa_expense_type:报销类型")
    @AppAuthority
	public BaseResp<List<DictInfo>> queryDictInfo(
			@RequestBody DictInfo dictInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		if(dictInfo != null && StringUtils.isBlank(dictInfo.getDictType())){
			return new BaseResp<List<DictInfo>>(IntfzRs.ERROR, "请输入要查询的数据字典类型", new ArrayList<DictInfo>());
		}
		List<DictInfo> dictList = Lists.newArrayList();

		if("oa_project".equals(dictInfo.getDictType())){
			String value = dictInfo.getProjectName();
			List<SysParams> list = SysParamsUtils.getParamsList("alive_word");
			ProjectInfo query = new ProjectInfo();
			for (SysParams sysParams : list) {
				if(StringUtils.isNotBlank(sysParams.getParamValue())){
					String[] split = sysParams.getParamValue().split(",");
					for (String ss : split) {
						if(StringUtils.isNotBlank(value) && ss.indexOf(value) > -1){
							return new  BaseResp<List<DictInfo>>(IntfzRs.ERROR, "该搜索范围过广，请输入精确信息!", null);
						}
					}
				}
			}
			query.setProjectName(value);
			List<ProjectInfo> projectList = projectInfoService.findList(query);
			for(ProjectInfo project : projectList){
				dictList.add(new DictInfo(project.getId(), project.getProjectName().trim(), dictInfo.getDictType(),
						project.getProjectName().trim(), 0, null,project));
			}
		}else{
			List<Dict> resultList = DictUtils.getDictList(dictInfo.getDictType());
			for(Dict dict : resultList){
				dictList.add(new DictInfo(dict.getValue().trim(),dict.getLabel().trim(),dict.getType().trim(),
						dict.getDescription().trim(),dict.getSort(),dict.getParentId()));
			}
		}
		if(dictList == null || dictList.size() == 0){
			return new  BaseResp<List<DictInfo>>(IntfzRs.ERROR, "未查询到结果！", null);
		}
		return new  BaseResp<List<DictInfo>>(IntfzRs.SUCCESS, "ok", dictList);
	}
	
//	/**
//	 * 流程发起（废弃！！！）
//	 * @param
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "startWorkFlow")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "APP端报销-发起申请")
//    @AppAuthority
//	public BaseResp<String> startWorkFlow(@RequestBody MainProjectApprovalFlowHandleReq req,
//			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
//			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request,HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		String expenseFlowId = req.getExpenseFlowId();
//		logger.info("===WxExpenseFlowContoller startWorkFlow[req]====== " + expenseFlowId);
//		User user = this.getCurrWxUser(sjboacert, clientType);
//		if(StringUtils.isBlank(expenseFlowId)){
//			return new  BaseResp<String>(IntfzRs.ERROR, "报销ID不能为空", "");
//		}
//		ExpenseFlow expenseFLow = expenseFlowService.get(expenseFlowId);
//
//		try {
//			intfzExpenseFlowService.startWorkFlow(expenseFLow, user);
//		} catch (ServiceException e) {
//			logger.info("=======WxExpenseFlowContoller startWorkFlow end============="+e.getMessage());
//			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
//		}
//
//		logger.info("=======WxExpenseFlowContoller startWorkFlow end=============");
//		return new  BaseResp<String>(IntfzRs.SUCCESS, "报销发起成功", "");
//	}
	
	/**
	 * 获取待办列表
	 */
	@RequestMapping(value = "todo")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-获取待办列表")
    @AppAuthority
	public BaseResp<PageResponse<List<TaskResponse>>> todoList(
			@RequestBody CommonFlowHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response){
		WebUtils.initPre( request, response);
		User curUser = this.getCurrWxUser(sjboacert, clientType);
		if(req.getPageNo()==0 || req.getPageSize()==0){
			return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<TaskResponse>>(null, 0, 0));
		}
		if(req.getApplyTimeEnd() > 0){
			req.setApplyTimeEnd(req.getApplyTimeEnd() + 86400000);
		}
		if(StringUtils.isBlank(req.getBillType())){
			return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.ERROR, "billType不能为空！", 
					new PageResponse<List<TaskResponse>>(null, 0, 0));
		}

        CommonFlowHandleParam commonFlowHandleParam = change(req,CommonFlowHandleParam.class);
        PagerResponse<TaskResult> todoListPage = expenseFlowNewService.queryTodoList(commonFlowHandleParam, curUser.getId());
        List<TaskResponse> list = new LinkedList<>();
        for(TaskResult item:todoListPage.getList()){
            list.add(change(item,TaskResponse.class));
        }

		return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<TaskResponse>>(list, req.getPageNo(), todoListPage.getCount()));
	}

	/**
	 * 获取已办列表
	 * url:historic
	 * request pageNo:当前页, pageSize:每页记录数
	 * response data:{"list":[], pageNo:"当前页", total:"当前页数"}
	 * @throws Exception 
	 */
	@RequestMapping(value = "historic")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-获取已办列表")
    @AppAuthority
	public BaseResp<PageResponse<List<TaskResponse>>> historicList(
			@RequestBody CommonFlowHandleReq req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		WebUtils.initPre(request, response);
		User curUser = this.getCurrWxUser(sjboacert, clientType);
		if(req.getPageNo() == 0 || req.getPageSize() == 0){
			return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.ERROR, "分页参数不能为空！", 
					new PageResponse<List<TaskResponse>>(null, 0, 0));
		}
		if(StringUtils.isBlank(req.getBillType())){
			return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.ERROR, "billType不能为空！", 
					new PageResponse<List<TaskResponse>>(null, 0, 0));
		}
		if(req.getApplyTimeEnd() > 0){
			req.setApplyTimeEnd(req.getApplyTimeEnd() + 86400000);
		}

        CommonFlowHandleParam commonFlowHandleParam = change(req,CommonFlowHandleParam.class);
        PagerResponse<TaskResult> page = expenseFlowNewService.queryHistoricList(commonFlowHandleParam, curUser.getId());
        List<TaskResponse> list = new LinkedList<>();
        for(TaskResult item:page.getList()){
            list.add(change(item,TaskResponse.class));
        }

		return new BaseResp<PageResponse<List<TaskResponse>>>(IntfzRs.SUCCESS, "ok", 
				new PageResponse<List<TaskResponse>>(list, req.getPageNo(), page.getCount()));
	}
	
//	/**
//	 * 查询费用科目 废弃
//	 * url:subinfo
//	 * request subCode:科目编号(非必填), parSubCode:父级科目编号(非必填), subName:科目名称(非必填)
//	 * response data:{"list":["subCode":"科目编号", "parSubCode":"父级科目编号", "subName":"科目名称","expenseNormal":"费用标准","unitType":"单位类型"]}
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "subInfo")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "APP端报销-查询费用科目")
//    @AppAuthority
//	public BaseResp<PageResponse<List<SubInfoResponse>>> subInfolist(
//			@RequestBody ExpensesSubInfo expensesSubInfo,
//            @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
//            @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request, HttpServletResponse response) throws Exception{
//		WebUtils.initPre(request, response);
//		logger.info("======WxExpenseFlowContoller subInfolist[req]====={}", expensesSubInfo.toString());
//		List<SubInfoResponse> subInfolist = intfzExpenseFlowService.getSubInfoList(expensesSubInfo);
//		logger.info("=======WxExpenseFlowContoller subInfolist end=============");
//		return new BaseResp<PageResponse<List<SubInfoResponse>>>(IntfzRs.SUCCESS, "ok",
//				new PageResponse<List<SubInfoResponse>>(subInfolist, 1, subInfolist.size()));
//	}

//	/**
//	 * 区域信息查询接口 废弃
//	 * @return
//	 */
//	@RequestMapping(value = "areaInfo")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "APP端报销-查询区域信息")
//    @AppAuthority
//	public BaseResp<List<AreaInfoResponse>> areaInfoList(
//            @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
//            @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request, HttpServletResponse response){
//		Area area = new Area();
//		List<Area> areaInfoList = areaInfoService.findList(area);
//		List<AreaInfoResponse> resultList = areaInfoService.setIntfzAreaInfo(areaInfoList);
//		return new BaseResp<List<AreaInfoResponse>>(IntfzRs.SUCCESS, "ok",resultList);
//	}
	
	/**
	 * 获取报销流程详情
	 */
	@RequestMapping(value = "flowdetail")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-查询审批流程详情")
    @AppAuthority
	public BaseResp<ExpenseFlowDetailResponse> flowDetail(
			@RequestBody ExpenseFlowHandleReq expenseFlowHandleReq,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response) {
		WebUtils.initPre(request, response);
		User curUser = this.getCurrWxUser(sjboacert, clientType);
		String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址
		// 获取流程实例对象
		if (StringUtils.isBlank(expenseFlowHandleReq.getExpenseFlowId())){
			logger.info("=======WxExpenseFlowContoller flowDetail end=============参数不能为空");
			return new BaseResp<>(IntfzRs.ERROR, "参数不能为空", new ExpenseFlowDetailResponse());
		}

        ExpenseFlowHandleParam expenseFlowHandleParam = change(expenseFlowHandleReq,ExpenseFlowHandleParam.class);
		expenseFlowHandleParam.setClientType(Constant.CLIENT_TYPE_APP);
        ExpenseFlowResult expenseFlowResult =  expenseFlowNewService.queryExpenseFLowDetail(expenseFlowHandleParam, serverUrl,curUser.getId());
        ExpenseFlowResponse mainInfo = change(expenseFlowResult, ExpenseFlowResponse.class);

        //特殊处理：如果APP端查询的报销关联了接待申请，给前端返回的陪客人员字段内容应为人员的id，而不是人员的loginName
        String[] userLoginNameArray = mainInfo.getEmployees();
        if(userLoginNameArray != null && userLoginNameArray.length > 0){
            String[] userIdArray = new String[userLoginNameArray.length];
            for(int i = 0;i< userLoginNameArray.length;i++){
                String id = UserUtils.getByLoginName(userLoginNameArray[i]).getId();
                userIdArray[i] = id;
            }
            mainInfo.setEmployees(userIdArray);
        }

        ExpenseFlowDetailResponse resp =  change(expenseFlowResult,ExpenseFlowDetailResponse.class);
        resp.setDetail(mainInfo);

		return new BaseResp<>(IntfzRs.SUCCESS, "ok", resp);
	}

	/**
	 * 查询某人过去6个月历史报销记录
	 */
	@RequestMapping(value = "queryExpenseHistory")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-查询某人过去6个月历史报销记录")
	@AppAuthority
	public BaseResp<ExpenseHistoryResponse> queryExpenseHistory(
			@RequestBody ExpenseHistoryQueryRequest req,
			@ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
			@ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
			HttpServletRequest request, HttpServletResponse response){
        ExpenseHistoryResult expenseHistoryResult = expenseFlowNewService.queryExpenseHistory(req.getApplyPerCode());
		ExpenseHistoryResponse expenseHistoryResponse = new ExpenseHistoryResponse();
        List<ExpenseHistoryPerMonth> expenseHistoryPerMonthList = new ArrayList<>();

        for(ExpenseHistoryPerMonthResult perMonthResult : expenseHistoryResult.getExpenseHistoryPerMonthResultList()){

            ExpenseHistoryPerMonth perMonth = new ExpenseHistoryPerMonth();
            List<ExpenseHistoryPerMonthPerSub> perSubList = new ArrayList<>();

            for(ExpenseHistoryPerMonthPerSubResult perMonthPerSubResult : perMonthResult.getExpenseHistoryPerMonthPerSubResultList()){
                perSubList.add(change(perMonthPerSubResult,ExpenseHistoryPerMonthPerSub.class));
            }

            perMonth.setExpenseHistoryPerMonthPerSubList(perSubList);
            expenseHistoryPerMonthList.add(perMonth);
        }
        expenseHistoryResponse.setExpenseHistoryPerMonthList(expenseHistoryPerMonthList);

		return new BaseResp<>(IntfzRs.SUCCESS, "", expenseHistoryResponse);
	}

	/**
	 * 查询历史报销记录按科目检索的明细列表
	 */
	@RequestMapping(value = "queryExpenseHistoryDetailList")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-查询历史报销记录按科目检索的明细列表")
	@AppAuthority
	public BaseResp<List<ExpenseDetailResponse>> queryExpenseHistoryDetailList(@RequestBody ExpenseHistoryQueryDetailListRequest expenseHistoryQueryDetailListRequest,
																			   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
																			   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
																			   HttpServletRequest request, HttpServletResponse response){
        String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址
		ExpenseHistoryDetailQueryListParam param = change(expenseHistoryQueryDetailListRequest,ExpenseHistoryDetailQueryListParam.class);
		param.setServeUrl(serverUrl);

        List<ExpenseDetailResult> resultList = expenseFlowNewService.queryExpenseHistoryDetailList(param);
		List<ExpenseDetailResponse> expenseDetailResponseList = new ArrayList<>();

        for(ExpenseDetailResult item : resultList){
			ExpenseDetailResponse expenseDetailResponse = change(item,ExpenseDetailResponse.class);
			expenseDetailResponseList.add(expenseDetailResponse);
		}

		return new BaseResp<>(IntfzRs.SUCCESS, "", expenseDetailResponseList);
	}

	/**
	 * 流程收回接口
	 */
	@RequestMapping(value = "repealTask")
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "APP端报销-流程收回")
    @AppAuthority
	public BaseResp<String> repealTask(@RequestBody ExpenseFlowRepealRequest expenseRepealRequest,
									   @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
									   @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
									   HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(expenseRepealRequest.getTaskId())){
			return new BaseResp<String>(IntfzRs.ERROR, "任务ID不能为空!", "");
		}
		if(StringUtils.isBlank(expenseRepealRequest.getProcInsId())){
			return new BaseResp<String>(IntfzRs.ERROR, "流程实例ID不能为空!", "");
		}
		//获取当前申请人信息
		User user = super.getCurrWxUser(sjboacert, clientType);

        try {
            ExpenseFlowRepealParam expenseFlowRepealParam = change(expenseRepealRequest,ExpenseFlowRepealParam.class);
            expenseFlowNewService.repealTask(expenseFlowRepealParam,user.getId());
        }catch (ServiceException e){
            return new BaseResp<>(IntfzRs.ERROR, e.getMessage(), "");
        }

		return new BaseResp<String>(IntfzRs.SUCCESS, "流程收回成功!", "");
	}
	
	
//	/**
//	 * 设置项目和员工姓名首字母 废弃
//	 * @return
//	 */
//	@RequestMapping(value = "updateInitials")
//	@ResponseBody
//	@ApiOperation(httpMethod = "POST", value = "设置项目和员工首字母")
//  @AppAuthority
//	public BaseResp<String> updateInitials(
//            @ApiParam(value="clientType为1时为OPENID,为2时为用户手机号") @RequestHeader String sjboacert,
//            @ApiParam(value="客户端类型:1微信公众号2混合开发APP") @RequestHeader String clientType,
//			HttpServletRequest request, HttpServletResponse response){
//		logger.info("=====WxExpenseFlowContoller updateInitials[req]======{}");
//		try {
//			//更新项目名称首字母
//			List<ProjectInfo> projectInfoList = projectInfoService.findList(new ProjectInfo());
//			for(ProjectInfo p:projectInfoList){
//				projectInfoService.save(p);
//			}
//			//更新用户名称首字母
//			List<User> userList =  systemService.findUserNotAccess(new User());
//			for(User u:userList){
//				systemService.saveUserForIntfz(u);
//			}
//		} catch (Exception e) {
//			return new BaseResp<String>(IntfzRs.ERROR, e.getMessage(), "");
//		}
//		return new BaseResp<String>(IntfzRs.SUCCESS, "更新成功!", "");
//	}
	
}
