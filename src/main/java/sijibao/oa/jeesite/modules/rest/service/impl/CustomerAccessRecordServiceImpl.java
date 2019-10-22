package sijibao.oa.jeesite.modules.rest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.message.api.IntfzMessageService;
import com.sijibao.oa.message.api.request.DingTalkMessageRequest;
import com.sijibao.oa.modules.rest.dao.CustomerAccessRecordDao;
import com.sijibao.oa.modules.rest.service.CustomerAccessRecordService;
import com.sijibao.oa.modules.rest.vo.CustomerAccessRecord;

/**
 * @description: 沟通记录表，用于保存沟通记录主要信息
 * @author: xgx
 * @create: 2019-09-29 14:41
 **/
@Service("customerAccessRecordService")
@Transactional
public class CustomerAccessRecordServiceImpl implements CustomerAccessRecordService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CustomerAccessRecordDao customerAccessRecordDao;
    @Autowired
    private IntfzMessageService intfzMessageService;

    @Override
    public int insertCustomerAccessRecord(CustomerAccessRecord vo) {
        int result=0;
        try {
            result=customerAccessRecordDao.insertCustomerAccessRecord(vo);
            customerAccessRecordDao.insertRecordBigfield(vo.getCommunicateRecordBigfield());
        } catch (Exception e) {
            logger.error("新增失败",e);
            DingTalkMessageRequest request=new DingTalkMessageRequest();
            request.setProjectName("【运营平台数据同步】");
            request.setClassName("同步失败");
            request.setExceptionNo(null);
            request.setErrorMsg("新增失败：{"+ JSON.toJSONString(vo)+"}");
            intfzMessageService.sendDingTalk(request);
            throw new ServiceException("新增操作失败");
        }
        return result;
    }

    @Override
    public int updateCustomerAccessRecord(CustomerAccessRecord vo) {
        int result=0;
        try {
            result=customerAccessRecordDao.updateCustomerAccessRecord(vo);
            if(vo.getCommunicateRecordBigfield()!=null && vo.getCommunicateRecordBigfield().getId()!=null){
                customerAccessRecordDao.updateRecordBigfield(vo.getCommunicateRecordBigfield());
            }
        } catch (Exception e) {
            logger.error("更新失败",e);
            DingTalkMessageRequest request=new DingTalkMessageRequest();
            request.setProjectName("【运营平台数据同步】");
            request.setClassName("同步失败");
            request.setExceptionNo(null);
            request.setErrorMsg("更新失败：{"+ JSON.toJSONString(vo)+"}");
            intfzMessageService.sendDingTalk(request);
            throw new ServiceException("更新操作失败");
        }
        return result;
    }
}
