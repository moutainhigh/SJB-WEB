package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sijibao.oa.jeesite.modules.oa.entity.*;
import sijibao.oa.jeesite.persistence.CrudDao;
import sijibao.oa.jeesite.persistence.annotation.MyBatisDao;

/**
 * 客户信息DAO接口
 * @author wanxb
 * @version 2018-05-25
 */
@MyBatisDao
public interface CustInfoDao extends CrudDao<CustInfo> {


	void insertCustTradeStructure(CustTradeStructure cts);

	void updataCustTradeStructure(CustTradeStructure cts);

	void insertCustLinkman(CustLinkman c);

	void updataCustLinkman(CustLinkman c);

	List<CustLinkman> findCustLinkmanListByCustId(String id);

	List<CustTradeStructure> findCustTradeStructureListByCustId(String id);

	List<CustInfo> getCustInfoByCustName(String custName);

	void deleteCustTradeStructure(CustTradeStructure cts);

	void deleteCustLinkman(String c);

	void deleteCustLinkmanByCustId(String custId);

	List<FirstCompanyInfo> findFirstCompanyInfoList(FirstCompanyInfo firstCompanyInfo);
	
	/**
	 * 根据客户ID查询客户合同信息
	 * @param custId 客户信息ID
	 * @return
	 */
	List<FirstCompanyInfo> findCustContractAndLinkmanInfoForCustId(String custId);

	CustLinkman findCustLinkmanListById(String id);
	
	/**
	 * 更新客户归属
	 * @param custInfo
	 */
	void updateCustListPlaceInfo(CustInfo custInfo);
	
	/**
	 * 查询客户信息(定时任务使用)
	 * @param custInfo
	 * @return
	 */
	List<CustInfo> queryCustInfoTask(CustInfo custInfo);

	List<CustInfo> findPoolPage(CustInfo custInfo);

	int getCustCount(String userId);

	void custBatchMove(BatchMove move);

	List<CustInfo> queryCustInstant(CustInfo c);

	void custMerge(CustInfo c);

	List<CustInfo> queryChilds(CustInfo c);

	List<CustInfo> webFindCustInfos(CustInfo custInfo);

	List<CustInfo> appFindCustInfos(CustInfo custInfo);

	int findListOne(CustInfo cust);

	int findListTwo(CustInfo cust);

	int findListThree(CustInfo cust);

	int findListFour(CustInfo cust);

	int findListFive(CustInfo cust);

    List<CustInfo> queryCustInfoByIds(@Param("custIds") List<String> custIds);

	List<MyCustInfoResponse> queryMyCustInfos(@Param("userId") String userId);
}