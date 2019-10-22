package sijibao.oa.jeesite.modules.intfz.request.common;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询字典请求
 */
@ApiModel
public class DictQueryReq {

    @ApiModelProperty(value = "字典类型（数组，必填）", required = true)
    private List<String> typeList;

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }
}
