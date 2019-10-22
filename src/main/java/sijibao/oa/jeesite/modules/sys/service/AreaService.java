/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.TreeService;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.response.common.AreaInfoResponse;
import com.sijibao.oa.modules.sys.dao.AreaDao;
import com.sijibao.oa.modules.sys.entity.Area;

/**
 * 区域表service
 * @author wanxiongbo
 * @version 2018-03-22
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {
	@Autowired
	private AreaDao areaDao;
	public Area get(String id) {
		return super.get(id);
	}
	
	public Area getForCode(Area area) {
		return dao.getForCode(area);
	}
	
	public List<Area> findList(Area area) {
		if (StringUtils.isNotBlank(area.getParentIds())){
			area.setParentIds(","+area.getParentIds());
		}
		List<Area> findList = super.findList(area);
		return findList;
	}
	
	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
	}
	
	/**
	 * 接口端区域信息查询
	 */
	public List<AreaInfoResponse> setIntfzAreaInfo(List<Area> areaInfoList){
		List<AreaInfoResponse> resultList = new ArrayList<AreaInfoResponse>();
		List<Area> newAreaInfoList = new ArrayList<Area>();
		if(areaInfoList != null && areaInfoList.size() > 0){
			for(Area a1:areaInfoList){
				if("1".equals(a1.getType())){
					int count = 0;
					for(Area a2:areaInfoList){
						if(a1.getId().equals(a2.getParentId())){
							count = 1;
						}
					}
					if(count == 0){
						Area areaInfo = new Area();
						areaInfo.setName(a1.getName());
						areaInfo.setCode(a1.getCode());
						Area parent = new Area();
						parent.setId(a1.getId());
						parent.setCode(a1.getCode());
						areaInfo.setParent(parent);
						newAreaInfoList.add(areaInfo);
					}
				}
			}
			areaInfoList.addAll(newAreaInfoList);
			for(Area a:areaInfoList){
				AreaInfoResponse areaInfoResponse = new AreaInfoResponse();
				areaInfoResponse.setName(a.getName());
				areaInfoResponse.setValue(a.getCode());
				if("86".equals(a.getParentId())){
					areaInfoResponse.setParent("");
				}else{
					areaInfoResponse.setParent(a.getParent().getId());
				}
				resultList.add(areaInfoResponse);
			}
		}
		return resultList;
	}
	
	public List<AreaInfoResponse> setIntfzAreaInfoForWeb(List<Area> areaInfoList){
		List<AreaInfoResponse> resultList = new ArrayList<AreaInfoResponse>();
		if(areaInfoList != null && areaInfoList.size() > 0){
			for(Area a:areaInfoList){
				AreaInfoResponse areaInfoResponse = new AreaInfoResponse();
				areaInfoResponse.setName(a.getName());
				areaInfoResponse.setValue(a.getCode());
				if("86".equals(a.getParentId())){
					areaInfoResponse.setParent("");
				}else{
					areaInfoResponse.setParent(a.getParent().getId());
				}
				resultList.add(areaInfoResponse);
			}
		}
		return resultList;
	}

	public List<String> findAreaNameByIds(String[] areaId) {
		return areaDao.findAreaNameByIds(areaId);
	}
}