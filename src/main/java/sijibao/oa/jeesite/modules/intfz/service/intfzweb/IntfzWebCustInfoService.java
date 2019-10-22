package sijibao.oa.jeesite.modules.intfz.service.intfzweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.request.custinfo.*;
import com.sijibao.oa.modules.intfz.response.custinfo.*;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.BatchMoveHiDao;
import com.sijibao.oa.modules.oa.dao.ContractInfoDao;
import com.sijibao.oa.modules.oa.dao.CustInfoDao;
import com.sijibao.oa.modules.oa.dao.CustMaintenanceDao;
import com.sijibao.oa.modules.oa.entity.*;
import com.sijibao.oa.modules.oa.service.CustInfoService;
import com.sijibao.oa.modules.oa.service.CustMaintenanceService;
import com.sijibao.oa.modules.oa.service.CustMergeService;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.entity.Dict;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.AreaService;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 客户资料管理service
 * @author wanxb
 *
 */
@Service
@Transactional(readOnly = true)
public class IntfzWebCustInfoService extends BaseService  {
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private CustInfoDao custInfoDao;
	@Autowired
	private CustMaintenanceService custMaintenanceService;
	@Autowired
	private CustMaintenanceDao custMaintenanceDao;
	@Autowired
	private ContractInfoDao contractInfoDao;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private CustMergeService custMergeService;
	@Autowired
	private BatchMoveHiDao batchMoveHiDao ;

	/**
	 * 查询列表数量
	 * @param user
	 * @param req 
	 * @return
	 */
	public CustInfoResponse findNum(User user, CustInfoHandleReq req) {
		CustInfo cust = new CustInfo();
		cust.setConflict(req.getConflictName());
		cust.setMarketLeaderId(req.getMarketLeaderId());
		cust.setCustTrades(req.getCustTrades());
		cust.setBeginTime(DateUtils.parseDate(req.getBeginTime()));
		cust.setEndTime(DateUtils.parseDate(req.getEndTime()));
		cust.setUser(user);
		int count1 = custInfoDao.findListOne(cust);
		int count2 = custInfoDao.findListTwo(cust);
		int count3 = 0;
		if(StringUtils.isNotBlank(req.getMarketLeaderId()) ||
					req.getBeginTime() != 0l ||
					req.getEndTime() != 0l ||
					StringUtils.isNotBlank(req.getCustTrades()) ||
					StringUtils.isNotBlank(req.getConflictName())){
			count3 = custInfoDao.findListThree(cust);
		}
		int count4 = custInfoDao.findListFour(cust);
		int count5 = custInfoDao.findListFive(cust);
//		List<CustInfo> list = custInfoService.findList(cust);
//		HashMap<String, Integer> map = Maps.newHashMap();
//		map.put("1",0);
//		map.put("2",0);
//		map.put("3",0);
//		map.put("4",0);
//		map.put("5",0);
//		for (CustInfo c : list) {
//			if(StringUtils.isNotBlank(c.getCustListPlace())){
//				switch (c.getCustListPlace()) {
//				case "1":
//					map.put("1", map.get("1") + 1);
//					break;
//				case "2":
//					if(StringUtils.equals(user.getOffice().getId(), c.getCustOfficeId())){
//						map.put("2", map.get("2") + 1);
//					}
//					if(!StringUtils.equals(user.getOffice().getId(), c.getCustOfficeId()) &&
//							(StringUtils.isNotBlank(req.getMarketLeaderId()) ||
//								req.getBeginTime() != 0l ||
//								req.getEndTime() != 0l ||
//								StringUtils.isNotBlank(req.getCustTrades()) ||
//								StringUtils.isNotBlank(req.getConflictName()))	){
//						map.put("3", map.get("3") + 1);
//					}
//					break;
//				case "4":
//					if(user.getId().equals(c.getMarketLeaderId())){
//						map.put("4", map.get("4") + 1);
//					}
//					if(!user.getId().equals(c.getMarketLeaderId()) &&
//							(StringUtils.isNotBlank(req.getMarketLeaderId()) ||
//							req.getBeginTime() != 0l ||
//							req.getEndTime() != 0l ||
//							StringUtils.isNotBlank(req.getCustTrades()) ||
//							StringUtils.isNotBlank(req.getConflictName()))){
//						map.put("3", map.get("3") + 1);
//					}
//					break;
//				case "5":
//					if(user.getId().equals(c.getMarketLeaderId())){
//						map.put("5", map.get("5") + 1);
//					}
//					if(!user.getId().equals(c.getMarketLeaderId()) &&
//							(StringUtils.isNotBlank(req.getMarketLeaderId()) ||
//							req.getBeginTime() != 0l ||
//							req.getEndTime() != 0l ||
//							StringUtils.isNotBlank(req.getCustTrades()) ||
//							StringUtils.isNotBlank(req.getConflictName()))){
//						map.put("3", map.get("3") + 1);
//					}
//					break;
//				default:
//					break;
//				}
//			}
//		}
		CustInfoResponse cu = new CustInfoResponse();
		cu.setHighSeasNum(count1); //公海客户
		cu.setRegionSeasNum(count2); //区域公海
		cu.setOtherNum(count3); //其他
		cu.setOwnNum(count4);//个人客户
		cu.setSigningNum(count5);//签约客户
		return cu;
	}
	/**
	 * 列表查询，带搜索条件
	 * @param page
	 * @param req
	 * @return
	 */
	public Page<CustInfoPageResponse> findPage(Page<CustInfo> page, CustInfoReq req, User user) {
		CustInfo custInfo = new CustInfo();
		custInfo.setConflict(req.getConflictName());
		custInfo.setCustListPlace(req.getCustListPlace());
		custInfo.setMarketLeaderId(req.getMarketLeaderId());
		custInfo.setCustTrades(req.getCustTrades());
		custInfo.setBeginTime(DateUtils.parseDate(req.getBeginTime()));
		custInfo.setEndTime(DateUtils.parseDate(req.getEndTime()));
		custInfo.setUser(user);
		Page<CustInfo> findPage = custInfoService.findPage(page, custInfo);
		Page<CustInfoPageResponse> newPage = new Page<CustInfoPageResponse>();
		newPage.setPageNo(findPage.getPageNo());
		newPage.setPageSize(findPage.getPageSize());
		newPage.setCount(findPage.getCount());
		ArrayList<CustInfoPageResponse> list = Lists.newArrayList();
		List<Dict> placeList = DictUtils.getDictList("cust_list_place");
		for (CustInfo cu : findPage.getList()) {
			CustInfoPageResponse cust = new CustInfoPageResponse();
			cust.setId(cu.getId());
			cust.setCustCode(cu.getCustCode());//客户编号
			cust.setCustName(cu.getCustName());//客户名称
			if(com.sijibao.base.common.provider.utils.StringUtils.isNotBlank(cu.getMainCustId())){
				cust.setMainCustId(cu.getMainCustId());
				cust.setMainCustName(cu.getMainCustName());
			}
			cust.setCustListPlace(cu.getCustListPlace());
			cust.setCreditCode(cu.getCreditCode());
			if(cu.getCustCompanySize() != null && !"".equals(cu.getCustCompanySize())){
				cust.setCustCompanySize(cu.getCustCompanySize());//	规模
			}else{
				cust.setCustCompanySize(null);
			}
			cust.setCustStage(cu.getCustStage());
			cust.setCustStageName(cu.getCustStageName());//客户状态
			cust.setCustTradesName(cu.getCustTradesName());//行业
			cust.setOfficeName(cu.getCustOfficeName());//所属区域

			//公海，区域公海   不同的项： 1 联系人职位  2开放时间
			if( "1".equals(cu.getCustListPlace()) || "2".equals(cu.getCustListPlace())){
				cust.setCustListPlace(cu.getCustListPlace());
				cust.setCustListPlaceName(DictUtils.getName(placeList,cu.getCustListPlace()));
			}
			//个人   不同的项： 1 跟进人  2更新时间
			if( "4".equals(cu.getCustListPlace()) || "5".equals(cu.getCustListPlace())){
				cust.setCustListPlace("0");
				cust.setCustListPlaceName(DictUtils.getName(placeList,"0"));
				cust.setMan(cu.getMarketLeaderName());
			}
			cust.setTime(cu.getUpdateTime().getTime());
			list.add(cust);
		}
		newPage.setList(list);
		return newPage;
		
	}
	
