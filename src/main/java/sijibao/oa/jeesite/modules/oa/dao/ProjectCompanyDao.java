/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.ProjectCompany;

/**
 * 企业与项目关联关系DAO接口
 * @author wanxb
 * @version 2018-09-29
 */
@MyBatisDao
public interface ProjectCompanyDao extends CrudDao<ProjectCompany> {
	/*
	 * 查询绑定
	 */
	ProjectCompany queryBanding(@Param("companyCode") String companyCode);
	/*
	 * 查询企业信息
	 */
	ProjectCompany queryCompanyByProjectId(@Param("projectId") String projectId);
	
}