package sijibao.oa.jeesite.modules.intfz.web.intfzThird;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.office.api.IntfzWebContractHisService;
import com.sijibao.oa.office.api.request.contracthis.ContractHisForBamsRequest;
import com.sijibao.oa.office.api.response.contracthis.ContractHisForBamsResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 合同第三方接口
 * @author xby
 * @date 2019-01-03
 */
@Controller
@RequestMapping(value = "third/contractHis")
@Api(value="第三方接口管理服务",tags="第三方接口管理服务")
public class IntfzThirdContractHisController extends BaseController {
	
	
	@Autowired
	private IntfzWebContractHisService intfzWebContractHisService;
	
	/**
	 * 运营查询合同信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryContractHisForBams")
	@ApiOperation(httpMethod = "POST", value = "第三方接口管理服务-运营合同附件查询")
	@ResponseBody
	public BaseResp<List<ContractHisForBamsResponse>> queryContractHisForBams(@RequestBody ContractHisForBamsRequest req, HttpServletRequest request, HttpServletResponse response){
		logger.info("===================IntfzQuartzContractHisController queryContractHisForBams start===================req:{}", JSON.toJSONString(req));
		
		if(StringUtils.isBlank(req.getHolderCode())){
			return new BaseResp<List<ContractHisForBamsResponse>>(IntfzRs.ERROR,"查询失败,企业用户编码不能为空!",null);
		}
		List<ContractHisForBamsResponse> resultList = intfzWebContractHisService.queryContractHisForBams(req);
		logger.info("===================IntfzQuartzContractHisController queryContractHisForBams  end===================response:{}", JSON.toJSONString(resultList));
		return new BaseResp<List<ContractHisForBamsResponse>>(IntfzRs.SUCCESS,"查询成功!",resultList);
	}
}
