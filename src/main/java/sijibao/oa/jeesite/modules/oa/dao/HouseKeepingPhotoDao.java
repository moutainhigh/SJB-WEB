package sijibao.oa.jeesite.modules.oa.dao;

import sijibao.oa.jeesite.modules.oa.entity.HouseKeepingPhoto;
import sijibao.oa.jeesite.persistence.CrudDao;
import sijibao.oa.jeesite.persistence.annotation.MyBatisDao;

/**
 * 内务管理DAO接口
 * @author Petter
 * @version 2017-12-24
 */
@MyBatisDao
public interface HouseKeepingPhotoDao extends CrudDao<HouseKeepingPhoto> {
	
	HouseKeepingPhoto getByMainCode(HouseKeepingPhoto houseKeepingPhoto);
	
}