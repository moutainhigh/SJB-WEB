package sijibao.oa.jeesite.modules.intfz.service.common;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.NumericUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.UploadUtils;
import com.sijibao.oa.modules.intfz.bean.UserInfo;
import com.sijibao.oa.modules.intfz.bean.WorkInfo;
import com.sijibao.oa.modules.intfz.request.WorkHandleReq;
import com.sijibao.oa.modules.intfz.request.WorkReq;
import com.sijibao.oa.modules.oa.dao.HouseKeepingDao;
import com.sijibao.oa.modules.oa.entity.HouseKeeping;
import com.sijibao.oa.modules.oa.service.HouseKeepingService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 内务管理服务层
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class IntfzHouseKeepingService extends BaseService {
	
	@Autowired
	private HouseKeepingService houseKeepingService;
	@Autowired
	private HouseKeepingDao houseKeepingDao;
	/**
	 * 提交OR保存草稿 
	 * @param req 请求参数
	 * @param user 签到人
	 * @return
	 */
	@Transactional(readOnly=false)
	public String save(WorkReq req, User user){
		HouseKeeping query = new HouseKeeping();
		query.setWorkDate(req.getWorkDate());
		query.setSignUrl(req.getWorkSignUrl());
		query.setWorkUrl(req.getWorkPhotoUrl());
		query.setInnerUrl(req.getInterPhotoUrl());
		return houseKeepingService.save(query, user, req.getType());
	}

	/**
	 * 评分
	 * @param req 请求参数
	 * @param user 评分人
	 * @return
	 */
	@Transactional(readOnly=false)
	public String score(WorkReq req, User user) {
		HouseKeeping query = new HouseKeeping();
		query.setUserId(req.getUserId());
		query.setWorkDate(req.getWorkDate());
		query.setScore(new BigDecimal(req.getScore()));
		return houseKeepingService.score(query, user);
	}

	/**
	 * 年月列表（分页）
	 * @param page
	 * @param req
	 * @return
	 */
	public Page<HouseKeeping> findMonthPage(Page<HouseKeeping> page, WorkHandleReq req, User user) {
		HouseKeeping query = new HouseKeeping();
		query.setUserId(req.getUserId());
		query.setStartWorkMonth(req.getStartMonth());
		query.setEndWorkMonth(req.getEndMonth());
		return houseKeepingService.findMonthPage(page, query, user);
	}
	/**
	 * 日列表（分页）
	 * @param page
	 * @param req
	 * @return
	 */
	public Page<HouseKeeping> findDayPage(Page<HouseKeeping> page, WorkHandleReq req, User user) {
		HouseKeeping query = new HouseKeeping();
		query.setUserName(UserUtils.get(req.getUserId()).getName());
		query.setWorkDate(req.getWorkDate());
		return houseKeepingService.findDayPage(page, query, user);
	}
	/**
	 * 草稿列表（分页）
	 * @param page
	 * @param req
	 * @return
	 */
	public Page<HouseKeeping> findDraftPage(Page<HouseKeeping> page, User user) {
		HouseKeeping query = new HouseKeeping();
		query.setUserName(user.getName());
		query.setIsSign("2");//草稿
		query.setEndWorkDate(DateUtils.getDate());
		return houseKeepingService.findDayPage(page, query, user);
	}
	
	/**
	 * 详情
	 * @param id 签到主键
	 * @param userName 签到人姓名
	 * @return
	 */
	public HouseKeeping findDetail(String id, String userName){
		HouseKeeping query = new HouseKeeping();
		query.setUserName(userName);
		query.setId(id);
		List<HouseKeeping> hkList = houseKeepingDao.findDayList(query);
		return hkList.get(0);
	}
	/**
	 * 查询月份列表
	 * @return
	 */
	public List<WorkInfo> monthList(WorkHandleReq req,User user,Page<HouseKeeping> page) {
		
		List<WorkInfo> workList = Lists.newArrayList();
		for(HouseKeeping hk : page.getList()){
			WorkInfo work = new WorkInfo();
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(hk.getUserId());
			userInfo.setUserName(hk.getUserName());
			work.setUserInfo(userInfo);
			work.setWorkYear(hk.getWorkDate().substring(0, 4));
			work.setWorkMonth(hk.getWorkDate().substring(5, 7));
			work.setWorkSignCount(hk.getSignNum());
			work.setWorkPhotoCount(hk.getWorkNum());
			work.setInteriorPhotoCount(hk.getInnerNum());
			if(hk.getScore() != null){
				work.setScore(NumericUtils.formatNumber(hk.getScore(),"#,##0.00#"));
			}
			workList.add(work);
		}
		return workList;
	}
	/**
	 * 查询天数列表
	 * @param req
	 * @param user
	 * @return
	 */
	public List<WorkInfo> dayList(WorkHandleReq req, User user, Page<HouseKeeping> page) {
		List<WorkInfo> workList = Lists.newArrayList();
		for(HouseKeeping hk : page.getList()){
			WorkInfo work = new WorkInfo();
			work.setSignId(hk.getId());
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(hk.getUserId());
			userInfo.setUserName(hk.getUserName());
			work.setUserInfo(userInfo);
			work.setWorkYear(hk.getWorkDate().substring(0, 4));
			work.setWorkMonth(hk.getWorkDate().substring(5, 7));
			work.setWorkDay(hk.getWorkDate().substring(8, 10));
			work.setIsSign(hk.getIsSign());
			if(hk.getScore() != null){
				work.setScore(NumericUtils.formatNumber(hk.getScore(),"#,##0.00#"));
				work.setIsScore("1");
			}else{
				work.setScore("0");
				work.setIsScore("0");
			}
			workList.add(work);
		}
		return workList;
				
	}
	/**
	 * 草稿列表
	 * @param req
	 * @param user
	 * @return
	 */
	public List<WorkInfo> draftList(WorkHandleReq req, User user, Page<HouseKeeping> page) {
		List<WorkInfo> workList = Lists.newArrayList();
		for(HouseKeeping hk : page.getList()){
			WorkInfo work = new WorkInfo();
			work.setSignId(hk.getId());
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(hk.getUserId());
			userInfo.setUserName(hk.getUserName());
			work.setUserInfo(userInfo);
			work.setWorkYear(hk.getWorkDate().substring(0, 4));
			work.setWorkMonth(hk.getWorkDate().substring(5, 7));
			work.setWorkDay(hk.getWorkDate().substring(8, 10));
			if(hk.getScore() != null){
				work.setScore(NumericUtils.formatNumber(hk.getScore(),"#,##0.00#"));
			}else{
				work.setScore("0");
			}
			workList.add(work);
		}
		return workList;
	}
	/**
	 * 详情
	 * @param req
	 * @return
	 */
	public WorkInfo getDetail(WorkHandleReq req) {
		HouseKeeping hk = findDetail(req.getSignId(), req.getUserName());
		String serverUrl = UploadUtils.getServerUrl(); //附件服务器地址
		WorkInfo work = new WorkInfo();
		work.setSignId(hk.getId());
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(hk.getUserId());
		userInfo.setUserName(hk.getUserName());
		work.setUserInfo(userInfo);
		work.setWorkYear(hk.getWorkDate().substring(0, 4));
		work.setWorkMonth(hk.getWorkDate().substring(5, 7));
		work.setWorkDay(hk.getWorkDate().substring(8, 10));
		if(StringUtils.isNotBlank(hk.getSignUrl())){
			work.setSignUrl(serverUrl+hk.getSignUrl());
		}
		if(StringUtils.isNotBlank(hk.getWorkUrl())){
			work.setWorkUrl(serverUrl+hk.getWorkUrl());
		}
		if(StringUtils.isNotBlank(hk.getInnerUrl())){
			work.setInnerUrl(serverUrl+hk.getInnerUrl());
		}
		if(hk.getScore() != null){
			work.setScore(NumericUtils.formatNumber(hk.getScore(),"#,##0.00#"));
		}else{
			work.setScore("0");
		}
		return work;
	}
	
}
