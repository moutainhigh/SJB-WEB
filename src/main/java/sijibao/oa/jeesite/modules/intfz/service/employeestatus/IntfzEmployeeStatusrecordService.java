package sijibao.oa.jeesite.modules.intfz.service.employeestatus;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.modules.intfz.request.bi.QueryEmployeeStatusRecordTableForBIReq;
import com.sijibao.oa.modules.intfz.request.employeestatus.EmployeeStatusSaveReq;
import com.sijibao.oa.modules.intfz.request.report.EmpStatusDayReportRequest;
import com.sijibao.oa.modules.intfz.response.bi.EmployStatusRecordRespForBI;
import com.sijibao.oa.modules.intfz.response.employeestatus.EmployeeStatusrecordResponse;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.EmployeeStatusrecordDao;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusrecord;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 员工状态服务层
 * @author xuby
 */
@Service
@Transactional(readOnly = true)
public class IntfzEmployeeStatusrecordService extends BaseService{
	
	@Autowired
	private EmployeeStatusrecordDao employeeStatusrecordDao;
	
	
	/**
	 * 保存员工状态变更记录
	 * @param user
	 * @param employeeStatusSaveReq
	 */
	@Transactional(readOnly = false)
	public void saveEmployeeStatusrecord(User user,EmployeeStatusSaveReq employeeStatusSaveReq){
		
		EmployeeStatusrecord employeeStatusrecord = change(employeeStatusSaveReq,EmployeeStatusrecord.class);
		employeeStatusrecord.setUser(user); //当前用户
		String status = ""; //员工状态
		EmployeeStatusrecord old = employeeStatusrecordDao.getNewTimeForUserId(employeeStatusrecord);
		if(old != null && StringUtils.isNotBlank(old.getStatus())){
			status = old.getStatus();
		}
		
		switch (employeeStatusrecord.getUserAction()) {
		case Constant.EMPLOYEE_ACTION_SETOUT: // 出发，状态为项目中
			status = Constant.EMPLOYEE_STATUS_IN;
			break;
		case Constant.EMPLOYEE_ACTION_EVINSITU: //撤离原地待命，状态为待命中
			status = Constant.EMPLOYEE_STATUS_OUT;
			break;
		case Constant.EMPLOYEE_ACTION_EVBASE: //撤离回基地，状态为待命中
			status = Constant.EMPLOYEE_STATUS_OUT;
			break;
		default:
			break;
		}
		
		employeeStatusrecord.setStatus(status); //员工状态
		employeeStatusrecord.preInsertForWeChart(user);
		if(employeeStatusrecord.getRemarks() == null){
		    employeeStatusrecord.setRemarks("");
        }
		employeeStatusrecordDao.insert(employeeStatusrecord);
	}
	
	/**
	 * 查询人员状态列表
	 * @param user
	 * @param req
	 * @return
	 */
	public Page<EmployeeStatusrecordResponse> findEmployeeStatusList(User user,Page<EmployeeStatusrecord> page,EmpStatusDayReportRequest req){
		
		Page<EmployeeStatusrecordResponse> resultPage = new Page<EmployeeStatusrecordResponse>();
		
		EmployeeStatusrecord employeeStatusrecord = new EmployeeStatusrecord();
		employeeStatusrecord.setPage(page);
		employeeStatusrecord.setUser(user);
		if(req != null && req.getDateTime() != 0l){
			employeeStatusrecord.setCreateTime(DateUtils.parseDate(req.getDateTime()));
		}
		
		page.setList(employeeStatusrecordDao.findList(employeeStatusrecord));

		resultPage.setCount(page.getCount());
		resultPage.setPageNo(page.getPageNo());
		resultPage.setPageSize(page.getPageSize());
		if(page.getList() != null && page.getList().size() > 0){
			List<EmployeeStatusrecordResponse> resultList = new ArrayList<EmployeeStatusrecordResponse>();
			for(EmployeeStatusrecord e:page.getList()){
				EmployeeStatusrecordResponse response = change(e, EmployeeStatusrecordResponse.class);
				response.setBaseName(DictUtils.getDictLabel(response.getBaseId(),"oa_employee_base", "")); //基地名称
				response.setStatusTxt(DictUtils.getDictLabel(response.getStatus(), "oa_employee_status", "")); //人员状态名称
				response.setUserActionTxt(DictUtils.getDictLabel(response.getUserAction(), "oa_employee_action", "")); //人员动作文案
				response.setCreateTime(e.getCreateTime().getTime());
				response.setUpdateTime(e.getUpdateTime().getTime());
				response.setRemarks(StringUtils.isBlank(e.getRemarks())?"":e.getRemarks());
				resultList.add(response);
			}
			resultPage.setList(resultList);
		}
		return resultPage;
	}
	//查询离职人员是否有实施状态
	public List<EmployeeStatusrecord> queryEmployee(User user) {
		List<EmployeeStatusrecord> list = employeeStatusrecordDao.queryEmployee(user);
		return list;
		
	}

    /**
     * 获取员工状态变更记录表数据，给BI使用
     * @param req
     * @return
     */
	public List<EmployStatusRecordRespForBI> queryEmployStatusRecordTableForBI(QueryEmployeeStatusRecordTableForBIReq req){
	    return employeeStatusrecordDao.findAllColumnsForBI(req);
    }

}
