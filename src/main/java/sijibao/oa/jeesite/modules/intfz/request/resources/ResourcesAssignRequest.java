/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.resources;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请人员指派记录表Request
 * @author xuby
 * @version 2018-05-29
 */
@ApiModel
public class ResourcesAssignRequest{
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="被指派者人员ID")
	private String targetAssign;		// 被指派者
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="满足人数")
	private int personelNum;		// 满足人数
	
	@IntfzValid(max = 4000)
	@ApiModelProperty(value="意见备注")
	private String remarks; //意见备注
	
	public String getTargetAssign() {
		return targetAssign;
	}

	public void setTargetAssign(String targetAssign) {
		this.targetAssign = targetAssign;
	}
	
	public int getPersonelNum() {
		return personelNum;
	}

	public void setPersonelNum(int personelNum) {
		this.personelNum = personelNum;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ResourcesAssignRequest [targetAssign=" + targetAssign + ", personelNum=" + personelNum + ", remarks="
				+ remarks + "]";
	}
}