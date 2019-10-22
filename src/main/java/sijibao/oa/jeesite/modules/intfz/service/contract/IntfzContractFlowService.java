package sijibao.oa.jeesite.modules.intfz.service.contract;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.IdNumberUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.dao.ContractCompanyInfoDao;
import com.sijibao.oa.modules.flow.dao.ContractFlowDao;
import com.sijibao.oa.modules.flow.entity.ContractAttachment;
import com.sijibao.oa.modules.flow.entity.ContractCompanyInfo;
import com.sijibao.oa.modules.flow.entity.ContractFlow;
import com.sijibao.oa.modules.flow.service.ContractAttachmentService;
import com.sijibao.oa.modules.flow.service.ContractCompanyInfoService;
import com.sijibao.oa.modules.flow.service.ContractFlowService;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.request.contract.ContractFlowListReq;
import com.sijibao.oa.modules.intfz.request.contract.ContractFlowRequest;
import com.sijibao.oa.modules.intfz.request.contract.FirstCompanyInfoHandleRequest;
import com.sijibao.oa.modules.intfz.request.contract.MainContractAttachmentRequest;
import com.sijibao.oa.modules.intfz.response.contract.ContractAttachmentResponse;
import com.sijibao.oa.modules.intfz.response.contract.ContractCompanyInfoResponse;
import com.sijibao.oa.modules.intfz.response.contract.ContractFlowResponse;
import com.sijibao.oa.modules.intfz.response.contract.FirstCompanyInfoResponse;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.intfz.validator.IntfzValidateUtils;
import com.sijibao.oa.modules.oa.entity.FirstCompanyInfo;
import com.sijibao.oa.modules.oa.service.CustInfoService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 合同管理流程服务层
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzContractFlowService extends BaseService{
	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ContractFlowDao contractFlowDao;
	@Autowired
	private ContractFlowService contractFlowService;
	@Autowired
	private ContractCompanyInfoService contractCompanyInfoService ;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private ContractAttachmentService contractAttachmentService;
	@Autowired
	private ContractCompanyInfoDao contractCompanyInfoDao ;

	/**
	 * 合同管理提交申请服务
	 * @param contractFlowRequest
	 */
	@Transactional(readOnly = false)
	public void contractApplyService(ContractFlowRequest contractFlowRequest, User user,String clientType){
		try {
			/**********合同管理流程业务处理start*******/
			IntfzValidateUtils.valid(contractFlowRequest);
			ContractFlow contractFlow = new ContractFlow();
			contractFlow.setApplyPerCode(user.getLoginName()); //申请人编号
			contractFlow.setApplyPerName(user.getName()); //申请人名称
			contractFlow.setApplyTime(new Date());//申请时间
			contractFlow.setOffice(user.getOffice()); //所属部门
			contractFlow.setRemarks(contractFlowRequest.getRemarks()); //备注
			contractFlow.setContractFlowStatus(Constant.expense_save); //合同管理状态保存
			contractFlow.setContractName(contractFlowRequest.getContractName()); //合同名称
			contractFlow.setContractNameCode(contractFlowRequest.getContractNameCode()); //合同名称编码
			contractFlow.setFirstPartyName(contractFlowRequest.getFirstPartyName());		// 甲方名称
			contractFlow.setFirstCreditCode(contractFlowRequest.getFirstCreditCode());		// 甲方统一社会信用代码
			contractFlow.setFirstAddress(contractFlowRequest.getFirstAddress());		// 甲方住所
			contractFlow.setFirstLegalRepresentative(contractFlowRequest.getFirstLegalRepresentative());		// 甲方法定代表人
			contractFlow.setFirstLinkman(contractFlowRequest.getFirstLinkman());		// 甲方联系人
			contractFlow.setFirstLinkmanPhone(contractFlowRequest.getFirstLinkmanPhone());		// 甲方联系人电话
			contractFlow.setFirstLinkmanMail(contractFlowRequest.getFirstLinkmanMail());		// 甲方联系人邮箱
			
			contractFlow.setSecondPartyName(contractFlowRequest.getSecondPartyName());		// 乙方名称
			contractFlow.setSecondCreditCode(contractFlowRequest.getSecondCreditCode());		// 乙方统一社会信用代码
			contractFlow.setSecondAddress(contractFlowRequest.getSecondAddress());		// 乙方住所
			contractFlow.setSecondLegalRepresentative(contractFlowRequest.getSecondLegalRepresentative());		// 乙方法定代表人
			contractFlow.setSecondLinkman(contractFlowRequest.getSecondLinkman());		// 乙方联系人
			contractFlow.setSecondLinkmanPhone(contractFlowRequest.getSecondLinkmanPhone());		// 乙方联系人电话
			contractFlow.setSecondLinkmanMail(contractFlowRequest.getSecondLinkmanMail());		// 乙方联系人邮箱
			
			contractFlow.setDispatchProportion(contractFlowRequest.getDispatchProportion()); // 调度费比例
			contractFlow.setPenaltyProportion(contractFlowRequest.getPenaltyProportion()); //违约金比例
			contractFlow.setValidityDate(contractFlowRequest.getValidityDate()); //有效期（年）
			contractFlow.setContractDate(DateUtils.parseDate(contractFlowRequest.getContractDate())); //签约日期
			contractFlow.setExpressBill(contractFlowRequest.getExpressBill()); //快递单号
			contractFlow.setExpressCompany(contractFlowRequest.getExpressCompany()); //快递公司
			contractFlow.setMarketLeaderId(user.getId()); //市场负责人
			contractFlow.setCustId(contractFlowRequest.getCustId());
			
			String flowCode = "";
			// 申请发起
			if (StringUtils.isBlank(contractFlowRequest.getProcInsId())){ //判断流程定义ID
				flowCode = FlowUtils.genFlowCode();
				contractFlow.setProcCode(flowCode);
				String title = user.getName()+"_合同_"+DateUtils.formatDate(contractFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
				contractFlow.setProcName(title);
				contractFlow.setContractFlowStatus(Constant.expense_approve); //合同管理状态审批中
				contractFlow.preInsertForWeChart(user);
				contractFlowDao.insert(contractFlow);
				// 启动流程
				String procInsd = actTaskService.startProcessForWechat(ActUtils.OA_CONSTRACT_FLOW[0], ActUtils.OA_CONSTRACT_FLOW[1], contractFlow.getId(), title,user);
				
				//自动跳过第一个环节
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1");
				Act act = new Act();
				act.setProcInsId(procInsd); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				act.setComment("发起申请");
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(),title, vars);
				
				//审批人员为空自动跳过
				actTaskService.completeAutoEmptyPersonsel(act.getProcInsId(), "", vars);
			}else{// 重新编辑申请直接跳转流程
				/*************查询当前任务IDstart************/
				Act act = new Act();
				act.setAssignee(user.getLoginName()); //当前登录用户
				act.setProcInsId(contractFlowRequest.getProcInsId()); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				if(act == null || StringUtils.isBlank(act.getTaskId())){
					throw new ServiceException("找不到当前流程任务信息，禁止提交！");
				}else{
					contractFlow.getAct().setTaskId(act.getTaskId()); 
				}
				/*************查询当前任务IDend************/
				contractFlow.setId(contractFlowRequest.getId());
				contractFlow.getAct().setProcInsId(contractFlowRequest.getProcInsId()); //流程实例ID
				contractFlow.getAct().setComment("重新申请");
				
				// 完成当前流程任务
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1"); //设置变量，1：true,0:false
				ContractFlow temp =contractFlowDao.get(contractFlow.getId());
				contractFlow.setProcCode(temp.getProcCode());
				contractFlow.setProcInsId(temp.getProcInsId());
				contractFlow.setProcName(temp.getProcName());
				contractFlow.setContractFlowStatus(Constant.expense_approve); //合同管理状态审批中
				contractFlow.preUpdateForWechart(user);
				contractFlowDao.update(contractFlow);
				flowCode = temp.getProcCode(); //获取流程编码
				actTaskService.complete(contractFlow.getAct().getTaskId(), contractFlow.getAct().getProcInsId(), contractFlow.getAct().getComment(), temp.getProcName(), vars);
				//审批人员为空自动跳过
				actTaskService.completeAutoEmptyPersonsel(contractFlow.getAct().getProcInsId(), "", vars);
			}
			/*****合同管理流程业务处理end*******/
			
			/*********合同附件信息处理start*********/
			contractAttachmentService.deleteContractCode(flowCode); //插入前删除之前的附件信息
			List<MainContractAttachmentRequest> contractAttachmentList = contractFlowRequest.getContractAttachmentList();
			if(contractAttachmentList != null && contractAttachmentList.size() > 0){
				for(MainContractAttachmentRequest caReq:contractAttachmentList){
					ContractAttachment contractAttachment = new ContractAttachment();
					contractAttachment.setContractProcCode(flowCode);
					contractAttachment.setFileType(caReq.getFileType());
					contractAttachment.setFileName(caReq.getName());
					contractAttachment.setContractAttachmentUrl(caReq.getContractAttachmentUrl());
					contractAttachmentService.save(contractAttachment);
				}
			}
			/*********合同附件信息处理end*********/
			
		} catch (ServerException e) {
			throw new ServiceException(e.getMessage());
		} 
	}
	
	/**
	 *合同管理流程保存服务
	 * @param travelFlowRequest
	 */
	@Transactional(readOnly = false)
	public void saveContractFlowService(ContractFlowRequest contractFlowRequest,User user,String clientType){
		/**********合同管理流程保存业务处理start*******/
		ContractFlow contractFlow = new ContractFlow();
//		String contractCode = "";
		contractFlow.setApplyPerCode(user.getLoginName()); //申请人编号
		contractFlow.setApplyPerName(user.getName()); //申请人名称
		contractFlow.setApplyTime(new Date());//申请时间
		contractFlow.setOffice(user.getOffice()); //所属部门
		contractFlow.setRemarks(contractFlowRequest.getRemarks()); //备注
		contractFlow.setContractFlowStatus(Constant.expense_save); //合同管理状态保存
//		contractFlow.setContractCode(contractCode); //合同编码
		contractFlow.setContractName(contractFlowRequest.getContractName()); //合同名称
		
		contractFlow.setFirstPartyName(contractFlowRequest.getFirstPartyName());		// 甲方名称
		contractFlow.setFirstCreditCode(contractFlowRequest.getFirstCreditCode());		// 甲方统一社会信用代码
		contractFlow.setFirstAddress(contractFlowRequest.getFirstAddress());		// 甲方住所
		contractFlow.setFirstLegalRepresentative(contractFlowRequest.getFirstLegalRepresentative());		// 甲方法定代表人
		contractFlow.setFirstLinkman(contractFlowRequest.getFirstLinkman());		// 甲方联系人
		contractFlow.setFirstLinkmanPhone(contractFlowRequest.getFirstLinkmanPhone());		// 甲方联系人电话
		contractFlow.setFirstLinkmanMail(contractFlowRequest.getFirstLinkmanMail());		// 甲方联系人邮箱
		
		contractFlow.setSecondPartyName(contractFlowRequest.getSecondPartyName());		// 乙方名称
		contractFlow.setSecondCreditCode(contractFlowRequest.getSecondCreditCode());		// 乙方统一社会信用代码
		contractFlow.setSecondAddress(contractFlowRequest.getSecondAddress());		// 乙方住所
		contractFlow.setSecondLegalRepresentative(contractFlowRequest.getSecondLegalRepresentative());		// 乙方法定代表人
		contractFlow.setSecondLinkman(contractFlowRequest.getSecondLinkman());		// 乙方联系人
		contractFlow.setSecondLinkmanPhone(contractFlowRequest.getSecondLinkmanPhone());		// 乙方联系人电话
		contractFlow.setSecondLinkmanMail(contractFlowRequest.getSecondLinkmanMail());		// 乙方联系人邮箱
		
		contractFlow.setDispatchProportion(contractFlowRequest.getDispatchProportion()); // 调度费比例
		contractFlow.setPenaltyProportion(contractFlowRequest.getPenaltyProportion()); //违约金比例
		contractFlow.setValidityDate(contractFlowRequest.getValidityDate()); //有效期（年）
		contractFlow.setContractDate(DateUtils.parseDate(contractFlowRequest.getContractDate())); //签约日期
		contractFlow.setExpressBill(contractFlowRequest.getExpressBill()); //快递单号
		contractFlow.setExpressCompany(contractFlowRequest.getExpressCompany()); //快递公司
		contractFlow.setMarketLeaderId(user.getId()); //市场负责人
		String flowCode = "";
		// 第一次保存
		if (StringUtils.isBlank(contractFlowRequest.getId())){ //判断单据ID
			flowCode = FlowUtils.genFlowCode();
			contractFlow.setProcCode(flowCode);
			String title = user.getName()+"_合同_"+DateUtils.formatDate(contractFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			contractFlow.setProcName(title);
			contractFlow.preInsertForWeChart(user);
			contractFlowDao.insert(contractFlow);
		}else{// 重新保存
			contractFlow.setId(contractFlowRequest.getId());
			
			ContractFlow temp = contractFlowDao.get(contractFlow.getId());			
			flowCode = temp.getProcCode(); //获取流程编码
			contractFlow.setProcCode(flowCode);
			contractFlow.setProcName(temp.getProcName());
			contractFlow.setContractFlowStatus(temp.getContractFlowStatus());
			contractFlow.setProcInsId(temp.getProcInsId());
			contractFlow.preUpdateForWechart(user);
			contractFlowDao.update(contractFlow);
		}
		/*****合同管理流程保存业务处理end*******/
	}
	
	/**
	 * 合同管理流程撤销
	 * @param contractFlow
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void contractRepealTask(ContractFlow contractFlow,User user){
		
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		
		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(contractFlow.getAct().getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
		
		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(contractFlow.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(contractFlow.getAct().getTaskId());
			targetAct.setProcInsId(contractFlow.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}
		
		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComent(contractFlow.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
	}
	
	/**
	 * 合同管理流程审批 同意/驳回
	 * @param testAudit
	 * @throws ServerException 
	 */
	@Transactional(readOnly = false)
	public void contractFlowCompleteTask(ContractFlow contractFlow,User user,List<MainContractAttachmentRequest> contractAttachmentList) throws ServerException {
		// 设置审批意见
		contractFlow.getAct().setComment(("yes".equals(contractFlow.getAct().getFlag())?"[同意] ":"[驳回] ")+contractFlow.getAct().getComment());
		String flag = contractFlow.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(contractFlow.getAct().getFlag())? "1" : "0");
		
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(contractFlow.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComent(contractFlow.getAct().getProcInsId(),act.getTaskId(),contractFlow.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
			//判断流程是否退回并更新合同管理申请状态
			contractFlow.setContractFlowStatus(Constant.expense_back); //合同管理申请状态被退回
			contractFlowDao.updateContractFlowStatus(contractFlow);
		}else{
			actTaskService.complete(contractFlow.getAct().getTaskId(), contractFlow.getAct().getProcInsId(), contractFlow.getAct().getComment(), vars);
			//审批人员为空自动跳过
			actTaskService.completeAutoEmptyPersonsel(contractFlow.getAct().getProcInsId(), "", vars);
			
			//判断当前环节是否是法务二审，如果是则生成合同编号
			if(Constant.CONTRACT_FLOW_NODE_SECONDLEGAL.equals(contractFlow.getAct().getTaskDefKey())){
				String contractCode = this.createContractCode(contractFlow.getContractNameCode());  
				if(StringUtils.isBlank(contractCode)){
					throw new ServerException("生成合同号失败，请联系管理员检查系统配置!");
				}
				contractFlow.setContractCode(contractCode); //合同编号
				contractFlowDao.updateContractCode(contractFlow); //更新合同号
			}
			
			//财务用印环节，进行扫描件上传
			if(Constant.CONTRACT_FLOW_NODE_PRINTING.equals(contractFlow.getAct().getTaskDefKey()) &&
					contractAttachmentList != null && contractAttachmentList.size() > 0){
				for(MainContractAttachmentRequest cr:contractAttachmentList){
					ContractAttachment contractAttachment = new ContractAttachment();
					contractAttachment.setContractProcCode(contractFlow.getProcCode()); //流程编号
					contractAttachment.setContractAttachmentUrl(cr.getContractAttachmentUrl()); //附件URL
					contractAttachment.setFileName(cr.getName()); //附件名称
					contractAttachment.setFileType(cr.getFileType()); //附件类型
					contractAttachmentService.save(contractAttachment);
				}
			}
		}
		
		//判断流程是否结束并更新合同管理申请状态
		Act e = new Act();
		e.setProcInsId(contractFlow.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		if(e == null){
			//流程已结束
			contractFlow.setFlowFinishTime(new Date());
			contractFlow.setContractFlowStatus(Constant.expense_approve_end); //合同管理申请状态审批结束
			contractFlowDao.updateContractStatusAndFlowFinshTime(contractFlow);
		}
	}
	
	/**
	 * 删除合同管理流程信息
	 * @param contractFlow
	 */
	@Transactional(readOnly = false)
	public void deleteContractFlow(ContractFlow contractFlow){
		if(Constant.expense_save.equals(contractFlow.getContractFlowStatus())){ //保存的单据进行物理删除
			contractFlowDao.deleteContractFlowInfo(contractFlow); //删除合同管理主表信息
		}else{
			if(StringUtils.isNotBlank(contractFlow.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(contractFlow.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(contractFlow.getProcInsId()); //删除历史任务
			}
			//删除单据
			contractFlowDao.delete(contractFlow);
			//修改单据状态为已删除
			contractFlow.setContractFlowStatus(Constant.expense_delete); //合同管理状态状态被删除
			contractFlowDao.updateContractFlowStatus(contractFlow);
		}
	}
	
	/**
	 * 合同详情数据
	 * @param travelFlowResponse
	 * @param travelFlow
	 * @param curUser
	 * @param act
	 * @return
	 */
	public ContractFlowResponse contractFlowDetail(ContractFlowResponse contractFlowResponse,ContractFlow contractFlow,User curUser){
		contractFlowResponse = new ContractFlowResponse(contractFlow);
		contractFlowResponse.setIsUploadFile(0); //是否可上传扫描件
		contractFlowResponse.setIsBack(1); //是否可驳回
		contractFlowResponse.setIsCancel(1); //是否可撤回
		//查询当前流程的审核环节
		Act act = new Act();
		act.setProcInsId(contractFlowResponse.getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act);
		if(act != null && Constant.CONTRACT_FLOW_NODE_PRINTING.equals(act.getTaskDefKey())){ //如果当前环节是财务用印环节
			contractFlowResponse.setIsUploadFile(1); //可以上传扫描件
			contractFlowResponse.setIsBack(0); //不可驳回
			contractFlowResponse.setIsCancel(0); //不可撤销
		}
		
		if(Constant.expense_approve_end.equals(contractFlowResponse.getContractFlowStatus())){ //如果流程已经审批结束，则不可驳回和撤销
			contractFlowResponse.setIsBack(0); //不可驳回
			contractFlowResponse.setIsCancel(0); //不可撤销
		}
		
		//当前环节在修改节点时，可以编辑合同信息
		if(act != null && Constant.DEFAULT_NOE_MODIFY.equals(act.getTaskDefKey()) && StringUtils.equals(curUser.getLoginName(), act.getAssignee())){
			contractFlowResponse.setModify(Constant.DEFAULT_NOE_MODIFY);
		}
		
		//查询科目附件信息
		ContractAttachment contractAttachment = new ContractAttachment();
		contractAttachment.setContractProcCode(contractFlowResponse.getProcCode()); //流程编号
		List<ContractAttachment> contractAttachmentList = contractAttachmentService.findList(contractAttachment);
		if(contractAttachmentList != null && contractAttachmentList.size() > 0){
			List<ContractAttachmentResponse> caResponseList = new ArrayList<ContractAttachmentResponse>();
			for(ContractAttachment c:contractAttachmentList){
				ContractAttachmentResponse ar = change(c, ContractAttachmentResponse.class);
				ar.setUrlPrefix(UploadUtils.getServerUrl());
				ar.setName(c.getFileName());
				caResponseList.add(ar);
			}
			contractFlowResponse.setContractAttachmentList(caResponseList);
		}
		return contractFlowResponse;
	}
	
	/**
	 * 查询我的合同管理申请列表
	 * @param req
	 * @param user
	 * @return
	 */
	public Page<ContractFlowResponse> queryMyContractFlowList(ContractFlowListReq req,User user,Page<ContractFlowResponse> resultPage){
		ContractFlow query = new ContractFlow();
//		query.setCreateBy(user);
//		query.setBeginApplyTime(DateUtils.parseDate(req.getApplyTimeStart()));
//		query.setEndApplyTime(DateUtils.parseDate(req.getApplyTimeEnd()));
		query.setUser(user);
		if(StringUtils.isNotBlank(req.getContractFlowStatus())){
			query.setContractFlowStatus(String.valueOf(req.getContractFlowStatus())); //合同状态
		}
		query.setApplyPerName(req.getApplyName()); //申请人名称
		query.setProcCode(req.getProcCode()); //流程编号
		query.setPrintingTimeStart(DateUtils.parseDate(req.getPrintingTimeStart())); //用印开始日期
		query.setPrintingTimeEnd(DateUtils.parseDate(req.getPrintingTimeEnd())); //用印结束日期
		query.setContractNameCode(req.getContractNameCode()); //合同名称编码
		query.setCompanyName(req.getCompanyName());  //公司名称
		query.setContractCode(req.getContractCode());  //合同编号
		
		Page<ContractFlow> page = contractFlowService.findPage(
				new Page<ContractFlow>(req.getPageNo(), req.getPageSize()), query); //查询合同管理信息
		resultPage.setPageNo(page.getPageNo());
		resultPage.setPageSize(page.getPageSize());
		resultPage.setCount(page.getCount());
		if(page.getList() != null && page.getList().size() > 0){
			for(ContractFlow c:page.getList()){
				ContractFlowResponse contractFlowResponse = new ContractFlowResponse(c);
				resultPage.getList().add(contractFlowResponse);
			}
		}
		return resultPage;
	}
	/**
	 * 获取乙方信息
	 * @return
	 */
	public List<ContractCompanyInfoResponse> contractCompanyInfoList() {
		List<ContractCompanyInfo> list = contractCompanyInfoService.findList(new ContractCompanyInfo());
		List<ContractCompanyInfoResponse> nList = Lists.newArrayList();
		for (ContractCompanyInfo co : list) {
			ContractCompanyInfoResponse re = new ContractCompanyInfoResponse();
			re.setContractName(co.getContractName());
			re.setContractCompanyCode(co.getContractCompanyCode());
			if(StringUtils.isNotBlank(co.getSecondPartyName())){
				re.setSecondPartyNameKey(co.getSecondPartyName());
				re.setSecondPartyName(DictUtils.getDictLabel(co.getSecondPartyName(), "second_party_name", ""));
			}
			re.setSecondCreditCode(co.getSecondCreditCode());
			re.setSecondAddress(co.getSecondAddress());
			re.setSecondLegalRepresentative(co.getSecondLegalRepresentative());
			re.setSecondLinkman(co.getSecondLinkman());
			re.setSecondLinkmanPhone(co.getSecondLinkmanPhone());
			re.setSecondLinkmanMail(co.getSecondLinkmanMail());
			re.setValidityTime(co.getValidityTime());
			if(StringUtils.isNotBlank(co.getContractTypeKey())){
				re.setContractTypeKey(co.getContractTypeKey());
				re.setContractType(DictUtils.getDictLabel(co.getContractTypeKey(), "contract_type", ""));
			}
			
			re.setPenaltyProportion(co.getPenaltyProportion());
			nList.add(re);
		}
		return nList;
	}
	
	/**
	 * 生成合同编号
	 * @param companyNameCode 公司名称编码
	 * @return
	 */
	public String createContractCode(String companyNameCode){
		//根据公司名称编码查询乙方公司信息
		ContractCompanyInfo contractCompanyInfo = contractCompanyInfoDao.getForContractCompanyCode(companyNameCode);
		if(contractCompanyInfo != null){
			StringBuffer contractCode = new StringBuffer(); //合同号
			contractCode.append(StringUtils.isBlank(contractCompanyInfo.getSecondPartyName())?"WH":contractCompanyInfo.getSecondPartyName());  //公司简称
			contractCode.append("-");
			contractCode.append(StringUtils.isBlank(contractCompanyInfo.getContractTypeKey())?"YS":contractCompanyInfo.getContractTypeKey());  //合同类型
			contractCode.append("-"+DateUtils.format(new Date(), DateUtils.YYYYMMDD));  //日期-年月日
			contractCode.append("-"+new IdNumberUtils().getContractId());  //五位流水
			return contractCode.toString();
		}else{
			return "";
		}
	}
	
	/**
	 * 查询甲方公司信息
	 * @param req 
	 * @return
	 */
	public Page<FirstCompanyInfoResponse> firstCompanyInfoList(FirstCompanyInfoHandleRequest req, User user) {
		Page<FirstCompanyInfo> p = new Page<FirstCompanyInfo>();
		p.setPageNo(req.getPageNo());
		p.setPageSize(req.getPageSize());
		FirstCompanyInfo firstCompanyInfo = new FirstCompanyInfo();
		firstCompanyInfo.setCustName(req.getCustName());
		firstCompanyInfo.setMarketLeaderName(req.getMarketLeaderName());
		firstCompanyInfo.setUser(user);
		Page<FirstCompanyInfo> first = custInfoService.findFirstCompanyInfoList(p,firstCompanyInfo);
		Page<FirstCompanyInfoResponse> page = new Page<FirstCompanyInfoResponse>();
		List<FirstCompanyInfoResponse> nList = Lists.newArrayList();
		for (FirstCompanyInfo f : first.getList()) {
			FirstCompanyInfoResponse fc = new FirstCompanyInfoResponse();
			fc.setCustId(f.getCustId());
			fc.setCustName(f.getCustName());
			fc.setCustAddress(f.getCustAddress());
			fc.setCreditCode(f.getCreditCode());
			fc.setLegalRepresentative(f.getLegalRepresentative());
			fc.setDispatchProportion(f.getDispatchProportion());
			fc.setLinkmanName(f.getLinkmanName());
			fc.setLinkmanPhone(f.getLinkmanPhone());
			fc.setLinkmanMail(f.getLinkmanMail());
			fc.setLinkmanPost(f.getLinkmanPost());
			fc.setMarketLeaderName(f.getMarketLeaderName());
			if(
					StringUtils.isNotBlank(f.getCreditCode()) &&
					StringUtils.isNotBlank(f.getCustAddress()) && 
					StringUtils.isNotBlank(f.getLegalRepresentative()) &&
					StringUtils.isNotBlank(f.getDispatchProportion()) &&
					StringUtils.isNotBlank(f.getLinkmanName()) &&
					StringUtils.isNotBlank(f.getLinkmanPhone())&&
					StringUtils.isNotBlank(f.getLinkmanMail())){
				fc.setIsOverAll("1");
			}else{
				fc.setIsOverAll("0");
			}
			nList.add(fc);
		}
		page.setPageNo(first.getPageNo());
		page.setPageSize(first.getPageSize());
		page.setCount(first.getCount());
		page.setList(nList);
		return page;
	}
}