	/**
	 * 详细页面
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public CustInfoDetailResponse getCustInfoById(String id) {
		CustInfo custInfo = custInfoService.get(id);
		List<CustLinkman> lklist = custInfoService.findCustLinkmanListByCustId(custInfo.getId());//获取联系人信息
		List<CustLinkmanResponse> lkmList = Lists.newArrayList();
		for (CustLinkman cu : lklist) {
			CustLinkmanResponse c = new CustLinkmanResponse();
			c.setLinkmanName(cu.getLinkmanName());
			c.setLinkmanPhone(cu.getLinkmanPhone());
			c.setLinkmanMail(cu.getLinkmanMail() == null ? "": cu.getLinkmanMail());
			c.setIsContractLinkman(cu.getIsContractLinkman());
			c.setLinkmanPost(cu.getLinkmanPost());
			c.setRemarks(cu.getRemarks() == null ? "":cu.getRemarks());
			lkmList.add(c);
		}
		List<CustTradeStructure> ctsList = custInfoService.findCustTradeStructureListByCustId(custInfo.getId());//获取结构信息
		CustTradeStructureResponse trade = new CustTradeStructureResponse();
		if(ctsList.size() > 0){
			trade.setDownstream(ctsList.get(0).getDownstream());
			trade.setUpstream(ctsList.get(0).getUpstream());
		}else{//解决app端bug
			trade.setDownstream("");
			trade.setUpstream("");
		}
		//设置合同信息
		ContractInfo c = contractInfoDao.findContractInfoByCsutId(custInfo.getId());
		if(c == null){
			c = new ContractInfo();
		}
		ContractInfoDetailResponse con = new ContractInfoDetailResponse();
		con.setCreditCode(c.getCreditCode());
		con.setLegalRepresentative(c.getLegalRepresentative());
		con.setDispatchProportion(c.getDispatchProportion());
		con.setRegisteredAddress(c.getRegisteredAddress());
		CustInfoDetailResponse custInfoDetailResponse = new CustInfoDetailResponse();
		if(lkmList.size() == 0){//处理app端bug
			CustLinkmanResponse st = new CustLinkmanResponse();
			st.setIsContractLinkman("0");
			st.setLinkmanMail("");
			st.setLinkmanName("");
			st.setLinkmanPost("");
			st.setLinkmanPhone("");
			st.setRemarks("");
			lkmList.add(st);
		}
		custInfoDetailResponse.setCustLinkmanResponse(lkmList);
		custInfoDetailResponse.setCustTradeStructureResponse(trade);
		custInfoDetailResponse.setCustName(custInfo.getCustName()); //客户名称
		custInfoDetailResponse.setCustAbbreviation(custInfo.getCustAbbreviation());
		
		custInfoDetailResponse.setCustCode(custInfo.getCustCode());
		custInfoDetailResponse.setContractInfoDetailResponse(con);
		custInfoDetailResponse.setCustHolderStructure(custInfo.getCustHolderStructure());
		custInfoDetailResponse.setCustListPlace(custInfo.getCustListPlace());
		if(StringUtils.isNotBlank(custInfo.getCustNature())){
			custInfoDetailResponse.setCustNature(custInfo.getCustNature());
			custInfoDetailResponse.setCustNatureAppName(DictUtils.getDictLabel(custInfo.getCustNature(), "cust_nature", ""));
		}
		custInfoDetailResponse.setCustSource(custInfo.getCustSource());
		if(StringUtils.isNotBlank(custInfo.getCustAddressCode())){
			custInfoDetailResponse.setCustAddressCode(custInfo.getCustAddressCode().split(","));
			String ss = "";
			for (String s : custInfo.getCustAddressCode().split(",")) {
				ss += areaService.get(s).getName() + ",";
			}
			custInfoDetailResponse.setCustAddressCodeName(ss.substring(0, ss.length()-1));
		}
		
		custInfoDetailResponse.setCustOfficeName(officeService.get(custInfo.getCustOfficeId()).getName());
		
		custInfoDetailResponse.setCustAddress(custInfo.getCustAddress());
		if(StringUtils.isNotBlank(custInfo.getCustType())){
			custInfoDetailResponse.setCustType(custInfo.getCustType());
			custInfoDetailResponse.setCustTypeAppName(DictUtils.getDictLabel(custInfo.getCustType(), "cust_type", ""));
		}
		if(StringUtils.isNotBlank(custInfo.getCustStage())){
			custInfoDetailResponse.setCustStage(custInfo.getCustStage());
			custInfoDetailResponse.setCustStageAppName(DictUtils.getDictLabel(custInfo.getCustStage(), "cust_stage", ""));
		}
		custInfoDetailResponse.setMarketLeaderId(custInfo.getMarketLeaderId());
		custInfoDetailResponse.setMarketLeaderName(custInfo.getMarketLeaderName());
		custInfoDetailResponse.setMarketLeaderPhone(custInfo.getMarketLeaderPhone());
		if(StringUtils.isNotBlank(custInfo.getCustTrades())){
			custInfoDetailResponse.setCustTrades(custInfo.getCustTrades());
			custInfoDetailResponse.setCustTradesAppName(DictUtils.getDictLabel(custInfo.getCustTrades(), "cust_trades", ""));
		}
		if(StringUtils.isNotBlank(custInfo.getCustBusinessType())){
			custInfoDetailResponse.setCustBusinessType(custInfo.getCustBusinessType());
			custInfoDetailResponse.setCustBusinessTypeAppName(DictUtils.getDictLabel(custInfo.getCustBusinessType(), "cust_business_type", ""));
		}
		if(custInfo.getCustCompanySize() != null && !"".equals(custInfo.getCustCompanySize())){
			custInfoDetailResponse.setCustCompanySize(custInfo.getCustCompanySize());
		}else{
			custInfoDetailResponse.setCustCompanySize(null);
		}
		if(StringUtils.isNotBlank(custInfo.getCustReceiveMode())){
			custInfoDetailResponse.setCustReceiveMode(custInfo.getCustReceiveMode());
			custInfoDetailResponse.setCustReceiveModeAppName(DictUtils.getDictLabel(custInfo.getCustReceiveMode(), "cust_receive_mode", ""));
		}
		if(StringUtils.isNotBlank(custInfo.getCustPowerMode())){
			custInfoDetailResponse.setCustPowerMode(custInfo.getCustPowerMode());
			custInfoDetailResponse.setCustPowerModeAppName(DictUtils.getDictLabel(custInfo.getCustPowerMode(), "cust_power_mode", ""));
		}
		if(StringUtils.isNotBlank(custInfo.getCustDeliverMode())){
			custInfoDetailResponse.setCustDeliverMode(custInfo.getCustDeliverMode());
			custInfoDetailResponse.setCustDeliverModeAppName(DictUtils.getDictLabel(custInfo.getCustDeliverMode(), "cust_deliver_mode", ""));
		}
		if(StringUtils.isNotBlank(custInfo.getCustBalanceObj())){
			custInfoDetailResponse.setCustBalanceObj(custInfo.getCustBalanceObj());
			custInfoDetailResponse.setCustBalanceObjAppName(DictUtils.getDictLabel(custInfo.getCustBalanceObj(), "cust_balance_obj", ""));
		}
		custInfoDetailResponse.setCustBalanceCycle(custInfo.getCustBalanceCycle());
		if(StringUtils.isNotBlank(custInfo.getPayMethod())){
			custInfoDetailResponse.setPayMethod(custInfo.getPayMethod().split(","));
			String ss = "";
			for (String s : custInfo.getPayMethod().split(",")) {
				ss += DictUtils.getDictLabel(s, "pay_method", "") + ",";
			}
			custInfoDetailResponse.setPayMethodName(ss.substring(0, ss.length()-1));
		}
		if(StringUtils.isNotBlank(custInfo.getAreaId()) ){
			custInfoDetailResponse.setAreaId(custInfo.getAreaId().split(","));
			String ss = "";
			for (String s : custInfo.getAreaId().split(",")) {
				ss += areaService.get(s).getName() + ",";
			}
			custInfoDetailResponse.setPayMethodName(ss.substring(0, ss.length()-1));
		}
		custInfoDetailResponse.setLetGoMan(custInfo.getLetGoMan());
		if(StringUtils.isNotBlank(custInfo.getLetGoMan()) && UserUtils.get(custInfo.getLetGoMan()) != null ){
			custInfoDetailResponse.setLetGoManMoble(UserUtils.get(custInfo.getLetGoMan()).getMobile());
		}
		custInfoDetailResponse.setRemarks(custInfo.getRemarks()==null?"":custInfo.getRemarks()); //备注
		return custInfoDetailResponse;
	}
	/**
	 * 保存客户信息
	 * @param custInfoSaveReq
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public void saveCustInfo(CustInfoSaveReq custInfoSaveReq,User user) throws ServiceException{
		if(StringUtils.isBlank(custInfoSaveReq.getId())){
			int num = custInfoDao.getCustCount(user.getId());
			if(num >= Integer.parseInt(DictUtils.getDictLabel("1", "cust_max_count", "")) ){
				throw new ServiceException("个人客户超过上限!");
			}
		}
		//判断市场负责人是否离职
		if(StringUtils.isNotBlank(custInfoSaveReq.getMarketLeaderId())){
			if(!"1".equals(user.getUserStatus())){
				throw new ServiceException("您已被人事设为离职状态，请找人事更改后再次提交!");
			}
		}
		//判断机构是否停用
		if(user.getOffice() != null && StringUtils.isNotBlank(user.getOffice().getId())){
			Office office = officeService.get(user.getOffice().getId());
			if(!"1".equals(office.getUseable())){
				throw new ServiceException("您所属的部门已停用，请联系管理员修改后再次提交!");
			}
		}
		//客户名称校验
		CustInfo custInfo = new CustInfo();
		if(StringUtils.isNotBlank(custInfoSaveReq.getId()) && !"0".equals(custInfoSaveReq.getId())){
			custInfo = custInfoService.get(custInfoSaveReq.getId());
			custInfo.preUpdateForWechart(user);
		}else{
			custInfo.setCustCode(CodeUtils.genCode("C", DateUtils.getDate("yyyyMMdd"), 3));
			custInfo.setProducSide(custInfoSaveReq.getProducSide());
			custInfo.preInsertForWeChart(user);
			custInfo.setMarketLeaderId(user.getId());
			custInfo.setMarketLeaderName(user.getName());
			custInfo.setMarketLeaderPhone(user.getMobile());
		}
		custInfo.setCustName(custInfoSaveReq.getCustName()); //客户名称
		custInfo.setCustAbbreviation(custInfoSaveReq.getCustAbbreviation());
		custInfo.setCustHolderStructure(custInfoSaveReq.getCustHolderStructure());
		custInfo.setCustNature(custInfoSaveReq.getCustNature());
		custInfo.setCustSource(custInfoSaveReq.getCustSource());
		if(custInfoSaveReq.getCustAddressCode() != null && custInfoSaveReq.getCustAddressCode().length != 0){
			String[] code = custInfoSaveReq.getCustAddressCode();
			String s = "";
			for (int i = 0; i < code.length; i++) {
				s += code[i] + ",";
			}
			custInfo.setCustAddressCode(s.substring(0,s.length()-1));
		}else{
			custInfo.setCustAddressCode("");
		}
		custInfo.setCustAddress(custInfoSaveReq.getCustAddress());
		custInfo.setCustType(custInfoSaveReq.getCustType());
		custInfo.setCustTrades(custInfoSaveReq.getCustTrades());
		custInfo.setCustOfficeId(user.getOffice().getId());
		custInfo.setCustBusinessType(custInfoSaveReq.getCustBusinessType());
		if(custInfoSaveReq.getCustCompanySize() != null && !"".equals(custInfoSaveReq.getCustCompanySize())){
			custInfo.setCustCompanySize(custInfoSaveReq.getCustCompanySize());
		}else{
			custInfo.setCustCompanySize(null);
		}
		custInfo.setCustPowerMode(custInfoSaveReq.getCustPowerMode());
		custInfo.setCustDeliverMode(custInfoSaveReq.getCustDeliverMode());
		custInfo.setCustReceiveMode(custInfoSaveReq.getCustReceiveMode());
		custInfo.setCustBalanceObj(custInfoSaveReq.getCustBalanceObj());
		custInfo.setCustBalanceCycle(custInfoSaveReq.getCustBalanceCycle());
		if(custInfoSaveReq.getPayMethod() != null && custInfoSaveReq.getPayMethod().length != 0){
			String[] payMethod = custInfoSaveReq.getPayMethod();
//			String s = "";
//			for (int i = 0; i < payMethod.length; i++) {
//				s = s + payMethod[i] + ",";
//			}
			StringBuilder methods = new StringBuilder();
			for (int i = 0; i < payMethod.length; i++) {
				methods = methods.append(payMethod[i] + ",");
			}
			if(methods != null){
				String s = methods.toString();
				String method = s.substring(0, s.length()-1);
				custInfo.setPayMethod(method);
			}
		}else{
			custInfo.setPayMethod("");
		}
		if(StringUtils.isBlank(custInfoSaveReq.getRemarks())){
			custInfo.setRemarks(""); //备注
		}else{
			custInfo.setRemarks(custInfoSaveReq.getRemarks()); //备注
		}
		//保存CustTradeStructureReq贸易结构信息
		CustTradeStructureReq ctsrList = custInfoSaveReq.getCustTradeStructureReq();
		CustTradeStructure cts = new CustTradeStructure();
		cts.setCustId(custInfo.getId());
		if(ctsrList != null){
			if(ctsrList.getUpstream() != null){
				cts.setUpstream(ctsrList.getUpstream());
			}
			if(ctsrList.getDownstream() != null){
				cts.setDownstream(ctsrList.getDownstream());
			}
		}
		custInfoService.deleteCustTradeStructure(cts);
		custInfoService.saveCustTradeStructure(cts);
		//保存custLinkman联系人信息
		List<CustLinkmanReq> custLinkman = custInfoSaveReq.getCustLinkman();
		CustMaintenanceSaveReq req = new CustMaintenanceSaveReq();
		if(custLinkman != null){
			//判断合同联系人唯一
			int count = 0 ;
			for (CustLinkmanReq c : custLinkman) {
				if("1".equals(c.getIsContractLinkman())){
					count++;
				}
			}
			if(count < 2){
				custInfoService.deleteCustLinkman(custInfo.getId());
				int num = 1;
				for (CustLinkmanReq lkq : custLinkman) {
					CustLinkman c = new CustLinkman();
					c.setCustId(custInfo.getId());
					c.setLinkmanName(lkq.getLinkmanName());
					c.setLinkmanPhone(lkq.getLinkmanPhone());
					c.setLinkmanMail(lkq.getLinkmanMail());
					c.setIsContractLinkman(lkq.getIsContractLinkman());
					c.setLinkmanPost(lkq.getLinkmanPost());
					if(StringUtils.isBlank(lkq.getRemarks())){
						c.setRemarks("");
					}else {
						c.setRemarks(lkq.getRemarks());
					}

					c.setOrderNum(num);
					custInfoService.saveCustLinkman(c,user);
					if(num == 1 ){
						req.setLinkmanId(c.getId());
						req.setLinkmanName(c.getLinkmanName());
					}
					if("1".equals(c.getIsContractLinkman())){
						req.setLinkmanId(c.getId());
						req.setLinkmanName(c.getLinkmanName());
					}
					num++;
				}
			}else{
				throw new ServiceException("合同联系人为单选项！");
			}
		}
        if(custInfo.getRemarks() == null){
            custInfo.setRemarks("");
        }
        //保存或更新客户信息
		if(StringUtils.isNotBlank(custInfoSaveReq.getId()) && !"0".equals(custInfoSaveReq.getId())){
			custInfoDao.update(custInfo);
		}else{
			custInfoDao.insert(custInfo);
			req.setCustId(custInfo.getId());
			req.setCustName(custInfo.getCustName());
			req.setCustMaintenanceDate(new Date().getTime());
			req.setCustStage(Constant.CUST_STAGE_G);
			req.setCustMaintenanceContent("新建客户");
			req.setVisitType("1");
			this.saveCustMaintenance(req, user);
		}
		//保存合同信息
		ContractInfo t = new ContractInfo();
		if(StringUtils.isNotBlank(custInfo.getId())){
			contractInfoDao.deleteContractInfoByCustId(custInfo.getId());
		}
		if(custInfoSaveReq.getContractInfoSaveReq() != null){
			t.setCustId(custInfo.getId());
			t.setCreditCode(custInfoSaveReq.getContractInfoSaveReq().getCreditCode());
			t.setRegisteredAddress(custInfoSaveReq.getContractInfoSaveReq().getRegisteredAddress());
			t.setLegalRepresentative(custInfoSaveReq.getContractInfoSaveReq().getLegalRepresentative());
			t.setDispatchProportion(custInfoSaveReq.getContractInfoSaveReq().getDispatchProportion());
			t.preInsertForWeChart(user);
			contractInfoDao.insert(t);
		}
	}

	/**
	 * 删除客户信息
	 * @param custInfo
	 */
	@Transactional(readOnly = false)
	public void deleteCustInfo(CustInfo custInfo){
		custInfoService.delete(custInfo);
		custMaintenanceDao.deleteByCustId(custInfo.getId());
		custInfoDao.deleteCustLinkmanByCustId(custInfo.getId());
		contractInfoDao.deleteContractInfoByCustId(custInfo.getId());
	}
	/**
	 * 客户维护管理分页查询
	 * @param page
	 * @param req
	 * @param user
	 * @return
	 */
	public Page<CustMaintenanceResponse> findCustMaintenancePage(Page<CustMaintenance> page,
			CustMaintenanceHandleReq req, User user) {
		CustMaintenance c = new CustMaintenance();
		c.setCustId(req.getCustId());
		Page<CustMaintenance> findPage = custMaintenanceService.findPage(page, c);
		Page<CustMaintenanceResponse> newPage = new Page<CustMaintenanceResponse>();
		newPage.setPageNo(findPage.getPageNo());
		newPage.setPageSize(findPage.getPageSize());
		newPage.setCount(findPage.getCount());
		ArrayList<CustMaintenanceResponse> list = Lists.newArrayList();
		for (CustMaintenance cu : findPage.getList()) {
			CustMaintenanceResponse cc = new CustMaintenanceResponse();
			cc.setCustName(cu.getCustName());
			if(cu.getCustMaintenanceDate() != null){
				cc.setCustMaintenanceDate(cu.getCustMaintenanceDate().getTime());
			}
			if(StringUtils.isNotBlank(cu.getCustStage())){
				cc.setCustStage(cu.getCustStage());
				cc.setCustStageName(DictUtils.getDictLabel(cu.getCustStage(), "cust_stage", ""));
			}
			if(StringUtils.isNotBlank(cu.getVisitType())){
				cc.setVisitType(cu.getVisitType());
				cc.setVisitTypeName(DictUtils.getDictLabel(cu.getVisitType(), "visit_type", ""));
			}
			cc.setLinkmanId(cu.getLinkmanId());
			cc.setLinkmanName(cu.getLinkmanName());
			cc.setCustMaintenanceContent(cu.getCustMaintenanceContent());
			cc.setCustMaintenanceMan(UserUtils.get(cu.getCreateBy().getId()).getName());
			if(StringUtils.isBlank(cu.getRemarks())){
				cc.setRemarks("");
			}else {
				cc.setRemarks(cu.getRemarks());
			}

			list.add(cc);
		}
		newPage.setList(list);
		return newPage;
	}
	/**
	 * 保存客户维护信息
	 * @param req
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void saveCustMaintenance(CustMaintenanceSaveReq req, User user) {
		//客户名称校验
		CustMaintenance c = new CustMaintenance();
		c.setProducSide(req.getProducSide());
		c.preInsertForWeChart(user);
		c.setCustId(req.getCustId());
		if(StringUtils.isNotBlank(req.getCustId())){
			CustInfo a = custInfoService.get(req.getCustId());
			c.setCustName(a.getCustName());
		}
		c.setCustMaintenanceDate(req.getCustMaintenanceDate() != 0?DateUtils.parseDate(req.getCustMaintenanceDate()):new Date());
		c.setCustStage(req.getCustStage());
		c.setCustMaintenanceContent(req.getCustMaintenanceContent());
		c.setLinkmanId(req.getLinkmanId());
		c.setLinkmanName(req.getLinkmanName());
		c.setVisitType(req.getVisitType());
		if(StringUtils.isBlank(req.getRemarks())){
			c.setRemarks("");
		}else{
			c.setRemarks(req.getRemarks());
		}

		custMaintenanceDao.insert(c);
		//存入客户信息
		CustInfo cu = custInfoService.get(req.getCustId());
		cu.setCustStage(req.getCustStage());
		cu.setUpdateBy(user);
		if(req.isFlag()){
			switch (req.getCustStage()) {
			case Constant.CUST_STAGE_A:
			case Constant.CUST_STAGE_B:
			case Constant.CUST_STAGE_C:
				cu.setCustListPlace("5");
				break;
			case Constant.CUST_STAGE_D:
			case Constant.CUST_STAGE_E:
			case Constant.CUST_STAGE_F:
			case Constant.CUST_STAGE_G:
				cu.setCustListPlace("4");
				break;
				
			default:
				break;
			}
		}
		cu.setUpdateTime(new Date());
		custInfoService.save(cu);
		
	}
	
	/**
	 * 客户维护管理-查询联系人
	 * @param req
	 * @return
	 */
	public List<CustLinkman> findCustLinkman(CustMaintenanceSaveReq req) {
		
		return custInfoDao.findCustLinkmanListByCustId(req.getCustId());
		
	}
	/**
	 * 客户管理-开放客户/捡入客户
	 * @param req
	 * @return
	 */
	@Transactional(readOnly = false)
	public void custChangePick(ChangePickReq req,User user) throws ServiceException {
		CustInfo custInfo = custInfoService.get(req.getId());
		
		//开放客户
		boolean flog = false;
		if("1".equals(req.getIsChange())){
			//判断客户的状态
			if("1".equals(custInfo.getCustListPlace()) ||
					"2".equals(custInfo.getCustListPlace())	){
				throw new ServiceException("该客户不可开放！");
			}
			custInfo.setLetGoMan(user.getId());
			custInfo.setLetGoTime(new Date());
			switch (custInfo.getCustStage()) {
			case Constant.CUST_STAGE_F:
			case Constant.CUST_STAGE_G:
				custInfo.setCustListPlace("1");
				custInfo.setSecondGoMan(user.getId());
				custInfo.setSecondGoTime(new Date());
				custInfo.setMarketLeaderId("");
				custInfo.setMarketLeaderName("");
				custInfo.setMarketLeaderPhone("");
				custInfoService.save(custInfo);
				flog = true;
				break;
			case Constant.CUST_STAGE_D:
			case Constant.CUST_STAGE_E:
				custInfo.setCustListPlace("2");
				custInfo.setMarketLeaderId("");
				custInfo.setMarketLeaderName("");
				custInfo.setMarketLeaderPhone("");
				custInfoService.save(custInfo);
				flog = true;
				break;
			case Constant.CUST_STAGE_A:
			case Constant.CUST_STAGE_B:
			case Constant.CUST_STAGE_C:
				throw new ServiceException("签约客户，不可开放！");
			default:
				break;
			}
			
		}
		if(flog){
			CustMaintenanceSaveReq rq = new CustMaintenanceSaveReq();
			rq.setCustId(custInfo.getId());
			rq.setCustName(custInfo.getCustName());
			rq.setCustMaintenanceDate(new Date().getTime());
			rq.setCustStage(custInfo.getCustStage());
			rq.setCustMaintenanceContent("开放客户");
			rq.setVisitType("1");
			rq.setFlag(false);
			//获取联系人
			CustLinkman ss = getCustLinkmanInfo(req.getId(), true);
			if(ss != null){
				rq.setLinkmanId(ss.getId());
				rq.setLinkmanName(ss.getLinkmanName());
			}
			if(StringUtils.isNotBlank(rq.getLinkmanId())){
				this.saveCustMaintenance(rq, user);
			}	
		}
		//捡入客户
		if("2".equals(req.getIsChange())){
			//判断客户的状态
			if("4".equals(custInfo.getCustListPlace()) ||
					"5".equals(custInfo.getCustListPlace())){
				throw new ServiceException("该客户已被捡入！");
			}
			int count = custInfoDao.getCustCount(user.getId());
			if(count >= Integer.parseInt(DictUtils.getDictLabel("1", "cust_max_count", "")) ){
				throw new ServiceException("个人客户超过上限!");
			}
			if(req.getIds() == null || req.getIds().size() == 0){
				if(StringUtils.equals(custInfo.getLetGoMan(), user.getId()) ||
						StringUtils.equals(custInfo.getSecondGoMan(), user.getId())	){
					throw new ServiceException("该客户不可捡回！");
				}
			}
			custInfo.setMarketLeaderId(user.getId());
			custInfo.setMarketLeaderName(user.getName());
			custInfo.setMarketLeaderPhone(user.getMobile());
			custInfo.setCustOfficeId(user.getOffice().getId());
			custInfo.setPickUpMan(user.getId());
			custInfo.setPickUpTime(new Date());
			custInfo.setCustListPlace("4");
			custInfo.setLetGoMan("");
			custInfo.setLetGoTime(null);
			custInfo.setSecondGoMan("");
			custInfo.setSecondGoTime(null);
			custInfoService.save(custInfo);
			CustMaintenanceSaveReq rq = new CustMaintenanceSaveReq();
			rq.setCustId(custInfo.getId());
			rq.setCustName(custInfo.getCustName());
			rq.setCustMaintenanceDate(new Date().getTime());
			rq.setCustStage(custInfo.getCustStage());
			rq.setCustMaintenanceContent("捡入新客户");
			rq.setVisitType("1");
			CustLinkman ss = getCustLinkmanInfo(req.getId(), false);
			if(ss != null){
				rq.setLinkmanId(ss.getId());
				rq.setLinkmanName(ss.getLinkmanName());
			}
			if(StringUtils.isNotBlank(rq.getLinkmanId())){
				this.saveCustMaintenance(rq, user);
			}
		}
	}
	
