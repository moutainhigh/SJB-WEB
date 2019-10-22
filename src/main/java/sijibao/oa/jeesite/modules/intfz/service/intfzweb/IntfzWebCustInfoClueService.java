package sijibao.oa.jeesite.modules.intfz.service.intfzweb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.request.custinfo.CustInfoClueHandleReq;
import com.sijibao.oa.modules.intfz.request.custinfo.CustInfoClueSaveReq;
import com.sijibao.oa.modules.intfz.response.custinfo.CustInfoClueDetailResponse;
import com.sijibao.oa.modules.intfz.response.custinfo.CustInfoClueResponse;
import com.sijibao.oa.modules.oa.dao.CustInfoClueDao;
import com.sijibao.oa.modules.oa.entity.CustInfoClue;
import com.sijibao.oa.modules.oa.service.CustInfoClueService;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.AreaService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 客户资料管理service
 * @author wanxb
 *
 */
@Service
@Transactional(readOnly = true)
public class IntfzWebCustInfoClueService  {
	@Autowired
	private CustInfoClueService custInfoClueService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private CustInfoClueDao custInfoClueDao;
	/**
	 * 列表查询，带搜索条件
	 * @param page
	 * @param req
	 * @return
	 */
	public Page<CustInfoClueResponse> findPage(Page<CustInfoClue> page, CustInfoClueHandleReq req, User user) {
		CustInfoClue custInfoClue = new CustInfoClue();
		custInfoClue.setUser(user);
		custInfoClue.setCustName(req.getCustName());
		custInfoClue.setMarketLeaderName(req.getMarketLeaderName());
		if(StringUtils.isNotBlank(req.getMarketLeaderId())){
			custInfoClue.setMarketLeader(UserUtils.get(req.getMarketLeaderId()));
		}
		custInfoClue.setUser(user);
		if(req.getClueTimeStart() != 0l ){
			custInfoClue.setClueTimeStart(DateUtils.parseDateFormUnix(req.getClueTimeStart(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		if(req.getClueTimeEnd() != 0l){
			custInfoClue.setClueTimeEnd(DateUtils.parseDateFormUnix(req.getClueTimeEnd(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		Page<CustInfoClue> findPage = custInfoClueService.findPage(page, custInfoClue);
		Page<CustInfoClueResponse> newPage = new Page<CustInfoClueResponse>();
		newPage.setPageNo(findPage.getPageNo());
		newPage.setPageSize(findPage.getPageSize());
		newPage.setCount(findPage.getCount());
		ArrayList<CustInfoClueResponse> list = Lists.newArrayList();
		for (CustInfoClue cu : findPage.getList()) {
			CustInfoClueResponse clue = new CustInfoClueResponse();
			clue.setCustCode(cu.getClueCode());
			clue.setId(cu.getId());
			clue.setCustName(cu.getCustName());
			clue.setCustAbbreviation(cu.getCustAbbreviation());
//			clue.setCustClassify(cu.getCustClassify());
			clue.setCustClassifyValue(DictUtils.getDictLabel(cu.getCustClassify(), "cust_classify", ""));
			if(cu.getAreaName() != null){
				int length = cu.getAreaName().split(",").length;
				clue.setAreaName(cu.getAreaName().split(",")[length-1]);
			}
			if(cu.getCustCompanySize() != null || "".equals(cu.getCustCompanySize())){
				clue.setCustCompanySize(cu.getCustCompanySize());
			}
//			if(cu.getTranMethod() != null){
//				clue.setTranMethod(cu.getTranMethod().split(","));
//			}
			if(cu.getTranMethodValue() != null){
				clue.setTranMethodValue(cu.getTranMethodValue());
			}
			clue.setCustLinkmanName(cu.getCustLinkmanName());
			clue.setLinkmanPost(cu.getLinkmanPost());
			clue.setLinkmanPhone(cu.getLinkmanPhone());
			if(cu.getMarketLeader() != null){
				cu.setMarketLeader(UserUtils.get(cu.getMarketLeader().getId()));
//				clue.setMarketLeaderId(cu.getMarketLeader().getId());
				clue.setMarketLeaderName(cu.getMarketLeader().getName());
			}
			if(cu.getUpdateTime() != null){
				clue.setUpdateTime(cu.getUpdateTime().getTime());
			}
			list.add(clue);
		}
		newPage.setList(list);
		return newPage;
	}
	/**
	 * 不带分页的查询
	 * @param req
	 * @return
	 */
	public List<CustInfoClueResponse> findCustInfos(CustInfoClueHandleReq req) {
		CustInfoClue custInfoClue = new CustInfoClue();
		custInfoClue.setCustName(req.getCustName());
		List<CustInfoClue> findList = custInfoClueService.findList(custInfoClue);
		ArrayList<CustInfoClueResponse> list = Lists.newArrayList();
		for (CustInfoClue cu : findList) {
			CustInfoClueResponse clue = new CustInfoClueResponse();
			clue.setId(cu.getId());
			clue.setCustName(cu.getCustName());
			clue.setCustAbbreviation(cu.getCustAbbreviation());
//			clue.setCustClassify(cu.getCustClassify());
			if(cu.getAreaName() != null){
				clue.setAreaName(cu.getAreaName().split(",")[cu.getAreaName().length()-1]);
			}
			if(cu.getCustCompanySize() != null || "".equals(cu.getCustCompanySize())){
				clue.setCustCompanySize(cu.getCustCompanySize());
			}
//			if(cu.getTranMethod() != null){
//				clue.setTranMethod(cu.getTranMethod().split(","));
//			}
			if(cu.getTranMethodValue() != null){
				clue.setTranMethodValue(cu.getTranMethodValue());
			}
			clue.setCustLinkmanName(cu.getCustLinkmanName());
			clue.setLinkmanPost(cu.getLinkmanPost());
			clue.setLinkmanPhone(cu.getLinkmanPhone());
//			if(cu.getMarketLeader() != null){
//				clue.setMarketLeaderId(cu.getMarketLeader().getId());
//			}
			clue.setUpdateTime(cu.getUpdateTime().getTime());
			list.add(clue);
		}
		return list;
	}
	/**
	 * 详细页面
	 * @param id
	 * @return
	 */
	public CustInfoClueDetailResponse getCustInCluefoById(String id) {
		CustInfoClue cu = custInfoClueService.get(id);
		CustInfoClueDetailResponse detail= new CustInfoClueDetailResponse();
		detail.setId(cu.getId());
		detail.setCustName(cu.getCustName());
		detail.setCustAbbreviation(cu.getCustAbbreviation());
		detail.setCustClassify(cu.getCustClassify());
		detail.setCustClassifyValue(DictUtils.getDictLabel(cu.getCustClassify(), "cust_classify", ""));
		if(cu.getAreaId() != null){
			detail.setAreaId(cu.getAreaId().split(","));
		}
		if(cu.getAreaName() != null){
			detail.setAreaName(cu.getAreaName().split(","));
		}
		if(cu.getCustCompanySize() != null || "".equals(cu.getCustCompanySize())){
			detail.setCustCompanySize(cu.getCustCompanySize());
		}
		if(cu.getTranMethod() != null){
			detail.setTranMethod(cu.getTranMethod().split(","));
		}
		if(cu.getTranMethodValue() != null){
			detail.setTranMethodValue(cu.getTranMethodValue().split(","));
		}
		detail.setCustLinkmanName(cu.getCustLinkmanName());
		detail.setLinkmanPost(cu.getLinkmanPost());
		detail.setLinkmanPhone(cu.getLinkmanPhone());
		if(cu.getMarketLeader() != null){
			detail.setMarketLeaderId(cu.getMarketLeader().getId());
			detail.setMarketLeaderName(cu.getMarketLeader().getName());
		}
		detail.setCustActuality(cu.getCustActuality());
		detail.setCustBusinessProfile(cu.getCustBusinessProfile());
		detail.setCustClueExplain(cu.getCustClueExplain());
		return detail;
	}
	/**
	 * 保存客户信息
	 * @param custInfoSaveReq
	 */
	@Transactional(readOnly = false)
	public void saveCustInfoClue(CustInfoClueSaveReq req,User user){
		CustInfoClue custInfoClue = new CustInfoClue();
		if(StringUtils.isNotBlank(req.getId()) && !"0".equals(req.getId())){
			custInfoClue = custInfoClueService.get(req.getId());
			custInfoClue.preUpdateForWechart(user);
		}else{
			custInfoClue.setClueCode(CodeUtils.genCode("D", DateUtils.getDate("yyyyMMdd"), 3));
			custInfoClue.preInsertForWeChart(user);
		}
		custInfoClue.setCustName(req.getCustName());
		custInfoClue.setCustAbbreviation(req.getCustAbbreviation());
		custInfoClue.setCustBusinessProfile(req.getCustBusinessProfile());
		custInfoClue.setCustClassify(req.getCustClassify());
		if(req.getAreaId().length != 0){
			String areaId = "";
			String areaName = "";
			for (String id : req.getAreaId()) {
				areaId = areaId + id + ",";
			}
			custInfoClue.setAreaId(areaId.substring(0, areaId.length()-1));
			List<String> areaNameList = areaService.findAreaNameByIds(req.getAreaId());
			for (String s : areaNameList) {
				areaName = areaName + s +",";
			}
			custInfoClue.setAreaName(areaName.substring(0, areaName.length()-1));
		}else{
			custInfoClue.setAreaId(null);
			custInfoClue.setAreaName(null);
		}
		if(req.getCustCompanySize() != null && !"".equals(req.getCustCompanySize()) ){
			custInfoClue.setCustCompanySize(req.getCustCompanySize());
		}else{
			custInfoClue.setCustCompanySize(null);
		}
		
		String method = "";
		String methodValue = "";
		if(req.getTranMethod() != null && req.getTranMethod().length > 0){
			for (int i = 0; i < req.getTranMethod().length; i++) {
				method = method + req.getTranMethod()[i] + ",";
				methodValue = methodValue + DictUtils.getDictLabel(req.getTranMethod()[i], "tran_method", "") + ",";
			}
			custInfoClue.setTranMethod(method.substring(0, method.length()-1));
			custInfoClue.setTranMethodValue(methodValue.substring(0, methodValue.length()-1));
		}else{
			custInfoClue.setTranMethod(null);
			custInfoClue.setTranMethodValue(null);
		}
		custInfoClue.setCustLinkmanName(req.getCustLinkmanName());
		custInfoClue.setLinkmanPost(req.getLinkmanPost());
		custInfoClue.setLinkmanPhone(req.getLinkmanPhone());
		custInfoClue.setMarketLeader(user);
		custInfoClue.setCustActuality(req.getCustActuality());
		custInfoClue.setCustClueExplain(req.getCustClueExplain());
		if(StringUtils.isBlank(req.getId()) && !"0".equals(req.getId())){
			custInfoClueDao.insert(custInfoClue);
		}else{
			custInfoClueDao.update(custInfoClue);
		}
	}
	/**
	 * 删除客户信息
	 * @param custInfoId
	 */
	@Transactional(readOnly = false)
	public void deleteCustInfo(CustInfoClue custInfoClue){
		custInfoClueService.delete(custInfoClue);
	}
}
