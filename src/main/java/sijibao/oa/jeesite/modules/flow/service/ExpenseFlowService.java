/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sijibao.oa.activiti.api.response.expense.ExpenseReportResult;
import com.sijibao.oa.activiti.api.response.expense.SubjectAndAmount;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.common.utils.excel.ExportExcel;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.dao.ExpenseAttachmentDao;
import com.sijibao.oa.modules.flow.dao.ExpenseDetailDao;
import com.sijibao.oa.modules.flow.dao.ExpenseFlowDao;
import com.sijibao.oa.modules.flow.dao.RecpParamsDao;
import com.sijibao.oa.modules.flow.entity.ExpenseAttachment;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.flow.entity.ExpenseFlow;
import com.sijibao.oa.modules.flow.entity.RecpParams;
import com.sijibao.oa.modules.flow.entity.report.ExpenseReport;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.ExpensesStandardsDetailDao;
import com.sijibao.oa.modules.oa.dao.ExpensesStandardsMainDao;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsDetail;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsMain;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.oa.service.ExpensesStandardsDetailService;
import com.sijibao.oa.modules.sys.entity.ApproveTime;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 费用报销Service
 * @author Petter
 * @version 2017-12-24
 */
@Service
@Transactional(readOnly = true)
public class ExpenseFlowService extends CrudService<ExpenseFlowDao, ExpenseFlow> {

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ExpenseDetailDao expenseDetailDao;
	@Autowired
	private ExpenseFlowDao expenseFlowDao;
	@Autowired
	private ExpenseAttachmentDao expenseAttachmentDao;
	@Autowired
	private ExpensesStandardsMainDao expensesStandardsMainDao;
	@Autowired
	private ExpensesStandardsDetailDao expensesStandardsDetailDao;
	@Autowired
	private ExpensesStandardsDetailService expensesStandardsDetailService;
	@Autowired
	private RecpParamsDao recpParamsDao;
	public ExpenseFlow getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}

	public ExpenseFlow get(String id) {
		return super.get(id);
	}

	public List<ExpenseFlow> findList(ExpenseFlow expenseFlow) {
		return super.findList(expenseFlow);
	}

	public Page<ExpenseFlow> findPage(Page<ExpenseFlow> page, ExpenseFlow expenseFlow) {
		Page<ExpenseFlow> resultPage = super.findPage(page, expenseFlow);
		for(ExpenseFlow e:resultPage.getList()){
			e.setExpenseStatusValue(DictUtils.getDictLabel(e.getExpenseStatus(), "expense_status", "")); //设置单据状态
		}
		return resultPage;
	}

	/**
	 * 分页查询(包含已删除的)
	 */
	public Page<ExpenseFlow> findAllPageInDelete(Page<ExpenseFlow> page, ExpenseFlow expenseFlow) {
		expenseFlow.setPage(page);
		page.setList(dao.findAllListIncludeDelete(expenseFlow));
//		Page<ExpenseFlow> resultPage = super.findPage(page, expenseFlow);
		for(ExpenseFlow e:page.getList()){
			e.setExpenseStatusValue(DictUtils.getDictLabel(e.getExpenseStatus(), "expense_status", "")); //设置单据状态
			//接待申请/接待报销通过后，隐藏该单据的“项目名称“，仅用固定文案：“接待对象：客户接待”替代
			if(Constant.OA_EXPENSE_TYPE_TWO.equals(e.getApplyType())){
				if(Constant.expense_approve_end.equals(String.valueOf(e.getExpenseStatus()))){
					e.setProjectName("客户接待");
				}
			}
		}
		return page;
	}

	public Page<ExpenseFlow> findAllPage(Page<ExpenseFlow> page, ExpenseFlow expenseFlow) {
		expenseFlow.setPage(page);
		page.setList(dao.findAllPage(expenseFlow));
//		Page<ExpenseFlow> resultPage = super.findPage(page, expenseFlow);
		for(ExpenseFlow e:page.getList()){
			e.setExpenseStatusValue(DictUtils.getDictLabel(e.getExpenseStatus(), "expense_status", "")); //设置单据状态
		}
		return page;
	}

	/**
	 * 不带分页查询(包含已删除的)
	 */
	public List<ExpenseReport> findAllListIncludeDeleteForReport(ExpenseFlow expenseFlow){
		return dao.findAllListIncludeDeleteForReport(expenseFlow);
	}

	@Transactional(readOnly = false)
	public Map<String,Object> save(ExpenseFlow expenseFlow,List<ExpenseDetail> expenseDetailList,List<ExpenseAttachment> expenseAttachmentList) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String flowCode = null;
		String title = "";
		ExpenseFlow temp = new ExpenseFlow();
		if(Constant.expense_yes.equals(expenseFlow.getSaveFlag())){
			expenseFlow.setExpenseStatus(Constant.expense_save); //报销状态保存
		}else{
			expenseFlow.setExpenseStatus(Constant.expense_approve); //报销状态审批中
		}
