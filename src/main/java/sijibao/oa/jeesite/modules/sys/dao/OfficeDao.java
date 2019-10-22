/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.sijibao.oa.common.persistence.TreeDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.intfz.response.bi.OfficeRespForBI;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 机构DAO接口
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office>
{

//    Office getByCode(@Param("code") String code);

    Office getByName(String name);

//    List<Office> findChildList(String parentId);
    
    /**
     * 根据机构层级查询机构信息
     * @param office
     * @return
     */
    List<Office> queryOfficInfoForGrade(Office office);

	List<Office> queryMarketOffice(Office office);

	void disableChildOffice(Office office);

    /**
     * 查找机构的后代机构
     * @param entity office
     * @return List<Office>
     */
    List<Office> findDescendants(Office entity);

    List<OfficeRespForBI> findAllColumnsForBI();

    List<User> getUserExclusionExEmployee();

    List<Map<String, Object>> queryDeptUserTree(Office entity);
}

