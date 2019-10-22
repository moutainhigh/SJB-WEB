package sijibao.oa.jeesite.modules.sys.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 年会体重信息Entity
 * @author xby
 * @version 2019-01-24
 */
public class LuckyWeight extends DataEntity<LuckyWeight> {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal weight;		// 体重
	private String name;		// 姓名
	private BigDecimal high;    //身高
	
	private String orderByType;
	
	public LuckyWeight() {
		super();
	}

	public LuckyWeight(String id){
		super(id);
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	@Length(min=1, max=128, message="姓名长度必须介于 1 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(String orderByType) {
		this.orderByType = orderByType;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	
}