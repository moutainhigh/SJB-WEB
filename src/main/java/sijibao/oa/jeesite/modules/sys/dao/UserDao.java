/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.intfz.response.bi.UserRespForBI;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 根据钉钉ID查询用户
	 * @param user
	 * @return
	 */
	public User getByDingId(User user);

	/**
	 * 根据微信OPENID查询用户
	 * @param user
	 * @return
	 */
	public User getByWechatId(User user);
	
	/**
	 * 根据手机号查询用户
	 * @param user
	 * @return
	 */
	public User getByPhone(User user);
	
	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	/**
	 * 删除用户, 最后更新时间不是传入时间
	 * @param user
	 * @return
	 */
	public int deleteUser(User user);
	
	/**
	 * 根据岗位ID查询该岗位下的所有用户
	 * @param user
	 * @return
	 */
	public List<User> queryUserInfoForPostCode(PostInfo postInfo);
	
	/**
	 * 根据岗位编码和部门ID查询关联的用户信息
	 * @param user
	 * @return
	 */
	public List<User> queryUserInfoForPostCodeAndOffice(User user);
	
	/**
	 * 查询资源办理人员列表信息
	 * @param user
	 * @return
	 */
	public List<User> queryHandlerUserInfo(User user);
	/**
	 * 查询市场人员信息
	 * @param user
	 * @return
	 */
	public List<User> queryMemberInfo(User user);

	public void updateUserStatus(User user);

	public void updateBeReport();

	public void entry(User user);

	public List<User> getUserByNo(String no);

	public List<User> findReportList(User user);

	int findUserForValidate(String officeId);

	public List<String> queryAllUserId();

	/**
	 * 根据手机号查询用户
	 * @param name
	 * @return
	 */
	public User getByName(String name);

    User getUserByMail(String mail);

    List<UserRespForBI> findAllColumnsForBI();

    int checkMobile(User user);

    int checkLoginName(User user);

    List<User> getUserByIds(@Param("userIds") List<String> userIds);

	List<String> queryUserIdsByOfficeId(@Param("officeId") String officeId);

	List<String> queryUserIdsByRoleId(@Param("roleId") String roleId);

	List<String> queryUserIdsByPostId(@Param("postId") String postId);
}
