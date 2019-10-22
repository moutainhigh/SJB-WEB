package sijibao.oa.jeesite.modules.rest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.rest.service.CustomerAccessRecordService;
import com.sijibao.oa.modules.rest.vo.CustomerAccessRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @description: 运营平台沟通记录数据同步
 * @author: xgx
 * @create: 2019-09-29 16:40
 **/
@Controller
@RequestMapping(value = "rest/operate")
@Api(value="REST对外暴露接口",tags="运营平台沟通记录数据同步")
public class CustomerAccessRecordRest {
    @Autowired
    private CustomerAccessRecordService accessRecordService;

    @RequestMapping(value = "dataSync")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "运营平台数据同步")
    public BaseResp<String> dataSync(@RequestBody CustomerAccessRecord customerAccessRecord){
        String message="操作成功，影响0行!";
        try {
            if(customerAccessRecord.getCreateDate()!=null && customerAccessRecord.getUpdateDate()==null){
                int i = accessRecordService.insertCustomerAccessRecord(customerAccessRecord);
                if(i>0){
                    message="新增操作成功";
                }else{
                    return new BaseResp<String>(IntfzRs.ERROR,"新增失败!",null);
                }
            }else if(customerAccessRecord.getCreateDate()==null && customerAccessRecord.getUpdateDate()!=null){
                int i = accessRecordService.updateCustomerAccessRecord(customerAccessRecord);
                if(i>0){
                    message="更新（或删除）操作成功";
                }else{
                    return new BaseResp<String>(IntfzRs.ERROR,"更新失败!",null);
                }
            }else{
                return new BaseResp<String>(IntfzRs.ERROR,"请指明相关操作（新增或更新）!",null);
            }
        } catch (ServiceException e) {
            return new BaseResp<String>(IntfzRs.ERROR,e.getMessage(),null);
        }
        return new BaseResp<String>(IntfzRs.SUCCESS,message,null);
    }


}
