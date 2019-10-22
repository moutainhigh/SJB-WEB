package sijibao.oa.jeesite.modules.intfz.bean;

import com.alibaba.fastjson.JSONObject;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回基础传输接口
 * @date 2016-02-29 15:46:02
 * @author xuhang
 */
@SuppressWarnings("serial")
@ApiModel(description="基础响应对象")
public class BaseResp<T> implements java.io.Serializable{

	@ApiModelProperty(value="结果编码:0成功,1失败", required = true)
	protected Integer status;	//结果编码	返回值:0表示成功,1表示失败
	@ApiModelProperty(value="结果描述")
	protected String message;	//结果描述	String(32)	结果描述	不可空
	@ApiModelProperty(value="业务数据")
	protected T data;  //业务数据
	
	public BaseResp(){
		
	}
	public BaseResp(IntfzRs intfzRs, String message, T data){
		this(intfzRs.getCode(), StringUtils.isBlank(message)?intfzRs.getMsg():message, data);
	}
	public BaseResp(Integer status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
}
