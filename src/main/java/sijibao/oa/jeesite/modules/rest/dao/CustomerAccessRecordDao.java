package sijibao.oa.jeesite.modules.rest.dao;

import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.rest.vo.CommunicateRecordBigfield;
import com.sijibao.oa.modules.rest.vo.CustomerAccessRecord;

@MyBatisDao
public interface CustomerAccessRecordDao {
    int insertCustomerAccessRecord(CustomerAccessRecord vo);
    int updateCustomerAccessRecord(CustomerAccessRecord vo);
    int insertRecordBigfield(CommunicateRecordBigfield vo);
    int updateRecordBigfield(CommunicateRecordBigfield vo);
}