	/**
	 * 客户管理————客户池列表查询（分页）
	 * @param page
	 * @param req
	 * @param user
	 * @return
	 */
	public Page<CustInfoPageResponse> findPoolPage(Page<CustInfo> page, CustInfoReq req, User user) throws ServiceException {
		if(page.getPageSize() == 0 && page.getPageNo() == 0){
			throw new ServiceException("分页参数不能为空！");
		}
		CustInfo custInfo = new CustInfo();
		custInfo.setConflict(req.getConflictName());
		custInfo.setCustListPlace(req.getCustListPlace());
		
		custInfo.setMarketLeaderId(req.getMarketLeaderId());
		custInfo.setCustTrades(req.getCustTrades());
		if(req.getBeginTime() != 0l){
			custInfo.setBeginTime(DateUtils.parseDate(req.getBeginTime()));
		}
		if(req.getEndTime() != 0l){
			custInfo.setEndTime(DateUtils.parseDate(req.getEndTime()));
		}
		custInfo.setUser(user);
		int length = req.getCustOfficeId().length;
		if(length > 0){
			custInfo.setCustOfficeId(req.getCustOfficeId()[length - 1]);
		}
		Page<CustInfo> findPage = custInfoService.findPoolPage(page, custInfo);
		Page<CustInfoPageResponse> newPage = new Page<CustInfoPageResponse>();
		newPage.setPageNo(findPage.getPageNo());
		newPage.setPageSize(findPage.getPageSize());
		newPage.setCount(findPage.getCount());
		ArrayList<CustInfoPageResponse> list = Lists.newArrayList();
		for (CustInfo cu : findPage.getList()) {
			CustInfoPageResponse cust = new CustInfoPageResponse();
			ContractInfo c = contractInfoDao.findContractInfoByCsutId(cu.getId());//合同信息
			cust.setId(cu.getId());
			cust.setCustCode(cu.getCustCode());//客户编号
			cust.setCustName(cu.getCustName());//客户名称
			if(StringUtils.isNotBlank(cu.getMainCustId())){
				cust.setMainCustId(cu.getMainCustId());
				cust.setMainCustName(cu.getMainCustName());
				
			}
			cust.setCustListPlace(cu.getCustListPlace());
			if(c != null){
				cust.setCreditCode(c.getCreditCode());
			}
			if(cu.getCustCompanySize() != null && !"".equals(cu.getCustCompanySize())){
				cust.setCustCompanySize(cu.getCustCompanySize());//	规模
			}else{
				cust.setCustCompanySize(null);
			}
			cust.setCustStage(cu.getCustStage());
			cust.setCustStageName(DictUtils.getDictLabel(cu.getCustStage(), "cust_stage", ""));//客户状态
			if(StringUtils.isNotBlank(cu.getCustTrades())){
				cust.setCustTradesName(DictUtils.getDictLabel(cu.getCustTrades(), "cust_trades", ""));//行业
			}
			if(StringUtils.isNotBlank(cu.getCustOfficeId()) &&
					officeService.get(cu.getCustOfficeId()) != null	){
				cust.setOfficeName(officeService.get(cu.getCustOfficeId()).getName());//所属区域
			}
			
			//公海，区域公海   不同的项： 1 联系人职位  2开放时间
			if( "1".equals(cu.getCustListPlace()) || "2".equals(cu.getCustListPlace())){
				cust.setCustListPlace(cu.getCustListPlace());
				cust.setCustListPlaceName(DictUtils.getDictLabel(cu.getCustListPlace(), "cust_list_place", ""));
			}
			//个人   不同的项： 1 跟进人  2更新时间
			if( "4".equals(cu.getCustListPlace()) || "5".equals(cu.getCustListPlace())){
				cust.setCustListPlace("0");
				cust.setCustListPlaceName(DictUtils.getDictLabel("0", "cust_list_place", ""));
				cust.setMan(cu.getMarketLeaderName());
			}
			cust.setTime(cu.getUpdateTime().getTime());
			list.add(cust);
		}
		newPage.setList(list);
		return newPage;

	}
	