//		expenseFlow.setProjectName(DictUtils.getDictLabel(expenseFlow.getProjectId(), "oa_project", ""));
		if(StringUtils.isBlank(expenseFlow.getProjectId())){
			expenseFlow.setProjectId(null);
		}
		if(StringUtils.isBlank(expenseFlow.getProjectName())){
			expenseFlow.setProjectName(null);
		}
		//保存陪客人数信息
		if(expenseFlow.getEmployees() != null){
			expenseFlow.setRecpParticNum(expenseFlow.getEmployees().length); //陪客人数
		}
		// 申请发起
		if (StringUtils.isBlank(expenseFlow.getId())){
			flowCode = FlowUtils.genFlowCode();
			expenseFlow.setProcCode(flowCode);
			title = expenseFlow.getApplyPerName()+"_报销_"+DateUtils.formatDate(expenseFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			expenseFlow.setProcName(title);
			expenseFlow.preInsert();
			dao.insert(expenseFlow);
		}else{// 重新编辑申请
			title = expenseFlow.getProcName();
			expenseFlow.preUpdate();
			dao.update(expenseFlow);
			temp = get(expenseFlow.getId());
			flowCode = temp.getProcCode();
		}

		//保存陪客人数信息
		if(expenseFlow.getEmployees() != null){
			//保存陪客人员明细信息
			recpParamsDao.deleteForProcCode(flowCode);
			for(String employee:expenseFlow.getEmployees()){
				RecpParams recpParams = new RecpParams();
				recpParams.setProcCode(flowCode); //流程编号
				recpParams.setParamType(Constant.RECP_PARAM_TYPE_02); //类型
				User user = UserUtils.getByLoginName(employee);
				recpParams.setParamName(user.getName()); //人员名称
				recpParams.setParamValue(employee); //人员信息
				recpParams.preInsert();
				recpParamsDao.insert(recpParams);
			}
		}

		expenseDetailDao.deleteByProcCode(flowCode);
		for(int i = 0;i<expenseDetailList.size();i++){
			//判断是否为空
			if(expenseDetailList.get(i) != null){
				if(expenseDetailList.get(i).getExpenseAmt() != null){
					if("".equals(expenseDetailList.get(i).getSecondSub())){
						expenseDetailList.get(i).setSecondSub(null);
					}
					ExpenseDetail expenseDetail = expenseDetailList.get(i);
					expenseDetail.setProcCode(flowCode);
					expenseDetail.setRowNum(i);
					expenseDetail.preInsert();
					expenseDetailDao.insert(expenseDetail);
					expenseDetail.setExpenseFlow(expenseFlow);
					if(expenseDetail.getExpenseAttachment() != null && expenseDetail.getExpenseAttachment().size() > 0){
						for(ExpenseAttachment expenseAttachment :expenseDetail.getExpenseAttachment()){
								expenseAttachment.setExpenseDetailId(expenseDetail.getId());
								expenseAttachment.setSubCode(expenseDetail.getSecondSub());
								expenseAttachment.setFileType("0");
								if(expenseAttachmentList == null){
									expenseAttachmentList = new ArrayList<ExpenseAttachment>();
								}
								expenseAttachmentList.add(expenseAttachment);
						}
					}
				}
			}
		}
		/****附件信息操作start****/
		//删除所有科目附件信息
		ExpenseAttachment e = new ExpenseAttachment();
		e.setExpenseCode(flowCode);
		expenseAttachmentDao.deleteForExpenseCodeAndExpenseDetailId(e);
		//保存附件信息
		if(expenseAttachmentList != null){
			for(ExpenseAttachment expenseAttachment :expenseAttachmentList){
				if(StringUtils.isNotBlank(expenseAttachment.getExpenseAttachmentUrl())){ //为空则不进行保存
					expenseAttachment.setExpenseCode(flowCode);
					expenseAttachment.preInsert();
					expenseAttachmentDao.insert(expenseAttachment);
				}
			}
		}
		/****附件信息操作end****/

		if(!Constant.expense_yes.equals(expenseFlow.getSaveFlag())){
			/************校验控制标准start************/
			returnMap = this.checkExpenseStandard(expenseFlow);
			if(!(boolean) returnMap.get("checkFlag")){
				//更新单据状态为保存
				expenseFlow.setExpenseStatus(Constant.expense_save); //报销状态被退回
				expenseFlowDao.updateExpenseStatus(expenseFlow);
				return returnMap;
			}
		   /************校验控制标准end************/

			if(StringUtils.isBlank(expenseFlow.getProcInsId())){
				// 启动流程
				String procInsd = actTaskService.startProcess(ActUtils.OA_EXPENSE_FLOW[0], ActUtils.OA_EXPENSE_FLOW[1], expenseFlow.getId(), title);
				//自动跳过第一个环节
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1");
				Act act = new Act();
				act.setProcInsId(procInsd); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				act.setComment("发起申请");
				if(StringUtils.isBlank(title)){
					title = temp.getProcName();
				}
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), title, vars);
				//相同环节自动跳过
				actTaskService.completeAutoIdenPersonel(act.getProcInsId(), title, vars, expenseFlow.getApplyPerCode());
				//审批人员为空自动跳过
				actTaskService.completeAutoEmptyPersonsel(act.getProcInsId(), "", vars);
			}else{
				expenseFlow.getAct().setComment(("yes".equals(expenseFlow.getAct().getFlag())?"[重新申请] ":"[删除] ")+"重新提交申请");
				// 完成流程任务
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "yes".equals(expenseFlow.getAct().getFlag())? "1" : "0");
				actTaskService.complete(expenseFlow.getAct().getTaskId(), expenseFlow.getAct().getProcInsId(), expenseFlow.getAct().getComment(), temp.getProcName(), vars);
			}
		}
		return returnMap;
	}

	/**
	 * 审核审批保存
	 */
	@Transactional(readOnly = false)
	public void auditSave(ExpenseFlow expenseFlow) {
		//获取当前登录人
		User user = UserUtils.getUser();
		// 设置意见
		expenseFlow.getAct().setComment(("yes".equals(expenseFlow.getAct().getFlag())?"[同意] ":"[驳回] ")+expenseFlow.getAct().getComment());

		String flag = expenseFlow.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(expenseFlow.getAct().getFlag())? "1" : "0");
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(expenseFlow.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComent(expenseFlow.getAct().getProcInsId(),act.getTaskId(),expenseFlow.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
		}else{
			actTaskService.complete(expenseFlow.getAct().getTaskId(), expenseFlow.getAct().getProcInsId(), expenseFlow.getAct().getComment(), vars);
		}
		//判断流程是否退回并更新报销状态
		if("no".equals(flag)){
			expenseFlow.setExpenseStatus(Constant.expense_back); //报销状态被退回
			expenseFlowDao.updateExpenseStatus(expenseFlow);
		}

		//判断流程是否结束并更新报销状态
		Act e = new Act();
		e.setProcInsId(expenseFlow.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		if(e == null){
			//流程已结束
			expenseFlow.setFlowFinishTime(new Date());
			expenseFlow.setExpenseStatus(Constant.expense_approve_end); //报销状态审批结束
			expenseFlowDao.updateExpenseStatusAndFlowFinshTime(expenseFlow);
		}
	}

	/**
	 * 任务撤销
	 * @param expenseFlow
	 */
	@Transactional(readOnly = false)
	public void repealTask(ExpenseFlow expenseFlow,User user){

		/********判断当前节点的下一个节点任务是否已经完成*********/

		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");

		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(expenseFlow.getAct().getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息

		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(expenseFlow.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(expenseFlow.getAct().getTaskId());
			targetAct.setProcInsId(expenseFlow.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}

		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComent(expenseFlow.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
	}


	/**
	 * 删除任务
	 */
	@Transactional(readOnly = false)
	public void delete(ExpenseFlow expenseFlow) {
		super.delete(expenseFlow);
	}

	/**
	 * 删除任务
	 * @param expenseFlow
	 */
	@Transactional(readOnly = false)
	public void repealApply(ExpenseFlow expenseFlow){
		if(Constant.expense_save.equals(expenseFlow.getExpenseStatus())){ //保存的单据进行物理删除
			expenseDetailDao.deleteByProcCode(expenseFlow.getProcCode()); //删除报销明细信息
			expenseFlowDao.deleteExpenseFlowInfo(expenseFlow); //删除报销主表信息
		}else{
			if(StringUtils.isNotBlank(expenseFlow.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(expenseFlow.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(expenseFlow.getProcInsId()); //删除历史任务
			}
			//删除单据
			expenseFlowDao.delete(expenseFlow);
			//修改单据状态为已删除
			expenseFlow.setExpenseStatus(Constant.expense_delete); //报销状态被删除
			expenseFlowDao.updateExpenseStatus(expenseFlow);
		}
	}

	/**
	 * 校验费用明细标准
	 * @param expenseFlow
	 * @return
	 */
	public Map<String,Object> checkExpenseStandard(ExpenseFlow expenseFlow){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("checkFlag", true);
		resultMap.put("message", "");

		List<ExpenseDetail> detailList =
				expenseDetailDao.queryExpenseDetailGroupForExpenseFlow(expenseFlow.getProcCode());

		if(detailList != null && detailList.size() > 0){
			for(ExpenseDetail expenseDetail:detailList){
				//校验当前报销科目日期与之前是否重叠
				ExpensesStandardsMain smain = new ExpensesStandardsMain();

				//根据部门和费用科目查询对应的控制标准信息
				smain.setOfficeId(expenseDetail.getExpenseFlow().getOffice().getId());
				ExpensesSubInfo expensesSubInfo = new ExpensesSubInfo();
				expensesSubInfo.setId(expenseDetail.getFirstSub());
				smain.setProject(expensesSubInfo);
				smain = expensesStandardsMainDao.queryStandardsMainForOrgIdAndProjectCode(smain);
				if(smain == null){
					ExpensesStandardsMain smainSecondSub = new ExpensesStandardsMain();
					expensesSubInfo.setId(expenseDetail.getSecondSub());
					smainSecondSub.setProject(expensesSubInfo);
					smainSecondSub.setOfficeId(expenseDetail.getExpenseFlow().getOffice().getId());
					smain = expensesStandardsMainDao.queryStandardsMainForOrgIdAndProjectCode(smainSecondSub);
				}

				if(smain != null && StringUtils.isNotBlank(smain.getStandsType())){
					//查询控制标准明细信息
					ExpensesStandardsDetail sDetail = new ExpensesStandardsDetail();
					sDetail.setStandsMainId(smain.getId());
					List<ExpensesStandardsDetail> sDetailList = expensesStandardsDetailDao.findList(sDetail);
					if(sDetailList != null && sDetailList.size() > 0){
						//获取控制类别，分别判断不同的控制标准是否超标
						resultMap = expensesStandardsDetailService.checkStandardsDetail(sDetailList, smain,expenseDetail);
						if(!(boolean) (resultMap.get("checkFlag"))){
							return resultMap;
						}
					}
				}
			}
		}else{
			resultMap.put("checkFlag", false);
			resultMap.put("message", "提交失败,当前报销单据没有明细信息!");
			return resultMap;
		}
		return resultMap;
	}

	public String getPostId(ExpenseFlow expenseFlow) {
		return expenseFlowDao.getPostId(expenseFlow);
	}

	/**
	 * 查询提前打款列表
	 * @param expenseFlow
	 * @return
	 */
	public List<ExpenseFlow> queryBringRemitList(ExpenseFlow expenseFlow){
		return expenseFlowDao.queryBringRemitList(expenseFlow);
	}


	/**
	 * 查询副负责人(可为多个)
	 * @param employee
	 * @return
	 */
	public List<String> findManagerForEmployee(String employee){
		String deputyPerson = expenseFlowDao.findDeputyPerson(employee);
		if(!"".equals(deputyPerson)){
			List<String> list = new ArrayList<String>();
			String[] parms = deputyPerson.split(",");
			for(int i=0; i<parms.length;i++){
				if(null !=parms[i] && !"".equals(parms[i])){
					list.add(parms[i]);
				}
			}
			List<String> result = expenseFlowDao.findDeputyPersonForEmployee(list);
			return result;
		}
		return null;
	}

	/**
	 * 查询提前打款列表--分页查询
	 * @param expenseFlow
	 * @return
	 */
	public Page<ExpenseFlow> queryBringRemitListForPage(Page<ExpenseFlow> page,ExpenseFlow expenseFlow){
		expenseFlow.setPage(page);
		page.setList(expenseFlowDao.queryBringRemitList(expenseFlow));
		return page;
	}

	/**
	 * 更新提前打款信息
	 * @param expenseFlow
	 */
	@Transactional(readOnly = false)
	public void updateBringInfo(ExpenseFlow expenseFlow){
		expenseFlowDao.updateBringInfo(expenseFlow);
	}

	/**
	 * 导出个人报销报表
	 */
	public String exportMyExpenseList(List<ExpenseReportResult> list, String fileName){
		String url = "";
		// 创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = workBook.createSheet();
		// 设置表名
		workBook.setSheetName(0, "个人报销情况");
		// 第0行 列头
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		HSSFCellStyle style = ExportExcel.getStyle(workBook);
		ExportExcel.myCreateCell(0, "流程编号", row, cell, style);
		ExportExcel.myCreateCell(1, "报销日期", row, cell, style);
		ExportExcel.myCreateCell(2, "报销类型", row, cell, style);
		ExportExcel.myCreateCell(3, "项目名称", row, cell, style);
		ExportExcel.myCreateCell(4, "项目负责人", row, cell, style);
		ExportExcel.myCreateCell(5, "科目及金额", row, cell, style);
		ExportExcel.myCreateCell(6, "总金额", row, cell, style);

		//设置数据
		DateFormat format = new SimpleDateFormat("yyyy-M-d");
		int rowNum = 1;
		for (ExpenseReportResult q : list) {

			List<SubjectAndAmount> subjectAndAmountList = q.getSubjectAndAmountList();//某个报销流程的科目及金额
			StringBuilder sb = new StringBuilder();
			for(SubjectAndAmount sa : subjectAndAmountList){
				sb.append(sa.getFirstSub());
				if(StringUtils.isNotBlank(sa.getSecondSub())){
					sb.append("-").append(sa.getSecondSub());
				}
				BigDecimal expenseAmt = sa.getExpenseAmt();
				expenseAmt = expenseAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
				sb.append("-").append(expenseAmt.toString()).append(",");
			}
			String subjectAndAmountStr = sb.toString();//科目及金额字符串
			if(subjectAndAmountStr.endsWith(",")){
				subjectAndAmountStr = subjectAndAmountStr.substring(0, subjectAndAmountStr.length() - 1);
			}

			row = sheet.createRow(rowNum);
			ExportExcel.myCreateCell(0, q.getProcCode(), row, cell, style);
			ExportExcel.myCreateCell(1, format.format(q.getApplyTime()), row, cell, style);
			ExportExcel.myCreateCell(2, q.getApplyType(), row, cell, style);
			ExportExcel.myCreateCell(3, q.getProjectName(), row, cell, style);
			ExportExcel.myCreateCell(4, q.getProjectPersonel(), row, cell, style);
			ExportExcel.myCreateCell(5, subjectAndAmountStr, row, cell, style);
			String expenseTotalStr = "";
			if(q.getExpenseTotal() != null){
				expenseTotalStr = q.getExpenseTotal().setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString();
			}
			ExportExcel.myCreateCell(6, expenseTotalStr, row, cell, style);
			rowNum++;
		}

		try {
			//保存到服务器
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workBook.write(os);
			url = UploadUtils.uploadDoc(os, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}


	public List<ApproveTime> findApproveTimeData(ApproveTime approveTime){
		return dao.findApproveTimeData(approveTime);
	}
}