/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.TestAudit;
import com.sijibao.oa.modules.oa.entity.test.ContracthisMoveHis;
import com.sijibao.oa.modules.oa.entity.test.NeedMoveHis;
import com.sijibao.oa.modules.oa.entity.test.ProjectMoveHis;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface TestAuditDao extends CrudDao<TestAudit> {

	public TestAudit getByProcInsId(String procInsId);
	
	public int updateInsId(TestAudit testAudit);
	
	public int updateHrText(TestAudit testAudit);
	
	public int updateLeadText(TestAudit testAudit);
	
	public int updateMainLeadText(TestAudit testAudit);

	List<ContracthisMoveHis> queryContracthisMoveHi();


	void insertContracthisMoveHis(List<ContracthisMoveHis> lis);

	List<ProjectMoveHis> queryProjectHi();

	void insertProjectHis(List<ProjectMoveHis> lis);

	List<NeedMoveHis> queryNeedMoveHi();

	void insertNeedMoveHis(List<NeedMoveHis> list);
}