	/**
	 * 客户管理-批量移动客户
	 * @param req
	 * @param user
	 */
	@Transactional(readOnly = false)
	public int custBatchMove(BatchMoveReq req, User user) {
		//判断市场负责人是否离职
		if(StringUtils.isNotBlank(req.getMarketLeaderId())){
			User marketLeader = UserUtils.get(req.getMarketLeaderId());
			if(!"1".equals(marketLeader.getUserStatus())){
				throw new ServiceException("市场负责人(" + marketLeader.getName() + ")已离职，请重新选择后再提交。");
			}
		}
		//判断机构是否停用
		if(StringUtils.isNotBlank(req.getCustOfficeId())){
			Office office = officeService.get(req.getCustOfficeId());
			if(!"1".equals(office.getUseable())){
				throw new ServiceException("归属区域(" + office.getName() + ")停用，请重新选择后再提交。");
			}
		}
		boolean f = false;
		int errorCount = 0;
		List<CustInfo> lists = custInfoDao.queryCustInfoByIds(req.getCustIds());//查询所有的勾选客户
		List<CustInfo> list = Lists.newArrayList();
		//校验当前市场负责人个人客户上限
		if(StringUtils.isNotBlank(req.getMarketLeaderId()) &&
				!"A".equals(req.getCustStage()) &&
				!"B".equals(req.getCustStage()) &&
				!"C".equals(req.getCustStage())	&&
				StringUtils.isNotBlank(req.getCustStage())
		){
			int num = custInfoDao.getCustCount(req.getMarketLeaderId());//查询已有客户数量
			int max = Integer.parseInt(DictUtils.getDictLabel("1", "cust_max_count", ""));
			if(num+req.getCustIds().size() > max){
				throw new ServiceException("当前市场负责人个人客户超过上限("+max+")，超过个数"+(num+req.getCustIds().size()-max)+"个!请重新勾选后再进行批量移动。");
			}
		}else if (StringUtils.isNotBlank(req.getMarketLeaderId()) &&
				!"A".equals(req.getCustStage()) &&
				!"B".equals(req.getCustStage()) &&
				!"C".equals(req.getCustStage())	&&
				StringUtils.isBlank(req.getCustStage())
		){
			int num = custInfoDao.getCustCount(req.getMarketLeaderId());//查询已有客户数量
			int max = Integer.parseInt(DictUtils.getDictLabel("1", "cust_max_count", ""));

			if(num+req.getCustIds().size() > max){
				for(CustInfo custInfo : lists){
					if(StringUtils.isNotBlank(custInfo.getCustStage()) &&
							!"A".equals(custInfo.getCustStage()) &&
							!"B".equals(custInfo.getCustStage()) &&
							!"C".equals(custInfo.getCustStage()) &&
							num >= max
					){
						errorCount ++ ;
					}else if (StringUtils.isNotBlank(custInfo.getCustStage()) &&
							!"A".equals(custInfo.getCustStage()) &&
							!"B".equals(custInfo.getCustStage()) &&
							!"C".equals(custInfo.getCustStage()) &&
							num < max){
						num ++;
						list.add(custInfo);
					}else{
						list.add(custInfo);
					}
				}
			}else{
				list = lists;
			}
		}else{
			list = lists;
		}
		
		for (CustInfo custInfo : list) {
			BatchMove move = change(req,BatchMove.class);
			move.setId(custInfo.getId());

			if(StringUtils.isNotBlank(req.getMarketLeaderId()) && 
					("1".equals(custInfo.getCustListPlace()) || "2".equals(custInfo.getCustListPlace()))){ //如果该客户为空并且位置为公海/区域公海，则该次移动视为客户捡入
				User marketUser = UserUtils.get(req.getMarketLeaderId()); //市场负责人用户信息
				ChangePickReq changePickReq = new ChangePickReq();
				changePickReq.setId(custInfo.getId());
				changePickReq.setIsChange("2"); //捡入
				changePickReq.setIds(req.getCustIds());
				this.custChangePick(changePickReq, marketUser); //执行捡入操作
			}
			
			CustMaintenanceSaveReq rq = new CustMaintenanceSaveReq();
			rq.setCustId(custInfo.getId());
			rq.setFlag(false);
			rq.setCustName(custInfo.getCustName());
			rq.setCustMaintenanceDate(DateUtils.getNewDateAddFirst().getTime());
			rq.setCustStage(StringUtils.isNotBlank(req.getCustStage())?req.getCustStage():custInfo.getCustStage());
			rq.setCustMaintenanceContent(user.getName()+"("+user.getLoginName()+")批量移动客户");
			//获取联系人
			CustLinkman ss = getCustLinkmanInfo(custInfo.getId(),false);
			if(ss != null){
				rq.setLinkmanId(ss.getId());
				rq.setLinkmanName(ss.getLinkmanName());
			}
			rq.setVisitType("1");
			this.saveCustMaintenance(rq, user);
			
			//批量移动
			if(StringUtils.isNotBlank(req.getMarketLeaderId()) &&
					UserUtils.get(req.getMarketLeaderId()) != null){
				move.setMarketLeaderName(UserUtils.get(req.getMarketLeaderId()).getName());
				move.setMarketLeaderPhone(UserUtils.get(req.getMarketLeaderId()).getMobile());
			}
			if(StringUtils.isNotBlank(req.getCustStage())){
				if("4".equals(custInfo.getCustListPlace()) ||
						"5".equals(custInfo.getCustListPlace())
						){
					switch (req.getCustStage()) {
					case "A":
					case "B":
					case "C":
						move.setCustListPlace("5");
						break;
					case "D":
					case "E":
					case "F":
					case "G":
						move.setCustListPlace("4");
						break;

					default:
						break;
					}
				}
				move.setCustStage(req.getCustStage());
			}
			custInfoDao.custBatchMove(move);
			f = true;
		}
		if(f){
			BatchMoveHi hi = new BatchMoveHi();
			hi.preInsertForWeChart(user);
			hi.setMarketLeaderId(req.getMarketLeaderId());
			hi.setCustOfficeId(req.getCustOfficeId());
			hi.setCustStage(req.getCustStage());
			hi.setCustTrades(req.getCustTrades());
//			String custIds = "";
//			if(req.getCustIds().size() > 0){
//				for (String s : req.getCustIds()) {
//					custIds = custIds + s + ",";
//				}
//			}
			StringBuilder custId = new StringBuilder();
			if(req.getCustIds().size() > 0){
				for (String s : req.getCustIds()) {
					custId = custId.append(s + ",");
				}
			}
			if(custId != null){
				String custIds = custId.toString();
				hi.setCustIds(custIds.substring(0, custIds.length() - 1));
			}
			batchMoveHiDao.insert(hi);
		}
		return errorCount;
	}
	
