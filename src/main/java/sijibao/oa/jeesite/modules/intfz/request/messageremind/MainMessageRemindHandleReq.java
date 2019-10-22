package sijibao.oa.jeesite.modules.intfz.request.messageremind;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 消息提醒接收实体
 * @author: xgx
 * @create: 2019-09-04 14:11
 **/
@ApiModel(value="提醒管理服务--查询条件对象")
public class MainMessageRemindHandleReq implements Serializable {

    private static final long serialVersionUID = -9027321523576011450L;
    @ApiModelProperty(value="列表页面带入id（所有详情页面只传Id）")
    private String id;
    @ApiModelProperty(value="提醒管理分类标识：0-代办提醒，1-审批提醒，2-其他")
    private Integer remindCategory;

    @ApiModelProperty(value="页数")
    private int pageNo;//页数
    @ApiModelProperty(value="每页数量")
    private int pageSize;//每页数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRemindCategory() {
        return remindCategory;
    }

    public void setRemindCategory(Integer remindCategory) {
        this.remindCategory = remindCategory;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "MainMessageRemindHandleReq{" +
                "id='" + id + '\'' +
                ", remindCategory='" + remindCategory + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
