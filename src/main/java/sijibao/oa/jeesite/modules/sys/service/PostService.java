package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.response.bi.PostRespForBI;
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 岗位信息管理
 * @author wanxb
 * 
 *
 */
@Service
@Transactional(readOnly = true)
public class PostService extends CrudService<PostInfoDao, PostInfo> {
	
	@Autowired
	private PostInfoDao postInfoDao;
	@Autowired
	private SystemService systemService;
	
	public PostInfo get(String id) {
		return super.get(id);
	}
	
	public List<PostInfo> findList(PostInfo postInfo) {
		return super.findList(postInfo);
	}
	
	public Page<PostInfo> findPage(Page<PostInfo> page, PostInfo postInfo) {
		Page<PostInfo> result = super.findPage(page, postInfo);
		List<PostInfo> list = result.getList();
		if(null != list && list.size() > 0){
			for(PostInfo post : list){
				//查出每个岗位有哪些人员
				User u = new User();
				u.setPostIds(post.getId());
				List<User> userList = systemService.findUserNotAccess(u);

				if(null != userList && userList.size() > 0){
					StringBuilder sb = new StringBuilder();
					for(int i=0; i < userList.size(); i++){
						sb.append(userList.get(i).getName());
						if(i < userList.size() - 1){
							sb.append(",");
						}
					}
					post.setUserNames(sb.toString());
				}
				//查出上级岗位名称
				if(StringUtils.isNotBlank(post.getParentId())) {
					PostInfo p = postInfoDao.get(post.getParentId());
					post.setParentName(p.getPostName());
				}

				//查询该岗位是否有下级岗位
				PostInfo pi = new PostInfo();
				pi.setParentId(post.getId());
				List<PostInfo> list2 = postInfoDao.findList(pi);
				if(null != list2 && list2.size() > 0){
					post.setSubordinate(Global.YES);
				}else{
					post.setSubordinate(Global.NO);
				}
			}
		}
		return result;
	}
	
	@Transactional(readOnly = false)
	public void save(PostInfo postInfo) {
		super.save(postInfo);
		//清除用户缓存
		List<String> ids = UserUtils.queryUserIdsByPostId(postInfo.getId());
		if(ids != null && ids.size() > 0){//遍历清缓存
			for (String s : ids) {
				UserUtils.clearCache(UserUtils.get(s));
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(PostInfo postInfo) {
		super.delete(postInfo);
		//清除用户缓存
		List<String> ids = UserUtils.queryUserIdsByPostId(postInfo.getId());
		if(ids != null && ids.size() > 0){//遍历清缓存
			for (String s : ids) {
				UserUtils.clearCache(UserUtils.get(s));
			}
		}
	}

	public PostInfo getByPostName(String postCode) {
		return postInfoDao.getByPostName(postCode);
		 
	}

	public List<PostInfo> getPostInfoByParentId(String parentId){
		return postInfoDao.getPostInfoByParentId(parentId);
	}

	public PostInfo getPostInfoByPostCode(String postCode) {
		return postInfoDao.getPostInfoByPostCode(postCode);
	}


    public List<PostRespForBI> queryPostTableForBI() {
	    return postInfoDao.findAllColumnsForBI();
    }

    public int queryPostInfoNameById(PostInfo postInfo) {
		return postInfoDao.queryPostInfoNameById(postInfo);
    }

	public int queryPostInfoCodeById(PostInfo postInfo) {
		return postInfoDao.queryPostInfoCodeById(postInfo);
	}
}