	/**
	 * 获取主客户和子客户
	 * @param req
	 * @param user
	 * @return 
	 */
	public List<CustInstantResponse> queryCustInstant(CustInstantReq req, User user) {
		CustInfo c = new CustInfo();
		c.setCustName(req.getCustName());
		c.setUser(user);
		List<CustInfo> list = custInfoService.queryCustInstant(c);
		List<CustInstantResponse> resp = Lists.newArrayList();
		//查询主客户
		if(Constant.CUST_TYPE_MAIN.equals(req.getCustType())){
			for (CustInfo cu : list) {
				if(StringUtils.isBlank(cu.getIsMainCust()) || 
						Constant.CUST_TYPE_MAIN.equals(cu.getIsMainCust())){
					CustInstantResponse cc = new CustInstantResponse();
					cc.setCustId(cu.getId());
					cc.setCustName(cu.getCustName());
					resp.add(cc);
				}
			}
			return resp;
		}
		//查询子客户
		if(Constant.CUST_TYPE_CHILD.equals(req.getCustType())){
			for (CustInfo cu : list) {
				CustInstantResponse cc = new CustInstantResponse();
				cc.setCustId(cu.getId());
				cc.setCustName(cu.getCustName());
				resp.add(cc);
			}
			return resp;
		}
		return resp;
	}
	
