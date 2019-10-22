/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.oa.dao.CustInfoDao;
import com.sijibao.oa.modules.oa.entity.CustInfo;
import com.sijibao.oa.modules.oa.entity.CustLinkman;
import com.sijibao.oa.modules.oa.entity.CustTradeStructure;
import com.sijibao.oa.modules.oa.entity.FirstCompanyInfo;
import com.sijibao.oa.modules.sys.entity.User;


/**
 * 客户信息表Service
 * @author wanxb
 * @version 2018-05-25
 */
@Service
@Transactional(readOnly = true)
public class CustInfoService extends CrudService<CustInfoDao, CustInfo> {
	@Autowired
	private CustInfoDao custInfoDao;
	
	public CustInfo get(String id) {
		return super.get(id);
	}
	/**
	 * 获取列表
	 */
	public List<CustInfo> findList(CustInfo custInfo) {
		return super.findList(custInfo);
	}
	/**
	 * 分页查询
	 */
	public Page<CustInfo> findPage(Page<CustInfo> page, CustInfo custInfo) {
		return super.findPage(page, custInfo);
	}
	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save(CustInfo custInfo) {
		super.save(custInfo);
	}
	/**
	 * 删除
	 */
	@Transactional(readOnly = false)
	public void delete(CustInfo custInfo) {
		super.delete(custInfo);
	}
	/**
	 * 保存贸易结构信息
	 * @param cts
	 */
	@Transactional(readOnly = false)
	public void saveCustTradeStructure(CustTradeStructure cts) {
		if(StringUtils.isBlank(cts.getId())){
			cts.preInsert();
			custInfoDao.insertCustTradeStructure(cts);
		}else{
			cts.preUpdate();
			custInfoDao.updataCustTradeStructure(cts);
		}
	}
	
	/**
	 * 保存联系人信息
	 * @param c
	 */
	@Transactional(readOnly = false)
	public void saveCustLinkman(CustLinkman c,User user) {
		if(StringUtils.isBlank(c.getId())){
			c.preInsertForWeChart(user);
			custInfoDao.insertCustLinkman(c);
		}else{
			c.preUpdateForWechart(user);
			custInfoDao.updataCustLinkman(c);
		}
	}
	public List<CustLinkman> findCustLinkmanListByCustId(String id) {
		return custInfoDao.findCustLinkmanListByCustId(id);
	}
	public List<CustTradeStructure> findCustTradeStructureListByCustId(String id) {
		
		
		return custInfoDao.findCustTradeStructureListByCustId(id);
		
	}
	public CustInfo getCustInfoByCustName(String custName) {
		List<CustInfo> cList = custInfoDao.getCustInfoByCustName(custName);
		if(cList != null && cList.size() > 0){
			return cList.get(0);
		}
		return null;
	}
	public void deleteCustTradeStructure(CustTradeStructure cts) {
		custInfoDao.deleteCustTradeStructure(cts);
		
	}
	public void deleteCustLinkman(String c) {
		custInfoDao.deleteCustLinkman(c);
		
	}
	public void deleteCustLinkmanByCustId(String custId) {
		custInfoDao.deleteCustLinkmanByCustId(custId);
		
	}
	public Page<FirstCompanyInfo> findFirstCompanyInfoList(Page<FirstCompanyInfo> p,
			FirstCompanyInfo firstCompanyInfo) {
		firstCompanyInfo.setPage(p);
		p.setList(dao.findFirstCompanyInfoList(firstCompanyInfo));
		return p;
	}
	
	/**
	 * 根据客户ID查询客户合同信息
	 * @param custId 客户信息ID
	 * @return
	 */
	public List<FirstCompanyInfo> findCustContractAndLinkmanInfoForCustId(String custId){
		return custInfoDao.findCustContractAndLinkmanInfoForCustId(custId);
	}
	
	/**
	 * 更新客户归属
	 * @param custInfo
	 */
	@Transactional(readOnly = false)
	public void updateCustListPlaceInfo(CustInfo custInfo){
		custInfoDao.updateCustListPlaceInfo(custInfo);
	} 
	
	/**
	 * 查询客户信息(定时任务使用)
	 * @param custInfo
	 * @return
	 */
	public List<CustInfo> queryCustInfoTask(CustInfo custInfo){
		return custInfoDao.queryCustInfoTask(custInfo);
	}
	/**
	 * 负责人查看列表
	 * @param page
	 * @param custInfo
	 * @return
	 */
	public Page<CustInfo> findPoolPage(Page<CustInfo> page, CustInfo custInfo) {
		String sql = dataScopeFilter(custInfo.getUser(), "o", "u");
		custInfo.setSql(sql);
		custInfo.setPage(page);
		page.setList(custInfoDao.findPoolPage(custInfo));
		return page;
	}
	
	/**
	 * 查询主、子客户
	 * @param c
	 * @return
	 */
	public List<CustInfo> queryCustInstant(CustInfo c) {
		String sql = dataScopeFilter(c.getUser(), "o", "u");
		c.setSql(sql);
		return custInfoDao.queryCustInstant(c);
	}

}