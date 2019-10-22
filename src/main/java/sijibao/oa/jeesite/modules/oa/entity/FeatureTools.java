package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.common.utils.excel.annotation.ExcelField;

/**
 * 小工具（查询报表列表）
 * @author wanxb
 *
 */
public class FeatureTools extends DataEntity<FeatureTools> {

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
	private String nine ;//9
	private String ten;//10
	@NotNull(message="开始时间不能为空！")
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}
	@NotNull(message="截止时间不能为空！")
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	@ExcelField(title="使用次数", align=2, sort=500)
	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}
	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}
	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}
	public String getFour() {
		return four;
	}

	public void setFour(String four) {
		this.four = four;
	}
	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}


	public String getSix() {
		return six;
	}

	public void setSix(String six) {
		this.six = six;
	}

	public String getSeven() {
		return seven;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}

	public String getEight() {
		return eight;
	}

	public void setEight(String eight) {
		this.eight = eight;
	}

	public String getNine() {
		return nine;
	}

	public void setNine(String nine) {
		this.nine = nine;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@Override
	public String toString() {
		return "FeatureTools{" +
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
				", nine='" + nine + '\'' +
				", ten='" + ten + '\'' +
				'}';
	}
}
