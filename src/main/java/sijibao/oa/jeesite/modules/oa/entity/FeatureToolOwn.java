package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.common.utils.excel.annotation.ExcelField;

/**
 * 小工具（查询报表列表）
 * @author wanxb
 *
 */
public class FeatureToolOwn extends DataEntity<FeatureToolOwn> {

	private static final long serialVersionUID = 1L;
	private Date start ;//开始时间（搜索）
	private Date end ;//结束时间（搜索）
	private String one;//1
	private String two;//2
	private String three;//3
	private String four;//4
	private String five;//5
	private String six;//6
	private String seven ;//7
	private String eight ;//8

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
	@ExcelField(title="时间", align=2, sort=400)
	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}
	@ExcelField(title="功能名称", align=2, sort=400)
	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}
	@ExcelField(title="手机号", align=2, sort=400)
	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}
	@ExcelField(title="姓名", align=2, sort=400)
	public String getFour() {
		return four;
	}

	public void setFour(String four) {
		this.four = four;
	}
	@ExcelField(title="审批单数", align=2, sort=400)
	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}

	@ExcelField(title="总审批时间", align=2, sort=400)
	public String getSix() {
		return six;
	}

	public void setSix(String six) {
		this.six = six;
	}
	@ExcelField(title="审批中的单数", align=2, sort=400)
	public String getSeven() {
		return seven;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}
	@ExcelField(title="审批中的时间", align=2, sort=400)
	public String getEight() {
		return eight;
	}

	public void setEight(String eight) {
		this.eight = eight;
	}

	@Override
	public String toString() {
		return "FeatureToolOwn{" +
				"start=" + start +
				", end=" + end +
				", one='" + one + '\'' +
				", two='" + two + '\'' +
				", three='" + three + '\'' +
				", four='" + four + '\'' +
				", five='" + five + '\'' +
				", six='" + six + '\'' +
				", seven='" + seven + '\'' +
				", eight='" + eight + '\'' +
				'}';
	}
}
