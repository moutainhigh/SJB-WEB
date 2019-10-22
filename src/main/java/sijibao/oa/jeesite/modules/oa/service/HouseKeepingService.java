/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.oa.dao.HouseKeepingDao;
import com.sijibao.oa.modules.oa.dao.HouseKeepingPhotoDao;
import com.sijibao.oa.modules.oa.entity.HouseKeeping;
import com.sijibao.oa.modules.oa.entity.HouseKeepingPhoto;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 工作内务管理服务
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class HouseKeepingService extends CrudService<HouseKeepingDao, HouseKeeping> {
	
	@Autowired
	private HouseKeepingPhotoDao houseKeepingPhotoDao;
	
	public HouseKeeping get(String id) {
		return super.get(id);
	}

	@Transactional(readOnly = false)
	public void save(HouseKeeping houseKeeping) {
		super.save(houseKeeping);
	}

	@Transactional(readOnly = false)
	public void delete(HouseKeeping houseKeeping) {
		super.delete(houseKeeping);
	}
	
	@Transactional(readOnly = false)
	public void savePhoto(HouseKeepingPhoto houseKeepingPhoto){
		HouseKeepingPhoto hkp = houseKeepingPhotoDao.getByMainCode(houseKeepingPhoto);
		if(hkp == null){
			hkp = houseKeepingPhoto;
		}else{
			hkp.setUrl(houseKeepingPhoto.getUrl());
		}
		if(hkp.getIsNewRecord()){
			hkp.preInsert();
			houseKeepingPhotoDao.insert(hkp);
		}else{
			hkp.preUpdate();
			houseKeepingPhotoDao.update(hkp);
		}
	}
	
	@Transactional(readOnly = false)
	public String save(HouseKeeping houseKeeping, User user, String type){
		//初始化签到数据
		initDate(user);
		HouseKeeping hk = dao.getByUserIdAndWordDate(user.getId(), houseKeeping.getWorkDate());
		if("1".equals(hk.getIsSign())){
			return "已签到！无需重复签到";
		}
		if(DateUtils.compareDate(DateUtils.parse(houseKeeping.getWorkDate(), DateUtils.YYYY_MM_DD), new Date()) > 0){
			return "签到时间大于当前时间，无法签到！";
		}
		String userName = hk.getUserName();
		String workDate = hk.getWorkDate();
		switch(type){
			case "0":
				hk.setIsSign("2");
				break;
			case "1":
				hk.setIsSign("1");
				break;
			default:
				break;
		}
		boolean isAll = true;
		HouseKeepingPhoto query = new HouseKeepingPhoto();
		query.setUserName(userName);
		query.setWorkDate(workDate);
		query.setPhotoType("sign");
		HouseKeepingPhoto hkp = houseKeepingPhotoDao.getByMainCode(query);
		if(hkp == null){
			hkp = new HouseKeepingPhoto();
			if(StringUtils.isNotBlank(houseKeeping.getSignUrl())){
				hkp.setUserName(userName);
				hkp.setWorkDate(workDate);
				hkp.setPhotoType("sign");
				hkp.setUrl(houseKeeping.getSignUrl());
				if(hkp.getIsNewRecord()){
					hkp.preInsert();
					houseKeepingPhotoDao.insert(hkp);
				}else{
					hkp.preUpdate();
					houseKeepingPhotoDao.update(hkp);
				}
				hk.setSignCode("sign");
			}else{
				isAll = false;
			}
		}
		query.setPhotoType("work");
		hkp = houseKeepingPhotoDao.getByMainCode(query);
		if(hkp == null){
			hkp = new HouseKeepingPhoto();
			if(StringUtils.isNotBlank(houseKeeping.getWorkUrl())){
				hkp.setUserName(userName);
				hkp.setWorkDate(workDate);
				hkp.setPhotoType("work");
				hkp.setUrl(houseKeeping.getWorkUrl());
				if(hkp.getIsNewRecord()){
					hkp.preInsert();
					houseKeepingPhotoDao.insert(hkp);
				}else{
					hkp.preUpdate();
					houseKeepingPhotoDao.update(hkp);
				}
				hk.setWorkCode("work");
			}else{
				isAll = false;
			}
		}
		query.setPhotoType("inner");
		hkp = houseKeepingPhotoDao.getByMainCode(query);
		if(hkp == null){
			hkp = new HouseKeepingPhoto();
			if(StringUtils.isNotBlank(houseKeeping.getInnerUrl())){
				hkp.setUserName(userName);
				hkp.setWorkDate(workDate);
				hkp.setPhotoType("inner");
				hkp.setUrl(houseKeeping.getInnerUrl());
				if(hkp.getIsNewRecord()){
					hkp.preInsert();
					houseKeepingPhotoDao.insert(hkp);
				}else{
					hkp.preUpdate();
					houseKeepingPhotoDao.update(hkp);
				}
				hk.setInnerCode("inner");
			}else{
				isAll = false;
			}
		}
		if(!isAll){
			return "图片上传不完整，请重新上传后再签到！";
		}
		hk.preUpdate();
		hk.setUpdateBy(user);
		dao.update(hk);
		return null;
	}
	
	@Transactional(readOnly = false)
	public String score(HouseKeeping query, User user){
		HouseKeeping hk = dao.getByUserIdAndWordDate(query.getUserId(), query.getWorkDate());
		if(hk == null){
			return "未找到对应的签到记录，请刷新后重试";
		}
		if("0".equals(hk.getIsSign())){
			return "该签到记录未完成签到，签到后才可评分";
		}
		if(hk.getScore() != null){
			return "该签到已评分，无需重复打分";
		}
		hk.setScore(query.getScore());
		hk.setScoreId(user.getId());
		hk.setScoreName(user.getName());
		hk.preUpdate();
		hk.setUpdateBy(user);
		dao.update(hk);
		return null;
	}
	
	public Page<HouseKeeping> findMonthPage(Page<HouseKeeping> page, HouseKeeping query, User user){
		query.setPage(page);
		query.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
		page.setList(dao.findMonthList(query));
		return page;
	}
	
	public Page<HouseKeeping> findDayPage(Page<HouseKeeping> page, HouseKeeping query, User user){
		query.setPage(page);
		page.setList(dao.findDayList(query));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void initDate(User user){
		String workDate = DateUtils.formatDate(new Date(), "yyyy-MM");
		int count = dao.checkMonth(user.getId(), workDate);
		if(count == 0){
			Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
			int day = aCalendar.getActualMaximum(Calendar.DATE);
			DecimalFormat df = new DecimalFormat("00");
			for (int i = 1; i <= day; i++) {
				HouseKeeping hk = new HouseKeeping();
				hk.setUserId(user.getId());
				hk.setUserName(user.getName());
				hk.setWorkDate(workDate + "-" + df.format(i));
				hk.setIsSign("0");
				hk.preInsert();
				hk.setCreateBy(user);
				dao.insert(hk);
			}
		}
	}

}