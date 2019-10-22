package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.common.utils.excel.annotation.ExcelField;

/**
 * 小工具（查询报表列表）
 * @author wanxb
 *
 */
public class FeatureToolFlow extends DataEntity<FeatureToolFlow> {

	private static final long serialVersionUID = 1L;
	private Date start ;//开始时间（搜索）
	private Date end ;//结束时间（搜索）
	private String one;//1
	private String two;//2
	private String three;//3
	private String four;//4
	private String five;//5

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

	@ExcelField(title="审批人", align=2, sort=200)
	public String getTwo() {
		return two;
	}
	public void setTwo(String two) {
		this.two = two;
	}

	@ExcelField(title="审批总时间(小时)", align=2, sort=300)
	public String getThree() {
		return three;
	}
	public void setThree(String three) {
		this.three = three;
	}

	@ExcelField(title="审批总单数", align=2, sort=400)
	public String getFour() {
		return four;
	}
	public void setFour(String four) {
		this.four = four;
	}

	@ExcelField(title="审批总次数", align=2, sort=500)
	public String getFive() {
		return five;
	}
	public void setFive(String five) {
		this.five = five;
	}

	@Override
	public String toString() {
		return "FeatureToolFlow{" +
				"start=" + start +
				", end=" + end +
				", one='" + one + '\'' +
				", two='" + two + '\'' +
				", three='" + three + '\'' +
				", four='" + four + '\'' +
				", five='" + five + '\'' +
				'}';
	}
}
