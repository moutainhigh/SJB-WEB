package sijibao.oa.jeesite.modules.flow.utils;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.modules.flow.entity.TravelFlow;
import com.sijibao.oa.modules.flow.service.ExpenseFlowService;
import com.sijibao.oa.modules.flow.service.TravelFlowService;

@Service
public class MutiGroupsListener implements TaskListener {
//    private ExpenseFlowDao expenseFlowDao;


    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("delegateTask.getEventName() = " + delegateTask.getEventName());

         //添加会签的人员，所有的都审批通过才可进入下一环节
        String insId = delegateTask.getProcessInstanceId();
        TravelFlowService travelFlowService = SpringContextHolder.getBean("travelFlowService");
        TravelFlow travelFlow = travelFlowService.getByProcInsId(insId);

//        String employee = delegateTask.getAssignee();
        String employee = travelFlow.getApplyPerCode();
        ExpenseFlowService expenseFlowService = SpringContextHolder.getBean("expenseFlowService");
        List<String> result = expenseFlowService.findManagerForEmployee(employee);
        if(null != result && result.size() > 0 ){
//            List<String> assigneeList = new ArrayList<String>();
//            assigneeList.add("18571570826");
//            assigneeList.add("17612759425");
            delegateTask.setVariable("partyList",result);
        }
    }


}
