/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.dao.OpenAccountFlowDao;
import com.sijibao.oa.modules.flow.entity.OpenAccountFlow;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 开户管理Service
 * @author wanxb
 * @version 2018-07-02
 */
@Service
@Transactional(readOnly = true)
public class OpenAccountFlowService extends CrudService<OpenAccountFlowDao, OpenAccountFlow> {

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OpenAccountFlowDao openAccountFlowDao;
	
	public OpenAccountFlow get(String id) {
		return super.get(id);
	}

	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void openAccountFlowCompleteTask(OpenAccountFlow open,User user) {
		
		// 设置审批意见
		open.getAct().setComment(("yes".equals(open.getAct().getFlag())?"[同意] ":"[驳回] ")+open.getAct().getComment());
		String flag = open.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(open.getAct().getFlag())? "1" : "0");
		
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(open.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComentForChild(open.getAct().getProcInsId(),act.getTaskId(),open.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
		}else{
			actTaskService.complete(open.getAct().getTaskId(), open.getAct().getProcInsId(), open.getAct().getComment(), vars);
		}
		
		//判断流程是否退回并更新开户申请状态
		if("no".equals(flag)){ 
			open.setStatus(Constant.expense_back); //开户申请状态被退回
			openAccountFlowDao.updateOpenAccountFlowStatus(open);
		}
		
		//判断流程是否结束并更新开户申请状态
		Act e = new Act();
		e.setProcInsId(open.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		if(e == null){
			//流程已结束
			open.setFlowFinishTime(new Date());
			open.setOpenAccountStatus("1");//开户状态从配置中修改为可使用
			open.setStatus(Constant.expense_approve_end); //开户申请状态审批结束
			openAccountFlowDao.updateStatusAndFlowFinshTime(open);
		}
	}
	
	public List<OpenAccountFlow> findList(OpenAccountFlow openAccountFlow) {
		return super.findList(openAccountFlow);
	}
	
	public Page<OpenAccountFlow> findPage(Page<OpenAccountFlow> page, OpenAccountFlow openAccountFlow) {
		return super.findPage(page, openAccountFlow);
	}
	
	@Transactional(readOnly = false)
	public void save(OpenAccountFlow openAccountFlow) {
		super.save(openAccountFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpenAccountFlow openAccountFlow) {
		super.delete(openAccountFlow);
	}

	public OpenAccountFlow getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}
	
	/**
	 * 撤销流程接口
	 * @param resourcesApply
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void repealTask(OpenAccountFlow openAccountFlow, User user) {
		/********判断当前节点的下一个节点任务是否已经完成*********/
		
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		
		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(openAccountFlow.getAct().getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
		
		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(openAccountFlow.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(openAccountFlow.getAct().getTaskId());
			targetAct.setProcInsId(openAccountFlow.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}
		
		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComentForChild(openAccountFlow.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
	}

	
	
}