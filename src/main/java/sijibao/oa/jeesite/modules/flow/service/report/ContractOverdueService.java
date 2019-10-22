package sijibao.oa.jeesite.modules.flow.service.report;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.report.ContractOverdueDao;
import com.sijibao.oa.modules.flow.entity.ContractOverdue;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 合同逾期报表Service
 * @author huangkai
 */
@Service
@Transactional(readOnly = true)
public class ContractOverdueService extends CrudService<ContractOverdueDao, ContractOverdue> {

	/**
	 * 查询合同逾期信息(分页)
	 * @param page
	 * @param contractOverdue
	 * @return
	 */
	public Page<ContractOverdue> findContractOverduePage(Page<ContractOverdue> page, ContractOverdue contractOverdue) {
		Page<ContractOverdue> resultPage = super.findPage(page, contractOverdue);
		List<ContractOverdue> list = resultPage.getList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null != list && list.size() >0){
			for(ContractOverdue co : list){
				co.setContractFlowStatus(DictUtils.getDictLabel(co.getContractFlowStatus(),"expense_status",""));
				co.setOpenAccountTimes(sdf.format(co.getOpenAccountTime()));
			}
		}
		return resultPage;
	}
	/**
	 * 查询合同逾期信息
	 * @param contractOverdue
	 * @return
	 */
	public List<ContractOverdue> findContractOverdueList(ContractOverdue contractOverdue){
		return dao.findList(contractOverdue);
	}

	/**
	 * 查询合同逾期信息
	 * @param page
	 * @param contractOverdue
	 * @return
	 */
	public ContractOverdue queryContractOverdue(ContractOverdue contractOverdue) {
		return dao.queryContractOverdue(contractOverdue);
	}
}