	/**
	 * 合并客户
	 * @param req
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void custMerge(CustMergeReq req, User user) throws ServiceException{
		CustInfo c = custInfoService.get(req.getMainCustId());
		for (String child : req.getChildCustIds()) {
			if(StringUtils.equals(req.getMainCustId(), child)){
				throw new ServiceException("主客户和子客户选择有误，请重新选择！");
			}
		}
		if(c == null){
			throw new ServiceException("参数错误！");	
		}
		//判断主客户是否被合并过
		if(StringUtils.isNotBlank(c.getMainCustId())){
			throw new ServiceException("该主客户已经被" + c.getMainCustName() + "合并，请重新选择！");
		}
		c.setMergeChildIds(req.getChildCustIds());
		c.setUpdateTime(new Date());
		c.setIsMainCust("1");
		custInfoService.save(c);
		//判断子客户是否有子客户
		List<CustInfo> list = custInfoDao.queryChilds(c);
		if(list != null && list.size() > 0){
			for (CustInfo cu : list) {
				if(cu != null && StringUtils.isNotBlank(cu.getId())){
					c.getMergeChildIds().add(cu.getId());
				}
			}
		}
		//判断子客户是否有主客户，如果有主客户还有没有子客户
		if(list.size() > 0){
			for (CustInfo cu : list) {
				if(StringUtils.isNotBlank(cu.getMainCustId())){
					CustInfo cc = custInfoService.get(cu.getMainCustId());
					List<CustInfo> li = custInfoDao.queryChilds(cc);
					if(li == null || li.size() == 0){
						cc.setIsMainCust("");
						custInfoService.save(cc);
					}
				}
			}
		}
		
		custInfoDao.custMerge(c);
		for (String id : req.getChildCustIds()) {
			CustMerge me = new CustMerge();
			me.setMainCustId(req.getMainCustId());
			me.setChildCustId(id);
			me.setCreateTime(new Date());
			me.setCreateBy(user);
			custMergeService.save(me);
		}
		
		
	}
	/**
	 * 客户管理-批量开放
	 * @param req
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void custBatchOpen(ChangePickReq req, User user) {
		for (String id : req.getIds()) {
			ChangePickReq rq = new ChangePickReq();
			User mark = UserUtils.get(custInfoService.get(id).getMarketLeaderId());
			if(mark != null){
				rq.setId(id);
				rq.setIsChange("1");
				this.custChangePick(rq,mark);
			}
		}
	}
	
	/**
	 * web端不带分页的查询
	 */
	public List<CustInfoPageResponse> webFindCustInfos(CustInfoHandleReq req,User user) {
		CustInfo custInfo = new CustInfo();
		custInfo.setCustName(req.getCustName());
		custInfo.setConflict(req.getConflictName());
		custInfo.setMarketLeaderId(req.getMarketLeaderId());
		custInfo.setCustTrades(req.getCustTrades());
		custInfo.setBeginTime(DateUtils.parseDate(req.getBeginTime()));
		custInfo.setEndTime(DateUtils.parseDate(req.getEndTime()));
		custInfo.setUser(user);
		List<CustInfo> list = custInfoDao.webFindCustInfos(custInfo);
		
		ArrayList<CustInfoPageResponse> resultList = Lists.newArrayList();
		for (CustInfo cu : list) {
			CustInfoPageResponse cust = new CustInfoPageResponse();
			cust.setId(cu.getId());
			cust.setCustName(cu.getCustName());//客户名称
			resultList.add(cust);
		}
		return resultList;
	}

