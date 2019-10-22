package sijibao.oa.jeesite.modules.oa.entity;


import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 费用控制标准主表Entity
 * @author xuby
 * @version 2018-03-20
 */
public class ExpensesStandardsMain extends DataEntity<ExpensesStandardsMain> {

	private static final long serialVersionUID = 1L;
	private String officeId; //所属公司ID
	private String officeName; //所属公司名称
	private ExpensesSubInfo project; //费控科目ID
	private String standsName; //策略名称
	private String standsType; //控制类别(金额,交通方式,城市类别,岗位)(可多选)
	private String controlStrategy; //控制策略(刚性/柔性)
	
	private String[] standsTypes; // 控制类别列表
	private String officeGrade; //机构层级
	public ExpensesStandardsMain(){
		super();
	}
	
	public ExpensesStandardsMain(String id){
		super(id);
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public ExpensesSubInfo getProject() {
		return project;
	}

	public void setProject(ExpensesSubInfo project) {
		this.project = project;
	}

	public String getStandsType() {
		return standsType;
	}

	public void setStandsType(String standsType) {
		this.standsType = standsType;
	}

	public String getControlStrategy() {
		return controlStrategy;
	}

	public void setControlStrategy(String controlStrategy) {
		this.controlStrategy = controlStrategy;
	}

	public String getStandsName() {
		return standsName;
	}

	public void setStandsName(String standsName) {
		this.standsName = standsName;
	}

	public String[] getStandsTypes() {
		return standsTypes;
	}

	public void setStandsTypes(String[] standsTypes) {
		this.standsTypes = standsTypes;
	}

	public String getOfficeGrade() {
		return officeGrade;
	}

	public void setOfficeGrade(String officeGrade) {
		this.officeGrade = officeGrade;
	}
}
