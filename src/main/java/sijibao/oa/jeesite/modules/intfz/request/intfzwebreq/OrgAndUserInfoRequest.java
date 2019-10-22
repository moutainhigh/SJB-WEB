package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq;


import io.swagger.annotations.ApiModelProperty;

/**
 * 机构用户返回结果对象
 *
 * @author xuby
 */
public class OrgAndUserInfoRequest {

    @ApiModelProperty(value = "查询类别，1机构信息，2机构信息+人员信息")
    private String queryType; //查询类别

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    @Override
    public String toString() {
        return "OrgAndUserInfoRequest [queryType=" + queryType + "]";
    }

}