	/**
	 * app端不带分页的查询
	 */
	public List<CustInfoPageResponse> appFindCustInfos(CustInfoHandleReq req,User user) {
		CustInfo custInfo = new CustInfo();
		custInfo.setCustName(req.getCustName());
		custInfo.setConflict(req.getConflictName());
		custInfo.setMarketLeaderId(req.getMarketLeaderId());
		custInfo.setCustTrades(req.getCustTrades());
		custInfo.setBeginTime(DateUtils.parseDate(req.getBeginTime()));
		custInfo.setEndTime(DateUtils.parseDate(req.getEndTime()));
		custInfo.setUser(user);
		List<CustInfo> list = custInfoDao.appFindCustInfos(custInfo);

		ArrayList<CustInfoPageResponse> resultList = Lists.newArrayList();
		for (CustInfo cu : list) {
			CustInfoPageResponse cust = new CustInfoPageResponse();
			cust.setId(cu.getId());
			cust.setCustName(cu.getCustName());//客户名称
			resultList.add(cust);
		}
		return resultList;
	}
	
	
	//获取联系人信息
	private CustLinkman getCustLinkmanInfo(String custId,Boolean f) {
		//公海，区域公海   不同的项： 1 联系人职位  2开放时间
		List<CustLinkman> lklist = custInfoDao.findCustLinkmanListByCustId(custId);//获取联系人信息
		if(f){
			if( lklist == null || lklist.size() == 0){
				throw new ServiceException(custInfoService.get(custId).getCustName() + "联系人信息不完善！");
			}
		}
		boolean flag = true;
		if(lklist != null){
			for (CustLinkman man : lklist) {
				if("1".equals(man.getIsContractLinkman())){
					flag = false;
					return man;
				}
			}
			if(flag){
				if(lklist.size() != 0 ){
					return lklist.get(0);
				}
			}
		}
		return null;
	}
	
