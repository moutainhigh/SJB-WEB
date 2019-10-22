package sijibao.oa.jeesite.modules.sys.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.intfz.response.bi.PostRespForBI;
import com.sijibao.oa.modules.sys.entity.PostInfo;

/**
 * 岗位信息dao接口
 * @author wuys
 *
 */
@MyBatisDao
public interface PostInfoDao extends CrudDao<PostInfo>  {

	PostInfo getByPostName(String postCode);


	PostInfo getPostInfoByPostCode(String postCode);
	
	
	List<PostInfo> getPostInfoByParentId(String parentId);

    String getPostIdByName(String postName);

    List<PostRespForBI> findAllColumnsForBI();

    int queryPostInfoNameById(PostInfo postInfo);

    int queryPostInfoCodeById(PostInfo postInfo);
}
