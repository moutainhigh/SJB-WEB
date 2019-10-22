package sijibao.oa.jeesite.modules.sys.entity;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 岗位信息对象
 * @author wanxb
 *
 */
public class PostInfo extends DataEntity<PostInfo> {
	
	private static final long serialVersionUID = 1L;
	private String id;//主键id
	private String postCode;//岗位编号
	private String postName;//岗位名称
	private String parentId; //父岗位id
	
	private String createByName;//创建者
	private String createDateTime;//创建时间
	private String updataByName;//更新者
	private String updataTime;//更新时间
	private String remarks;//岗位说明
	private String delFlag;//岗位状态
	
	private String officeId; //所属部门ID
	private String oldPostCode;


	private String userNames;//岗位人员
	private String parentName; //父岗位名称
	private String subordinate; //是否有下级岗位
    private String searchWord; //搜索关键字

	public PostInfo() {
		super();
	}

	public PostInfo(String id){
		super(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	


	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getUpdataByName() {
		return updataByName;
	}

	public void setUpdataByName(String updataByName) {
		this.updataByName = updataByName;
	}

	public String getUpdataTime() {
		return updataTime;
	}

	public void setUpdataTime(String updataTime) {
		this.updataTime = updataTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	@Override
	public String toString() {
		return "PostInfo [id=" + id + ", postCode=" + postCode + ", postName=" + postName + ", parentId=" + parentId
				+ ", createByName=" + createByName + ", createDateTime=" + createDateTime + ", updataByName="
				+ updataByName + ", updataTime=" + updataTime + ", remarks=" + remarks + ", delFlag=" + delFlag
				+ ", officeId=" + officeId + "]";
	}


	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	public String getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(String subordinate) {
		this.subordinate = subordinate;
	}

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

	public String getOldPostCode() {
		return oldPostCode;
	}

	public void setOldPostCode(String oldPostCode) {
		this.oldPostCode = oldPostCode;
	}
}