	@Transactional(readOnly = false)
	public void saveCustMaintenanceForTask(CustInfo custInfo){
		CustMaintenanceSaveReq rq = new CustMaintenanceSaveReq();
		rq.setCustId(custInfo.getId());
		rq.setCustName(custInfo.getCustName());
		rq.setCustMaintenanceDate(new Date().getTime());
		rq.setCustStage(custInfo.getCustStage());
		rq.setCustMaintenanceContent("开放客户");
		rq.setVisitType("1");
		rq.setFlag(false);
		
		User user = UserUtils.getByLoginName("admin");
		//客户名称校验
		CustMaintenance c = new CustMaintenance();
		c.preInsertForWeChart(user);
		c.setCustId(custInfo.getId());
		if(StringUtils.isNotBlank(custInfo.getId())){
			CustInfo a = custInfoService.get(custInfo.getId());
			c.setCustName(a.getCustName());
		}
		c.setCustMaintenanceDate(new Date());
		c.setCustStage(custInfo.getCustStage());
		c.setCustMaintenanceContent("系统自动开放客户");
		c.setLinkmanId("");
		c.setLinkmanName("");
		c.setVisitType("1");
		c.setRemarks("");
		custMaintenanceDao.insert(c);
	}

	public List<MyCustInfoResponse> queryMyCustInfos(User user) {
		return custInfoDao.queryMyCustInfos(user.getId());
	}
}
