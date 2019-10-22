package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.common.utils.excel.annotation.ExcelField;

/**
 * 小工具-当前审批中的单据（查询报表列表）
 * @author wanxb
 *
 */
public class FeatureToolOwnUn extends DataEntity<FeatureToolOwnUn> {

	private static final long serialVersionUID = 1L;
	private Date start ;//开始时间（搜索）
	private Date end ;//结束时间（搜索）
	private String one;//1
	private String two;//2
	private String three;//3
	private String four;//4

	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

	@ExcelField(title="功能", align=2, sort=100)
	public String getOne() {
		return one;
	}
	public void setOne(String one) {
		this.one = one;
	}

	@ExcelField(title="提交人", align=2, sort=200)
	public String getTwo() {
		return two;
	}
	public void setTwo(String two) {
		this.two = two;
	}

	@ExcelField(title="总审批时间(小时)", align=2, sort=300)
	public String getThree() {
		return three;
	}
	public void setThree(String three) {
		this.three = three;
	}

	@ExcelField(title="完结总单数", align=2, sort=400)
	public String getFour() {
		return four;
	}
	public void setFour(String four) {
		this.four = four;
	}

	@Override
	public String toString() {
		return "FeatureToolOwnUn{" +
				"start=" + start +
				", end=" + end +
				", one='" + one + '\'' +
				", two='" + two + '\'' +
				", three='" + three + '\'' +
				", four='" + four + '\'' +
				'}';
	}
}
